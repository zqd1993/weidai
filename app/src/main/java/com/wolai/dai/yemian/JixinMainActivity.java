package com.wolai.dai.yemian;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.wolai.dai.R;
import com.wolai.dai.gongju.JiXinPreferencesOpenUtil;
import com.wolai.dai.suipian.JixinMainFragment;
import com.wolai.dai.suipian.JixinProductFragment;
import com.wolai.dai.suipian.JixinSetFragment;
import com.wolai.dai.mvp.XActivity;
import com.wolai.dai.gongju.JiXinMyToast;
import com.wolai.dai.gongju.JiXinStatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class JixinMainActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private JixinTabAdapter tabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        JiXinStatusBarUtil.setTransparent(this, false);
        JiXinStatusBarUtil.setLightMode(this);
        if (JiXinPreferencesOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
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
        fragments.add(new JixinMainFragment());
//        fragments.add(new JixinProductFragment());
        fragments.add(new JixinSetFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new JixinFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new JixinTabAdapter(R.layout.jixin_adapter_tab, tabModels);
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
        return R.layout.jixin_activity_main;
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
                JiXinMyToast.showShort("再按一次退出程序");
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
