package com.se.service;

import com.se.domain.ShopCategory;

import java.util.List;

/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 8:43 2020/5/29
 * @Modified by:
 */
public interface ShopCategoryService {
    /**
     * 获取ShopCategory 根据店铺信息
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
