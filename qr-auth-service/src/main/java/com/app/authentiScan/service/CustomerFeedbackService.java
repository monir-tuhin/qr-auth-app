package com.app.authentiScan.service;

import com.app.authentiScan.enums.*;
import com.app.authentiScan.exception.UserInformException;
import com.app.authentiScan.models.QrCodeValidation;
import com.app.authentiScan.models.QrData;
import com.app.authentiScan.payload.dto.CustomerFeedbackDto;
import com.app.authentiScan.models.CustomerFeedback;
import com.app.authentiScan.payload.request.QrVerificationSmsRequest;
import com.app.authentiScan.payload.response.QrCodeVerificationResponse;
import com.app.authentiScan.repository.CustomerFeedbackRepository;
import com.app.authentiScan.repository.QrCodeValidationRepository;
import com.app.authentiScan.repository.QrDataRepository;
import com.app.authentiScan.service.mapper.CustomerFeedbackMapper;
import com.app.authentiScan.service.query.QrCodeValidationQueryService;
import com.app.authentiScan.utils.UtilsService;
import com.app.authentiScan.utils.response.SmsResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerFeedbackService {

    private final CustomerFeedbackRepository customerFeedbackRepository;
    private final QrDataRepository qrDataRepository;
    private final QrCodeValidationRepository qrCodeValidationRepository;
    private final QrCodeValidationQueryService qrCodeValidationQueryService;
    private final SmsService smsService;

    public void save(CustomerFeedbackDto dto) {
        CustomerFeedback customerFeedback = new CustomerFeedback();
        BeanUtils.copyProperties(dto, customerFeedback);
        customerFeedbackRepository.save(customerFeedback);
    }

    @Transactional
    public QrCodeVerificationResponse qrCodeVerificationScan(String uid) {
        QrData qrData = qrDataRepository.findFirstByUidIgnoreCaseContaining(uid).orElse(null);

        if (Objects.nonNull(qrData)) {
            if (Objects.nonNull(qrData.getVerificationScanStatusEnum()) && qrData.getVerificationScanStatusEnum().equals(VerificationStatusEnum.VERIFIED)) {
                qrData.setQrVerificationCountScan(qrData.getQrVerificationCountScan() + 1);
            } else {
                qrData.setVerificationScanStatusEnum(VerificationStatusEnum.VERIFIED);
                qrData.setQrVerificationCountScan(1);
            }
            qrData.setLastQrVerificationScanAt(LocalDateTime.now());
            QrData updatedQrData = qrDataRepository.save(qrData);
            QrCodeValidation savedQrCodeValidation = saveQrCodeValidation(updatedQrData, ValidationMethodsEnum.SCAN, null, uid);

            CustomerFeedback customerFeedback = customerFeedbackRepository.findByProductUid(uid).orElseGet(()-> null);
            return CustomerFeedbackMapper.mapToQrCodeVerificationResponseFromQrData(updatedQrData, Objects.nonNull(customerFeedback));
        } else {
            saveQrCodeValidation(null, ValidationMethodsEnum.SCAN, null, uid);
        }

        return null;
    }


    @Transactional
    public QrCodeVerificationResponse qrCodeVerificationSms(QrVerificationSmsRequest request) {
        QrData qrData = qrDataRepository.findFirstByUidIgnoreCaseContaining(request.getUid()).orElse(null);

        if (Objects.nonNull(qrData)) {
            if (Objects.nonNull(qrData.getVerificationSmsStatusEnum()) && qrData.getVerificationSmsStatusEnum().equals(VerificationStatusEnum.VERIFIED)) {
                qrData.setQrVerificationCountSms(qrData.getQrVerificationCountSms() + 1);
            } else {
                qrData.setVerificationSmsStatusEnum(VerificationStatusEnum.VERIFIED);
                qrData.setQrVerificationCountSms(1);
            }

            qrData.setLastQrVerificationSmsAt(LocalDateTime.now());
            QrData updatedQrData = qrDataRepository.save(qrData);

            CustomerFeedback customerFeedback;
            if (updatedQrData.getQrVerificationCountSms() == 1) {
                CustomerFeedback feedbackBuilder = CustomerFeedback.builder()
                        .productUid(request.getUid())
                        .mobileNumber(request.getMobileNumber())
                        .opinion(request.getMessage())
                        .build();
                customerFeedback = customerFeedbackRepository.save(feedbackBuilder);
            } else {
                customerFeedback = customerFeedbackRepository.findByProductUid(request.getUid()).orElseGet(()-> null);
            }

            QrCodeValidation savedQrCodeValidation = saveQrCodeValidation(updatedQrData, ValidationMethodsEnum.SMS, request.getMobileNumber(), request.getUid());

            if (Objects.nonNull(savedQrCodeValidation)) {
               toSendSms(savedQrCodeValidation);
            }

            return CustomerFeedbackMapper.mapToQrCodeVerificationResponseFromQrData(updatedQrData, Objects.nonNull(customerFeedback));
        } else {
            QrCodeValidation savedQrCodeValidation = saveQrCodeValidation(null, ValidationMethodsEnum.SMS, request.getMobileNumber(), request.getUid());
            if (Objects.nonNull(savedQrCodeValidation)) {
                toSendSms(savedQrCodeValidation);
            }
        }

        return null;
    }

    private QrCodeValidation saveQrCodeValidation(QrData savedQrData, ValidationMethodsEnum validationMethodsEnum, String mobileNumber, String uid) {
        ValidationStatusEnum statusEnum = validationStatus(savedQrData, validationMethodsEnum);
        int dayOfMonth = LocalDateTime.now().getDayOfMonth();
        int monthValue = LocalDateTime.now().getMonthValue();
        int year = LocalDateTime.now().getYear();

        if (Objects.isNull(statusEnum)) {
            return null;
        }

        QrCodeValidation qrCodeValidation  = QrCodeValidation.builder()
                .uid(uid)
                .validationMethodsEnum(validationMethodsEnum)
                .mobileNumber(mobileNumber)
                .validationDate(LocalDateTime.now())
                .validationStatusEnum(statusEnum)
                .weekEnum(UtilsService.getWeekOfMonth(dayOfMonth))
                .month(UtilsService.getMonthName(monthValue))
                .quarterEnum(UtilsService.getQuarter(monthValue))
                .year(year)
                .build();

        return qrCodeValidationRepository.save(qrCodeValidation);
    }

    private ValidationStatusEnum validationStatus(QrData qrData, ValidationMethodsEnum validationMethodsEnum) {
        if (Objects.isNull(qrData)) {
            return ValidationStatusEnum.INVALID;
        }

        List<QrCodeValidation> qrCodeValidationList = qrCodeValidationQueryService.searchByUid(qrData.getUid());
        if (qrCodeValidationList.isEmpty()) {
            return ValidationStatusEnum.VALID; // First time use
        }
        if (qrCodeValidationList.size() >= 5) {
            return null;
        }

        boolean hasScan = qrCodeValidationList.stream()
                .anyMatch(entry -> ValidationMethodsEnum.SCAN.equals(entry.getValidationMethodsEnum()));
        boolean hasSms = qrCodeValidationList.stream()
                .anyMatch(entry -> ValidationMethodsEnum.SMS.equals(entry.getValidationMethodsEnum()));

        if ((validationMethodsEnum.equals(ValidationMethodsEnum.SMS) && hasScan) || (validationMethodsEnum.equals(ValidationMethodsEnum.SCAN) && hasSms)) {
            return ValidationStatusEnum.MULTI_ATTEMPT; // Mixed methods
        }


        long scanCount = qrCodeValidationList.stream().filter(i -> i.equals(ValidationMethodsEnum.SCAN)).count();
        long smsCount = qrCodeValidationList.stream().filter(i -> i.equals(ValidationMethodsEnum.SMS)).count();
        boolean isStatusValid = qrCodeValidationList.stream()
                .anyMatch( i -> i.getValidationStatusEnum().equals(ValidationStatusEnum.VALID));

        if (isStatusValid) {
            return ValidationStatusEnum.USED; // If valid exists, mark as used
        }

        return null;
    }

    private void toSendSms(QrCodeValidation savedQrCodeValidation) {
        String message = "";
        if (savedQrCodeValidation.getValidationStatusEnum() == ValidationStatusEnum.VALID) {
            message = "Welcome to Vini Cosmetics BD. This is the original product. Validation is ok. No need to Scan or SMS it again";
        } else if (savedQrCodeValidation.getValidationStatusEnum() == ValidationStatusEnum.USED) {
            message = "This code is already in use";
        } else if (savedQrCodeValidation.getValidationStatusEnum() == ValidationStatusEnum.MULTI_ATTEMPT) {
            message = "You already both attempts by scanning and SMS also";
        } else if (savedQrCodeValidation.getValidationStatusEnum() == ValidationStatusEnum.INVALID) {
            message = "This code is INVALID. No data found in the Vini database";
        }

        SmsResponse smsResponse = smsService.sendSms(savedQrCodeValidation.getMobileNumber(), message);
        if (smsResponse.getResponseCode() != 202) {
            throw new UserInformException(smsResponse.getErrorMessage());
        }
    }

}
