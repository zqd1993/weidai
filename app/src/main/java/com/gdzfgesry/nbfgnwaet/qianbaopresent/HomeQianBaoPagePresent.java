package com.gdzfgesry.nbfgnwaet.qianbaopresent;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.BaseRespQianBaoModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.GoodsQianBaoModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.RequQianBaoModel;
import com.gdzfgesry.nbfgnwaet.uiqianbao.fragment.QianBaoHomePageFragment;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.SharedQianBaoPreferencesUtilis;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.StaticUtilQianBao;
import com.gdzfgesry.nbfgnwaet.mvp.XPresent;
import com.gdzfgesry.nbfgnwaet.netqianbao.QianBaoApi;
import com.gdzfgesry.nbfgnwaet.netqianbao.ApiSubscriber;
import com.gdzfgesry.nbfgnwaet.netqianbao.NetError;
import com.gdzfgesry.nbfgnwaet.netqianbao.XApi;

import java.util.List;

import okhttp3.RequestBody;


public class HomeQianBaoPagePresent extends XPresent<QianBaoHomePageFragment> {

    private int mobileType;

    private String phone, token;

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
        if (!TextUtils.isEmpty(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedQianBaoPreferencesUtilis.getStringFromPref("token");
            RequQianBaoModel model = new RequQianBaoModel();
            model.setToken(token);
            RequestBody body = StaticUtilQianBao.createBody(new Gson().toJson(model));
            QianBaoApi.getGankService().productList(body)
                    .compose(XApi.<BaseRespQianBaoModel<List<GoodsQianBaoModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespQianBaoModel<List<GoodsQianBaoModel>>>getScheduler())
                    .compose(getV().<BaseRespQianBaoModel<List<GoodsQianBaoModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQianBaoModel<List<GoodsQianBaoModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticUtilQianBao.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespQianBaoModel<List<GoodsQianBaoModel>> gankResults) {
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
    }

    public static String tyrthgsrty(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double aretzdfer(Object o) {

        return toDouble(o, 0);
    }

    public static double gfhseryzdfh(Object o, int defaultValue) {
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

    public static long jaergzret(Object o, long defaultValue) {
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

    public void productClick(GoodsQianBaoModel model) {
        phone = SharedQianBaoPreferencesUtilis.getStringFromPref("phone");
        QianBaoApi.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespQianBaoModel>getApiTransformer())
                .compose(XApi.<BaseRespQianBaoModel>getScheduler())
                .compose(getV().<BaseRespQianBaoModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespQianBaoModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        StaticUtilQianBao.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespQianBaoModel gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    public void aindex() {
        if (!TextUtils.isEmpty(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = SharedQianBaoPreferencesUtilis.getStringFromPref("token");
            RequQianBaoModel model = new RequQianBaoModel();
            model.setToken(token);
            RequestBody body = StaticUtilQianBao.createBody(new Gson().toJson(model));
            QianBaoApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespQianBaoModel<List<GoodsQianBaoModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespQianBaoModel<List<GoodsQianBaoModel>>>getScheduler())
                    .compose(getV().<BaseRespQianBaoModel<List<GoodsQianBaoModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQianBaoModel<List<GoodsQianBaoModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespQianBaoModel<List<GoodsQianBaoModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {

                            }
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topGoodsQianBaoModel = gankResults.getTop();
                                            if (!TextUtils.isEmpty(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
                                                Glide.with(getV()).load(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL") + gankResults.getTop().getImgs()).into(getV().topImg);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public static String jjsrtyzdh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double aertreghxfh(Object o) {

        return toDouble(o, 0);
    }

    public static double ttrhsry(Object o, int defaultValue) {
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

    public static long rtyhxj(Object o, long defaultValue) {
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
