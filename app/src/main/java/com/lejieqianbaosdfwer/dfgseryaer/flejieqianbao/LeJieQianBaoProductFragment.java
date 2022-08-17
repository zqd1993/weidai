package com.lejieqianbaosdfwer.dfgseryaer.flejieqianbao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lejieqianbaosdfwer.dfgseryaer.R;
import com.lejieqianbaosdfwer.dfgseryaer.alejieqianbao.JumpH5ActivityLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.alejieqianbao.ProductGoodsItemAdapter;
import com.lejieqianbaosdfwer.dfgseryaer.apilejieqianbao.HttpApiLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.imageloader.ILFactory;
import com.lejieqianbaosdfwer.dfgseryaer.imageloader.ILoader;
import com.lejieqianbaosdfwer.dfgseryaer.mlejieqianbao.BaseModelLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.mlejieqianbao.ProductModelLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.mvp.XActivity;
import com.lejieqianbaosdfwer.dfgseryaer.mvp.XFragment;
import com.lejieqianbaosdfwer.dfgseryaer.net.ApiSubscriber;
import com.lejieqianbaosdfwer.dfgseryaer.net.NetError;
import com.lejieqianbaosdfwer.dfgseryaer.net.XApi;
import com.lejieqianbaosdfwer.dfgseryaer.ulejieqianbao.LeJieQianBaoOpenUtil;
import com.lejieqianbaosdfwer.dfgseryaer.ulejieqianbao.PreferencesOpenUtilLeJieQianBao;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class LeJieQianBaoProductFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.click_fl)
    View click_fl;
    @BindView(R.id.goods_list)
    RecyclerView goods_list;
    @BindView(R.id.main_top_img)
    View main_top_img;
    private ProductModelLeJieQianBao productModelLeJieQianBao;

    private Bundle bundle;

    private ProductGoodsItemAdapter productGoodsItemAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productModelLeJieQianBao);
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelLeJieQianBao);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductModelLeJieQianBao model) {
            if (model != null) {
                phone = PreferencesOpenUtilLeJieQianBao.getString("phone");
                HttpApiLeJieQianBao.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseModelLeJieQianBao>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseModelLeJieQianBao baseModelLeJieQianBao) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = PreferencesOpenUtilLeJieQianBao.getInt("mobileType");
            phone = PreferencesOpenUtilLeJieQianBao.getString("phone");
            productModelLeJieQianBao = null;
            HttpApiLeJieQianBao.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeJieQianBao<List<ProductModelLeJieQianBao>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            LeJieQianBaoOpenUtil.showErrorInfo(getActivity(), error);
                            noDataTv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onNext(BaseModelLeJieQianBao<List<ProductModelLeJieQianBao>> baseModelLeJieQianBao) {
                            setRefreshing.setRefreshing(false);
                            if (baseModelLeJieQianBao != null) {
                                if (baseModelLeJieQianBao.getCode() == 200 && baseModelLeJieQianBao.getData() != null) {
                                    if (baseModelLeJieQianBao.getData() != null && baseModelLeJieQianBao.getData().size() > 0) {
                                        productModelLeJieQianBao = baseModelLeJieQianBao.getData().get(0);
                                        initAdapter(baseModelLeJieQianBao.getData());
                                    } else {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            } else {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    });
    }

    private void initAdapter(List<ProductModelLeJieQianBao> mList){
        if (productGoodsItemAdapter == null){
            productGoodsItemAdapter = new ProductGoodsItemAdapter(getActivity());
            productGoodsItemAdapter.setData(mList);
            productGoodsItemAdapter.setHasStableIds(true);
            productGoodsItemAdapter.setRecItemClick(new RecyclerItemCallback<ProductModelLeJieQianBao, ProductGoodsItemAdapter.ProductGoodsItemHolder>() {
                @Override
                public void onItemClick(int position, ProductModelLeJieQianBao model, int tag, ProductGoodsItemAdapter.ProductGoodsItemHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goods_list.setHasFixedSize(true);
            goods_list.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            goods_list.setAdapter(productGoodsItemAdapter);
        } else {
            productGoodsItemAdapter.setData(mList);
        }
    }

    private void addProductView(List<ProductModelLeJieQianBao> mList) {
        for (ProductModelLeJieQianBao model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_le_jie_qian_bao_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            ILFactory.getLoader().loadNet(pic, HttpApiLeJieQianBao.HTTP_API_URL + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            shijian_tv.setText(model.getDes());
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            parentFl.setOnClickListener(v -> {
                productClick(model);
            });
            pic.setOnClickListener(v -> {
                productClick(model);
            });
            yjsqSl.setOnClickListener(v -> {
                productClick(model);
            });
        }
    }

    public void toWeb(ProductModelLeJieQianBao model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            LeJieQianBaoOpenUtil.jumpPage((XActivity) getActivity(), JumpH5ActivityLeJieQianBao.class, bundle);
        }
    }
}
