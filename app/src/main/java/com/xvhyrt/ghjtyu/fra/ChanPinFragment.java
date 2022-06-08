package com.xvhyrt.ghjtyu.fra;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.act.JumpH5Activity;
import com.xvhyrt.ghjtyu.net.WangLuoApi;
import com.xvhyrt.ghjtyu.imageloader.ILFactory;
import com.xvhyrt.ghjtyu.imageloader.ILoader;
import com.xvhyrt.ghjtyu.bean.ParentModel;
import com.xvhyrt.ghjtyu.bean.ChanPinModel;
import com.xvhyrt.ghjtyu.mvp.XFragment;
import com.xvhyrt.ghjtyu.net.ApiSubscriber;
import com.xvhyrt.ghjtyu.net.NetError;
import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.uti.GongJuLei;
import com.xvhyrt.ghjtyu.uti.SPFile;

import java.util.List;

import butterknife.BindView;

public class ChanPinFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_ll)
    View main_top_ll;
    @BindView(R.id.jx_bg)
    View jx_bg;
    private ChanPinModel chanPinModel;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_ll.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_ll.setOnClickListener(v -> {
            productClick(chanPinModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(chanPinModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(chanPinModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_parent;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ChanPinModel model) {
        if (model != null) {
            phone = SPFile.getString("phone");
            WangLuoApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<ParentModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(ParentModel parentModel) {
                            toWeb(model);
                        }
                    });
        }
    }


    public void productList() {
        mobileType = SPFile.getInt("mobileType");
        WangLuoApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<ParentModel<List<ChanPinModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        GongJuLei.showErrorInfo(getActivity(), error);
                        if (goodsListLl.getChildCount() == 0) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(ParentModel<List<ChanPinModel>> parentModel) {
                        setRefreshing.setRefreshing(false);
                        if (parentModel != null) {
                            if (parentModel.getCode() == 200 && parentModel.getData() != null) {
                                if (parentModel.getData() != null && parentModel.getData().size() > 0) {
                                    chanPinModel = parentModel.getData().get(0);
                                    addProductView(parentModel.getData());
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

    private void addProductView(List<ChanPinModel> mList) {
        goodsListLl.removeAllViews();
        for (ChanPinModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView goods_mingzi_tv = view.findViewById(R.id.goods_mingzi_tv);
            TextView label_tv = view.findViewById(R.id.label_tv);
            TextView price_tv = view.findViewById(R.id.price_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            TextView time_tv = view.findViewById(R.id.time_tv);
            TextView many_tv = view.findViewById(R.id.many_tv);
            many_tv.setText(String.valueOf(model.getPassingRate()));
            ILFactory.getLoader().loadNet(pic, WangLuoApi.HTTP_API_URL + model.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            goods_mingzi_tv.setText(model.getProductName());
            time_tv.setText(model.getDes() + "个月");
            label_tv.setText(model.getTag());
            price_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
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

    public void toWeb(ChanPinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            GongJuLei.jumpPage(getActivity(), JumpH5Activity.class, bundle);
        }
    }
}
