package com.hana.chagokchagok.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hana.chagokchagok.dto.ValidationParkingInfoDto;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ValidateAreaResponse {
    public ValidateAreaResponse(ValidationParkingInfoDto parkingInfoDto) {
        this.parkId = parkingInfoDto.getParkId();
    }
    @JsonProperty("park_no")
    private final Integer parkId; // 해제할 차단봉 자리 번호
}
