package com.jiekuanzhijiasdoert.fghirtidfks.adapterjiekuanzhijia;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jiekuanzhijiasdoert.fghirtidfks.R;
import com.jiekuanzhijiasdoert.fghirtidfks.base.SimpleRecAdapter;
import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.JieKuanZhiJiaGoodsModel;
import com.jiekuanzhijiasdoert.fghirtidfks.utilsjiekuanzhijia.JieKuanZhiJiaSharedPreferencesUtilis;
import com.jiekuanzhijiasdoert.fghirtidfks.kit.KnifeKit;

import butterknife.BindView;

public class GoodsItemAdapterJieKuanZhiJia1 extends SimpleRecAdapter<JieKuanZhiJiaGoodsModel, GoodsItemAdapterJieKuanZhiJia1.ViewHolder> {

    public GoodsItemAdapterJieKuanZhiJia1(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_goods_item_jie_kuan_zhi_jia_1;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        JieKuanZhiJiaGoodsModel model = data.get(i);
        if (!TextUtils.isEmpty(model.getFan_time()) && model.getFan_time().length() > 2) {
            viewHolder.cycleTv.setText("最长可分期" + model.getFan_time().substring(0, 2) + "期");
        }
         if (i % 2 == 0){
            viewHolder.parent_fl.setBackgroundResource(R.drawable.cvbndrtusr);
        } else {
             viewHolder.parent_fl.setBackgroundResource(R.drawable.bnzxfgusrtu);
         }
        viewHolder.people_num_tv.setText(model.getNum() + "人申请");
        viewHolder.productNameTv.setText(model.getTitle());
        viewHolder.info_tv.setText(model.getInfo());
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            Glide.with(context).load(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL") + model.getImgs()).into(viewHolder.productImg);
        }
        viewHolder.limitTv.setText(model.getMax_money());
        viewHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name_tv)
        TextView productNameTv;
        @BindView(R.id.product_img)
        ImageView productImg;
        @BindView(R.id.limit_tv)
        TextView limitTv;
        @BindView(R.id.cycle_tv)
        TextView cycleTv;
        @BindView(R.id.click_view)
        View clickView;
        @BindView(R.id.people_num_tv)
        TextView people_num_tv;
        @BindView(R.id.info_tv)
        TextView info_tv;
        @BindView(R.id.parent_fl)
        View parent_fl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
