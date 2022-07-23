package com.wolai.dai.suipian;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wolai.dai.R;
import com.wolai.dai.mvp.XActivity;
import com.wolai.dai.yemian.ImageAdapter;
import com.wolai.dai.yemian.JixinJumpH5Activity;
import com.wolai.dai.yemian.JixinMainActivity;
import com.wolai.dai.jiekou.JiXinApi;
import com.wolai.dai.shiti.JixinBaseModel;
import com.wolai.dai.shiti.JixinProductModel;
import com.wolai.dai.mvp.XFragment;
import com.wolai.dai.net.ApiSubscriber;
import com.wolai.dai.net.NetError;
import com.wolai.dai.net.XApi;
import com.wolai.dai.gongju.JiXinOpenUtil;
import com.wolai.dai.gongju.JiXinPreferencesOpenUtil;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class JixinMainFragment extends XFragment {

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
    @BindView(R.id.more_fl)
    View moreFl;
    @BindView(R.id.shenqing_sl)
    View shenqingSl;

    private JixinProductModel jixinProductModel;

    private Bundle bundle;

    private ImageAdapter imageAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        productList();
        banner.setBannerGalleryEffect(0, (int) (px2dp(width()) / 5), 15, 0.85f);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(jixinProductModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(jixinProductModel);
        });
        bgFl.setOnClickListener(v -> {
            productClick(jixinProductModel);
        });
        shenqingSl.setOnClickListener(v -> {
            productClick(jixinProductModel);
        });
        moreFl.setOnClickListener(v -> {
            if (getActivity() instanceof JixinMainActivity) {
                ((JixinMainActivity) getActivity()).jumpMore();
            }
        });
    }

    public int width() {
        return getActivity().getResources().getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue 像素
     * @return 虚拟像素
     */
    public float px2dp(int pxValue) {
        return (pxValue / getActivity().getResources().getSystem().getDisplayMetrics().density);
    }

    private void initBannerAdapter(List<JixinProductModel> data) {
        imageAdapter = null;
        imageAdapter = new ImageAdapter(data);
        imageAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.jixin_fragment_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(JixinProductModel model) {
        if (!TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("API_BASE_URL"))) {
            if (model == null) {
                return;
            }
            phone = JiXinPreferencesOpenUtil.getString("phone");
            JiXinApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<JixinBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(JixinBaseModel baseModel) {
                            toWeb(model);
                        }
                    });
        }
    }


    public void productList() {
        if (!TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("API_BASE_URL"))) {
            mobileType = JiXinPreferencesOpenUtil.getInt("mobileType");
            JiXinApi.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<JixinBaseModel<List<JixinProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            JiXinOpenUtil.showErrorInfo(getActivity(), error);
                            if (imageAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(JixinBaseModel<List<JixinProductModel>> baseModel) {
                            setRefreshing.setRefreshing(false);
                            if (baseModel != null) {
                                if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                    if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                        jixinProductModel = baseModel.getData().get(0);
                                        initBannerAdapter(baseModel.getData());
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
    }

    public void toWeb(JixinProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            JiXinOpenUtil.getValue((XActivity) getActivity(), JixinJumpH5Activity.class, bundle);
        }
    }
}
