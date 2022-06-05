package com.akjsdhfkjhj.kahssj.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akjsdhfkjhj.kahssj.R;
import com.akjsdhfkjhj.kahssj.model.BaseModel;
import com.akjsdhfkjhj.kahssj.model.ProductModel;
import com.akjsdhfkjhj.kahssj.net.Api;
import com.akjsdhfkjhj.kahssj.net.ApiSubscriber;
import com.akjsdhfkjhj.kahssj.net.NetError;
import com.akjsdhfkjhj.kahssj.net.XApi;
import com.akjsdhfkjhj.kahssj.ui.LoginActivityHuiMin;
import com.akjsdhfkjhj.kahssj.ui.WebActivity;
import com.akjsdhfkjhj.kahssj.ui.activity.AppinfoActivity;
import com.akjsdhfkjhj.kahssj.ui.activity.ZhuXiaoUserActivity;
import com.akjsdhfkjhj.kahssj.utils.MainUtil;
import com.akjsdhfkjhj.kahssj.utils.SPUtilis;
import com.akjsdhfkjhj.kahssj.utils.ToastUtil;
import com.akjsdhfkjhj.kahssj.router.Router;

import butterknife.BindView;

import com.akjsdhfkjhj.kahssj.widget.PuTongDialog;
import com.akjsdhfkjhj.kahssj.mvp.XFragment;
import com.akjsdhfkjhj.kahssj.ui.activity.FeedBackActivityHuiMin;

import java.util.List;

public class WodeFragment extends XFragment {
    @BindView(R.id.mobile_tv)
    TextView phoneTv;
    @BindView(R.id.app_info)
    View appInfo;
    @BindView(R.id.zcxy)
    View zcxy;
    @BindView(R.id.ysxy)
    View ysxy;
    @BindView(R.id.yjfk)
    View yjfk;
    @BindView(R.id.gxhtj)
    View gxhtj;
    @BindView(R.id.tsyx)
    View tsyx;
    @BindView(R.id.zxzh)
    View zxzh;
    @BindView(R.id.logout)
    View logout;
    @BindView(R.id.biaoti_img)
    ImageView biaotiImg;

    private Bundle bundle, webBundle;
    private PuTongDialog puTongDialog;
    private String mailStr = "", phone = "";
    private ProductModel productModel;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = SPUtilis.getStringFromPref("app_mail");
        phone = SPUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        productList();
        appInfo.setOnClickListener(v -> {
            Router.newIntent(getActivity())
                    .to(AppinfoActivity.class)
                    .launch();
        });
        zcxy.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 1);
            bundle.putString("url", Api.PRIVACY_POLICY);
            Router.newIntent(getActivity())
                    .to(WebActivity.class)
                    .data(bundle)
                    .launch();
        });
        ysxy.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 2);
            bundle.putString("url", Api.USER_SERVICE_AGREEMENT);
            Router.newIntent(getActivity())
                    .to(WebActivity.class)
                    .data(bundle)
                    .launch();
        });
        yjfk.setOnClickListener(v -> {
            Router.newIntent(getActivity())
                    .to(FeedBackActivityHuiMin.class)
                    .launch();
        });
        gxhtj.setOnClickListener(j -> {
            puTongDialog = new PuTongDialog(getActivity());
            puTongDialog.setTitle("温馨提示")
                    .setContent("关闭或开启推送")
                    .setCancelText("开启")
                    .setLeftListener(v -> {
                        ToastUtil.showShort("开启成功");
                        puTongDialog.dismiss();
                    })
                    .setConfirmText("关闭")
                    .setRightListener(v -> {
                        ToastUtil.showShort("关闭成功");
                        puTongDialog.dismiss();
                    }).show();
        });
        tsyx.setOnClickListener(v -> {
            puTongDialog = new PuTongDialog(getActivity());
            puTongDialog.setTitle("温馨提示")
                    .setContent(mailStr)
                    .showOnlyBtn().show();
        });
        zxzh.setOnClickListener(v -> {
            Router.newIntent(getActivity())
                    .to(ZhuXiaoUserActivity.class)
                    .launch();
        });
        logout.setOnClickListener(j -> {
            puTongDialog = new PuTongDialog(getActivity());
            puTongDialog.setTitle("温馨提示")
                    .setContent("确定退出当前登录")
                    .setCancelText("取消")
                    .setLeftListener(v -> {
                        puTongDialog.dismiss();
                    })
                    .setConfirmText("退出")
                    .setRightListener(v -> {
                        puTongDialog.dismiss();
                        SPUtilis.saveStringIntoPref("phone", "");
                        Router.newIntent(getActivity())
                                .to(LoginActivityHuiMin.class)
                                .launch();
                        getActivity().finish();
                    }).show();
        });
        biaotiImg.setOnClickListener(v -> {
            if (productModel != null) {
                productClick(productModel);
            }
        });
    }

    private int mobileType;

    public void productClick(ProductModel model) {
        phone = SPUtilis.getStringFromPref("phone");
        Api.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(this.<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        jumpWebYouXinActivity(model);
                        MainUtil.showError(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseModel gankResults) {
                        jumpWebYouXinActivity(model);
                    }
                });
    }

    public void jumpWebYouXinActivity (ProductModel model){
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrl());
            webBundle.putString("title", model.getProductName());
            Router.newIntent(getActivity())
                    .to(WebActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void productList() {
        mobileType = SPUtilis.getIntFromPref("mobileType");
        Api.getGankService().productList(mobileType)
                .compose(XApi.<BaseModel<List<ProductModel>>>getApiTransformer())
                .compose(XApi.<BaseModel<List<ProductModel>>>getScheduler())
                .compose(this.<BaseModel<List<ProductModel>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<List<ProductModel>>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseModel<List<ProductModel>> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                    productModel = gankResults.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (puTongDialog != null) {
            puTongDialog.dismiss();
            puTongDialog = null;
        }
        super.onDestroy();
    }
}
