package com.hana.chagokchagok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter @Setter
public class ValidationParkingInfoDto {
    private Integer parkNo; // 주차장 구역 번호
    private String areaCode; // 구역 코드

    public String getFullName() {
        return (areaCode + parkNo);
    }
}
