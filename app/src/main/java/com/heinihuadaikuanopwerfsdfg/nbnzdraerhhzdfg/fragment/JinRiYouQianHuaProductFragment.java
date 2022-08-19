package com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.R;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.jinriyouqianhuaactivity.JumpH5ActivityJinRiYouQianHua;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.jinriyouqianhuaapi.JinRiYouQianHuaHttpApi;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.imageloader.ILFactory;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.imageloader.ILoader;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.jinriyouqianhuamodel.JinRiYouQianHuaBaseModel;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.jinriyouqianhuamodel.JinRiYouQianHuaProductModel;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.mvp.XActivity;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.mvp.XFragment;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.net.ApiSubscriber;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.net.NetError;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.net.XApi;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.jinriyouqianhuautils.OpenUtilJinRiYouQianHua;
import com.heinihuadaikuanopwerfsdfg.nbnzdraerhhzdfg.jinriyouqianhuautils.PreferencesJinRiYouQianHuaOpenUtil;

import java.util.List;

import butterknife.BindView;

public class JinRiYouQianHuaProductFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.click_view_fl)
    View click_view_fl;
    private JinRiYouQianHuaProductModel jinRiYouQianHuaProductModel;

    private Bundle bundle;

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

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_img.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        click_view_fl.setVisibility(View.GONE);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(jinRiYouQianHuaProductModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(jinRiYouQianHuaProductModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(jinRiYouQianHuaProductModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jin_ri_you_qian_hua_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(JinRiYouQianHuaProductModel model) {
            if (model != null) {
                phone = PreferencesJinRiYouQianHuaOpenUtil.getString("phone");
                JinRiYouQianHuaHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<JinRiYouQianHuaBaseModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(JinRiYouQianHuaBaseModel jinRiYouQianHuaBaseModel) {
                                toWeb(model);
                            }
                        });
            }
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap aertgzdryrsu(Activity activity) {
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
    public Bitmap sryfxhrtury(Activity activity) {
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

    public void productList() {
            mobileType = PreferencesJinRiYouQianHuaOpenUtil.getInt("mobileType");
            phone = PreferencesJinRiYouQianHuaOpenUtil.getString("phone");
            JinRiYouQianHuaHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<JinRiYouQianHuaBaseModel<List<JinRiYouQianHuaProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenUtilJinRiYouQianHua.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(JinRiYouQianHuaBaseModel<List<JinRiYouQianHuaProductModel>> jinRiYouQianHuaBaseModel) {
                            setRefreshing.setRefreshing(false);
                            if (jinRiYouQianHuaBaseModel != null) {
                                if (jinRiYouQianHuaBaseModel.getCode() == 200 && jinRiYouQianHuaBaseModel.getData() != null) {
                                    if (jinRiYouQianHuaBaseModel.getData() != null && jinRiYouQianHuaBaseModel.getData().size() > 0) {
                                        jinRiYouQianHuaProductModel = jinRiYouQianHuaBaseModel.getData().get(0);
                                        addProductView(jinRiYouQianHuaBaseModel.getData());
                                    } else {
                                        if (goodsListLl.getChildCount() == 0) {
                                            noDataTv.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (goodsListLl.getChildCount() == 0) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (goodsListLl.getChildCount() == 0) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap hjdtugfj(Activity activity) {
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
    public Bitmap aertdghrtu(Activity activity) {
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

    private void addProductView(List<JinRiYouQianHuaProductModel> mList) {
        goodsListLl.removeAllViews();
        for (JinRiYouQianHuaProductModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_jin_ri_you_qian_hua_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            shijian_tv.setText(model.getDes() + "个月");
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
                ILFactory.getLoader().loadNet(pic, JinRiYouQianHuaHttpApi.HTTP_API_URL + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            parentFl.setOnClickListener(v -> {
                productClick(model);
            });
            pic.setOnClickListener(v -> {
                productClick(model);
            });
            yjsqSl.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
    }

    public void toWeb(JinRiYouQianHuaProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenUtilJinRiYouQianHua.getValue((XActivity) getActivity(), JumpH5ActivityJinRiYouQianHua.class, bundle);
        }
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap ashreyfgujtru(Activity activity) {
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
    public Bitmap mjsrthxfrruy(Activity activity) {
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
}
