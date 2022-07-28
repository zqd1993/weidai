package com.wolai.dai.suipian;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wolai.dai.R;
import com.wolai.dai.mvp.XActivity;
import com.wolai.dai.shiti.ItemModel;
import com.wolai.dai.yemian.ImageAdapter;
import com.wolai.dai.yemian.ItemAdapter;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

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
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.progress_tv)
    TextView progress_tv;
    @BindView(R.id.item_list)
    RecyclerView item_list;
    @BindView(R.id.jine_ll)
    View jine_ll;
    @BindView(R.id.xiala_ll)
    View xiala_ll;
    @BindView(R.id.jine_tv_1)
    TextView jine_tv_1;
    @BindView(R.id.jine_tv_2)
    TextView jine_tv_2;
    @BindView(R.id.jine_tv_3)
    TextView jine_tv_3;
    @BindView(R.id.jine_tv_4)
    TextView jine_tv_4;
    @BindView(R.id.jine_tv_5)
    TextView jine_tv_5;
    @BindView(R.id.root_ll)
    View root_ll;

    private JixinProductModel jixinProductModel;

    private Bundle bundle;

    private ImageAdapter imageAdapter;
    private ItemAdapter itemAdapter;

    private String[] msg = {"恭喜187****5758用户领取87000元额度", "恭喜138****5666用户领取36000元额度", "恭喜199****5009用户领取49000元额度",
            "恭喜137****6699用户领取69000元额度", "恭喜131****8889用户领取18000元额度", "恭喜177****8899用户领取26000元额度",
            "恭喜155****6789用户领取58000元额度", "恭喜166****5335用户领取29000元额度", "恭喜163****2299用户领取92000元额度",
            "恭喜130****8866用户领取86000元额度"};

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
        jine_ll.setOnClickListener(v -> {
            xiala_ll.setVisibility(View.VISIBLE);
        });
        root_ll.setOnClickListener(v -> {
            xiala_ll.setVisibility(View.GONE);
        });
        jine_tv_1.setOnClickListener(v -> {
            progress_tv.setText(jine_tv_1.getText().toString());
            xiala_ll.setVisibility(View.GONE);
        });
        jine_tv_2.setOnClickListener(v -> {
            progress_tv.setText(jine_tv_2.getText().toString());
            xiala_ll.setVisibility(View.GONE);
        });
        jine_tv_3.setOnClickListener(v -> {
            progress_tv.setText(jine_tv_3.getText().toString());
            xiala_ll.setVisibility(View.GONE);
        });
        jine_tv_4.setOnClickListener(v -> {
            progress_tv.setText(jine_tv_4.getText().toString());
            xiala_ll.setVisibility(View.GONE);
        });
        jine_tv_5.setOnClickListener(v -> {
            progress_tv.setText(jine_tv_5.getText().toString());
            xiala_ll.setVisibility(View.GONE);
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
            View view = getLayoutInflater().inflate(R.layout.item_view_flipper, null);
            TextView textView = view.findViewById(R.id.item_text);
            textView.setText(data);
            viewFlipper.addView(view);
        }
    }

    private void initItemAdapter() {
        List<ItemModel> list = new ArrayList<>();
        ItemModel model = new ItemModel();
        model.setName("3期");
        model.setAmount("5000");
        list.add(model);
        ItemModel model1 = new ItemModel();
        model1.setName("6期");
        model1.setAmount("50000");
        list.add(model1);
        ItemModel model2 = new ItemModel();
        model2.setName("9期");
        model2.setAmount("100000");
        model2.setChecked(true);
        model2.setAomuntChecked(true);
        list.add(model2);
        ItemModel model3 = new ItemModel();
        model3.setName("12期");
        model3.setAmount("150000");
        list.add(model3);
        ItemModel model4 = new ItemModel();
        model4.setName("24期");
        model4.setAmount("200000");
        list.add(model4);
        itemAdapter = new ItemAdapter(getActivity());
        itemAdapter.setHasStableIds(true);
        itemAdapter.setData(list);
        itemAdapter.setRecItemClick(new RecyclerItemCallback<ItemModel, ItemAdapter.ItemViewHolder>() {
            @Override
            public void onItemClick(int position, ItemModel model, int tag, ItemAdapter.ItemViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                progress_tv.setText(model.getAmount());
            }
        });
        item_list.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        item_list.setHasFixedSize(true);
        item_list.setAdapter(itemAdapter);
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


    public void productList() {
        mobileType = JiXinPreferencesOpenUtil.getInt("mobileType");
        jixinProductModel = null;
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

    public void toWeb(JixinProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            JiXinOpenUtil.getValue((XActivity) getActivity(), JixinJumpH5Activity.class, bundle);
        }
    }
}
