package com.hana.chagokchagok.service;

import com.hana.chagokchagok.dto.AllocationDto;
import com.hana.chagokchagok.dto.ValidationParkingInfoDto;
import com.hana.chagokchagok.dto.request.AllocateCarRequest;
import com.hana.chagokchagok.dto.request.OpenBarRequest;
import com.hana.chagokchagok.dto.request.ValidateAreaRequest;
import com.hana.chagokchagok.dto.response.AllocateCarResponse;
import com.hana.chagokchagok.dto.response.ValidateAreaResponse;
import com.hana.chagokchagok.entity.AllocationLog;
import com.hana.chagokchagok.entity.ParkingInfo;
import com.hana.chagokchagok.entity.RealtimeParking;
import com.hana.chagokchagok.entity.Report;
import com.hana.chagokchagok.exception.CustomException;
import com.hana.chagokchagok.exception.ErrorCode;
import com.hana.chagokchagok.repository.AllocationLogRepository;
import com.hana.chagokchagok.repository.ParkingInfoRepository;
import com.hana.chagokchagok.repository.RealtimeParkingRepository;
import com.hana.chagokchagok.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.hana.chagokchagok.util.SeparateLocation.separateLocationInput;


@Service
@RequiredArgsConstructor
@Transactional
public class ParkService {
    private final RealtimeParkingRepository realTimeParkingRepository;
    private final AllocationLogRepository allocationLogRepository;
    private final ParkingInfoRepository parkingInfoRepository;
    private final ReportRepository reportRepository;
    private final FeignService feignService;
    /**
     * 자리 배정 로직 수행 메소드
     * @author 김용준
     * @param allocateCarRequest 장애 여부, 차량 번호
     * @return 자리가 있다면 allocateCarResponse, 만차라면 NO_PARKING_SPACE 에러 반환
     */
    public ResponseEntity<AllocateCarResponse> getAllocatedInfo(AllocateCarRequest allocateCarRequest) {
        RealtimeParking allocatedLocation;
        // 장애 배려 차량 여부 확인
        if (allocateCarRequest.getIsDisabled()) {
            allocatedLocation = realTimeParkingRepository.findFirstWhoIsDisabled();
        } else {
            allocatedLocation = realTimeParkingRepository.findFirstWhoIsNotDisabled();
        }

        // 자리가 없다면 만차 응답 반환
        // 404 CustomException
        if (allocatedLocation == null) {
            throw new CustomException(ErrorCode.NO_PARKING_SPACE);
        }
        // 자리 있다면 배정 로직 수행
        else {
            // 입출차기록 테이블에 저장
            AllocationDto allocationDto = new AllocationDto(allocatedLocation, allocateCarRequest.getCarNo());
            AllocationLog allocationLog = AllocationLog.createAllocationLog(allocationDto);
            allocationLogRepository.save(allocationLog);

            // 주차현황 테이블 업데이트
            allocatedLocation.changeAllocationLog(allocationLog);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new AllocateCarResponse(allocatedLocation, allocationLog));
        }
    }

    /**
     * 구역 판별 메소드
     * @author 김용준
     * @param validateAreaRequest 차량 번호, 구역 코드
     * @return 배정된 구역이라면 배정 자리 번호, 그 외에는 null
     */
    public ValidateAreaResponse validateArea(ValidateAreaRequest validateAreaRequest) {
        // 차량 번호로 배정된 구역 검색
        ValidationParkingInfoDto area = parkingInfoRepository.findValidationParkingInfo(validateAreaRequest.getCarNo());

        // 차량 번호로 검색했을 때 배정된 구역이 나오지 않음
        if (area == null) {
            return null;
        }
        // 배정된 구역이 검색됨
        else {
            // 배정된 구역에 진입했다면
            if (area.getAreaCode().equals(validateAreaRequest.getArea())) {
                return new ValidateAreaResponse(area);
            }
            // 다른 구역에 진입했다면
            else {
                return null;
            }
        }
    }

    /**
     * 출차처리 메소드
     * @param carNo
     */
    public ResponseEntity<String> pullOut(String carNo) {
        //해당 차량 출차처리
        AllocationLog allocationLog = allocationLogRepository.findByCarNo(carNo);
        RealtimeParking realtimeParking = realTimeParkingRepository.findByAllocationLog(allocationLog);

        allocationLog.pullOut();
        realtimeParking.deleteAllocationLog();

        // 만차판별
        if(!realTimeParkingRepository.existsByAllocationLogIsNull()){
            //만차였던 경우 빈자리가 생겼으므로 키오스크 서버로 SSE알림
        }

        //공통 -> 라즈베리로 위치 반환
        return new ResponseEntity<>(realtimeParking.getParkingInfo().getFullName(), HttpStatus.OK);
    }

    /**
     * 자동신고시스템 => Report작성
     * @param input (자리값)
     * @return
     */
    public ResponseEntity<Void> autoSystem(String input) {
        String[] location = separateLocationInput(input);
        ParkingInfo parkingInfo = parkingInfoRepository.findByParkNoAndAreaCode(Integer.valueOf(location[1]),location[0]);
        reportRepository.save(Report.createReport(parkingInfo));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 특정 위치 차단바 여는 메소드
     * @author 김용준
     * @param openBarRequest
     * @return
     */
    public ResponseEntity<Void> openBar(OpenBarRequest openBarRequest) {
        // 주차현황 가져오기
        RealtimeParking searchedRealTimeParking = realTimeParkingRepository
                .findByParkingInfo_ParkNoAndParkingInfo_AreaCode(
                        openBarRequest.getParkNo(),
                        openBarRequest.getAreaCode());

        AllocationLog allocationLog = searchedRealTimeParking.getAllocationLog();
        // 입출차기록에 저장된 차량 번호를 입력받은 차량 번호로 변경
        allocationLog.changeCarNo(openBarRequest.getCarNo());
        // 차단바 제어 서버로 전송(자리 번호)
        feignService.sendOpenBarRequest(searchedRealTimeParking.getParkId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
