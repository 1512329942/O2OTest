package com.se.exceptions;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 15:11 2020/5/27
 * @Modified by:
 */

/**
 * @Classname: ShopOperationException
 * @Description: 店铺的异常处理
 * @Author: Qi weidong
 * @Date: 2020/5/27 15:11
 */
public class ShopOperationException extends RuntimeException {

    private static final long serialVersionUID = 5415304283763828743L;

    public ShopOperationException(String msg){
        super(msg);
    }
}
