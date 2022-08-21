package com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.fragmentjiujijiedai;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jiujjidaikuansdafjer.fgbnsrtyeasy.R;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.adapterjiujijiedai.GoodsItemAdapterJiuJiJieDai;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiGoodsModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.JiuJiJieDaiHomePageActivity;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.JiuJiJieDaiWebViewActivity;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.mvp.XFragment;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.presentjiujijiedai.JiuJiJieDaiHomePagePresent;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class JiuJiJieDaiHomePageFragment extends XFragment<JiuJiJieDaiHomePagePresent> {

    @BindView(R.id.product_bg)
    View productBg;
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.top_layout)
    View topLayout;
    @BindView(R.id.money_num_tv)
    public TextView money_num_tv;
    @BindView(R.id.click_view_1)
    View click_view_1;
    @BindView(R.id.info_tv)
    public TextView info_tv;
    @BindView(R.id.top_img)
    public ImageView topImg;
    @BindView(R.id.more_tv)
    View more_tv;

    private Bundle webBundle;
    public GoodsItemAdapterJiuJiJieDai goodsItemAdapterJiuJiJieDai;
    public JiuJiJieDaiGoodsModel jiuJiJieDaiGoodsModel, topJiuJiJieDaiGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topJiuJiJieDaiGoodsModel);
        });
        more_tv.setOnClickListener(v -> {
            ((JiuJiJieDaiHomePageActivity)getActivity()).changPage();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_jiu_ji_jie_dai;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public JiuJiJieDaiHomePagePresent newP() {
        return new JiuJiJieDaiHomePagePresent();
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
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
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
