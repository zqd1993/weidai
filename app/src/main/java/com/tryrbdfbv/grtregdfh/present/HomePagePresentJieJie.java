package com.tryrbdfbv.grtregdfh.present;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tryrbdfbv.grtregdfh.model.BaseRespModelJieJie;
import com.tryrbdfbv.grtregdfh.model.GoodsJieJieModel;
import com.tryrbdfbv.grtregdfh.model.RequJieJieModel;
import com.tryrbdfbv.grtregdfh.net.ApiJieJie;
import com.tryrbdfbv.grtregdfh.ui.fragment.HomePageFragmentJieJie;
import com.tryrbdfbv.grtregdfh.utils.JieJieStaticUtil;
import com.tryrbdfbv.grtregdfh.utils.SharedPreferencesUtilisJieJie;
import com.tryrbdfbv.grtregdfh.mvp.XPresent;
import com.tryrbdfbv.grtregdfh.net.ApiSubscriber;
import com.tryrbdfbv.grtregdfh.net.NetError;
import com.tryrbdfbv.grtregdfh.net.XApi;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;


public class HomePagePresentJieJie extends XPresent<HomePageFragmentJieJie> {

    private int mobileType;

    private String phone, token;

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

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisJieJie.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilisJieJie.getStringFromPref("token");
            RequJieJieModel model = new RequJieJieModel();
            model.setToken(token);
            RequestBody body = JieJieStaticUtil.createBody(new Gson().toJson(model));
            ApiJieJie.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelJieJie<List<GoodsJieJieModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieJie<List<GoodsJieJieModel>>>getScheduler())
                    .compose(getV().<BaseRespModelJieJie<List<GoodsJieJieModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieJie<List<GoodsJieJieModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            JieJieStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieJie<List<GoodsJieJieModel>> gankResults) {
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

    public void productClick(GoodsJieJieModel model) {
        phone = SharedPreferencesUtilisJieJie.getStringFromPref("phone");
        ApiJieJie.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModelJieJie>getApiTransformer())
                .compose(XApi.<BaseRespModelJieJie>getScheduler())
                .compose(getV().<BaseRespModelJieJie>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelJieJie>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        JieJieStaticUtil.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelJieJie gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> wefzdgf(String gsonString) {
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
    public static <T> Map<String, T> mnfsghsdfh(String gsonString) {
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
    public static <T> List<T> rtnghdhj(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    public void aindex() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisJieJie.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilisJieJie.getStringFromPref("token");
            RequJieJieModel model = new RequJieJieModel();
            model.setToken(token);
            RequestBody body = JieJieStaticUtil.createBody(new Gson().toJson(model));
            ApiJieJie.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelJieJie<List<GoodsJieJieModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieJie<List<GoodsJieJieModel>>>getScheduler())
                    .compose(getV().<BaseRespModelJieJie<List<GoodsJieJieModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieJie<List<GoodsJieJieModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        JieJieStaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieJie<List<GoodsJieJieModel>> gankResults) {
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
                                        getV().renshu_tv.setText(String.valueOf(gankResults.getTop().getNum()));
                                        getV().month_tv.setText(gankResults.getTop().getFan_time().substring(0, 2));
                                        getV().mianxi_tv.setText(String.valueOf(gankResults.getTop().getDisplay()));
                                        getV().edu_tv.setText(String.valueOf(gankResults.getTop().getMax_money()));
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topGoodsJieJieModel = gankResults.getTop();
                                            if (!TextUtils.isEmpty(SharedPreferencesUtilisJieJie.getStringFromPref("API_BASE_URL"))) {
//                                                Glide.with(getV()).load(SharedPreferencesUtilisJieJie.getStringFromPref("API_BASE_URL") + gankResults.getTop().getImgs()).into(getV().topImg);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> iytursh(String gsonString) {
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
    public static <T> Map<String, T> wersfgh(String gsonString) {
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
    public static <T> List<T> fhdbvcn(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

}
