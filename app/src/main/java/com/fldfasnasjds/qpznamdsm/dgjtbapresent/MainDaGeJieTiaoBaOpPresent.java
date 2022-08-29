package com.fldfasnasjds.qpznamdsm.dgjtbapresent;

import android.content.Context;
import android.content.SharedPreferences;

import com.fldfasnasjds.qpznamdsm.dgjtbamodel.BaseRespModelDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.LoginRespDaGeJieTiaoBaOpModel;
import com.fldfasnasjds.qpznamdsm.dgjtbaui.HomePageActivityDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbautils.DaGeJieTiaoBaOpSharedPreferencesUtilis;
import com.fldfasnasjds.qpznamdsm.dgjtbautils.StaticUtilDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.mvp.XPresent;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.ApiDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.NetError;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.XApi;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.ApiSubscriber;

public class MainDaGeJieTiaoBaOpPresent extends XPresent<HomePageActivityDaGeJieTiaoBaOp> {

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
            phone = DaGeJieTiaoBaOpSharedPreferencesUtilis.getStringFromPref("phone");
            ip = DaGeJieTiaoBaOpSharedPreferencesUtilis.getStringFromPref("ip");
            ApiDaGeJieTiaoBaOp.getGankService().logins(phone, ip)
                    .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel>>getScheduler())
                    .compose(getV().<BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilDaGeJieTiaoBaOp.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel> gankResults) {
                            if (gankResults != null) {

                            }
                        }
                    });
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
