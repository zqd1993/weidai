package com.gdzfgesry.nbfgnwaet.uiqianbao.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gdzfgesry.nbfgnwaet.R;
import com.gdzfgesry.nbfgnwaet.adapterqianbao.GoodsItemAdapterQianBao;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.GoodsQianBaoModel;
import com.gdzfgesry.nbfgnwaet.mvp.XFragment;
import com.gdzfgesry.nbfgnwaet.qianbaopresent.ProductQianBaoPresent;
import com.gdzfgesry.nbfgnwaet.router.Router;
import com.gdzfgesry.nbfgnwaet.uiqianbao.QianBaoWebViewActivity;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductQianBaoFragment extends XFragment<ProductQianBaoPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

    private Bundle webBundle;
    public GoodsItemAdapterQianBao goodsItemAdapterQianBao;
    private GoodsQianBaoModel goodsQianBaoModel;

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().productList();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        parentLl.setOnClickListener(v -> {
            productClick(goodsQianBaoModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_qian_bao_product;
    }

    public static String fstdfhxfh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double bbzedrtzjh(Object o) {

        return toDouble(o, 0);
    }

    public static double jhzerh(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long ggertyxfj(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public ProductQianBaoPresent newP() {
        return new ProductQianBaoPresent();
    }

    private void productClick(GoodsQianBaoModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsQianBaoModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(QianBaoWebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public static String wetgdfuy(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double jjrtzdhj(Object o) {

        return toDouble(o, 0);
    }

    public static double xbxdftyh(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long rthxfhxn(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    public void initGoodsItemAdapter(List<GoodsQianBaoModel> mData) {
        if (goodsItemAdapterQianBao == null) {
            goodsItemAdapterQianBao = new GoodsItemAdapterQianBao(getActivity());
            goodsItemAdapterQianBao.setRecItemClick(new RecyclerItemCallback<GoodsQianBaoModel, GoodsItemAdapterQianBao.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsQianBaoModel model, int tag, GoodsItemAdapterQianBao.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterQianBao.setHasStableIds(true);
            goodsItemAdapterQianBao.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterQianBao);
        } else {
            goodsItemAdapterQianBao.setData(mData);
        }
    }

    public static String jdtyuihxh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double ggzserthxdfh(Object o) {

        return toDouble(o, 0);
    }

    public static double retydhfxgh(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long bsrthzdgh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    public void setModel(GoodsQianBaoModel goodsQianBaoModel) {
        this.goodsQianBaoModel = goodsQianBaoModel;
    }
}
