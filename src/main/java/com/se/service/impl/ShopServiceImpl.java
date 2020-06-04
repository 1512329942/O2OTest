package com.se.service.impl;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 11:17 2020/5/27
 * @Modified by:
 */

import com.se.dao.ShopDao;
import com.se.domain.Shop;
import com.se.dto.ImageHolder;
import com.se.dto.ShopExecution;
import com.se.enums.ShopStateEnum;
import com.se.service.ShopService;
import com.se.util.ImageUtil;
import com.se.util.PageCalculator;
import com.se.util.PathUtil;
import com.se.exceptions.ShopOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

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

    /**
     * 根据shopCondtiop分页返回相应的店铺列表
     * 这里是pageIndex,但是dao层是rowIndex
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex= PageCalculator.calculRowIndex(pageIndex,pageSize);

        List<Shop> shopList=shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count=shopDao.queryShopCount(shopCondition);
        ShopExecution se=new ShopExecution();
        if (shopList!=null){
            se.setShopList(shopList);
            se.setCount(count);
        }else{
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }


    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
      /*  InputStream shopImgInputStream, String fileName*/
        if(shop==null&&shop.getShopId()==null&&"".equals(thumbnail.getImageName())){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else{
            //1.判断是否要处理图片
            try{
                if(thumbnail.getImage()!=null){
                    Shop tempShop=shopDao.queryByShopId(shop.getShopId());
                    if(tempShop.getShopImg()!=null){
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addshopImgInputStream(shop,thumbnail);
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum=shopDao.updateShop(shop);
                if(effectedNum<=0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);

                }else{
                    shop=shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }

            }catch (Exception ex){
                throw new ShopOperationException("modifyShop err:"+ ex.getMessage());
            }

        }

    }


    @Override
    @Transactional//事务控制
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
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
                if(thumbnail.getImage()!=null){
                    //存储图片
                    try{
                        addshopImgInputStream(shop,thumbnail);
                    }catch(Exception e){
                        throw new ShopOperationException("addshopImgInputStream error:"+e.toString());
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

    /**
     * 生成新的图片
     * @param shop
     * @param
     * @param
     */
    private void addshopImgInputStream(Shop shop,ImageHolder thumbnail) {
        //获取shop图片目录的相对路径
        String dest= PathUtil.getShopImagePath(shop.getShopId());

        String shopImgInputStreamAddr= ImageUtil.generateThumbnail(thumbnail,dest);
        System.out.println("lastRela:"+shopImgInputStreamAddr);
        shop.setShopImg(shopImgInputStreamAddr);
    }
}
