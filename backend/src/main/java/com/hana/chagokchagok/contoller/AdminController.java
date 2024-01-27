package com.hana.chagokchagok.contoller;

import com.hana.chagokchagok.dto.request.LoginRequest;
import com.hana.chagokchagok.dto.request.LogoutRequest;
import com.hana.chagokchagok.dto.response.LoginResponse;
import com.hana.chagokchagok.dto.response.LogoutResponse;
import com.hana.chagokchagok.dto.response.RefreshTokenResponse;
import com.hana.chagokchagok.service.AdminService;
import com.hana.chagokchagok.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final JWTUtil jwtUtil;

    @GetMapping("/test")
    public void test(){
        System.out.println("hello ^^");
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest adminInfoDto){
        System.out.println("테스트중입니다");
        LoginResponse loginResponse  = adminService.login(adminInfoDto);
        return loginResponse;
    }

    @GetMapping("/refresh")
    public RefreshTokenResponse refreshToken(HttpServletRequest request) throws Exception {
        //클라이언트가 가지고 있는 refreshToken 가져오기
        String token = request.getHeader("Authorization");
        System.out.println("jwtUtil.getUserId(token) = " + jwtUtil.getUserId(token));

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

    @PostMapping("/logout")
    public LogoutResponse removeToken(@RequestBody LogoutRequest logoutRequest) {
        LogoutResponse logoutResponse = new LogoutResponse();
        try {
            adminService.deleRefreshToken(logoutRequest.getId());
            logoutResponse.setMessage("로그아웃되었습니다.");
        } catch (Exception e) {
            logoutResponse.setMessage(e.getMessage());
        }
        return logoutResponse;
    }
}
