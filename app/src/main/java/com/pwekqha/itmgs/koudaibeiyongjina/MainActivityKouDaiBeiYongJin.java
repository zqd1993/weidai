package com.pwekqha.itmgs.koudaibeiyongjina;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.pwekqha.itmgs.R;
import com.pwekqha.itmgs.koudaibeiyongjinapi.HttpKouDaiBeiYongJinApi;
import com.pwekqha.itmgs.koudaibeiyongjinf.MainKouDaiBeiYongJinFragment;
import com.pwekqha.itmgs.koudaibeiyongjinf.ProductKouDaiBeiYongJinFragment;
import com.pwekqha.itmgs.koudaibeiyongjinf.SetKouDaiBeiYongJinFragment;
import com.pwekqha.itmgs.koudaibeiyongjinm.BaseKouDaiBeiYongJinModel;
import com.pwekqha.itmgs.koudaibeiyongjinm.ConfigKouDaiBeiYongJinEntity;
import com.pwekqha.itmgs.mvp.XActivity;
import com.pwekqha.itmgs.net.ApiSubscriber;
import com.pwekqha.itmgs.net.NetError;
import com.pwekqha.itmgs.net.XApi;
import com.pwekqha.itmgs.koudaibeiyongjinu.MyKouDaiBeiYongJinToast;
import com.pwekqha.itmgs.koudaibeiyongjinu.KouDaiBeiYongJinPreferencesOpenUtil;
import com.pwekqha.itmgs.koudaibeiyongjinu.StatusBarKouDaiBeiYongJinUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivityKouDaiBeiYongJin extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabKouDaiBeiYongJinAdapter tabKouDaiBeiYongJinAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarKouDaiBeiYongJinUtil.setTransparent(this, false);
        if (KouDaiBeiYongJinPreferencesOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.jjsdrtsru);
        tabModel.setSelectedIcon(R.drawable.ghxfsrtu);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.dxtyruygfj);
        tabModel1.setSelectedIcon(R.drawable.llgyopfysdr);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.cvbnrdtusr);
        tabModel2.setSelectedIcon(R.drawable.csrtugj);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainKouDaiBeiYongJinFragment());
        fragments.add(new ProductKouDaiBeiYongJinFragment());
        fragments.add(new SetKouDaiBeiYongJinFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new KouDaiBeiYongJinFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabKouDaiBeiYongJinAdapter == null){
            tabKouDaiBeiYongJinAdapter = new TabKouDaiBeiYongJinAdapter(R.layout.adapter_kou_dai_bei_yong_jin_tab, tabModels);
            tabKouDaiBeiYongJinAdapter.setHasStableIds(true);
            tabKouDaiBeiYongJinAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabKouDaiBeiYongJinAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_kou_dai_bei_yong_jin_main;
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
                MyKouDaiBeiYongJinToast.showShort("再按一次退出程序");
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
        HttpKouDaiBeiYongJinApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinModel<ConfigKouDaiBeiYongJinEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseKouDaiBeiYongJinModel<ConfigKouDaiBeiYongJinEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                KouDaiBeiYongJinPreferencesOpenUtil.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (KouDaiBeiYongJinPreferencesOpenUtil.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
