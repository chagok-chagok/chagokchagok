package com.hana.chagokchagok.repository;

import com.hana.chagokchagok.entity.ParkingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingInfoRepository extends JpaRepository<ParkingInfo, Integer> {
}
