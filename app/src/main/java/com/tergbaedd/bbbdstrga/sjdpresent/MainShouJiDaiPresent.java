package com.tergbaedd.bbbdstrga.sjdpresent;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.tergbaedd.bbbdstrga.sldmodel.BaseRespModelShouJiDai;
import com.tergbaedd.bbbdstrga.sldmodel.LoginRespShouJiDaiModel;
import com.tergbaedd.bbbdstrga.sjdui.HomePageActivityShouJiDai;
import com.tergbaedd.bbbdstrga.sjdutils.ShouJiDaiSharedPreferencesUtilis;
import com.tergbaedd.bbbdstrga.sjdutils.StaticUtilShouJiDai;
import com.tergbaedd.bbbdstrga.mvp.XPresent;
import com.tergbaedd.bbbdstrga.sjdnet.ApiShouJiDai;
import com.tergbaedd.bbbdstrga.sjdnet.NetError;
import com.tergbaedd.bbbdstrga.sjdnet.XApi;
import com.tergbaedd.bbbdstrga.sjdnet.ApiSubscriber;

public class MainShouJiDaiPresent extends XPresent<HomePageActivityShouJiDai> {

    private String phone, ip;

    /**
     * 保存在手机里面的文件名
     */
    public final String FILE_NAME = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void put(Context context, String key, Object object)
    {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
            editor.putString(key, object.toString());
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public void login() {
        if (!TextUtils.isEmpty(ShouJiDaiSharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            phone = ShouJiDaiSharedPreferencesUtilis.getStringFromPref("phone");
            ip = ShouJiDaiSharedPreferencesUtilis.getStringFromPref("ip");
            ApiShouJiDai.getGankService().logins(phone, ip)
                    .compose(XApi.<BaseRespModelShouJiDai<LoginRespShouJiDaiModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelShouJiDai<LoginRespShouJiDaiModel>>getScheduler())
                    .compose(getV().<BaseRespModelShouJiDai<LoginRespShouJiDaiModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelShouJiDai<LoginRespShouJiDaiModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilShouJiDai.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelShouJiDai<LoginRespShouJiDaiModel> gankResults) {
                            if (gankResults != null) {

                            }
                        }
                    });
        }
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void rthfgh(Context context, String key, Object object)
    {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
            editor.putString(key, object.toString());
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object vfdgdst(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

}
