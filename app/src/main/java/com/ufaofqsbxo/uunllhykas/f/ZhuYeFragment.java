package com.ufaofqsbxo.uunllhykas.f;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ufaofqsbxo.uunllhykas.R;
import com.ufaofqsbxo.uunllhykas.a.GoodsAdapter;
import com.ufaofqsbxo.uunllhykas.a.WangYeActivity;
import com.ufaofqsbxo.uunllhykas.api.MyApi;
import com.ufaofqsbxo.uunllhykas.m.MainModel;
import com.ufaofqsbxo.uunllhykas.m.ShangPinModel;
import com.ufaofqsbxo.uunllhykas.mvp.XFragment;
import com.ufaofqsbxo.uunllhykas.net.ApiSubscriber;
import com.ufaofqsbxo.uunllhykas.net.NetError;
import com.ufaofqsbxo.uunllhykas.net.XApi;
import com.ufaofqsbxo.uunllhykas.u.BaseUtil;
import com.ufaofqsbxo.uunllhykas.u.PreferencesStaticOpenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ZhuYeFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View mainTopImg;
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.click_view)
    View clickView;

    private ShangPinModel shangPinModel;

    private Bundle bundle;

    private GoodsAdapter mGoodsAdapter;

    private String[] msg = {"恭喜187****5758用户领取87000元额度", "恭喜138****5666用户领取36000元额度", "恭喜199****5009用户领取49000元额度",
            "恭喜137****6699用户领取69000元额度", "恭喜131****8889用户领取18000元额度", "恭喜177****8899用户领取26000元额度",
            "恭喜155****6789用户领取58000元额度", "恭喜166****5335用户领取29000元额度", "恭喜163****2299用户领取92000元额度",
            "恭喜130****8866用户领取86000元额度"};

    @Override
    public void initData(Bundle savedInstanceState) {
        productList();
        setRefreshing.setOnRefreshListener(() -> productList());
        mainTopImg.setOnClickListener(v -> {
            productClick(shangPinModel);
        });
        clickView.setOnClickListener(v -> {
            productClick(shangPinModel);
        });
        initViewData();
        setViewConfig();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhuyao;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ShangPinModel model) {
        if (model == null) {
            return;
        }
        phone = PreferencesStaticOpenUtil.getString("phone");
        MyApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<MainModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(MainModel mainModel) {
                        toWeb(model);
                    }
                });
    }


    public void productList() {
        mobileType = PreferencesStaticOpenUtil.getInt("mobileType");
        MyApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<MainModel<List<ShangPinModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        BaseUtil.showErrorInfo(getActivity(), error);
                        if (mGoodsAdapter == null) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(MainModel<List<ShangPinModel>> mainModel) {
                        setRefreshing.setRefreshing(false);
                        if (mainModel != null) {
                            if (mainModel.getCode() == 200 && mainModel.getData() != null) {
                                if (mainModel.getData() != null && mainModel.getData().size() > 0) {
                                    shangPinModel = mainModel.getData().get(0);
                                    initAdapter(mainModel.getData());
                                } else {
                                    if (mGoodsAdapter == null) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (mGoodsAdapter == null) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (mGoodsAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    private void initAdapter(List<ShangPinModel> mList) {
        if (mGoodsAdapter == null) {
            mGoodsAdapter = new GoodsAdapter(getActivity());
            mGoodsAdapter.setHasStableIds(true);
            mGoodsAdapter.setRecItemClick(new RecyclerItemCallback<ShangPinModel, GoodsAdapter.GoodsHolder>() {
                @Override
                public void onItemClick(int position, ShangPinModel model, int tag, GoodsAdapter.GoodsHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            rvy.setHasFixedSize(true);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setAdapter(mGoodsAdapter);
        }
        mGoodsAdapter.setData(mList);
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

    public void toWeb(ShangPinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            BaseUtil.jumpPage(getActivity(), WangYeActivity.class, bundle);
        }
    }
}
