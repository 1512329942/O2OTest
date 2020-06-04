package com.se.util;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 11:03 2020/5/29
 * @Modified by:
 */

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname: CodeUtil
 * @Description: 判断验证码
 * @Author: Qi weidong
 * @Date: 2020/5/29 11:03
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
        String verifyCodeException=(String ) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual=HttpServletRequestUtil.getString(request,"verifyCodeActual");
        if(verifyCodeActual==null||!verifyCodeActual.equals(verifyCodeException)){
            return false;
        }
        return true;
    }
}
