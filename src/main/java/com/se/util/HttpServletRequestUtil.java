package com.se.util;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 9:05 2020/5/28
 * @Modified by:
 */

import javax.servlet.http.HttpServletRequest;

/**
 * @Classname: HttpServletRequestUtil
 * @Description: 处理request参数的工具类
 * @Author: Qi weidong
 * @Date: 2020/5/28 9:05
 */
public class HttpServletRequestUtil {
    /**
     * 1.根据key取出值
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request,String key){
        /**
         * integer.deocde(string)将字符串转换成数字，可以解析非10进制，返回integer
         * integer.parseInt(str) 返回int ,解析非10进制，Integer.parseInt(str,int)
         */
        try{
            return Integer.decode(request.getParameter(key));

        }catch (Exception ex){
            return -1;
        }
    }
    public static long getLong(HttpServletRequest request,String key){
        try{
            return Long.valueOf(request.getParameter(key));

        }catch (Exception ex){
            return -1;
        }
    }
    public static Double getDouble(HttpServletRequest request,String key){
        try{
            return Double.valueOf(request.getParameter(key));

        }catch (Exception ex){
            return -1D;
        }
    }

    public static boolean getBoolean(HttpServletRequest request,String key){
        try{
            return Boolean.valueOf(request.getParameter(key));

        }catch (Exception ex){
            return false;
        }
    }
    public static String getString(HttpServletRequest request,String key){
        try{
            String result=request.getParameter(key);
            if (result!=null){
                result=result.trim();
            }
            if(result==null||"".equals(result)){
                result=null;
            }
            return result;
        }catch (Exception ex){
            return null;
        }
    }
}
