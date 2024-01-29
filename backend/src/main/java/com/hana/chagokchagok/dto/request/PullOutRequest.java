package com.hana.chagokchagok.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PullOutRequest {
    @JsonProperty("car_no")
    private String carNo;
}
