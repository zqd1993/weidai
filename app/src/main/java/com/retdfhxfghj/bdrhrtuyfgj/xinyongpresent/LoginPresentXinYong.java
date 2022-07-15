package com.retdfhxfghj.bdrhrtuyfgj.xinyongpresent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.XinYongBaseRespModel;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.CompanyInfoModelXinYong;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.XinYongLoginRespModel;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.LoginStatusModelXinYong;
import com.retdfhxfghj.bdrhrtuyfgj.ui.LoginActivityXinYong;
import com.retdfhxfghj.bdrhrtuyfgj.ui.XinYongHomePageActivity;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongutils.SharedPreferencesXinYongUtilis;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongutils.XinYongStaticUtil;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongutils.XinYongToastUtil;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongwidget.CountDownTimerUtilsXinYong;
import com.retdfhxfghj.bdrhrtuyfgj.mvp.XPresent;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.XinYongApi;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.NetError;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.XApi;
import com.retdfhxfghj.bdrhrtuyfgj.router.Router;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.ApiSubscriber;

import java.io.IOException;

public class LoginPresentXinYong extends XPresent<LoginActivityXinYong> {

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

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesXinYongUtilis.getStringFromPref("API_BASE_URL"))) {
            XinYongApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelXinYong>getApiTransformer())
                    .compose(XApi.<LoginStatusModelXinYong>getScheduler())
                    .compose(getV().<LoginStatusModelXinYong>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelXinYong>() {
                        @Override
                        protected void onFail(NetError error) {
                            XinYongStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginStatusModelXinYong loginStatusModelXinYong) {
                            if (loginStatusModelXinYong != null) {
                                if ("1".equals(loginStatusModelXinYong.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginStatusModelXinYong.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginStatusModelXinYong.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + loginStatusModelXinYong.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + loginStatusModelXinYong.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesXinYongUtilis.getStringFromPref("API_BASE_URL"))) {
            XinYongApi.getGankService().getCompanyInfo()
                    .compose(XApi.<XinYongBaseRespModel<CompanyInfoModelXinYong>>getApiTransformer())
                    .compose(XApi.<XinYongBaseRespModel<CompanyInfoModelXinYong>>getScheduler())
                    .compose(getV().<XinYongBaseRespModel<CompanyInfoModelXinYong>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<XinYongBaseRespModel<CompanyInfoModelXinYong>>() {
                        @Override
                        protected void onFail(NetError error) {
                            XinYongStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(XinYongBaseRespModel<CompanyInfoModelXinYong> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedPreferencesXinYongUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public float wetgdzh(String imageFile) {
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

    public Bitmap mjgzdf(Context context, Uri uri, int w, int h) {
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

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(SharedPreferencesXinYongUtilis.getStringFromPref("API_BASE_URL"))) {
            XinYongApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<XinYongBaseRespModel<XinYongLoginRespModel>>getApiTransformer())
                    .compose(XApi.<XinYongBaseRespModel<XinYongLoginRespModel>>getScheduler())
                    .compose(getV().<XinYongBaseRespModel<XinYongLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<XinYongBaseRespModel<XinYongLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            XinYongStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(XinYongBaseRespModel<XinYongLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesXinYongUtilis.saveStringIntoPref("phone", phone);
                                    SharedPreferencesXinYongUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(XinYongHomePageActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    XinYongToastUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public float werydfgh(String imageFile) {
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

    public Bitmap vzsetgdxfh(Context context, Uri uri, int w, int h) {
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

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedPreferencesXinYongUtilis.getStringFromPref("API_BASE_URL"))) {
            XinYongApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<XinYongBaseRespModel>getApiTransformer())
                    .compose(XApi.<XinYongBaseRespModel>getScheduler())
                    .compose(getV().<XinYongBaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<XinYongBaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            XinYongStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(XinYongBaseRespModel gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                XinYongToastUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtilsXinYong mCountDownTimerUtilsXinYong = new CountDownTimerUtilsXinYong(textView, 60000, 1000);
                                    mCountDownTimerUtilsXinYong.start();
                                }
                            }
                        }
                    });
        }
    }

    public float oyjdfgjh(String imageFile) {
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

    public Bitmap rtyhxfg(Context context, Uri uri, int w, int h) {
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
