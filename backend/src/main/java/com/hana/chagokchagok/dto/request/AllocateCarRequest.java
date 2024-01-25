package com.hana.chagokchagok.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AllocateCarRequest {
    private String carNo;
    private Boolean isDisabled;
}
