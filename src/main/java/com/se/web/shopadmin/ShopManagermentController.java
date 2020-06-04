package com.se.web.shopadmin;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 8:58 2020/5/28
 * @Modified by:
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.domain.Area;
import com.se.domain.PersonInfo;
import com.se.domain.Shop;
import com.se.domain.ShopCategory;
import com.se.dto.ImageHolder;
import com.se.dto.ShopExecution;
import com.se.enums.ShopStateEnum;
import com.se.service.AreaService;
import com.se.service.ShopCategoryService;
import com.se.service.ShopService;
import com.se.util.CodeUtil;
import com.se.util.HttpServletRequestUtil;
import com.se.util.ImageUtil;
import com.se.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getManagementInfo(HttpServletRequest request){
        System.out.println("getshopmanagementinfo执行了");
        Map<String,Object> modelMap=new HashMap<>();
        long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId<=0){
            Object currentShopObj=request.getSession().getAttribute("currentShop");
            if(currentShopObj==null){
                modelMap.put("redirect",true);
                modelMap.put("url","/o2oTest1_war_exploded/shopadmin/shoplist");
            }else{
                Shop currentShop=(Shop)currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        }else{
            Shop currentShop=new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }



    /**
     * 从session域中获取user,根据user获取所有的店铺
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    private Map<String, Object> getShopList(HttpServletRequest request){
        Map<String, Object> modelMap=new HashMap<>();

        PersonInfo user=new PersonInfo();

        user.setUserId(8L);
        user.setName("qiweidon");
        request.getSession().setAttribute("user",user);

        user=(PersonInfo)request.getSession().getAttribute("user");

        try{
            Shop shopCondition=new Shop();
            shopCondition.setOwner(user);
            ShopExecution shopExecution=shopService.getShopList(shopCondition,0,100);
            modelMap.put("shopList",shopExecution.getShopList());
            modelMap.put("user",user);
            modelMap.put("success",true);
        }catch (Exception ex){
            modelMap.put("success",false);
            modelMap.put("errMsg", ex.getMessage());
        }
        return modelMap;
    }


    /**
     * 更改店铺信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    private Map<String,Object> modifyShop(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误 的验证码");
            return modelMap;
        }

        //1.
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        //jackson将json转换成对象
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

        }
        //2.修改店铺
        if(shop!=null&&shop.getShopId()!=null){
            PersonInfo owner=new PersonInfo();

            //session（1L）
            owner.setUserId(1L);
            shop.setOwner(owner);

            ShopExecution se= null;
            try {
                if(shopImg==null){
                    se = shopService.modifyShop(shop,null);
                }else{
                    ImageHolder imageHolder2=new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                    se = shopService.modifyShop(shop,imageHolder2);
                }

                if (se.getState()== ShopStateEnum.SUCCESS.getState()){
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
            modelMap.put("errMsg", "请输入店铺id");
            return modelMap;
        }
        //3.返回结果
    }



    /**
     * 根据shopid获取店铺的信息，同时还会获取所有的area
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    private Map<String,Object> getShopById(HttpServletRequest request){
        Map<String ,Object> modelMap=new HashMap<>();
        Long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId>-1){
            try{
                Shop shop=shopService.getByShopId(shopId);
                List<Area> areaList=areaService.getAreaList();
                request.getSession().setAttribute("currentShop",shop);
                modelMap.put("shop",shop);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            }catch (Exception ex){
                modelMap.put("success",false);
                modelMap.put("errMsg", ex.toString());
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }


    /**
     * 获取店铺注册页面的 初始化信息
     * @return
     */
    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopInitInfo(){
        Map<String,Object> modelMap=new HashMap<>();
        List<ShopCategory> shopCategoryList=new ArrayList<>();
        List<Area> areaList=new ArrayList<>();
        try{
            //获取所有shopcategory
            shopCategoryList=shopCategoryService.getShopCategoryList(new ShopCategory());
            //获取所有区域信息
            areaList=areaService.getAreaList();
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }catch (Exception ex){
            modelMap.put("success",false);
            modelMap.put("errMsg", ex.getMessage());
        }
        return modelMap;
    }

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
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误 的验证码");
            return modelMap;
        }

        //1.
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        //jackson将json转换成对象
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
            PersonInfo owner=(PersonInfo) request.getSession().getAttribute("user");

            shop.setOwner(owner);

            ShopExecution se= null;
            try {
                ImageHolder imageHolder=new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                se = shopService.addShop(shop,imageHolder);
                if (se.getState()== ShopStateEnum.CHECK.getState()){
                    modelMap.put("success",true);
                    //该用户可以操作的店铺列表
                    List<Shop> shopList=(List<Shop>) request.getSession().getAttribute("shopList");
                    if(shopList==null||shopList.size()==0){
                        shopList=new ArrayList<>();

                    }
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList",shopList);

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
