package com.hana.chagokchagok.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Report {

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
}
