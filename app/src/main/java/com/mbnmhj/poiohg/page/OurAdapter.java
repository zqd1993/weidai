package com.mbnmhj.poiohg.page;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mbnmhj.poiohg.R;
import com.mbnmhj.poiohg.entity.SettingModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OurAdapter extends BaseQuickAdapter<SettingModel, BaseViewHolder> {

    private OnClickListener onClickListener;

    public static boolean isTramp(String str) {
        Pattern pa = Pattern.compile("^[\u4e00-\u9fa5]*$");
        Matcher matcher = pa.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public OurAdapter(int layoutResId, @Nullable List<SettingModel> data) {
        super(layoutResId, data);
    }

    public static String storeState(String state) {
        if (TextUtils.isEmpty(state)) return "";
        return new String[]{"未认证", "认证中", "已认证", "审核未通过"}[Integer.parseInt(state)];
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SettingModel item) {
        ImageView imageView = helper.getView(R.id.set_pic);
        helper.setText(R.id.set_name, item.getName());
        Glide.with(mContext).load(item.getPic()).into(imageView);
        helper.getView(R.id.parent_fl).setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClicked(helper.getLayoutPosition());
            }
        });
    }

    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /*
    校验过程：
    1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
    2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
    3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
    */

    public interface OnClickListener {
        void onClicked(int position);
    }

}
