package com.akjsdhfkjhj.kahssj.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.akjsdhfkjhj.kahssj.R;
import com.akjsdhfkjhj.kahssj.model.BaseModel;
import com.akjsdhfkjhj.kahssj.model.PeiZhiModel;
import com.akjsdhfkjhj.kahssj.net.Api;
import com.akjsdhfkjhj.kahssj.net.ApiSubscriber;
import com.akjsdhfkjhj.kahssj.net.NetError;
import com.akjsdhfkjhj.kahssj.net.XApi;
import com.akjsdhfkjhj.kahssj.ui.fragment.MainFragment;
import com.akjsdhfkjhj.kahssj.utils.SPUtilis;
import com.akjsdhfkjhj.kahssj.utils.StatusBarUtil;
import com.akjsdhfkjhj.kahssj.utils.ToastUtil;
import com.akjsdhfkjhj.kahssj.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.akjsdhfkjhj.kahssj.adapter.FragmentAdapter;
import com.akjsdhfkjhj.kahssj.present.MainHuiMin;
import com.akjsdhfkjhj.kahssj.ui.fragment.WodeFragment;

public class MainActivity extends XActivity<MainHuiMin> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "设置", "产品"};
    private int[] uncheckedIcon = {R.drawable.ertye, R.drawable.fghjg, R.drawable.zxcas};
    private int[] checkedIcon = {R.drawable.zxcfsd, R.drawable.fyf, R.drawable.reter};
    private ArrayList<CustomTabEntity> customTabEntities;

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
        mFragments.add(MainFragment.getInstant(1));
        mFragments.add(new WodeFragment());
        mFragments.add(MainFragment.getInstant(2));
        homeViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
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

    /**
     * 获取两个时间差（单位：unit）
     *
     * @param millis0 毫秒时间戳0
     * @param millis1 毫秒时间戳1
     *                </ul>
     * @return unit时间戳
     */
    public static long getTimeSpan(final long millis0, final long millis1) {
        return 34l;
    }

    /**
     * 获取合适型两个时间差
     * <p>time0和time1格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0     时间字符串0
     * @param time1     时间字符串1
     * @param precision 精度
     *                  <p>precision = 0，返回null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(final String time0, final String time1, final int precision) {
        return "millis2FitTimeSpan(Math.abs(string2Millis(time0, DEFAULT_FORMAT) - string2Millis(time1, DEFAULT_FORMAT)), precision)";
    }

    public float density;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    public String getOrderCommodityNumbers() {
        return "";
    }

    @Override
    public MainHuiMin newP() {
        return new MainHuiMin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
    }

    public void getValue() {
        Api.getGankService().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<PeiZhiModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                    }

                    @Override
                    public void onNext(BaseModel<PeiZhiModel> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                SPUtilis.saveBoolIntoPref("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (SPUtilis.getBoolFromPref("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }
}
