package com.hana.chagokchagok.entity;

import jakarta.persistence.*;
import org.hibernate.mapping.Join;

import java.time.LocalDateTime;

//주차배정기록
@Entity
public class AllocationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="allocation_id")
    private Long allocationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="park_id")
    private ParkingInfo parkingInfo;

    @Column(name="car_number")
    private String carNumber;

    @Column(name="parment_status")
    private Boolean paymentStatus;

    @Column(name="parking_fee")
    private Integer parkingFee;

    @Column(name="entry_time")
    private LocalDateTime entryTime;

    @Column(name="exit_time")
    private LocalDateTime exitTime;
}
