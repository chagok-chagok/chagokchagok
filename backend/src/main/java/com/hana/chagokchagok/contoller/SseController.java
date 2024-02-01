package com.hana.chagokchagok.contoller;

import com.hana.chagokchagok.dto.request.CarNumRequest;
import com.hana.chagokchagok.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class SseController {
    @Value("${kiosk.key}")
    private String KIOSK_KEY;
    @Value("${admin.key}")
    private String ADMIN_KEY;

    private final SseService sseService;

    @GetMapping("/s-admin")
    public String tmp1(){
        return "admin.html";
    }
    @GetMapping("/s-kiosk")
    public String tmp2(){
        return "kiosk.html";
    }

    // sseEmitter객체를 매핑해서 static으로 전역공유
    public static Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @GetMapping(value = "/sse/admin", produces = "text/event-stream")
    public SseEmitter subscribeAdmin(){
        return sseService.subscribe(ADMIN_KEY);
    }

    @GetMapping(value = "/sse/kiosk", produces = "text/event-stream")
    public SseEmitter subscribeKiosk(){
        return sseService.subscribe(KIOSK_KEY);
    }

    //AI서버로부터 차번호 추출 텍스트를 입력받아 키오스크로 전송


}
