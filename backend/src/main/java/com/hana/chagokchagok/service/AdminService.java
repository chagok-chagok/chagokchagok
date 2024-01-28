package com.hana.chagokchagok.service;

import com.hana.chagokchagok.dto.request.ExchangeRequest;
import com.hana.chagokchagok.entity.AllocationLog;
import com.hana.chagokchagok.entity.ParkingInfo;
import com.hana.chagokchagok.entity.RealtimeParking;
import com.hana.chagokchagok.exception.SpotNotEmptyException;
import com.hana.chagokchagok.repository.RealtimeParkingRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final RealtimeParkingRepository realtimeParkingRepository;
    private final EntityManager em;

    public ResponseEntity<String> exchangeAllocation(ExchangeRequest exchangeRequest) {

        try{
            RealtimeParking originRealtimeParking = realtimeParkingRepository
                    .findByParkingInfo_ParkNo(exchangeRequest.getOriginalLocation());
            RealtimeParking targetRealtimeParking = realtimeParkingRepository
                    .findByAllocationLog_CarNo(exchangeRequest.getCarNumber());

            if(targetRealtimeParking == null) {
                throw new SpotNotEmptyException(exchangeRequest.getCarNumber()+"에 해당하는 자리가 존재하지 않습니다.");
            }

            //Original, Target 정보를 임시변수에 저장
            ParkingInfo tmpOriginParkingInfo = originRealtimeParking.getAllocationLog().getParkingInfo();
            ParkingInfo tmpTargetParkingInfo = targetRealtimeParking.getAllocationLog().getParkingInfo();

            //(1)주차배정로그 테이블의 배정내역을 변경
            originRealtimeParking.getAllocationLog().changeParkingInfo(tmpTargetParkingInfo);
            targetRealtimeParking.getAllocationLog().changeParkingInfo(tmpOriginParkingInfo);

            //변경된 Original, Target 정보를 임시변수에 저장
            AllocationLog tmpOriginAllocationLog = originRealtimeParking.getAllocationLog();
            AllocationLog tmpTargetAllocationLog = targetRealtimeParking.getAllocationLog();

            //(2)주차현황 테이블의 매핑을 변경 - 변경감지를 사용하므로 제약조건위배 방지를 위해 null로 초기화후 사용
            originRealtimeParking.changeAllocationLog(null);
            targetRealtimeParking.changeAllocationLog(null);

            em.flush();

            originRealtimeParking.changeAllocationLog(tmpTargetAllocationLog);
            targetRealtimeParking.changeAllocationLog(tmpOriginAllocationLog);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SpotNotEmptyException e) {
            return new ResponseEntity<>("Spot not empty: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}


