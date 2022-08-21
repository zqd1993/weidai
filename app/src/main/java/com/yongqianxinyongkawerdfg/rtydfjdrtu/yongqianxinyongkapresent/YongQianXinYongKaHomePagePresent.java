package com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkapresent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaBaseRespModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaGoodsModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.RequModelYongQianXinYongKa;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.yongqianxinyongkafragment.HomePageYongQianXinYongKaFragment;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkautils.SharedPreferencesYongQianXinYongKaUtilis;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkautils.YongQianXinYongKaStaticUtil;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.mvp.XPresent;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.YongQianXinYongKaApi;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.ApiSubscriber;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.NetError;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.XApi;

import java.io.IOException;
import java.util.List;

import okhttp3.RequestBody;


public class YongQianXinYongKaHomePagePresent extends XPresent<HomePageYongQianXinYongKaFragment> {

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
        if (!TextUtils.isEmpty(SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("token");
            RequModelYongQianXinYongKa model = new RequModelYongQianXinYongKa();
            model.setToken(token);
            RequestBody body = YongQianXinYongKaStaticUtil.createBody(new Gson().toJson(model));
            YongQianXinYongKaApi.getGankService().productList(body)
                    .compose(XApi.<YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>>>getApiTransformer())
                    .compose(XApi.<YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>>>getScheduler())
                    .compose(getV().<YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            YongQianXinYongKaStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>> gankResults) {
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

    public void productClick(YongQianXinYongKaGoodsModel model) {
        phone = SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("phone");
        YongQianXinYongKaApi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<YongQianXinYongKaBaseRespModel>getApiTransformer())
                .compose(XApi.<YongQianXinYongKaBaseRespModel>getScheduler())
                .compose(getV().<YongQianXinYongKaBaseRespModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<YongQianXinYongKaBaseRespModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        YongQianXinYongKaStaticUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(YongQianXinYongKaBaseRespModel gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public float bzbdrtgh(String imageFile) {
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

    public Bitmap ithujfgj(Context context, Uri uri, int w, int h) {
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

    public void aindex() {
        if (!TextUtils.isEmpty(SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("token");
            RequModelYongQianXinYongKa model = new RequModelYongQianXinYongKa();
            model.setToken(token);
            RequestBody body = YongQianXinYongKaStaticUtil.createBody(new Gson().toJson(model));
            YongQianXinYongKaApi.getGankService().aindex(body)
                    .compose(XApi.<YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>>>getApiTransformer())
                    .compose(XApi.<YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>>>getScheduler())
                    .compose(getV().<YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(YongQianXinYongKaBaseRespModel<List<YongQianXinYongKaGoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {

                            }
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topYongQianXinYongKaGoodsModel = gankResults.getTop();
                                            if (!TextUtils.isEmpty(SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("API_BASE_URL"))) {
                                                Glide.with(getV()).load(SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("API_BASE_URL") + gankResults.getTop().getImgs()).into(getV().topImg);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public float werzdzdh(String imageFile) {
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

    public Bitmap rtyxfgh(Context context, Uri uri, int w, int h) {
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
