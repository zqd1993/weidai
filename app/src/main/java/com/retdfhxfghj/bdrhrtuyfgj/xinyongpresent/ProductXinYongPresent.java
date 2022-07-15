package com.retdfhxfghj.bdrhrtuyfgj.xinyongpresent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.XinYongBaseRespModel;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.XinYongGoodsModel;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.RequModelXinYong;
import com.retdfhxfghj.bdrhrtuyfgj.mvp.XPresent;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.XinYongApi;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.ApiSubscriber;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.NetError;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.XApi;
import com.retdfhxfghj.bdrhrtuyfgj.ui.xinyongfragment.ProductXinYongFragment;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongutils.SharedPreferencesXinYongUtilis;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongutils.XinYongStaticUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.RequestBody;


public class ProductXinYongPresent extends XPresent<ProductXinYongFragment> {

    private int mobileType;

    private String phone, token;

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

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesXinYongUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesXinYongUtilis.getStringFromPref("token");
            RequModelXinYong model = new RequModelXinYong();
            model.setToken(token);
            RequestBody body = XinYongStaticUtil.createBody(new Gson().toJson(model));
            XinYongApi.getGankService().productList(body)
                    .compose(XApi.<XinYongBaseRespModel<List<XinYongGoodsModel>>>getApiTransformer())
                    .compose(XApi.<XinYongBaseRespModel<List<XinYongGoodsModel>>>getScheduler())
                    .compose(getV().<XinYongBaseRespModel<List<XinYongGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<XinYongBaseRespModel<List<XinYongGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            XinYongStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(XinYongBaseRespModel<List<XinYongGoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public float ifyjfgcj(String imageFile) {
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

    public Bitmap werzdfgh(Context context, Uri uri, int w, int h) {
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

    public void productClick(XinYongGoodsModel model) {
        if (!TextUtils.isEmpty(SharedPreferencesXinYongUtilis.getStringFromPref("API_BASE_URL"))) {
            phone = SharedPreferencesXinYongUtilis.getStringFromPref("phone");
            XinYongApi.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<XinYongBaseRespModel>getApiTransformer())
                    .compose(XApi.<XinYongBaseRespModel>getScheduler())
                    .compose(getV().<XinYongBaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<XinYongBaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            XinYongStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(XinYongBaseRespModel gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

    public float urthfgxh(String imageFile) {
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

    public Bitmap bdartfhy(Context context, Uri uri, int w, int h) {
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
