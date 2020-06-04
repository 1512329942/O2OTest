package TestSpring;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 9:42 2020/6/2
 * @Modified by:
 */

import com.se.domain.Product;
import com.se.domain.ProductCategory;
import com.se.domain.Shop;
import com.se.dto.ImageHolder;
import com.se.dto.ProductExecution;
import com.se.enums.ProductStateEnum;
import com.se.exceptions.ShopOperationException;
import com.se.service.ProductService;
import jdk.internal.util.xml.impl.Input;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname: ProductTest
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/6/2 9:42
 */

public class ProductTest extends BaseTest {
    @Autowired
    private ProductService productService;
    @Test
    public void testAddProduct() throws ShopOperationException, FileNotFoundException {
        Product product=new Product();
        Shop shop=new Shop();
        shop.setShopId(16L);
        ProductCategory productCategory=new ProductCategory();
        productCategory.setProductCategoryId(10L);

        product.setProductCategory(productCategory);
        product.setShop(shop);
        product.setProductName("测试商品2");
        product.setProductDesc("测试商品2");
        product.setPriority(6);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        //
        File thumbnailFile=new File("F:\\校园商铺\\第26项目：SSM到Spring Boot-校园商铺平台\\images\\item\\shop\\27\\2017060715512185473.jpg");
        InputStream is=new FileInputStream(thumbnailFile);
        ImageHolder imageHolder=new ImageHolder(thumbnailFile.getName(),is);
        //
        File productImg1=new File("F:\\校园商铺\\第26项目：SSM到Spring Boot-校园商铺平台\\images\\item\\shop\\27\\2017060715512185473.jpg");
        InputStream inputStream1=new FileInputStream(productImg1);
        File productImg2=new File("F:\\校园商铺\\第26项目：SSM到Spring Boot-校园商铺平台\\images\\item\\shop\\27\\2017060715512185473.jpg");
        InputStream inputStream2=new FileInputStream(productImg2);
        List<ImageHolder> productImgList=new ArrayList<>();

        productImgList.add(new ImageHolder(productImg1.getName(),inputStream1));
        productImgList.add(new ImageHolder(productImg2.getName(),inputStream2));

        ProductExecution pe=productService.addProduct(product,imageHolder,productImgList);
        System.out.println(pe.getState());;


    }
    @Test
    public void testModifyProduct() throws ShopOperationException,FileNotFoundException{
        Product product=new Product();
        Shop shop=new Shop();
        shop.setShopId(15L);
        ProductCategory productCategory=new ProductCategory();
        productCategory.setProductCategoryId(9L);
        product.setProductId(20L);
        product.setProductName("234234");
        product.setProductDesc("sdafsdfas");
        product.setShop(shop);
        product.setProductCategory(productCategory);
        //创建文件流
        File thumbnailFile=new File("F:\\校园商铺\\第26项目：SSM到Spring Boot-校园商铺平台\\images\\item\\shop\\27\\2017060715512185473.jpg");
        InputStream is=new FileInputStream(thumbnailFile);
        ImageHolder thumbnail=new ImageHolder(thumbnailFile.getName(),is);

        File productImg1=new File("F:\\校园商铺\\第26项目：SSM到Spring Boot-校园商铺平台\\images\\item\\shop\\27\\2017060715512185473.jpg");
        InputStream is1=new FileInputStream(productImg1);
        ImageHolder thumbanail1=new ImageHolder(productImg1.getName(),is1);

        File productImg2=new File("F:\\校园商铺\\第26项目：SSM到Spring Boot-校园商铺平台\\images\\item\\shop\\27\\2017060715512185473.jpg");
        InputStream is2=new FileInputStream(productImg2);
        ImageHolder thumbnail2=new ImageHolder(productImg2.getName(),is2);
        List<ImageHolder> info=new ArrayList<>();
        info.add(thumbanail1);
        info.add(thumbnail2);
        ProductExecution productExecution = productService.modifyProduct(product, thumbnail, info);
        System.out.println(productExecution.getState());
    }
}
