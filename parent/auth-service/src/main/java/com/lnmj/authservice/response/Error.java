package com.lnmj.authservice.response;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 10:29
 * @Description:
 */
//错误信息
public class Error {
    private Integer ErrorCode;
    private Object ErrorMsg;


    public Error(Integer errorCode, Object errorMsg) {
        ErrorCode = errorCode;
        ErrorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(Integer errorCode) {
        ErrorCode = errorCode;
    }

    public Object getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        ErrorMsg = errorMsg;
    }
}
