package com.imyuanxiao.rbac.controller;

import com.imyuanxiao.rbac.annotation.ExceptionCode;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;

/**
 * @ClassName ExceptionControllerAdvice
 * @Description Global exception handler
 * @Author imyuanxiao
 * @Date 2023/5/3 9:22
 * @Version 1.0
 **/
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * 处理自定义的ApiException异常
     * @author imyuanxiao
     * @date 20:33 2023/5/6
     * @param e ApiException
     * @return ResultVO with error message
     **/
    @ExceptionHandler(ApiException.class)
    public ResultVO<String> apiExceptionHandler(ApiException e) {
        // 返回自定义异常提示信息
        return new ResultVO<>(e.getResultCode(), e.getMsg());
    }

    /**
     *
     * @author imyuanxiao
     * @date 20:33 2023/5/6
     * @param e MethodArgumentNotValidException
     * @return ResultVO with error message
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e)
            throws NoSuchFieldException{

        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        Class<?> parameterType = e.getParameter().getParameterType();

        String fieldName = e.getBindingResult().getFieldError().getField();
        Field field = parameterType.getDeclaredField(fieldName);
        ExceptionCode annotation = field.getAnnotation(ExceptionCode.class);

        if(annotation != null){
            return new ResultVO<>(annotation.value(), annotation.message(), defaultMessage);
        }

        return new ResultVO<>(ResultCode.VALIDATE_FAILED, defaultMessage);
    }

    /**
     * 封装处理运行时发生的其他异常
     * @author imyuanxiao
     * @date 20:32 2023/5/6
     * @param e RuntimeException
     * @return ResultVO with error message
     **/
    @ExceptionHandler(RuntimeException.class)
    public ResultVO<String> runtimeExceptionHandler(RuntimeException e) {
        // 返回自定义异常提示信息
        return new ResultVO<>(ResultCode.ERROR, "系统异常，请稍后重试");
    }

//    /**
//     * 方法异常
//     * @author imyuanxiao
//     * @date 18:22 2023/5/9
//     * @param ex method exception
//     * @return ResultVO
//     **/
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//    public ResultVO<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
//        // 处理逻辑
//        return new ResultVO<>(ResultCode.METHOD_NOT_ALLOWED, "Request method not allowed!");
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResultVO<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
//        return new ResultVO<>(ResultCode.BAD_REQUEST,"Missing or invalid request body parameter format.");
//    }

}
