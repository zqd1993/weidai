package com.retdfhxfghj.bdrhrtuyfgj.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.retdfhxfghj.bdrhrtuyfgj.R;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.LoginStatusModelXinYong;
import com.retdfhxfghj.bdrhrtuyfgj.mvp.XActivity;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.ApiSubscriber;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.NetError;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.XApi;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongutils.SharedPreferencesXinYongUtilis;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongutils.StatusXinYongBarUtil;
import com.retdfhxfghj.bdrhrtuyfgj.router.Router;

import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.XinYongApi;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongwidget.WelcomeXinYongDialog;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XinYongWelcomeActivity extends XActivity {

    private WelcomeXinYongDialog welcomeXinYongDialog;

    private Bundle bundle;

    private boolean isAgree = false, isResume = false;

    private String loginPhone = "";

    public float getRatio(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap loadFile2MemoryVersion10(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

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
        welcomeXinYongDialog = new WelcomeXinYongDialog(this, "温馨提示");
        welcomeXinYongDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    XinYongWelcomeActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        welcomeXinYongDialog.setOnClickedListener(new WelcomeXinYongDialog.OnClickedListener() {
            @Override
            public void topBtnClicked() {
                SharedPreferencesXinYongUtilis.saveStringIntoPref("uminit", "1");
                SharedPreferencesXinYongUtilis.saveBoolIntoPref("agree", true);
                Router.newIntent(XinYongWelcomeActivity.this)
                        .to(LoginActivityXinYong.class)
                        .launch();
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                XinYongWelcomeActivity.this.finish();
            }

            @Override
            public void registrationAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 1);
                bundle.putString("url", XinYongApi.getZc());
                Router.newIntent(XinYongWelcomeActivity.this)
                        .to(WebViewXinYongActivity.class)
                        .data(bundle)
                        .launch();
            }

            @Override
            public void privacyAgreementClicked() {
                bundle = new Bundle();
                bundle.putInt("tag", 2);
                bundle.putString("url", XinYongApi.getYs());
                Router.newIntent(XinYongWelcomeActivity.this)
                        .to(WebViewXinYongActivity.class)
                        .data(bundle)
                        .launch();
            }
        });
        welcomeXinYongDialog.show();
    }

    public float getRhsreydfhatio(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap rtuyjgk(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/k-xygj.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);//调用json解析的方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);//新建json对象实例
            String net = jsonObject.getString("data");//取得其名字的值，一般是字符串
            if (!TextUtils.isEmpty(net)) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("tag", 1);
//                bundle.putString("url", net);
//                Router.newIntent(WelcomeActivity.this)
//                        .to(WebViewActivity.class)
//                        .data(bundle)
//                        .launch();
//                finish();
                XinYongApi.API_BASE_URL = net;
                SharedPreferencesXinYongUtilis.saveStringIntoPref("API_BASE_URL", net);
                Thread.sleep(1000);
                getGankData();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float tuhfgjdrt(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap zzrtfdhy(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

    private void jumpPage() {
        if (isAgree) {
            if (!TextUtils.isEmpty(loginPhone)) {
                Router.newIntent(XinYongWelcomeActivity.this)
                        .to(XinYongHomePageActivity.class)
                        .launch();
            } else {
                Router.newIntent(XinYongWelcomeActivity.this)
                        .to(LoginActivityXinYong.class)
                        .launch();
            }
            finish();
        } else {
            showDialog();
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesXinYongUtilis.getStringFromPref("API_BASE_URL"))) {
            XinYongApi.getGankService().getGankData()
                    .compose(XApi.<LoginStatusModelXinYong>getApiTransformer())
                    .compose(XApi.<LoginStatusModelXinYong>getScheduler())
                    .compose(this.<LoginStatusModelXinYong>bindToLifecycle())
                    .subscribe(new ApiSubscriber<LoginStatusModelXinYong>() {
                        @Override
                        protected void onFail(NetError error) {
                            jumpPage();
                        }

                        @Override
                        public void onNext(LoginStatusModelXinYong loginStatusModelXinYong) {
                            if (loginStatusModelXinYong != null) {
                                SharedPreferencesXinYongUtilis.saveBoolIntoPref("is_agree_check", "0".equals(loginStatusModelXinYong.getIs_agree_check()));
                                SharedPreferencesXinYongUtilis.saveBoolIntoPref("is_code_register", "0".equals(loginStatusModelXinYong.getIs_code_register()));
                                jumpPage();
                            }
                        }
                    });
        }
    }

    public float iutyufdh(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap mdtyujdtuhj(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (welcomeXinYongDialog != null) {
            welcomeXinYongDialog.dismiss();
            welcomeXinYongDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusXinYongBarUtil.setTransparent(this, false);
        isAgree = SharedPreferencesXinYongUtilis.getBoolFromPref("agree");
        loginPhone = SharedPreferencesXinYongUtilis.getStringFromPref("phone");
        sendRequestWithOkHttp();
    }

    public float werdytry(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap rtyfghjdf(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_xin_yong_weclome;
    }

    @Override
    public Object newP() {
        return null;
    }
}
