package com.rihdkauecgh.plihgnytrvfws.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.http.ApiSubscriber;
import com.rihdkauecgh.plihgnytrvfws.http.ApiXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.http.NetError;
import com.rihdkauecgh.plihgnytrvfws.http.XApi;
import com.rihdkauecgh.plihgnytrvfws.models.BaseRespXiaoNiuModel;
import com.rihdkauecgh.plihgnytrvfws.models.ConfigModelXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.ui.fragment.MineXiaoNiuFragment;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesXiaoNiuUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StatusXiaoNiuBarUtil;
import com.rihdkauecgh.plihgnytrvfws.utils.ToasXiaoNiutUtil;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import com.rihdkauecgh.plihgnytrvfws.adap.XiaoNiuMyFragmentAdapter;
import com.rihdkauecgh.plihgnytrvfws.pres.MainXiaoNiuPresent;
import com.rihdkauecgh.plihgnytrvfws.ui.fragment.HomePageFragmentXiaoNiu;

public class HomePageActivityXiaoNiu extends XActivity<MainXiaoNiuPresent> {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "产品", "我的"};
    private int[] uncheckedIcon = {R.drawable.wedfg, R.drawable.opuiigyj, R.drawable.lyujfgh};
    private int[] checkedIcon = {R.drawable.luigdfhf, R.drawable.refhdf, R.drawable.wevxdg};
    private ArrayList<CustomTabEntity> customTabEntities;
    private XiaoNiuMyFragmentAdapter xiaoNiuMyFragmentAdapter;

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T changeGsonToBean(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: "+e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusXiaoNiuBarUtil.setTransparent(this, false);
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
        mFragments.add(HomePageFragmentXiaoNiu.getInstant(1));
        mFragments.add(HomePageFragmentXiaoNiu.getInstant(2));
        mFragments.add(new MineXiaoNiuFragment());

        homeViewPager.setAdapter(new XiaoNiuMyFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T advdfgdfg(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public <T> List<T> mdhfhs(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: "+e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> tygfgdzfg(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> nfddsdfd(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page_xiaoniu;
    }

    @Override
    public MainXiaoNiuPresent newP() {
        return new MainXiaoNiuPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToasXiaoNiutUtil.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T yijhdghj(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public <T> List<T> ljkfghjghj(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: "+e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> retgdfa(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> ndfgadster(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
    }

    public void getValue() {
        ApiXiaoNiu.getGankService().getValve("VIDEOTAPE")
                .compose(XApi.<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>>getApiTransformer())
                .compose(XApi.<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>>getScheduler())
                .subscribe(new ApiSubscriber<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespXiaoNiuModel<ConfigModelXiaoNiu> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                SharedPreferencesXiaoNiuUtilis.saveBoolIntoPref("NO_RECORD", !gankResults.getData().getVideoTape().equals("0"));
                                if (SharedPreferencesXiaoNiuUtilis.getBoolFromPref("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }
}
