package com.lnmj.data.entity.VO;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.Date;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2020/3/17
 */

@Data
public class BackMemberVO {
    //会员账号
    private String memberNum;
    private String userName;
    private String name;
    private String mobile;
    private String membershipLevelId;
    private String membershipLevelName;
    private String wxOpenId;
    private String headImgUrl;
}
