package com.dshqwbabasmdsncx.fjdfj.andinjiedaiapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dshqwbabasmdsncx.fjdfj.net.XApi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class HttpApiAnXinJieDai {
    public static final String ZCXY = "https://bones2.jhgujkg.com/profile/opxfq/zcxy.html";
    public static final String YSXY= "https://bones2.jhgujkg.com/profile/opxfq/ysxy.html";
    public static String HTTP_API_URL = "http://202.52.144.94:7759";

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap ScaleBitmap(byte[] bytes, int width, int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bitmap, int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }

    private static InterfaceAnXinJieDaiUtils interfaceAnXinJieDaiUtils;

    public static InterfaceAnXinJieDaiUtils getInterfaceUtils() {
        if (interfaceAnXinJieDaiUtils == null) {
            synchronized (HttpApiAnXinJieDai.class) {
                if (interfaceAnXinJieDaiUtils == null) {
                    interfaceAnXinJieDaiUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(InterfaceAnXinJieDaiUtils.class);
                }
            }
        }
        return interfaceAnXinJieDaiUtils;
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap zzfdhrtu(byte[] bytes, int width, int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap mmnsfgujsrtu(Bitmap bitmap, int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }
}
