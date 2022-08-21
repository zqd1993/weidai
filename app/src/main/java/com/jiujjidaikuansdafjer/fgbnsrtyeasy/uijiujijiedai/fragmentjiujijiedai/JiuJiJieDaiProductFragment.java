package com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.fragmentjiujijiedai;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jiujjidaikuansdafjer.fgbnsrtyeasy.R;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.adapterjiujijiedai.GoodsItemAdapterJiuJiJieDai1;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiGoodsModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.mvp.XFragment;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.presentjiujijiedai.JiuJiJieDaiProductPresent;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.router.Router;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.JiuJiJieDaiWebViewActivity;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class JiuJiJieDaiProductFragment extends XFragment<JiuJiJieDaiProductPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapterJiuJiJieDai1 goodsItemAdapterJiuJiJieDai;
    private JiuJiJieDaiGoodsModel jiuJiJieDaiGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_jiu_ji_jie_dai;
    }

    @Override
    public JiuJiJieDaiProductPresent newP() {
        return new JiuJiJieDaiProductPresent();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    private void productClick(JiuJiJieDaiGoodsModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(JiuJiJieDaiGoodsModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(JiuJiJieDaiWebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<JiuJiJieDaiGoodsModel> mData) {
        if (goodsItemAdapterJiuJiJieDai == null) {
            goodsItemAdapterJiuJiJieDai = new GoodsItemAdapterJiuJiJieDai1(getActivity());
            goodsItemAdapterJiuJiJieDai.setRecItemClick(new RecyclerItemCallback<JiuJiJieDaiGoodsModel, GoodsItemAdapterJiuJiJieDai1.ViewHolder>() {
                @Override
                public void onItemClick(int position, JiuJiJieDaiGoodsModel model, int tag, GoodsItemAdapterJiuJiJieDai1.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterJiuJiJieDai.setHasStableIds(true);
            goodsItemAdapterJiuJiJieDai.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterJiuJiJieDai);
        } else {
            goodsItemAdapterJiuJiJieDai.setData(mData);
        }
    }

    public void setModel(JiuJiJieDaiGoodsModel jiuJiJieDaiGoodsModel) {
        this.jiuJiJieDaiGoodsModel = jiuJiJieDaiGoodsModel;
    }
}
