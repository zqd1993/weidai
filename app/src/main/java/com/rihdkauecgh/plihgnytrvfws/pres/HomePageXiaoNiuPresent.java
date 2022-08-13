package com.rihdkauecgh.plihgnytrvfws.pres;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rihdkauecgh.plihgnytrvfws.models.BaseRespXiaoNiuModel;
import com.rihdkauecgh.plihgnytrvfws.models.XiaoNiuGoodsModel;
import com.rihdkauecgh.plihgnytrvfws.ui.fragment.HomePageFragmentXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesXiaoNiuUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StaticUtilXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.mvp.XPresent;
import com.rihdkauecgh.plihgnytrvfws.http.ApiXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.http.ApiSubscriber;
import com.rihdkauecgh.plihgnytrvfws.http.NetError;
import com.rihdkauecgh.plihgnytrvfws.http.XApi;

import java.util.List;
import java.util.Map;


public class HomePageXiaoNiuPresent extends XPresent<HomePageFragmentXiaoNiu> {

    private int mobileType;

    private String phone;

    // 把json字符串变成实体类Bean并对对应参数赋值
    public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
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
    public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
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
    public static <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
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
    public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
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

    public void productList() {
            mobileType = SharedPreferencesXiaoNiuUtilis.getIntFromPref("mobileType");
            ApiXiaoNiu.getGankService().productList(mobileType)
                    .compose(XApi.<BaseRespXiaoNiuModel<List<XiaoNiuGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespXiaoNiuModel<List<XiaoNiuGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespXiaoNiuModel<List<XiaoNiuGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespXiaoNiuModel<List<XiaoNiuGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().goodsItemAdapterXiaoNiu == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            StaticUtilXiaoNiu.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespXiaoNiuModel<List<XiaoNiuGoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    } else {
                                        if (getV().goodsItemAdapterXiaoNiu == null) {
                                            getV().noDataFl.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (getV().goodsItemAdapterXiaoNiu == null) {
                                        getV().noDataFl.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (getV().goodsItemAdapterXiaoNiu == null) {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public static <T> T wefdsg(String gsonString, Class<T> cls) {
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
    public static <T> List<T> jdsfgcb(String gsonString, Class<T> cls) {
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
    public static <T> List<Map<String, T>> ertsdgdfg(String gsonString) {
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
    public static <T> Map<String, T> fndgsdg(String gsonString) {
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

    public void productClick(XiaoNiuGoodsModel model) {
            phone = SharedPreferencesXiaoNiuUtilis.getStringFromPref("phone");
            ApiXiaoNiu.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespXiaoNiuModel>getApiTransformer())
                    .compose(XApi.<BaseRespXiaoNiuModel>getScheduler())
                    .compose(getV().<BaseRespXiaoNiuModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespXiaoNiuModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticUtilXiaoNiu.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespXiaoNiuModel gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
    }

}
