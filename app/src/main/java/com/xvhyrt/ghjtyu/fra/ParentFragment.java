package com.xvhyrt.ghjtyu.fra;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.act.LunBoAdapter;
import com.xvhyrt.ghjtyu.act.JumpH5Activity;
import com.xvhyrt.ghjtyu.net.WangLuoApi;
import com.xvhyrt.ghjtyu.bean.ParentModel;
import com.xvhyrt.ghjtyu.bean.ChanPinModel;
import com.xvhyrt.ghjtyu.mvp.XFragment;
import com.xvhyrt.ghjtyu.net.ApiSubscriber;
import com.xvhyrt.ghjtyu.net.NetError;
import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.uti.GongJuLei;
import com.xvhyrt.ghjtyu.uti.SPFile;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class ParentFragment extends XFragment {

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
    @BindView(R.id.bg_fl)
    FrameLayout bgFl;

    private ChanPinModel chanPinModel;

    private Bundle bundle;

    private LunBoAdapter imageAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(chanPinModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(chanPinModel);
        });
        bgFl.setOnClickListener(v -> {
            productClick(chanPinModel);
        });
    }

    private void initBannerAdapter(List<ChanPinModel> data) {
        imageAdapter = null;
        imageAdapter = new LunBoAdapter(data);
        imageAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapter);
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
        if (model == null){
            return;
        }
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
                        if (imageAdapter == null) {
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
                                    initBannerAdapter(parentModel.getData());
                                } else {
                                    if (imageAdapter == null) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (imageAdapter == null) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (imageAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    public void toWeb(ChanPinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            GongJuLei.jumpPage(getActivity(), JumpH5Activity.class, bundle);
        }
    }
}
