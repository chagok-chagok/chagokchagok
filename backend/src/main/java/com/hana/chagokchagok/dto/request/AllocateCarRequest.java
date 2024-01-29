package com.hana.chagokchagok.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
public class AllocateCarRequest {
    @JsonProperty("car_no")
    private String carNo; // 차량 번호
    @JsonProperty("is_disabled")
    private Boolean isDisabled; // 장애 여부
}
