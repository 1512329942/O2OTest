package com.se.service;

import com.se.domain.Shop;
import com.se.dto.ShopExecution;
import com.se.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 11:16 2020/5/27
 * @Modified by:
 */
public interface ShopService {
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) throws ShopOperationException;
}
