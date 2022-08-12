package com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.jiekuanzhijiafragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jieuacnisdoert.naweodfigety.R;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaadapter.GoodsItemAdapterJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.JieKuanZhiJiaGoodsModel;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.WebViewActivityJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.mvp.XFragment;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiapresent.JieKuanZhiJiaHomePagePresent;
import com.jieuacnisdoert.naweodfigety.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class JieKuanZhiJiaHomePageFragment extends XFragment<JieKuanZhiJiaHomePagePresent> {

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

    private Bundle webBundle;
    public GoodsItemAdapterJieKuanZhiJia goodsItemAdapterJieKuanZhiJia;
    public JieKuanZhiJiaGoodsModel jieKuanZhiJiaGoodsModel, topJieKuanZhiJiaGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topJieKuanZhiJiaGoodsModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_jie_kuan_zhi_jia;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public JieKuanZhiJiaHomePagePresent newP() {
        return new JieKuanZhiJiaHomePagePresent();
    }

    private void productClick(JieKuanZhiJiaGoodsModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(JieKuanZhiJiaGoodsModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewActivityJieKuanZhiJia.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<JieKuanZhiJiaGoodsModel> mData) {
        if (goodsItemAdapterJieKuanZhiJia == null) {
            goodsItemAdapterJieKuanZhiJia = new GoodsItemAdapterJieKuanZhiJia(getActivity());
            goodsItemAdapterJieKuanZhiJia.setRecItemClick(new RecyclerItemCallback<JieKuanZhiJiaGoodsModel, GoodsItemAdapterJieKuanZhiJia.ViewHolder>() {
                @Override
                public void onItemClick(int position, JieKuanZhiJiaGoodsModel model, int tag, GoodsItemAdapterJieKuanZhiJia.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterJieKuanZhiJia.setHasStableIds(true);
            goodsItemAdapterJieKuanZhiJia.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterJieKuanZhiJia);
        } else {
            goodsItemAdapterJieKuanZhiJia.setData(mData);
        }
    }

    public void setModel(JieKuanZhiJiaGoodsModel jieKuanZhiJiaGoodsModel) {
        this.jieKuanZhiJiaGoodsModel = jieKuanZhiJiaGoodsModel;
    }
}
