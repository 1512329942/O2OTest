package com.se.dao;

import com.se.domain.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Qi Weidong
 * @Description: 商品 类别管理
 * @Date: Create in 9:33 2020/5/31
 * @Modified by:
 */
public interface ProductCategoryDao {
    /**
     * 根据shopId 返回商品类别列表
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);

    /**
     * 批量添加商品类别
     * int影响了多少行数
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除指定商铺的 指定商品类别
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);
}
