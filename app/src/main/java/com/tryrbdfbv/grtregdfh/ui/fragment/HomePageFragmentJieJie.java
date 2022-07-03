package com.tryrbdfbv.grtregdfh.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tryrbdfbv.grtregdfh.R;
import com.tryrbdfbv.grtregdfh.adapter.GoodsItemAdapterJieJie;
import com.tryrbdfbv.grtregdfh.model.GoodsJieJieModel;
import com.tryrbdfbv.grtregdfh.ui.HomePageJieJieActivity;
import com.tryrbdfbv.grtregdfh.ui.WebViewJieJieActivity;
import com.tryrbdfbv.grtregdfh.mvp.XFragment;
import com.tryrbdfbv.grtregdfh.present.HomePagePresentJieJie;
import com.tryrbdfbv.grtregdfh.router.Router;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragmentJieJie extends XFragment<HomePagePresentJieJie> {

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
    @BindView(R.id.more_ll)
    View more_ll;
    @BindView(R.id.lingqu_sl)
    View lingqu_sl;
    @BindView(R.id.renshu_tv)
    public TextView renshu_tv;
    @BindView(R.id.edu_tv)
    public TextView edu_tv;
    @BindView(R.id.month_tv)
    public TextView month_tv;
    @BindView(R.id.mianxi_tv)
    public TextView mianxi_tv;

    private Bundle webBundle;
    public GoodsItemAdapterJieJie goodsItemAdapterJieJie;
    public GoodsJieJieModel goodsJieJieModel, topGoodsJieJieModel;

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> parseJsonToList(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        getP().productList();
//        ToastUtilJieJie.showShort("API_BASE_URL = " + SharedPreferencesUtilisJieJie.getStringFromPref("API_BASE_URL") +
//                "API_BASE_URL_1 = " + ApiJieJie.API_BASE_URL);
        getP().aindex();
        swipeRefreshLayout.setOnRefreshListener(() -> {
//            getP().productList();
            getP().aindex();
        });
//        productBg.setOnClickListener(v -> {
//            productClick(goodsJieJieModel);
//        });
//        swipeRefreshLayout.setOnClickListener(v -> {
//            productClick(goodsJieJieModel);
//        });
//        topLayout.setOnClickListener(v -> {
//            productClick(goodsJieJieModel);
//        });
//        click_view.setOnClickListener(v -> {
//            productClick(goodsJieJieModel);
//        });
//        click_view_1.setOnClickListener(v -> {
//            productClick(goodsJieJieModel);
//        });
        lingqu_sl.setOnClickListener(v -> {
            productClick(topGoodsJieJieModel);
        });
        more_ll.setOnClickListener(v -> {
            ((HomePageJieJieActivity)getActivity()).changePage();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_jiejie;
    }

    @Override
    public HomePagePresentJieJie newP() {
        return new HomePagePresentJieJie();
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> trydfbsdfgh(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> mdfghfgsh(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> aertgfgfgh(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    private void productClick(GoodsJieJieModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(GoodsJieJieModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewJieJieActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void initGoodsItemAdapter(List<GoodsJieJieModel> mData) {
        if (goodsItemAdapterJieJie == null) {
            goodsItemAdapterJieJie = new GoodsItemAdapterJieJie(getActivity());
            goodsItemAdapterJieJie.setRecItemClick(new RecyclerItemCallback<GoodsJieJieModel, GoodsItemAdapterJieJie.ViewHolder>() {
                @Override
                public void onItemClick(int position, GoodsJieJieModel model, int tag, GoodsItemAdapterJieJie.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterJieJie.setHasStableIds(true);
            goodsItemAdapterJieJie.setData(mData);
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterJieJie);
        } else {
            goodsItemAdapterJieJie.setData(mData);
        }
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> jhgdfg(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> wetgfbdgh(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> bnsdfgasdg(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    public void setModel(GoodsJieJieModel goodsJieJieModel) {
        this.goodsJieJieModel = goodsJieJieModel;
    }
}
