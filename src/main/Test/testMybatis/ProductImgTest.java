package testMybatis;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 10:31 2020/6/1
 * @Modified by:
 */

import com.se.dao.ProductImgDao;
import com.se.domain.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname: ProductImgTest
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/6/1 10:31
 */
public class ProductImgTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;
    /**
     * batchInsertProductImg
     */
    @Test
    public void testBatchInsertProductImg(){
        ProductImg productImg1=new ProductImg();
        productImg1.setImgAddr("图片");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(5L);

        ProductImg productImg2=new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试图片2");
        productImg2.setPriority(2);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(5L);

        List<ProductImg> productImgList=new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum=productImgDao.batchInsertProductImg(productImgList);
        System.out.println(effectedNum);
    }
    @Test
    public void testDeleteProductImgByProductId(){
        long productId=10L;
        int effectedNum=productImgDao.deleteProductImgByProductId(productId);
        System.out.println(effectedNum);
    }

    /**
     * queryProductImgList
     */
    @Test
    public  void testQueryProductImgList(){
        List<ProductImg> list=productImgDao.queryProductImgList(11L);
        for(ProductImg p: list){
            System.out.println(p.getProductImgId());
        }
    }
}
