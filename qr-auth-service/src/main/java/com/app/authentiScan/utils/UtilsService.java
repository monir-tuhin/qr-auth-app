package com.app.authentiScan.utils;

import com.app.authentiScan.enums.QuarterEnum;
import com.app.authentiScan.enums.WeekEnum;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class UtilsService {
    private static final String BASE36_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String[] MONTHS = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    public static String generateRandomString() {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(BASE36_CHARS.charAt(random.nextInt(BASE36_CHARS.length())));
        }
        return sb.toString();
    }

    public static ResponseEntity<byte[]> respondReportOutputWithoutHeader(CustomJasperReport jasperReport, boolean forceDownload) throws IOException {
        if (jasperReport == null || jasperReport.getContent() == null) {
            throw new FileNotFoundException("jasper Report Not found");
        } else {
            String outputFileName = (jasperReport.getOutputFilename()) + "." + jasperReport.getReportFormat().getExtension();
            String contentDisposition = forceDownload == true ? "attachment;filename=\"" + outputFileName + "\"" : "filename=\"" + outputFileName + "\"";
            return ResponseEntity
                    .ok()
                    .header("Content-Type", jasperReport.getReportFormat().getMimeType() + ";charset=UTF-8")
                    .header("Content-Disposition", contentDisposition)
                    .body(jasperReport.getContent());

        }
    }

    public static String getResourcePath(String filePath) {
        Resource resource = new ClassPathResource(filePath);
        try {
            return resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public static InputStream getResourceAsStream(String filePath) {
        Resource resource = new ClassPathResource(filePath);
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public static InputStream getResourceAsStream(String filePath) {
        Resource resource = new ClassPathResource(filePath);
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource: " + filePath, e);
        }
    }

    public static WeekEnum getWeekOfMonth(int dayOfMonth) {
        if (dayOfMonth >= 1 && dayOfMonth <= 7) {
            return WeekEnum.WEEK_1;
        } else if (dayOfMonth >= 8 && dayOfMonth <= 14) {
            return WeekEnum.WEEK_2;
        } else if (dayOfMonth >= 15 && dayOfMonth <= 22) {
            return WeekEnum.WEEK_3;
        } else {
            return WeekEnum.WEEK_4;
        }
    }

    public static QuarterEnum getQuarter(int month) {
        if (month >= 1 && month <= 3) {
            return QuarterEnum.Q1;
        } else if (month >= 4 && month <= 6) {
            return QuarterEnum.Q2;
        } else if (month >= 7 && month <= 9) {
            return QuarterEnum.Q3;
        } else {
            return QuarterEnum.Q4;
        }
    }

    public static String getMonthName(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            return "Invalid Month"; // Error handling
        }
        return MONTHS[monthNumber - 1]; // Array index starts from 0
    }

}
