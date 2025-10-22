package com.example.owner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private boolean result;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, data);
    }

    public static <T> BaseResponse<T> fail(T data) {
        return new BaseResponse<>(false, data);
    }
}
