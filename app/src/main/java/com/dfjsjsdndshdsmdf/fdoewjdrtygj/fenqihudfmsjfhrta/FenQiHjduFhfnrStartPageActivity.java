package com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.dfjsjsdndshdsmdf.fdoewjdrtygj.R;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtapi.HttpFenQiHjduFhfnrApi;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.mvp.XActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.OpeFenQiHjduFhfnrUti;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.PreferencFenQiHjduFhfnrOpenUtil;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.FenQiHjduFhfnrStatusBarUtil;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtw.StartPageFenQiHjduFhfnrRemindDialo;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FenQiHjduFhfnrStartPageActivity extends XActivity {

    private Bundle bundle;

    private boolean isSure = false, isResume = false;

    private String phone = "";

    private StartPageFenQiHjduFhfnrRemindDialo startPageKuaiFqRemindDialo;

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
        Looper.prepare();
        startPageKuaiFqRemindDialo = new StartPageFenQiHjduFhfnrRemindDialo(this);
        startPageKuaiFqRemindDialo.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    FenQiHjduFhfnrStartPageActivity.this.finish();
                    return false;
                }
                return true;
            }
        });
        startPageKuaiFqRemindDialo.setOnListener(new StartPageFenQiHjduFhfnrRemindDialo.OnListener() {
            @Override
            public void oneBtnClicked() {
//                initUm();
                PreferencFenQiHjduFhfnrOpenUtil.saveBool("isSure", true);
                startPageKuaiFqRemindDialo.dismiss();
                OpeFenQiHjduFhfnrUti.getValue(FenQiHjduFhfnrStartPageActivity.this, FenQiHjduFhfnrDlActivity.class, null, true);
            }

            @Override
            public void zcxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", HttpFenQiHjduFhfnrApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                OpeFenQiHjduFhfnrUti.getValue(FenQiHjduFhfnrStartPageActivity.this, JumpH5FenQiHjduFhfnrActivity.class, bundle);
            }

            @Override
            public void twoBtnClicked() {
                finish();
            }

            @Override
            public void ysxyClicked() {
                bundle = new Bundle();
                bundle.putString("url", HttpFenQiHjduFhfnrApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                OpeFenQiHjduFhfnrUti.getValue(FenQiHjduFhfnrStartPageActivity.this, JumpH5FenQiHjduFhfnrActivity.class, bundle);
            }
        });
        startPageKuaiFqRemindDialo.show();
        Looper.loop();
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
                            .url("https://ossbj0714.oss-cn-beijing.aliyuncs.com/server7753.txt")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (!TextUtils.isEmpty(responseData)) {
                        if (responseData.contains(",")) {
                            String[] net = responseData.split(",");
                            if (net.length > 1) {
                                PreferencFenQiHjduFhfnrOpenUtil.saveString("HTTP_API_URL", "http://" + net[0]);
                                PreferencFenQiHjduFhfnrOpenUtil.saveString("AGREEMENT", net[1]);
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
                OpeFenQiHjduFhfnrUti.getValue(FenQiHjduFhfnrStartPageActivity.this, FenQiHjduFhfnrDlActivity.class, null, true);
            } else {
                OpeFenQiHjduFhfnrUti.getValue(FenQiHjduFhfnrStartPageActivity.this, MainFenQiHjduFhfnrActivity.class, null, true);
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
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        FenQiHjduFhfnrStatusBarUtil.setTransparent(this, false);
        isSure = PreferencFenQiHjduFhfnrOpenUtil.getBool("isSure");
        phone = PreferencFenQiHjduFhfnrOpenUtil.getString("phone");
        sendRequestWithOkHttp();
//        jumpPage();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_page_fen_qas_han_jnfe;
    }

    @Override
    public Object newP() {
        return null;
    }
}
