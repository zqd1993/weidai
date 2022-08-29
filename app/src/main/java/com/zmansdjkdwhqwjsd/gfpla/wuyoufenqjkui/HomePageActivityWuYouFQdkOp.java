package com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zmansdjkdwhqwjsd.gfpla.R;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.ApiSubscriber;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.ApiWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.NetError;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.XApi;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.BaseRespWuYouFQdkOpModel;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.ConfigModelWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.wuyoufenqjkfragment.MineWuYouFQdkOpFragment;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.SharedPreferencesWuYouFQdkOpUtilis;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.StatusWuYouFQdkOpBarUtil;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.ToasWuYouFQdkOptUtil;
import com.zmansdjkdwhqwjsd.gfpla.mvp.XActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkadap.WuYouFQdkOpMyFragmentAdapter;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkpres.MainWuYouFQdkOpPresent;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.wuyoufenqjkfragment.HomePageFragmentWuYouFQdkOp;

public class HomePageActivityWuYouFQdkOp extends XActivity<MainWuYouFQdkOpPresent> {

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
    private WuYouFQdkOpMyFragmentAdapter wuYouFQdkOpMyFragmentAdapter;

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
        StatusWuYouFQdkOpBarUtil.setTransparent(this, false);
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
        mFragments.add(HomePageFragmentWuYouFQdkOp.getInstant(1));
        mFragments.add(HomePageFragmentWuYouFQdkOp.getInstant(2));
        mFragments.add(new MineWuYouFQdkOpFragment());

        homeViewPager.setAdapter(new WuYouFQdkOpMyFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
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
        return R.layout.activity_home_page_wu_you_fen_qi_jk_op;
    }

    @Override
    public MainWuYouFQdkOpPresent newP() {
        return new MainWuYouFQdkOpPresent();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToasWuYouFQdkOptUtil.showShort("再按一次退出程序");
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
        ApiWuYouFQdkOp.getGankService().getValve("VIDEOTAPE")
                .compose(XApi.<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>>getApiTransformer())
                .compose(XApi.<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>>getScheduler())
                .subscribe(new ApiSubscriber<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                SharedPreferencesWuYouFQdkOpUtilis.saveBoolIntoPref("NO_RECORD", !gankResults.getData().getVideoTape().equals("0"));
                                if (SharedPreferencesWuYouFQdkOpUtilis.getBoolFromPref("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }
}
