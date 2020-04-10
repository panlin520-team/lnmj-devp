package com.lnmj.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lnmj.common.Enum.AckCodeTypeEnum;
import io.swagger.annotations.ApiModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 10:25
 * @Description:
 */
//请求返回对象
public class ResponseResult<T> {
    //返回状态
    private ResponseStatusType responseStatusType;
    //返回结果集
    private T result;

    public ResponseResult() {

    }
    public ResponseResult(ResponseStatusType responseStatusType, T Result) {
        this.responseStatusType = responseStatusType;
        this.result = Result;
    }

    public ResponseResult(ResponseStatusType responseStatusType) {
        this.responseStatusType = responseStatusType;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess() {
        return this.responseStatusType.getAck() == 1;
    }

    public static <T> ResponseResult<T> success() {
        ResponseStatusType responseStatusType = new ResponseStatusType();
        responseStatusType.setAck(AckCodeTypeEnum.SUCCESS.getCode());
        responseStatusType.setMessage(AckCodeTypeEnum.SUCCESS.getDesc());
        Error error = new Error(null, null);
        responseStatusType.setError(error);
        responseStatusType.setVersion("1.0");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        responseStatusType.setTimestamp(dateNowStr);
        return new ResponseResult<T>(responseStatusType);
    }

    public static <T> ResponseResult<T> success(T result) {
        ResponseStatusType responseStatusType = new ResponseStatusType();
        responseStatusType.setAck(AckCodeTypeEnum.SUCCESS.getCode());
        responseStatusType.setMessage(AckCodeTypeEnum.SUCCESS.getDesc());
        Error error = new Error(null, null);
        responseStatusType.setError(error);
        responseStatusType.setVersion("1.0");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        responseStatusType.setTimestamp(dateNowStr);
        return new ResponseResult<T>(responseStatusType, result);
    }

    public static <T> ResponseResult<T> error(Error error) {
        ResponseStatusType responseStatusType = new ResponseStatusType();
        responseStatusType.setAck(AckCodeTypeEnum.FAILURE.getCode());
        responseStatusType.setMessage(AckCodeTypeEnum.FAILURE.getDesc());
        responseStatusType.setError(error);
        responseStatusType.setVersion("1.0");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        responseStatusType.setTimestamp(dateNowStr);
        return new ResponseResult<T>(responseStatusType);
    }

    public static <T> ResponseResult<T> warning(Error error) {
        ResponseStatusType responseStatusType = new ResponseStatusType();
        responseStatusType.setAck(AckCodeTypeEnum.WARNING.getCode());
        responseStatusType.setMessage(AckCodeTypeEnum.WARNING.getDesc());
        responseStatusType.setError(error);
        responseStatusType.setVersion("1.0");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        responseStatusType.setTimestamp(dateNowStr);
        return new ResponseResult<T>(responseStatusType);
    }

    public static <T> ResponseResult<T> partialFailuer(Error error) {
        ResponseStatusType responseStatusType = new ResponseStatusType();
        responseStatusType.setAck(AckCodeTypeEnum.PARTIAL_FAILURE.getCode());
        responseStatusType.setMessage(AckCodeTypeEnum.PARTIAL_FAILURE.getDesc());
        responseStatusType.setError(error);
        responseStatusType.setVersion("1.0");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        responseStatusType.setTimestamp(dateNowStr);
        return new ResponseResult<T>(responseStatusType);
    }

    public ResponseStatusType getResponseStatusType() {
        return responseStatusType;
    }

    public void setResponseStatusType(ResponseStatusType responseStatusType) {
        this.responseStatusType = responseStatusType;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "responseStatusType=" + responseStatusType +
                ", result=" + result +
                '}';
    }
}
