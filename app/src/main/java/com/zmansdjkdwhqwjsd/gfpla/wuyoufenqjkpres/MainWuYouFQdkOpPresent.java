package com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkpres;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.BaseRespWuYouFQdkOpModel;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.LoginRespModelWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.HomePageActivityWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.SharedPreferencesWuYouFQdkOpUtilis;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.StaticUtilWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.mvp.XPresent;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.ApiWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.NetError;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.XApi;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.ApiSubscriber;

import java.util.List;
import java.util.Map;

public class MainWuYouFQdkOpPresent extends XPresent<HomePageActivityWuYouFQdkOp> {

    private String phone, ip;

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

    public void login() {
            phone = SharedPreferencesWuYouFQdkOpUtilis.getStringFromPref("phone");
            ip = SharedPreferencesWuYouFQdkOpUtilis.getStringFromPref("ip");
            ApiWuYouFQdkOp.getGankService().logins(phone, ip)
                    .compose(XApi.<BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp>>getApiTransformer())
                    .compose(XApi.<BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp>>getScheduler())
                    .compose(getV().<BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilWuYouFQdkOp.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespWuYouFQdkOpModel<LoginRespModelWuYouFQdkOp> gankResults) {
                            if (gankResults != null) {

                            }
                        }
                    });
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public static <T> T sdfbvnxh(String gsonString, Class<T> cls) {
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
    public static <T> List<T> ihfsdhfsgh(String gsonString, Class<T> cls) {
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
    public static <T> List<Map<String, T>> rtyfghdsfgh(String gsonString) {
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
    public static <T> Map<String, T> bnsfghadrg(String gsonString) {
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
