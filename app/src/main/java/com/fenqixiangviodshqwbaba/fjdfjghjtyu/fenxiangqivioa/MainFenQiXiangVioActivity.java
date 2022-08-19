package com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.fenqixiangviodshqwbaba.fjdfjghjtyu.R;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioapi.HttpApiFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviof.MainFenQiXiangVioFragment;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviof.ProductFragmentFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviof.SetFenQiXiangVioFragment;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioBaseModel;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioConfigEntity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.mvp.XActivity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.ApiSubscriber;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.NetError;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.XApi;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.MyFenQiXiangVioToast;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.PreferencesOpenUtilFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.StatusBarUtilFenQiXiangVio;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFenQiXiangVioActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabFenQiXiangVioAdapter tabFenQiXiangVioAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtilFenQiXiangVio.setTransparent(this, false);
        if (PreferencesOpenUtilFenQiXiangVio.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.xfhsreay);
        tabModel.setSelectedIcon(R.drawable.rtyxdfhsr);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.dfhrtu);
        tabModel1.setSelectedIcon(R.drawable.ffhdrt);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.sezrsry);
        tabModel2.setSelectedIcon(R.drawable.jjhsfhgae);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainFenQiXiangVioFragment());
        fragments.add(new ProductFragmentFenQiXiangVio());
        fragments.add(new SetFenQiXiangVioFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentFenQiXiangVioAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabFenQiXiangVioAdapter == null){
            tabFenQiXiangVioAdapter = new TabFenQiXiangVioAdapter(R.layout.adapter_fen_xiang_qi_vio_tab, tabModels);
            tabFenQiXiangVioAdapter.setHasStableIds(true);
            tabFenQiXiangVioAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabFenQiXiangVioAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fen_xiang_qi_vio_main;
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
                MyFenQiXiangVioToast.showShort("再按一次退出程序");
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
        HttpApiFenQiXiangVio.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilFenQiXiangVio.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesOpenUtilFenQiXiangVio.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
