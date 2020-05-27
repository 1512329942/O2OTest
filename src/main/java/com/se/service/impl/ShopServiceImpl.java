package com.se.service.impl;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 11:17 2020/5/27
 * @Modified by:
 */

import com.se.dao.ShopDao;
import com.se.domain.Shop;
import com.se.dto.ShopExecution;
import com.se.enums.ShopStateEnum;
import com.se.service.ShopService;
import com.se.util.ImageUtil;
import com.se.util.PathUtil;
import com.se.exceptions.ShopOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

/**
 * @Classname: ShopServiceImpl
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/27 11:17
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;


    @Override
    @Transactional//事务控制
    public ShopExecution addShop(Shop shop, File shopImg) {
        //空值判断
        if (shop==null){

            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try{

            //给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //调用dao层添加店铺信息
            int effectedNum=shopDao.insertShop(shop);

            if(effectedNum<=0){
                throw new ShopOperationException("店铺创建失败");
                /**
                 * 事务终止并且回滚，如果是exception
                 * 不会回滚
                 */
            }else{
                if(shopImg!=null){
                    //存储图片
                    try{
                        addShopImg(shop,shopImg);
                    }catch(Exception e){
                        throw new ShopOperationException("addShopImg error:"+e.toString());
                    }

                    effectedNum=shopDao.updateShop(shop);
                    if (effectedNum<=0){
                            throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        }catch(Exception e){
            throw new ShopOperationException("addShop Error:"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, File shopImg) {
        //获取shop图片目录的相对路径
        String dest= PathUtil.getShopImagePath(shop.getShopId());

        String shopImgAddr= ImageUtil.generateThumbnail(shopImg,dest);
        System.out.println("lastRela:"+shopImgAddr);
        shop.setShopImg(shopImgAddr);
    }
}
