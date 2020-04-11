package com.lnmj.data.entity.VO;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/3/18
 */

@Data
public class ProductParamVO {
    @JSONField(name ="FName")
    private String FName;
}
