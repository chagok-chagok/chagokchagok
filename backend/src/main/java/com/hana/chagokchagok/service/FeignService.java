package com.hana.chagokchagok.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
// 외부로 api 요청하는 기능을 수행하는 Service
public class FeignService {
    private final DjangoClient djangoClient;

    /**
     * AI서버로 차단바 해제 Request 보내는 메소드
     * @author 김용준
     * @param parkId
     */
    public void sendOpenBarRequest(int parkId) {
        djangoClient.sendOpenBarRequestToDjango(parkId);
    }
}