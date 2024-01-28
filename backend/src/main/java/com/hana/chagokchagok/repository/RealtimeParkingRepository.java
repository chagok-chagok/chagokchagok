package com.hana.chagokchagok.repository;

import com.hana.chagokchagok.entity.RealtimeParking;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealtimeParkingRepository extends JpaRepository<RealtimeParking, Integer> {
    @Override
    @EntityGraph(attributePaths = {"parkingInfo"})
    List<RealtimeParking> findAll();

    RealtimeParking findByParkingInfo_ParkNo(String parkNo);
    RealtimeParking findByAllocationLog_CarNo(String carNo);
}
