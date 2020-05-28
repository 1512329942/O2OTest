package com.se.web.shopadmin;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 8:58 2020/5/28
 * @Modified by:
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.domain.PersonInfo;
import com.se.domain.Shop;
import com.se.dto.ShopExecution;
import com.se.enums.ShopStateEnum;
import com.se.service.ShopService;
import com.se.util.HttpServletRequestUtil;
import com.se.util.ImageUtil;
import com.se.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname: ShopManagermentController
 * @Description: 店铺
 * @Author: Qi weidong
 * @Date: 2020/5/28 8:58
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagermentController {
    @Autowired
    private ShopService shopService;

    /**
     * 1.接受并转换参数，包括店铺信息及其图片
     * 2.注册店铺
     * 3.返回结果
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    private Map<String,Object> registerShop(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        //1.
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper=new ObjectMapper();
        Shop shop=null;
        try{
            shop=mapper.readValue(shopStr,Shop.class);
        }catch (Exception ex){
            modelMap.put("success",false);
            modelMap.put("errMsg", ex.toString());
            return modelMap;
        }
        //spring自带的
        CommonsMultipartFile shopImg=null;
        //文件解析器
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        if (commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
            shopImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");

        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        //2.
        if(shop!=null&&shopImg!=null){
            PersonInfo owner=new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);

            ShopExecution se= null;
            try {
                se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                if (se.getState()== ShopStateEnum.CHECK.getState()){
                    modelMap.put("success",true);

                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success",true);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

            return modelMap;
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
        //3.返回结果
    }



    /*public static void inputStreamToFile(InputStream ins, File file){
        FileOutputStream os=null;
        try{
            os=new FileOutputStream(file);
            int bytesRead=0;
            byte[] buffer=new byte[1024];
            while((bytesRead=ins.read(buffer))!=-1){
                os.write(buffer,0,bytesRead);
            }
        }catch (Exception ex){
            throw new RuntimeException("调用inputStreamToFile异常："+ex.toString());
        }finally {
            try{
                if (os!=null){
                    os.close();
                }
                if (ins!=null){
                    ins.close();
                }
            }catch (IOException exe){
                throw new RuntimeException("inputStreamToFile关闭流异常："+exe.toString());
            }
        }
    }*/
}
