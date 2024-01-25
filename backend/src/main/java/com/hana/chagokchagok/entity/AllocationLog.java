package com.hana.chagokchagok.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.mapping.Join;

import java.time.LocalDateTime;

//입출차기록(배정기록)
@Entity @Getter
public class AllocationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="allocation_id")
    private Long allocationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="park_id")
    private ParkingInfo parkingInfo;

    @Column(name="car_number")
    private String carNo;

    @Column(name="payment_status")
    private Boolean paymentStatus;

    @Column(name="parking_fee")
    private Integer parkingFee;

    @Column(name="entry_time")
    private LocalDateTime entryTime;

    @Column(name="exit_time")
    private LocalDateTime exitTime;
}
