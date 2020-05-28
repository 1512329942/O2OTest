package com.se.web.shopadmin;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 16:35 2020/5/28
 * @Modified by:
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Classname: ShopAdminController
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/28 16:35
 */
@Controller
@RequestMapping(value = "shopadmin",method = RequestMethod.GET)
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
}
