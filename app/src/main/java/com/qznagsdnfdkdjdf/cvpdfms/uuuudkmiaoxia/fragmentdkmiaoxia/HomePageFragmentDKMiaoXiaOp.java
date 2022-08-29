package com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.fragmentdkmiaoxia;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qznagsdnfdkdjdf.cvpdfms.R;
import com.qznagsdnfdkdjdf.cvpdfms.aaaadkmiaoxia.GoodsItemAdapterDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.DKMiaoXiaOpGoodsModel;
import com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.DKMiaoXiaOpWebViewActivity;
import com.qznagsdnfdkdjdf.cvpdfms.mvp.XFragment;
import com.qznagsdnfdkdjdf.cvpdfms.ppppdkmiaoxia.HomePagePresentDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragmentDKMiaoXiaOp extends XFragment<HomePagePresentDKMiaoXiaOp> {

    @BindView(R.id.product_bg)
    View productBg;
    @BindView(R.id.home_page_bg)
    View homePageBg;
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data_fl)
    public View noDataFl;
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.top_layout)
    View topLayout;
    @BindView(R.id.show_ll)
    View showLL;
    @BindView(R.id.show_ll_2)
    View showLL2;

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

    private Bundle bundle, webBundle;
    private int tag;
    public GoodsItemAdapterDKMiaoXiaOp goodsItemAdapterDKMiaoXiaOp;
    private DKMiaoXiaOpGoodsModel DKMiaoXiaOpGoodsModel;

    public static HomePageFragmentDKMiaoXiaOp getInstant(int tag) {
        HomePageFragmentDKMiaoXiaOp homePageFragmentDKMiaoXiaOp = new HomePageFragmentDKMiaoXiaOp();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        homePageFragmentDKMiaoXiaOp.setArguments(bundle);
        return homePageFragmentDKMiaoXiaOp;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getInt("tag");
            if (tag == 1) {
                productBg.setVisibility(View.GONE);
                homePageBg.setVisibility(View.VISIBLE);
                showLL.setVisibility(View.GONE);
                showLL2.setVisibility(View.VISIBLE);
            } else {
                productBg.setVisibility(View.VISIBLE);
                homePageBg.setVisibility(View.GONE);
                showLL.setVisibility(View.GONE);
                showLL2.setVisibility(View.GONE);
            }
        }
        getP().productList();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        noDataFl.setOnClickListener(v -> getP().productList());
        productBg.setOnClickListener(v -> {
            productClick(DKMiaoXiaOpGoodsModel);
        });
        parentFl.setOnClickListener(v -> {
            productClick(DKMiaoXiaOpGoodsModel);
        });
        topLayout.setOnClickListener(v -> {
            productClick(DKMiaoXiaOpGoodsModel);
        });
    }

    public static String werdgz(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double mcgnxfh(Object o) {

        return toDouble(o, 0);
    }

    public static double jtyugj(Object o, int defaultValue) {
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

    public static long vdfdssf(Object o, long defaultValue) {
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
    public int getLayoutId() {
        return R.layout.fragment_home_page_dk_miao_xia_op_new;
    }

    @Override
    public HomePagePresentDKMiaoXiaOp newP() {
        return new HomePagePresentDKMiaoXiaOp();
    }

    private void productClick(DKMiaoXiaOpGoodsModel model){
        if (model != null) {
            getP().productClick(model);
        }
    }

    public void jumpWebActivity (DKMiaoXiaOpGoodsModel model){
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            Router.newIntent(getActivity())
                    .to(DKMiaoXiaOpWebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public static String nxfgh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double tyfgdh(Object o) {

        return toDouble(o, 0);
    }

    public static double nbzdfg(Object o, int defaultValue) {
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

    public static long ewrdfgasd(Object o, long defaultValue) {
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

    public void initGoodsItemAdapter(List<DKMiaoXiaOpGoodsModel> mData) {
        if (goodsItemAdapterDKMiaoXiaOp == null) {
            goodsItemAdapterDKMiaoXiaOp = new GoodsItemAdapterDKMiaoXiaOp(getActivity());
            goodsItemAdapterDKMiaoXiaOp.setRecItemClick(new RecyclerItemCallback<DKMiaoXiaOpGoodsModel, GoodsItemAdapterDKMiaoXiaOp.ViewHolder>() {
                @Override
                public void onItemClick(int position, DKMiaoXiaOpGoodsModel model, int tag, GoodsItemAdapterDKMiaoXiaOp.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterDKMiaoXiaOp.setHasStableIds(true);
            goodsItemAdapterDKMiaoXiaOp.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterDKMiaoXiaOp);
        } else {
            goodsItemAdapterDKMiaoXiaOp.setData(mData);
        }
    }

    public void setModel(DKMiaoXiaOpGoodsModel DKMiaoXiaOpGoodsModel){
        this.DKMiaoXiaOpGoodsModel = DKMiaoXiaOpGoodsModel;
    }

    public static String vzesrtfdg(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double lgyukhjk(Object o) {

        return toDouble(o, 0);
    }

    public static double bfxhdfg(Object o, int defaultValue) {
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

    public static long etrdfxz(Object o, long defaultValue) {
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
}
