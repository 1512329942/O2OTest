package com.se.service;

import com.se.domain.Shop;
import com.se.dto.ImageHolder;
import com.se.dto.ShopExecution;
import com.se.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 11:16 2020/5/27
 * @Modified by:
 */
public interface ShopService {

    /**
     * 根据shopCondtiop分页返回相应的店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);


    /**
     * 根据shopId获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;



    /**
     * 注册店铺信息
     * 1.注册信息，
     * 2.图片处理（加水印）
     * @param shop
     * @param
     *
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
}
