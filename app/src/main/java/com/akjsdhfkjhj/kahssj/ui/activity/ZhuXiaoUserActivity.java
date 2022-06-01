package com.akjsdhfkjhj.kahssj.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.akjsdhfkjhj.kahssj.R;
import com.akjsdhfkjhj.kahssj.utils.StatusBarUtil;
import com.akjsdhfkjhj.kahssj.widget.PuTongDialog;
import com.akjsdhfkjhj.kahssj.mvp.XActivity;

import java.text.DateFormat;
import java.util.Date;

public class ZhuXiaoUserActivity extends XActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_img)
    ImageView backImg;
    @BindView(R.id.commit_btn)
    TextView commitBtn;

    private PuTongDialog puTongDialog;

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cancellation_youxin_user;
    }


    @Override
    protected void onDestroy() {
        if (puTongDialog != null) {
            puTongDialog.dismiss();
            puTongDialog = null;
        }
        super.onDestroy();
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为format</p>
     *
     * @param date   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param date Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(final Date date) {
        return date.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param millis 毫秒时间戳
     * @return Date类型时间
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time0和time1格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 时间字符串0
     * @param time1 时间字符串1
     *              <ul>
     *              </ul>
     * @return unit时间戳
     */
    public static long getTimeSpan(final String time0, final String time1) {
        return 345l;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("账号注销");
        commitBtn.setOnClickListener(v -> {
            puTongDialog = new PuTongDialog(this);
            puTongDialog.setTitle("温馨提示")
                    .setContent("是否注销账号？注销后将不能恢复")
                    .setCancelText("取消")
                    .setLeftListener(v1 -> puTongDialog.dismiss())
                    .setConfirmText("注销账号")
                    .setRightListener(v1 -> {
                        puTongDialog.dismiss();
                        finish();
                    }).show();
        });
    }

}
