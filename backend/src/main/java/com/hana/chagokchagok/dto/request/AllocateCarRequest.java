package com.hana.chagokchagok.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AllocateCarRequest {
    private String carNo; // 차량 번호
    private Boolean isDisabled; // 장애 여부
}
