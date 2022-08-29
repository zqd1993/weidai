package com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkpres;

import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.BaseRespWuYouFQdkOpModel;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.WuYouFQdkOpGoodsModel;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.wuyoufenqjkfragment.HomePageFragmentWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.SharedPreferencesWuYouFQdkOpUtilis;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.StaticUtilWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.mvp.XPresent;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.ApiWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.ApiSubscriber;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.NetError;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.XApi;

import java.util.List;
import java.util.Map;


public class HomePageWuYouFQdkOpPresent extends XPresent<HomePageFragmentWuYouFQdkOp> {

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
            mobileType = SharedPreferencesWuYouFQdkOpUtilis.getIntFromPref("mobileType");
            ApiWuYouFQdkOp.getGankService().productList(mobileType)
                    .compose(XApi.<BaseRespWuYouFQdkOpModel<List<WuYouFQdkOpGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespWuYouFQdkOpModel<List<WuYouFQdkOpGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespWuYouFQdkOpModel<List<WuYouFQdkOpGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespWuYouFQdkOpModel<List<WuYouFQdkOpGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().goodsItemAdapterWuYouFQdkOp == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            StaticUtilWuYouFQdkOp.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespWuYouFQdkOpModel<List<WuYouFQdkOpGoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    } else {
                                        if (getV().goodsItemAdapterWuYouFQdkOp == null) {
                                            getV().noDataFl.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (getV().goodsItemAdapterWuYouFQdkOp == null) {
                                        getV().noDataFl.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (getV().goodsItemAdapterWuYouFQdkOp == null) {
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

    public void productClick(WuYouFQdkOpGoodsModel model) {
            phone = SharedPreferencesWuYouFQdkOpUtilis.getStringFromPref("phone");
            ApiWuYouFQdkOp.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespWuYouFQdkOpModel>getApiTransformer())
                    .compose(XApi.<BaseRespWuYouFQdkOpModel>getScheduler())
                    .compose(getV().<BaseRespWuYouFQdkOpModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespWuYouFQdkOpModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticUtilWuYouFQdkOp.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespWuYouFQdkOpModel gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
    }

}
