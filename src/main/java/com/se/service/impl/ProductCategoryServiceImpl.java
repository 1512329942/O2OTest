package com.se.service.impl;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 9:48 2020/5/31
 * @Modified by:
 */

import com.se.dao.ProductCategoryDao;
import com.se.domain.ProductCategory;
import com.se.dto.ProductCategoryExecution;
import com.se.enums.ProductCategoryStateEnum;
import com.se.exceptions.ProductCategoryOperationException;
import com.se.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname: ProductCategoryServiceImpl
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/31 9:48
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;


    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if(productCategoryList!=null&&productCategoryList.size()>0){
            try{
                int effectedNum=productCategoryDao.batchInsertProductCategory(productCategoryList);
                if(effectedNum<=0){
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                }else{
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch(Exception ex){
                throw new ProductCategoryOperationException("batchAddProductCategory error:"+ ex.getMessage());
            }
        }else{
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }


    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        //TODO 但是注意，在了删除商品类别前，将此商品类别下的所有商品的商品类别置为Null
        try{
            int effectedNum=productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if(effectedNum<=0){
                throw new ProductCategoryOperationException("商品类别删除失败");
            }else{
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (Exception ex){
            throw new ProductCategoryOperationException("deleteProductCategory error:"+ ex.getMessage());

        }

    }

}
