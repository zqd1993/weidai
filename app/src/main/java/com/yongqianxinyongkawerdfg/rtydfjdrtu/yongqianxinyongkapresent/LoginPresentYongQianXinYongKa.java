package com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkapresent;

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

import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaBaseRespModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.CompanyInfoModelYongQianXinYongKa;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaLoginRespModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.LoginStatusModelYongQianXinYongKa;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.LoginActivityYongQianXinYongKa;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.YongQianXinYongKaHomePageActivity;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkautils.SharedPreferencesYongQianXinYongKaUtilis;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkautils.YongQianXinYongKaStaticUtil;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkautils.YongQianXinYongKaToastUtil;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkawidget.CountDownTimerUtilsYongQianXinYongKa;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.mvp.XPresent;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.YongQianXinYongKaApi;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.NetError;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.XApi;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.router.Router;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.ApiSubscriber;

import java.io.IOException;

public class LoginPresentYongQianXinYongKa extends XPresent<LoginActivityYongQianXinYongKa> {

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
        if (!TextUtils.isEmpty(SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("API_BASE_URL"))) {
            YongQianXinYongKaApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelYongQianXinYongKa>getApiTransformer())
                    .compose(XApi.<LoginStatusModelYongQianXinYongKa>getScheduler())
                    .compose(getV().<LoginStatusModelYongQianXinYongKa>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelYongQianXinYongKa>() {
                        @Override
                        protected void onFail(NetError error) {
                            YongQianXinYongKaStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(LoginStatusModelYongQianXinYongKa loginStatusModelYongQianXinYongKa) {
                            if (loginStatusModelYongQianXinYongKa != null) {
                                if ("1".equals(loginStatusModelYongQianXinYongKa.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(loginStatusModelYongQianXinYongKa.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(loginStatusModelYongQianXinYongKa.getIs_code_register());
                                Log.d("zqd", "loginStatusModel.getIs_agree_check() = " + loginStatusModelYongQianXinYongKa.getIs_agree_check() +
                                        "--->loginStatusModel.getIs_code_register() = " + loginStatusModelYongQianXinYongKa.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("API_BASE_URL"))) {
            YongQianXinYongKaApi.getGankService().getCompanyInfo()
                    .compose(XApi.<YongQianXinYongKaBaseRespModel<CompanyInfoModelYongQianXinYongKa>>getApiTransformer())
                    .compose(XApi.<YongQianXinYongKaBaseRespModel<CompanyInfoModelYongQianXinYongKa>>getScheduler())
                    .compose(getV().<YongQianXinYongKaBaseRespModel<CompanyInfoModelYongQianXinYongKa>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YongQianXinYongKaBaseRespModel<CompanyInfoModelYongQianXinYongKa>>() {
                        @Override
                        protected void onFail(NetError error) {
                            YongQianXinYongKaStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(YongQianXinYongKaBaseRespModel<CompanyInfoModelYongQianXinYongKa> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedPreferencesYongQianXinYongKaUtilis.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
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
        if (!TextUtils.isEmpty(SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("API_BASE_URL"))) {
            YongQianXinYongKaApi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel>>getApiTransformer())
                    .compose(XApi.<YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel>>getScheduler())
                    .compose(getV().<YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            YongQianXinYongKaStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(YongQianXinYongKaBaseRespModel<YongQianXinYongKaLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesYongQianXinYongKaUtilis.saveStringIntoPref("phone", phone);
                                    SharedPreferencesYongQianXinYongKaUtilis.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(YongQianXinYongKaHomePageActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    YongQianXinYongKaToastUtil.showShort(gankResults.getMsg());
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
        if (!TextUtils.isEmpty(SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("API_BASE_URL"))) {
            YongQianXinYongKaApi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<YongQianXinYongKaBaseRespModel>getApiTransformer())
                    .compose(XApi.<YongQianXinYongKaBaseRespModel>getScheduler())
                    .compose(getV().<YongQianXinYongKaBaseRespModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YongQianXinYongKaBaseRespModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            YongQianXinYongKaStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(YongQianXinYongKaBaseRespModel gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                YongQianXinYongKaToastUtil.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtilsYongQianXinYongKa mCountDownTimerUtilsYongQianXinYongKa = new CountDownTimerUtilsYongQianXinYongKa(textView, 60000, 1000);
                                    mCountDownTimerUtilsYongQianXinYongKa.start();
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
