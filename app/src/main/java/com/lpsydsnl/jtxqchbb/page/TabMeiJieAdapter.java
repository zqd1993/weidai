package com.lpsydsnl.jtxqchbb.page;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lpsydsnl.jtxqchbb.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TabMeiJieAdapter extends BaseQuickAdapter<MainActivityMeiJie.TabModel, BaseViewHolder> {

    private ClickedListener clickedListener;

    public TabMeiJieAdapter(int layoutResId, @Nullable List<MainActivityMeiJie.TabModel> data) {
        super(layoutResId, data);
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean isConnected(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MainActivityMeiJie.TabModel item) {
        ImageView tabImg = helper.getView(R.id.tab_img);
        TextView tabName = helper.getView(R.id.tab_name);
        tabName.setText(item.getName());
        if (item.isChecked()){
            Glide.with(mContext).load(item.getSelectedIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            Glide.with(mContext).load(item.getIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.color_bdbdc5));
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

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean bfggdf(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean cdsaerw(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public void setClickedListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }

    public interface ClickedListener{
        void onClick(int position);
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean htrfgh(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean dfere(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }
}
