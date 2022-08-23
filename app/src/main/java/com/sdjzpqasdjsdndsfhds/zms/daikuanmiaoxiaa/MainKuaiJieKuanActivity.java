package com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaa;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.sdjzpqasdjsdndsfhds.zms.R;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaapi.KuaiJieKuanHttpApi;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaf.KuaiJieKuanMainFragment;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaf.ProductFragmentKuaiJieKuan;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiaf.SetKuaiJieKuanFragment;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiam.KuaiJieKuanBaseModel;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiam.ConfigEntityKuaiJieKuan;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.MyKuaiJieKuanToast;
import com.sdjzpqasdjsdndsfhds.zms.mvp.XActivity;
import com.sdjzpqasdjsdndsfhds.zms.net.ApiSubscriber;
import com.sdjzpqasdjsdndsfhds.zms.net.NetError;
import com.sdjzpqasdjsdndsfhds.zms.net.XApi;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.PreferencesOpenUtilKuaiJieKuan;
import com.sdjzpqasdjsdndsfhds.zms.daikuanmiaoxiau.StatusBarKuaiJieKuanUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainKuaiJieKuanActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private KuaiJieKuanTabAdapter tabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarKuaiJieKuanUtil.setTransparent(this, false);
        if (PreferencesOpenUtilKuaiJieKuan.getBool("NO_RECORD")) {
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
        fragments.add(new KuaiJieKuanMainFragment());
        fragments.add(new ProductFragmentKuaiJieKuan());
        fragments.add(new SetKuaiJieKuanFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentAdapterKuaiJieKuan(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new KuaiJieKuanTabAdapter(R.layout.adapte_kuai_jie_kuan_tab, tabModels);
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
        return R.layout.activity_main_kuai_jie_kuan;
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
                MyKuaiJieKuanToast.showShort("再按一次退出程序");
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
        KuaiJieKuanHttpApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<KuaiJieKuanBaseModel<ConfigEntityKuaiJieKuan>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(KuaiJieKuanBaseModel<ConfigEntityKuaiJieKuan> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilKuaiJieKuan.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesOpenUtilKuaiJieKuan.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
