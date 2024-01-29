package com.hana.chagokchagok.entity;

import com.hana.chagokchagok.dto.AllocationDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.mapping.Join;

import java.time.LocalDateTime;

//입출차기록(배정기록)
@Entity @Getter @ToString
public class AllocationLog {
    /**
     * Allocation Entity를 생성하는 정적 팩토리 메소드
     * @author 김용준
     * @param allocationDto Allocation Entity를 생성하기 위한 DTO 객체
     * @return allocationLog
     */
    public static AllocationLog createAllocationLog(AllocationDto allocationDto) {
        AllocationLog allocationLog = new AllocationLog();
        allocationLog.parkingInfo = allocationDto.getParkingInfo();
        allocationLog.carNo = allocationDto.getCarNo();
        allocationLog.entryTime = allocationDto.getEntryTime();
        return allocationLog;
    }

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

    public void changeParkingInfo(ParkingInfo parkingInfo){
        this.parkingInfo = parkingInfo;
    }
}
