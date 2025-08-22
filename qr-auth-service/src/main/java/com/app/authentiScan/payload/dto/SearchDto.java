package com.app.authentiScan.payload.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    private Integer page = 0;
    private Integer size = 10;
    private Map<String, String> sortKeyOrderMap;
}
