package com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.fndrhsdfgh.sdgerytyyuddfg.R;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.ApiJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.ApiSubscriber;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.NetError;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.XApi;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui.sdufrgnsdffragment.HomePageFragmentJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui.sdufrgnsdffragment.MineJieAhsQidngWasdgFragment;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfutils.JieAhsQidngWasdgSharedPreferencesUtilis;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfutils.StatusJieAhsQidngWasdgBarUtil;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfutils.JieAhsQidngWasdgToastUtil;
import com.fndrhsdfgh.sdgerytyyuddfg.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfadapter.MyJieAhsQidngWasdgFragmentAdapter;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfpresent.MainJieAhsQidngWasdgPresent;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.BaseRespModelJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.ConfigJieAhsQidngWasdgModel;

public class HomePageActivityJieAhsQidngWasdg extends XActivity<MainJieAhsQidngWasdgPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.qwevsad, R.drawable.lgjhsd, R.drawable.lyuidh};
    private int[] checkedIcon = {R.drawable.bdsty, R.drawable.qwefa, R.drawable.yurghs};
    private ArrayList<CustomTabEntity> customTabEntities;
    private MyJieAhsQidngWasdgFragmentAdapter myShouJiDaiFragmentAdapter;

    /**
     * 保存在手机里面的文件名
     */
    public final String FILE_NAME = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void put(Context context, String key, Object object)
    {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
            editor.putString(key, object.toString());
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusJieAhsQidngWasdgBarUtil.setTransparent(this, false);
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
        mFragments.add(HomePageFragmentJieAhsQidngWasdg.getInstant(1));
        mFragments.add(HomePageFragmentJieAhsQidngWasdg.getInstant(2));
        mFragments.add(new MineJieAhsQidngWasdgFragment());

        homeViewPager.setAdapter(new MyJieAhsQidngWasdgFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    /**
     * 保存在手机里面的文件名
     */
    public final String gdfbd = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void ityuytg(Context context, String key, Object object)
    {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
            editor.putString(key, object.toString());
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object nfgdfg(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page_sdfrt_dfjret_kyihk_etfh;
    }

    @Override
    public MainJieAhsQidngWasdgPresent newP() {
        return new MainJieAhsQidngWasdgPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                JieAhsQidngWasdgToastUtil.showShort("再按一次退出程序");
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
     * 保存在手机里面的文件名
     */
    public final String oyuijfg = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void qwfsdf(Context context, String key, Object object)
    {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
            editor.putString(key, object.toString());
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object mdgfdg(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
    }

    public void getValue() {
        ApiJieAhsQidngWasdg.getGankService().getValve("VIDEOTAPE")
                .compose(XApi.<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>>getScheduler())
                .subscribe(new ApiSubscriber<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                JieAhsQidngWasdgSharedPreferencesUtilis.saveBoolIntoPref("NO_RECORD", !gankResults.getData().getVideoTape().equals("0"));
                                if (JieAhsQidngWasdgSharedPreferencesUtilis.getBoolFromPref("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }
}
