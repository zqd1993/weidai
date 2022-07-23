package com.jryqhdgreyey.bzdrghsrtuy.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jryqhdgreyey.bzdrghsrtuy.R;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaactivity.JinRiYouQianHuaImageAdapter;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaactivity.JumpH5ActivityJinRiYouQianHua;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaactivity.JinRiYouQianHuaMainActivity;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaapi.JinRiYouQianHuaHttpApi;
import com.jryqhdgreyey.bzdrghsrtuy.imageloader.ILFactory;
import com.jryqhdgreyey.bzdrghsrtuy.imageloader.ILoader;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.JinRiYouQianHuaBannerModel;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.JinRiYouQianHuaBaseModel;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.JinRiYouQianHuaProductModel;
import com.jryqhdgreyey.bzdrghsrtuy.mvp.XActivity;
import com.jryqhdgreyey.bzdrghsrtuy.mvp.XFragment;
import com.jryqhdgreyey.bzdrghsrtuy.net.ApiSubscriber;
import com.jryqhdgreyey.bzdrghsrtuy.net.NetError;
import com.jryqhdgreyey.bzdrghsrtuy.net.XApi;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuautils.OpenUtilJinRiYouQianHua;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuautils.PreferencesJinRiYouQianHuaOpenUtil;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainFragmentJinRiYouQianHua extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.goods_banner)
    Banner banner;
    @BindView(R.id.banner_fl)
    View banner_fl;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.banner_img)
    ImageView banner_img;
    @BindView(R.id.click_view_fl)
    View click_view_fl;

    private JinRiYouQianHuaProductModel jinRiYouQianHuaProductModel;

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

    private Bundle bundle;

    private JinRiYouQianHuaImageAdapter jinRiYouQianHuaImageAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
//        banner_fl.setVisibility(View.VISIBLE);
        goodsListLl.setVisibility(View.VISIBLE);
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
        click_view_fl.setOnClickListener(v -> {
            ((JinRiYouQianHuaMainActivity) getActivity()).jumpMine();
        });
        banner_img.setOnClickListener(v -> {
            productClick(jinRiYouQianHuaProductModel);
        });
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap werfzdgtery(Activity activity) {
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
    public Bitmap bzdfhsrty(Activity activity) {
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
    public void onResume() {
        super.onResume();
        productList();
        bannerList();
    }

    private void initBannerAdapter(List<JinRiYouQianHuaProductModel> data) {
        jinRiYouQianHuaImageAdapter = null;
        jinRiYouQianHuaImageAdapter = new JinRiYouQianHuaImageAdapter(data);
        jinRiYouQianHuaImageAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(jinRiYouQianHuaImageAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jin_ri_you_qian_hua_main;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap njsjrthxfgu(Activity activity) {
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
    public Bitmap eratygdfhgsru(Activity activity) {
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
    public Object newP() {
        return null;
    }

    public void productClick(JinRiYouQianHuaProductModel model) {
        if (!TextUtils.isEmpty(PreferencesJinRiYouQianHuaOpenUtil.getString("HTTP_API_URL"))) {
            if (model == null) {
                return;
            }
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


    public void productList() {
        if (!TextUtils.isEmpty(PreferencesJinRiYouQianHuaOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = PreferencesJinRiYouQianHuaOpenUtil.getInt("mobileType");
            phone = PreferencesJinRiYouQianHuaOpenUtil.getString("phone");
            jinRiYouQianHuaProductModel = null;
            JinRiYouQianHuaHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<JinRiYouQianHuaBaseModel<List<JinRiYouQianHuaProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenUtilJinRiYouQianHua.showErrorInfo(getActivity(), error);
                            if (jinRiYouQianHuaImageAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(JinRiYouQianHuaBaseModel<List<JinRiYouQianHuaProductModel>> jinRiYouQianHuaBaseModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (jinRiYouQianHuaBaseModel != null) {
                                if (jinRiYouQianHuaBaseModel.getCode() == 200 && jinRiYouQianHuaBaseModel.getData() != null) {
                                    if (jinRiYouQianHuaBaseModel.getData() != null && jinRiYouQianHuaBaseModel.getData().size() > 0) {
                                        jinRiYouQianHuaProductModel = jinRiYouQianHuaBaseModel.getData().get(0);
                                        initBannerAdapter(jinRiYouQianHuaBaseModel.getData());
//                                    addProductView(baseModel.getData());
                                    } else {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            } else {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
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
    public Bitmap agseryhxfghrtuy(Activity activity) {
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
    public Bitmap retyfdhsrtu(Activity activity) {
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

    private void bannerList() {
        if (!TextUtils.isEmpty(PreferencesJinRiYouQianHuaOpenUtil.getString("HTTP_API_URL"))) {
            JinRiYouQianHuaHttpApi.getInterfaceUtils().bannerList()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<JinRiYouQianHuaBaseModel<List<JinRiYouQianHuaBannerModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtilJinRiYouQianHua.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(JinRiYouQianHuaBaseModel<List<JinRiYouQianHuaBannerModel>> jinRiYouQianHuaBaseModel) {
                            if (jinRiYouQianHuaBaseModel != null) {
                                if (jinRiYouQianHuaBaseModel.getCode() == 200) {
                                    if (jinRiYouQianHuaBaseModel.getData() != null && jinRiYouQianHuaBaseModel.getData().size() > 0) {
                                        if (!TextUtils.isEmpty(PreferencesJinRiYouQianHuaOpenUtil.getString("HTTP_API_URL"))) {
                                            ILFactory.getLoader().loadNet(banner_img, PreferencesJinRiYouQianHuaOpenUtil.getString("HTTP_API_URL") + jinRiYouQianHuaBaseModel.getData().get(0).getLogo(),
                                                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    private void addProductView(List<JinRiYouQianHuaProductModel> mList) {
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
            shijian_tv.setText(model.getDes());
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
            if (!TextUtils.isEmpty(PreferencesJinRiYouQianHuaOpenUtil.getString("HTTP_API_URL"))) {
                ILFactory.getLoader().loadNet(pic, PreferencesJinRiYouQianHuaOpenUtil.getString("HTTP_API_URL") + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            }
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

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap puyikhjsrtu(Activity activity) {
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
    public Bitmap naehreryzdfg(Activity activity) {
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

    public void toWeb(JinRiYouQianHuaProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenUtilJinRiYouQianHua.getValue((XActivity) getActivity(), JumpH5ActivityJinRiYouQianHua.class, bundle);
        }
    }
}
