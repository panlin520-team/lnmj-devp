package com.lnmj.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Auther: panlin
 * @Date: 2019/4/25 12:51
 * @Description:
 */
public class BPwdEncoderUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String  BCryptPassword(String password){
        return encoder.encode(password);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword){
        return encoder.matches(rawPassword,encodedPassword);
    }

}