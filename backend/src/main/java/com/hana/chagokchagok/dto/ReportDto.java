package com.hana.chagokchagok.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class ReportDto {
    private Integer reportId;
    private Integer parkId;
    private LocalDateTime reportTime;
    private LocalDateTime doneTime;
    private String errorCode;
    private String status;
    private String note;
}
