package com.hana.chagokchagok.dto;

import com.hana.chagokchagok.entity.Report;
import com.hana.chagokchagok.enums.ErrorCode;
import com.hana.chagokchagok.enums.ReportStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
public class ReportDto {
    public ReportDto(Report report) {
        this.reportId = report.getReportId();
        this.parkId = report.getParkingInfo().getParkId();
        this.reportTime = report.getReportTime();
        this.doneTime = report.getDoneTime();
        this.errorCode = report.getErrorCode();
        this.status = report.getReportStatus();
        this.note = report.getNote();
    }

    private Long reportId; // 신고번호
    private Integer parkId; // 주차장 자리 번호
    private LocalDateTime reportTime; // 신고 시각
    private LocalDateTime doneTime; // 처리 시각
    private ErrorCode errorCode; // 신고 코드
    private ReportStatus status; // 처리 상태
    private String note; // 비고
}
