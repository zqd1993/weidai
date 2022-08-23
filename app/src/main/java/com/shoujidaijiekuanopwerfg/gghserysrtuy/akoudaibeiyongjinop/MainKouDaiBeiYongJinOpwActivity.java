package com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.shoujidaijiekuanopwerfg.gghserysrtuy.R;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.apikoudaibeiyongjinop.HttpKouDaiBeiYongJinOpApi;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.fkoudaibeiyongjinop.MainKouDaiBeiYongJinOpFragment;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.fkoudaibeiyongjinop.ProductKouDaiBeiYongJinOpFragment;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.fkoudaibeiyongjinop.SetKouDaiBeiYongJinOpFragment;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.BaseKouDaiBeiYongJinOpModel;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.ConfigKouDaiBeiYongJinOpEntity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mvp.XActivity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.ApiSubscriber;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.NetError;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.XApi;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.MyKouDaiBeiYongJinOpToast;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.PreferencesOpenUtilKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.StatusBarKouDaiBeiYongJinOpUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainKouDaiBeiYongJinOpwActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabKouDaiBeiYongJinOpAdapter tabKouDaiBeiYongJinOpAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarKouDaiBeiYongJinOpUtil.setTransparent(this, false);
        if (PreferencesOpenUtilKouDaiBeiYongJinOp.getBool("NO_RECORD")) {
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
        fragments.add(new MainKouDaiBeiYongJinOpFragment());
        fragments.add(new ProductKouDaiBeiYongJinOpFragment());
        fragments.add(new SetKouDaiBeiYongJinOpFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FragmentKouDaiBeiYongJinOpAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    private void initAdapter(){
        if (tabKouDaiBeiYongJinOpAdapter == null){
            tabKouDaiBeiYongJinOpAdapter = new TabKouDaiBeiYongJinOpAdapter(R.layout.adapter_kou_dai_bei_yong_jin_op_tab, tabModels);
            tabKouDaiBeiYongJinOpAdapter.setHasStableIds(true);
            tabKouDaiBeiYongJinOpAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabKouDaiBeiYongJinOpAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_kou_dai_bei_yong_jin_op;
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
                MyKouDaiBeiYongJinOpToast.showShort("再按一次退出程序");
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
        HttpKouDaiBeiYongJinOpApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinOpModel<ConfigKouDaiBeiYongJinOpEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseKouDaiBeiYongJinOpModel<ConfigKouDaiBeiYongJinOpEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                PreferencesOpenUtilKouDaiBeiYongJinOp.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (PreferencesOpenUtilKouDaiBeiYongJinOp.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
