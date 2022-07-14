package com.gdzfgesry.nbfgnwaet.adapterqianbao;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gdzfgesry.nbfgnwaet.R;
import com.gdzfgesry.nbfgnwaet.base.SimpleRecAdapter;
import com.gdzfgesry.nbfgnwaet.kit.KnifeKit;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.GoodsQianBaoModel;
import com.gdzfgesry.nbfgnwaet.netqianbao.QianBaoApi;

import butterknife.BindView;

public class GoodsItemAdapterQianBao extends SimpleRecAdapter<GoodsQianBaoModel, GoodsItemAdapterQianBao.ViewHolder> {

    public GoodsItemAdapterQianBao(Context context) {
        super(context);
    }

    public static String vgsdtgsrt(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double stfysert(Object o) {

        return toDouble(o, 0);
    }

    public static double bsrtysrg(Object o, int defaultValue) {
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

    public static long dfhrty(Object o, long defaultValue) {
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
        return R.layout.layout_product_qian_bao_item_meijie;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GoodsQianBaoModel model = data.get(i);
        viewHolder.productNameTv.setText(model.getTitle());
        Glide.with(context).load(QianBaoApi.API_BASE_URL + model.getImgs()).into(viewHolder.productImg);
//        ILFactory.getLoader().loadNet(viewHolder.productImg, Api.API_BASE_URL + model.getImgs(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        viewHolder.text_1.setText("·" + model.getMax_money());
        viewHolder.text_2.setText("·" + model.getDay_money());
        viewHolder.text_3.setText("·" + model.getInfo());
        viewHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
    }

    public static String ndfghrty(Object o) {
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

    public static double hsrtrtys(Object o, int defaultValue) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name_tv)
        TextView productNameTv;
        @BindView(R.id.product_img)
        ImageView productImg;
        @BindView(R.id.click_view)
        View clickView;
        @BindView(R.id.text_1)
        TextView text_1;
        @BindView(R.id.text_2)
        TextView text_2;
        @BindView(R.id.text_3)
        TextView text_3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double gsdrtysfgh(Object o) {

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

    public static long erdfg(Object o, long defaultValue) {
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
