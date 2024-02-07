package com.hana.chagokchagok.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hana.chagokchagok.contoller.SseController;
import com.hana.chagokchagok.dto.request.CarNumRequest;
import com.hana.chagokchagok.entity.Report;
import com.hana.chagokchagok.enums.SseStatus;
import com.hana.chagokchagok.exception.SseEmitterIsNullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class SseService {

    public SseEmitter subscribe(String keyValue) {
        // 현재 클라이언트를 위한 SseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("open").data(keyValue+"의 SSE가 연결되었습니다."));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SseController.sseEmitters.put(keyValue, sseEmitter);

        sseEmitter.onCompletion(() -> SseController.sseEmitters.remove(keyValue));
        sseEmitter.onTimeout(() -> SseController.sseEmitters.remove(keyValue));
        sseEmitter.onError((e) -> SseController.sseEmitters.remove(keyValue));

        return sseEmitter;
    }

    /**
     * 만차의 경우 차량 출차시 대기자를 위한 키오스크 요청
     * @param keyValue
     */
    public void congestionClear(String keyValue) {
        SseEmitter sseEmitter = SseController.sseEmitters.get(keyValue);
        try {
            System.out.println("=> "+sseEmitter);
            if(sseEmitter == null) throw new SseEmitterIsNullException(keyValue+" 연결이 존재하지 않음");
            else{
                sseEmitter.send(SseEmitter.event()
                        .name(String.valueOf(SseStatus.CONGESTION_CLEAR))
                        .data("CONGESTION_CLEAR"));
            }

        } catch (SseEmitterIsNullException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AI서버로부터 추출된 차번호텍스트를 SSE를 통해 키오스크로 전송
     * @param carNum
     */
    public void validateCarnum(String carNum, String keyValue) {
        SseEmitter sseEmitter = SseController.sseEmitters.get(keyValue);
        try {
            if(sseEmitter == null) throw new SseEmitterIsNullException(keyValue+" 연결이 존재하지 않음");
            else{
                System.out.println("carNum : " + carNum);
                System.out.println("keyValue : " + keyValue);
                String regax = "^[0-9]{2}[가-힣][0-9]{4}$";
                Pattern pattern = Pattern.compile(regax);
                Matcher matcher = pattern.matcher(carNum);
                if(matcher.matches()) {
                    sseEmitter.send(SseEmitter.event()
                            .name(String.valueOf(SseStatus.VALID_CAR_NUM))
                            .data(carNum));
                } else{ //적합하지 않으면 키오스크 화면에 재인식 코드 띄우기
                    sseEmitter.send(SseEmitter.event()
                            .name(String.valueOf(SseStatus.INVALID_CAR_NUM))
                            .data("INVALID_CAR_NUM"));
                }

            }

        } catch (SseEmitterIsNullException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 자동신고시스템 가동시 플로팅알림 데이터 전송
     * @param report
     * @param keyValue
     */
    public void sendSensorReport(Report report, String keyValue) {
        SseEmitter sseEmitter = SseController.sseEmitters.get(keyValue);
        try {
            if(sseEmitter == null) throw new SseEmitterIsNullException(keyValue+" 연결이 존재하지 않음");
            else{
                Map<String, Object> data = new HashMap<>();
                data.put("time", report.getReportTime());
                data.put("park", report.getParkingInfo().getFullName());
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                String jsonData = objectMapper.writeValueAsString(data);

                sseEmitter.send(SseEmitter.event()
                        .name(String.valueOf(SseStatus.SENSOR_REPORT))
                        .data(jsonData));
            }

        } catch (SseEmitterIsNullException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 공통바 업데이터 (트리거 : 입출차, 차단바해제, 자리바꾸기, 자동신고 done)
     * @param keyValue
     */
    public void sendRealtimeCommon(String keyValue) {
        //데이터를 주지 말고... 여기서 특정 SSE요청을 하면 프론트에서 공통바 달라는 요청을 다시 보내도록 하는 방식을 차용하는게 재사용성 측면에서 좋을듯
        SseEmitter sseEmitter = SseController.sseEmitters.get(keyValue);
        try {
            if(sseEmitter == null) throw new SseEmitterIsNullException(keyValue+" 연결이 존재하지 않음");
            else{
                sseEmitter.send(SseEmitter.event()
                        .name(String.valueOf(SseStatus.REALTIME_COMMON))
                        .data("REALTIME_COMMON"));
            }

        } catch (SseEmitterIsNullException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
