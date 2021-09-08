package com.moqi.scheduleminiprogrambackend.util;


import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

@Data
@Component
public class OssClientUtil {

    public static final Logger logger = LoggerFactory.getLogger(OssClientUtil.class);
    // endpoint
    private static final String ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";
    // accessKey

    private static String accessKeyId;

    private static String accessKeySecret;
    // 空间
    private static final String BUCKET_NAME = "schedule-mini-program";
    // 文件存储目录
    private static final String filedir = "excel/";

    private OSSClient ossClient;

    //参数注入
    @Value(value = "${oss.access-key-id}")
    public void setAccessKeyId(String accessKeyId) {
        OssClientUtil.accessKeyId = accessKeyId;
    }

    @Value(value = "${oss.access-key-secret}")
    public void setAccessKeySecret(String accessKeySecret) {
        OssClientUtil.accessKeySecret = accessKeySecret;
    }

    public OssClientUtil() {

    }

    /**
     * 初始化
     */
    public void init() {
        ClientConfiguration clientConfiguration=new ClientConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTPS);
        ossClient = new OSSClient(ENDPOINT, accessKeyId, accessKeySecret,clientConfiguration);
    }

    /**
     * 销毁
     */
    public void destory() {
        ossClient.shutdown();
    }

    /**
     * 上传图片
     *
     * @param url
     * @throws ImgException
     */
    public String uploadImg2Oss(String url) throws ImgException {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("\\\\");
            this.uploadFile2OSS(fin, split[split.length - 1]);
            return split[split.length - 1];
        } catch (FileNotFoundException e) {
            throw new ImgException("图片上传失败");
        }
    }

    public String uploadImg2Oss(MultipartFile file) throws ImgException {
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new ImgException("上传图片大小不能超过10M！");
        }
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new ImgException("图片上传失败");
        }
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        System.out.println(fileUrl);
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(filedir + split[split.length - 1]);
        }
        return null;
    }

    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     *
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf(".")+1)));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(BUCKET_NAME, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg")
                || filenameExtension.equalsIgnoreCase("jpg")) {
            return "image/jpge";
        }
        if(filenameExtension.equalsIgnoreCase("png")){
            return "image/png";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10

        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(BUCKET_NAME, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    public String getUrlByName(String name){

        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(BUCKET_NAME, filedir+name, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    /**
     * 删除Oss中某个文件
     * @param name 文件名称
     */
    public void delete(String name){
        ossClient.deleteObject(BUCKET_NAME,filedir+name);
    }

    public String uploadExcel2Oss(InputStream instream, String fileName){
        String ret = "";
        try {
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType("application/ms-excel");
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(BUCKET_NAME, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

   /*public static void main(String[] args) {
        String url="C:\\Users\\moqi\\Pictures\\pictures\\3.png";
        OssClientUtil ossClient=new OssClientUtil();
        String name=ossClient.uploadImg2Oss(url);
        System.out.println(ossClient.getImgUrl(name));
        ossClient.delete("3.png");
    }*/
}
