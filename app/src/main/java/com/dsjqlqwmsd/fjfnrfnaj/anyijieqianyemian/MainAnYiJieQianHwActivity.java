package com.dsjqlqwmsd.fjfnrfnaj.anyijieqianyemian;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dsjqlqwmsd.fjfnrfnaj.R;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiangongju.PreferencesAnYiJieQianHwOpenUtil;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianjiekou.AnYiJieQianHwApi;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianshiti.BaseAnYiJieQianHwModel;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianshiti.ConfigAnYiJieQianHwEntity;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiansuipian.MainAnYiJieQianHwFragment;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiansuipian.SetAnYiJieQianHwFragment;
import com.dsjqlqwmsd.fjfnrfnaj.mvp.XActivity;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiangongju.AnYiJieQianHwMyToast;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiangongju.StatusBarAnYiJieQianHwUtil;
import com.dsjqlqwmsd.fjfnrfnaj.net.ApiSubscriber;
import com.dsjqlqwmsd.fjfnrfnaj.net.NetError;
import com.dsjqlqwmsd.fjfnrfnaj.net.XApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainAnYiJieQianHwActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabAnYiJieQianHwAdapter tabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarAnYiJieQianHwUtil.setTransparent(this, false);
        StatusBarAnYiJieQianHwUtil.setLightMode(this);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.vbgeds);
        tabModel.setSelectedIcon(R.drawable.lkusc);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.rtgexvs);
        tabModel1.setSelectedIcon(R.drawable.sdrtgvb);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.qwewwfx);
        tabModel2.setSelectedIcon(R.drawable.zswrfd);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
//        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainAnYiJieQianHwFragment());
//        fragments.add(new ProductWeiXxyongHuaFragment());
        fragments.add(new SetAnYiJieQianHwFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentAnYiJieQianHwAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new TabAnYiJieQianHwAdapter(R.layout.an_yi_jie_qian__adapter_tab, tabModels);
            tabAdapter.setHasStableIds(true);
            tabAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 2));
            bottomRvy.setAdapter(tabAdapter);
        }
    }

    public void jumpMore(){
        mainViewPager.setCurrentItem(1, false);
        for (int i = 0; i < tabModels.size(); i++){
            if (i == 1){
                tabModels.get(i).setChecked(true);
            } else {
                tabModels.get(i).setChecked(false);
            }
        }
        tabAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutId() {
        return R.layout.an_yi_jie_qian_activity_main;
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
                AnYiJieQianHwMyToast.showShort("再按一次退出程序");
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
        AnYiJieQianHwApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseAnYiJieQianHwModel<ConfigAnYiJieQianHwEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                    }

                    @Override
                    public void onNext(BaseAnYiJieQianHwModel<ConfigAnYiJieQianHwEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesAnYiJieQianHwOpenUtil.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesAnYiJieQianHwOpenUtil.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
