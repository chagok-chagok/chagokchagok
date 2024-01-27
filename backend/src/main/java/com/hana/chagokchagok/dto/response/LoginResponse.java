package com.hana.chagokchagok.dto.response;

import lombok.*;

@Getter @ToString @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String message;
}
