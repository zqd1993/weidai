package com.ufaofqsbxo.uunllhykas.a;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.ufaofqsbxo.uunllhykas.R;
import com.ufaofqsbxo.uunllhykas.f.ZhuYeFragment;
import com.ufaofqsbxo.uunllhykas.f.ProductFragment;
import com.ufaofqsbxo.uunllhykas.f.SheZhiFragment;
import com.ufaofqsbxo.uunllhykas.mvp.XActivity;
import com.ufaofqsbxo.uunllhykas.u.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZhuYeActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<ItemModel> tabModels;

    private ItemAdapter tabAdapter;

    private List<Fragment> fragments;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        ItemModel tabModel = new ItemModel();
        tabModel.setIcon(R.drawable.lhj);
        tabModel.setSelectedIcon(R.drawable.hjkhjk);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        ItemModel tabModel1 = new ItemModel();
        tabModel1.setIcon(R.drawable.tyutyu);
        tabModel1.setSelectedIcon(R.drawable.sdfd);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        ItemModel tabModel2 = new ItemModel();
        tabModel2.setIcon(R.drawable.uottr);
        tabModel2.setSelectedIcon(R.drawable.cvdfg);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new ZhuYeFragment());
        fragments.add(new ProductFragment());
        fragments.add(new SheZhiFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new WodeFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new ItemAdapter(R.layout.adapter_item, tabModels);
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
        return R.layout.activity_zhuyao;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static int getStatusBarHeight() {
        int statusBarHeight = 0;
//获取status_bar_height资源的ID
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public class ItemModel{

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
}
