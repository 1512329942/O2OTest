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

/**
 * @Classname: ShopTest
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/27 0:09
 */
public class ShopTest extends BaseTest{
    @Autowired
    private ShopDao  shopDao;
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
}
