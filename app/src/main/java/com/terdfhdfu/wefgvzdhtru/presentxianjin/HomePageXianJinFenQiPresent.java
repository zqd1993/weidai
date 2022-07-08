package com.terdfhdfu.wefgvzdhtru.presentxianjin;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.BaseRespModelXianJinFenQi;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.GoodsModelXianJinFenQi;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.XianJinFenQiRequModel;
import com.terdfhdfu.wefgvzdhtru.utilsxianjin.XianJinFenQiSharedPreferencesUtilis;
import com.terdfhdfu.wefgvzdhtru.utilsxianjin.StaticXianJinFenQiUtil;
import com.terdfhdfu.wefgvzdhtru.mvp.XPresent;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.XianJinFenQiApi;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.ApiSubscriber;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.NetError;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.XApi;
import com.terdfhdfu.wefgvzdhtru.xianjinui.fragment.HomePageXianJinFenQiFragment;

import java.io.ByteArrayOutputStream;
import java.util.List;

import okhttp3.RequestBody;


public class HomePageXianJinFenQiPresent extends XPresent<HomePageXianJinFenQiFragment> {

    private int mobileType;

    private String phone, token;

    public Bitmap createBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap createBitmapOnHide(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap createBitmapTransparentBg(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] createBitmapOnHideToBytes(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

    public void productList() {
        if (!TextUtils.isEmpty(XianJinFenQiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = XianJinFenQiSharedPreferencesUtilis.getStringFromPref("token");
            XianJinFenQiRequModel model = new XianJinFenQiRequModel();
            model.setToken(token);
            RequestBody body = StaticXianJinFenQiUtil.createBody(new Gson().toJson(model));
            XianJinFenQiApi.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>>>getScheduler())
                    .compose(getV().<BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticXianJinFenQiUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>> gankResults) {
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

    public void productClick(GoodsModelXianJinFenQi model) {
        phone = XianJinFenQiSharedPreferencesUtilis.getStringFromPref("phone");
        XianJinFenQiApi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModelXianJinFenQi>getApiTransformer())
                .compose(XApi.<BaseRespModelXianJinFenQi>getScheduler())
                .compose(getV().<BaseRespModelXianJinFenQi>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelXianJinFenQi>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        StaticXianJinFenQiUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelXianJinFenQi gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public Bitmap rethxjy(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap vcbxcvhtyrty(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap iytjghjf(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] retfxhtuy(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

    public void aindex() {
        if (!TextUtils.isEmpty(XianJinFenQiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = XianJinFenQiSharedPreferencesUtilis.getStringFromPref("token");
            XianJinFenQiRequModel model = new XianJinFenQiRequModel();
            model.setToken(token);
            RequestBody body = StaticXianJinFenQiUtil.createBody(new Gson().toJson(model));
            XianJinFenQiApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>>>getScheduler())
                    .compose(getV().<BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelXianJinFenQi<List<GoodsModelXianJinFenQi>> gankResults) {
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
                                            getV().topGoodsModelXianJinFenQi = gankResults.getTop();
                                            getV().money_num_tv.setText(gankResults.getTop().getMax_money());
                                            getV().rililv_tv.setText("日利率最低" + gankResults.getTop().getDay_money() + "%");
//                                            if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
//                                                Glide.with(getV()).load(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + gankResults.getTop().getImgs()).into(getV().topImg);
//                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public Bitmap zxcbtryrty(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap oukhgjd(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap nfghgfutyu(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] wertghdfh(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

}
