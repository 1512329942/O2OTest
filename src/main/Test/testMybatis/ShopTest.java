package testMybatis;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 0:09 2020/5/27
 * @Modified by:
 */

import com.se.dao.ShopDao;
import com.se.domain.Area;
import com.se.domain.PersonInfo;
import com.se.domain.Shop;
import com.se.domain.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Classname: ShopTest
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/27 0:09
 */
public class ShopTest extends BaseTest{
    @Autowired
    private ShopDao  shopDao;
    /**
     * insertShop
     */
    @Test
    public void testInsertShop(){
        Shop shop=new Shop();
        PersonInfo owner=new PersonInfo();
        Area area=new Area();
        ShopCategory shopCategory=new ShopCategory();
        owner.setUserId(8L);
        area.setAreaId(3);
        shopCategory.setShopCategoryId(14L);

        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setOwner(owner);
        shop.setShopName("测试的店铺1");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核");
        int effectedNum=shopDao.insertShop(shop);
        System.out.println(effectedNum);
    }

    /**
     * updateShop
     */
    @Test
    public void testUpdateShop(){
        Shop shop=new Shop();
        shop.setShopId(30L);
        shop.setShopName("测试的店铺2");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核2");
        int effectedNum=shopDao.updateShop(shop);
        System.out.println(effectedNum);
    }
    /**
     * queryShopById
     */
    @Test
    public void testQueryShopById(){
        long shopId=27L;
        Shop shop =shopDao.queryByShopId(shopId);
        System.out.println(shop.getShopId()+":"+shop.getShopName());
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop.getShopCategory().getShopCategoryName());
    }

    /**
     * queryShopList
     * queryShopCount
     */
    @Test
    public void testQueryShopListAndCount(){
        Shop shopCondition =new Shop();
        PersonInfo owner=new PersonInfo();
        owner.setUserId(8L);
        shopCondition.setOwner(owner);

        ShopCategory sc=new ShopCategory();
        sc.setShopCategoryId(14L);
        shopCondition.setShopCategory(sc);
        shopCondition.setShopName("正式的店铺名字");
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        System.out.println("店铺列表的大小"+shopList.size());

        int shopList2=shopDao.queryShopCount(shopCondition);
        System.out.println("店铺的总数:"+shopList2);
    }

}
