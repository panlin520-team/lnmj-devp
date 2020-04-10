package com.lnmj.store.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.store.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/7/5
 */
@Data
public class UserWalletVO extends BaseEntity {
    private Long userId;
    private String account;
    private String password;
    private Integer userType;
    private String mobile;
    private String email;
    private String name;
    private Integer sex;
    private String nickName;
    private Integer idType;
    private String idNumber;
    private String openId;
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private String streetAddress;
    private String addressDetail;
    private String cardNumber;
    private Integer membershipLevelId;
    private String membershipLevelName;
    private String registrationChannel;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registrationTime;
    private Integer emailAuthentication;
    private Integer mobileAuthentication;
    private Long parentId;
    private String userIdentityKey;
    private Integer storeId;
    private Integer isEnable;
    private Integer isLogin;
    private Long walletId;
    private BigDecimal rechargeAmount;
    private BigDecimal rebateAmount;
    private BigDecimal returnAmount;
    private BigDecimal treatAmount;
    private BigDecimal giveAmount;
    private BigDecimal shareAmount;
    private BigDecimal prestoreAmount;
    private BigDecimal integral;
}
