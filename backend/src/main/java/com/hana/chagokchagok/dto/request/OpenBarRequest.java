package com.hana.chagokchagok.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class OpenBarRequest {
    private String carNo;
    private String areaCode;
    private Integer parkNo;
}
