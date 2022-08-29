package com.qznagsdnfdkdjdf.cvpdfms.aaaadkmiaoxia;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qznagsdnfdkdjdf.cvpdfms.R;
import com.qznagsdnfdkdjdf.cvpdfms.base.SimpleRecAdapter;
import com.qznagsdnfdkdjdf.cvpdfms.imageloader.ILFactory;
import com.qznagsdnfdkdjdf.cvpdfms.imageloader.ILoader;
import com.qznagsdnfdkdjdf.cvpdfms.kit.KnifeKit;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.DKMiaoXiaOpGoodsModel;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.ApiDKMiaoXiaOp;

import butterknife.BindView;

public class GoodsItemAdapterDKMiaoXiaOp extends SimpleRecAdapter<DKMiaoXiaOpGoodsModel, GoodsItemAdapterDKMiaoXiaOp.ViewHolder> {

    public GoodsItemAdapterDKMiaoXiaOp(Context context) {
        super(context);
    }

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_goods_item_dk_miao_xia_op_new;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        DKMiaoXiaOpGoodsModel model = data.get(i);
        if (!TextUtils.isEmpty(model.getDes()) && model.getDes().length() > 2) {
            viewHolder.cycleTv.setText("周期" + model.getDes() + "个月");
        }
        viewHolder.passingRateTv.setText(String.valueOf(model.getPassingRate()));
        viewHolder.tagTv.setText(model.getTag());
        viewHolder.productNameTv.setText(model.getProductName());
            ILFactory.getLoader().loadNet(viewHolder.productImg, ApiDKMiaoXiaOp.API_BASE_URL + model.getProductLogo(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        viewHolder.limitTv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
        viewHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.parent_fl)
        View parentFl;
        @BindView(R.id.passing_rate_tv)
        TextView passingRateTv;
        @BindView(R.id.tag_tv)
        TextView tagTv;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    public static String atshfxh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double dfsgsery(Object o) {

        return toDouble(o, 0);
    }

    public static double tyudgj(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long hthdfh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

}
