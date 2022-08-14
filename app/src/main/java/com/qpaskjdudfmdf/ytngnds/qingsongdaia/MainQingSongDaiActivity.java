package com.qpaskjdudfmdf.ytngnds.qingsongdaia;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.qpaskjdudfmdf.ytngnds.R;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiapi.HttpApiQingSongDai;
import com.qpaskjdudfmdf.ytngnds.qingsongdaif.MainQingSongDaiFragment;
import com.qpaskjdudfmdf.ytngnds.qingsongdaif.ProductQingSongDaiFragment;
import com.qpaskjdudfmdf.ytngnds.qingsongdaif.SetQingSongDaiFragment;
import com.qpaskjdudfmdf.ytngnds.qingsongdaim.BaseQingSongDaiModel;
import com.qpaskjdudfmdf.ytngnds.qingsongdaim.ConfigEntityQingSongDai;
import com.qpaskjdudfmdf.ytngnds.mvp.XActivity;
import com.qpaskjdudfmdf.ytngnds.net.ApiSubscriber;
import com.qpaskjdudfmdf.ytngnds.net.NetError;
import com.qpaskjdudfmdf.ytngnds.net.XApi;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiu.MyQingSongDaiToast;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiu.PreferencesOpenUtilQingSongDai;
import com.qpaskjdudfmdf.ytngnds.qingsongdaiu.QingSongDaiStatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainQingSongDaiActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabAdapterQingSongDai tabAdapterQingSongDai;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        QingSongDaiStatusBarUtil.setTransparent(this, false);
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.srrtsyxfgj);
        tabModel.setSelectedIcon(R.drawable.jsrtnfgx);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.vxbnrtuasr);
        tabModel1.setSelectedIcon(R.drawable.zbzdf);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.aeeryrtj);
        tabModel2.setSelectedIcon(R.drawable.fcxbnsrty);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainQingSongDaiFragment());
        fragments.add(new ProductQingSongDaiFragment());
        fragments.add(new SetQingSongDaiFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentQingSongDaiAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabAdapterQingSongDai == null){
            tabAdapterQingSongDai = new TabAdapterQingSongDai(R.layout.adapter_tab_qing_song_dai, tabModels);
            tabAdapterQingSongDai.setHasStableIds(true);
            tabAdapterQingSongDai.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabAdapterQingSongDai);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qing_song_dai_main;
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
                MyQingSongDaiToast.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
    }

    public void getValue() {
        HttpApiQingSongDai.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseQingSongDaiModel<ConfigEntityQingSongDai>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseQingSongDaiModel<ConfigEntityQingSongDai> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilQingSongDai.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesOpenUtilQingSongDai.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
