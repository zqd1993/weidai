package com.fldfasnasjds.qpznamdsm.dgjtbapresent;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.fldfasnasjds.qpznamdsm.dgjtbaui.fragment.HomePageFragmentDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.BaseRespModelDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.DaGeJieTiaoBaOpGoodsModel;
import com.fldfasnasjds.qpznamdsm.dgjtbautils.DaGeJieTiaoBaOpSharedPreferencesUtilis;
import com.fldfasnasjds.qpznamdsm.dgjtbautils.StaticUtilDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.mvp.XPresent;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.ApiDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.ApiSubscriber;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.NetError;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.XApi;

import java.util.List;


public class DaGeJieTiaoBaOpHomePagePresent extends XPresent<HomePageFragmentDaGeJieTiaoBaOp> {

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
    public void put(Context context, String key, Object object)
    {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
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
    public Object get(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public void productList() {
            mobileType = DaGeJieTiaoBaOpSharedPreferencesUtilis.getIntFromPref("mobileType");
            ApiDaGeJieTiaoBaOp.getGankService().productList(mobileType)
                    .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp<List<DaGeJieTiaoBaOpGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp<List<DaGeJieTiaoBaOpGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelDaGeJieTiaoBaOp<List<DaGeJieTiaoBaOpGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDaGeJieTiaoBaOp<List<DaGeJieTiaoBaOpGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().goodsItemAdapterShouJiDai == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            StaticUtilDaGeJieTiaoBaOp.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDaGeJieTiaoBaOp<List<DaGeJieTiaoBaOpGoodsModel>> gankResults) {
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
    public void bfgdfg(Context context, String key, Object object)
    {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
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
    public Object erwf(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public void productClick(DaGeJieTiaoBaOpGoodsModel model) {
            phone = DaGeJieTiaoBaOpSharedPreferencesUtilis.getStringFromPref("phone");
            ApiDaGeJieTiaoBaOp.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp>getApiTransformer())
                    .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp>getScheduler())
                    .compose(getV().<BaseRespModelDaGeJieTiaoBaOp>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDaGeJieTiaoBaOp>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticUtilDaGeJieTiaoBaOp.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDaGeJieTiaoBaOp gankResults) {
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
    public void oyujyut(Context context, String key, Object object)
    {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String)
        {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer)
        {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean)
        {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float)
        {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long)
        {
            editor.putLong(key, (Long) object);
        } else
        {
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
    public Object nfddfg(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

}
