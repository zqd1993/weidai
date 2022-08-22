package com.fdhsdjqqhds.ppfdzabsdvd.qufenqia;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqif.MainFragmentQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqif.ProductQuFenQiFragment;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqif.QuFenQiSetFragment;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.QuFenQiMyToast;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.PreferencesQuFenQiOpenUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.QuFenQiStatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivityQuFenQi extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabAdapterQuFenQi tabAdapterQuFenQi;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        QuFenQiStatusBarUtil.setTransparent(this, false);
        QuFenQiStatusBarUtil.setLightMode(this);
        if (PreferencesQuFenQiOpenUtil.getBool("NO_RECORD")) {
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
        fragments.add(new MainFragmentQuFenQi());
//        fragments.add(new ProductQuFenQiFragment());
        fragments.add(new QuFenQiSetFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentAdapterQuFenQi(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    public void jumpMine(){
        tabAdapterQuFenQi.getData().get(0).setChecked(false);
        tabAdapterQuFenQi.getData().get(1).setChecked(true);
        mainViewPager.setCurrentItem(1, false);
        tabAdapterQuFenQi.notifyDataSetChanged();
    }

    private void initAdapter(){
        if (tabAdapterQuFenQi == null){
            tabAdapterQuFenQi = new TabAdapterQuFenQi(R.layout.adapter_tab_qu_fen_qi, tabModels);
            tabAdapterQuFenQi.setHasStableIds(true);
            tabAdapterQuFenQi.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 2));
            bottomRvy.setAdapter(tabAdapterQuFenQi);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qu_fen_qi_main;
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
                QuFenQiMyToast.showShort("再按一次退出程序");
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
