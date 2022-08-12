package com.nfsthjsrtuae.fghserytuxfh.ui.zhouzhuanzijinfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinadapter.ZhouZhuanZiJinGoodsItemAdapter;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinGoodsModel;
import com.nfsthjsrtuae.fghserytuxfh.ui.ZhouZhuanZiJinWebViewActivity;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XFragment;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinpresent.ZhouZhuanZiJinHomePagePresent;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ZhouZhuanZiJinHomePageFragment extends XFragment<ZhouZhuanZiJinHomePagePresent> {

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

    private Bundle webBundle;
    public ZhouZhuanZiJinGoodsItemAdapter zhouZhuanZiJinGoodsItemAdapter;
    public ZhouZhuanZiJinGoodsModel zhouZhuanZiJinGoodsModel, topZhouZhuanZiJinGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topZhouZhuanZiJinGoodsModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_zhou_zhuan_zi_jin;
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
    }

    @Override
    public ZhouZhuanZiJinHomePagePresent newP() {
        return new ZhouZhuanZiJinHomePagePresent();
    }

    private void productClick(ZhouZhuanZiJinGoodsModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(ZhouZhuanZiJinGoodsModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(ZhouZhuanZiJinWebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<ZhouZhuanZiJinGoodsModel> mData) {
        if (zhouZhuanZiJinGoodsItemAdapter == null) {
            zhouZhuanZiJinGoodsItemAdapter = new ZhouZhuanZiJinGoodsItemAdapter(getActivity());
            zhouZhuanZiJinGoodsItemAdapter.setRecItemClick(new RecyclerItemCallback<ZhouZhuanZiJinGoodsModel, ZhouZhuanZiJinGoodsItemAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, ZhouZhuanZiJinGoodsModel model, int tag, ZhouZhuanZiJinGoodsItemAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            zhouZhuanZiJinGoodsItemAdapter.setHasStableIds(true);
            zhouZhuanZiJinGoodsItemAdapter.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(zhouZhuanZiJinGoodsItemAdapter);
        } else {
            zhouZhuanZiJinGoodsItemAdapter.setData(mData);
        }
    }

    public void setModel(ZhouZhuanZiJinGoodsModel zhouZhuanZiJinGoodsModel) {
        this.zhouZhuanZiJinGoodsModel = zhouZhuanZiJinGoodsModel;
    }
}
