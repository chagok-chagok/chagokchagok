package com.hana.chagokchagok.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hana.chagokchagok.entity.Report;
import com.hana.chagokchagok.enums.ErrorCode;
import com.hana.chagokchagok.enums.ReportStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

// 신고 정보 DTO
@Getter @Setter
@ToString
public class ReportDto {
    // 신고 수정 요청 시 Json의 String 값을 Enum 필드로 매핑하는 생성자
    @JsonCreator
    public ReportDto(
            @JsonProperty("error_code") String errorCode,
            @JsonProperty("status") String status) {
        this.errorCode = ErrorCode.valueOf(errorCode);
        this.status = ReportStatus.valueOf(status);
    }
    public ReportDto(Report report) {
        this.reportId = report.getReportId();
        this.parkId = report.getParkingInfo().getParkId();
        this.reportTime = report.getReportTime();
        this.doneTime = report.getDoneTime();
        this.errorCode = report.getErrorCode();
        this.status = report.getReportStatus();
        this.note = report.getNote();
        this.fullName = report.getParkingInfo().getFullName();
    }
    @JsonProperty("report_id")
    private Long reportId; // 신고번호
    @JsonProperty("park_id")
    private Integer parkId; // 주차장 자리 번호
    @JsonProperty("report_time")
    private LocalDateTime reportTime; // 신고 시각
    @JsonProperty("done_time")
    private LocalDateTime doneTime; // 처리 시각
    @JsonProperty("error_code")
    private ErrorCode errorCode; // 신고 코드
    @JsonProperty("status")
    private ReportStatus status; // 처리 상태
    @JsonProperty("note")
    private String note; // 비고
    @JsonProperty("full_name")
    private String fullName; // 주차장 풀네임
}
