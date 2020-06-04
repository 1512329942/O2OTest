package testMybatis;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 8:33 2020/5/29
 * @Modified by:
 */

import com.se.dao.ShopCategoryDao;
import com.se.domain.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Classname: ShopCategoryDaoTest
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/29 8:33
 */
public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public void testQueryShopCategory(){
        List<ShopCategory> shopCategoryList=shopCategoryDao.queryShopCategory(new ShopCategory());
        for(ShopCategory shopCategory:shopCategoryList){

        }
    }
}
