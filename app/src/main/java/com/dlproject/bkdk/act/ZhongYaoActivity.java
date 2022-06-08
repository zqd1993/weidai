package com.dlproject.bkdk.act;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dlproject.bkdk.R;
import com.dlproject.bkdk.fra.ParentFragment;
import com.dlproject.bkdk.fra.ChanPinFragment;
import com.dlproject.bkdk.fra.SheZhiFragment;
import com.dlproject.bkdk.mvp.XActivity;
import com.dlproject.bkdk.uti.TiShi;
import com.dlproject.bkdk.uti.ZhuangTaiLanUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZhongYaoActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private XuanXiangAdapter tabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    /**
     * 历史地址
     */
    public final static String HISTORYADDRESS = "HISTORYADDRESS";
    /**
     * 扫描类型
     */
    public final static String QRCODETYPE = "QRCODETYPE";

    public final static String BARCODE = "BARCODE";

    //    获取默认文件存储目录
    public static String getUpdateFileDir() {
        return "amez/apk/";
    }

    public final static String INTENT_KEY = "update_dialog_values";

    // 360手机助手
    public static final String MARKET = "com.qihoo.appstore";
    // 淘宝手机助手
    public static final String MARKET_TAOBAO = "com.taobao.appcenter";
    // 应用宝
    public static final String MARKET_QQDOWNLOADER = "com.tencent.Android.qqdownloader";
    // 安卓市场
    public static final String MARKET_HIAPK = "com.hiapk.marketpho";
    // 安智市场
    public static final String MARKET_GOAPK = "cn.goapk.market";
    // 包名
    public static final String APP_PACKAGE_NAME = "com.*.*";

    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        ZhuangTaiLanUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.iopiop);
        tabModel.setSelectedIcon(R.drawable.tytuj);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.iouioui);
        tabModel1.setSelectedIcon(R.drawable.rtrytr);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.asddfd);
        tabModel2.setSelectedIcon(R.drawable.tyuyiu);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new ParentFragment());
        fragments.add(new ChanPinFragment());
        fragments.add(new SheZhiFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new SuiPianAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    /**
     * 启动App
     *
     * @param context
     */
    public static void launchapp(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager()
                    .getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 跳转到应用市场
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }


    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new XuanXiangAdapter(R.layout.adapter_xuanxiang, tabModels);
            tabAdapter.setHasStableIds(true);
            tabAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabAdapter);
        }
    }

    /**
     * 跳转到应用市场
     */
    public static void werwef(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_parent;
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

    /**
     * 跳转到应用市场
     */
    public static void rtyh(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                TiShi.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 跳转到应用市场
     */
    public static void uyiymghj(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

}
