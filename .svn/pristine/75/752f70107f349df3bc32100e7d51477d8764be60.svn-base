package com.lnmj.authority.controller.backend;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: panlin
 * @Date: 2019/8/19 15:51
 * @Description: 生成验证码路由
 */
@RestController
@RequestMapping("/manager/authority")
public class DrawImageController {

	//使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
	public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	private static Random random = new Random();
	 /**
     * 生成图片
     * @param
     * @param
     */
    @RequestMapping("/drawImage")
    public ResponseResult drawImage(HttpSession session){
		int codesLen = VERIFY_CODES.length();
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder verifyCode = new StringBuilder(4);
		for (int i = 0; i < 4; i++) {
			verifyCode.append(VERIFY_CODES.charAt(rand.nextInt(codesLen - 1)));
		}
		session.setAttribute("checkcode",verifyCode);
		Map<String ,Object> map = new HashMap();
		map.put("sRand",verifyCode);
		map.put("sessionId",session.getId());
		return ResponseResult.success(map);
    }

}
