package com.se.service;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 15:29 2020/5/27
 * @Modified by:
 */

import TestSpring.BaseTest;
import com.se.domain.Area;
import com.se.domain.PersonInfo;
import com.se.domain.Shop;
import com.se.domain.ShopCategory;
import com.se.dto.ShopExecution;
import com.se.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

/**
 * @Classname: ShopServiceTest
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/27 15:29
 */
public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;
    @Test
    public void testAddShop(){
        Shop shop=new Shop();
        PersonInfo owner=new PersonInfo();
        Area area=new Area();
        ShopCategory shopCategory=new ShopCategory();
        owner.setUserId(8L);
        area.setAreaId(3);
        shopCategory.setShopCategoryId(14L);

        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setOwner(owner);
        shop.setShopName("测试的店铺4444");
        shop.setShopDesc("tes3444");
        shop.setShopAddr("test44");
        shop.setPhone("test34444");

        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核");
        File shopImg=new File("F:\\校园商铺\\第26项目：SSM到Spring Boot-校园商铺平台\\images\\item\\shop\\15\\20170605233022618071.jpg");
        System.out.println(shopImg.toString());
        InputStream is= null;
        try {
            is = new FileInputStream(shopImg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ShopExecution shopExecution = shopService.addShop(shop, is,shopImg.getName());
        System.out.println(shopExecution.getState());
    }
}
