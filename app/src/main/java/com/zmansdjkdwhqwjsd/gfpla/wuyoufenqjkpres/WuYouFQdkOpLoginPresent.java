package com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkpres;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.BaseRespWuYouFQdkOpModel;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.ConfigModelWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.LoginRespModelWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.HomePageActivityWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.LoginWuYouFQdkOpActivity;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.SharedPreferencesWuYouFQdkOpUtilis;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.StaticUtilWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.ToasWuYouFQdkOptUtil;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkwidget.CountDownTimerUtilsWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.mvp.XPresent;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.ApiWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.NetError;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.XApi;
import com.zmansdjkdwhqwjsd.gfpla.router.Router;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.ApiSubscriber;

import java.util.List;
import java.util.Map;

public class WuYouFQdkOpLoginPresent extends XPresent<LoginWuYouFQdkOpActivity> {

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
            ApiWuYouFQdkOp.getGankService().getGankData()
                    .compose(XApi.<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>>getApiTransformer())
                    .compose(XApi.<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>>getScheduler())
                    .compose(getV().<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilWuYouFQdkOp.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    SharedPreferencesWuYouFQdkOpUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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
            ApiWuYouFQdkOp.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp>>getApiTransformer())
                    .compose(XApi.<BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp>>getScheduler())
                    .compose(getV().<BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticUtilWuYouFQdkOp.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 200) {
                                if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                    SharedPreferencesWuYouFQdkOpUtilis.saveStringIntoPref("phone", phone);
                                    SharedPreferencesWuYouFQdkOpUtilis.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                    SharedPreferencesWuYouFQdkOpUtilis.saveStringIntoPref("ip", ip);
                                    Router.newIntent(getV())
                                            .to(HomePageActivityWuYouFQdkOp.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (gankResults.getCode() == 500) {
                                    ToasWuYouFQdkOptUtil.showShort(gankResults.getMsg());
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
            ApiWuYouFQdkOp.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespWuYouFQdkOpModel>getApiTransformer())
                    .compose(XApi.<BaseRespWuYouFQdkOpModel>getScheduler())
                    .compose(getV().<BaseRespWuYouFQdkOpModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespWuYouFQdkOpModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilWuYouFQdkOp.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespWuYouFQdkOpModel gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200) {
                                    ToasWuYouFQdkOptUtil.showShort("验证码发送成功");
                                    CountDownTimerUtilsWuYouFQdkOp mCountDownTimerUtilsWuYouFQdkOp = new CountDownTimerUtilsWuYouFQdkOp(textView, 60000, 1000);
                                    mCountDownTimerUtilsWuYouFQdkOp.start();
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
