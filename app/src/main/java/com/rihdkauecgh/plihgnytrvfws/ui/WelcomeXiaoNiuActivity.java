package com.rihdkauecgh.plihgnytrvfws.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesXiaoNiuUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StaticUtilXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.utils.StatusXiaoNiuBarUtil;
import com.rihdkauecgh.plihgnytrvfws.router.Router;

import com.rihdkauecgh.plihgnytrvfws.http.ApiXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.widget.WelcomeXiaoNiuDialog;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeXiaoNiuActivity extends XActivity {

    private WelcomeXiaoNiuDialog welcomeXiaoNiuDialog;

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
        welcomeXiaoNiuDialog = new WelcomeXiaoNiuDialog(this, "温馨提示");
        welcomeXiaoNiuDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    WelcomeXiaoNiuActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        welcomeXiaoNiuDialog.setOnClickedListener(new WelcomeXiaoNiuDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedPreferencesXiaoNiuUtilis.saveStringIntoPref("uminit", "1");
                SharedPreferencesXiaoNiuUtilis.saveBoolIntoPref("agree", true);
                welcomeXiaoNiuDialog.dismiss();
                StaticUtilXiaoNiu.getValue(WelcomeXiaoNiuActivity.this, LoginXiaoNiuActivity.class, null, true);
            }

            @Override
            public void bottomBtnClicked() {
                WelcomeXiaoNiuActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", ApiXiaoNiu.PRIVACY_POLICY);
                StaticUtilXiaoNiu.getValue(WelcomeXiaoNiuActivity.this, XiaoNiuWebViewActivity.class, bundle);
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", ApiXiaoNiu.USER_SERVICE_AGREEMENT);
                StaticUtilXiaoNiu.getValue(WelcomeXiaoNiuActivity.this, XiaoNiuWebViewActivity.class, bundle);
            }
        });
        welcomeXiaoNiuDialog.show();
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
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7738.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                SharedPreferencesXiaoNiuUtilis.saveStringIntoPref("HTTP_API_URL", "http://" + net[0]);
                                SharedPreferencesXiaoNiuUtilis.saveStringIntoPref("AGREEMENT", net[1]);
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
            if (!TextUtils.isEmpty(loginPhone)) {
                StaticUtilXiaoNiu.getValue(WelcomeXiaoNiuActivity.this, HomePageActivityXiaoNiu.class, null, true);
            } else {
                StaticUtilXiaoNiu.getValue(WelcomeXiaoNiuActivity.this, LoginXiaoNiuActivity.class, null, true);
            }
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
        if (welcomeXiaoNiuDialog != null) {
            welcomeXiaoNiuDialog.dismiss();
            welcomeXiaoNiuDialog = null;
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

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusXiaoNiuBarUtil.setTransparent(this, false);
        isAgree = SharedPreferencesXiaoNiuUtilis.getBoolFromPref("agree");
        loginPhone = SharedPreferencesXiaoNiuUtilis.getStringFromPref("phone");
//        sendRequestWithOkHttp();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpPage();
            }
        }, 500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_xiaoniu_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
