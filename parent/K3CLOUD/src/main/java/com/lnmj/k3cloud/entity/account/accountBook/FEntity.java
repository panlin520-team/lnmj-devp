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
public class FEntity {
    @JSONField(name ="FADJUSTPERIODID")
    private Integer FADJUSTPERIODID;
    @JSONField(name ="FYEAR")
    private String FYEAR;
    @JSONField(name ="FADJUSTPERIOD")
    private String FADJUSTPERIOD;
    @JSONField(name ="FADJUSTNAME")
    private String FADJUSTNAME;
    @JSONField(name ="FCOMMENT")
    private String FCOMMENT;
    @JSONField(name ="FADJUSTSTATUS")
    private String FADJUSTSTATUS;

}
