package com.tryrbdfbv.grtregdfh.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tryrbdfbv.grtregdfh.R;
import com.tryrbdfbv.grtregdfh.base.SimpleRecAdapter;
import com.tryrbdfbv.grtregdfh.kit.KnifeKit;
import com.tryrbdfbv.grtregdfh.model.GoodsJieJieModel;
import com.tryrbdfbv.grtregdfh.net.ApiJieJie;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class GoodsItemAdapterJieJie extends SimpleRecAdapter<GoodsJieJieModel, GoodsItemAdapterJieJie.ViewHolder> {

    public GoodsItemAdapterJieJie(Context context) {
        super(context);
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> parseJsonToList(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_goods_item_new_jiejie;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        GoodsJieJieModel model = data.get(i);
            viewHolder.month_tv.setText("1-" + model.getFan_time());
        viewHolder.edu_tv.setText(String.valueOf(model.getMax_money()));
        viewHolder.month_tv.setText(model.getFan_time().substring(0, 2));
        viewHolder.productNameTv.setText(model.getTitle());
        viewHolder.day_money_tv.setText(model.getDay_money());
        Glide.with(context).load(ApiJieJie.API_BASE_URL + model.getImgs()).into(viewHolder.productImg);
//        ILFactory.getLoader().loadNet(viewHolder.productImg, ApiJieJie.API_BASE_URL + model.getImgs(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
//        viewHolder.limitTv.setText(model.getMax_money());
//        viewHolder.text_1.setText("·" + model.getMax_money());
//        viewHolder.text_2.setText("·" + model.getDay_money());
//        viewHolder.text_3.setText("·" + model.getInfo());
        viewHolder.clickView.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, model, 1, viewHolder);
        });
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> mghjsfdh(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> tyerhfg(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> bfsdf(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.parent_fl)
//        View parentFl;
//        @BindView(R.id.passing_rate_tv)
//        TextView passingRateTv;
        @BindView(R.id.day_money_tv)
        TextView day_money_tv;
        @BindView(R.id.product_name_tv)
        TextView productNameTv;
        @BindView(R.id.product_img)
        ImageView productImg;
        @BindView(R.id.edu_tv)
        TextView edu_tv;
        @BindView(R.id.month_tv)
        TextView month_tv;
        @BindView(R.id.click_view)
        View clickView;
//        @BindView(R.id.text_1)
//        TextView text_1;
//        @BindView(R.id.text_2)
//        TextView text_2;
//        @BindView(R.id.text_3)
//        TextView text_3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> iyjghjgn(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> asdbfgh(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> ytrhfgn(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

}
