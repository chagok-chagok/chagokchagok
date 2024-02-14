package com.hana.chagokchagok.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
// 사용자 정의 에러
public enum ErrorType {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    INVALID_AREA(HttpStatus.BAD_REQUEST, "배정된 구역이 아닙니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    NO_PARKING_SPACE(HttpStatus.NOT_FOUND, "배정 가능한 자리가 없습니다."),
    NO_CAR_NUMBER(HttpStatus.NOT_FOUND, "일치하는 차량 정보가 없습니다"),
    NO_TOOLTIP_INFORMATION(HttpStatus.NOT_FOUND, "일치하는 툴팁 정보가 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}