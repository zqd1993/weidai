package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaapi.DaiKuanMiaoXiaHttpApi;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaf.DaiKuanMiaoXiaMainFragment;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaf.ProductFragmentDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaf.SetDaiKuanMiaoXiaFragment;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.DaiKuanMiaoXiaBaseModel;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.ConfigEntityDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XActivity;
import com.sdyqwjqwias.fdpwejqwdjew.net.ApiSubscriber;
import com.sdyqwjqwias.fdpwejqwdjew.net.NetError;
import com.sdyqwjqwias.fdpwejqwdjew.net.XApi;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.MyDaiKuanMiaoXiaToast;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.PreferencesOpenUtilDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.StatusBarDaiKuanMiaoXiaUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainDaiKuanMiaoXiaActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private DaiKuanMiaoXiaTabAdapter tabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarDaiKuanMiaoXiaUtil.setTransparent(this, false);
        if (PreferencesOpenUtilDaiKuanMiaoXia.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.llpfysruy);
        tabModel.setSelectedIcon(R.drawable.wqqrty);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.xfghjsruta);
        tabModel1.setSelectedIcon(R.drawable.xcvgjsrtu);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.zzvhsrtyuas);
        tabModel2.setSelectedIcon(R.drawable.zzfhseryu);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new DaiKuanMiaoXiaMainFragment());
        fragments.add(new ProductFragmentDaiKuanMiaoXia());
        fragments.add(new SetDaiKuanMiaoXiaFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentAdapterDaiKuanMiaoXia(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new DaiKuanMiaoXiaTabAdapter(R.layout.adapter_dai_kuan_miao_xia_tab, tabModels);
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
        return R.layout.activity_main_dai_kuan_miao_xia;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                MyDaiKuanMiaoXiaToast.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
    }

    public void getValue() {
        DaiKuanMiaoXiaHttpApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<DaiKuanMiaoXiaBaseModel<ConfigEntityDaiKuanMiaoXia>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(DaiKuanMiaoXiaBaseModel<ConfigEntityDaiKuanMiaoXia> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilDaiKuanMiaoXia.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesOpenUtilDaiKuanMiaoXia.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
