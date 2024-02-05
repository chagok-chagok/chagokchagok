package com.hana.chagokchagok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter @Setter
public class ValidationParkingInfoDto {
    private Integer parkId; // 주차장 번호
    private String areaCode; // 구역 코드
    public String getFullName() {
        if (this.parkId == null || this.areaCode == null) return null;
        return this.areaCode + this.parkId;
    }
}
