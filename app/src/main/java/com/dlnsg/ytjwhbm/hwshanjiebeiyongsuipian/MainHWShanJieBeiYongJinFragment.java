package com.dlnsg.ytjwhbm.hwshanjiebeiyongsuipian;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dlnsg.ytjwhbm.R;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongkongjian.AmountHWShanJieBeiYongJinWindow;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongyemian.ItemHWShanJieBeiYongJinAdapter;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongyemian.MainHWShanJieBeiYongJinActivity;
import com.dlnsg.ytjwhbm.mvp.XActivity;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongshiti.HWShanJieBeiYongJinItemModel;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongyemian.ImageAdapterHWShanJieBeiYongJin;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongyemian.JumpH5HWShanJieBeiYongJinActivity;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongjiekou.HWShanJieBeiYongJinApi;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongshiti.BaseHWShanJieBeiYongJinModel;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongshiti.ProductHWShanJieBeiYongJinModel;
import com.dlnsg.ytjwhbm.mvp.XFragment;
import com.dlnsg.ytjwhbm.net.ApiSubscriber;
import com.dlnsg.ytjwhbm.net.NetError;
import com.dlnsg.ytjwhbm.net.XApi;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongjingongju.OpenHWShanJieBeiYongJinUtil;
import com.dlnsg.ytjwhbm.hwshanjiebeiyongjingongju.PreferenceHWShanJieBeiYongJinOpenUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MainHWShanJieBeiYongJinFragment extends XFragment {

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

    private ImageAdapterHWShanJieBeiYongJin imageAdapterHWShanJieBeiYongJin;
    private ItemHWShanJieBeiYongJinAdapter itemHWShanJieBeiYongJinAdapter;
    private AmountHWShanJieBeiYongJinWindow amountHWShanJieBeiYongJinWindow;
    private int[] location;

    public List<ProductHWShanJieBeiYongJinModel> productModels = new ArrayList<>();
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
        amountHWShanJieBeiYongJinWindow = new AmountHWShanJieBeiYongJinWindow(getActivity());
        amountHWShanJieBeiYongJinWindow.setOnItemClieckListener(new AmountHWShanJieBeiYongJinWindow.OnItemClieckListener() {
            @Override
            public void cliecked(String text) {
                progress_tv.setText(text);
            }
        });
        moreFl.setOnClickListener(v -> {
            if (getActivity() instanceof MainHWShanJieBeiYongJinActivity) {
                ((MainHWShanJieBeiYongJinActivity) getActivity()).jumpMore();
            }
        });
        jine_ll.setOnClickListener(v -> {
            location = new int[2];
            jine_ll.getLocationOnScreen(location);
            amountHWShanJieBeiYongJinWindow.showPopupWindow(location[0] + jine_ll.getWidth(), location[1] + jine_ll.getHeight());
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

    private void initBannerAdapter(List<ProductHWShanJieBeiYongJinModel> data) {
        imageAdapterHWShanJieBeiYongJin = null;
        imageAdapterHWShanJieBeiYongJin = new ImageAdapterHWShanJieBeiYongJin(data);
        imageAdapterHWShanJieBeiYongJin.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapterHWShanJieBeiYongJin);
    }

    private void initItemAdapter() {
        List<HWShanJieBeiYongJinItemModel> list = new ArrayList<>();
        HWShanJieBeiYongJinItemModel model = new HWShanJieBeiYongJinItemModel();
        model.setName("3期");
        model.setAmount("5000");
        list.add(model);
        HWShanJieBeiYongJinItemModel model1 = new HWShanJieBeiYongJinItemModel();
        model1.setName("6期");
        model1.setAmount("50000");
        list.add(model1);
        HWShanJieBeiYongJinItemModel model2 = new HWShanJieBeiYongJinItemModel();
        model2.setName("9期");
        model2.setAmount("100000");
        model2.setChecked(true);
        model2.setAomuntChecked(true);
        list.add(model2);
        HWShanJieBeiYongJinItemModel model3 = new HWShanJieBeiYongJinItemModel();
        model3.setName("12期");
        model3.setAmount("150000");
        list.add(model3);
        HWShanJieBeiYongJinItemModel model4 = new HWShanJieBeiYongJinItemModel();
        model4.setName("24期");
        model4.setAmount("200000");
        list.add(model4);
        itemHWShanJieBeiYongJinAdapter = new ItemHWShanJieBeiYongJinAdapter(getActivity());
        itemHWShanJieBeiYongJinAdapter.setHasStableIds(true);
        itemHWShanJieBeiYongJinAdapter.setData(list);
        itemHWShanJieBeiYongJinAdapter.setRecItemClick(new RecyclerItemCallback<HWShanJieBeiYongJinItemModel, ItemHWShanJieBeiYongJinAdapter.ItemViewHolder>() {
            @Override
            public void onItemClick(int position, HWShanJieBeiYongJinItemModel model, int tag, ItemHWShanJieBeiYongJinAdapter.ItemViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                progress_tv.setText(model.getAmount());
            }
        });
        item_list.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        item_list.setHasFixedSize(true);
        item_list.setAdapter(itemHWShanJieBeiYongJinAdapter);
    }

    private ProductHWShanJieBeiYongJinModel getGoodsModel() {
        ProductHWShanJieBeiYongJinModel productModel = null;
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
        return R.layout.hw_shan_jie_bei_yong_jie_fragment_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductHWShanJieBeiYongJinModel model) {
        if (model == null) {
            return;
        }
        phone = PreferenceHWShanJieBeiYongJinOpenUtil.getString("phone");
        productModels.clear();
        HWShanJieBeiYongJinApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHWShanJieBeiYongJinModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseHWShanJieBeiYongJinModel baseModel) {
                        toWeb(model);
                    }
                });
    }


    public void productList() {
        mobileType = PreferenceHWShanJieBeiYongJinOpenUtil.getInt("mobileType");
        productModels.clear();
        HWShanJieBeiYongJinApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHWShanJieBeiYongJinModel<List<ProductHWShanJieBeiYongJinModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenHWShanJieBeiYongJinUtil.showErrorInfo(getActivity(), error);
                        if (imageAdapterHWShanJieBeiYongJin == null) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(BaseHWShanJieBeiYongJinModel<List<ProductHWShanJieBeiYongJinModel>> baseModel) {
                        setRefreshing.setRefreshing(false);
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productModels.addAll(baseModel.getData());
                                    initBannerAdapter(baseModel.getData());
                                } else {
                                    if (imageAdapterHWShanJieBeiYongJin == null) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (imageAdapterHWShanJieBeiYongJin == null) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (imageAdapterHWShanJieBeiYongJin == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    public void toWeb(ProductHWShanJieBeiYongJinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenHWShanJieBeiYongJinUtil.getValue((XActivity) getActivity(), JumpH5HWShanJieBeiYongJinActivity.class, bundle);
        }
    }
}
