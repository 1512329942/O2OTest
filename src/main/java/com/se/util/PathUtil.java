package com.se.util;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 11:51 2020/5/27
 * @Modified by:
 */

/**
 * @Classname: PathUtil
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/27 11:51
 */
public class PathUtil {
    //获取系统文件的分割符
    private static String seperator=System.getProperty("file.separator");

    /**
     * 获取项目图片的根路径
     * @return
     */
    public static String getImgBasePath(){
        String os=System.getProperty("os.name");
        String basePath="";
        if(os.toLowerCase().startsWith("win")){
            basePath="D:/project3/image/";
        }else{
            basePath="/home/qiweidong/image/";
        }
        basePath=basePath.replace("/",seperator);
        return basePath;
    }

    /**
     * 获取店铺的图片路径
     * 调用了
     * @param shopId
     */
    public static String getShopImagePath(long shopId){
        String imagePath="/upload/item/shop/"+shopId+"/";

        String s=imagePath.replace("/",seperator);
        return s;
    }
}
