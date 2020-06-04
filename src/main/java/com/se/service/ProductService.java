package com.se.service;

import com.se.domain.Product;
import com.se.dto.ImageHolder;
import com.se.dto.ProductExecution;
import com.se.exceptions.ProductOperationException;

import java.io.InputStream;
import java.util.List;

/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 11:09 2020/6/1
 * @Modified by:
 */
public interface ProductService {
    /**
     * 添加商品信息，和图片处理
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail,
                                List<ImageHolder> productImgHolderList) throws ProductOperationException;

    /**
     * 根据product 获取product
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     *
     */
    ProductExecution modifyProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgHolderList) throws ProductOperationException;
}
