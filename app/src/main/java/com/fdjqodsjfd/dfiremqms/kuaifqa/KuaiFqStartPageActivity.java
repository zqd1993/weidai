package com.fdjqodsjfd.dfiremqms.kuaifqa;

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
import android.util.Log;
import android.view.KeyEvent;

import com.fdjqodsjfd.dfiremqms.R;
import com.fdjqodsjfd.dfiremqms.kuaifqapi.HttpApiKuaiFq;
import com.fdjqodsjfd.dfiremqms.mvp.XActivity;
import com.fdjqodsjfd.dfiremqms.kuaifqu.OpeKuaiFqnUti;
import com.fdjqodsjfd.dfiremqms.kuaifqu.PreferencKuaiFqOpenUtil;
import com.fdjqodsjfd.dfiremqms.kuaifqu.KuaiFqStatusBarUtil;
import com.fdjqodsjfd.dfiremqms.kuaifqw.StartPageKuaiFqRemindDialo;
import com.umeng.commonsdk.UMConfigure;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class KuaiFqStartPageActivity extends XActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private StartPageKuaiFqRemindDialo startPageKuaiFqRemindDialo;

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
        startPageKuaiFqRemindDialo = new StartPageKuaiFqRemindDialo(this);
        startPageKuaiFqRemindDialo.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    KuaiFqStartPageActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        startPageKuaiFqRemindDialo.setOnListener(new StartPageKuaiFqRemindDialo.OnListener() {
            @Override
            public void oneBtnClicked() {
//                initUm();
                PreferencKuaiFqOpenUtil.saveBool("isSure", true);
                startPageKuaiFqRemindDialo.dismiss();
                OpeKuaiFqnUti.getValue(KuaiFqStartPageActivity.this, KuaiFqDlActivity.class, null, true);
            }

            @Override
            public void zcxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", HttpApiKuaiFq.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                OpeKuaiFqnUti.getValue(KuaiFqStartPageActivity.this, KuaiFqJumpH5Activity.class, bundle);
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", HttpApiKuaiFq.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                OpeKuaiFqnUti.getValue(KuaiFqStartPageActivity.this, KuaiFqJumpH5Activity.class, bundle);
            }
        });
        startPageKuaiFqRemindDialo.show();
    }

    public float kdtyjhgj(String imageFile) {
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

    public Bitmap veveryfgh(Context context, Uri uri, int w, int h) {
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
                            .url("https://luosedk1.oss-cn-shenzhen.aliyuncs.com/server7733.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                PreferencKuaiFqOpenUtil.saveString("HTTP_API_URL", "http://" + net[0]);
                                PreferencKuaiFqOpenUtil.saveString("AGREEMENT", net[1]);
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

    private void jumpPage() {
        if (isSure) {
//            initUm();
            if (TextUtils.isEmpty(phone)) {
                OpeKuaiFqnUti.getValue(KuaiFqStartPageActivity.this, KuaiFqDlActivity.class, null, true);
            } else {
                OpeKuaiFqnUti.getValue(KuaiFqStartPageActivity.this, MainActivityKuaiFq.class, null, true);
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
            UMConfigure.init(this, "629eff2005844627b5a41d7f", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        KuaiFqStatusBarUtil.setTransparent(this, false);
        isSure = PreferencKuaiFqOpenUtil.getBool("isSure");
        phone = PreferencKuaiFqOpenUtil.getString("phone");
//        sendRequestWithOkHttp();
        jumpPage();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_page_kuai_fq;
    }

    @Override
    public Object newP() {
        return null;
    }
}
