package com.rihdkauecgh.plihgnytrvfws.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.adapter.GoodsItemAdapter;
import com.rihdkauecgh.plihgnytrvfws.adapter.ItemAdapter;
import com.rihdkauecgh.plihgnytrvfws.model.GoodsModel;
import com.rihdkauecgh.plihgnytrvfws.model.ItemModel;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.WebViewActivity;
import com.rihdkauecgh.plihgnytrvfws.mvp.XFragment;
import com.rihdkauecgh.plihgnytrvfws.present.HomePagePresent;
import com.rihdkauecgh.plihgnytrvfws.router.Router;
import com.rihdkauecgh.plihgnytrvfws.utils.StaticUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragment extends XFragment<HomePagePresent> {

    @BindView(R.id.product_bg)
    View productBg;
    @BindView(R.id.home_page_bg)
    View homePageBg;
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data_fl)
    public View noDataFl;
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.top_layout)
    View topLayout;
    @BindView(R.id.banner_fl)
    View banner_fl;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.pb_progressbar)
    SeekBar pb_progressbar;
    @BindView(R.id.progress_tv)
    TextView progress_tv;
    @BindView(R.id.item_list)
    RecyclerView item_list;
    @BindView(R.id.click_view)
    View click_view;
    @BindView(R.id.shenqing_tv)
    View shenqing_tv;

    private Bundle bundle, webBundle;
    private int tag, index = 0;
    public GoodsItemAdapter goodsItemAdapter;
    public List<GoodsModel> goodsModels;
    private ItemAdapter itemAdapter;

    private String[] msg = {"恭喜187****5758用户领取87000元额度", "恭喜138****5666用户领取36000元额度", "恭喜199****5009用户领取49000元额度",
            "恭喜137****6699用户领取69000元额度", "恭喜131****8889用户领取18000元额度", "恭喜177****8899用户领取26000元额度",
            "恭喜155****6789用户领取58000元额度", "恭喜166****5335用户领取29000元额度", "恭喜163****2299用户领取92000元额度",
            "恭喜130****8866用户领取86000元额度"};

    public static HomePageFragment getInstant(int tag) {
        HomePageFragment homePageFragment = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        homePageFragment.setArguments(bundle);
        return homePageFragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getInt("tag");
        }
        initViewData();
        setViewConfig();
        initItemAdapter();
        goodsModels = new ArrayList<>();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        noDataFl.setOnClickListener(v -> getP().productList());
        click_view.setOnClickListener(v -> {
            productClick(getGoodsModel());
        });
        shenqing_tv.setOnClickListener(v -> {
            productClick(getGoodsModel());
        });
        pb_progressbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if ((2000 * progress) <= 5000) {
                    progress_tv.setText("5000");
                } else {
                    progress_tv.setText(String.valueOf(2000 * progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().productList();
    }

    private void initItemAdapter() {
        List<ItemModel> list = new ArrayList<>();
        ItemModel model = new ItemModel();
        model.setName("3期");
        list.add(model);
        ItemModel model1 = new ItemModel();
        model1.setName("6期");
        list.add(model1);
        ItemModel model2 = new ItemModel();
        model2.setName("9期");
        list.add(model2);
        ItemModel model3 = new ItemModel();
        model3.setName("12期");
        list.add(model3);
        ItemModel model4 = new ItemModel();
        model4.setName("24期");
        list.add(model4);
        itemAdapter = new ItemAdapter(getActivity());
        itemAdapter.setHasStableIds(true);
        itemAdapter.setData(list);
        item_list.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        item_list.setHasFixedSize(true);
        item_list.setAdapter(itemAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public HomePagePresent newP() {
        return new HomePagePresent();
    }

    private void productClick(GoodsModel model) {
        if (model != null) {
            getP().productClick(model);
        }
    }

    public void jumpWebActivity(GoodsModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            StaticUtil.getValue((XActivity) getActivity(), WebViewActivity.class, webBundle);
        }
    }

    private GoodsModel getGoodsModel() {
        GoodsModel goodsModel = null;
        if (goodsModels.size() <= index) {
            index = 0;
        }
        if (goodsModels != null && goodsModels.size() > index) {
            goodsModel = goodsModels.get(index);
            if (index < goodsModels.size() - 1) {
                index = index + 1;
            } else {
                index = 0;
            }
        }
        return goodsModel;
    }

    /**
     * 设置上下切换控件配置
     */
    private void setViewConfig() {
        viewFlipper.setInAnimation(getActivity(), R.anim.anim_in);
        viewFlipper.setOutAnimation(getActivity(), R.anim.anim_out);
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

    public void initGoodsItemAdapter(List<GoodsModel> mData) {
        if (goodsItemAdapter == null) {
            goodsItemAdapter = new GoodsItemAdapter(getActivity());
            goodsItemAdapter.setRecItemClick(new RecyclerItemCallback<GoodsModel, GoodsItemAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsModel model, int tag, GoodsItemAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapter.setHasStableIds(true);
            goodsItemAdapter.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapter);
        } else {
            goodsItemAdapter.setData(mData);
        }
    }

    public void setModel(List<GoodsModel> goodsModels) {
        this.goodsModels = goodsModels;
    }
}
