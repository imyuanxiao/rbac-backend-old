package com.imyuanxiao.rbac.exception;

import com.imyuanxiao.rbac.enums.ResultCode;
import lombok.Getter;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 9:25
 */
@Getter
public class APIException extends RuntimeException{

    private ResultCode resultCode;
    private String msg;

    public APIException(ResultCode resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public APIException(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.msg = resultCode.getMsg();
    }


//    private int code;
//    private String msg;

//    public APIException() {
//        this(1001, "接口错误");
//    }
//
//    public APIException(String msg) {
//        this(1001, msg);
//    }

//    public APIException(int code, String msg) {
//        super(msg);
//        this.code = code;
//        this.msg = msg;
//    }
//
//    public APIException(ResultCode resultCode, String msg) {
//        super(msg);
//        this.code = resultCode.getCode();
//    }

//    public APIException(ResultCode resultCode) {
//        this.code = resultCode.getCode();
//        this.msg = resultCode.getMsg();
//    }

}
