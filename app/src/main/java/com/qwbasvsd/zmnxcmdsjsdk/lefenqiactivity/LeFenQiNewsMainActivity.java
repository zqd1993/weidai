package com.qwbasvsd.zmnxcmdsjsdk.lefenqiactivity;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.qwbasvsd.zmnxcmdsjsdk.R;
import com.qwbasvsd.zmnxcmdsjsdk.fragment.MainFragmentLeFenQiNews;
import com.qwbasvsd.zmnxcmdsjsdk.fragment.LeFenQiNewsSetFragment;
import com.qwbasvsd.zmnxcmdsjsdk.fragment.ProductLeFenQiNewsFragment;
import com.qwbasvsd.zmnxcmdsjsdk.mvp.XActivity;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.MyToastLeFenQiNews;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.LeFenQiNewsPreferencesOpenUtil;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.StatusBarUtilLeFenQiNews;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LeFenQiNewsMainActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private LeFenQiNewsTabAdapter leFenQiNewsTabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int sp2px(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilLeFenQiNews.setTransparent(this, false);
        StatusBarUtilLeFenQiNews.setLightMode(this);
        if (LeFenQiNewsPreferencesOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.msrthfgh);
        tabModel.setSelectedIcon(R.drawable.krthfgjh);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.msrthfgh);
        tabModel1.setSelectedIcon(R.drawable.msrthfgh);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.kltyhsdf);
        tabModel2.setSelectedIcon(R.drawable.ertgdfh);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainFragmentLeFenQiNews());
        fragments.add(new ProductLeFenQiNewsFragment());
        fragments.add(new LeFenQiNewsSetFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentAdapterLeFenQiNews(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    public void jumpMine(){
        leFenQiNewsTabAdapter.getData().get(0).setChecked(false);
        leFenQiNewsTabAdapter.getData().get(1).setChecked(true);
        mainViewPager.setCurrentItem(1, false);
        leFenQiNewsTabAdapter.notifyDataSetChanged();
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int utjgj(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float aertgfhfgh(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float wqrdgh(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    private void initAdapter(){
        if (leFenQiNewsTabAdapter == null){
            leFenQiNewsTabAdapter = new LeFenQiNewsTabAdapter(R.layout.adapter_tab_le_fen_qi, tabModels);
            leFenQiNewsTabAdapter.setHasStableIds(true);
            leFenQiNewsTabAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(leFenQiNewsTabAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_le_fen_qi_main;
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
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int irthgjdfurt(Context context, float spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float wetsdfh(Context context, float pxVal)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float nsrtysdfh(Context context, float pxVal)
    {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                MyToastLeFenQiNews.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
