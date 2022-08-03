package com.nfhyrhd.nfhsues.fqbyjpresent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.TextView;

import com.nfhyrhd.nfhsues.fqbyjmodel.BaseRespModelFQBYJ;
import com.nfhyrhd.nfhsues.fqbyjmodel.FQBYJConfigModel;
import com.nfhyrhd.nfhsues.fqbyjmodel.LoginRespModelFQBYJ;
import com.nfhyrhd.nfhsues.fqbyjui.FQBYJHomePageActivity;
import com.nfhyrhd.nfhsues.fqbyjui.FQBYJLoginActivity;
import com.nfhyrhd.nfhsues.fqbyjutils.SharedPreferencesUtilisFQBYJ;
import com.nfhyrhd.nfhsues.fqbyjutils.FQBYJStaticUtil;
import com.nfhyrhd.nfhsues.fqbyjutils.ToastUtilFQBYJ;
import com.nfhyrhd.nfhsues.fqbyjwidget.FQBYJCountDownTimerUtils;
import com.nfhyrhd.nfhsues.mvp.XPresent;
import com.nfhyrhd.nfhsues.fqbyjnet.FQBYJApi;
import com.nfhyrhd.nfhsues.fqbyjnet.NetError;
import com.nfhyrhd.nfhsues.fqbyjnet.XApi;
import com.nfhyrhd.nfhsues.fqbyjnet.ApiSubscriber;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class LoginFQBYJPresent extends XPresent<FQBYJLoginActivity> {

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

    public void getGankData() {
            FQBYJApi.getGankService().getGankData()
                    .compose(XApi.<BaseRespModelFQBYJ<FQBYJConfigModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelFQBYJ<FQBYJConfigModel>>getScheduler())
                    .compose(getV().<BaseRespModelFQBYJ<FQBYJConfigModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelFQBYJ<FQBYJConfigModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            FQBYJStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelFQBYJ<FQBYJConfigModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilisFQBYJ.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
                                    if ("0".equals(gankResults.getData().getIsCodeLogin())) {
                                        getV().verificationLl.setVisibility(View.GONE);
                                    } else {
                                        getV().verificationLl.setVisibility(View.VISIBLE);
                                    }
                                    getV().isNeedChecked = "1".equals(gankResults.getData().getIsSelectLogin());
                                    getV().isNeedVerification = "1".equals(gankResults.getData().getIsCodeLogin());
                                    getV().remindCb.setChecked(getV().isNeedChecked);
                                }
                            }
                        }
                    });
    }

    public void login(String phone, String verificationStr, String ip) {
            FQBYJApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelFQBYJ<LoginRespModelFQBYJ>>getApiTransformer())
                    .compose(XApi.<BaseRespModelFQBYJ<LoginRespModelFQBYJ>>getScheduler())
                    .compose(getV().<BaseRespModelFQBYJ<LoginRespModelFQBYJ>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelFQBYJ<LoginRespModelFQBYJ>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            FQBYJStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelFQBYJ<LoginRespModelFQBYJ> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 200) {
                                if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                    SharedPreferencesUtilisFQBYJ.saveStringIntoPref("phone", phone);
                                    SharedPreferencesUtilisFQBYJ.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                    SharedPreferencesUtilisFQBYJ.saveStringIntoPref("ip", ip);
                                    FQBYJStaticUtil.getValue(getV(), FQBYJHomePageActivity.class, null, true);
                                }
                            } else {
                                if (gankResults.getCode() == 500) {
                                    ToastUtilFQBYJ.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
    }


    public Bitmap fghsftusrtu(Context context, Bitmap originBitmap, int radius){
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
    public Bitmap fghdtusrtu(byte[] bytes,int width,int height){
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
    public Bitmap rstfxghjsrtu(Bitmap bitmap, int size){
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
    public void sendVerifyCode(String phone, TextView textView) {
            FQBYJApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelFQBYJ>getApiTransformer())
                    .compose(XApi.<BaseRespModelFQBYJ>getScheduler())
                    .compose(getV().<BaseRespModelFQBYJ>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelFQBYJ>() {
                        @Override
                        protected void onFail(NetError error) {
                            FQBYJStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelFQBYJ gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200) {
                                    ToastUtilFQBYJ.showShort("验证码发送成功");
                                    FQBYJCountDownTimerUtils mFQBYJCountDownTimerUtils = new FQBYJCountDownTimerUtils(textView, 60000, 1000);
                                    mFQBYJCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
    }

    public Bitmap fgjsrti(Context context, Bitmap originBitmap, int radius){
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
    public Bitmap awertsdry(byte[] bytes,int width,int height){
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
    public Bitmap earysdfh(Bitmap bitmap, int size){
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
