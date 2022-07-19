package com.dfgderv.erterqweq.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.dfgderv.erterqweq.R;
import com.dfgderv.erterqweq.utils.SharedPreferencesUtilis;
import com.dfgderv.erterqweq.utils.StatusBarUtil;
import com.dfgderv.erterqweq.widget.NormalDialog;
import com.dfgderv.erterqweq.mvp.XActivity;

import java.util.Date;

public class CancellationUserActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.commit_btn)
    TextView commitBtn;

    private NormalDialog normalDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        if (SharedPreferencesUtilis.getBoolFromPref("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            normalDialog = new NormalDialog(this);
            normalDialog.setTitle("温馨提示")
                    .setContent("是否注销账号？注销后将不能恢复")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> normalDialog.dismiss())
                    .setConfirmText("注销账号")
                    .setRightListener(v1 -> {
                        normalDialog.dismiss();
//                        SharedPreferencesUtilis.saveStringIntoPref("phone", "");
//                        Router.newIntent(CancellationAccountActivity.this)
//                                .to(LoginActivity.class)
//                                .launch();
                        finish();
                    }).show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_user;
    }

    @Override
    public Object newP() {
        return null;
    }

    /***
     * 获取评论的时间串
     */
    public static String getCommentTimeString(String str)
    {
        float diff=0;
        int index = 0;
        String reslut ="";
        Date date;
//        for(index=0;index<checkDiff.length;index++)
//        {
//            diff = getDateDiff(date,checkDiff[index]);
//            if(diff>1)
//            {
//                break;
//            }
//        }

        if(index==0)
        {
            reslut = String.format("%d年前",(int)diff);
        }else if(index == 1){
            reslut = String.format("%d月前",(int)diff);
        }else if(index ==2){
            if(diff>1f && diff<2f)
                reslut = "昨天";
            else {
                reslut = String.format("%d天前",(int)diff);
            }
        }else if(index == 3){
            int min = (int) ((diff - (int)diff)*60);
            reslut = String.format("今天%d小时%d分",(int)diff,min);
        }else if(index == 4){
            reslut = String.format("%d分钟前",(int)diff);
        }else if(index == 5){
            if(diff<10f)
                reslut = "刚刚";
        }
        return reslut;
    }

    @Override
    protected void onDestroy() {
        if (normalDialog != null) {
            normalDialog.dismiss();
            normalDialog = null;
        }
        super.onDestroy();
    }
}
