package com.rtyhdfh.mnzdfgdsg.pppp;

import android.text.TextUtils;
import android.view.View;

import com.rtyhdfh.mnzdfgdsg.mmmm.BaseRespModelGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.mmmm.GeiNiHuaGoodsModel;
import com.rtyhdfh.mnzdfgdsg.uuuu.fragment.HomePageFragmentGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.utils.SharedPreferencesUtilisGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.utils.GeiNiHuaStaticUtil;
import com.rtyhdfh.mnzdfgdsg.mvp.XPresent;
import com.rtyhdfh.mnzdfgdsg.nnnn.ApiGeiNiHua;
import com.rtyhdfh.mnzdfgdsg.nnnn.ApiSubscriber;
import com.rtyhdfh.mnzdfgdsg.nnnn.NetError;
import com.rtyhdfh.mnzdfgdsg.nnnn.XApi;

import java.util.List;


public class HomePagePresentGeiNiHua extends XPresent<HomePageFragmentGeiNiHua> {

    private int mobileType;

    private String phone;

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

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisGeiNiHua.getStringFromPref("HTTP_API_URL"))) {
            mobileType = SharedPreferencesUtilisGeiNiHua.getIntFromPref("mobileType");
            phone = SharedPreferencesUtilisGeiNiHua.getStringFromPref("phone");
            ApiGeiNiHua.getGankService().productList(mobileType, phone)
                    .compose(XApi.<BaseRespModelGeiNiHua<List<GeiNiHuaGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelGeiNiHua<List<GeiNiHuaGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelGeiNiHua<List<GeiNiHuaGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelGeiNiHua<List<GeiNiHuaGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().goodsItemAdapterGeiNiHua == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            GeiNiHuaStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelGeiNiHua<List<GeiNiHuaGoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    } else {
                                        getV().noDataFl.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            } else {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }

    public void productClick(GeiNiHuaGoodsModel model) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisGeiNiHua.getStringFromPref("HTTP_API_URL"))) {
            phone = SharedPreferencesUtilisGeiNiHua.getStringFromPref("phone");
            ApiGeiNiHua.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModelGeiNiHua>getApiTransformer())
                    .compose(XApi.<BaseRespModelGeiNiHua>getScheduler())
                    .compose(getV().<BaseRespModelGeiNiHua>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelGeiNiHua>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            GeiNiHuaStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelGeiNiHua gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

    public static String vzdfgrg(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double ytjfh(Object o) {

        return toDouble(o, 0);
    }

    public static double eawtrfd(Object o, int defaultValue) {
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

    public static long ngsdhft(Object o, long defaultValue) {
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
