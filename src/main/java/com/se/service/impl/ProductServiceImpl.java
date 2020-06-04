package com.se.service.impl;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 11:54 2020/6/1
 * @Modified by:
 */

import com.se.dao.ProductDao;
import com.se.dao.ProductImgDao;
import com.se.domain.Product;
import com.se.domain.ProductImg;
import com.se.dto.ImageHolder;
import com.se.dto.ProductExecution;
import com.se.enums.ProductStateEnum;
import com.se.exceptions.ProductOperationException;
import com.se.service.ProductService;
import com.se.util.ImageUtil;
import com.se.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname: ProductServiceImpl
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/6/1 11:54
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;


    /**
     * 1.处理缩略图，获取缩略图相对路径并赋值给product
     * 2.向tb_product写入商品信息，获取productid
     * 3.结合productid批量处理商品详情图
     * 4.将商品详情图列表批量插入tb_product_img中
     *
     * @param product
     * @param thumbnail
     * @param
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional
    public ProductExecution addProduct(Product product,
                                       ImageHolder thumbnail,
                                       List<ImageHolder> productImgHolderList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品加上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认上架状态
            product.setEnableStatus(1);
            //如果商品缩略图不是空，就添加
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                //创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception ex) {
                throw new ProductOperationException("创建商品失败 errMsg：" + ex.toString());
            }
            //若商品详情图不为空就添加
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                addProductImgList(product, productImgHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {

        }

        return null;
    }

    @Override
    public Product getProductById(long productId) {

        return productDao.queryProductById(productId);
    }

    /**
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional
    /**
     * 1.若缩略图有参数，就处理缩略图，若原先存在缩略图，就先删除所有缩略图，然后新添加，之后获取缩略图
     * 并赋值给product
     * 2.若商品详情图有参数，同样
     * 3.将tb_product_img下面的该商品原先的商品详情图记录删除
     * 4.更新tb_product信息
     */
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(new Date());
            //如果缩略图不空，就删除原来的所有，并添加新的
            if(thumbnail!=null){
                Product tempProduct=productDao.queryProductById(product.getProductId());
                if(tempProduct.getImgAddr()!=null){
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product,thumbnail);
            }
            //如果有新存入的商品详情图，就删除原来的，并添加图片
            if(productImgHolderList!=null&&productImgHolderList.size()>0){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgHolderList);
            }
            try{
                int effectedNum=productDao.updateProduct(product);
                if (effectedNum<=0){
                    throw new ProductOperationException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS,product);
            }catch (Exception ex){
                throw new ProductOperationException("更新商品信息失败"+ex.toString());
            }
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }


    }

    /**
     * 根据productid删除productImg里的所有的图片，及路进下的所有图片
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        List<ProductImg> productImgList=productImgDao.queryProductImgList(productId);
        for(ProductImg productImg:productImgList){
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //删除数据库里原有图片的信息
        productImgDao.deleteProductImgByProductId(productId);
    }

    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        //获取图片存储路径，这里直接存放到相应店铺的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        //遍历一次图片就处理一次，并添加进productimg实体类中
        for (ImageHolder imageHolder : productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(imageHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        //如果有图片需要添加，就执行批量添加
        if (productImgList.size() > 0) {
            try {
                int effectNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectNum <= 0) {
                    throw new ProductOperationException("创建商品详情图片失败！");
                }
            } catch (Exception ex) {
                throw new ProductOperationException("创建商品详情失败：" + ex.toString());
            }
        }

    }

    /**
     * 添加商品的缩略图
     * 文件的文件名和文件流
     */
    public void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thunbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thunbnailAddr);
    }


}
