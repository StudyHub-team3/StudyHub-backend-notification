package com.studyhub.notification.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseDto<T> {
    private boolean success;
    private T data;
    private String message;

    // 성공 응답 생성
    public static <T> ApiResponseDto<T> success(T data) {
        return new ApiResponseDto<>(true, data, null);
    }

    // 실패 응답 생성
    public static <T> ApiResponseDto<T> fail(String message) {
        return new ApiResponseDto<>(false, null, message);
    }
}
