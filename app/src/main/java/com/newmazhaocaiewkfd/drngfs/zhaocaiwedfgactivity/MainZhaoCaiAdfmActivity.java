package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.newmazhaocaiewkfd.drngfs.R;
import com.newmazhaocaiewkfd.drngfs.net.ApiSubscriber;
import com.newmazhaocaiewkfd.drngfs.net.NetError;
import com.newmazhaocaiewkfd.drngfs.net.XApi;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgapi.ZhaoCaiAdfmHttpApi;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgfragment.MainFragmentZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgfragment.ProductZhaoCaiAdfmFragment;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgfragment.SetZhaoCaiAdfmFragment;
import com.newmazhaocaiewkfd.drngfs.mvp.XActivity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.BaseModelZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.ConfigZhaoCaiAdfmEntity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.MyZhaoCaiAdfmToas;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.PreferencesOpenUtilZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.StatusBarZhaoCaiAdfmUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainZhaoCaiAdfmActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabZhaoCaiAdfmAdapter tabZhaoCaiAdfmAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarZhaoCaiAdfmUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.qwwtersy);
        tabModel.setSelectedIcon(R.drawable.xxvcnbsrtuy);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.zdfgher);
        tabModel1.setSelectedIcon(R.drawable.srthcgh);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.zzbeary);
        tabModel2.setSelectedIcon(R.drawable.lltisru);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainFragmentZhaoCaiAdfm());
        fragments.add(new ProductZhaoCaiAdfmFragment());
        fragments.add(new SetZhaoCaiAdfmFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentAdapterZhaoCaiAdfm(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    public void jumpMine(){
        tabZhaoCaiAdfmAdapter.getData().get(0).setChecked(false);
        tabZhaoCaiAdfmAdapter.getData().get(1).setChecked(true);
        mainViewPager.setCurrentItem(1, false);
        tabZhaoCaiAdfmAdapter.notifyDataSetChanged();
    }

    private void initAdapter(){
        if (tabZhaoCaiAdfmAdapter == null){
            tabZhaoCaiAdfmAdapter = new TabZhaoCaiAdfmAdapter(R.layout.adapter_zhao_cai_endfi_weng_tab, tabModels);
            tabZhaoCaiAdfmAdapter.setHasStableIds(true);
            tabZhaoCaiAdfmAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabZhaoCaiAdfmAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_zhao_cai_endfi_weng;
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
                MyZhaoCaiAdfmToas.showShort("再按一次退出程序");
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
        ZhaoCaiAdfmHttpApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelZhaoCaiAdfm<ConfigZhaoCaiAdfmEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                    }

                    @Override
                    public void onNext(BaseModelZhaoCaiAdfm<ConfigZhaoCaiAdfmEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilZhaoCaiAdfm.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesOpenUtilZhaoCaiAdfm.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
