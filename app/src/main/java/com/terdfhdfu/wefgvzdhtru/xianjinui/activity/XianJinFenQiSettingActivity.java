package com.terdfhdfu.wefgvzdhtru.xianjinui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.terdfhdfu.wefgvzdhtru.ActivityCollector;
import com.terdfhdfu.wefgvzdhtru.R;
import com.terdfhdfu.wefgvzdhtru.mvp.XActivity;
import com.terdfhdfu.wefgvzdhtru.router.Router;
import com.terdfhdfu.wefgvzdhtru.xianjinui.LoginXianJinFenQiActivity;
import com.terdfhdfu.wefgvzdhtru.utilsxianjin.XianJinFenQiSharedPreferencesUtilis;
import com.terdfhdfu.wefgvzdhtru.utilsxianjin.StatusBarUtilXianJinFenQi;
import com.terdfhdfu.wefgvzdhtru.utilsxianjin.XianJinFenQiToastUtil;
import com.terdfhdfu.wefgvzdhtru.xinajinwidget.NormalXianJinFenQiDialog;
import com.terdfhdfu.wefgvzdhtru.xinajinwidget.SwitchButtonXianJinFenQi;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;

public class XianJinFenQiSettingActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchButtonXianJinFenQi switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    public Bitmap createBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap createBitmapOnHide(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap createBitmapTransparentBg(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] createBitmapOnHideToBytes(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

    private String phone = "";
    private boolean isPush = false;

    private NormalXianJinFenQiDialog normalXianJinFenQiDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilXianJinFenQi.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = XianJinFenQiSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = XianJinFenQiSharedPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchButtonXianJinFenQi.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                XianJinFenQiSharedPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                XianJinFenQiToastUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalXianJinFenQiDialog = new NormalXianJinFenQiDialog(this);
            normalXianJinFenQiDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalXianJinFenQiDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalXianJinFenQiDialog.dismiss();
                        XianJinFenQiSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginXianJinFenQiActivity.class)
                                .launch();
                    }).show();
        });
    }

    public Bitmap bgdyshhf(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap oyujghj(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap zdvdfyrtuy(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] erytxhfgh(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_xian_jin_setting;
    }

    @Override
    public Object newP() {
        return null;
    }

    public Bitmap qwergzd(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap mgkhyui(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap vsdgrtyfghfd(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] ertghzdhy(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

}
