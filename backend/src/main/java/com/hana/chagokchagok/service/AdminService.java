package com.hana.chagokchagok.service;

import com.hana.chagokchagok.dto.request.ReportRequest;
import com.hana.chagokchagok.dto.response.ReportResponse;
import com.hana.chagokchagok.entity.Report;
import com.hana.chagokchagok.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hana.chagokchagok.dto.request.ExchangeRequest;
import com.hana.chagokchagok.dto.request.ReportRequest;
import com.hana.chagokchagok.dto.response.ReportResponse;
import com.hana.chagokchagok.entity.AllocationLog;
import com.hana.chagokchagok.entity.ParkingInfo;
import com.hana.chagokchagok.entity.RealtimeParking;
import com.hana.chagokchagok.entity.Report;
import com.hana.chagokchagok.exception.InvalidInputException;
import com.hana.chagokchagok.exception.SpotNotEmptyException;
import com.hana.chagokchagok.repository.RealtimeParkingRepository;
import com.hana.chagokchagok.repository.ReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final RealtimeParkingRepository realtimeParkingRepository;
    private final EntityManager em;
    private final ReportRepository reportRepository;

    /**
     * 신고 기록을 조회하는 메소드
     * @param reportRequest 페이지 정보, 오늘 날짜
     * @return 신고 기록 페이지에 필요한 데이터를 담은 ReportResponse
     */
    public ReportResponse getReportList(ReportRequest reportRequest) {
        // 현재 페이지 신고 내역
        Page<Report> page = reportRepository.findAll(reportRequest.getPageable());
        // 오늘의 신고 내역
        List<Report> todayReports = getTodayReports(reportRequest.getToday());
        return new ReportResponse(page, todayReports);
    }

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

    /**
     * FullName을 구역과 번호로 나눠주는 메소드
     * @param input
     * @return [0]은 구역, [1]은 번호
     */
    public String[] separateLocationInput(String input){
        String[] answer = new String[2];

        String regex = "([A-Za-z]+)(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 매칭된 경우에만 그룹을 가져옴
        if (matcher.matches()) {
            answer[0] = matcher.group(1);
            answer[1] = matcher.group(2);
        }
        return answer;
    }


}


