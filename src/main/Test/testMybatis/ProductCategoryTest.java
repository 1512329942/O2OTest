package testMybatis;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 9:39 2020/5/31
 * @Modified by:
 */

import com.se.dao.ProductCategoryDao;
import com.se.domain.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname: ProductCategoryTest
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/31 9:39
 */
public class ProductCategoryTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    /**
     * queryProductCategory
     */
    @Test
    public void testQueryProductCategory(){
        List<ProductCategory> productCategoryList=productCategoryDao.queryProductCategoryList(20L);
        for(ProductCategory productCategory:productCategoryList){
            System.out.println(productCategory.getProductCategoryId());
        }
    }

    /**
     * batchInsertProductCategory
     */
    @Test
    public void testBatchInsertProductCategory(){

        ProductCategory productCategory=new ProductCategory();
        productCategory.setShopId(17L);
        productCategory.setProductCategoryName("三手书籍");
        productCategory.setPriority(20);
        productCategory.setCreateTime(new Date());

        ProductCategory productCategory2=new ProductCategory();
        productCategory2.setShopId(16L);
        productCategory2.setProductCategoryName("四手书籍");
        productCategory2.setPriority(20);
        productCategory2.setCreateTime(new Date());

        List<ProductCategory> list=new ArrayList<>();
        list.add(productCategory);
        list.add(productCategory2);
        System.out.println(list.size());
        int i = productCategoryDao.batchInsertProductCategory(list);
        System.out.println("影响的行数："+i);
    }

    /**
     * deleteProductCategory
     */
    @Test
    public void testDeleteProductCategory(){
        int i=productCategoryDao.deleteProductCategory(33L,16L);
        System.out.println(i);
    }
}
