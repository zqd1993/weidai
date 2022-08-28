package com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.xiaoniukaudiakuandsfwet.vsdaetgerat.R;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.ApiSubscriber;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.NetError;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.XApi;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.apicxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewHttpApi;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.fragmentcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewMainFragment;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.fragmentcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewProductFragment;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.fragmentcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewSetFragment;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.mvp.XActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewConfigEntity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewBaseModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewMyToast;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewStatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class XiaoNiuHuaDaiKuanOpNewMainActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private XiaoNiuHuaDaiKuanOpNewTabAdapter xiaoNiuHuaDaiKuanOpNewTabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        XiaoNiuHuaDaiKuanOpNewStatusBarUtil.setTransparent(this, false);
        XiaoNiuHuaDaiKuanOpNewStatusBarUtil.setLightMode(this);
        if (XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.ldtyusery);
        tabModel.setSelectedIcon(R.drawable.lyijdrsrtuy);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.ldtyusery);
        tabModel1.setSelectedIcon(R.drawable.werfzdhgjh);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.qrergxj);
        tabModel2.setSelectedIcon(R.drawable.kldrtserty);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new XiaoNiuHuaDaiKuanOpNewMainFragment());
        fragments.add(new XiaoNiuHuaDaiKuanOpNewProductFragment());
        fragments.add(new XiaoNiuHuaDaiKuanOpNewSetFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new XiaoNiuHuaDaiKuanOpNewFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public void jumpMine(){
        xiaoNiuHuaDaiKuanOpNewTabAdapter.getData().get(0).setChecked(false);
        xiaoNiuHuaDaiKuanOpNewTabAdapter.getData().get(1).setChecked(true);
        mainViewPager.setCurrentItem(1, false);
        xiaoNiuHuaDaiKuanOpNewTabAdapter.notifyDataSetChanged();
    }

    private void initAdapter(){
        if (xiaoNiuHuaDaiKuanOpNewTabAdapter == null){
            xiaoNiuHuaDaiKuanOpNewTabAdapter = new XiaoNiuHuaDaiKuanOpNewTabAdapter(R.layout.adapter_xiao_niu_hua_dai_kuan_op_new_tab, tabModels);
            xiaoNiuHuaDaiKuanOpNewTabAdapter.setHasStableIds(true);
            xiaoNiuHuaDaiKuanOpNewTabAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(xiaoNiuHuaDaiKuanOpNewTabAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_xiao_niu_hua_dai_kuan_op_new;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int tyitykjfghk(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int wereay(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int trydgj(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                XiaoNiuHuaDaiKuanOpNewMyToast.showShort("再按一次退出程序");
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
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int puyikfgkty(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int zzfdyrey(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int ertywtrdhg(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
    }

    public void getValue() {
        XiaoNiuHuaDaiKuanOpNewHttpApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
