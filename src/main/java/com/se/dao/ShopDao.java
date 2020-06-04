package com.se.dao;

import com.se.domain.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Qi Weidong
 * @Description: 店铺
 * @Date: Create in 23:43 2020/5/26
 * @Modified by:
 */
public interface ShopDao {

    /**
     * 返回queryShopList的所有店铺的总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    /**
     * 分页查询店铺列表，可以输入：
     *      店铺名（模糊），店铺状态，店铺类别，区域id,owner
     *      rowIndex:从第几行取
     *      pageSize:取多少行
     * @return
     */
    List<Shop> queryShopList(@Param(("shopCondition"))Shop shopCondition,
                             @Param("rowIndex") int rowIndex,@Param("pageSize")int pageSize);

    /**
     * 根据id查询店铺
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);

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
