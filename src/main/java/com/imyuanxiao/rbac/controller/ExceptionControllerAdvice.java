package com.imyuanxiao.rbac.controller;

import com.imyuanxiao.rbac.annotation.ExceptionCode;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 9:22
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResultVO<String> APIExceptionHandler(ApiException e) {
        // 返回自定义异常提示信息
        return new ResultVO<>(e.getResultCode(), e.getMsg());
    }


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
//            return new ResultVO<>(annotation, defaultMessage);
        }

        return new ResultVO<>(ResultCode.VALIDATE_FAILED, defaultMessage);
    }

}
