package com.nfhyrhd.nfhsues.fqbyjpresent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

import com.nfhyrhd.nfhsues.fqbyjmodel.BaseRespModelFQBYJ;
import com.nfhyrhd.nfhsues.fqbyjmodel.GoodsFQBYJModel;
import com.nfhyrhd.nfhsues.fqbyjutils.SharedPreferencesUtilisFQBYJ;
import com.nfhyrhd.nfhsues.fqbyjutils.FQBYJStaticUtil;
import com.nfhyrhd.nfhsues.mvp.XPresent;
import com.nfhyrhd.nfhsues.fqbyjnet.FQBYJApi;
import com.nfhyrhd.nfhsues.fqbyjnet.ApiSubscriber;
import com.nfhyrhd.nfhsues.fqbyjnet.NetError;
import com.nfhyrhd.nfhsues.fqbyjnet.XApi;
import com.nfhyrhd.nfhsues.fqbyjui.fqbyjfragment.FQBYJHomePageFragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;


public class HomePagePresentFQBYJ extends XPresent<FQBYJHomePageFragment> {

    private int mobileType;

    private String phone;

    public Bitmap handleGlassblur(Context context, Bitmap originBitmap, int radius){
        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript,originBitmap);
        Allocation output = Allocation.createTyped(renderScript,input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(originBitmap);

        return originBitmap;
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public Bitmap ScaleBitmap(byte[] bytes,int width,int height){
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
    public Bitmap compressBitmap(Bitmap bitmap, int size){
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

    public void productList() {
        mobileType = SharedPreferencesUtilisFQBYJ.getIntFromPref("mobileType");
        phone = SharedPreferencesUtilisFQBYJ.getStringFromPref("phone");
        getV().goodsFQBYJModel = null;
        FQBYJApi.getGankService().productList(mobileType, phone)
                .compose(XApi.<BaseRespModelFQBYJ<List<GoodsFQBYJModel>>>getApiTransformer())
                .compose(XApi.<BaseRespModelFQBYJ<List<GoodsFQBYJModel>>>getScheduler())
                .compose(getV().<BaseRespModelFQBYJ<List<GoodsFQBYJModel>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelFQBYJ<List<GoodsFQBYJModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().swipeRefreshLayout.setRefreshing(false);
                        if (getV().goodsItemAdapterFQBYJ == null) {
                            getV().noDataFl.setVisibility(View.VISIBLE);
                        }
                        FQBYJStaticUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelFQBYJ<List<GoodsFQBYJModel>> gankResults) {
                        getV().swipeRefreshLayout.setRefreshing(false);
                        if (gankResults != null) {
                            if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                    getV().setModel(gankResults.getData().get(0));
                                    getV().initGoodsItemAdapter(gankResults.getData());
                                } else {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            } else {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                        } else {
                            getV().noDataFl.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    public Bitmap fgfgjsrtu(Context context, Bitmap originBitmap, int radius){
        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript,originBitmap);
        Allocation output = Allocation.createTyped(renderScript,input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(originBitmap);

        return originBitmap;
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public Bitmap fghsdrtuwsrtu(byte[] bytes,int width,int height){
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
    public Bitmap rtufghsruy(Bitmap bitmap, int size){
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

    public void productClick(GoodsFQBYJModel model) {
        phone = SharedPreferencesUtilisFQBYJ.getStringFromPref("phone");
        FQBYJApi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModelFQBYJ>getApiTransformer())
                .compose(XApi.<BaseRespModelFQBYJ>getScheduler())
                .compose(getV().<BaseRespModelFQBYJ>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelFQBYJ>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        FQBYJStaticUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelFQBYJ gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public Bitmap nntyirstu(Context context, Bitmap originBitmap, int radius){
        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript,originBitmap);
        Allocation output = Allocation.createTyped(renderScript,input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(originBitmap);

        return originBitmap;
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public Bitmap easryfh(byte[] bytes,int width,int height){
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
    public Bitmap srthfgnfgj(Bitmap bitmap, int size){
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
