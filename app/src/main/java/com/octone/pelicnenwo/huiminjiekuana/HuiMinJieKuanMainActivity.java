package com.octone.pelicnenwo.huiminjiekuana;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.octone.pelicnenwo.R;
import com.octone.pelicnenwo.f.HuiMinJieKuanMainFragment;
import com.octone.pelicnenwo.f.ProductFragmentHuiMinJieKuan;
import com.octone.pelicnenwo.f.SetHuiMinJieKuanFragment;
import com.octone.pelicnenwo.huiminjiekuanapi.HttpApiHuiMinJieKuan;
import com.octone.pelicnenwo.huiminjiekuanm.HuiMinJieKuanBaseModel;
import com.octone.pelicnenwo.huiminjiekuanm.HuiMinJieKuanConfigEntity;
import com.octone.pelicnenwo.mvp.XActivity;
import com.octone.pelicnenwo.huiminjiekuanu.HuiMinJieKuanMyToast;
import com.octone.pelicnenwo.huiminjiekuanu.HuiMinJieKuanPreferencesOpenUtil;
import com.octone.pelicnenwo.huiminjiekuanu.StatusBarUtilHuiMinJieKuan;
import com.octone.pelicnenwo.net.ApiSubscriber;
import com.octone.pelicnenwo.net.NetError;
import com.octone.pelicnenwo.net.XApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HuiMinJieKuanMainActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private HuiMinJieKuanTabAdapter huiMinJieKuanTabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilHuiMinJieKuan.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.xyedry);
        tabModel.setSelectedIcon(R.drawable.rtuxfghj);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.ldty);
        tabModel1.setSelectedIcon(R.drawable.xnxzft);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.xzrytuj);
        tabModel2.setSelectedIcon(R.drawable.xzxtfhu);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new HuiMinJieKuanMainFragment());
        fragments.add(new ProductFragmentHuiMinJieKuan());
        fragments.add(new SetHuiMinJieKuanFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentHuiMinJieKuanAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (huiMinJieKuanTabAdapter == null){
            huiMinJieKuanTabAdapter = new HuiMinJieKuanTabAdapter(R.layout.adapter_hui_min_jie_kuan_tab, tabModels);
            huiMinJieKuanTabAdapter.setHasStableIds(true);
            huiMinJieKuanTabAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(huiMinJieKuanTabAdapter);
        }
    }

    public void changePage(){
        if (huiMinJieKuanTabAdapter != null){
            huiMinJieKuanTabAdapter.getData().get(0).setChecked(false);
            huiMinJieKuanTabAdapter.getData().get(1).setChecked(true);
            huiMinJieKuanTabAdapter.getData().get(2).setChecked(false);
            mainViewPager.setCurrentItem(1, false);
            huiMinJieKuanTabAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_hui_min_jie_kuan;
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
                HuiMinJieKuanMyToast.showShort("再按一次退出程序");
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
        HttpApiHuiMinJieKuan.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<HuiMinJieKuanBaseModel<HuiMinJieKuanConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                    }

                    @Override
                    public void onNext(HuiMinJieKuanBaseModel<HuiMinJieKuanConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                HuiMinJieKuanPreferencesOpenUtil.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (HuiMinJieKuanPreferencesOpenUtil.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
