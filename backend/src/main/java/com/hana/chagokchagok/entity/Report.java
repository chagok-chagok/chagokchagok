package com.hana.chagokchagok.entity;

import com.hana.chagokchagok.dto.ReportDto;
import com.hana.chagokchagok.enums.ErrorCode;
import com.hana.chagokchagok.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity @Getter
@ToString @NoArgsConstructor
public class Report {
    public void updateParkingInfo(ParkingInfo parkingInfo) {
        this.parkingInfo = parkingInfo;
    }
    public void updateReport(ReportDto reportDto) {
        this.reportId = reportDto.getReportId();
        this.errorCode = reportDto.getErrorCode();
        this.reportTime = reportDto.getReportTime();
        this.doneTime = reportDto.getDoneTime();
        this.reportStatus = reportDto.getStatus();
        this.note = reportDto.getNote();
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="park_id")
    private ParkingInfo parkingInfo;

    @Enumerated(EnumType.STRING)
    private ErrorCode errorCode;

    @Column(name="report_time")
    private LocalDateTime reportTime;

    @Column(name="done_time")
    private LocalDateTime doneTime;

    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    private String note;

    public Report(ParkingInfo parkingInfo) {
        this.parkingInfo = parkingInfo;
        this.errorCode = ErrorCode.AUTO_REPORT;
        this.reportTime = LocalDateTime.now();
        this.reportStatus = ReportStatus.UNRESOLVED;
    }

    public static Report createReport(ParkingInfo parkingInfo) {
        return new Report(parkingInfo);
    }

}
