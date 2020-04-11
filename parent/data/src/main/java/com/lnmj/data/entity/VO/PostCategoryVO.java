package com.lnmj.data.entity.VO;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/10/17 10:08
 * @Description: 小类对应职位分类
 */
@Data
public class PostCategoryVO {
    private String  postCategoryId;
//    private String  oneOrHalf;
//    private String  salemanProportion;
    private String postCategoryName;

    private List<Map> beauticianList;
    private List<Map> postList;
}

