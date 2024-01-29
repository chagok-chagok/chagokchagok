package com.hana.chagokchagok.repository;

import com.hana.chagokchagok.entity.ParkingInfo;
import com.hana.chagokchagok.entity.RealtimeParking;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealtimeParkingRepository extends JpaRepository<RealtimeParking, Integer> {
    @Override
    @EntityGraph(attributePaths = {"parkingInfo"})
    List<RealtimeParking> findAll();

    RealtimeParking findByParkingInfo_ParkNoAndParkingInfo_AreaCode(int parkNo, String areaCode);
    RealtimeParking findByAllocationLog_CarNo(String carNo);

    /**
     * 장애 좌석 배정 가능 메소드
     * @author 김용준
     * @return RealtimeParking
     */
    @Query("SELECT r FROM RealtimeParking r " +
            "JOIN ParkingInfo p ON r.parkId = p.parkId " +
            "WHERE r.allocationLog IS NULL " +
            "ORDER BY p.isDisabled DESC, r.parkId ASC " +
            "LIMIT 1")
    RealtimeParking findFirstWhoIsDisabled();

    /**
     * 장애 좌석 배정 불가 메소드
     * @author 김용준
     * @return RealtimeParking
     */
    @Query("SELECT r FROM RealtimeParking r " +
            "JOIN ParkingInfo p ON r.parkId = p.parkId " +
            "WHERE r.allocationLog IS NULL AND p.isDisabled = false " +
            "ORDER BY r.parkId ASC " +
            "LIMIT 1")
    RealtimeParking findFirstWhoIsNotDisabled();
}
