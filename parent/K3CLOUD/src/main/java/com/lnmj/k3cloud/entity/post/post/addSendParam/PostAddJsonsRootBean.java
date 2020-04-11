package com.lnmj.k3cloud.entity.post.post.addSendParam;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.lnmj.k3cloud.entity.base.BaseData;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2019-11-11 19:8:51
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
@Data
public class PostAddJsonsRootBean extends BaseData {
    @JSONField(name="Model")
    private PostAddModel model;
}