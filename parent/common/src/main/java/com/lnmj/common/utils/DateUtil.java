package com.lnmj.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: panlin
 * @Date: 2019/4/18 15:51
 * @Description:
 */
public class DateUtil {
    /**
     * string类型日期转long类型
     *
     * @return
     */
    public static Long stringForDateTime(Object date) {
        Date val = null;
        String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        if (date instanceof String) {
            SimpleDateFormat sdf = null;
            String value = (String) date;
            if (!value.matches(regex)) {
                return null;
            }
            int isAcross = value.indexOf("-");
            int isSlash = value.indexOf("/");
            if (value.indexOf(":") != -1) {
                if (isAcross != -1) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                } else if (isSlash != -1) {
                    sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                }
            } else {
                if (isAcross != -1) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                } else if (isSlash != -1) {
                    sdf = new SimpleDateFormat("yyyy/MM/dd");
                }
            }
            try {
                val = sdf.parse((String) date);
            } catch (ParseException e) {
                return null;
            }
        } else if (date instanceof Date) {
            val = (Date) date;
        }
        return val.getTime();
    }

    /**
     * 随机数生成
     *
     * @return
     */
    public static String randomNumber() {
        String n = System.nanoTime() + "" + (int) Math.floor(Math.random() * 100);
        if (n.length() == 17) {
            n += 0;
        }
        return n;
    }

    /**
     * 编号生成
     *
     * @return
     */
    public static String produceNum() {
        String chars = "0123456789";
        String res = "";
        for (int j = 0; j < 6; j++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        String ma = "yyyyMMdd";
        SimpleDateFormat forma = new SimpleDateFormat(ma);
        String nwdate = forma.format(date);
        String saleBillCode = nwdate + res;
        return saleBillCode;
    }

    /**
     * 日期格式化
     *
     * @return
     */
    public static String dateFormat(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 日期格式化
     *
     * @return
     */
    public static String dateFormat(String format, String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    /**
     * 日期格式化
     *
     * @param format
     * @param date
     * @return
     */
    public static Date formatDt(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.parse(sdf.format(date));
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * String 装换成date类型
     *
     * @param format
     * @param dateString
     * @return
     */
    public static Date dateStringForDate(String format, String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        if (dateString == null || "".equals(dateString)) {
            return date;
        }
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //日期计算
    public static String dateCompute(String format, String dateStr, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try {
            date = (sdf).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        date = cal.getTime();
        return sdf.format(date);
    }

    public static String randomNum() {
        int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        String randomNum = "";
        for (int index = 0; index < 6; index++) {
            randomNum += nums[(int) (Math.random() * 10)];
        }
        return randomNum;
    }

    public static Map getDateTodayAndYesToday() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date start = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        Date start2 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        Date start3 = calendar.getTime();
        String today = simpleDateFormat.format(start);//今天凌晨
        String tomorrow = simpleDateFormat.format(start2);//明天凌晨
        String yesterday = simpleDateFormat.format(start3); //昨天凌晨
        Map map = new HashMap();
        map.put("today", today);
        map.put("tomorrow", tomorrow);
        map.put("yesterday", yesterday);
        return map;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean compareTime(Date nowTime, Date appointTime) {
       /* String beginTime=new String("2017-06-10");
        String endTime=new String("2017-06-11");*/
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date sd1 = appointTime/*df.parse(beginTime)*/;
        Date sd2 = nowTime/*df.parse(endTime)*/;

        long long1 = sd1.getTime();
        long long2 = sd2.getTime();
        long a = long2 - long1;
        if (a > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String beginTime=new String("2020-06-09");
        Date sd1=df.parse(beginTime);
        boolean a = compareTime(new Date(), sd1);
        System.out.println(a);
    }
}