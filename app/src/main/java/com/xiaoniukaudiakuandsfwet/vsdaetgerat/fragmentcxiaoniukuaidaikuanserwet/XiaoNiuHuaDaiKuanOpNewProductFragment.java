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
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewJumpActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.apicxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewHttpApi;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.imageloader.ILFactory;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.imageloader.ILoader;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewBaseModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewProductModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.mvp.XActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.mvp.XFragment;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.ApiSubscriber;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.NetError;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.XApi;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewOpenUtil;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil;

import java.util.List;

import butterknife.BindView;

public class XiaoNiuHuaDaiKuanOpNewProductFragment extends XFragment {

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
    @BindView(R.id.tuijian_fl)
    View tuijian_fl;
    @BindView(R.id.top_ll)
    View top_ll;
    private XiaoNiuHuaDaiKuanOpNewProductModel xiaoNiuHuaDaiKuanOpNewProductModel;

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

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_img.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        tuijian_fl.setVisibility(View.GONE);
        top_ll.setVisibility(View.GONE);
        productList();
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
        goodsListLl.setOnClickListener(v -> {
            productClick(xiaoNiuHuaDaiKuanOpNewProductModel);
        });
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
            if (model != null) {
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
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int otyujdtktdy(Context context) {
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
    public static int werzdh(Context context) {
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
    public static int ujrthyfgjn(Context context) {
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

    public void productList() {
            mobileType = XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getInt("mobileType");
            phone = XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getString("phone");
            XiaoNiuHuaDaiKuanOpNewHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<XiaoNiuHuaDaiKuanOpNewBaseModel<List<XiaoNiuHuaDaiKuanOpNewProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            XiaoNiuHuaDaiKuanOpNewOpenUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(XiaoNiuHuaDaiKuanOpNewBaseModel<List<XiaoNiuHuaDaiKuanOpNewProductModel>> xiaoNiuHuaDaiKuanOpNewBaseModel) {
                            setRefreshing.setRefreshing(false);
                            if (xiaoNiuHuaDaiKuanOpNewBaseModel != null) {
                                if (xiaoNiuHuaDaiKuanOpNewBaseModel.getCode() == 200 && xiaoNiuHuaDaiKuanOpNewBaseModel.getData() != null) {
                                    if (xiaoNiuHuaDaiKuanOpNewBaseModel.getData() != null && xiaoNiuHuaDaiKuanOpNewBaseModel.getData().size() > 0) {
                                        xiaoNiuHuaDaiKuanOpNewProductModel = xiaoNiuHuaDaiKuanOpNewBaseModel.getData().get(0);
                                        addProductView(xiaoNiuHuaDaiKuanOpNewBaseModel.getData());
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
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int aagerygh(Context context) {
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
    public static int retrtez(Context context) {
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
    public static int mktrhsh(Context context) {
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

    private void addProductView(List<XiaoNiuHuaDaiKuanOpNewProductModel> mList) {
        goodsListLl.removeAllViews();
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
            shijian_tv.setText(model.getDes() + "个月");
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
            bundle.putString("title", model.getProductName());
            XiaoNiuHuaDaiKuanOpNewOpenUtil.getValue((XActivity) getActivity(), XiaoNiuHuaDaiKuanOpNewJumpActivity.class, bundle);
        }
    }
}
