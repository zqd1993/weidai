package com.fghyugfg.mjkyhgb.f;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fghyugfg.mjkyhgb.R;
import com.fghyugfg.mjkyhgb.a.JumpH5Activity;
import com.fghyugfg.mjkyhgb.api.NewApi;
import com.fghyugfg.mjkyhgb.imageloader.ILFactory;
import com.fghyugfg.mjkyhgb.imageloader.ILoader;
import com.fghyugfg.mjkyhgb.m.RenRenBaseModel;
import com.fghyugfg.mjkyhgb.m.RenRenGoodsModel;
import com.fghyugfg.mjkyhgb.mvp.XFragment;
import com.fghyugfg.mjkyhgb.net.ApiSubscriber;
import com.fghyugfg.mjkyhgb.net.NetError;
import com.fghyugfg.mjkyhgb.net.XApi;
import com.fghyugfg.mjkyhgb.u.OpenMethodUtil;
import com.fghyugfg.mjkyhgb.u.SPOpenUtil;
import com.youth.banner.Banner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

import butterknife.BindView;

public class RenRenGoodsFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.hot_fl)
    View hotFl;
    @BindView(R.id.banner1)
    Banner banner;
    private RenRenGoodsModel renRenGoodsModel;

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_img.setVisibility(View.GONE);
        hotFl.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        banner.setVisibility(View.GONE);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(renRenGoodsModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(renRenGoodsModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(renRenGoodsModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(RenRenGoodsModel model) {
        if (model != null) {
            phone = SPOpenUtil.getString("phone");
            NewApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<RenRenBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(RenRenBaseModel renRenBaseModel) {
                            toWeb(model);
                        }
                    });
        }
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager mgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo[] info = mgr.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void productList() {
        mobileType = SPOpenUtil.getInt("mobileType");
        NewApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<RenRenBaseModel<List<RenRenGoodsModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenMethodUtil.showErrorInfo(getActivity(), error);
                        if (goodsListLl.getChildCount() == 0) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(RenRenBaseModel<List<RenRenGoodsModel>> renRenBaseModel) {
                        setRefreshing.setRefreshing(false);
                        if (renRenBaseModel != null) {
                            if (renRenBaseModel.getCode() == 200 && renRenBaseModel.getData() != null) {
                                if (renRenBaseModel.getData() != null && renRenBaseModel.getData().size() > 0) {
                                    renRenGoodsModel = renRenBaseModel.getData().get(0);
                                    addProductView(renRenBaseModel.getData());
                                } else {
                                    if (goodsListLl.getChildCount() == 0) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (goodsListLl.getChildCount() == 0) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    public static String dealHtml(String data, String color) {
        Document doc = null;
        Element head = null;
//        head.clearAttributes();
//        Elements children = head.children();
//        if (children != null) {
//            children.removeGoods();
//        }

//        Element list = head.getElementById("list");
//        if (list != null) {
//            list.removeGoods();
//        }

//        head.prepend("<meta name=\"format-detection\" content=\"telephone=no\" />");
//        head.prepend("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0\"/>");
//        head.append("<style type=\"text/css\"> body{background-color:" + color + ";word-wrap:break-word;font-family:Arial;width:100%;height:auto;margin:auto} img{max-width:100%;height:auto} </style>");
//        Element body = doc.body();
//        body.attr("style", "padding:0px;");
//        body.append("<style type=\"text/css\"> body{width:100%;height:auto;margin:auto} img{max-width:100%} </style>");


//        Element body = doc.body();
//        Elements allElements = body.getAllElements();
//        if (allElements != null && allElements.size() > 0) {
//            for (Element element : allElements) {
//                if (element != null) {
//                    element.attr("max-width", "100%");
//                }
//            }
//        }
//        Logger.d(doc.toString());
        return doc.toString();
    }

    private void addProductView(List<RenRenGoodsModel> mList) {
        goodsListLl.removeAllViews();
        for (RenRenGoodsModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_goods_item, null);
            TextView timeTv = view.findViewById(R.id.time_tv);
            TextView peopleNumberTv = view.findViewById(R.id.personal_number_tv);
            ImageView pic = view.findViewById(R.id.goods_pic);
            TextView product_name_tv = view.findViewById(R.id.goods_name_tv);
            TextView rixi_tv = view.findViewById(R.id.rixi_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            View click_view = view.findViewById(R.id.click_view);
            timeTv.setText(model.getDes() + "个月");
            peopleNumberTv.setText(String.valueOf(model.getPassingRate()));
            ILFactory.getLoader().loadNet(pic, NewApi.HTTP_API_URL + model.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            product_name_tv.setText(model.getProductName());
            rixi_tv.setText(model.getTag());
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            click_view.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
    }

    public void toWeb(RenRenGoodsModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenMethodUtil.jumpPage(getActivity(), JumpH5Activity.class, bundle);
        }
    }

    public static void e(Exception meg, boolean allStack, boolean hasModification) {
        if (true) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
            meg.printStackTrace(printWriter);

        }
    }

}
