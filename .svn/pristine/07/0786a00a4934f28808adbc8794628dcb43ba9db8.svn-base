package com.lnmj.authservice.common;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 */
public class VerificationUtil {
    public static final String REGEX  = "[0-9a-zA-Z .?,@#$%^*&~`*+=-_!]{6,}";
    public static final String IP_REGEX  = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    /**
     * 手机号验证正则
     */
    private  static final String MOBILE_NUMBER_REGEX = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

    /** 多邮箱格式验证 */
    public static final String MULTIPLE_EMAIL_REGEX_FORMAT = "(?:(?:%1$s)(?:(?:\\s*,\\s*)|(?:\\s*;\\s*)|\\s*$))*";

    /** 单个邮箱验证 */
    public static final String SINGLE_EMAIL_REGEX = "(?:(?:[A-Za-z0-9\\-_@!#$%&'*+/=?^`{|}~]|(?:\\\\[\\x00-\\xFF]?)|(?:\"[\\x00-\\xFF]*\"))+(?:\\.(?:(?:[A-Za-z0-9\\-_@!#$%&'*+/=?^`{|}~])|(?:\\\\[\\x00-\\xFF]?)|(?:\"[\\x00-\\xFF]*\"))+)*)@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+(?:(?:[A-Za-z0-9]*[A-Za-z][A-Za-z0-9]*)(?:[A-Za-z0-9-]*[A-Za-z0-9])?))";

    /**
     * 密码复杂程度
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        return password.matches(REGEX);
    }

    public static boolean isIP(String ip) {
        if(StringUtils.isBlank(ip)|| !ip.matches(IP_REGEX)) {
            return false;
        }
        return true;
    }
    /**
     * 单个邮箱验证是否正确
     * @param email
     * @return
     */
    public static boolean isSignleEmail(String email) {
        return matches(email,SINGLE_EMAIL_REGEX);
    }

    /**
     * 多个邮箱验证是否正确
     * @param email
     * @return
     */
    public static boolean isMoreEmail(String email) {
        return matches(email,MULTIPLE_EMAIL_REGEX_FORMAT);
    }

    /**
     * 手机号验证
     * @param mobileNumber
     * @return
     */
    public static boolean isMobileNumber(String mobileNumber) {
        return matches(mobileNumber,MOBILE_NUMBER_REGEX);
    }

    /**
     *  验证
     * @param v
     * @param regex
     * @return
     */
    private static boolean matches(String v,String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(v);
        return m.matches();
    }


}
