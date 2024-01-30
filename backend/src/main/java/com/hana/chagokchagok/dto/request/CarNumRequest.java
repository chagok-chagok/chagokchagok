package com.hana.chagokchagok.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class CarNumRequest {
    @JsonProperty("car_num")
    private String carNum;
}
