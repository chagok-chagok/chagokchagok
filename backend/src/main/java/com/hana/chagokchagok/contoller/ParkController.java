package com.hana.chagokchagok.contoller;

import com.hana.chagokchagok.dto.request.AllocateCarRequest;
import com.hana.chagokchagok.dto.request.CarNumRequest;
import com.hana.chagokchagok.dto.request.PullOutRequest;
import com.hana.chagokchagok.dto.request.ValidateAreaRequest;
import com.hana.chagokchagok.dto.response.AllocateCarResponse;
import com.hana.chagokchagok.dto.response.CarNumValidationDto;
import com.hana.chagokchagok.dto.response.ValidateAreaResponse;
import com.hana.chagokchagok.service.AdminService;
import com.hana.chagokchagok.service.ParkService;
import com.hana.chagokchagok.service.SseService;
import com.hana.chagokchagok.util.ValidateCarNum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/park")
public class ParkController {
    private final ParkService parkService;
    private final AdminService adminService;
    private final SseService sseService;
    @Value("${admin.key}")
    private String ADMIN_KEY;
    @Value("${kiosk.key}")
    private String KIOSK_KEY;
    /**
     * 자리 배정 요청
     * @param allocateCarRequest 차량 번호, 장애 여부를 가진 요청
     * @return 자리가 배정됐다면 차량 번호, 입차 시간, 자리 번호를 가진 allocateCarResponse, 만차라면 null
     * @author 김용준
     */
    @PostMapping("/allocation")
    public ResponseEntity<AllocateCarResponse> allocateCar(@RequestBody AllocateCarRequest allocateCarRequest) {
        System.out.println(allocateCarRequest);
        ResponseEntity<AllocateCarResponse> response = parkService.getAllocatedInfo(allocateCarRequest);
        sseService.sendRealtimeCommon(ADMIN_KEY);
        return response;
    }

    /**
     * 구역 판별 요청
     * @author 김용준
     * @param carNo 구역에 진입한 차량 번호
     * @param area 촬영된 구역 코드
     * @return 배정된 구역이라면 주차장 번호, 그렇지 않다면 null
     */
    @GetMapping("/validation")
    public ResponseEntity<ValidateAreaResponse> validateArea(
            @RequestParam(name = "car_no") String carNo,
            @RequestParam(name = "area") String area) {
        ValidateAreaRequest validateAreaRequest = new ValidateAreaRequest(carNo, area);
        return parkService.validateArea(validateAreaRequest);
    }

    /**
     * 출차처리
     * @param pullOutRequest 차번호
     * @return 해당 차량이 배정받았던 자리
     */
    @PostMapping("/out")
    public ResponseEntity<String> pullOut(@RequestBody PullOutRequest pullOutRequest){
        ResponseEntity<String> response = parkService.pullOut(pullOutRequest.getCarNo());
        //출차처리 되었으니 관리자페이지에 공통바 SSE알림
        sseService.sendRealtimeCommon(ADMIN_KEY);
        return response;
    }

    /**
     * 자동신고 시스템
     * @param location
     * @return resoponseEntity
     */
    @GetMapping("/auto")
    public ResponseEntity<Void> autoSystem(@RequestParam(name = "location") String location){
        return parkService.autoSystem(location);
    }

    /**
     * AI서버가 보낸 추출 텍스트에 대해 유효성을 검사한 뒤 SSE로 프론트에 화면코드를 넘겨줌
     * @return Void
     */
    @PostMapping("/validation/carnum")
    public ResponseEntity<CarNumValidationDto> sendCarNum(@RequestBody CarNumRequest carNumRequest){
        System.out.println("carnum 들어옴"+carNumRequest);
        boolean validation = ValidateCarNum.validateCarNum(carNumRequest.getCarNum());
        sseService.validateCarnum(validation, carNumRequest.getCarNum(), KIOSK_KEY);
        return new ResponseEntity<>(new CarNumValidationDto(validation), HttpStatus.OK);
    }
}
