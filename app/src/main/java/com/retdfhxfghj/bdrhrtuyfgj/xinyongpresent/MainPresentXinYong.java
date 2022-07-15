package com.retdfhxfghj.bdrhrtuyfgj.xinyongpresent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.XinYongBaseRespModel;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.XinYongLoginRespModel;
import com.retdfhxfghj.bdrhrtuyfgj.ui.XinYongHomePageActivity;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongutils.SharedPreferencesXinYongUtilis;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongutils.XinYongStaticUtil;
import com.retdfhxfghj.bdrhrtuyfgj.mvp.XPresent;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.XinYongApi;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.NetError;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.XApi;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.ApiSubscriber;

import java.io.IOException;

public class MainPresentXinYong extends XPresent<XinYongHomePageActivity> {

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
        phone = SharedPreferencesXinYongUtilis.getStringFromPref("phone");
        ip = SharedPreferencesXinYongUtilis.getStringFromPref("ip");
        XinYongApi.getGankService().logins(phone, ip)
                .compose(XApi.<XinYongBaseRespModel<XinYongLoginRespModel>>getApiTransformer())
                .compose(XApi.<XinYongBaseRespModel<XinYongLoginRespModel>>getScheduler())
                .compose(getV().<XinYongBaseRespModel<XinYongLoginRespModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<XinYongBaseRespModel<XinYongLoginRespModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        XinYongStaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(XinYongBaseRespModel<XinYongLoginRespModel> gankResults) {
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
