package com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyusuipian;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.snvhretnsjggdsfd.tughuturtgf.R;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyukongjian.AmountHaoJJQiaHwfhsnWindow;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian.ItemHaoJJQiaHwfhsnAdapter;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian.MainHaoJJQiaHwfhsnActivity;
import com.snvhretnsjggdsfd.tughuturtgf.mvp.XActivity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.HaoJJQiaHwfhsnItemModel;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian.ImageAdapterHaoJJQiaHwfhsn;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian.JumpH5HaoJJQiaHwfhsnActivity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuapi.HaoJJQiaHwfhsnApi;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.BaseHaoJJQiaHwfhsnModel;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.ProductHaoJJQiaHwfhsnModel;
import com.snvhretnsjggdsfd.tughuturtgf.mvp.XFragment;
import com.snvhretnsjggdsfd.tughuturtgf.net.ApiSubscriber;
import com.snvhretnsjggdsfd.tughuturtgf.net.NetError;
import com.snvhretnsjggdsfd.tughuturtgf.net.XApi;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuu.OpenHaoJJQiaHwfhsnUtil;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuu.PreferenceHaoJJQiaHwfhsnOpenUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MainHaoJJQiaHwfhsnFragment extends XFragment {

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
    @BindView(R.id.progress_tv)
    TextView progress_tv;
    @BindView(R.id.item_list)
    RecyclerView item_list;
    @BindView(R.id.jine_ll)
    View jine_ll;
    @BindView(R.id.root_ll)
    View root_ll;
    @BindView(R.id.shenqing_tv)
    View shenqing_tv;
    @BindView(R.id.banner_iv)
    View banner_iv;

    private Bundle bundle;

    private ImageAdapterHaoJJQiaHwfhsn imageAdapterHaoJJQiaHwfhsn;
    private ItemHaoJJQiaHwfhsnAdapter itemHaoJJQiaHwfhsnAdapter;
    private AmountHaoJJQiaHwfhsnWindow amountHaoJJQiaHwfhsnWindow;
    private int[] location;

    public List<ProductHaoJJQiaHwfhsnModel> productModels = new ArrayList<>();
    private int index = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        initItemAdapter();
        banner.setBannerGalleryEffect(0, (int) (px2dp(width()) / 5), 15, 0.85f);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        amountHaoJJQiaHwfhsnWindow = new AmountHaoJJQiaHwfhsnWindow(getActivity());
        amountHaoJJQiaHwfhsnWindow.setOnItemClieckListener(new AmountHaoJJQiaHwfhsnWindow.OnItemClieckListener() {
            @Override
            public void cliecked(String text) {
                progress_tv.setText(text);
            }
        });
        moreFl.setOnClickListener(v -> {
            if (getActivity() instanceof MainHaoJJQiaHwfhsnActivity) {
                ((MainHaoJJQiaHwfhsnActivity) getActivity()).jumpMore();
            }
        });
        jine_ll.setOnClickListener(v -> {
            location = new int[2];
            jine_ll.getLocationOnScreen(location);
            amountHaoJJQiaHwfhsnWindow.showPopupWindow(location[0] + jine_ll.getWidth(), location[1] + jine_ll.getHeight());
        });
        shenqing_tv.setOnClickListener(v -> {
            productClick(getGoodsModel());
        });
        banner_iv.setOnClickListener(v -> {
            productClick(getGoodsModel());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
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

    private void initBannerAdapter(List<ProductHaoJJQiaHwfhsnModel> data) {
        imageAdapterHaoJJQiaHwfhsn = null;
        imageAdapterHaoJJQiaHwfhsn = new ImageAdapterHaoJJQiaHwfhsn(data);
        imageAdapterHaoJJQiaHwfhsn.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapterHaoJJQiaHwfhsn);
    }

    private void initItemAdapter() {
        List<HaoJJQiaHwfhsnItemModel> list = new ArrayList<>();
        HaoJJQiaHwfhsnItemModel model = new HaoJJQiaHwfhsnItemModel();
        model.setName("3期");
        model.setAmount("5000");
        list.add(model);
        HaoJJQiaHwfhsnItemModel model1 = new HaoJJQiaHwfhsnItemModel();
        model1.setName("6期");
        model1.setAmount("50000");
        list.add(model1);
        HaoJJQiaHwfhsnItemModel model2 = new HaoJJQiaHwfhsnItemModel();
        model2.setName("9期");
        model2.setAmount("100000");
        model2.setChecked(true);
        model2.setAomuntChecked(true);
        list.add(model2);
        HaoJJQiaHwfhsnItemModel model3 = new HaoJJQiaHwfhsnItemModel();
        model3.setName("12期");
        model3.setAmount("150000");
        list.add(model3);
        HaoJJQiaHwfhsnItemModel model4 = new HaoJJQiaHwfhsnItemModel();
        model4.setName("24期");
        model4.setAmount("200000");
        list.add(model4);
        itemHaoJJQiaHwfhsnAdapter = new ItemHaoJJQiaHwfhsnAdapter(getActivity());
        itemHaoJJQiaHwfhsnAdapter.setHasStableIds(true);
        itemHaoJJQiaHwfhsnAdapter.setData(list);
        itemHaoJJQiaHwfhsnAdapter.setRecItemClick(new RecyclerItemCallback<HaoJJQiaHwfhsnItemModel, ItemHaoJJQiaHwfhsnAdapter.ItemViewHolder>() {
            @Override
            public void onItemClick(int position, HaoJJQiaHwfhsnItemModel model, int tag, ItemHaoJJQiaHwfhsnAdapter.ItemViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                progress_tv.setText(model.getAmount());
            }
        });
        item_list.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        item_list.setHasFixedSize(true);
        item_list.setAdapter(itemHaoJJQiaHwfhsnAdapter);
    }

    private ProductHaoJJQiaHwfhsnModel getGoodsModel() {
        ProductHaoJJQiaHwfhsnModel productModel = null;
        if (productModels.size() <= index) {
            index = 0;
        }
        if (productModels != null && productModels.size() > index) {
            productModel = productModels.get(index);
            if (index < productModels.size() - 1) {
                index = index + 1;
            } else {
                index = 0;
            }
        }
        return productModel;
    }


    @Override
    public int getLayoutId() {
        return R.layout.hao_jjqian_dfjr_uert_hw_fragment_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductHaoJJQiaHwfhsnModel model) {
        if (model == null) {
            return;
        }
        phone = PreferenceHaoJJQiaHwfhsnOpenUtil.getString("phone");
        productModels.clear();
        HaoJJQiaHwfhsnApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHaoJJQiaHwfhsnModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseHaoJJQiaHwfhsnModel baseModel) {
                        toWeb(model);
                    }
                });
    }


    public void productList() {
        mobileType = PreferenceHaoJJQiaHwfhsnOpenUtil.getInt("mobileType");
        productModels.clear();
        phone = PreferenceHaoJJQiaHwfhsnOpenUtil.getString("phone");
        HaoJJQiaHwfhsnApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHaoJJQiaHwfhsnModel<List<ProductHaoJJQiaHwfhsnModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenHaoJJQiaHwfhsnUtil.showErrorInfo(getActivity(), error);
                        if (imageAdapterHaoJJQiaHwfhsn == null) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(BaseHaoJJQiaHwfhsnModel<List<ProductHaoJJQiaHwfhsnModel>> baseModel) {
                        setRefreshing.setRefreshing(false);
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productModels.addAll(baseModel.getData());
                                    initBannerAdapter(baseModel.getData());
                                } else {
                                    if (imageAdapterHaoJJQiaHwfhsn == null) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (imageAdapterHaoJJQiaHwfhsn == null) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (imageAdapterHaoJJQiaHwfhsn == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    public void toWeb(ProductHaoJJQiaHwfhsnModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenHaoJJQiaHwfhsnUtil.getValue((XActivity) getActivity(), JumpH5HaoJJQiaHwfhsnActivity.class, bundle);
        }
    }
}
