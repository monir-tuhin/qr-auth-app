package com.app.authentiScan.utils.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsResponse {
    @JsonProperty("response_code")
    private Integer responseCode;

    @JsonProperty("success_message")
    private String successMessage;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("message_id")
    private Integer messageId;

    public SmsResponse(Integer responseCode, String successMessage, String errorMessage, Integer messageId) {
        this.responseCode = responseCode;
        this.successMessage = successMessage;
        this.errorMessage = errorMessage;
        this.messageId = messageId;
    }
}
