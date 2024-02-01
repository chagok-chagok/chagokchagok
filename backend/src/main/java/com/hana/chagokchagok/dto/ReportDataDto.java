package com.hana.chagokchagok.dto;

import com.hana.chagokchagok.enums.ErrorCode;
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
public class ReportDataDto {
    private long no;
    private LocalDateTime time;
    private ErrorCode code;
    private String note;
}
