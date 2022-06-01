package com.dfgderv.erterqweq.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.dfgderv.erterqweq.R;
import com.dfgderv.erterqweq.utils.StatusBarUtil;
import com.dfgderv.erterqweq.utils.ToastUtil;
import com.dfgderv.erterqweq.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import com.dfgderv.erterqweq.adapter.MiaoJieMyFragmentAdapter;
import com.dfgderv.erterqweq.present.MainPresent;
import com.dfgderv.erterqweq.ui.fragment.HomePageFragment;
import com.dfgderv.erterqweq.ui.fragment.MineFragment;

public class HomePageActivity extends XActivity<MainPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.footer_icon_n_sy, R.drawable.footer_icon_n_cp, R.drawable.footer_icon_n_wd};
    private int[] checkedIcon = {R.drawable.footer_icon_f_sy, R.drawable.footer_icon_f_cp, R.drawable.footer_icon_f_wd};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MiaoJieMyFragmentAdapter miaoJieMyFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        getP().login();
        customTabEntities = new ArrayList<>();
        homeViewPager.setUserInputEnabled(false);
        for (int i = 0; i < 3; i++) {
            int position = i;
            CustomTabEntity customTabEntity = new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return mTitles[position];
                }

                @Override
                public int getTabSelectedIcon() {
                    return checkedIcon[position];
                }

                @Override
                public int getTabUnselectedIcon() {
                    return uncheckedIcon[position];
                }
            };
            customTabEntities.add(customTabEntity);
        }
        tabLayout.setTabData(customTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                homeViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mFragments.add(HomePageFragment.getInstant(1));
        mFragments.add(HomePageFragment.getInstant(2));
        mFragments.add(new MineFragment());

        homeViewPager.setAdapter(new MiaoJieMyFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    public static Date str2Date(String str)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = format.parse(str);
        }catch (ParseException e)
        {

        }
        return date;
    }

    public static Date str2Date(String str,String format)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(str);
        }catch (ParseException e)
        {

        }
        return date;
    }

    public static String getReadableStr(Date date,String format)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String getReadableStr(String source,String format)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(str2Date(source));
    }

    public static String getPriaseTimeString(Date date)
    {
        return "";
    }

    private static final long[] checkDiff={
//            Constants.DIFF_YEAR,Constants.DIFF_MONTH,Constants.DIFF_DAY,Constants.DIFF_HOUR,Constants.DIFF_MIN,Constants.DIFF_SEC
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    public MainPresent newP() {
        return new MainPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showShort("再按一次退出程序");
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
