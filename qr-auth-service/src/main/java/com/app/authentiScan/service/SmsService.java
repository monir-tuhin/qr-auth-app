package com.app.authentiScan.service;

import com.app.authentiScan.utils.response.SmsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class SmsService {
    private final String apiUrl = "http://bulksmsbd.net/api/smsapi";

    public SmsResponse sendSms(String phoneNumber, String message) {
        String encodedMessage;
        try {
            encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            log.error("Error in encoding message::::::::::::::::: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("api_key", "fQfptMb6CLu2cPkKX4Kb")
                .queryParam("type", "text")
                .queryParam("number", phoneNumber)
                .queryParam("senderid", "8809648902746")
                .queryParam("message", encodedMessage);

        RestTemplate restTemplate = new RestTemplate();
        SmsResponse smsResponse = restTemplate.getForObject(builder.toUriString(), SmsResponse.class);
        if (Objects.nonNull(smsResponse)) {
            log.info("SMS Gateway Response::::::::::::::::::::::: {}", smsResponse);
        }

        return smsResponse;
    }

}
