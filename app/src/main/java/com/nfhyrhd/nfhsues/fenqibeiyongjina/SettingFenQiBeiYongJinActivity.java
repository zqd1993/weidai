package com.nfhyrhd.nfhsues.fenqibeiyongjina;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nfhyrhd.nfhsues.R;
import com.nfhyrhd.nfhsues.fenqibeiyongjinapi.HttpApiFenQiBeiYongJin;
import com.nfhyrhd.nfhsues.fenqibeiyongjinm.FenQiBeiYongJinBaseModel;
import com.nfhyrhd.nfhsues.fenqibeiyongjinm.ConfigEntityFenQiBeiYongJin;
import com.nfhyrhd.nfhsues.mvp.XActivity;
import com.nfhyrhd.nfhsues.net.ApiSubscriber;
import com.nfhyrhd.nfhsues.net.NetError;
import com.nfhyrhd.nfhsues.net.XApi;
import com.nfhyrhd.nfhsues.fenqibeiyongjinu.MyToastFenQiBeiYongJin;
import com.nfhyrhd.nfhsues.fenqibeiyongjinu.OpenFenQiBeiYongJinUtil;
import com.nfhyrhd.nfhsues.fenqibeiyongjinu.PreferencesFenQiBeiYongJinOpenUtil;
import com.nfhyrhd.nfhsues.fenqibeiyongjinu.StatusBarFenQiBeiYongJinUtil;
import com.nfhyrhd.nfhsues.fenqibeiyongjinw.RemindFenQiBeiYongJinDialog;
import com.nfhyrhd.nfhsues.fenqibeiyongjinw.SwitchButtonFenQiBeiYongJin;

import butterknife.BindView;

public class SettingFenQiBeiYongJinActivity extends XActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchButtonFenQiBeiYongJin switchBtn;
    @BindView(R.id.biaoti_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;
    @BindView(R.id.mail_fl)
    View mailFl;
    @BindView(R.id.mail_tv)
    TextView mail_tv;

    private String phone = "";
    private boolean isPush = false;

    private RemindFenQiBeiYongJinDialog normalDialog;
    private String mailStr = "";
    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarFenQiBeiYongJinUtil.setTransparent(this, false);
        if (PreferencesFenQiBeiYongJinOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        backImg.setOnClickListener(v -> finish());
        mailStr = PreferencesFenQiBeiYongJinOpenUtil.getString("app_mail");
        titleTv.setText("设置");
        getConfig();
        phone = PreferencesFenQiBeiYongJinOpenUtil.getString("phone");
        phoneTv.setText(phone);
        isPush = PreferencesFenQiBeiYongJinOpenUtil.getBool("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchButtonFenQiBeiYongJin.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                PreferencesFenQiBeiYongJinOpenUtil.saveBool("push", isChecked);
                MyToastFenQiBeiYongJin.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            normalDialog = new RemindFenQiBeiYongJinDialog(this);
            OpenFenQiBeiYongJinUtil.getValue(this, FenQiBeiYongJinZhuXiaoActivity.class, null);
        });
        mailFl.setOnClickListener(v -> {
            copyStr();
        });
    }

    private void copyStr(){
        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, mailStr);
        clipboard.setPrimaryClip(clipData);
        MyToastFenQiBeiYongJin.showShort("复制成功");
    }

    public void getConfig() {
        HttpApiFenQiBeiYongJin.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<FenQiBeiYongJinBaseModel<ConfigEntityFenQiBeiYongJin>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(FenQiBeiYongJinBaseModel<ConfigEntityFenQiBeiYongJin> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                mail_tv.setText(mailStr);
                                PreferencesFenQiBeiYongJinOpenUtil.saveString("app_mail", mailStr);
                            }
                        }
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fen_qi_bei_yong_jin_setting;
    }

    @Override
    public Object newP() {
        return null;
    }
}
