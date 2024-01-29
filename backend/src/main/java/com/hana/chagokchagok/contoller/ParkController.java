package com.hana.chagokchagok.contoller;

import com.hana.chagokchagok.dto.request.AllocateCarRequest;
import com.hana.chagokchagok.dto.request.PullOutRequest;
import com.hana.chagokchagok.dto.request.ValidateAreaRequest;
import com.hana.chagokchagok.dto.response.AllocateCarResponse;
import com.hana.chagokchagok.dto.response.ValidateAreaResponse;
import com.hana.chagokchagok.service.ParkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/park")
public class ParkController {
    private final ParkService parkService;

    /**
     * 자리 배정 요청
     * @author 김용준
     * @param allocateCarRequest 차량 번호, 장애 여부를 가진 요청
     * @return 자리가 배정됐다면 차량 번호, 입차 시간, 자리 번호를 가진 allocateCarResponse, 만차라면 null
     */
    @PostMapping("/allocation")
    public AllocateCarResponse allocateCar(@RequestBody AllocateCarRequest allocateCarRequest) {
        System.out.println(allocateCarRequest);
        AllocateCarResponse allocateCarResponse = parkService.getAllocatedInfo(allocateCarRequest);

        // 만차일 때
        if (allocateCarResponse == null) {
            // 뭘 리턴해야 하지?
            return null;
        }
        // 자리가 배정됐을 때
        else {
            return allocateCarResponse;
        }
    }

    /**
     * 구역 판별 요청
     * @author 김용준
     * @param carNo 구역에 진입한 차량 번호
     * @param area 촬영된 구역 코드
     * @return 배정된 구역이라면 주차장 번호, 그렇지 않다면 null
     */
    @GetMapping("/validation")
    public ValidateAreaResponse validateArea(
            @RequestParam(name = "car_no") String carNo,
            @RequestParam(name = "area") String area) {
        ValidateAreaRequest validateAreaRequest = new ValidateAreaRequest(carNo, area);
        ValidateAreaResponse validateAreaResponse = parkService.validateArea(validateAreaRequest);
        // 다른 구역에 진입했거나, 오인식된 차량일 때
        if (validateAreaResponse == null) {
            return null;
        }
        // 배정된 구역에 진입했을 때
        else {
            return validateAreaResponse;
        }
    }

    /**
     * 출차처리
     * @param pullOutRequest 차번호
     * @return 해당 차량이 배정받았던 자리
     */
    @PostMapping("/out")
    public ResponseEntity<String> pullOut(@RequestBody PullOutRequest pullOutRequest){
        return parkService.pullOut(pullOutRequest.getCarNo());
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
}
