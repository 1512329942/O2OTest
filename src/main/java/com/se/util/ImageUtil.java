package com.se.util;
/**
 * @Author: Qi Weidong
 * @Description:
 * @Date: Create in 10:01 2020/5/27
 * @Modified by:
 */

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Classname: ImageUtil
 * @Description: TODO
 * @Author: Qi weidong
 * @Date: 2020/5/27 10:01
 */
public class ImageUtil {
    private String basePath="";
    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r=new Random();
    private static Logger logger= LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 将Comm转换成File
     * @param cFile
     * @return
     */
    private static File transferCommonsMultipartFile(CommonsMultipartFile cFile){
        File newFile=new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }
    /**
     * @Author: Qi Weidong
     * @Description: 处理缩略图，返回新生成的图片路径
     * @Date: 14:25 2020/5/27
     * @Params: [thumbnail, targetAddr]
     * @return: java.lang.String
     */
    public static String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr){
        String realFileName=getRandomFileName();//随机文件名
        String extension=getFileExtension(fileName);//文件扩展名
        makeDirPath(targetAddr);//创建目录
        //相对路径
        String relativeAddr=targetAddr+realFileName+extension;//相对路径+文件名

        logger.debug("current ralativeAddr is:"+relativeAddr);
        logger.debug("current complete Addr is:"+PathUtil.getImgBasePath()+relativeAddr);
        File dest=new File(PathUtil.getImgBasePath()+relativeAddr);//绝对路径
        System.out.println(dest.getAbsolutePath());
        try{
            Thumbnails.of(thumbnailInputStream).size(200,200).watermark(Positions.BOTTOM_RIGHT, ImageIO.
                    read(new File("F:\\校园商铺\\第26项目：SSM到Spring Boot-校园商铺平台\\images\\item\\watermark.jpg")),0.25f).
                    outputQuality(0.8f).toFile(dest);
        }catch (Exception e){
            logger.error(e.toString());
            e.toString();
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及的目录，就是D://,那么自动创建路径
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath=PathUtil.getImgBasePath()+targetAddr;
        File dirPath=new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {

        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机的文件，当前年月日+小时分+5位随机数
     * @return
     */
    public  static String getRandomFileName() {
        int rannum=r.nextInt(89999)+10000;
        String nowTimeStr=simpleDateFormat.format(new Date());
        return  nowTimeStr+rannum;
    }

    public static void main(String[] args) throws IOException {
        String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(basePath+"watermark.jpg");
        Thumbnails.of(new File("F:\\校园商铺\\第26项目：SSM到Spring Boot-校园商铺平台\\images\\item\\shop\\15\\2017060523302118864.jpg"))
                .size(200,200).watermark(Positions.BOTTOM_RIGHT, ImageIO.
                read(new File(basePath+"watermark.jpg")),0.25f).outputQuality(0.8f).
        toFile("F:\\校园商铺\\第26项目：SSM到Spring Boot-校园商铺平台\\images\\item\\shop\\15\\2017060523302118864new.jpg");
    }

}
