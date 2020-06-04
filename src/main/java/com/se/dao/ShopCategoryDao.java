package com.se.dao;

import com.beust.jcommander.Parameter;
import com.se.domain.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Qi Weidong
 * @Description: 店铺种类
 * @Date: Create in 8:15 2020/5/29
 * @Modified by:
 */
public interface ShopCategoryDao {

    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")
                                         ShopCategory shopCategoryCondition);
}
