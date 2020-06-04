package com.se.dao;

import com.se.domain.ProductImg;

import java.util.List;

/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 10:06 2020/6/1
 * @Modified by:
 */
public interface ProductImgDao {

    /**
     * 列出某个商品的详情图列表
     *
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);

    /**
     * 批量添加商品详情图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 根据productid删除所有的productImg
     * @return
     */
    int deleteProductImgByProductId(long productId);
}
