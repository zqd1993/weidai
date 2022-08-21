package com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinui.zhouzhuanzijinfragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zhouzhuanzijianrdgfg.haerawyry.R;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinui.ZhouZhuanZiJinWebViewActivity;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinadapter.ZhouZhuanZiJinGoodsItemAdapter;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinmodel.ZhouZhuanZiJinGoodsModel;
import com.zhouzhuanzijianrdgfg.haerawyry.mvp.XFragment;
import com.zhouzhuanzijianrdgfg.haerawyry.zhouzhuanzijinpresent.ZhouZhuanZiJinProductPresent;
import com.zhouzhuanzijianrdgfg.haerawyry.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ZhouZhuanZiJinProductFragment extends XFragment<ZhouZhuanZiJinProductPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public ZhouZhuanZiJinGoodsItemAdapter zhouZhuanZiJinGoodsItemAdapter;
    private ZhouZhuanZiJinGoodsModel zhouZhuanZiJinGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setOnRefreshListener(() -> getP().aindex());
//        parentLl.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_zhou_zhuan_zi_jin;
    }

    @Override
    public ZhouZhuanZiJinProductPresent newP() {
        return new ZhouZhuanZiJinProductPresent();
    }

    @Override
    public void onResume() {
        super.onResume();
        getP().aindex();
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
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
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
