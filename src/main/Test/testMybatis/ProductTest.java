package testMybatis;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 10:49 2020/6/1
 * @Modified by:
 */

import com.se.dao.ProductDao;
import com.se.domain.Product;
import com.se.domain.ProductCategory;
import com.se.domain.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Classname: ProductTest
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/6/1 10:49
 */
public class ProductTest extends BaseTest{
    @Autowired
    private ProductDao productDao;
    @Test
    public void testInsertProduct(){
        Shop shop1=new Shop();
        shop1.setShopId(16L);
        ProductCategory productCategory=new ProductCategory();
        productCategory.setProductCategoryId(10L);

        Product product1=new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试desc1");
        product1.setImgAddr("test1");
        product1.setPriority(5);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(productCategory);
        int i = productDao.insertProduct(product1);
        System.out.println(i);
    }
    @Test
    public void testQueryProductById(){
        long productId=11L;
        Product product = productDao.queryProductById(productId);
        System.out.println(product.getProductName());
    }
    @Test
    public void testUpdateProduct(){
        Product product=new Product();
        ProductCategory pc=new ProductCategory();
        Shop shop=new Shop();
        shop.setShopId(16L);
        pc.setProductCategoryId(9L);
        product.setProductId(19L);
        product.setProductName("更改商品1");
        product.setShop(shop);
        product.setProductCategory(pc);
        int i = productDao.updateProduct(product);
        System.out.println(i);
    }
    @Test
    public void testQueryProductList(){
        Product productConditon=new Product();
        List<Product> productList=productDao.queryProductList(productConditon,0,3);
        System.out.println(productList.size());

        productConditon.setProductName("1");
        productList=productDao.queryProductList(productConditon,0,4);
        System.out.println(productList.size());

        int list=productDao.queryProductCount(productConditon);
        System.out.println(list);
    }
}
