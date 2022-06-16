package com.dlnsg.ytjwhbm.f;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dlnsg.ytjwhbm.R;
import com.dlnsg.ytjwhbm.a.GoodsAdapter;
import com.dlnsg.ytjwhbm.a.WangYeActivity;
import com.dlnsg.ytjwhbm.api.MyApi;
import com.dlnsg.ytjwhbm.m.MainModel;
import com.dlnsg.ytjwhbm.m.ShangPinModel;
import com.dlnsg.ytjwhbm.mvp.XFragment;
import com.dlnsg.ytjwhbm.net.ApiSubscriber;
import com.dlnsg.ytjwhbm.net.NetError;
import com.dlnsg.ytjwhbm.net.XApi;
import com.dlnsg.ytjwhbm.u.BaseUtil;
import com.dlnsg.ytjwhbm.u.PreferencesStaticOpenUtil;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ListFragment extends XFragment {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.click_view)
    View clickView;

    private int mobileType;

    private String phone;

    private ShangPinModel shangPinModel;

    private Bundle bundle;

    private GoodsAdapter mGoodsAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        getGoodsList();
        setRefreshing.setOnRefreshListener(() -> getGoodsList());
        clickView.setOnClickListener(v -> {
            goodsClick(shangPinModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void goodsClick(ShangPinModel model) {
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
                        toH5(model);
                    }

                    @Override
                    public void onNext(MainModel mainModel) {
                        toH5(model);
                    }
                });
    }


    public void getGoodsList() {
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
                    goodsClick(model);
                }
            });
            rvy.setHasFixedSize(true);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setAdapter(mGoodsAdapter);
        }
        mGoodsAdapter.setData(mList);
    }

    public void toH5(ShangPinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            BaseUtil.jumpPage(getActivity(), WangYeActivity.class, bundle);
        }
    }
}
