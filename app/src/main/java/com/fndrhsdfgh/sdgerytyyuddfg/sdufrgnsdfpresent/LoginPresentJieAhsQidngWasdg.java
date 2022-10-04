package com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfpresent;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui.HomePageActivityJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.BaseRespModelJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.ConfigJieAhsQidngWasdgModel;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.LoginRespJieAhsQidngWasdgModel;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui.LoginJieAhsQidngWasdgActivity;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfutils.JieAhsQidngWasdgSharedPreferencesUtilis;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfutils.StaticUtilJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfutils.JieAhsQidngWasdgToastUtil;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfwidget.CountDownJieAhsQidngWasdgTimerUtils;
import com.fndrhsdfgh.sdgerytyyuddfg.mvp.XPresent;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.ApiJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.NetError;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.XApi;
import com.fndrhsdfgh.sdgerytyyuddfg.router.Router;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.ApiSubscriber;

public class LoginPresentJieAhsQidngWasdg extends XPresent<LoginJieAhsQidngWasdgActivity> {

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
        ApiJieAhsQidngWasdg.getGankService().getGankData()
                .compose(XApi.<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>>getScheduler())
                .compose(getV().<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtilJieAhsQidngWasdg.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                JieAhsQidngWasdgSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
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
        ApiJieAhsQidngWasdg.getGankService().login(phone, verificationStr, "", ip, oaidStr)
                .compose(XApi.<BaseRespModelJieAhsQidngWasdg<LoginRespJieAhsQidngWasdgModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelJieAhsQidngWasdg<LoginRespJieAhsQidngWasdgModel>>getScheduler())
                .compose(getV().<BaseRespModelJieAhsQidngWasdg<LoginRespJieAhsQidngWasdgModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelJieAhsQidngWasdg<LoginRespJieAhsQidngWasdgModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        StaticUtilJieAhsQidngWasdg.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelJieAhsQidngWasdg<LoginRespJieAhsQidngWasdgModel> gankResults) {
                        getV().loadingFl.setVisibility(View.GONE);
                        getV().rotateLoading.stop();
                        if (gankResults != null && gankResults.getCode() == 200) {
                            if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                JieAhsQidngWasdgSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                JieAhsQidngWasdgSharedPreferencesUtilis.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                JieAhsQidngWasdgSharedPreferencesUtilis.saveStringIntoPref("ip", ip);
                                Router.newIntent(getV())
                                        .to(HomePageActivityJieAhsQidngWasdg.class)
                                        .launch();
                                getV().finish();
                            }
                        } else {
                            if (gankResults.getCode() == 500) {
                                JieAhsQidngWasdgToastUtil.showShort(gankResults.getMsg());
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
        ApiJieAhsQidngWasdg.getGankService().sendVerifyCode(phone)
                .compose(XApi.<BaseRespModelJieAhsQidngWasdg>getApiTransformer())
                .compose(XApi.<BaseRespModelJieAhsQidngWasdg>getScheduler())
                .compose(getV().<BaseRespModelJieAhsQidngWasdg>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelJieAhsQidngWasdg>() {
                    @Override
                    protected void onFail(NetError error) {
                        StaticUtilJieAhsQidngWasdg.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelJieAhsQidngWasdg gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getCode() == 200) {
                                JieAhsQidngWasdgToastUtil.showShort("验证码发送成功");
                                CountDownJieAhsQidngWasdgTimerUtils mCountDownJieAhsQidngWasdgTimerUtils = new CountDownJieAhsQidngWasdgTimerUtils(textView, 60000, 1000);
                                mCountDownJieAhsQidngWasdgTimerUtils.start();
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
