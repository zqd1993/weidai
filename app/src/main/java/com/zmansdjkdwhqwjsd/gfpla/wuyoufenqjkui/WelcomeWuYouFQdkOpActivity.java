package com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zmansdjkdwhqwjsd.gfpla.R;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.SharedPreferencesWuYouFQdkOpUtilis;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.StatusWuYouFQdkOpBarUtil;
import com.zmansdjkdwhqwjsd.gfpla.router.Router;

import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.ApiWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkwidget.WelcomeWuYouFQdkOpDialog;
//import com.umeng.commonsdk.UMConfigure;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeWuYouFQdkOpActivity extends AppCompatActivity {

    private WelcomeWuYouFQdkOpDialog welcomeWuYouFQdkOpDialog;

    private Bundle bundle;

    private boolean isAgree = false, isResume = false;

    private String loginPhone = "";

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T changeGsonToBean(String gsonString, Class<T> cls) {
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
    public <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: " + e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wu_you_fen_qi_jk_op_weclome);
        StatusWuYouFQdkOpBarUtil.setTransparent(this, false);
        isAgree = SharedPreferencesWuYouFQdkOpUtilis.getBoolFromPref("agree");
        loginPhone = SharedPreferencesWuYouFQdkOpUtilis.getStringFromPref("phone");
//        sendRequestWithOkHttp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpPage();
            }
        }, 500);
    }

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isResume = false;
            }
        }, 500);
    }


    private void showDialog() {
//        Looper.prepare();
        welcomeWuYouFQdkOpDialog = new WelcomeWuYouFQdkOpDialog(this, "温馨提示");
        welcomeWuYouFQdkOpDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeWuYouFQdkOpActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeWuYouFQdkOpDialog.setOnClickedListener(new WelcomeWuYouFQdkOpDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                initUm();
                welcomeWuYouFQdkOpDialog.dismiss();
                SharedPreferencesWuYouFQdkOpUtilis.saveStringIntoPref("uminit", "1");
                SharedPreferencesWuYouFQdkOpUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(WelcomeWuYouFQdkOpActivity.this)
                        .to(LoginWuYouFQdkOpActivity.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeWuYouFQdkOpActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", ApiWuYouFQdkOp.PRIVACY_POLICY);
                    Router.newIntent(WelcomeWuYouFQdkOpActivity.this)
                            .to(WuYouFQdkOpWebViewActivity.class)
                            .data(bundle)
                            .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", ApiWuYouFQdkOp.USER_SERVICE_AGREEMENT);
                    Router.newIntent(WelcomeWuYouFQdkOpActivity.this)
                            .to(WuYouFQdkOpWebViewActivity.class)
                            .data(bundle)
                            .launch();
            }
        });
        welcomeWuYouFQdkOpDialog.show();
//        Looper.loop();
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T ityrurthjfg(String gsonString, Class<T> cls) {
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
    public <T> List<T> mdhdsgadrt(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: " + e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> tyrthdfgh(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> bnfgsgdg(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7741.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                SharedPreferencesWuYouFQdkOpUtilis.saveStringIntoPref("HTTP_API_URL", "http://" + net[0]);
                                SharedPreferencesWuYouFQdkOpUtilis.saveStringIntoPref("AGREEMENT", net[1]);
                                Thread.sleep(1000);
                                jumpPage();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T rtyhfshg(String gsonString, Class<T> cls) {
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
    public <T> List<T> msdydftg(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: " + e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> wrrgdfgh(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> nsfgadrt(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    private void jumpPage() {
        if (isAgree) {
            initUm();
            if (!TextUtils.isEmpty(loginPhone)) {
                Router.newIntent(WelcomeWuYouFQdkOpActivity.this)
                        .to(HomePageActivityWuYouFQdkOp.class)
                        .launch();
            } else {
                Router.newIntent(WelcomeWuYouFQdkOpActivity.this)
                        .to(LoginWuYouFQdkOpActivity.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (welcomeWuYouFQdkOpDialog != null) {
            welcomeWuYouFQdkOpDialog.dismiss();
            welcomeWuYouFQdkOpDialog = null;
        }
        super.onDestroy();
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T kzdfgsdfg(String gsonString, Class<T> cls) {
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
    public <T> List<T> zxvdfghfdg(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: " + e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> tyufhfghf(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> retbdgsdg(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    private void initUm() {
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
//        if (!UMConfigure.isInit) {
//            UMConfigure.setLogEnabled(true);
//            Log.d("youmeng", "zhuche chenggong");
//            //友盟正式初始化
////            UMConfigure.init(getApplicationContext(), UMConfigure.DEVICE_TYPE_PHONE, "Umeng");
//            // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
//            // 参数一：当前上下文context；
//            // 参数二：应用申请的Appkey（需替换）；
//            // 参数三：渠道名称；
//            // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
//            // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
//            UMConfigure.init(this, "62c007d705844627b5d4d246", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        }
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T bsdgsdt(String gsonString, Class<T> cls) {
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
    public <T> List<T> avbfdgcvb(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: " + e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> ewrtfdga(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> bdcadstsf(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
}
