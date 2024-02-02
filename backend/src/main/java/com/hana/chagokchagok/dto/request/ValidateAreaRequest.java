package com.hana.chagokchagok.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ValidateAreaRequest {
    private String carNo; // 구역에 진입한 차량 번호
    private String area; // 촬영된 구역 코드
}
