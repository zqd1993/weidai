package com.nadryeary.msftusertyu.wxxyhsuipian;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nadryeary.msftusertyu.R;
import com.nadryeary.msftusertyu.wxxyhkongjian.AmountWeiXxyongHuaWindow;
import com.nadryeary.msftusertyu.mvp.XActivity;
import com.nadryeary.msftusertyu.wxxyhshiti.WeiXxyongHuaItemModel;
import com.nadryeary.msftusertyu.wxxyhyemian.ImageAdapterWeiXxyongHua;
import com.nadryeary.msftusertyu.wxxyhyemian.ItemWeiXxyongHuaAdapter;
import com.nadryeary.msftusertyu.wxxyhyemian.JumpH5WeiXxyongHuaActivity;
import com.nadryeary.msftusertyu.wxxyhyemian.MainWeiXxyongHuaActivity;
import com.nadryeary.msftusertyu.wxxyhjiekou.WeiXxyongHuaApi;
import com.nadryeary.msftusertyu.wxxyhshiti.BaseWeiXxyongHuaModel;
import com.nadryeary.msftusertyu.wxxyhshiti.ProductWeiXxyongHuaModel;
import com.nadryeary.msftusertyu.mvp.XFragment;
import com.nadryeary.msftusertyu.net.ApiSubscriber;
import com.nadryeary.msftusertyu.net.NetError;
import com.nadryeary.msftusertyu.net.XApi;
import com.nadryeary.msftusertyu.wxxyhgongju.OpenWeiXxyongHuaUtil;
import com.nadryeary.msftusertyu.wxxyhgongju.PreferencesWeiXxyongHuaOpenUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MainWeiXxyongHuaFragment extends XFragment {

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
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
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

    private ImageAdapterWeiXxyongHua imageAdapterWeiXxyongHua;
    private ItemWeiXxyongHuaAdapter itemWeiXxyongHuaAdapter;
    private AmountWeiXxyongHuaWindow amountWeiXxyongHuaWindow;
    private int[] location;

    private String[] msg = {"恭喜187****5758用户领取87000元额度", "恭喜138****5666用户领取36000元额度", "恭喜199****5009用户领取49000元额度",
            "恭喜137****6699用户领取69000元额度", "恭喜131****8889用户领取18000元额度", "恭喜177****8899用户领取26000元额度",
            "恭喜155****6789用户领取58000元额度", "恭喜166****5335用户领取29000元额度", "恭喜163****2299用户领取92000元额度",
            "恭喜130****8866用户领取86000元额度"};

    public List<ProductWeiXxyongHuaModel> productModels = new ArrayList<>();
    private int index = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        initViewData();
        setViewConfig();
        initItemAdapter();
        banner.setBannerGalleryEffect(0, (int) (px2dp(width()) / 5), 15, 0.85f);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        amountWeiXxyongHuaWindow = new AmountWeiXxyongHuaWindow(getActivity());
        amountWeiXxyongHuaWindow.setOnItemClieckListener(new AmountWeiXxyongHuaWindow.OnItemClieckListener() {
            @Override
            public void cliecked(String text) {
                progress_tv.setText(text);
            }
        });
//        main_top_img.setOnClickListener(v -> {
//            productClick(jixinProductModel);
//        });
//        jx_bg.setOnClickListener(v -> {
//            productClick(jixinProductModel);
//        });
//        bgFl.setOnClickListener(v -> {
//            productClick(jixinProductModel);
//        });
//        shenqingSl.setOnClickListener(v -> {
//            productClick(jixinProductModel);
//        });
        moreFl.setOnClickListener(v -> {
            if (getActivity() instanceof MainWeiXxyongHuaActivity) {
                ((MainWeiXxyongHuaActivity) getActivity()).jumpMore();
            }
        });
        jine_ll.setOnClickListener(v -> {
            location = new int[2];
            jine_ll.getLocationOnScreen(location);
            amountWeiXxyongHuaWindow.showPopupWindow(location[0] + jine_ll.getWidth(), location[1] + jine_ll.getHeight());
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

    private void initBannerAdapter(List<ProductWeiXxyongHuaModel> data) {
        imageAdapterWeiXxyongHua = null;
        imageAdapterWeiXxyongHua = new ImageAdapterWeiXxyongHua(data);
        imageAdapterWeiXxyongHua.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapterWeiXxyongHua);
    }

    /**
     * 设置上下切换控件配置
     */
    private void setViewConfig() {
        viewFlipper.setInAnimation(getActivity(), R.anim.jixin_anim_in);
        viewFlipper.setOutAnimation(getActivity(), R.anim.jixin_anim_out);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }

    private void initViewData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < msg.length; i++) {
            datas.add(msg[i]);
        }
        for (String data : datas) {
            View view = getLayoutInflater().inflate(R.layout.item_wxxyh_view_flipper, null);
            TextView textView = view.findViewById(R.id.item_text);
            textView.setText(data);
            viewFlipper.addView(view);
        }
    }

    private void initItemAdapter() {
        List<WeiXxyongHuaItemModel> list = new ArrayList<>();
        WeiXxyongHuaItemModel model = new WeiXxyongHuaItemModel();
        model.setName("3期");
        model.setAmount("5000");
        list.add(model);
        WeiXxyongHuaItemModel model1 = new WeiXxyongHuaItemModel();
        model1.setName("6期");
        model1.setAmount("50000");
        list.add(model1);
        WeiXxyongHuaItemModel model2 = new WeiXxyongHuaItemModel();
        model2.setName("9期");
        model2.setAmount("100000");
        model2.setChecked(true);
        model2.setAomuntChecked(true);
        list.add(model2);
        WeiXxyongHuaItemModel model3 = new WeiXxyongHuaItemModel();
        model3.setName("12期");
        model3.setAmount("150000");
        list.add(model3);
        WeiXxyongHuaItemModel model4 = new WeiXxyongHuaItemModel();
        model4.setName("24期");
        model4.setAmount("200000");
        list.add(model4);
        itemWeiXxyongHuaAdapter = new ItemWeiXxyongHuaAdapter(getActivity());
        itemWeiXxyongHuaAdapter.setHasStableIds(true);
        itemWeiXxyongHuaAdapter.setData(list);
        itemWeiXxyongHuaAdapter.setRecItemClick(new RecyclerItemCallback<WeiXxyongHuaItemModel, ItemWeiXxyongHuaAdapter.ItemViewHolder>() {
            @Override
            public void onItemClick(int position, WeiXxyongHuaItemModel model, int tag, ItemWeiXxyongHuaAdapter.ItemViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                progress_tv.setText(model.getAmount());
            }
        });
        item_list.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        item_list.setHasFixedSize(true);
        item_list.setAdapter(itemWeiXxyongHuaAdapter);
    }

    private ProductWeiXxyongHuaModel getGoodsModel() {
        ProductWeiXxyongHuaModel productModel = null;
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
        return R.layout.wxxyh__fragment_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductWeiXxyongHuaModel model) {
        if (model == null) {
            return;
        }
        phone = PreferencesWeiXxyongHuaOpenUtil.getString("phone");
        productModels.clear();
        WeiXxyongHuaApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseWeiXxyongHuaModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseWeiXxyongHuaModel baseModel) {
                        toWeb(model);
                    }
                });
    }


    public void productList() {
        mobileType = PreferencesWeiXxyongHuaOpenUtil.getInt("mobileType");
        productModels.clear();
        WeiXxyongHuaApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseWeiXxyongHuaModel<List<ProductWeiXxyongHuaModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenWeiXxyongHuaUtil.showErrorInfo(getActivity(), error);
                        if (imageAdapterWeiXxyongHua == null) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(BaseWeiXxyongHuaModel<List<ProductWeiXxyongHuaModel>> baseModel) {
                        setRefreshing.setRefreshing(false);
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productModels.addAll(baseModel.getData());
                                    initBannerAdapter(baseModel.getData());
                                } else {
                                    if (imageAdapterWeiXxyongHua == null) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (imageAdapterWeiXxyongHua == null) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (imageAdapterWeiXxyongHua == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    public void toWeb(ProductWeiXxyongHuaModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenWeiXxyongHuaUtil.getValue((XActivity) getActivity(), JumpH5WeiXxyongHuaActivity.class, bundle);
        }
    }
}
