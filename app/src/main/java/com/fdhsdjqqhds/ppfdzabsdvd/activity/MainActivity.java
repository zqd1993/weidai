package com.fdhsdjqqhds.ppfdzabsdvd.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.fragment.MainFragment;
import com.fdhsdjqqhds.ppfdzabsdvd.fragment.SetFragment;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.utils.MyToast;
import com.fdhsdjqqhds.ppfdzabsdvd.utils.PreferencesOpenUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabAdapter tabAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        StatusBarUtil.setLightMode(this);
        if (PreferencesOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.ebxfhrtu);
        tabModel.setSelectedIcon(R.drawable.zbxdfhrtsy);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.ewrtzxhxfh);
        tabModel1.setSelectedIcon(R.drawable.hzythxdfh);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.bxfdbbrdg);
        tabModel2.setSelectedIcon(R.drawable.gdfgresyhz);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
//        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainFragment());
//        fragments.add(new ProductFragment());
        fragments.add(new SetFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    public void jumpMine(){
        tabAdapter.getData().get(0).setChecked(false);
        tabAdapter.getData().get(1).setChecked(true);
        mainViewPager.setCurrentItem(1, false);
        tabAdapter.notifyDataSetChanged();
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new TabAdapter(R.layout.adapter_tab, tabModels);
            tabAdapter.setHasStableIds(true);
            tabAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 2));
            bottomRvy.setAdapter(tabAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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
                MyToast.showShort("再按一次退出程序");
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
