package com.hana.chagokchagok.contoller;

import com.hana.chagokchagok.dto.request.ExchangeRequest;
import com.hana.chagokchagok.dto.request.LoginRequest;
import com.hana.chagokchagok.dto.request.OpenBarRequest;
import com.hana.chagokchagok.dto.request.ReportRequest;
import com.hana.chagokchagok.dto.response.CommonAlertResponse;
import com.hana.chagokchagok.dto.response.LoginResponse;
import com.hana.chagokchagok.dto.response.LogoutResponse;
import com.hana.chagokchagok.dto.response.RefreshTokenResponse;
import com.hana.chagokchagok.dto.response.ReportResponse;
import com.hana.chagokchagok.service.AdminService;
import com.hana.chagokchagok.service.ParkService;
import com.hana.chagokchagok.service.SseService;
import com.hana.chagokchagok.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final JWTUtil jwtUtil;
    private final ParkService parkService;
    private final SseService sseService;
    @Value("${admin.key}")
    private String ADMIN_KEY;


    @GetMapping("/test")
    public void test(){
        adminService.createRoot();
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest adminInfoDto){
        LoginResponse loginResponse  = adminService.login(adminInfoDto);
        return loginResponse;
    }

    @GetMapping("/refresh")
    public RefreshTokenResponse refreshToken(HttpServletRequest request){
        //클라이언트가 가지고 있는 refreshToken 가져오기
        String token = request.getHeader("Authorization");

        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
        //정상적인 토큰일때
        if (jwtUtil.checkToken(token)) {
            if (token.equals(adminService.getRefreshToken(jwtUtil.getUserId(token)))) {
                String accessToken = jwtUtil.createAccessToken(jwtUtil.getUserId(token));
                refreshTokenResponse.setAccessToken(accessToken);
            }
        }else {
            refreshTokenResponse.setMessage("리프레쉬토큰도 사용불가!!!!!!!");
        }
        return refreshTokenResponse;
    }

    @GetMapping("/logout")
    public LogoutResponse removeToken(HttpServletRequest request) {
        LogoutResponse logoutResponse = new LogoutResponse();
        try {
            adminService.deleRefreshToken(jwtUtil.getUserId(request.getHeader("Authorization")));
            logoutResponse.setMessage("로그아웃되었습니다.");
        } catch (Exception e) {
            logoutResponse.setMessage(e.getMessage());
        }
        return logoutResponse;
    }
    @GetMapping("/report")
    public ReportResponse getReportList(@RequestParam(name = "page", defaultValue = "0") int page) {
        ReportRequest reportRequest = new ReportRequest(page);
        return adminService.getReportList(reportRequest);
    }
    @PutMapping("/exchange")
    public ResponseEntity<String> exchangeAllocation(@RequestBody ExchangeRequest exchangeRequest) {
        ResponseEntity<String> resp = adminService.exchangeAllocation(exchangeRequest);
        sseService.sendRealtimeCommon(ADMIN_KEY);
        return resp;
    }
    @GetMapping("/common")
    public CommonAlertResponse getCommonAlertData() {
        return adminService.getCommonAlertData();
    }

    @PutMapping("/bar")
    public void openBar(@RequestBody OpenBarRequest openBarRequest) {
        parkService.openBar(openBarRequest); // 차단바 해제 요청
    }
}
