package com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.fvhrnthdfggeinihua.hdfghuerungf.R;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewfragment.OpNewGeiNiHuaDaiKuanProductFragment;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewfragment.OpNewGeiNiHuaDaiKuanMainFragment;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewfragment.OpNewGeiNiHuaDaiKuanSetFragment;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewaapi.OpNewGeiNiHuaDaiKuanHttpApi;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanBaseModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanConfigEntity;
import com.fvhrnthdfggeinihua.hdfghuerungf.mvp.XActivity;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanMyToast;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanPreferencesOpenUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanStatusBarUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.ApiSubscriber;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.NetError;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.XApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OpNewGeiNiHuaDaiKuanMainActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private OpNewGeiNiHuaDaiKuanTabAdapter opNewGeiNiHuaDaiKuanTabAdapter;

    private List<Fragment> fragments;

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        OpNewGeiNiHuaDaiKuanStatusBarUtil.setTransparent(this, false);
        OpNewGeiNiHuaDaiKuanStatusBarUtil.setLightMode(this);
        if (OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.zwettyu);
        tabModel.setSelectedIcon(R.drawable.ldtysrtysru);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.fghsrtu);
        tabModel1.setSelectedIcon(R.drawable.xcvbzsrhy);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.lyuirtysrey);
        tabModel2.setSelectedIcon(R.drawable.wetgxdfhyu);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new OpNewGeiNiHuaDaiKuanMainFragment());
        fragments.add(new OpNewGeiNiHuaDaiKuanProductFragment());
        fragments.add(new OpNewGeiNiHuaDaiKuanSetFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new OpNewGeiNiHuaDaiKuanFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    public void jumpMine(){
        opNewGeiNiHuaDaiKuanTabAdapter.getData().get(0).setChecked(false);
        opNewGeiNiHuaDaiKuanTabAdapter.getData().get(1).setChecked(true);
        mainViewPager.setCurrentItem(1, false);
        opNewGeiNiHuaDaiKuanTabAdapter.notifyDataSetChanged();
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap wetfgzdghery(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap nrthfujsrtu(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    private void initAdapter(){
        if (opNewGeiNiHuaDaiKuanTabAdapter == null){
            opNewGeiNiHuaDaiKuanTabAdapter = new OpNewGeiNiHuaDaiKuanTabAdapter(R.layout.adapter_gei_ni_hua_dai_kuan_op_new_tab, tabModels);
            opNewGeiNiHuaDaiKuanTabAdapter.setHasStableIds(true);
            opNewGeiNiHuaDaiKuanTabAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(opNewGeiNiHuaDaiKuanTabAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_gei_ni_hua_dai_kuan_op_new;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap zzgedrtry(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap mjkrthgasert(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
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
                OpNewGeiNiHuaDaiKuanMyToast.showShort("再按一次退出程序");
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
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap zaaertdfhry(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap nerhdfhersy(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getValue();
    }

    public void getValue() {
        OpNewGeiNiHuaDaiKuanHttpApi.getInterfaceUtils().getValue("VIDEOTAPE")
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.saveBool("NO_RECORD", !configEntity.getData().getVideoTape().equals("0"));
                                if (OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getBool("NO_RECORD")) {
                                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
                                }
                            }
                        }
                    }
                });
    }

}
