package com.fjx.gkdcwf.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fjx.gkdcwf.R;
import com.fjx.gkdcwf.bean.SetModel;
import com.fjx.gkdcwf.mainapi.KuaiJieApi;
import com.fjx.gkdcwf.gongju.OpenKuaiJieUtil;
import com.fjx.gkdcwf.gongju.KuaiJiePreferencesOpenUtil;
import com.fjx.gkdcwf.gongju.StatusKuaiJieBarUtil;
import com.fjx.gkdcwf.weidgt.StartPageKuaiJieRemindDialog;
import com.umeng.commonsdk.UMConfigure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class KuaiJieStartPageActivity extends AppCompatActivity {

    private Bundle bundle;

    private boolean isSure = false;

    private String phone = "";

    private StartPageKuaiJieRemindDialog startPageRemindDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuaijie_start_page);
        StatusKuaiJieBarUtil.setTransparent(this, false);
        isSure = KuaiJiePreferencesOpenUtil.getBool("isSure");
        phone = KuaiJiePreferencesOpenUtil.getString("phone");
        jumpPage();
    }

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {

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

        /**
         * commit操作使用了SharedPreferencesCompat.apply进行了替代，目的是尽可能的使用apply代替commit
         * 因为commit方法是同步的，并且我们很多时候的commit操作都是UI线程中，毕竟是IO操作，尽可能异步；
         */
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
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

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    private void jumpPage() {
        if (!isSure) {
            startPageRemindDialog = new StartPageKuaiJieRemindDialog(this);
            startPageRemindDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        KuaiJieStartPageActivity.this.finish();
                        return false;
                    }
                    return true;
                }
            });
            startPageRemindDialog.setOnListener(new StartPageKuaiJieRemindDialog.OnListener() {
                @Override
                public void oneBtnClicked() {
                    initUm();
                    KuaiJiePreferencesOpenUtil.saveBool("isSure", true);
                    OpenKuaiJieUtil.jumpPage(KuaiJieStartPageActivity.this, KuaiJieDlActivity.class);
                    finish();
                }

                @Override
                public void zcxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", KuaiJieApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenKuaiJieUtil.jumpPage(KuaiJieStartPageActivity.this, KuaiJieWebViewActivity.class, bundle);
                }

                @Override
                public void twoBtnClicked() {
                    finish();
                }

                @Override
                public void ysxyClicked() {
                    bundle = new Bundle();
                    bundle.putString("url", KuaiJieApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenKuaiJieUtil.jumpPage(KuaiJieStartPageActivity.this, KuaiJieWebViewActivity.class, bundle);
                }
            });
            startPageRemindDialog.show();
        } else {
            initUm();
            new Handler().postDelayed(() -> {
                if (TextUtils.isEmpty(phone)) {
                    OpenKuaiJieUtil.jumpPage(KuaiJieStartPageActivity.this, KuaiJieDlActivity.class);
                } else {
                    OpenKuaiJieUtil.jumpPage(KuaiJieStartPageActivity.this, MainKuaiJieActivity.class);
                }
                finish();
            }, 1000);
        }
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author dj
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initUm() {
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        if (!UMConfigure.isInit) {
            UMConfigure.setLogEnabled(true);
            Log.d("youmeng", "zhuche chenggong");
            //友盟正式初始化
//            UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "Umeng");
            // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
            // 参数一：当前上下文context；
            // 参数二：应用申请的Appkey（需替换）；
            // 参数三：渠道名称；
            // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
            // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
            UMConfigure.init(this, "62603259d024421570c557d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }
}
