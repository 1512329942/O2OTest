package com.se.dto;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 10:45 2020/5/27
 * @Modified by:
 */

import com.se.domain.Shop;
import com.se.enums.ShopStateEnum;

import java.util.List;

/**
 * @Classname: ShopExecution
 * @Description: 操作店铺返回的状态
 * @Author: Qi weidong
 * @Date: 2020/5/27 10:45
 */
public class ShopExecution {
    //结果状态
    private int state;
    //状态标示
    private String stateInfo;
    //店铺的数量
    private int count;

    //操作的shop(增删改时)
    private Shop shop;
    //shop列表（查询shop列表的时候使用）
    private List<Shop> shopList;

    public ShopExecution(){

    }
    //店铺操作失败的构造器
    public ShopExecution(ShopStateEnum stateEnum){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
    }
    //店铺操作成功的构造器
    public ShopExecution(ShopStateEnum stateEnum,Shop shop){
        this.state= stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
        this.shop=shop;
    }
    //成功的构造器
    public ShopExecution(ShopStateEnum stateEnum,Shop shop,List<Shop> shopList){
        this.state= stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
        this.shopList=shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
