package com.tryrbdfbv.grtregdfh.present;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tryrbdfbv.grtregdfh.model.BaseRespModelJieJie;
import com.tryrbdfbv.grtregdfh.model.CompanyJieJieInfoModel;
import com.tryrbdfbv.grtregdfh.model.JieJieLoginStatusModel;
import com.tryrbdfbv.grtregdfh.model.LoginRespJieJieModel;
import com.tryrbdfbv.grtregdfh.net.ApiJieJie;
import com.tryrbdfbv.grtregdfh.ui.HomePageJieJieActivity;
import com.tryrbdfbv.grtregdfh.ui.JieJieLoginActivity;
import com.tryrbdfbv.grtregdfh.utils.JieJieStaticUtil;
import com.tryrbdfbv.grtregdfh.utils.SharedPreferencesUtilisJieJie;
import com.tryrbdfbv.grtregdfh.utils.ToastUtilJieJie;
import com.tryrbdfbv.grtregdfh.widget.CountDownTimerUtilsJieJie;
import com.tryrbdfbv.grtregdfh.mvp.XPresent;
import com.tryrbdfbv.grtregdfh.net.NetError;
import com.tryrbdfbv.grtregdfh.net.XApi;
import com.tryrbdfbv.grtregdfh.router.Router;
import com.tryrbdfbv.grtregdfh.net.ApiSubscriber;

import java.util.List;
import java.util.Map;

public class LoginJieJiePresent extends XPresent<JieJieLoginActivity> {

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> parseJsonToList(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisJieJie.getStringFromPref("API_BASE_URL"))) {
            ApiJieJie.getGankService().getGankData()
                    .compose(XApi.<JieJieLoginStatusModel>getApiTransformer())
                    .compose(XApi.<JieJieLoginStatusModel>getScheduler())
                    .compose(getV().<JieJieLoginStatusModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<JieJieLoginStatusModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            JieJieStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(JieJieLoginStatusModel jieJieLoginStatusModel) {
                            if (jieJieLoginStatusModel != null) {
                                if ("1".equals(jieJieLoginStatusModel.getIs_code_register())) {
                                    getV().verificationLl.setVisibility(View.GONE);
                                } else {
                                    getV().verificationLl.setVisibility(View.VISIBLE);
                                }
                                getV().isNeedChecked = "0".equals(jieJieLoginStatusModel.getIs_agree_check());
                                getV().isNeedVerification = "0".equals(jieJieLoginStatusModel.getIs_code_register());
                                Log.d("zqd", "jieJieLoginStatusModel.getIs_agree_check() = " + jieJieLoginStatusModel.getIs_agree_check() +
                                        "--->jieJieLoginStatusModel.getIs_code_register() = " + jieJieLoginStatusModel.getIs_code_register());
                                getV().remindCb.setChecked(getV().isNeedChecked);
                            }
                        }
                    });
        }
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> iyujghj(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> revzdfgd(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> nsfdgsdfg(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisJieJie.getStringFromPref("API_BASE_URL"))) {
            ApiJieJie.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelJieJie<CompanyJieJieInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieJie<CompanyJieJieInfoModel>>getScheduler())
                    .compose(getV().<BaseRespModelJieJie<CompanyJieJieInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieJie<CompanyJieJieInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            JieJieStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieJie<CompanyJieJieInfoModel> loginStatusModel) {
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    SharedPreferencesUtilisJieJie.saveStringIntoPref("APP_MAIL", loginStatusModel.getData().getGsmail());
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip, String oaidStr) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisJieJie.getStringFromPref("API_BASE_URL"))) {
            ApiJieJie.getGankService().login(phone, verificationStr, "", ip, oaidStr, "OAID")
                    .compose(XApi.<BaseRespModelJieJie<LoginRespJieJieModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieJie<LoginRespJieJieModel>>getScheduler())
                    .compose(getV().<BaseRespModelJieJie<LoginRespJieJieModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieJie<LoginRespJieJieModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            JieJieStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieJie<LoginRespJieJieModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 0) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesUtilisJieJie.saveStringIntoPref("phone", phone);
                                    SharedPreferencesUtilisJieJie.saveStringIntoPref("token", gankResults.getData().getToken());
                                    Router.newIntent(getV())
                                            .to(HomePageJieJieActivity.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (!TextUtils.isEmpty(gankResults.getMsg())) {
                                    ToastUtilJieJie.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> jkgasdg(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> aertsdg(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> basdtfwert(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisJieJie.getStringFromPref("API_BASE_URL"))) {
            ApiJieJie.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelJieJie>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieJie>getScheduler())
                    .compose(getV().<BaseRespModelJieJie>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieJie>() {
                        @Override
                        protected void onFail(NetError error) {
                            JieJieStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieJie gankResults) {
                            if (gankResults != null && !TextUtils.isEmpty(gankResults.getMsg())) {
                                ToastUtilJieJie.showShort("验证码" + gankResults.getMsg());
                                if (gankResults.getMsg().contains("成功")) {
                                    CountDownTimerUtilsJieJie mCountDownTimerUtilsJieJie = new CountDownTimerUtilsJieJie(textView, 60000, 1000);
                                    mCountDownTimerUtilsJieJie.start();
                                }
                            }
                        }
                    });
        }
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> ljfghdf(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> wevdfb(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> yrthfsgh(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

}
