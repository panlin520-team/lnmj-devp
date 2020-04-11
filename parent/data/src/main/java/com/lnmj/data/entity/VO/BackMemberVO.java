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
    private String memberNum;
    private String userName;
    private String password;
    private String birthday;
    private String name;
    private Integer sex;
    private String idCard;
    private String mobile;
    private String membershipLevelId;
    private String membershipLevelName;
}
