package com.rtvfbgfh.yuiyjghn.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.rtvfbgfh.yuiyjghn.R;
import com.rtvfbgfh.yuiyjghn.f.HomeMainFragment;
import com.rtvfbgfh.yuiyjghn.f.RenRenGoodsFragment;
import com.rtvfbgfh.yuiyjghn.f.RenRenSetFragment;
import com.rtvfbgfh.yuiyjghn.mvp.XActivity;
import com.rtvfbgfh.yuiyjghn.u.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    public ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabAdapter tabAdapter;

    private List<Fragment> fragments;

    public static boolean isWifiConnected(Context mContext) {
        return isConnected(mContext, true, ConnectivityManager.TYPE_WIFI);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.tyhfg);
        tabModel.setSelectedIcon(R.drawable.tryr);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.fdgd);
        tabModel1.setSelectedIcon(R.drawable.ert);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.dfsd);
        tabModel2.setSelectedIcon(R.drawable.tyuy);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new HomeMainFragment());
        fragments.add(new RenRenGoodsFragment());
        fragments.add(new RenRenSetFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new RenRenFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    public void setCurrent(){
        mainViewPager.setCurrentItem(1, false);
        for (int i = 0; i < tabAdapter.getData().size(); i++){
            if (i == 1){
                tabAdapter.getData().get(i).setChecked(true);
            } else {
                tabAdapter.getData().get(i).setChecked(false);
            }
        }
        tabAdapter.notifyDataSetChanged();
    }

    public static boolean isNetworkConnected(Context mContext) {
        return isConnected(mContext, false, ConnectivityManager.TYPE_WIFI | ConnectivityManager.TYPE_MOBILE);
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new TabAdapter(R.layout.adapter_tab_bottom, tabModels);
            tabAdapter.setHasStableIds(true);
            tabAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_main;
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
    public Object newP() {
        return null;
    }

    public class TabModel{

        private int icon;

        private int selectedIcon;

        private String name;

        private boolean isChecked;

        public int getSelectedIcon() {
            return selectedIcon;
        }

        public void setSelectedIcon(int selectedIcon) {
            this.selectedIcon = selectedIcon;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
