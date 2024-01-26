package com.hana.chagokchagok.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
public class AllocateCarRequest {
    private String carNo;
    private Boolean isDisabled;
}
