package com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.activityqueqianmaboss;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.queqianmasdfjiert.bdafgawetr.ActivityCollector;
import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.LoginQueQianMaBossActivity;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.SharedPreferencesUtilisQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.ToastQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.mvp.XActivity;
import com.queqianmasdfjiert.bdafgawetr.router.Router;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.StatusBarQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.widgetqueqianmaboss.NormalQueQianMaBossDialog;
import com.queqianmasdfjiert.bdafgawetr.widgetqueqianmaboss.SwitchQueQianMaBossButton;

import butterknife.BindView;

public class QueQianMaBossSettingActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchQueQianMaBossButton switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone = "";
    private boolean isPush = false;

    private NormalQueQianMaBossDialog normalQueQianMaBossDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarQueQianMaBossUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SharedPreferencesUtilisQueQianMaBoss.getBoolFromPref("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchQueQianMaBossButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SharedPreferencesUtilisQueQianMaBoss.saveBoolIntoPref("push", isChecked);
                ToastQueQianMaBossUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalQueQianMaBossDialog = new NormalQueQianMaBossDialog(this);
            normalQueQianMaBossDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> {
                        normalQueQianMaBossDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v2 -> {
                        normalQueQianMaBossDialog.dismiss();
                        SharedPreferencesUtilisQueQianMaBoss.saveStringIntoPref("phone", "");
                        ActivityCollector.finishAll();
                        Router.newIntent(this)
                                .to(LoginQueQianMaBossActivity.class)
                                .launch();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_que_qian_ma_boss_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
