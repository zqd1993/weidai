package com.tergbaedd.bbbdstrga.sjdpresent;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tergbaedd.bbbdstrga.sjdui.HomePageActivityShouJiDai;
import com.tergbaedd.bbbdstrga.sjdui.WelcomeActivityShouJiDai;
import com.tergbaedd.bbbdstrga.sldmodel.BaseRespModelShouJiDai;
import com.tergbaedd.bbbdstrga.sldmodel.ConfigShouJiDaiModel;
import com.tergbaedd.bbbdstrga.sldmodel.LoginRespShouJiDaiModel;
import com.tergbaedd.bbbdstrga.sjdui.LoginShouJiDaiActivity;
import com.tergbaedd.bbbdstrga.sjdutils.ShouJiDaiSharedPreferencesUtilis;
import com.tergbaedd.bbbdstrga.sjdutils.StaticUtilShouJiDai;
import com.tergbaedd.bbbdstrga.sjdutils.ShouJiDaiToastUtil;
import com.tergbaedd.bbbdstrga.sjdwidget.CountDownShouJiDaiTimerUtils;
import com.tergbaedd.bbbdstrga.mvp.XPresent;
import com.tergbaedd.bbbdstrga.sjdnet.ApiShouJiDai;
import com.tergbaedd.bbbdstrga.sjdnet.NetError;
import com.tergbaedd.bbbdstrga.sjdnet.XApi;
import com.tergbaedd.bbbdstrga.router.Router;
import com.tergbaedd.bbbdstrga.sjdnet.ApiSubscriber;

public class LoginPresentShouJiDai extends XPresent<LoginShouJiDaiActivity> {

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

    public void getGankData() {
            ApiShouJiDai.getGankService().getGankData()
                    .compose(XApi.<BaseRespModelShouJiDai<ConfigShouJiDaiModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelShouJiDai<ConfigShouJiDaiModel>>getScheduler())
                    .compose(getV().<BaseRespModelShouJiDai<ConfigShouJiDaiModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelShouJiDai<ConfigShouJiDaiModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilShouJiDai.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelShouJiDai<ConfigShouJiDaiModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    ShouJiDaiSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void ertgfd(Context context, String key, Object object)
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
    public Object bdfgd(Context context, String key, Object defaultObject)
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

    public void login(String phone, String verificationStr, String ip, String oaidStr) {
            ApiShouJiDai.getGankService().login(phone, verificationStr, "", ip, oaidStr)
                    .compose(XApi.<BaseRespModelShouJiDai<LoginRespShouJiDaiModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelShouJiDai<LoginRespShouJiDaiModel>>getScheduler())
                    .compose(getV().<BaseRespModelShouJiDai<LoginRespShouJiDaiModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelShouJiDai<LoginRespShouJiDaiModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticUtilShouJiDai.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelShouJiDai<LoginRespShouJiDaiModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 200) {
                                if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                    ShouJiDaiSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    ShouJiDaiSharedPreferencesUtilis.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                    ShouJiDaiSharedPreferencesUtilis.saveStringIntoPref("ip", ip);
                                    StaticUtilShouJiDai.getValue(getV(), HomePageActivityShouJiDai.class, null, true);
                                }
                            } else {
                                if (gankResults.getCode() == 500) {
                                    ShouJiDaiToastUtil.showShort(gankResults.getMsg());
                                }
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
    public void wesdf(Context context, String key, Object object)
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
    public Object fvsdf(Context context, String key, Object defaultObject)
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

    public void sendVerifyCode(String phone, TextView textView) {
            ApiShouJiDai.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelShouJiDai>getApiTransformer())
                    .compose(XApi.<BaseRespModelShouJiDai>getScheduler())
                    .compose(getV().<BaseRespModelShouJiDai>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelShouJiDai>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilShouJiDai.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelShouJiDai gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200) {
                                    ShouJiDaiToastUtil.showShort("验证码发送成功");
                                    CountDownShouJiDaiTimerUtils mCountDownShouJiDaiTimerUtils = new CountDownShouJiDaiTimerUtils(textView, 60000, 1000);
                                    mCountDownShouJiDaiTimerUtils.start();
                                }
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
    public void wertdfg(Context context, String key, Object object)
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
    public Object ndfdg(Context context, String key, Object defaultObject)
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
