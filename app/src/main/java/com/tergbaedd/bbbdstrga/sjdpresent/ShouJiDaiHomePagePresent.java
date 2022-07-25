package com.tergbaedd.bbbdstrga.sjdpresent;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;

import com.tergbaedd.bbbdstrga.sjdui.fragment.HomePageFragmentShouJiDai;
import com.tergbaedd.bbbdstrga.sldmodel.BaseRespModelShouJiDai;
import com.tergbaedd.bbbdstrga.sldmodel.ShouJiDaiGoodsModel;
import com.tergbaedd.bbbdstrga.sjdutils.ShouJiDaiSharedPreferencesUtilis;
import com.tergbaedd.bbbdstrga.sjdutils.StaticUtilShouJiDai;
import com.tergbaedd.bbbdstrga.mvp.XPresent;
import com.tergbaedd.bbbdstrga.sjdnet.ApiShouJiDai;
import com.tergbaedd.bbbdstrga.sjdnet.ApiSubscriber;
import com.tergbaedd.bbbdstrga.sjdnet.NetError;
import com.tergbaedd.bbbdstrga.sjdnet.XApi;

import java.util.List;


public class ShouJiDaiHomePagePresent extends XPresent<HomePageFragmentShouJiDai> {

    private int mobileType;

    private String phone;

    /**
     * 保存在手机里面的文件名
     */
    public final String FILE_NAME = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public void productList() {
        mobileType = ShouJiDaiSharedPreferencesUtilis.getIntFromPref("mobileType");
        ApiShouJiDai.getGankService().productList(mobileType)
                .compose(XApi.<BaseRespModelShouJiDai<List<ShouJiDaiGoodsModel>>>getApiTransformer())
                .compose(XApi.<BaseRespModelShouJiDai<List<ShouJiDaiGoodsModel>>>getScheduler())
                .compose(getV().<BaseRespModelShouJiDai<List<ShouJiDaiGoodsModel>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelShouJiDai<List<ShouJiDaiGoodsModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().swipeRefreshLayout.setRefreshing(false);
                        if (getV().goodsItemAdapterShouJiDai == null) {
                            getV().noDataFl.setVisibility(View.VISIBLE);
                        }
                        StaticUtilShouJiDai.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelShouJiDai<List<ShouJiDaiGoodsModel>> gankResults) {
                        getV().swipeRefreshLayout.setRefreshing(false);
                        if (gankResults != null) {
                            if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                    getV().setModel(gankResults.getData().get(0));
                                    getV().initGoodsItemAdapter(gankResults.getData());
                                } else {
                                    if (getV().goodsItemAdapterShouJiDai == null) {
                                        getV().noDataFl.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (getV().goodsItemAdapterShouJiDai == null) {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (getV().goodsItemAdapterShouJiDai == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void bfgdfg(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object erwf(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public void productClick(ShouJiDaiGoodsModel model) {
        phone = ShouJiDaiSharedPreferencesUtilis.getStringFromPref("phone");
        ApiShouJiDai.getGankService().productClick(model.getId(), phone)
                .compose(XApi.<BaseRespModelShouJiDai>getApiTransformer())
                .compose(XApi.<BaseRespModelShouJiDai>getScheduler())
                .compose(getV().<BaseRespModelShouJiDai>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelShouJiDai>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().jumpWebActivity(model);
                        StaticUtilShouJiDai.showError(getV().getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseRespModelShouJiDai gankResults) {
                        getV().jumpWebActivity(model);
                    }
                });
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void oyujyut(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public Object nfddfg(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

}
