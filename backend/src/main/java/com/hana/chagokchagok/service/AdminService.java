package com.hana.chagokchagok.service;

import com.hana.chagokchagok.dto.AllocationDto;
import com.hana.chagokchagok.dto.ReportDataDto;
import com.hana.chagokchagok.dto.ReportDto;
import com.hana.chagokchagok.dto.ReportRateDto;
import com.hana.chagokchagok.dto.UnresolvedDto;
import com.hana.chagokchagok.dto.request.ChangePasswordRequest;
import com.hana.chagokchagok.dto.request.ExchangeRequest;
import com.hana.chagokchagok.dto.request.LoginRequest;
import com.hana.chagokchagok.dto.request.ReportRequest;
import com.hana.chagokchagok.dto.response.ChangePasswordDto;
import com.hana.chagokchagok.dto.response.CommonAlertResponse;
import com.hana.chagokchagok.dto.response.DashBoardResponse;
import com.hana.chagokchagok.dto.response.LoginResponse;
import com.hana.chagokchagok.dto.response.ReportResponse;
import com.hana.chagokchagok.entity.Admin;
import com.hana.chagokchagok.entity.AllocationLog;
import com.hana.chagokchagok.entity.ParkingInfo;
import com.hana.chagokchagok.entity.RealtimeParking;
import com.hana.chagokchagok.entity.Report;
import com.hana.chagokchagok.enums.ErrorCode;
import com.hana.chagokchagok.enums.ReportStatus;
import com.hana.chagokchagok.exception.InvalidInputException;
import com.hana.chagokchagok.exception.SpotNotEmptyException;
import com.hana.chagokchagok.repository.AdminRepository;
import com.hana.chagokchagok.repository.AllocationLogRepository;
import com.hana.chagokchagok.repository.ParkingInfoRepository;
import com.hana.chagokchagok.repository.RealtimeParkingRepository;
import com.hana.chagokchagok.repository.ReportRepository;
import com.hana.chagokchagok.util.JWTUtil;
import com.hana.chagokchagok.util.SHA256;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hana.chagokchagok.util.SeparateLocation.separateLocationInput;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final RealtimeParkingRepository realtimeParkingRepository;
    private final EntityManager em;
    private final ReportRepository reportRepository;
    private final AdminRepository adminRepository;
    private final AllocationLogRepository allocationLogRepository;
    private final JWTUtil jwtUtil;
    private final ParkingInfoRepository parkingInfoRepository;

    public LoginResponse login(LoginRequest adminInfoDto) {
        SHA256 sha256 = new SHA256();
        LoginResponse loginResponse = new LoginResponse();
        try{
            Admin admin = adminRepository.findById(adminInfoDto.getId());
            if(admin != null && sha256.encrypt(adminInfoDto.getPass()).equals(admin.getPassword())){

                String accessToken = jwtUtil.createAccessToken(adminInfoDto.getId());
                String refreshToken = jwtUtil.createRefreshToken(adminInfoDto.getId());

                //db에 리프레시 토큰 저장하기
                admin.updateRefreshToken(refreshToken);
                adminRepository.save(admin);

                //Json으로 token전달
                loginResponse.setAccessToken(accessToken);
                loginResponse.setRefreshToken(refreshToken);

                loginResponse.setStatus(HttpStatus.CREATED.value());
            }else {
                loginResponse.setMessage("아이디 또는 패스워드를 확인해주세요.");
                loginResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }catch (Exception e) {
            //로그인에러
            loginResponse.setMessage(e.getMessage());
            loginResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        System.out.println(loginResponse);
        return loginResponse;

    }

    public String getRefreshToken(String id) {
        Admin admin = adminRepository.findById(id);
        return admin.getRefreshToken();
    }

    public void deleRefreshToken(String id) {
        Admin admin = adminRepository.findById(id);
        admin.deleteRefreshToken();
        adminRepository.save(admin);
    }


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
     * @author 김용준
     * 신고 정보 수정하는 메소드
     * @param reportDto Request로 들어오는 DTO
     * @return 현재 수정으로 Report가 Done 되었는지 여부
     */
    public boolean updateReport(ReportDto reportDto) {
        // 더티체킹으로 업데이트
        Report report = reportRepository.findByReportId(reportDto.getReportId()); // 수정할 신고 검색
        boolean isNowDone = (report.getReportStatus() != ReportStatus.COMPLETED) && (reportDto.getStatus() == ReportStatus.COMPLETED);
        Optional<ParkingInfo> parkingInfo = parkingInfoRepository.findById(reportDto.getParkId()); // 수정할 parkId로 검색
        // 수정 신고 필드에 반영
        parkingInfo.ifPresent(report::updateParkingInfo); // 이거 뭔데 이렇게 바뀌었지..?
        report.updateReport(reportDto); // 나머지 필드도 수정
        return isNowDone;
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
     * 공통바에 사용할 데이터를 조회하는 메소드
     * @return 주차장 현황(숫자), 최근 해결된 신고데이터 5개, 주차현황리스트
     */
    public CommonAlertResponse getCommonAlertData() {
        List<RealtimeParking> realtimeParkingInfo = realtimeParkingRepository.findAll();
        List<Report> reports = reportRepository.findTop4ByOrderByDoneTimeDesc();
        return new CommonAlertResponse(realtimeParkingInfo, reports);
    }

    public void createRoot() {
        Admin admin = new Admin();
        try {
            admin.join("admin", new SHA256().encrypt("1"));
            adminRepository.save(admin);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    public DashBoardResponse getDashboard() {
        //오늘 방문 차량 조회
        int[] today_visits = new int[LocalDateTime.now().getHour()+1];
        findEnteredCarInDay(LocalDate.now(), today_visits);

        //어제 방문 차량 조회
        int[] previous_visits = new int[24];
        findEnteredCarInDay(LocalDate.now().minusDays(1), previous_visits);

        //최근 30일 오류 건수
        List<Report> reports = reportRepository.findBefore30Days(LocalDateTime.now().minusDays(30));
        int total_report = reports.size();
        ReportRateDto report_rate = new ReportRateDto();
        UnresolvedDto unresolved = new UnresolvedDto();
        List<ReportDataDto> ReportDataDto = new ArrayList<>();
        for(Report report : reports){
            if(report.getErrorCode() == ErrorCode.AUTO_REPORT) report_rate.setAuto(report_rate.getAuto()+1);
            else if(report.getErrorCode() == ErrorCode.HUMAN_ERROR) report_rate.setHuman(report_rate.getHuman()+1);
            else if(report.getErrorCode() == ErrorCode.SENSOR_ERROR) report_rate.setSensor(report_rate.getSensor()+1);
            if(report.getReportStatus() == ReportStatus.UNRESOLVED)  {
                unresolved.setCnt(unresolved.getCnt()+1);
                if(ReportDataDto.size() >= 2) continue;
                ReportDataDto.add(new ReportDataDto(report.getReportId(), report.getReportTime(), report.getErrorCode(), report.getNote()));
            }
        }
        unresolved.setReport_data(ReportDataDto);
        return new DashBoardResponse(today_visits, previous_visits, total_report, report_rate, unresolved);
    }

    private void findEnteredCarInDay(LocalDate enterDay, int[] today_visits) {
        LocalDateTime startOfDay = LocalDateTime.of(enterDay, LocalTime.MIN); // 오늘 0시 0분
        LocalDateTime endOfDay = LocalDateTime.of(enterDay, LocalTime.MAX);
        List<AllocationLog> todayVisits = allocationLogRepository.findAllByEntryTimeBetween(startOfDay, endOfDay);
        for(AllocationLog allocationLog : todayVisits){
            if(LocalDate.now().getDayOfMonth() == allocationLog.getEntryTime().getDayOfMonth() && LocalDateTime.now().getHour() < allocationLog.getEntryTime().getHour()) continue;
            today_visits[allocationLog.getEntryTime().getHour()]++;
        }
    }

    public void changePassword(String id, String newPassword){
        Admin admin = adminRepository.findById(id);
        System.out.println(admin);

        SHA256 sha256 = new SHA256();
        try {
            String hashPass = sha256.encrypt(newPassword);
            admin.changePassword(hashPass);
            adminRepository.save(admin);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
