package com.dlproject.bkdk.fra;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dlproject.bkdk.R;
import com.dlproject.bkdk.act.JumpH5Activity;
import com.dlproject.bkdk.net.WangLuoApi;
import com.dlproject.bkdk.imageloader.ILFactory;
import com.dlproject.bkdk.imageloader.ILoader;
import com.dlproject.bkdk.bean.ParentModel;
import com.dlproject.bkdk.bean.ChanPinModel;
import com.dlproject.bkdk.mvp.XFragment;
import com.dlproject.bkdk.net.ApiSubscriber;
import com.dlproject.bkdk.net.NetError;
import com.dlproject.bkdk.net.XApi;
import com.dlproject.bkdk.uti.GongJuLei;
import com.dlproject.bkdk.uti.SPFile;

import java.util.List;

import butterknife.BindView;

public class ChanPinFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_ll)
    View main_top_ll;
    @BindView(R.id.jx_bg)
    View jx_bg;
    private ChanPinModel chanPinModel;

    private Bundle bundle;

    /**
     * 历史地址
     */
    public final static String HISTORYADDRESS = "HISTORYADDRESS";
    /**
     * 扫描类型
     */
    public final static String QRCODETYPE = "QRCODETYPE";

    public final static String BARCODE = "BARCODE";

    //    获取默认文件存储目录
    public static String getUpdateFileDir() {
        return "amez/apk/";
    }

    public final static String INTENT_KEY = "update_dialog_values";

    // 360手机助手
    public static final String MARKET = "com.qihoo.appstore";
    // 淘宝手机助手
    public static final String MARKET_TAOBAO = "com.taobao.appcenter";
    // 应用宝
    public static final String MARKET_QQDOWNLOADER = "com.tencent.Android.qqdownloader";
    // 安卓市场
    public static final String MARKET_HIAPK = "com.hiapk.marketpho";
    // 安智市场
    public static final String MARKET_GOAPK = "cn.goapk.market";
    // 包名
    public static final String APP_PACKAGE_NAME = "com.*.*";

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_ll.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_ll.setOnClickListener(v -> {
            productClick(chanPinModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(chanPinModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(chanPinModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_parent;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ChanPinModel model) {
        if (model != null) {
            phone = SPFile.getString("phone");
            WangLuoApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<ParentModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(ParentModel parentModel) {
                            toWeb(model);
                        }
                    });
        }
    }



    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 启动App
     *
     * @param context
     */
    public static void launchapp(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager()
                    .getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 跳转到应用市场
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    public void productList() {
        mobileType = SPFile.getInt("mobileType");
        WangLuoApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<ParentModel<List<ChanPinModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        GongJuLei.showErrorInfo(getActivity(), error);
                        if (goodsListLl.getChildCount() == 0) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(ParentModel<List<ChanPinModel>> parentModel) {
                        setRefreshing.setRefreshing(false);
                        if (parentModel != null) {
                            if (parentModel.getCode() == 200 && parentModel.getData() != null) {
                                if (parentModel.getData() != null && parentModel.getData().size() > 0) {
                                    chanPinModel = parentModel.getData().get(0);
                                    addProductView(parentModel.getData());
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


    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean yuikjhg(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 启动App
     *
     * @param context
     */
    public static void gghh(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager()
                    .getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 跳转到应用市场
     */
    public static void ertg(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    private void addProductView(List<ChanPinModel> mList) {
        goodsListLl.removeAllViews();
        for (ChanPinModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView goods_mingzi_tv = view.findViewById(R.id.goods_mingzi_tv);
            TextView label_tv = view.findViewById(R.id.label_tv);
            TextView price_tv = view.findViewById(R.id.price_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            TextView time_tv = view.findViewById(R.id.time_tv);
            TextView many_tv = view.findViewById(R.id.many_tv);
            many_tv.setText(String.valueOf(model.getPassingRate()));
            ILFactory.getLoader().loadNet(pic, WangLuoApi.HTTP_API_URL + model.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            goods_mingzi_tv.setText(model.getProductName());
            time_tv.setText(model.getDes() + "个月");
            label_tv.setText(model.getTag());
            price_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
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
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean ecxft(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 启动App
     *
     * @param context
     */
    public static void tggfd(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager()
                    .getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 跳转到应用市场
     */
    public static void bcvbgf(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

    public void toWeb(ChanPinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            GongJuLei.jumpPage(getActivity(), JumpH5Activity.class, bundle);
        }
    }
}
