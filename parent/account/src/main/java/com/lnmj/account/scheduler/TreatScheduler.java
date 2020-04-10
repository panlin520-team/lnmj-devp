/*
package com.lnmj.account.scheduler;

import com.lnmj.account.business.impl.UserLabelService;
import com.lnmj.account.entity.*;
import com.lnmj.account.entity.VO.AccountVO;
import com.lnmj.account.repository.*;
import com.lnmj.account.serviceProxy.OrderApi;
import com.lnmj.account.serviceProxy.SystemApi;
import com.lnmj.account.serviceProxy.WalletApi;
import com.lnmj.common.Enum.AmountMethodEnum;
import com.lnmj.common.Enum.LabelGradeEnum;
import com.lnmj.common.Enum.OrderTypeEnum;
import com.lnmj.common.response.ResponseResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Component
public class TreatScheduler {
    @Resource
    private IMemberDao memberDao;
    @Resource
    private IMemberUserDao iMemberUserDao;
    @Resource
    private ILabelDao labelDao;
    @Resource
    private WalletApi walletApi;
    @Resource
    private SystemApi systemApi;
    @Resource
    private OrderApi orderApi;
    @Resource
    private IUserLabelDao userLabelDao;
    @Resource
    private UserLabelService userLabelService;
    @Resource
    private IStoreMemberDao iStoreMemberDao;

    */
/**
     * // @Scheduled(fixedRate = 60*1000)  //一分钟执行一次
     * 每分钟执行   0 * * * * ?  【秒】 【分】 【小时】 【日】 【月】 【周】 【年】（年可以不设置）
     * 每月1号执行  cron: 0 0 0 1 * ?
     *//*

    @Scheduled(cron = "${spring.quartz.cron}")
    public void treatInit() {
        System.out.println("--------------------定时任务执行---------------");
        //获取钱包账户类型
        ResponseResult walletAccount = walletApi.selectWalletAccountList();
        List<HashMap> walletAccountList = (List<HashMap>) walletAccount.getResult();
        if (walletAccountList.size() <= 0) {
            return;
        }
//        for(int i=0;i<walletAccountList.size();i++){
//            if("请客账户".equals(walletAccountList.get(i).get("accountType"))){
//                Integer ac = (Integer) walletAccountList.get(i).get("accountTypeId");
//                accountTypeId = ac.longValue();
//            }
//        }
//        if(accountTypeId == null){      //没有请客账户
//            return;
//        }
        //请求数据库的所有会员等级获取请客账户
        List<MMembershipLevel> membershipLevelList = memberDao.selectAllNormalMemberList();
        if (membershipLevelList.size() <= 0) {
            return;
        }
        //获取所有用户的等级
        AccountVO accountVO = new AccountVO();
        List<Account> accountList = accountDao.selectAccountList(accountVO);
        if (accountList.size() <= 0) {
            return;
        }
        //用户的等级的账户金额
        for (int i = 0; i < accountList.size(); i++) {  //用户
            Account account = accountList.get(i);
            Long membershipLevelId = account.getMembershipLevelId();
            Long userId = account.getUserId();
            String cardNumber = account.getCardNumber();
            if (membershipLevelId == null || membershipLevelId == 0) {
                continue;
            }
            //钱包信息
            ResponseResult responseResult = walletApi.selectWalletByCarNumber(account.getCardNumber());
            Integer w = (Integer) responseResult.getResult();
            if (w == null || w == 0) {
                continue;
            }
            Long walletId = w.longValue();
            for (int j = 0; j < membershipLevelList.size(); j++) {  //等级
                List<MMemberaccount> memberAccountList = membershipLevelList.get(j).getMemberAccountList();
                if (membershipLevelId.equals(membershipLevelList.get(j).getMembershipLevelId())) {
                    for (MMemberaccount memberaccount : memberAccountList) {    //账户金额
                        Long accountTypeId = memberaccount.getAccountTypeId();
                        BigDecimal accountAmount = memberaccount.getAccountAmount();
                        BigDecimal oneMonthAmount = memberaccount.getOneMonthAmount();
                        Long memberAccountId = memberaccount.getMemberAccountId();
                        String accountAmountScope = memberaccount.getAccountAmountScope();
                        //是否清零
                        Boolean isReset = memberaccount.getIsReset();
                        //记录
                        Date startTime = new Date();
                        Date endTime = new Date();
                        BigDecimal banlance = BigDecimal.ZERO;
                        BigDecimal amount = BigDecimal.ZERO;
                        //等级账户是否启用,是否合理
                        if (memberaccount.getIsUse()) {
                            //是否已经付过（会员账户金额分发记录）
                            MMemberamountrecord memberamountrecord = new MMemberamountrecord();
                            memberamountrecord.setUserId(userId);
                            memberamountrecord.setCardNumber(cardNumber);
                            memberamountrecord.setMemberLevelID(membershipLevelId);
//                          memberamountrecord.setMemberAmountType("账户");
                            memberamountrecord.setMemberAmountSource(memberAccountId);
                            List<MMemberamountrecord> memberamountrecordList = memberDao.selectMemberAmountRecordByCondition(memberamountrecord);
                            //一次性付
                            if (memberaccount.getAmountMethod().equals(AmountMethodEnum.ALL.getCode())) {
                                if (memberamountrecordList == null || memberamountrecordList.size() == 0) {
                                    amount = accountAmount;
                                    //开始时间，结束时间
                                    endTime = getEndTime(startTime, accountAmountScope);
                                    if (isReset) {
                                        //重置钱包账户金额
                                        walletApi.treatAccountInit(walletId, accountTypeId, amount);
                                    }
                                    //设置钱包的请客账户余额
                                    walletApi.treatAccountRecharge(walletId, accountTypeId, amount);
                                    //写入记录
                                }
                            }
                            //按月付
                            if (memberaccount.getAmountMethod().equals(AmountMethodEnum.ONE_MONTH.getCode())) {
//                                //已经付了好多，现在是否还付（会员账户金额分发记录，股东权益记录）
                                if (memberamountrecordList == null || memberamountrecordList.size() == 0) {
                                    amount = oneMonthAmount;
                                    //余额
                                    banlance = accountAmount.subtract(oneMonthAmount);
                                    //时间
                                    endTime = getEndTime(startTime, accountAmountScope);
                                    //第一个月
                                    if (isReset) {
                                        //重置钱包账户金额
                                        walletApi.treatAccountInit(walletId, accountTypeId, amount);
                                    }
                                    //设置钱包的请客账户余额
                                    walletApi.treatAccountRecharge(walletId, accountTypeId, amount);
                                    //写入记录
                                } else {
                                    //记录按时间排序
                                    if (memberamountrecordList.size() > 1) {
                                        Collections.sort(memberamountrecordList, new Comparator<MMemberamountrecord>() {
                                            @Override
                                            public int compare(MMemberamountrecord o1, MMemberamountrecord o2) {
                                                return o1.getAmountTime().compareTo(o2.getAmountTime());
                                            }
                                        });
                                    }
                                    amount = oneMonthAmount;
                                    //结束时间（第一次发的结束时间为准）
                                    endTime = memberamountrecordList.get(0).getAmountEndTime();
                                    //当前时间是否在结束时间之前(当前时间是否在有效期内)
                                    if (endTime.compareTo(startTime) <= 0) {
                                        continue;
                                    }
                                    //有效期内发完了没使用是否继续发
                                    Boolean isContinue = memberaccount.getIsContinue();
                                    //余额
                                    banlance = memberamountrecordList.get(memberamountrecordList.size() - 1).getBanlance();
                                    if (banlance.compareTo(BigDecimal.ZERO) <= 0) {   //发完
                                        if (!isContinue) {
                                            //不继续发
                                            continue;
                                        }
                                        //继续发
                                        //查询消费记录（查询是否使用）TODO
                                        ResponseResult responseResult1 = walletApi.selectUserWalletConsumeRecord(userId, walletId, accountTypeId);
                                        if (responseResult1.getResult() != null) {
                                            //消费过
                                            List<HashMap<String, String>> mapList = (List<HashMap<String, String>>) responseResult1.getResult();
                                            //消费总额(余额)
                                            BigDecimal amountTotal = BigDecimal.ZERO;
                                            for (HashMap<String, String> map : mapList) {
                                                amountTotal = amountTotal.add(new BigDecimal(map.get("amount")));
                                            }
                                            if (accountAmount.compareTo(amountTotal) <= 0) {
                                                //消费完
                                                continue;
                                            }
                                            memberamountrecord.setRemark(amountTotal.toString());
                                            accountAmount = accountAmount.subtract(amountTotal);
                                        }
                                        //没有消费过(直接继续发)
                                    }
                                    banlance = banlance.subtract(amount);
                                    if (banlance.compareTo(BigDecimal.ZERO) < 0) {
                                        banlance = BigDecimal.ZERO;
                                    }
                                    //设置钱包的请客账户余额
                                    if (isReset) {
                                        //重置钱包账户金额
                                        walletApi.treatAccountInit(walletId, accountTypeId, amount);
                                    }
                                    //充值
                                    walletApi.treatAccountRecharge(walletId, accountTypeId, amount);
                                }
                            }
                            //写入记录
                            memberamountrecord.setMemberAmountType("账户");
                            memberamountrecord.setTotalAmount(accountAmount);
                            memberamountrecord.setBanlance(banlance);
                            memberamountrecord.setAmount(amount);
                            memberamountrecord.setAmountTime(startTime);
                            memberamountrecord.setAmountEndTime(endTime);
                            memberamountrecord.setCreateOperator("scheduler");
                            memberDao.insertMemberAmountRecord(memberamountrecord);
                        }
                    }
                }
            }
            */
/*
                BigDecimal:
                加法  totalAmount.add(new BigDecimal(12))
                减法  totalAmount.subtract(new BigDecimal(12))
                乘法  totalAmount.multiply(new BigDecimal(12))
                除法  totalAmount.divide(new BigDecimal(12))
                CEILING	向正无限大方向舍入。
                DOWN	向零方向舍入。
                FLOOR	向负无限大方向舍入。
                HALF_DOWN	向最接近数字方向舍入，如果与两个相邻数字的距离相等，则向下舍入。
                HALF_EVEN	向最接近数字方向舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。
                HALF_UP	向最接近数字方向舍入，如果与两个相邻数字的距离相等，则向上舍入。
                UNNECESSARY	断言具有精确结果。
             *//*

        }
    }

    private Date getEndTime(Date startTime, String accountAmountScope) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        if (accountAmountScope.contains("年")) {
            accountAmountScope = accountAmountScope.trim().replaceAll("年", "");
            int year = Integer.valueOf(accountAmountScope);
            calendar.add(Calendar.YEAR, year);
        }
        if (accountAmountScope.contains("月")) {
            accountAmountScope = accountAmountScope.trim().replaceAll("月", "");
            int month = Integer.valueOf(accountAmountScope);
            calendar.add(Calendar.MONTH, month);
        }
        if (accountAmountScope.contains("天")) {
            accountAmountScope = accountAmountScope.trim().replaceAll("天", "");
            int day = Integer.valueOf(accountAmountScope);
            calendar.add(Calendar.DATE, day);
        }
        Date endTime = calendar.getTime();
        return endTime;
    }


    */
/**
     * @Description 标签初始化
     * @Param []
     * @Return void
     * @Author Mr.Ren
     * @Date 2019/9/16
     * @Time
     *//*

    @Scheduled(cron = "${spring.quartz.cron}")
//    @Scheduled(fixedRate = 60 * 1000)
    public void labelInit() {
        System.out.println("--------------------定时任务执行---------------");
        //查询所有用户
        AccountVO accountVO = new AccountVO();
        List<Account> result = accountDao.selectAccountList(accountVO);
        //判断储值以及消费金额是否达到条件
        for (Object obj : result) {
            HashMap map = (HashMap) obj;
            Integer id = (Integer) map.get("userId");
            Long userId = (Long) id.longValue();
            //通过用户id查询储值总额
            Integer storagesum = (Integer) walletApi.selectSumAmount(userId).getResult();
            //通过用户id查询消费总额
            Integer consumersum = (Integer) walletApi.selectSumAmount(userId).getResult();
            Map param = new HashMap<>();
            List<Label> labels = labelDao.selectAll(param);
            for (Label label : labels) {
                //总金额大于某一等级的储值金额就拥有该标签
                BigDecimal bigDecimal = new BigDecimal(storagesum);
                //-1 小于 0 等于 1 大于
                if (bigDecimal.compareTo(label.getUpgradeStandardStorage()) >= 0 || bigDecimal.compareTo(label.getUpgradeStandardConsumption()) >= 0) {
                    //满足规定的储值条件
                    param.put("userId", userId);
                    param.put("labelId", label.getLabelId());
                    List<UserLabelInfo> userLabelInfoList = userLabelDao.checkIsExit(param);
                    if (userLabelInfoList.size() <= 0) {
                        getLabel(userId, label);
                    }
                    //判断是否是新老用户(如果美容美发都有订单，判断在哪个行业属于老用户)

                    //通过用户id拿到用户
//                    List<Map<String, String>> account = (List<Map<String, String>>) accountApi.selectAccountById(userId).getResult();

                    Map<String, String> account = (Map<String, String>) accountDao.selecAccountByUserId(userId);
                    //通过手机号在订单中查询用户的美容美发订单
//                    String cardNumber = account.get(0).get("cardNumber");
                    String cardNumber = account.get("cardNumber");
                    List<Map> meifaOrderList = (List<Map>) orderApi.selectOrderListByCarNumber(Long.parseLong(cardNumber), OrderTypeEnum.SERVICE_MEIFA_ORDER.getCode()).getResult();
                    List<Map> meirongOrderList = (List<Map>) orderApi.selectOrderListByCarNumber(Long.parseLong(cardNumber), OrderTypeEnum.SERVICE_MEIRONG_ORDER.getCode()).getResult();
                    UserLabelInfo userLabelInfo = new UserLabelInfo();
                    userLabelInfo.setUserId(userId);
                    userLabelInfo.setLabelId(label.getLabelId());
                    if (meifaOrderList.size() > 0 && meirongOrderList.size() > 0) {
                        String meifacreateTime = (String) meifaOrderList.get(0).get("createTime");
                        String meirongcreateTime = (String) meirongOrderList.get(0).get("createTime");
                        try {
                            long meifatime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(meifacreateTime).getTime();
                            System.out.println(meifatime);
                            long meirongtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(meirongcreateTime).getTime();
                            System.out.println(meirongtime);

                            //判断是哪个行业的老用户
                            if (meifatime > meirongtime) {
                                userLabelInfo.setIsHairdressingOldUser(1L);
                                userLabelDao.updateUserLabel(userLabelInfo);
                            } else if (meirongtime > meifatime) {
                                userLabelInfo.setIsCosmetologyOldUser(1L);
                                userLabelDao.updateUserLabel(userLabelInfo);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    //判断是否是丢失客户
                    List<Map> createTimeList = (List<Map>) orderApi.selectCreateTimeByCarNumber(cardNumber).getResult();
                    String createTime = (String) createTimeList.get(0).get("createTime");
                    Map<Object, List<Map<Object, Object>>> paramsList = (Map<Object, List<Map<Object, Object>>>) systemApi.selectParameterList(1L).getResult();
                    List<Map<Object, Object>> params = paramsList.get("list");
                    for (Map<Object, Object> m : params) {
                        String parameterName = (String) m.get("parameterName");
                        if (parameterName.equals("丢失用户判定时间条件")) {
                            //系统时间参数--几个月
                            long parameterValue = Long.parseLong(m.get("parameterValue").toString());
                            //转时间戳
                            try {
                                long createtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTime).getTime();
                                long now = System.currentTimeMillis();
                                long a = now - createtime;
                                Timestamp end = new Timestamp(a);
                                LocalDate localDate = end.toLocalDateTime().toLocalDate();
                                //相差的天数
                                long days = localDate.toEpochDay();
                                if (days > parameterValue) {
                                    userLabelInfo.setIsLoseUser(1L);
                                    userLabelDao.updateUserLabel(userLabelInfo);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }


    public void getLabel(Long userId, Label label) {
        UserLabelInfo userLabelInfo = new UserLabelInfo();
        userLabelInfo.setUserId(userId);
        if (label.getLabelName().equals(LabelGradeEnum.HIGH_MEIRONG.getDesc())) {
            long code = (long) LabelGradeEnum.HIGH_MEIRONG.getCode();
            userLabelInfo.setLabelId(code);
        } else if (label.getLabelName().equals(LabelGradeEnum.MIDDLE_MEIRONG.getDesc())) {
            long code = (long) LabelGradeEnum.MIDDLE_MEIRONG.getCode();
            userLabelInfo.setLabelId(code);
        } else if (label.getLabelName().equals(LabelGradeEnum.LOWER_MEIRONG.getDesc())) {
            long code = (long) LabelGradeEnum.LOWER_MEIRONG.getCode();
            userLabelInfo.setLabelId(code);
        } else if (label.getLabelName().equals(LabelGradeEnum.HIGH_MEIFA.getDesc())) {
            long code = (long) LabelGradeEnum.HIGH_MEIFA.getCode();
            userLabelInfo.setLabelId(code);
        } else if (label.getLabelName().equals(LabelGradeEnum.MIDDLE_MEIFA.getDesc())) {
            long code = (long) LabelGradeEnum.MIDDLE_MEIFA.getCode();
            userLabelInfo.setLabelId(code);
        } else if (label.getLabelName().equals(LabelGradeEnum.LOWER_MEIFA.getDesc())) {
            long code = (long) LabelGradeEnum.LOWER_MEIFA.getCode();
            userLabelInfo.setLabelId(code);
        }
        //匹配该标签
        userLabelService.addUserLabel(userLabelInfo);
    }
    */
/**
     * @Description 更新门店会员新老状态
     * @Param []
     * @Return void
     * @Author Mr.pan
     * @Date 2019/9/26
     * @Time
     *//*

    */
/*@Scheduled(cron = "${spring.quartz.cron}")*//*

    */
/*@Scheduled(fixedRate = 30 * 1000)*//*

    public void updateStoreMemberInfo() throws ParseException {
        System.out.println("--------------------定时任务执行 判定丢失会员---------------");
        //查看系统参数
        List<Map> mapParaList = (List<Map>)systemApi.selectParameterByWhere(1L).getResult();
        Integer parameterValueLostParam = null;
        String  parameterValueIsOldMember = null;
        for (Map mapParam : mapParaList) {
            if (mapParam.get("parameterName").equals("丢失用户判定时间条件")){
                parameterValueLostParam=Integer.parseInt(mapParam.get("parameterValue").toString());
            }else if(mapParam.get("parameterName").equals("新老用户判定")){
                parameterValueIsOldMember=mapParam.get("parameterValue").toString();
            }
        }
        //如果从注册开始延续N天没有下单记录为修改丢失
        List<StoreMemberInfo> storeMemberInfos = iStoreMemberDao.selectStoreMemberInfoList();
        List<Map> orderList = (List<Map>) orderApi.selectOrderListNoPage().getResult();
        List<StoreMemberInfo> storeMemberInfoListForLost = new ArrayList<>();
        for (StoreMemberInfo storeMemberInfo : storeMemberInfos) {
            boolean flag = false;
            for (Map map : orderList) {
                //判定是否修改未消费状态
                if (daysBetween(storeMemberInfo.getCreateTime(), new Date()) >= parameterValueLostParam &&
                        map.get("cardNumber").toString().equals(storeMemberInfo.getMemberNum()) &&
                        inTheTwoDate(map.get("createTime").toString(), getEndTimeForOrder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), parameterValueLostParam), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                ) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                //没有注册了5天 或者这5天内没有订单 就标记为丢失
                storeMemberInfoListForLost.add(storeMemberInfo);
                System.out.println("丢失了");
                //TODO 修改状态
            }
        }

        //修改成为了丢失的会员状态
        Map mapLost = new HashMap();
        mapLost.put("list",storeMemberInfoListForLost);
        if (storeMemberInfoListForLost.size()!=0){
            iStoreMemberDao.updateStoreMemberToLost(mapLost);
        }



        System.out.println("--------------------定时任务执行 判定新老会员---------------");
        Integer conditionOne = Integer.parseInt(parameterValueIsOldMember.split(",")[0].split("-")[0]);//多少天内
        Integer conditionTwo = Integer.parseInt(parameterValueIsOldMember.split(",")[0].split("-")[1]);//有多少次消费
        Integer conditionThree = Integer.parseInt(parameterValueIsOldMember.split(",")[1]);//多少天未消费
         List<Map> mapsForOrder = (List<Map>)orderApi.selectOrderListByCarNumAndDates(null,
                 getEndTimeForOrder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), conditionOne),
                 new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).getResult();

         List<StoreMemberInfo> storeMemberInfoListForOld = new ArrayList<>();
        for (StoreMemberInfo storeMemberInfo : storeMemberInfos) {
            int number = 0;
            for (Map mapItem : mapsForOrder) {
                if (storeMemberInfo.getMemberNum().equals(mapItem.get("cardNumber"))){
                    number++;
                    if (number>=conditionTwo&&daysBetween(new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").parse(mapsForOrder.get(0).get("createTime").toString()),new Date())<=conditionThree){
                        System.out.println("老顾客");
                        storeMemberInfoListForOld.add(storeMemberInfo);
                        break;
                    }
                }
            }
        }
       //修改成为了老顾客的会员状态
        Map mapOld = new HashMap();
        mapOld.put("list",storeMemberInfoListForOld);
        if (storeMemberInfoListForOld.size()!=0){
            iStoreMemberDao.updateStoreMemberToOld(mapOld);
        }

    }

    public static String getEndTimeForOrder(String oldDate, int continueDays) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
        Date date = dateFormat.parse(oldDate); // 指定日期
        long time = date.getTime(); // 得到指定日期的毫秒数
        continueDays = continueDays * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time -= continueDays; // 相加得到新的毫秒数
        Date newDate = new Date(time); // 指定日期加上10天
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("EEE MMM d hh:mm:ss z yyyy", java.util.Locale.US);
        SimpleDateFormat bartDateFormat_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String memberCreateDate = null;
        Date d = new Date();
        d = bartDateFormat.parse(newDate.toString());
        memberCreateDate = bartDateFormat_1.format(d);
        return memberCreateDate;
    }

    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    */
/**
     * @return boolean true 表示这个日期在这二个日期之
     * @Description 传入yyyy-MM-dd的String类型的date 2019-06-28
     * @Date 18:13 2019/6/28
     * @Param [m, st, ed] m=判断时间  st开始时间 ed结束日期时间 时间都是yyyy-MM-dd格式
     **//*

    public static boolean inTheTwoDate(String m, String st, String ed) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int startDay = 0;
        int endDay = 0;
        int mDay = 0;
        try {
            Date dateStart = format.parse(st);
            Date datEnd = format.parse(ed);
            Date mDate = format.parse(m);

            startDay = (int) (dateStart.getTime() / 1000);
            endDay = (int) (datEnd.getTime() / 1000);
            mDay = (int) (mDate.getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (startDay <= mDay && mDay <= endDay) {
            return true;
        } else {
            return false;
        }
    }


}
*/
