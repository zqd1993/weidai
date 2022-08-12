package com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.jiekuanzhijiafragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jieuacnisdoert.naweodfigety.R;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaadapter.GoodsItemAdapterJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.JieKuanZhiJiaGoodsModel;
import com.jieuacnisdoert.naweodfigety.mvp.XFragment;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiapresent.JieKuanZhiJiaProductPresent;
import com.jieuacnisdoert.naweodfigety.router.Router;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.WebViewActivityJieKuanZhiJia;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class JieKuanZhiJiaProductFragment extends XFragment<JieKuanZhiJiaProductPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapterJieKuanZhiJia goodsItemAdapterJieKuanZhiJia;
    private JieKuanZhiJiaGoodsModel jieKuanZhiJiaGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_jie_kuan_zhi_jia;
    }

    @Override
    public JieKuanZhiJiaProductPresent newP() {
        return new JieKuanZhiJiaProductPresent();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
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
