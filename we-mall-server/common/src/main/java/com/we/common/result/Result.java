package com.we.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一响应结果封装类
 * 用于Controller层统一返回数据格式给前端，包含状态码、消息和数据三部分
 *
 * @author ryan
 * @since 2025-07-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 成功响应，无数据
     *
     * @return Result<Void>
     */
    public static Result<Void> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 成功响应，带数据
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return Result<T>
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功响应，自定义消息和数据
     *
     * @param message 响应消息
     * @param data    响应数据
     * @param <T>     数据类型
     * @return Result<T>
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    /**
     * 失败响应，只有消息
     *
     * @param message 响应消息
     * @return Result<Void>
     */
    public static Result<Void> error(String message) {
        return new Result<>(500, message, null);
    }

    /**
     * 失败响应，自定义状态码和消息
     *
     * @param code    响应码
     * @param message 响应消息
     * @return Result<Void>
     */
    public static Result<Void> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 失败响应，带数据
     *
     * @param message 响应消息
     * @param data    响应数据
     * @param <T>     数据类型
     * @return Result<T>
     */
    public static <T> Result<T> error(String message, T data) {
        return new Result<>(500, message, data);
    }

    /**
     * 参数错误响应
     *
     * @param message 错误信息
     * @return Result<Void>
     */
    public static Result<Void> badRequest(String message) {
        return new Result<>(400, message, null);
    }
}