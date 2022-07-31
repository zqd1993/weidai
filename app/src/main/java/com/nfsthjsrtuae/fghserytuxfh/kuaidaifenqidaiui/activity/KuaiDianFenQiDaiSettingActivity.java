package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nfsthjsrtuae.fghserytuxfh.ActivityCollector;
import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XActivity;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.LoginKuaiDianFenQiDaiActivity;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.SharedPreferencesKuaiDianFenQiDaiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.StatusBarUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.ToastUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiwidget.NormalKuaiDianFenQiDaiDialog;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiwidget.KuaiDianFenQiDaiSwitchButton;

import butterknife.BindView;

public class KuaiDianFenQiDaiSettingActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    KuaiDianFenQiDaiSwitchButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private NormalKuaiDianFenQiDaiDialog normalKuaiDianFenQiDaiDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilKuaiDianFenQiDai.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SharedPreferencesKuaiDianFenQiDaiUtilis.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new KuaiDianFenQiDaiSwitchButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SharedPreferencesKuaiDianFenQiDaiUtilis.saveBoolIntoPref("push", isChecked);
                ToastUtilKuaiDianFenQiDai.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalKuaiDianFenQiDaiDialog = new NormalKuaiDianFenQiDaiDialog(this);
            normalKuaiDianFenQiDaiDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalKuaiDianFenQiDaiDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalKuaiDianFenQiDaiDialog.dismiss();
                        SharedPreferencesKuaiDianFenQiDaiUtilis.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginKuaiDianFenQiDaiActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_kuai_dian_fen_qi_dai;
    }

    @Override
    public Object newP() {
        return null;
    }
}
