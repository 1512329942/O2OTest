package com.se.service;

import com.se.domain.ProductCategory;
import com.se.dto.ProductCategoryExecution;
import com.se.enums.ProductCategoryStateEnum;
import com.se.exceptions.ProductCategoryOperationException;

import java.util.List;

/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 9:45 2020/5/31
 * @Modified by:
 */
public interface ProductCategoryService {
    /**
     * 根据shopId获取店铺的种类信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    /**
     * 店铺种类的批量添加
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    /**
     * 根据shopid和productcateogoryId来删除
     * 但是注意，在了删除商品类别前，将此商品类别下的所有商品的商品类别置为Null
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) throws ProductCategoryOperationException;
}
