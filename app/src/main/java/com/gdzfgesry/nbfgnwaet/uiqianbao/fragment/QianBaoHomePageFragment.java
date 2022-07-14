package com.gdzfgesry.nbfgnwaet.uiqianbao.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gdzfgesry.nbfgnwaet.R;
import com.gdzfgesry.nbfgnwaet.adapterqianbao.GoodsItemAdapterQianBao;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.GoodsQianBaoModel;
import com.gdzfgesry.nbfgnwaet.uiqianbao.QianBaoWebViewActivity;
import com.gdzfgesry.nbfgnwaet.mvp.XFragment;
import com.gdzfgesry.nbfgnwaet.qianbaopresent.HomeQianBaoPagePresent;
import com.gdzfgesry.nbfgnwaet.router.Router;

import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class QianBaoHomePageFragment extends XFragment<HomeQianBaoPagePresent> {

    @BindView(R.id.product_bg)
    View productBg;
    @BindView(R.id.home_page_bg)
    View homePageBg;
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.top_layout)
    View topLayout;
    @BindView(R.id.click_view)
    View click_view;
    @BindView(R.id.click_view_1)
    View click_view_1;
    @BindView(R.id.top_img)
    public ImageView topImg;

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

    private Bundle webBundle;
    public GoodsItemAdapterQianBao goodsItemAdapterQianBao;
    public GoodsQianBaoModel goodsQianBaoModel, topGoodsQianBaoModel;

    @Override
    public void initData(Bundle savedInstanceState) {
//        getP().productList();
//        ToastUtil.showShort("API_BASE_URL = " + SharedPreferencesUtilis.getStringFromPref("API_BASE_URL") +
//                "API_BASE_URL_1 = " + Api.API_BASE_URL);
        getP().aindex();
        swipeRefreshLayout.setOnRefreshListener(() -> {
//            getP().productList();
            getP().aindex();
        });
//        productBg.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        swipeRefreshLayout.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        topLayout.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        click_view.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        click_view_1.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
        topImg.setOnClickListener(v -> {
            productClick(topGoodsQianBaoModel);
        });
    }

    public static String fdsgfrethz(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double bnxfmntuy(Object o) {

        return toDouble(o, 0);
    }

    public static double ergdhh(Object o, int defaultValue) {
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

    public static long kjxtryhzfdh(Object o, long defaultValue) {
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
        return R.layout.fragment_home_qian_bao_page;
    }

    @Override
    public HomeQianBaoPagePresent newP() {
        return new HomeQianBaoPagePresent();
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

    public static String wtdrydh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double fgretzd(Object o) {

        return toDouble(o, 0);
    }

    public static double gzdfhzrt(Object o, int defaultValue) {
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

    public static long mxnfzdry(Object o, long defaultValue) {
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

    public static String zbzreyx(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double puyjxfgjh(Object o) {

        return toDouble(o, 0);
    }

    public static double mxfghjxty(Object o, int defaultValue) {
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

    public static long tERtgh(Object o, long defaultValue) {
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
