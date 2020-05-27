package com.se.dao;

import com.se.domain.Shop;

/**
 * @Author: Qi Weidong
 * @Description: 店铺
 * @Date: Create in 23:43 2020/5/26
 * @Modified by:
 */
public interface ShopDao {
    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
