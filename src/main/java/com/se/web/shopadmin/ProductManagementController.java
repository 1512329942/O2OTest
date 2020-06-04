package com.se.web.shopadmin;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 10:12 2020/6/2
 * @Modified by:
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.dao.ProductCategoryDao;
import com.se.domain.Product;
import com.se.domain.ProductCategory;
import com.se.domain.Shop;
import com.se.dto.ImageHolder;
import com.se.dto.ProductExecution;
import com.se.enums.ProductStateEnum;
import com.se.service.ProductCategoryService;
import com.se.service.ProductService;
import com.se.util.CodeUtil;
import com.se.util.HttpServletRequestUtil;
import com.se.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @Classname: ProductManagementController
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/6/2 10:12
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;
    //支持上传商品详情图的最大数据
    private static final int IMAGEMAXCOUNT = 6;

    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //是商品编辑时候调用，还是上下架操作的时候调用
        //如果是前者就进行验证码判断，如果是后者就跳过
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        //验证码判断
        if (!statusChange &&!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //接受前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper=new ObjectMapper();
        Product product=null;
        ImageHolder thumbnail=null;
        List<ImageHolder> productImgList=new ArrayList<>();
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        //如果请求中存在文件流就就收文件,
        try{
            if(commonsMultipartResolver.isMultipart(request)){
               thumbnail=handleImage((MultipartHttpServletRequest)request,productImgList);
            }
        }catch (Exception ex){
            modelMap.put("success",false);
            modelMap.put("errMsg", ex.toString());
            return modelMap;
        }
        //接受从前端传来的表单string流并将其转换成product实体类
        try{
            String productStr=HttpServletRequestUtil.getString(request,"productStr");
            product=mapper.readValue(productStr,Product.class);
        }catch(Exception ex){
            modelMap.put("success",false);
            modelMap.put("errMsg", ex.toString());
            return modelMap;
        }
        //更新product数据库
        if(product!=null){
            try{
                Shop currentShop=(Shop)request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution pe=productService.modifyProduct(product,thumbnail,productImgList);
                if(pe.getState()==ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }
            }catch (Exception ex){
                modelMap.put("success",false);
                modelMap.put("errMsg", ex.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入商品信息");
        }
        return modelMap;
    }

    /**
     * 根据productid获取商品信息，以及获取商品种类列表
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            //获取该店铺下的商品类别列表
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(
                    productId
            );
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }


    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //接受前端变量的初始化，包括商品，缩略图，详情图
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartHttpServletRequest = null;
        ImageHolder thumbnail = null;

        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        try {
            //若文件中有文件流，就取出相关文件
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage((MultipartHttpServletRequest) request, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传的商品图片为空");
                return modelMap;
            }

        } catch (Exception ex) {
            modelMap.put("success", false);
            modelMap.put("errMsg", ex.toString());
            return modelMap;
        }
        try {
            //之前完成了前端传来的表单String流 并将其转换成product实体类
            product = mapper.readValue(productStr, Product.class);

        } catch (Exception ex) {
            modelMap.put("success", false);
            modelMap.put("errMsg", ex.toString());
            return modelMap;
        }

        //若Product信息，缩略图以及详情图列表不为空，就开始进行商品的添加操作
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                //从session中获取当前店铺的的id并赋值给product,减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                //执行添加商品图片操作
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception ex) {
                modelMap.put("success", true);
                modelMap.put("errMsg", ex.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");

        }
        return modelMap;
    }

    private ImageHolder handleImage(MultipartHttpServletRequest request,  List<ImageHolder> productImgList) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest;
        ImageHolder thumbnail;
        multipartHttpServletRequest = request;
        //取出所缩略图并构建ImageHulder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");

        thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());

        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                ImageHolder productImg = null;

                productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());

                productImgList.add(productImg);
            } else {
                //取出的文件流流为空，就中止循环
                break;
            }
        }
        return thumbnail;
    }
}
