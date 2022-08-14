package com.xiangfencqiasfew.ertaehrstyu.xiangfenqifragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xiangfencqiasfew.ertaehrstyu.R;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity.ImageAdapterXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity.MainXiangFenQiActivity;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity.XiangFenQiJumpH5Activity;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiapi.XiangFenQiHttpApi;
import com.xiangfencqiasfew.ertaehrstyu.imageloader.ILFactory;
import com.xiangfencqiasfew.ertaehrstyu.imageloader.ILoader;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.BannerModelXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.BaseModelXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.ProductModelXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.mvp.XActivity;
import com.xiangfencqiasfew.ertaehrstyu.mvp.XFragment;
import com.xiangfencqiasfew.ertaehrstyu.net.ApiSubscriber;
import com.xiangfencqiasfew.ertaehrstyu.net.NetError;
import com.xiangfencqiasfew.ertaehrstyu.net.XApi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.OpenXiangFenQiUtil;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.PreferencesOpenUtilXiangFenQi;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class MainFragmentXiangFenQi extends XFragment {

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

    private ProductModelXiangFenQi productModelXiangFenQi;

    private Bundle bundle;

    private ImageAdapterXiangFenQi imageAdapterXiangFenQi;

    @Override
    public void initData(Bundle savedInstanceState) {
//        banner_fl.setVisibility(View.VISIBLE);
        goodsListLl.setVisibility(View.VISIBLE);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
                bannerList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelXiangFenQi);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productModelXiangFenQi);
        });
        mine_tv.setOnClickListener(v -> {
            ((MainXiangFenQiActivity) getActivity()).jumpMine();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
        bannerList();
    }

    private void initBannerAdapter(List<ProductModelXiangFenQi> data) {
        imageAdapterXiangFenQi = null;
        imageAdapterXiangFenQi = new ImageAdapterXiangFenQi(data);
        imageAdapterXiangFenQi.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapterXiangFenQi);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_xiang_fen_qi;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelXiangFenQi model) {
        if (model == null) {
            return;
        }
        phone = PreferencesOpenUtilXiangFenQi.getString("phone");
        XiangFenQiHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi baseModelXiangFenQi) {
                        toWeb(model);
                    }
                });
    }


    public void productList() {
        mobileType = PreferencesOpenUtilXiangFenQi.getInt("mobileType");
        phone = PreferencesOpenUtilXiangFenQi.getString("phone");
        productModelXiangFenQi = null;
        XiangFenQiHttpApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi<List<ProductModelXiangFenQi>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenXiangFenQiUtil.showErrorInfo(getActivity(), error);
                        if (imageAdapterXiangFenQi == null) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi<List<ProductModelXiangFenQi>> baseModelXiangFenQi) {
                        setRefreshing.setRefreshing(false);
                        goodsListLl.removeAllViews();
                        if (baseModelXiangFenQi != null) {
                            if (baseModelXiangFenQi.getCode() == 200 && baseModelXiangFenQi.getData() != null) {
                                if (baseModelXiangFenQi.getData() != null && baseModelXiangFenQi.getData().size() > 0) {
                                    productModelXiangFenQi = baseModelXiangFenQi.getData().get(0);
//                                        initBannerAdapter(baseModelXiangFenQi.getData());
                                    addProductView(baseModelXiangFenQi.getData());
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

    private void bannerList() {
        XiangFenQiHttpApi.getInterfaceUtils().bannerList()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi<List<BannerModelXiangFenQi>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenXiangFenQiUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi<List<BannerModelXiangFenQi>> baseModelXiangFenQi) {
                        if (baseModelXiangFenQi != null) {
                            if (baseModelXiangFenQi.getCode() == 200) {
                                if (baseModelXiangFenQi.getData() != null && baseModelXiangFenQi.getData().size() > 0) {
                                    ILFactory.getLoader().loadNet(banner_img, XiangFenQiHttpApi.HTTP_API_URL + baseModelXiangFenQi.getData().get(0).getLogo(),
                                            new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
                                }
                            }
                        }
                    }
                });
    }

    private void addProductView(List<ProductModelXiangFenQi> mList) {
        for (ProductModelXiangFenQi model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_xiang_fen_qi, null);
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
            ILFactory.getLoader().loadNet(pic, XiangFenQiHttpApi.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(ProductModelXiangFenQi model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenXiangFenQiUtil.getValue((XActivity) getActivity(), XiangFenQiJumpH5Activity.class, bundle);
        }
    }
}
