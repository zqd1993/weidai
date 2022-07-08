package com.rongyidai.eetzgfzdfh.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.OverScroller;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.rongyidai.eetzgfzdfh.R;
import com.rongyidai.eetzgfzdfh.adapter.MineRongYiDaiAdapter;
import com.rongyidai.eetzgfzdfh.model.BaseRespModelRongYiDai;
import com.rongyidai.eetzgfzdfh.model.RongYiDaiCompanyInfoModel;
import com.rongyidai.eetzgfzdfh.model.RongYiDaiMineItemModel;
import com.rongyidai.eetzgfzdfh.net.ApiSubscriber;
import com.rongyidai.eetzgfzdfh.net.NetError;
import com.rongyidai.eetzgfzdfh.net.XApi;
import com.rongyidai.eetzgfzdfh.ui.WebRongYiDaiViewActivity;
import com.rongyidai.eetzgfzdfh.ui.activity.RongYiDaiAboutUsActivity;
import com.rongyidai.eetzgfzdfh.ui.activity.RongYiDaiSettingActivity;
import com.rongyidai.eetzgfzdfh.utils.SharedRongYiDaiPreferencesUtilis;
import com.rongyidai.eetzgfzdfh.utils.RongYiDaiToastUtil;
import com.rongyidai.eetzgfzdfh.net.RongYiDaiApi;
import com.rongyidai.eetzgfzdfh.router.Router;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.rongyidai.eetzgfzdfh.widget.NormalRongYiDaiDialog;
import com.rongyidai.eetzgfzdfh.mvp.XFragment;
import com.rongyidai.eetzgfzdfh.ui.activity.CancellationAccountActivityRongYiDai;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class RongYiDaiMineFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.gywm_ll)
    View gywm_ll;
    @BindView(R.id.ysxy_ll)
    View ysxy_ll;
    @BindView(R.id.zcxy_ll)
    View zcxy_ll;

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field retgdfhgxfh() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mFlingRunnable");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("flingRunnable");
            } else {
                return null;
            }
        }
    }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field wrfedgzdhg() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mScroller");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("scroller");
            }else {
                return null;
            }
        }
    }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private void oiujgcnxfht(AppBarLayout appBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            Field flingRunnableField = getFlingRunnableField();
            Field scrollerField = getScrollerField();
            if (flingRunnableField != null) {
                flingRunnableField.setAccessible(true);
            }
            if (scrollerField != null) {
                scrollerField.setAccessible(true);
            }
            Runnable flingRunnable = null;
            if (flingRunnableField != null) {
                flingRunnable = (Runnable) flingRunnableField.get(this);
            }
            OverScroller overScroller = (OverScroller) scrollerField.get(this);
            if (flingRunnable != null) {
                appBarLayout.removeCallbacks(flingRunnable);
                flingRunnableField.set(this, null);
            }
            if (overScroller != null && !overScroller.isFinished()) {
                overScroller.abortAnimation();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private MineRongYiDaiAdapter mineAdapter;
    private List<RongYiDaiMineItemModel> list;
    private int[] imgRes = {R.drawable.qwerfzdghfdh, R.drawable.wergzdffh, R.drawable.wqrfzsgxdfh};
    private String[] tvRes = {"系统设置", "投诉邮箱", "注销账户"};
    private Bundle bundle;
    private NormalRongYiDaiDialog normalRongYiDaiDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        getCompanyInfo();
        phone = SharedRongYiDaiPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 3; i++) {
            RongYiDaiMineItemModel model = new RongYiDaiMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        gywm_ll.setOnClickListener(v -> {
            Router.newIntent(getActivity())
                    .to(RongYiDaiAboutUsActivity.class)
                    .launch();
        });
        ysxy_ll.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 2);
            bundle.putString("url", RongYiDaiApi.getYs());
            Router.newIntent(getActivity())
                    .to(WebRongYiDaiViewActivity.class)
                    .data(bundle)
                    .launch();
        });
        zcxy_ll.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 1);
            bundle.putString("url", RongYiDaiApi.getZc());
            Router.newIntent(getActivity())
                    .to(WebRongYiDaiViewActivity.class)
                    .data(bundle)
                    .launch();
        });
    }

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field yuhgfxhft() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mFlingRunnable");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("flingRunnable");
            } else {
                return null;
            }
        }
    }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field fxbgc() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mScroller");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("scroller");
            }else {
                return null;
            }
        }
    }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private void gdfghfjuhtyu(AppBarLayout appBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            Field flingRunnableField = getFlingRunnableField();
            Field scrollerField = getScrollerField();
            if (flingRunnableField != null) {
                flingRunnableField.setAccessible(true);
            }
            if (scrollerField != null) {
                scrollerField.setAccessible(true);
            }
            Runnable flingRunnable = null;
            if (flingRunnableField != null) {
                flingRunnable = (Runnable) flingRunnableField.get(this);
            }
            OverScroller overScroller = (OverScroller) scrollerField.get(this);
            if (flingRunnable != null) {
                appBarLayout.removeCallbacks(flingRunnable);
                flingRunnableField.set(this, null);
            }
            if (overScroller != null && !overScroller.isFinished()) {
                overScroller.abortAnimation();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_rong_yi_dai;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field qrfzghxtfuy() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mFlingRunnable");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("flingRunnable");
            } else {
                return null;
            }
        }
    }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field putyhjfgcjh() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mScroller");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("scroller");
            }else {
                return null;
            }
        }
    }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private void hxfgbhjtyu(AppBarLayout appBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            Field flingRunnableField = getFlingRunnableField();
            Field scrollerField = getScrollerField();
            if (flingRunnableField != null) {
                flingRunnableField.setAccessible(true);
            }
            if (scrollerField != null) {
                scrollerField.setAccessible(true);
            }
            Runnable flingRunnable = null;
            if (flingRunnableField != null) {
                flingRunnable = (Runnable) flingRunnableField.get(this);
            }
            OverScroller overScroller = (OverScroller) scrollerField.get(this);
            if (flingRunnable != null) {
                appBarLayout.removeCallbacks(flingRunnable);
                flingRunnableField.set(this, null);
            }
            if (overScroller != null && !overScroller.isFinished()) {
                overScroller.abortAnimation();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        if (mineAdapter == null) {
            mineAdapter = new MineRongYiDaiAdapter(getActivity());
            mineAdapter.setData(list);
            mineAdapter.setHasStableIds(true);
            mineAdapter.setRecItemClick(new RecyclerItemCallback<RongYiDaiMineItemModel, MineRongYiDaiAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, RongYiDaiMineItemModel model, int tag, MineRongYiDaiAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    if (tag == 2) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText(null, mailStr);
                        clipboard.setPrimaryClip(clipData);
                        RongYiDaiToastUtil.showShort("复制成功");
                        return;
                    }
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(RongYiDaiSettingActivity.class)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(CancellationAccountActivityRongYiDai.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedRongYiDaiPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            RongYiDaiApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelRongYiDai<RongYiDaiCompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelRongYiDai<RongYiDaiCompanyInfoModel>>getScheduler())
                    .compose(this.<BaseRespModelRongYiDai<RongYiDaiCompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelRongYiDai<RongYiDaiCompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelRongYiDai<RongYiDaiCompanyInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    SharedRongYiDaiPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                    if (mineAdapter != null){
                                        mineAdapter.notifyDataSetChanged();
                                    } else {
                                        initAdapter();
                                    }
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field getFlingRunnableField() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mFlingRunnable");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("flingRunnable");
            } else {
                return null;
            }
        }
    }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field getScrollerField() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mScroller");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("scroller");
            }else {
                return null;
            }
        }
    }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private void stopAppbarLayoutFling(AppBarLayout appBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            Field flingRunnableField = getFlingRunnableField();
            Field scrollerField = getScrollerField();
            if (flingRunnableField != null) {
                flingRunnableField.setAccessible(true);
            }
            if (scrollerField != null) {
                scrollerField.setAccessible(true);
            }
            Runnable flingRunnable = null;
            if (flingRunnableField != null) {
                flingRunnable = (Runnable) flingRunnableField.get(this);
            }
            OverScroller overScroller = (OverScroller) scrollerField.get(this);
            if (flingRunnable != null) {
                appBarLayout.removeCallbacks(flingRunnable);
                flingRunnableField.set(this, null);
            }
            if (overScroller != null && !overScroller.isFinished()) {
                overScroller.abortAnimation();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (normalRongYiDaiDialog != null) {
            normalRongYiDaiDialog.dismiss();
            normalRongYiDaiDialog = null;
        }
        super.onDestroy();
    }
}
