package com.mbnmhj.poiohg.page;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.mbnmhj.poiohg.R;
import com.mbnmhj.poiohg.fragment.OneFragment;
import com.mbnmhj.poiohg.fragment.TwoFragment;
import com.mbnmhj.poiohg.fragment.ThreeFragment;
import com.mbnmhj.poiohg.mvp.XActivity;
import com.mbnmhj.poiohg.util.SBarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class WorkActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private WorkBottomAdapter tabAdapter;

    private List<Fragment> fragments;

    /**
     * 使用正则表达式去掉无效的0和.
     *
     * @param str
     * @return
     */
    public static String subZeroAndDot(String str) {
        if (str.indexOf(".") > 0) {
            str = str.replaceAll("0+?$", "");//去掉多余的0
            str = str.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return str;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        SBarUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.hyjwf);
        tabModel.setSelectedIcon(R.drawable.edqwd);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.pogerf);
        tabModel1.setSelectedIcon(R.drawable.vfer);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.wewvf);
        tabModel2.setSelectedIcon(R.drawable.uthrg);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new OneFragment());
        fragments.add(new TwoFragment());
        fragments.add(new ThreeFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FMAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    /**
     * @param str 需要转换的数字
     * @return 转换后的大写数字
     */

    public static String toChinese(String str) {
        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
        String result = "";
        int n = str.length();
        for (int i = 0; i < n; i++) {
            int num = str.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  " + result);
        }
        System.out.println(result);
        return result;
    }


    private void initAdapter(){
        if (tabAdapter == null){
            tabAdapter = new WorkBottomAdapter(R.layout.adapter_bottom_tab, tabModels);
            tabAdapter.setHasStableIds(true);
            tabAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabAdapter);
        }
    }

    /**
     * 计算有效时间
     *
     * @param validDay 有效天数
     * @return 有效时间
     */
    public static String showValidDay(Context context, int validDay) {
        int year = validDay / 365;
        int day = validDay % 365;
        String vaildTerm = "";
        //大于十年返回永久有效，否则返回xx年xx天格式字符串
        if (year > 10)
            return context.getString(R.string.app_name);
        else {
            if (year != 0)
                vaildTerm = vaildTerm + year + "年";
            if (day != 0)
                vaildTerm = vaildTerm + day + "天";
            return vaildTerm;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_work;
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

    /**
     * 有效时间
     *
     * @param validBeginDate 有效开始时间
     * @param validEndDate   有效结束时间
     * @return
     */
    public static String showValidTerm(String validBeginDate, String validEndDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long validBeginDatel = new Long(validBeginDate);
        long validEndDatel = new Long(validEndDate);
        Date beginDate = new Date(validBeginDatel);
        Date endDate = new Date(validEndDatel);
        return simpleDateFormat.format(beginDate) + "至" + simpleDateFormat.format(endDate);
    }
}
