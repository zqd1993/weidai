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
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity.JumpH5ActivityJinRiYouQianHua;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewaapi.OpNewGeiNiHuaDaiKuanHttpApi;
import com.fvhrnthdfggeinihua.hdfghuerungf.imageloader.ILFactory;
import com.fvhrnthdfggeinihua.hdfghuerungf.imageloader.ILoader;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanBaseModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanProductModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.mvp.XActivity;
import com.fvhrnthdfggeinihua.hdfghuerungf.mvp.XFragment;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.ApiSubscriber;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.NetError;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.XApi;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanOpenUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanPreferencesOpenUtil;

import java.util.List;

import butterknife.BindView;

public class OpNewGeiNiHuaDaiKuanProductFragment extends XFragment {

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
    private OpNewGeiNiHuaDaiKuanProductModel opNewGeiNiHuaDaiKuanProductModel;

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
            productClick(opNewGeiNiHuaDaiKuanProductModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(opNewGeiNiHuaDaiKuanProductModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(opNewGeiNiHuaDaiKuanProductModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gei_ni_hua_dai_kuan_op_new_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(OpNewGeiNiHuaDaiKuanProductModel model) {
            if (model != null) {
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
            mobileType = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getInt("mobileType");
            phone = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getString("phone");
            OpNewGeiNiHuaDaiKuanHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<OpNewGeiNiHuaDaiKuanBaseModel<List<OpNewGeiNiHuaDaiKuanProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpNewGeiNiHuaDaiKuanOpenUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(OpNewGeiNiHuaDaiKuanBaseModel<List<OpNewGeiNiHuaDaiKuanProductModel>> opNewGeiNiHuaDaiKuanBaseModel) {
                            setRefreshing.setRefreshing(false);
                            if (opNewGeiNiHuaDaiKuanBaseModel != null) {
                                if (opNewGeiNiHuaDaiKuanBaseModel.getCode() == 200 && opNewGeiNiHuaDaiKuanBaseModel.getData() != null) {
                                    if (opNewGeiNiHuaDaiKuanBaseModel.getData() != null && opNewGeiNiHuaDaiKuanBaseModel.getData().size() > 0) {
                                        opNewGeiNiHuaDaiKuanProductModel = opNewGeiNiHuaDaiKuanBaseModel.getData().get(0);
                                        addProductView(opNewGeiNiHuaDaiKuanBaseModel.getData());
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

    private void addProductView(List<OpNewGeiNiHuaDaiKuanProductModel> mList) {
        goodsListLl.removeAllViews();
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
            shijian_tv.setText(model.getDes() + "个月");
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

    public void toWeb(OpNewGeiNiHuaDaiKuanProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpNewGeiNiHuaDaiKuanOpenUtil.getValue((XActivity) getActivity(), JumpH5ActivityJinRiYouQianHua.class, bundle);
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
