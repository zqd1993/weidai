package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.linghuojieasdufne.vbdsetrrte.R;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmapi.HttpApiLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmf.LingHuoJieSvsdFdProductFragment;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmf.MainFragmentLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmf.SetFragmentLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.BaseModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.ConfigEntityLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.mvp.XActivity;
import com.linghuojieasdufne.vbdsetrrte.net.ApiSubscriber;
import com.linghuojieasdufne.vbdsetrrte.net.NetError;
import com.linghuojieasdufne.vbdsetrrte.net.XApi;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.MyToastLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.PreferencesOpenUtilLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.LingHuoJieSvsdFdStatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LingHuoJieSvsdFdMainActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabAdapterLingHuoJieSvsdFd tabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        LingHuoJieSvsdFdStatusBarUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.rdtusaery);
        tabModel.setSelectedIcon(R.drawable.xcvbery);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.xcvbserya);
        tabModel1.setSelectedIcon(R.drawable.bzrdsruy);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.xcvbaerya);
        tabModel2.setSelectedIcon(R.drawable.srtaserya);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainFragmentLingHuoJieSvsdFd());
        fragments.add(new LingHuoJieSvsdFdProductFragment());
        fragments.add(new SetFragmentLingHuoJieSvsdFd());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new LingHuoJieSvsdFdFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new TabAdapterLingHuoJieSvsdFd(R.layout.adapter_ling_huo_jie_djs_urng_tab, tabModels);
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
        return R.layout.activity_main_ling_huo_jie_djs_urng;
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
                MyToastLingHuoJieSvsdFd.showShort("再按一次退出程序");
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
        HttpApiLingHuoJieSvsdFd.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilLingHuoJieSvsdFd.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesOpenUtilLingHuoJieSvsdFd.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
