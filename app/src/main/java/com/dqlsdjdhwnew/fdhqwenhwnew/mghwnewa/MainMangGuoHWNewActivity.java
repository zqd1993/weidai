package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dqlsdjdhwnew.fdhqwenhwnew.R;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewapi.HttpApiMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewf.MainMangGuoHWNewFragment;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewf.ProductFragmentMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewf.SetMangGuoHWNewFragment;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewBaseModel;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewConfigEntity;
import com.dqlsdjdhwnew.fdhqwenhwnew.mvp.XActivity;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.ApiSubscriber;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.NetError;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.XApi;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.MyMangGuoHWNewToast;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.PreferencesOpenUtilMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.StatusBarUtilMangGuoHWNew;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainMangGuoHWNewActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabMangGuoHWNewAdapter tabMangGuoHWNewAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilMangGuoHWNew.setTransparent(this, false);
        if (PreferencesOpenUtilMangGuoHWNew.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.xfhsreay);
        tabModel.setSelectedIcon(R.drawable.rtyxdfhsr);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.dfhrtu);
        tabModel1.setSelectedIcon(R.drawable.ffhdrt);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.sezrsry);
        tabModel2.setSelectedIcon(R.drawable.jjhsfhgae);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainMangGuoHWNewFragment());
        fragments.add(new ProductFragmentMangGuoHWNew());
        fragments.add(new SetMangGuoHWNewFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentMangGuoHWNewAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabMangGuoHWNewAdapter == null){
            tabMangGuoHWNewAdapter = new TabMangGuoHWNewAdapter(R.layout.adapter_mang_guo_hw_new_tab, tabModels);
            tabMangGuoHWNewAdapter.setHasStableIds(true);
            tabMangGuoHWNewAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabMangGuoHWNewAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mang_guo_hw_new_main;
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
                MyMangGuoHWNewToast.showShort("再按一次退出程序");
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
        HttpApiMangGuoHWNew.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<MangGuoHWNewBaseModel<MangGuoHWNewConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(MangGuoHWNewBaseModel<MangGuoHWNewConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilMangGuoHWNew.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesOpenUtilMangGuoHWNew.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
