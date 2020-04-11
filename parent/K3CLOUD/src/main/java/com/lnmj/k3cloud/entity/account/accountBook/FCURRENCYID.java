package com.lnmj.k3cloud.entity.account.accountBook;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/1/13
 */
@Data
public class FCURRENCYID {
    @JSONField(name ="FNumber")
    private String FNumber;

    public FCURRENCYID() {
    }

    public FCURRENCYID(String FNumber) {
        this.FNumber = FNumber;
    }

    public String getFNumber() {
        return FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }
}
