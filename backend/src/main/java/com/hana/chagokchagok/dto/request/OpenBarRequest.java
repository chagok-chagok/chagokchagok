package com.hana.chagokchagok.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
// 특정 위치 차단바 해제용 RequestDto
public class OpenBarRequest {
    @JsonProperty("car_no")
    private String carNo;
    @JsonProperty("area_code")
    private String areaCode;
    @JsonProperty("park_no")
    private Integer parkNo;
}
