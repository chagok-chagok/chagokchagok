package com.hana.chagokchagok.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AllocateResponse {
    private String carNo;
    private String allocatedLocation;
    private LocalDateTime enterTime;
}
