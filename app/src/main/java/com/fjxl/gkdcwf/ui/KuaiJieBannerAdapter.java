package com.fjxl.gkdcwf.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fjxl.gkdcwf.R;
import com.fjxl.gkdcwf.mainapi.KuaiJieApi;
import com.fjxl.gkdcwf.imageloader.ILFactory;
import com.fjxl.gkdcwf.imageloader.ILoader;
import com.fjxl.gkdcwf.bean.ProductModel;
import com.youth.banner.adapter.BannerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class KuaiJieBannerAdapter extends BannerAdapter<ProductModel, KuaiJieBannerAdapter.ImageHolder> {

    private BannerClickedListener bannerClickedListener;

    public KuaiJieBannerAdapter(List<ProductModel> datas) {
        super(datas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_kuaijie_banner_item, parent, false);
        return new KuaiJieBannerAdapter.ImageHolder(view);
    }
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";
    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        /**
         * commit操作使用了SharedPreferencesCompat.apply进行了替代，目的是尽可能的使用apply代替commit
         * 因为commit方法是同步的，并且我们很多时候的commit操作都是UI线程中，毕竟是IO操作，尽可能异步；
         */
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    @Override
    public void onBindView(ImageHolder holder, ProductModel data, int position, int size) {
        holder.daikuan_name_tv.setText(data.getProductName());
        holder.tedian_tv.setText(data.getTag());
        holder.qian_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.shijian_tv.setText(data.getDes() + "个月");
        holder.renshu_tv.setText(String.valueOf(data.getPassingRate()));
        ILFactory.getLoader().loadNet(holder.daikuan_pic, KuaiJieApi.HTTP_API_URL + data.getProductLogo(),
                new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        holder.parentLl.setOnClickListener(v -> {
            if (bannerClickedListener != null){
                bannerClickedListener.onBannerClicked(data);
            }
        });
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author dj
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

    public class ImageHolder extends RecyclerView.ViewHolder{

        TextView daikuan_name_tv;
        TextView tedian_tv;
        TextView qian_tv;
        TextView shijian_tv;
        TextView renshu_tv;
        View parentLl;
        ImageView daikuan_pic;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            daikuan_name_tv = itemView.findViewById(R.id.daikuan_name_tv);
            tedian_tv = itemView.findViewById(R.id.tedian_tv);
            qian_tv = itemView.findViewById(R.id.qian_tv);
            shijian_tv = itemView.findViewById(R.id.shijian_tv);
            renshu_tv = itemView.findViewById(R.id.renshu_tv);
            parentLl = itemView.findViewById(R.id.parent_ll);
            daikuan_pic = itemView.findViewById(R.id.daikuan_pic);
        }
    }
    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }
    public void setBannerClickedListener(BannerClickedListener bannerClickedListener){
        this.bannerClickedListener = bannerClickedListener;
    }

    public interface BannerClickedListener{
        void onBannerClicked(ProductModel entity);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
    }
    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

}
