package com.se.web;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 12:31 2020/5/26
 * @Modified by:
 */

import com.se.domain.Area;
import com.se.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname: TestController
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/26 12:31
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AreaService areaService;


    @RequestMapping(value = "testArea" ,method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> test(){
        Map<String,Object> modelMap=new HashMap<>();
        List<Area> list=new ArrayList<>();
        try{
            list=areaService.getAreaList();
            modelMap.put("rows",list);
            modelMap.put("total",list.size());
        }catch (Exception ex){
            ex.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg", ex.toString());
        }
        return modelMap;
    }
}
