package com.hana.chagokchagok.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hana.chagokchagok.enums.SearchType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ExchangeRequest {
    @JsonProperty("car_num")
    private String carNumber; //잘못주차한 문제차량의 번호
    @JsonProperty("original_location")
    private String originalLocation; //원래위치
}






