package com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.wuyoufenqjkfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zmansdjkdwhqwjsd.gfpla.R;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkadap.GoodsItemAdapterWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.WuYouFQdkOpGoodsModel;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.WuYouFQdkOpWebViewActivity;
import com.zmansdjkdwhqwjsd.gfpla.mvp.XFragment;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkpres.HomePageWuYouFQdkOpPresent;
import com.zmansdjkdwhqwjsd.gfpla.router.Router;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageFragmentWuYouFQdkOp extends XFragment<HomePageWuYouFQdkOpPresent> {

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
    @BindView(R.id.show_fl)
    View show_fl;

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T changeGsonToBean(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: "+e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    private Bundle bundle, webBundle;
    private int tag;
    public GoodsItemAdapterWuYouFQdkOp goodsItemAdapterWuYouFQdkOp;
    private WuYouFQdkOpGoodsModel wuYouFQdkOpGoodsModel;

    public static HomePageFragmentWuYouFQdkOp getInstant(int tag) {
        HomePageFragmentWuYouFQdkOp homePageFragmentWuYouFQdkOp = new HomePageFragmentWuYouFQdkOp();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        homePageFragmentWuYouFQdkOp.setArguments(bundle);
        return homePageFragmentWuYouFQdkOp;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getInt("tag");
            if (tag == 1) {
                productBg.setVisibility(View.GONE);
                homePageBg.setVisibility(View.VISIBLE);
                show_fl.setVisibility(View.VISIBLE);
            } else {
                productBg.setVisibility(View.VISIBLE);
                homePageBg.setVisibility(View.GONE);
                show_fl.setVisibility(View.GONE);
            }
        }
        getP().productList();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        noDataFl.setOnClickListener(v -> getP().productList());
        productBg.setOnClickListener(v -> {
            productClick(wuYouFQdkOpGoodsModel);
        });
        parentFl.setOnClickListener(v -> {
            productClick(wuYouFQdkOpGoodsModel);
        });
        topLayout.setOnClickListener(v -> {
            productClick(wuYouFQdkOpGoodsModel);
        });
        show_fl.setOnClickListener(v -> {
            productClick(wuYouFQdkOpGoodsModel);
        });
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T oyuuityjfg(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public <T> List<T> mfghjdftyh(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: "+e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> wergdfg(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> fbdfgdfg(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_wu_you_fen_qi_jk_op;
    }

    @Override
    public HomePageWuYouFQdkOpPresent newP() {
        return new HomePageWuYouFQdkOpPresent();
    }

    private void productClick(WuYouFQdkOpGoodsModel model){
        if (model != null) {
            getP().productClick(model);
        }
    }

    public void jumpWebActivity (WuYouFQdkOpGoodsModel model){
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            Router.newIntent(getActivity())
                    .to(WuYouFQdkOpWebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T ddnhdfj(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public <T> List<T> kldfghbn(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: "+e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> ghjghjdfgh(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> nfgssdfg(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    public void initGoodsItemAdapter(List<WuYouFQdkOpGoodsModel> mData) {
        if (goodsItemAdapterWuYouFQdkOp == null) {
            goodsItemAdapterWuYouFQdkOp = new GoodsItemAdapterWuYouFQdkOp(getActivity());
            goodsItemAdapterWuYouFQdkOp.setRecItemClick(new RecyclerItemCallback<WuYouFQdkOpGoodsModel, GoodsItemAdapterWuYouFQdkOp.ViewHolder>() {
                @Override
                public void onItemClick(int position, WuYouFQdkOpGoodsModel model, int tag, GoodsItemAdapterWuYouFQdkOp.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterWuYouFQdkOp.setHasStableIds(true);
            goodsItemAdapterWuYouFQdkOp.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterWuYouFQdkOp);
        } else {
            goodsItemAdapterWuYouFQdkOp.setData(mData);
        }
    }

    public void setModel(WuYouFQdkOpGoodsModel wuYouFQdkOpGoodsModel){
        this.wuYouFQdkOpGoodsModel = wuYouFQdkOpGoodsModel;
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T tgbndfgh(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public <T> List<T> puiiyjgh(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: "+e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> ertdfgdfg(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> bsfghfghrt(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
}
