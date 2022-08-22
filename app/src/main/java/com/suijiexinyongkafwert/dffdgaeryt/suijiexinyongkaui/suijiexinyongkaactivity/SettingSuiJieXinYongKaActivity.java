package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkaactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.suijiexinyongkafwert.dffdgaeryt.ActivityCollector;
import com.suijiexinyongkafwert.dffdgaeryt.R;
import com.suijiexinyongkafwert.dffdgaeryt.mvp.XActivity;
import com.suijiexinyongkafwert.dffdgaeryt.router.Router;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.LoginSuiJieXinYongKaActivity;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.SuiJieXinYongKaSharedPreferencesUtilis;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.StatusBarUtilSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.ToastSuiJieXinYongKaUtil;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkawidget.NormalSuiJieXinYongKaDialog;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkawidget.SwitchSuiJieXinYongKaButton;

import butterknife.BindView;

public class SettingSuiJieXinYongKaActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchSuiJieXinYongKaButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private NormalSuiJieXinYongKaDialog normalSuiJieXinYongKaDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilSuiJieXinYongKa.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SuiJieXinYongKaSharedPreferencesUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchSuiJieXinYongKaButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SuiJieXinYongKaSharedPreferencesUtilis.saveBoolIntoPref("push", isChecked);
                ToastSuiJieXinYongKaUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalSuiJieXinYongKaDialog = new NormalSuiJieXinYongKaDialog(this);
            normalSuiJieXinYongKaDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalSuiJieXinYongKaDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalSuiJieXinYongKaDialog.dismiss();
                        SuiJieXinYongKaSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginSuiJieXinYongKaActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sui_jie_xin_yong_ka_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
