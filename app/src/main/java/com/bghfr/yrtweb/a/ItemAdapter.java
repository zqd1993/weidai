package com.bghfr.yrtweb.a;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bghfr.yrtweb.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ItemAdapter extends BaseQuickAdapter<ZhuYeActivity.ItemModel, BaseViewHolder> {

    private ClickedListener clickedListener;

    public ItemAdapter(int layoutResId, @Nullable List<ZhuYeActivity.ItemModel> data) {
        super(layoutResId, data);
    }

    public static SpannableString formatDoublePrice(Double price) {
        String str = "";
        SpannableString spannableString = new SpannableString(str);
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(0.7f);
        spannableString.setSpan(relativeSizeSpan, str.indexOf(".") + 1, str.indexOf(".") + 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ZhuYeActivity.ItemModel item) {
        ImageView tabImg = helper.getView(R.id.tab_img);
        TextView tabName = helper.getView(R.id.tab_name);
        tabName.setText(item.getName());
        if (item.isChecked()){
            Glide.with(mContext).load(item.getSelectedIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            Glide.with(mContext).load(item.getIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.color_999999));
        }
        helper.getView(R.id.click_view).setOnClickListener(v -> {
            if (!item.isChecked()){
                item.setChecked(true);
                for (int i = 0; i < getData().size(); i++){
                    if (i != helper.getLayoutPosition()){
                        getData().get(i).setChecked(false);
                    }
                }
                if (clickedListener != null){
                    clickedListener.onClick(helper.getLayoutPosition());
                }
                notifyDataSetChanged();
            }
        });
    }

    public void setClickedListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }

    public static SpannableString updateTextColor(int endIndex, String str, Context context) {
        SpannableString spannableString = new SpannableString(str);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.color_2974b8));
        spannableString.setSpan(foregroundColorSpan, 0, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public interface ClickedListener{
        void onClick(int position);
    }
}
