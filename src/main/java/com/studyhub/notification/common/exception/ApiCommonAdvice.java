package com.studyhub.notification.common.exception;

import com.studyhub.notification.common.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiCommonAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponseDto.fail("잘못된 요청입니다: " + e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleGeneralException(Exception e) {
        return ResponseEntity.internalServerError()
                .body(ApiResponseDto.fail("서버 오류: " + e.getMessage()));
    }
}
