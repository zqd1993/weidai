package com.terdfhdfu.wefgvzdhtru.presentxianjin;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.terdfhdfu.wefgvzdhtru.xianjinmodel.BaseRespModelXianJinFenQi;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.XianJinFenQiCompanyInfoModel;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.XianJinFenQiLoginRespModel;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.LoginStatusModelXianJinFenQi;
import com.terdfhdfu.wefgvzdhtru.xianjinui.LoginXianJinFenQiActivity;
import com.terdfhdfu.wefgvzdhtru.xianjinui.XianJinFenQiHomePageActivity;
import com.terdfhdfu.wefgvzdhtru.utilsxianjin.XianJinFenQiSharedPreferencesUtilis;
import com.terdfhdfu.wefgvzdhtru.utilsxianjin.StaticXianJinFenQiUtil;
import com.terdfhdfu.wefgvzdhtru.utilsxianjin.XianJinFenQiToastUtil;
import com.terdfhdfu.wefgvzdhtru.xinajinwidget.CountDownTimerUtilsXianJinFenQi;
import com.terdfhdfu.wefgvzdhtru.mvp.XPresent;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.XianJinFenQiApi;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.NetError;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.XApi;
import com.terdfhdfu.wefgvzdhtru.router.Router;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.ApiSubscriber;

import java.io.ByteArrayOutputStream;

public class XianJinFenQiLoginPresent extends XPresent<LoginXianJinFenQiActivity> {

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

    public void getGankData() {
        if (!TextUtils.isEmpty(XianJinFenQiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            XianJinFenQiApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelXianJinFenQi>getApiTransformer())
                    .compose(XApi.<LoginStatusModelXianJinFenQi>getScheduler())
                    .compose(getV().<LoginStatusModelXianJinFenQi>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelXianJinFenQi>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticXianJinFenQiUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginStatusModelXianJinFenQi loginStatusModelXianJinFenQi) {
                            if (loginStatusModelXianJinFenQi != null) {
                                if ("1".equals(loginStatusModelXianJinFenQi.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginStatusModelXianJinFenQi.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginStatusModelXianJinFenQi.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + loginStatusModelXianJinFenQi.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + loginStatusModelXianJinFenQi.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public Bitmap ertgfzxh(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap vfxdrty(View v, int shareSize) {
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

    public Bitmap iytjhgdfj(View v, int shareSize) {
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

    public byte[] ertgfdsh(View v) {
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

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(XianJinFenQiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            XianJinFenQiApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelXianJinFenQi<XianJinFenQiCompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianJinFenQi<XianJinFenQiCompanyInfoModel>>getScheduler())
                    .compose(getV().<BaseRespModelXianJinFenQi<XianJinFenQiCompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianJinFenQi<XianJinFenQiCompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticXianJinFenQiUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelXianJinFenQi<XianJinFenQiCompanyInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    XianJinFenQiSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public Bitmap ouyjkfjhf(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap cxvrtyry(View v, int shareSize) {
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

    public Bitmap mgfkuyidt(View v, int shareSize) {
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

    public byte[] reteshnfjj(View v) {
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

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(XianJinFenQiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            XianJinFenQiApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelXianJinFenQi<XianJinFenQiLoginRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianJinFenQi<XianJinFenQiLoginRespModel>>getScheduler())
                    .compose(getV().<BaseRespModelXianJinFenQi<XianJinFenQiLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianJinFenQi<XianJinFenQiLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticXianJinFenQiUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelXianJinFenQi<XianJinFenQiLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    XianJinFenQiSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    XianJinFenQiSharedPreferencesUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(XianJinFenQiHomePageActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    XianJinFenQiToastUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public Bitmap pioolgjcgjh(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap iuyjgj(View v, int shareSize) {
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

    public Bitmap vdxhtuy(View v, int shareSize) {
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

    public byte[] retdfgsd(View v) {
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

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(XianJinFenQiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            XianJinFenQiApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelXianJinFenQi>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianJinFenQi>getScheduler())
                    .compose(getV().<BaseRespModelXianJinFenQi>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianJinFenQi>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticXianJinFenQiUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelXianJinFenQi gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                XianJinFenQiToastUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtilsXianJinFenQi mCountDownTimerUtilsXianJinFenQi = new CountDownTimerUtilsXianJinFenQi(textView, 60000, 1000);
                                    mCountDownTimerUtilsXianJinFenQi.start();
                                }
                            }
                        }
                    });
        }
    }

    public Bitmap thxfgnx(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap zxcvdrytr(View v, int shareSize) {
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

    public Bitmap tyudgjfgik(View v, int shareSize) {
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

    public byte[] ertdfbfgyh(View v) {
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
