package com.fghyugfg.mjkyhgb.threesoxonea;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fghyugfg.mjkyhgb.R;
import com.fghyugfg.mjkyhgb.mvp.XActivity;
import com.fghyugfg.mjkyhgb.threesoxoneu.MyToastThreeSixOne;
import com.fghyugfg.mjkyhgb.threesoxoneu.ThreeSixOneOpenUtil;
import com.fghyugfg.mjkyhgb.threesoxoneu.PreferencesThreeSixOneOpenUtil;
import com.fghyugfg.mjkyhgb.threesoxoneu.StatusBarUtilThreeSixOne;
import com.fghyugfg.mjkyhgb.threesoxonew.ThreeSixOneRemindDialog;

import butterknife.BindView;

public class SetActivityThreeSixOne extends XActivity {

    @BindView(R.id.tuijian_ll)
    View tuijian_ll;
    @BindView(R.id.tuijian_tv)
    TextView tuijian_tv;
    @BindView(R.id.zhuxiao_ll)
    View zhuxiao_ll;
    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.biaoti_tv)
    TextView biaoti_tv;

    private ThreeSixOneRemindDialog dialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilThreeSixOne.setTransparent(this, false);
        biaoti_tv.setText("系统设置");
        tuijian_tv.setText(PreferencesThreeSixOneOpenUtil.getBool("tuijian") ? "开启" : "关闭");
        back_image.setOnClickListener(v -> {
            finish();
        });
        tuijian_ll.setOnClickListener(v -> {
            dialog = new ThreeSixOneRemindDialog(this).setCancelText("开启")
                    .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
            dialog.setOnButtonClickListener(new ThreeSixOneRemindDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    MyToastThreeSixOne.showShort("关闭成功");
                    tuijian_tv.setText("关闭");
                    PreferencesThreeSixOneOpenUtil.saveBool("tuijian", false);
                    dialog.dismiss();
                }

                @Override
                public void onCancelClicked() {
                    MyToastThreeSixOne.showShort("开启成功");
                    tuijian_tv.setText("开启");
                    PreferencesThreeSixOneOpenUtil.saveBool("tuijian", true);
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
        zhuxiao_ll.setOnClickListener(v -> {
            ThreeSixOneOpenUtil.jumpPage(this, ZhuXiaoActivityThreeSixOne.class);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_three_six_one;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        super.onDestroy();
    }
}
