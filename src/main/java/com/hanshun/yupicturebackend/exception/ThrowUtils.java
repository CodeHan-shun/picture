package com.hanshun.yupicturebackend.exception;

import com.hanshun.yupicturebackend.exception.BusinessException;
import com.hanshun.yupicturebackend.exception.ErrorCode;

/**
 * @author hxs20
 * @version 1.0
 * @project yu-picture-backend
 * @date 2025/11/25 22:23:50
 */
public class ThrowUtils {

    /**
     * 条件成立，则抛异常
     *
     * @param condition        条件
     * @param runtimeException 异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立，则抛异常
     *
     * @param condition 条件
     * @param errorCode 错误码
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立，则抛异常
     *
     * @param condition 条件
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }


}
