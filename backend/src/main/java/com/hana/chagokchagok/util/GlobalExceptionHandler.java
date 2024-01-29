package com.hana.chagokchagok.util;

import com.hana.chagokchagok.exception.CustomException;
import com.hana.chagokchagok.exception.ErrorCode;
import com.hana.chagokchagok.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * hibernate 관련 에러 처리
     * @author 김용준
     * @return Json Body에 409 예외
     */
    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleDataException() {
        log.error("handleDataException throw Exception : {}", ErrorCode.DUPLICATE_RESOURCE);
        return ErrorResponse.toResponseEntity(ErrorCode.DUPLICATE_RESOURCE);
    }

    /**
     *
     * @author 김용준
     * @param e 사용자 정의 예외
     * @return Json Body에 사용자 정의 예외 정보
     */
    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
