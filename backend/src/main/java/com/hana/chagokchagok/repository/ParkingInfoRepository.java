package com.hana.chagokchagok.repository;

import com.hana.chagokchagok.dto.ValidationParkingInfoDto;
import com.hana.chagokchagok.entity.ParkingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingInfoRepository extends JpaRepository<ParkingInfo, Integer> {
    // ParkingInfo Entity 형태로 반환받고 싶은데 이거 어떻게 하는거지?
    // Entity의 정적 팩토리 메소드를 호출하는 건 안되는 것 같음

    /**
     * 차량 번호로 배정 자리 정보를 검색하는 메소드
     * @author 김용준
     * @param carNumber 차량 번호
     * @return parkId, areaCode를 가진 ValidationParkingInfoDto
     */
    @Query("SELECT NEW com.hana.chagokchagok.dto.ValidationParkingInfoDto(pi.parkId, pi.areaCode) " +
            "FROM ParkingInfo pi " +
            "JOIN RealtimeParking rp ON pi.parkId = rp.parkId " +
            "JOIN AllocationLog al ON rp.log = al " +
            "WHERE al.carNo = :carNumber")
    ValidationParkingInfoDto findValidationParkingInfo(@Param("carNumber") String carNumber);

}
