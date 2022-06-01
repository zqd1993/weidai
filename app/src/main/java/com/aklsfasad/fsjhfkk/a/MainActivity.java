package com.aklsfasad.fsjhfkk.a;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.mvp.XActivity;
import com.aklsfasad.fsjhfkk.u.StatusBarUtil;

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

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.i);
        tabModel.setSelectedIcon(R.drawable.g);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.h);
        tabModel1.setSelectedIcon(R.drawable.f);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.j);
        tabModel2.setSelectedIcon(R.drawable.a);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
    }

    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new TabAdapter(R.layout.adapter_tab, tabModels);
            tabAdapter.setHasStableIds(true);
            tabAdapter.setClickedListener(new TabAdapter.ClickedListener() {
                @Override
                public void onClick(int position) {

                }
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
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
}
