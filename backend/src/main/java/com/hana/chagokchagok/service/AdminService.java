package com.hana.chagokchagok.service;

<<<<<<< PATCH SET (0814e0 :bug: rebase로 인한 merge로 버그 수정)
import com.hana.chagokchagok.dto.request.ReportRequest;
import com.hana.chagokchagok.dto.response.ReportResponse;
import com.hana.chagokchagok.entity.Report;
import com.hana.chagokchagok.exception.InvalidInputException;
import com.hana.chagokchagok.repository.ReportRepository;
=======
import com.hana.chagokchagok.dto.request.ExchangeRequest;
import com.hana.chagokchagok.entity.AllocationLog;
import com.hana.chagokchagok.entity.ParkingInfo;
import com.hana.chagokchagok.entity.RealtimeParking;
import com.hana.chagokchagok.exception.SpotNotEmptyException;
import com.hana.chagokchagok.repository.RealtimeParkingRepository;
import jakarta.persistence.EntityManager;
>>>>>>> BASE      (9feb6d :sparkles: 관리자용 자리교환 기능 완성)
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

<<<<<<< PATCH SET (0814e0 :bug: rebase로 인한 merge로 버그 수정)
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

=======
>>>>>>> BASE      (9feb6d :sparkles: 관리자용 자리교환 기능 완성)
@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final RealtimeParkingRepository realtimeParkingRepository;
    private final EntityManager em;

<<<<<<< PATCH SET (0814e0 :bug: rebase로 인한 merge로 버그 수정)
    private final ReportRepository reportRepository;
    private final RealtimeParkingRepository realtimeParkingRepository;
    private final EntityManager em;
=======
    public ResponseEntity<String> exchangeAllocation(ExchangeRequest exchangeRequest) {
>>>>>>> BASE      (9feb6d :sparkles: 관리자용 자리교환 기능 완성)

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
<<<<<<< PATCH SET (0814e0 :bug: rebase로 인한 merge로 버그 수정)

    /**
     * 오늘 들어온 신고를 조회하는 메소드
     * @param today 오늘 날짜
     * @return 오늘 신고 리스트
     */
    private List<Report> getTodayReports(LocalDate today) {
        LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIN); // 오늘 0시 0분
        LocalDateTime endOfDay = LocalDateTime.of(today, LocalTime.MAX); // 오늘 23시 59분 59초 ...
        List<Report> todayReports = reportRepository.findAllByReportTimeBetween(startOfDay, endOfDay);
        return todayReports;
    }

    /**
     * 신고가 들어온 두 자리를 교환하는 메소드
     * @param exchangeRequest(신고가 들어온 자리, 잘못 주차한차량번호)
     * @return ResponseEntity
     */
    public ResponseEntity<String> exchangeAllocation(ExchangeRequest exchangeRequest) {

        try{
            //입력값을 두 필드로 전환
            String[] originlocation = separateLocationInput(exchangeRequest.getOriginalLocation());
            if(originlocation[0] == null) {
                throw new InvalidInputException(exchangeRequest.getOriginalLocation()+"는 존재하지 않는 자리번호입니다.");
            }

            //RealTime 엔티티객체 찾기
            RealtimeParking originRealtimeParking = realtimeParkingRepository
                    .findByParkingInfo_ParkNoAndParkingInfo_AreaCode(Integer.valueOf(originlocation[1]), originlocation[0]);
            RealtimeParking targetRealtimeParking = realtimeParkingRepository
                    .findByAllocationLog_CarNo(exchangeRequest.getCarNumber());

            if(targetRealtimeParking == null) {
                throw new SpotNotEmptyException(exchangeRequest.getCarNumber()+"에 해당하는 데이터가 존재하지 않습니다.");
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

            //(2)주차현황 테이블의 매핑을 변경 - 변경감지를 사용하므로 제약조건위배 방지를 위해 null로 초기화후 flush하고 사용
            originRealtimeParking.changeAllocationLog(null);
            targetRealtimeParking.changeAllocationLog(null);

            em.flush();

            originRealtimeParking.changeAllocationLog(tmpTargetAllocationLog);
            targetRealtimeParking.changeAllocationLog(tmpOriginAllocationLog);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SpotNotEmptyException e) {
            return new ResponseEntity<>("Spot not empty: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InvalidInputException e) {
            return new ResponseEntity<>("Invalid Input : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public String[] separateLocationInput(String input){
        String[] answer = new String[2];

        String regex = "([A-Za-z]+)(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 매칭된 경우에만 그룹을 가져옴
        if (matcher.matches()) {
            answer[0] = matcher.group(1);
            answer[1] = matcher.group(2);
            System.out.println("Letters: " + answer[0]);
            System.out.println("Numbers: " + answer[1]);
        }
        return answer;
    }


=======
>>>>>>> BASE      (9feb6d :sparkles: 관리자용 자리교환 기능 완성)
}


