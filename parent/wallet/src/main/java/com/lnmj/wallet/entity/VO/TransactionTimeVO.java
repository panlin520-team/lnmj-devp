package com.lnmj.wallet.entity.VO;

import lombok.Data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈时间端VO〉
 *
 * @author RQY
 * @create 2019/4/23
 */
@Data
public class TransactionTimeVO {

    private String payAccount;
    private String cardNumber;
    private String startTime;
    private String endTime;

}
