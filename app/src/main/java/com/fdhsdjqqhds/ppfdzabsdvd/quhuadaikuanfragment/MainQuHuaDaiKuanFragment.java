package com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanfragment;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanactivity.ImageAdapterQuHuaDaiKuan;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanactivity.QuHuaDaiKuanMainActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanactivity.JumpH5QuHuaDaiKuanActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanapi.HttpApiQuHuaDaiKuan;
import com.fdhsdjqqhds.ppfdzabsdvd.imageloader.ILFactory;
import com.fdhsdjqqhds.ppfdzabsdvd.imageloader.ILoader;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.BannerModelQuHuaDaiKuan;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.QuHuaDaiKuanBaseModel;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.QuHuaDaiKuanProductModel;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XFragment;
import com.fdhsdjqqhds.ppfdzabsdvd.net.ApiSubscriber;
import com.fdhsdjqqhds.ppfdzabsdvd.net.NetError;
import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanutils.OpenQuHuaDaiKuanUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanutils.PreferencesOpenUtilQuHuaDaiKuan;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainQuHuaDaiKuanFragment extends XFragment {

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
    @BindView(R.id.mine_tv)
    TextView mine_tv;

    private QuHuaDaiKuanProductModel quHuaDaiKuanProductModel;

    private Bundle bundle;

    private ImageAdapterQuHuaDaiKuan imageAdapterQuHuaDaiKuan;

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

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
            productClick(quHuaDaiKuanProductModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(quHuaDaiKuanProductModel);
        });
        mine_tv.setOnClickListener(v -> {
            ((QuHuaDaiKuanMainActivity) getActivity()).jumpMine();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
        bannerList();
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int mlkytujdtuj(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int wertdxhru(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int yuidtyjdry(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private void initBannerAdapter(List<QuHuaDaiKuanProductModel> data) {
        imageAdapterQuHuaDaiKuan = null;
        imageAdapterQuHuaDaiKuan = new ImageAdapterQuHuaDaiKuan(data);
        imageAdapterQuHuaDaiKuan.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapterQuHuaDaiKuan);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_qu_hua_dai_kuan;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(QuHuaDaiKuanProductModel model) {
        if (model == null) {
            return;
        }
        phone = PreferencesOpenUtilQuHuaDaiKuan.getString("phone");
        HttpApiQuHuaDaiKuan.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<QuHuaDaiKuanBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(QuHuaDaiKuanBaseModel quHuaDaiKuanBaseModel) {
                        toWeb(model);
                    }
                });
    }


    public void productList() {
        mobileType = PreferencesOpenUtilQuHuaDaiKuan.getInt("mobileType");
        phone = PreferencesOpenUtilQuHuaDaiKuan.getString("phone");
        quHuaDaiKuanProductModel = null;
        HttpApiQuHuaDaiKuan.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<QuHuaDaiKuanBaseModel<List<QuHuaDaiKuanProductModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenQuHuaDaiKuanUtil.showErrorInfo(getActivity(), error);
                        if (imageAdapterQuHuaDaiKuan == null) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(QuHuaDaiKuanBaseModel<List<QuHuaDaiKuanProductModel>> quHuaDaiKuanBaseModel) {
                        setRefreshing.setRefreshing(false);
                        goodsListLl.removeAllViews();
                        if (quHuaDaiKuanBaseModel != null) {
                            if (quHuaDaiKuanBaseModel.getCode() == 200 && quHuaDaiKuanBaseModel.getData() != null) {
                                if (quHuaDaiKuanBaseModel.getData() != null && quHuaDaiKuanBaseModel.getData().size() > 0) {
                                    quHuaDaiKuanProductModel = quHuaDaiKuanBaseModel.getData().get(0);
//                                        initBannerAdapter(baseModel.getData());
                                    addProductView(quHuaDaiKuanBaseModel.getData());
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
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int werfzhddj(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int utrjhdrtiu(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int ertdhgersy(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private void bannerList() {
        HttpApiQuHuaDaiKuan.getInterfaceUtils().bannerList()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<QuHuaDaiKuanBaseModel<List<BannerModelQuHuaDaiKuan>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenQuHuaDaiKuanUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(QuHuaDaiKuanBaseModel<List<BannerModelQuHuaDaiKuan>> quHuaDaiKuanBaseModel) {
                        if (quHuaDaiKuanBaseModel != null) {
                            if (quHuaDaiKuanBaseModel.getCode() == 200) {
                                if (quHuaDaiKuanBaseModel.getData() != null && quHuaDaiKuanBaseModel.getData().size() > 0) {
                                    ILFactory.getLoader().loadNet(banner_img, HttpApiQuHuaDaiKuan.HTTP_API_URL + quHuaDaiKuanBaseModel.getData().get(0).getLogo(),
                                            new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
                                }
                            }
                        }
                    }
                });
    }

    private void addProductView(List<QuHuaDaiKuanProductModel> mList) {
        for (QuHuaDaiKuanProductModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_qu_hua_dai_kuan_item, null);
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
            ILFactory.getLoader().loadNet(pic, HttpApiQuHuaDaiKuan.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(QuHuaDaiKuanProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenQuHuaDaiKuanUtil.getValue((XActivity) getActivity(), JumpH5QuHuaDaiKuanActivity.class, bundle);
        }
    }
}
