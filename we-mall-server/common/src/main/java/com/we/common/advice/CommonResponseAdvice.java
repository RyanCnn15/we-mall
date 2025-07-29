package com.we.common.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.common.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一响应返回处理类
 * 用于统一处理Controller层的返回结果，将其封装为统一的Result格式
 *
 * @author ryan
 * @since 2025-07-29
 */
@RestControllerAdvice
public class CommonResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 判断是否需要处理该响应
     *
     * @param returnType    方法返回类型
     * @param converterType 消息转换器类型
     * @return true-需要处理, false-不需要处理
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 检查类或方法上是否有@IgnoreResponseAdvice注解
        if (
                returnType.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)
                        || returnType.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)
        ) {
            return false;
        }

        // 如果返回类型已经是Result，则不需要处理
        if (returnType.getParameterType().equals(Result.class)) {
            return false;
        }

        // 获取当前处理的类
        String className = returnType.getDeclaringClass().getName();

        // 如果是Swagger相关类，不进行处理
        if (className.contains("springfox.documentation") || className.contains("springdoc")) {
            return false;
        }

        // 其他情况都需要处理
        return true;
    }

    /**
     * 处理响应结果
     *
     * @param body                  原始响应体
     * @param returnType            方法返回类型
     * @param selectedContentType   选择的内容类型
     * @param selectedConverterType 选择的转换器类型
     * @param request               请求对象
     * @param response              响应对象
     * @return 处理后的响应体
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        // 如果body为null，返回成功但无数据
        if (body == null) {
            return Result.success();
        }

        // 如果body已经是Result类型，直接返回
        if (body instanceof Result) {
            return body;
        }

        // 如果是String类型，需要特殊处理，否则会因为转换器问题报错
        if (body instanceof String) {
            try {
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return objectMapper.writeValueAsString(Result.success(body));
            } catch (JsonProcessingException e) {
                return Result.success(body);
            }
        }

        // 其他情况统一包装成Result
        return Result.success(body);
    }
}