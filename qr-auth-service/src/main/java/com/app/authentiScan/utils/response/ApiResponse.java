package com.app.authentiScan.utils.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T>{
    private T data;
    private Integer httpStatusCode;
    private Boolean status;
    private String message;

    public ApiResponse(T data, Integer httpStatusCode, Boolean status, String message) {
        this.data = data;
        this.httpStatusCode = httpStatusCode;
        this.status = status;
        this.message = message;
    }
}
