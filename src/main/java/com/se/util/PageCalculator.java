package com.se.util;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 11:39 2020/5/30
 * @Modified by:
 */

/**
 * @Classname: PageCalculator
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/30 11:39
 */
public class PageCalculator {

    public static int calculRowIndex(int pageIndex,int pageSize){
        return (pageIndex>0)?(pageIndex-1)*pageSize:0;
    }
}
