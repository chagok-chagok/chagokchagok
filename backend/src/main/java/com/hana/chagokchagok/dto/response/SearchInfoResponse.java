package com.hana.chagokchagok.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchInfoResponse {
    private String area;
    private String car_no;
    private LocalDateTime entryTime;
}
