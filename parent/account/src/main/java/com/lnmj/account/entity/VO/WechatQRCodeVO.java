package com.lnmj.account.entity.VO;

import lombok.Data;

/**
 * @Auther: panlin
 * @Date: 2019/4/24 16:22
 * @Description:
 */
@Data
public class WechatQRCodeVO {
    // 获取的二维码
    private String ticket;
    // 二维码的有效时间,单位为秒,最大不超过2592000（即30天）
    private int expire_seconds;
    // 二维码图片解析后的地址
    private String url;

}
