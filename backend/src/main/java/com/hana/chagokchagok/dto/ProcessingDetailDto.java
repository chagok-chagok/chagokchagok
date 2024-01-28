package com.hana.chagokchagok.dto;

import com.hana.chagokchagok.entity.Report;
import com.hana.chagokchagok.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class ProcessingDetailDto {
    public ProcessingDetailDto(Report report) {
        this.code = report.getErrorCode();
        this.time = report.getDoneTime();
        this.location = report.getParkingInfo().getFullName();
    }
    private ErrorCode code;
    private LocalDateTime time;
    private String location;

}
