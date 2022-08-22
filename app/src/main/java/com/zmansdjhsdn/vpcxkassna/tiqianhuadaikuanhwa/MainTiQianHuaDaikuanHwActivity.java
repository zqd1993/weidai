package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.zmansdjhsdn.vpcxkassna.R;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwapi.HttpTiQianHuaDaikuanHwApi;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwf.MainTiQianHuaDaikuanHwFragment;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwf.ProductTiQianHuaDaikuanHwFragment;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwf.SetTiQianHuaDaikuanHwFragment;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.BaseTiQianHuaDaikuanHwModel;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.ConfigTiQianHuaDaikuanHwEntity;
import com.zmansdjhsdn.vpcxkassna.mvp.XActivity;
import com.zmansdjhsdn.vpcxkassna.net.ApiSubscriber;
import com.zmansdjhsdn.vpcxkassna.net.NetError;
import com.zmansdjhsdn.vpcxkassna.net.XApi;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.MyTiQianHuaDaikuanHwToast;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.PreferencesOpenUtilTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.StatusBarTiQianHuaDaikuanHwUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainTiQianHuaDaikuanHwActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabTiQianHuaDaikuanHwAdapter tabTiQianHuaDaikuanHwAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarTiQianHuaDaikuanHwUtil.setTransparent(this, false);
        if (PreferencesOpenUtilTiQianHuaDaikuanHw.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.qgtzdyezry);
        tabModel.setSelectedIcon(R.drawable.cvbnsrasery);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.eryfdxhert);
        tabModel1.setSelectedIcon(R.drawable.vbnaeryhrdtu);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.lpuysdrthjfg);
        tabModel2.setSelectedIcon(R.drawable.vrtgxfghsx);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainTiQianHuaDaikuanHwFragment());
        fragments.add(new ProductTiQianHuaDaikuanHwFragment());
        fragments.add(new SetTiQianHuaDaikuanHwFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentTiQianHuaDaikuanHwAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabTiQianHuaDaikuanHwAdapter == null){
            tabTiQianHuaDaikuanHwAdapter = new TabTiQianHuaDaikuanHwAdapter(R.layout.adapter_ti_qian_hua_dai_kuan_hw_tab, tabModels);
            tabTiQianHuaDaikuanHwAdapter.setHasStableIds(true);
            tabTiQianHuaDaikuanHwAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabTiQianHuaDaikuanHwAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_ti_qian_hua_dai_kuan_hw;
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
                MyTiQianHuaDaikuanHwToast.showShort("再按一次退出程序");
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
        HttpTiQianHuaDaikuanHwApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseTiQianHuaDaikuanHwModel<ConfigTiQianHuaDaikuanHwEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseTiQianHuaDaikuanHwModel<ConfigTiQianHuaDaikuanHwEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilTiQianHuaDaikuanHw.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesOpenUtilTiQianHuaDaikuanHw.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
