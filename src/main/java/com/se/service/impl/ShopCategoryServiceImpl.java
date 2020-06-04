package com.se.service.impl;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 8:44 2020/5/29
 * @Modified by:
 */

import com.se.dao.ShopCategoryDao;
import com.se.domain.ShopCategory;
import com.se.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname: ShopCategoryServiceImpl
 * @Description: 店铺种类
 * @Author: Qi weidong
 * @Date: 2020/5/29 8:44
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
     private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}
