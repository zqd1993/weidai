package com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewfragment;

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

import com.fvhrnthdfggeinihua.hdfghuerungf.R;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity.OpNewGeiNiHuaDaiKuanImageAdapter;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity.JumpH5ActivityJinRiYouQianHua;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity.OpNewGeiNiHuaDaiKuanMainActivity;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewaapi.OpNewGeiNiHuaDaiKuanHttpApi;
import com.fvhrnthdfggeinihua.hdfghuerungf.imageloader.ILFactory;
import com.fvhrnthdfggeinihua.hdfghuerungf.imageloader.ILoader;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanBannerModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanBaseModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanProductModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.mvp.XActivity;
import com.fvhrnthdfggeinihua.hdfghuerungf.mvp.XFragment;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.ApiSubscriber;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.NetError;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.XApi;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanOpenUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanPreferencesOpenUtil;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class OpNewGeiNiHuaDaiKuanMainFragment extends XFragment {

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

    private OpNewGeiNiHuaDaiKuanProductModel opNewGeiNiHuaDaiKuanProductModel;

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

    private OpNewGeiNiHuaDaiKuanImageAdapter opNewGeiNiHuaDaiKuanImageAdapter;

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
            productClick(opNewGeiNiHuaDaiKuanProductModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(opNewGeiNiHuaDaiKuanProductModel);
        });
        click_view_fl.setOnClickListener(v -> {
            ((OpNewGeiNiHuaDaiKuanMainActivity) getActivity()).jumpMine();
        });
        banner_img.setOnClickListener(v -> {
            productClick(opNewGeiNiHuaDaiKuanProductModel);
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

    private void initBannerAdapter(List<OpNewGeiNiHuaDaiKuanProductModel> data) {
        opNewGeiNiHuaDaiKuanImageAdapter = null;
        opNewGeiNiHuaDaiKuanImageAdapter = new OpNewGeiNiHuaDaiKuanImageAdapter(data);
        opNewGeiNiHuaDaiKuanImageAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(opNewGeiNiHuaDaiKuanImageAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gei_ni_hua_dai_kuan_op_new_main;
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

    public void productClick(OpNewGeiNiHuaDaiKuanProductModel model) {
        if (model == null) {
            return;
        }
        phone = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getString("phone");
        OpNewGeiNiHuaDaiKuanHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<OpNewGeiNiHuaDaiKuanBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(OpNewGeiNiHuaDaiKuanBaseModel opNewGeiNiHuaDaiKuanBaseModel) {
                        toWeb(model);
                    }
                });
    }


    public void productList() {
        mobileType = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getInt("mobileType");
        phone = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getString("phone");
        opNewGeiNiHuaDaiKuanProductModel = null;
        OpNewGeiNiHuaDaiKuanHttpApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<OpNewGeiNiHuaDaiKuanBaseModel<List<OpNewGeiNiHuaDaiKuanProductModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpNewGeiNiHuaDaiKuanOpenUtil.showErrorInfo(getActivity(), error);
                        if (opNewGeiNiHuaDaiKuanImageAdapter == null) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(OpNewGeiNiHuaDaiKuanBaseModel<List<OpNewGeiNiHuaDaiKuanProductModel>> opNewGeiNiHuaDaiKuanBaseModel) {
                        setRefreshing.setRefreshing(false);
                        goodsListLl.removeAllViews();
                        if (opNewGeiNiHuaDaiKuanBaseModel != null) {
                            if (opNewGeiNiHuaDaiKuanBaseModel.getCode() == 200 && opNewGeiNiHuaDaiKuanBaseModel.getData() != null) {
                                if (opNewGeiNiHuaDaiKuanBaseModel.getData() != null && opNewGeiNiHuaDaiKuanBaseModel.getData().size() > 0) {
                                    opNewGeiNiHuaDaiKuanProductModel = opNewGeiNiHuaDaiKuanBaseModel.getData().get(0);
                                    initBannerAdapter(opNewGeiNiHuaDaiKuanBaseModel.getData());
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
        OpNewGeiNiHuaDaiKuanHttpApi.getInterfaceUtils().bannerList()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<OpNewGeiNiHuaDaiKuanBaseModel<List<OpNewGeiNiHuaDaiKuanBannerModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpNewGeiNiHuaDaiKuanOpenUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(OpNewGeiNiHuaDaiKuanBaseModel<List<OpNewGeiNiHuaDaiKuanBannerModel>> opNewGeiNiHuaDaiKuanBaseModel) {
                        if (opNewGeiNiHuaDaiKuanBaseModel != null) {
                            if (opNewGeiNiHuaDaiKuanBaseModel.getCode() == 200) {
                                if (opNewGeiNiHuaDaiKuanBaseModel.getData() != null && opNewGeiNiHuaDaiKuanBaseModel.getData().size() > 0) {
                                    ILFactory.getLoader().loadNet(banner_img, OpNewGeiNiHuaDaiKuanHttpApi.HTTP_API_URL + opNewGeiNiHuaDaiKuanBaseModel.getData().get(0).getLogo(),
                                            new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
                                }
                            }
                        }
                    }
                });
    }

    private void addProductView(List<OpNewGeiNiHuaDaiKuanProductModel> mList) {
        for (OpNewGeiNiHuaDaiKuanProductModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_gei_ni_hua_dai_kuan_op_new_product_item, null);
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
            ILFactory.getLoader().loadNet(pic, OpNewGeiNiHuaDaiKuanHttpApi.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(OpNewGeiNiHuaDaiKuanProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpNewGeiNiHuaDaiKuanOpenUtil.getValue((XActivity) getActivity(), JumpH5ActivityJinRiYouQianHua.class, bundle);
        }
    }
}
