package com.hana.chagokchagok.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter @ToString @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private int status;
    private String message;
}
