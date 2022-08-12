package com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.jiujijiedaifragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jiejijiekuansdafjer.zdfgbaeryazer.R;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiadapter.GoodsItemAdapterJiuJiJieDai;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaimodel.JiuJiJieDaiGoodsModel;
import com.jiejijiekuansdafjer.zdfgbaeryazer.mvp.XFragment;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaipresent.JiuJiJieDaiProductPresent;
import com.jiejijiekuansdafjer.zdfgbaeryazer.router.Router;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.JiuJiJieDaiWebViewActivity;

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
    @BindView(R.id.money_num_tv)
    public TextView money_num_tv;

    private Bundle webBundle;
    public GoodsItemAdapterJiuJiJieDai goodsItemAdapterJiuJiJieDai;
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
            goodsItemAdapterJiuJiJieDai = new GoodsItemAdapterJiuJiJieDai(getActivity());
            goodsItemAdapterJiuJiJieDai.setRecItemClick(new RecyclerItemCallback<JiuJiJieDaiGoodsModel, GoodsItemAdapterJiuJiJieDai.ViewHolder>() {
                @Override
                public void onItemClick(int position, JiuJiJieDaiGoodsModel model, int tag, GoodsItemAdapterJiuJiJieDai.ViewHolder holder) {
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
