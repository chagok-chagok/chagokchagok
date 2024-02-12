package com.hana.chagokchagok.service;

import com.hana.chagokchagok.util.DjangoClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "DjangoClient",
        url = "http://i10b301.p.ssafy.io/apiv1", // django 서버의 주소 입력해야 함
        configuration = DjangoClientConfig.class)
// 이미지 AI 서버로 요청 보내는 클라이언트
public interface DjangoClient {
    @GetMapping("/open-area/{parkId}")
    void sendOpenBarRequestToDjango(@PathVariable("parkId") Integer parkId); // parkId에 해당하는 차단바를 해제 요청
}