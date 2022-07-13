package com.rtyhdfh.mnzdfgdsg.uuuu.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rtyhdfh.mnzdfgdsg.R;
import com.rtyhdfh.mnzdfgdsg.aaaa.GoodsItemAdapterGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.mmmm.GeiNiHuaGoodsModel;
import com.rtyhdfh.mnzdfgdsg.mvp.XActivity;
import com.rtyhdfh.mnzdfgdsg.utils.GeiNiHuaStaticUtil;
import com.rtyhdfh.mnzdfgdsg.uuuu.GeiNiHuaWebViewActivity;
import com.rtyhdfh.mnzdfgdsg.mvp.XFragment;
import com.rtyhdfh.mnzdfgdsg.pppp.HomePagePresentGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragmentGeiNiHua extends XFragment<HomePagePresentGeiNiHua> {

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
    public GoodsItemAdapterGeiNiHua goodsItemAdapterGeiNiHua;
    private GeiNiHuaGoodsModel geiNiHuaGoodsModel;

    public static HomePageFragmentGeiNiHua getInstant(int tag) {
        HomePageFragmentGeiNiHua homePageFragmentGeiNiHua = new HomePageFragmentGeiNiHua();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        homePageFragmentGeiNiHua.setArguments(bundle);
        return homePageFragmentGeiNiHua;
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
            productClick(geiNiHuaGoodsModel);
        });
        parentFl.setOnClickListener(v -> {
            productClick(geiNiHuaGoodsModel);
        });
        topLayout.setOnClickListener(v -> {
            productClick(geiNiHuaGoodsModel);
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
        return R.layout.fragment_home_page_geinihua;
    }

    @Override
    public HomePagePresentGeiNiHua newP() {
        return new HomePagePresentGeiNiHua();
    }

    private void productClick(GeiNiHuaGoodsModel model){
        if (model != null) {
            getP().productClick(model);
        }
    }

    public void jumpWebActivity (GeiNiHuaGoodsModel model){
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            GeiNiHuaStaticUtil.getValue((XActivity) getActivity(), GeiNiHuaWebViewActivity.class, webBundle);
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

    public void initGoodsItemAdapter(List<GeiNiHuaGoodsModel> mData) {
        if (goodsItemAdapterGeiNiHua == null) {
            goodsItemAdapterGeiNiHua = new GoodsItemAdapterGeiNiHua(getActivity());
            goodsItemAdapterGeiNiHua.setRecItemClick(new RecyclerItemCallback<GeiNiHuaGoodsModel, GoodsItemAdapterGeiNiHua.ViewHolder>() {
                @Override
                public void onItemClick(int position, GeiNiHuaGoodsModel model, int tag, GoodsItemAdapterGeiNiHua.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterGeiNiHua.setHasStableIds(true);
            goodsItemAdapterGeiNiHua.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterGeiNiHua);
        } else {
            goodsItemAdapterGeiNiHua.setData(mData);
        }
    }

    public void setModel(GeiNiHuaGoodsModel geiNiHuaGoodsModel){
        this.geiNiHuaGoodsModel = geiNiHuaGoodsModel;
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
