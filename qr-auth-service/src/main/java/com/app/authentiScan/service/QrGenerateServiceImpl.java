package com.app.authentiScan.service;

import com.app.authentiScan.enums.VerificationStatusEnum;
import com.app.authentiScan.exception.ResourceNotFoundException;
import com.app.authentiScan.exception.UserInformException;
import com.app.authentiScan.payload.dto.QrPrintData;
import com.app.authentiScan.models.QrData;
import com.app.authentiScan.payload.dto.search.QrVerificationHistorySearchDto;
import com.app.authentiScan.payload.request.QrCodePdfRequest;
import com.app.authentiScan.payload.response.QrCodeSummaryReportResponse;
import com.app.authentiScan.payload.response.QrCodeSummaryResponse;
import com.app.authentiScan.payload.response.QrVerificationHistoryResponse;
import com.app.authentiScan.repository.QrDataRepository;
import com.app.authentiScan.service.mapper.QrGeneratorMapper;
import com.app.authentiScan.service.query.QrDataQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class QrGenerateServiceImpl implements QrGenerateService {

    private final QrDataRepository qrDataRepository;
    private final Environment environment;
    private final QrDataQueryService qrDataQueryService;


    @Override
    public byte[] generateQrCodePdf(QrCodePdfRequest request) {
        List<QrData> qrDataSaveList = qrDataRepository.getQRListBySl(request.getCountFrom(), request.getCountTo());
        List<QrPrintData> data = Collections.singletonList(QrPrintData.builder().qrDataList(qrDataSaveList).build());

        try {
            // Load the JasperReport template as a resource stream
            Resource resource = new ClassPathResource("/reports/qr_code.jrxml");
            log.debug("Loading resource: {}", resource.getURL());
            InputStream reportFile = resource.getInputStream();

            // Compile and fill the report
            Map<String, Object> params = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    JasperCompileManager.compileReport(reportFile), // Compile the report
                    params, // Parameters (if any)
                    new JRBeanCollectionDataSource(data) // Data source
            );

           /* // ✅ Set properties to ensure vector export
            jasperPrint.setProperty("net.sf.jasperreports.export.pdf.force.synchronous", "true");
            jasperPrint.setProperty("net.sf.jasperreports.export.pdf.create.batch.mode", "false");
            jasperPrint.setProperty("net.sf.jasperreports.export.pdf.svg.barcode", "true");

            // ✅ Use JRPdfExporter instead of JasperExportManager
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

            // ✅ PDF Configuration (Enable Compression, Optimize Output)
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setCompressed(true);  // Enable compression
            exporter.setConfiguration(configuration);
            // Export to PDF
            exporter.exportReport();
            return outputStream.toByteArray();*/

            // Export the report to PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }  catch (Exception ex) {
            log.error("An error occurred during printing sample label: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to generate QR Code PDF", ex);
        }
    }

    //    this code taking less time to execute than upper code
    @Override
    public void generateQrCode(Integer count, String brandKey) {
        int lastSerial = qrDataRepository.findTopByOrderByIdDesc()
                .map(QrData::getSl)
                .orElseGet(()-> 0);

        // Generate the QrData list in parallel for better performance
        List<QrData> qrDataList = IntStream.range(1, count + 1)
                .parallel()
                .mapToObj(i -> QrData.builder()
                        .uid(generateUID())
                        .sl(lastSerial + i)
                        .verificationScanStatusEnum(VerificationStatusEnum.PENDING)
                        .verificationSmsStatusEnum(VerificationStatusEnum.PENDING)
                        .brandKey(brandKey)
                        .build())
                .collect(Collectors.toList());

        // Save in batches
        int batchSize = 500;
        for (int i = 0; i < qrDataList.size(); i += batchSize) {
            int end = Math.min(i + batchSize, qrDataList.size());
            List<QrData> batchList = qrDataList.subList(i, end);
            try {
                qrDataRepository.saveAll(batchList);
                qrDataRepository.flush(); // Ensures immediate write to DB
            } catch (DataIntegrityViolationException e) {
                // Regenerate only the duplicates
                log.error("Duplicate uid generated: {}", e.getMessage(), e);
                throw new UserInformException("Duplicate uid generated {}", e);
            }
        }
    }

    Set<String> generatedUids = new HashSet<>();
    public String generateUID() {
        String uid;
        do {
            uid = UUID.randomUUID().toString().replaceAll("^[A-Z0-9]+$", "").substring(0, 8);
        } while (generatedUids.contains(uid)); // Avoid in-memory duplicates
        generatedUids.add(uid);
        return uid;
    }

    @Override
    public QrCodeSummaryResponse getQrCodeSummary() {
        return qrDataQueryService.getQrCodeSummary();
    }

    @Override
    public Page<QrVerificationHistoryResponse> searchVerificationHistoryPage(QrVerificationHistorySearchDto searchDto) {
        Page<QrData> page = qrDataQueryService.searchPage(searchDto);
        List<QrVerificationHistoryResponse> resultList = page.getContent()
                .stream()
                .map(QrGeneratorMapper::mapToQrVerificationHistoryResponseFromQrData)
                .collect(Collectors.toList());
        return new PageImpl<>(resultList, page.getPageable(), page.getTotalElements());
    }

    @Override
    public byte[] generateQrCodeSummaryReport(QrVerificationHistorySearchDto dto) {
        QrData qrData = qrDataRepository.findFirstByUid(dto.getUid()).orElseThrow(()->
                new ResourceNotFoundException("No QR Data found with uid " + dto.getUid()));
        QrCodeSummaryReportResponse summaryReport = QrGeneratorMapper.mapToQrSummaryReportResponseFromQrData(qrData);
        try {
            // Load the JasperReport template as a resource stream
            Resource resource = new ClassPathResource("/reports/qr_code_summary.jrxml");
            log.debug("Loading resource: {}", resource.getURL());
            InputStream reportFile = resource.getInputStream();

            // Compile and fill the report
            Map<String, Object> params = new HashMap<>();
            params.put("companyTitle", "Vini Global, Bangladesh");
            params.put("reportTitle", "QR Code Summary");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    JasperCompileManager.compileReport(reportFile), // Compile the report
                    params, // Parameters (if any)
                    new JRBeanCollectionDataSource(Collections.singletonList(summaryReport)) // Data source
            );

            // Export the report to PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }  catch (Exception ex) {
            log.error("An error occurred during printing QR code summary: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to generate QR Code Summary PDF", ex);
        }
    }

}
