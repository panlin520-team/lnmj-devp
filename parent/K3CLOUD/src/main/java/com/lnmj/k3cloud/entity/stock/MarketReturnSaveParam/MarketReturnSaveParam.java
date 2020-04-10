package com.lnmj.k3cloud.entity.stock.MarketReturnSaveParam;

import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.base.BaseData;
import lombok.Data;

/**
 * @Author: yilihua
 * @Date: 2019-11-13 10:30
 * @Description:
 */
@Data
public class MarketReturnSaveParam extends BaseData {
    @JSONField(name = "Model")
    private MarketReturnModel Model;
}
