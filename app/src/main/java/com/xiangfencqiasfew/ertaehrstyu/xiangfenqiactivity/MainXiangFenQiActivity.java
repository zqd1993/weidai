package com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.xiangfencqiasfew.ertaehrstyu.R;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqifragment.MainFragmentXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqifragment.SetXiangFenQiFragment;
import com.xiangfencqiasfew.ertaehrstyu.mvp.XActivity;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.MyXiangFenQiToast;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.PreferencesOpenUtilXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.StatusBarXiangFenQiUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainXiangFenQiActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabXiangFenQiAdapter tabXiangFenQiAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarXiangFenQiUtil.setTransparent(this, false);
        StatusBarXiangFenQiUtil.setLightMode(this);
        if (PreferencesOpenUtilXiangFenQi.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.qwwtersy);
        tabModel.setSelectedIcon(R.drawable.xxvcnbsrtuy);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.qwwtersy);
        tabModel1.setSelectedIcon(R.drawable.xxvcnbsrtuy);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.zzbeary);
        tabModel2.setSelectedIcon(R.drawable.lltisru);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
//        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainFragmentXiangFenQi());
//        fragments.add(new ProductXiangFenQiFragment());
        fragments.add(new SetXiangFenQiFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentAdapterXiangFenQi(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    public void jumpMine(){
        tabXiangFenQiAdapter.getData().get(0).setChecked(false);
        tabXiangFenQiAdapter.getData().get(1).setChecked(true);
        mainViewPager.setCurrentItem(1, false);
        tabXiangFenQiAdapter.notifyDataSetChanged();
    }

    private void initAdapter(){
        if (tabXiangFenQiAdapter == null){
            tabXiangFenQiAdapter = new TabXiangFenQiAdapter(R.layout.adapter_xiang_fen_qi_tab, tabModels);
            tabXiangFenQiAdapter.setHasStableIds(true);
            tabXiangFenQiAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 2));
            bottomRvy.setAdapter(tabXiangFenQiAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_xiang_fen_qi;
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
                MyXiangFenQiToast.showShort("再按一次退出程序");
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
