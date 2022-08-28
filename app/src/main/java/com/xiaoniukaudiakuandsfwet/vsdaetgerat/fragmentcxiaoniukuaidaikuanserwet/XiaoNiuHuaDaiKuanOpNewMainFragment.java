package com.xiaoniukaudiakuandsfwet.vsdaetgerat.fragmentcxiaoniukuaidaikuanserwet;

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

import com.xiaoniukaudiakuandsfwet.vsdaetgerat.R;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewImageAdapter;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewMainActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewJumpActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.apicxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewHttpApi;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.imageloader.ILFactory;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.imageloader.ILoader;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewBannerModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewBaseModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewProductModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.mvp.XActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.mvp.XFragment;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.ApiSubscriber;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.NetError;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.XApi;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewOpenUtil;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class XiaoNiuHuaDaiKuanOpNewMainFragment extends XFragment {

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

    private XiaoNiuHuaDaiKuanOpNewProductModel xiaoNiuHuaDaiKuanOpNewProductModel;

    private Bundle bundle;

    private XiaoNiuHuaDaiKuanOpNewImageAdapter xiaoNiuHuaDaiKuanOpNewImageAdapter;

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
            productClick(xiaoNiuHuaDaiKuanOpNewProductModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(xiaoNiuHuaDaiKuanOpNewProductModel);
        });
        mine_tv.setOnClickListener(v -> {
            ((XiaoNiuHuaDaiKuanOpNewMainActivity) getActivity()).jumpMine();
        });
        banner_img.setOnClickListener(v -> {
            productClick(xiaoNiuHuaDaiKuanOpNewProductModel);
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

    private void initBannerAdapter(List<XiaoNiuHuaDaiKuanOpNewProductModel> data) {
        xiaoNiuHuaDaiKuanOpNewImageAdapter = null;
        xiaoNiuHuaDaiKuanOpNewImageAdapter = new XiaoNiuHuaDaiKuanOpNewImageAdapter(data);
        xiaoNiuHuaDaiKuanOpNewImageAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(xiaoNiuHuaDaiKuanOpNewImageAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_xiao_niu_hua_dai_kuan_op_new;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(XiaoNiuHuaDaiKuanOpNewProductModel model) {
            if (model == null) {
                return;
            }
            phone = XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getString("phone");
            XiaoNiuHuaDaiKuanOpNewHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<XiaoNiuHuaDaiKuanOpNewBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(XiaoNiuHuaDaiKuanOpNewBaseModel xiaoNiuHuaDaiKuanOpNewBaseModel) {
                            toWeb(model);
                        }
                    });
    }


    public void productList() {
            mobileType = XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getInt("mobileType");
            phone = XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getString("phone");
            xiaoNiuHuaDaiKuanOpNewProductModel = null;
            XiaoNiuHuaDaiKuanOpNewHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<XiaoNiuHuaDaiKuanOpNewBaseModel<List<XiaoNiuHuaDaiKuanOpNewProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            XiaoNiuHuaDaiKuanOpNewOpenUtil.showErrorInfo(getActivity(), error);
                            if (xiaoNiuHuaDaiKuanOpNewImageAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(XiaoNiuHuaDaiKuanOpNewBaseModel<List<XiaoNiuHuaDaiKuanOpNewProductModel>> xiaoNiuHuaDaiKuanOpNewBaseModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (xiaoNiuHuaDaiKuanOpNewBaseModel != null) {
                                if (xiaoNiuHuaDaiKuanOpNewBaseModel.getCode() == 200 && xiaoNiuHuaDaiKuanOpNewBaseModel.getData() != null) {
                                    if (xiaoNiuHuaDaiKuanOpNewBaseModel.getData() != null && xiaoNiuHuaDaiKuanOpNewBaseModel.getData().size() > 0) {
                                        xiaoNiuHuaDaiKuanOpNewProductModel = xiaoNiuHuaDaiKuanOpNewBaseModel.getData().get(0);
//                                        initBannerAdapter(baseModel.getData());
                                        addProductView(xiaoNiuHuaDaiKuanOpNewBaseModel.getData());
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
            XiaoNiuHuaDaiKuanOpNewHttpApi.getInterfaceUtils().bannerList()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<XiaoNiuHuaDaiKuanOpNewBaseModel<List<XiaoNiuHuaDaiKuanOpNewBannerModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            XiaoNiuHuaDaiKuanOpNewOpenUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(XiaoNiuHuaDaiKuanOpNewBaseModel<List<XiaoNiuHuaDaiKuanOpNewBannerModel>> xiaoNiuHuaDaiKuanOpNewBaseModel) {
                            if (xiaoNiuHuaDaiKuanOpNewBaseModel != null) {
                                if (xiaoNiuHuaDaiKuanOpNewBaseModel.getCode() == 200) {
                                    if (xiaoNiuHuaDaiKuanOpNewBaseModel.getData() != null && xiaoNiuHuaDaiKuanOpNewBaseModel.getData().size() > 0) {
                                            ILFactory.getLoader().loadNet(banner_img, XiaoNiuHuaDaiKuanOpNewHttpApi.HTTP_API_URL + xiaoNiuHuaDaiKuanOpNewBaseModel.getData().get(0).getLogo(),
                                                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
                                    }
                                }
                            }
                        }
                    });
    }

    private void addProductView(List<XiaoNiuHuaDaiKuanOpNewProductModel> mList) {
        for (XiaoNiuHuaDaiKuanOpNewProductModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_xiao_niu_hua_dai_kuan_op_new_item, null);
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
                ILFactory.getLoader().loadNet(pic, XiaoNiuHuaDaiKuanOpNewHttpApi.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(XiaoNiuHuaDaiKuanOpNewProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            XiaoNiuHuaDaiKuanOpNewOpenUtil.getValue((XActivity) getActivity(), XiaoNiuHuaDaiKuanOpNewJumpActivity.class, bundle);
        }
    }
}
