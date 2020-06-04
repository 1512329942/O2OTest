package com.se.dao;

import com.se.domain.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 10:02 2020/6/1
 * @Modified by:
 */
public interface ProductDao {
    List<Product> queryProductList(@Param("productCondition") Product productCondition,
                                   @Param("pageSize")int pageSize);

    /**
     * 添加商品
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 根据productid获取product的信息
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /**
     * 更新商品信息
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 根据页数 查询商品列表，可以输入的条件:商品名（模糊），商品状态，店铺id，商品种类
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,@Param("rowIndex") int rowIndex,
                                   @Param("pageSize")int pageSize);

    /**
     * 查询对应商品的总数
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);
}
