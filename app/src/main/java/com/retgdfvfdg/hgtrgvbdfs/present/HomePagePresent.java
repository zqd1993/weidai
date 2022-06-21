package com.retgdfvfdg.hgtrgvbdfs.present;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.retgdfvfdg.hgtrgvbdfs.R;
import com.retgdfvfdg.hgtrgvbdfs.imageloader.ILFactory;
import com.retgdfvfdg.hgtrgvbdfs.imageloader.ILoader;
import com.retgdfvfdg.hgtrgvbdfs.model.BaseRespModel;
import com.retgdfvfdg.hgtrgvbdfs.model.GoodsModel;
import com.retgdfvfdg.hgtrgvbdfs.model.LoginRespModel;
import com.retgdfvfdg.hgtrgvbdfs.model.RequModel;
import com.retgdfvfdg.hgtrgvbdfs.utils.SharedPreferencesUtilis;
import com.retgdfvfdg.hgtrgvbdfs.utils.StaticUtil;
import com.retgdfvfdg.hgtrgvbdfs.mvp.XPresent;
import com.retgdfvfdg.hgtrgvbdfs.net.Api;
import com.retgdfvfdg.hgtrgvbdfs.net.ApiSubscriber;
import com.retgdfvfdg.hgtrgvbdfs.net.NetError;
import com.retgdfvfdg.hgtrgvbdfs.net.XApi;
import com.retgdfvfdg.hgtrgvbdfs.ui.fragment.HomePageFragment;

import java.util.List;

import okhttp3.RequestBody;


public class HomePagePresent extends XPresent<HomePageFragment> {

    private int mobileType;

    private String phone, token;

    public void productList() {
        token = SharedPreferencesUtilis.getStringFromPref("token");
        RequModel model = new RequModel();
        model.setToken(token);
        RequestBody body = StaticUtil.createBody(new Gson().toJson(model));
        Api.getGankService().productList(body)
                .compose(XApi.<BaseRespModel<List<GoodsModel>>>getApiTransformer())
                .compose(XApi.<BaseRespModel<List<GoodsModel>>>getScheduler())
                .compose(getV().<BaseRespModel<List<GoodsModel>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModel<List<GoodsModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().swipeRefreshLayout.setRefreshing(false);
                        StaticUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModel<List<GoodsModel>> gankResults) {
                        getV().swipeRefreshLayout.setRefreshing(false);
                        if (gankResults != null) {
                            if (gankResults.getCode() == 0 && gankResults.getData() != null) {
                                if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                    getV().setModel(gankResults.getData().get(0));
                                    getV().initGoodsItemAdapter(gankResults.getData());
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(GoodsModel model) {
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        Api.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModel>getApiTransformer())
                .compose(XApi.<BaseRespModel>getScheduler())
                .compose(getV().<BaseRespModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        StaticUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModel gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public void aindex() {
        token = SharedPreferencesUtilis.getStringFromPref("token");
        RequModel model = new RequModel();
        model.setToken(token);
        RequestBody body = StaticUtil.createBody(new Gson().toJson(model));
        Api.getGankService().aindex(body)
                .compose(XApi.<BaseRespModel>getApiTransformer())
                .compose(XApi.<BaseRespModel>getScheduler())
                .compose(getV().<BaseRespModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModel>() {
                    @Override
                    protected void onFail(NetError error) {
//                        StaticUtil.showError(getV(), error);
                    }

                    @Override
                    public void onNext(BaseRespModel gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getTop() != null) {
                                if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                    Glide.with(getV()).load(Api.API_BASE_URL + gankResults.getTop().getImgs()).into(getV().topImg);
//                                    ILFactory.getLoader().loadNet(getV().topImg, Api.API_BASE_URL + gankResults.getTop().getImgs(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
                                }
                            }
                        }
                    }
                });
    }

}
