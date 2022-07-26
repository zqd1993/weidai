package com.rihdkauecgh.plihgnytrvfws.pres;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rihdkauecgh.plihgnytrvfws.models.BaseRespXiaoNiuModel;
import com.rihdkauecgh.plihgnytrvfws.models.ConfigModelXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.models.LoginRespModelXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.ui.HomePageActivityXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.ui.LoginXiaoNiuActivity;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesXiaoNiuUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StaticUtilXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.utils.ToasXiaoNiutUtil;
import com.rihdkauecgh.plihgnytrvfws.widget.CountDownTimerUtilsXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.mvp.XPresent;
import com.rihdkauecgh.plihgnytrvfws.http.ApiXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.http.NetError;
import com.rihdkauecgh.plihgnytrvfws.http.XApi;
import com.rihdkauecgh.plihgnytrvfws.router.Router;
import com.rihdkauecgh.plihgnytrvfws.http.ApiSubscriber;

import java.util.List;
import java.util.Map;

public class XiaoNiuLoginPresent extends XPresent<LoginXiaoNiuActivity> {

    // 把json字符串变成实体类Bean并对对应参数赋值
    public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: "+e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: "+e.getMessage());
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
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    public void getGankData() {
            ApiXiaoNiu.getGankService().getGankData()
                    .compose(XApi.<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>>getApiTransformer())
                    .compose(XApi.<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>>getScheduler())
                    .compose(getV().<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilXiaoNiu.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespXiaoNiuModel<ConfigModelXiaoNiu> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesXiaoNiuUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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

    // 把json字符串变成实体类Bean并对对应参数赋值
    public static <T> T iytfghfh(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public static <T> List<T> mghhfgh(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: "+e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> gwezdfb(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> nfhdfg(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    public void login(String phone, String verificationStr, String ip) {
            ApiXiaoNiu.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespXiaoNiuModel<LoginRespModelXiaoNiu>>getApiTransformer())
                    .compose(XApi.<BaseRespXiaoNiuModel<LoginRespModelXiaoNiu>>getScheduler())
                    .compose(getV().<BaseRespXiaoNiuModel<LoginRespModelXiaoNiu>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespXiaoNiuModel<LoginRespModelXiaoNiu>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticUtilXiaoNiu.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespXiaoNiuModel<LoginRespModelXiaoNiu> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 200) {
                                if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                    SharedPreferencesXiaoNiuUtilis.saveStringIntoPref("phone", phone);
                                    SharedPreferencesXiaoNiuUtilis.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                    SharedPreferencesXiaoNiuUtilis.saveStringIntoPref("ip", ip);
                                    StaticUtilXiaoNiu.getValue(getV(), HomePageActivityXiaoNiu.class, null, true);
                                }
                            } else {
                                if (gankResults.getCode() == 500) {
                                    ToasXiaoNiutUtil.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public static <T> T iyujghj(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public static <T> List<T> msdfzg(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: "+e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> qwrdgdfs(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> gfnfgdhj(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    public void sendVerifyCode(String phone, TextView textView) {
            ApiXiaoNiu.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespXiaoNiuModel>getApiTransformer())
                    .compose(XApi.<BaseRespXiaoNiuModel>getScheduler())
                    .compose(getV().<BaseRespXiaoNiuModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespXiaoNiuModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilXiaoNiu.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespXiaoNiuModel gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200) {
                                    ToasXiaoNiutUtil.showShort("验证码发送成功");
                                    CountDownTimerUtilsXiaoNiu mCountDownTimerUtilsXiaoNiu = new CountDownTimerUtilsXiaoNiu(textView, 60000, 1000);
                                    mCountDownTimerUtilsXiaoNiu.start();
                                }
                            }
                        }
                    });
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public static <T> T zvbdfhfy(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public static <T> List<T> ikyugj(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: "+e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> fgssf(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> aewgdbf(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

}
