package com.fldfasnasjds.qpznamdsm.dgjtbapresent;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.fldfasnasjds.qpznamdsm.dgjtbaui.HomePageActivityDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.BaseRespModelDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.ConfigDaGeJieTiaoBaOpModel;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.LoginRespDaGeJieTiaoBaOpModel;
import com.fldfasnasjds.qpznamdsm.dgjtbaui.LoginDaGeJieTiaoBaOpActivity;
import com.fldfasnasjds.qpznamdsm.dgjtbautils.DaGeJieTiaoBaOpSharedPreferencesUtilis;
import com.fldfasnasjds.qpznamdsm.dgjtbautils.StaticUtilDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbautils.DaGeJieTiaoBaOpToastUtil;
import com.fldfasnasjds.qpznamdsm.dgjtbawidget.CountDownDaGeJieTiaoBaOpTimerUtils;
import com.fldfasnasjds.qpznamdsm.mvp.XPresent;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.ApiDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.NetError;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.XApi;
import com.fldfasnasjds.qpznamdsm.router.Router;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.ApiSubscriber;

public class LoginPresentDaGeJieTiaoBaOp extends XPresent<LoginDaGeJieTiaoBaOpActivity> {

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
    public void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
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
    public Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public void getGankData() {
        ApiDaGeJieTiaoBaOp.getGankService().getGankData()
                .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel>>getScheduler())
                .compose(getV().<BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtilDaGeJieTiaoBaOp.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                DaGeJieTiaoBaOpSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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
    public void ertgfd(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
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
    public Object bdfgd(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public void login(String phone, String verificationStr, String ip, String oaidStr) {
        ApiDaGeJieTiaoBaOp.getGankService().login(phone, verificationStr, "", ip, oaidStr)
                .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel>>getScheduler())
                .compose(getV().<BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        StaticUtilDaGeJieTiaoBaOp.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelDaGeJieTiaoBaOp<LoginRespDaGeJieTiaoBaOpModel> gankResults) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        if (gankResults != null && gankResults.getCode() == 200) {
                            if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                DaGeJieTiaoBaOpSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                DaGeJieTiaoBaOpSharedPreferencesUtilis.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                DaGeJieTiaoBaOpSharedPreferencesUtilis.saveStringIntoPref("ip", ip);
                                Router.newIntent(getV())
                                        .to(HomePageActivityDaGeJieTiaoBaOp.class)
                                        .launch();
                                getV().finish();
                            }
                        } else {
                            if (gankResults.getCode() == 500) {
                                DaGeJieTiaoBaOpToastUtil.showShort(gankResults.getMsg());
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
    public void wesdf(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
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
    public Object fvsdf(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public void sendVerifyCode(String phone, TextView textView) {
        ApiDaGeJieTiaoBaOp.getGankService().sendVerifyCode(phone)
                .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp>getApiTransformer())
                .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp>getScheduler())
                .compose(getV().<BaseRespModelDaGeJieTiaoBaOp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelDaGeJieTiaoBaOp>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtilDaGeJieTiaoBaOp.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelDaGeJieTiaoBaOp gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getCode() == 200) {
                                DaGeJieTiaoBaOpToastUtil.showShort("验证码发送成功");
                                CountDownDaGeJieTiaoBaOpTimerUtils mCountDownDaGeJieTiaoBaOpTimerUtils = new CountDownDaGeJieTiaoBaOpTimerUtils(textView, 60000, 1000);
                                mCountDownDaGeJieTiaoBaOpTimerUtils.start();
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
    public void wertdfg(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
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
    public Object ndfdg(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

}
