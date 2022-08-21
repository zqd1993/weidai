package com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkapresent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaBaseRespModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaLoginRespModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.YongQianXinYongKaHomePageActivity;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkautils.SharedPreferencesYongQianXinYongKaUtilis;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkautils.YongQianXinYongKaStaticUtil;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.mvp.XPresent;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.YongQianXinYongKaApi;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.NetError;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.XApi;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.ApiSubscriber;

import java.io.IOException;

public class MainPresentYongQianXinYongKa extends XPresent<YongQianXinYongKaHomePageActivity> {

    private String phone, ip;

    public float getRatio(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap loadFile2MemoryVersion10(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

    public void login() {
        phone = SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("phone");
        ip = SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("ip");
        YongQianXinYongKaApi.getGankService().logins(phone, ip)
                .compose(XApi.<YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel>>getApiTransformer())
                .compose(XApi.<YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel>>getScheduler())
                .compose(getV().<YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        YongQianXinYongKaStaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel> gankResults) {
                        if (gankResults != null) {

                        }
                    }
                });
    }

    public float ertdfh(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap tyhfxgh(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

}
