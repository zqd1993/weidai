package com.tryrbdfbv.grtregdfh.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tryrbdfbv.grtregdfh.R;
import com.tryrbdfbv.grtregdfh.base.SimpleRecAdapter;
import com.tryrbdfbv.grtregdfh.kit.KnifeKit;
import com.tryrbdfbv.grtregdfh.model.MineItemModelJieJie;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MineJieJieAdapter extends SimpleRecAdapter<MineItemModelJieJie, MineJieJieAdapter.ViewHolder> {

    public MineJieJieAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
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
    public int getLayoutId() {
        return R.layout.adapter_mine_item_jiejie;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        viewHolder.parentLl.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, data.get(i), 1, viewHolder);
        });
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> mghdfgsdfh(String gsonString) {
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
    public static <T> Map<String, T> rtyfghfs(String gsonString) {
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
    public static <T> List<T> nfsdfsgdfg(String json, int  type) {
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


        @BindView(R.id.item_title_tv)
        TextView tvItem;
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.parent_ll)
        View parentLl;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> kgdfgdsdg(String gsonString) {
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
    public static <T> Map<String, T> uytjdfgs(String gsonString) {
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
    public static <T> List<T> bsdfgasdfgds(String json, int  type) {
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
