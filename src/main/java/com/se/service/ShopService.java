package com.se.service;

import com.se.domain.Shop;
import com.se.dto.ShopExecution;

import java.io.File;

/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 11:16 2020/5/27
 * @Modified by:
 */
public interface ShopService {
    ShopExecution addShop(Shop shop, File shopImg);
}
