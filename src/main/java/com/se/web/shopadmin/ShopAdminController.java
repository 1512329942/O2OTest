package com.se.web.shopadmin;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 16:35 2020/5/28
 * @Modified by:
 */

import com.beust.jcommander.IValueValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname: ShopAdminController
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/28 16:35
 */
@Controller
@RequestMapping(value = "/shopadmin",method = RequestMethod.GET)
public class ShopAdminController {
    /**
     * 1.指向shopoperation.html
     * 2.为什么没有加./html:因为在spring-mvc的视图解析器中加了前缀和后缀
     *      会将 shop/shopoperation加上前缀和后缀
     * @return
     */
    @RequestMapping(value = "/shopoperation")
    public String shopOperation(){
        return "shop/shopoperation";
    }

    /**
     * 指向shoplist.html
     * @return
     */
    @RequestMapping(value = "/shoplist")
    public String shopList(){
        return "shop/shoplist";
    }


    @RequestMapping(value = "/shopmanagement")
    public String shopManagement(){
        return "shop/shopmanagement";
    }


    @RequestMapping(value = "/productcategorymanagement",method = RequestMethod.GET)
    public String productCategoryManagement(){
        return "shop/productcategorymanagement";
    }


    @RequestMapping(value = "productoperation")
    public String  productOperation(){
        //转发至商品添加/编辑页面
        return "shop/productoperation";
    }



}
