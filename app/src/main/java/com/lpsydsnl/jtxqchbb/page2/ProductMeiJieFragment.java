package com.lpsydsnl.jtxqchbb.page2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lpsydsnl.jtxqchbb.R;
import com.lpsydsnl.jtxqchbb.mvp.XActivity;
import com.lpsydsnl.jtxqchbb.page.MeiJieJumpH5Activity;
import com.lpsydsnl.jtxqchbb.net.HttpMeiJieApi;
import com.lpsydsnl.jtxqchbb.imageloader.ILFactory;
import com.lpsydsnl.jtxqchbb.imageloader.ILoader;
import com.lpsydsnl.jtxqchbb.model.MeiJieBaseModel;
import com.lpsydsnl.jtxqchbb.model.ProductMeiJieModel;
import com.lpsydsnl.jtxqchbb.mvp.XFragment;
import com.lpsydsnl.jtxqchbb.net.ApiSubscriber;
import com.lpsydsnl.jtxqchbb.net.NetError;
import com.lpsydsnl.jtxqchbb.net.XApi;
import com.lpsydsnl.jtxqchbb.use.OpenMeiJieUtil;
import com.lpsydsnl.jtxqchbb.use.MeiJiePreferencesOpenUtil;

import java.util.List;

import butterknife.BindView;

public class ProductMeiJieFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.bg_im)
    View bg_im;
    @BindView(R.id.list_fl)
    View list_fl;
    private ProductMeiJieModel productMeiJieModel;

    private Bundle bundle;

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

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean isConnected(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) list_fl.getLayoutParams();
        layoutParams.topMargin = 30;
        list_fl.setLayoutParams(layoutParams);
        jx_bg.setVisibility(View.VISIBLE);
        bg_im.setVisibility(View.GONE);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productMeiJieModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productMeiJieModel);
        });
        list_fl.setOnClickListener(v -> {
            productClick(productMeiJieModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_meijie;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductMeiJieModel model) {
        if (!TextUtils.isEmpty(MeiJiePreferencesOpenUtil.getString("HTTP_API_URL"))) {
            if (model != null) {
                phone = MeiJiePreferencesOpenUtil.getString("phone");
                HttpMeiJieApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<MeiJieBaseModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(MeiJieBaseModel meiJieBaseModel) {
                                toWeb(model);
                            }
                        });
            }
        }
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean erefdsv(Context mContext) {
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

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean nfgdf(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public void productList() {
        if (!TextUtils.isEmpty(MeiJiePreferencesOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = MeiJiePreferencesOpenUtil.getInt("mobileType");
            HttpMeiJieApi.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MeiJieBaseModel<List<ProductMeiJieModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenMeiJieUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(MeiJieBaseModel<List<ProductMeiJieModel>> meiJieBaseModel) {
                            setRefreshing.setRefreshing(false);
                            if (meiJieBaseModel != null) {
                                if (meiJieBaseModel.getCode() == 200 && meiJieBaseModel.getData() != null) {
                                    if (meiJieBaseModel.getData() != null && meiJieBaseModel.getData().size() > 0) {
                                        productMeiJieModel = meiJieBaseModel.getData().get(0);
                                        addProductView(meiJieBaseModel.getData());
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
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean werfsdf(Context mContext) {
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

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean mhjgjgfh(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    private void addProductView(List<ProductMeiJieModel> mList) {
        goodsListLl.removeAllViews();
        for (ProductMeiJieModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_meijie, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.product_name_tv);
            TextView remind_tv = view.findViewById(R.id.remind_tv);
            TextView money_number_tv = view.findViewById(R.id.money_number_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            shijian_tv.setText(model.getDes() + "个月");
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            if (!TextUtils.isEmpty(MeiJiePreferencesOpenUtil.getString("HTTP_API_URL"))) {
                ILFactory.getLoader().loadNet(pic, MeiJiePreferencesOpenUtil.getString("HTTP_API_URL") + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            }
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            parentFl.setOnClickListener(v -> {
                productClick(model);
            });
            pic.setOnClickListener(v -> {
                productClick(model);
            });
            yjsqSl.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
    }

    /**
     * 返回是否有网络连接
     *
     * @param mContext
     * @return
     */
    public static boolean wersdfsf(Context mContext) {
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

    /***
     * 返回指定网络是否连接
     * @param mContext
     * @param distinguish
     * @param type
     * @return
     */
    public static boolean bdfsdfew(Context mContext, boolean distinguish, int type) {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo;
            if (distinguish) {
                mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(type);
            } else {
                mMobileNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public void toWeb(ProductMeiJieModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenMeiJieUtil.getValue((XActivity) getActivity(), MeiJieJumpH5Activity.class, bundle);
        }
    }
}
