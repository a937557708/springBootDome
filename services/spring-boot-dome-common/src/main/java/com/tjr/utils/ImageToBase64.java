package com.tjr.utils;

import lombok.Cleanup;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageToBase64 {
    /**
     * 　　* 网络图片转换Base64的方法
     * 　　* @param netImagePath
     *
     */
    private static String NetImageToBase64(String netImagePath) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(netImagePath);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.toByteArray());
    }
    /**
     　　* 本地图片转换Base64的方法
     　　* @param imgPath
     　　*/
    public static String ImageToBase64(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }
   /**
      * 对字节数组字符串进行Base64解码并生成图片
      * @param imgStr 图片数据
      * @param imgFilePath 保存图片全路径地址
      * @return
      */
   public static boolean generateImage(String imgStr, String imgFilePath) {
       //
       if (imgStr == null) {// 图像数据为空
           return false;
       }
       BASE64Decoder decoder = new BASE64Decoder();
       try {
           // Base64解码
           byte[] b = decoder.decodeBuffer(imgStr);
           for (int i = 0; i < b.length; ++i) {
               if (b[i] < 0) {// 调整异常数据
                   b[i] += 256;
               }
           }
           // 生成jpg图片
           @Cleanup
           OutputStream out = new FileOutputStream(imgFilePath);
           out.write(b);
           return true;
       } catch (Exception e) {
           return false;
       }
   }

}
