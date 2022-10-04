package com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfpresent;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui.sdufrgnsdffragment.HomePageFragmentJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.BaseRespModelJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.JieAhsQidngWasdgGoodsModel;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfutils.JieAhsQidngWasdgSharedPreferencesUtilis;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfutils.StaticUtilJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.mvp.XPresent;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.ApiJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.ApiSubscriber;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.NetError;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.XApi;

import java.util.List;


public class JieAhsQidngWasdgHomePagePresent extends XPresent<HomePageFragmentJieAhsQidngWasdg> {

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
            mobileType = JieAhsQidngWasdgSharedPreferencesUtilis.getIntFromPref("mobileType");
            ApiJieAhsQidngWasdg.getGankService().productList(mobileType)
                    .compose(XApi.<BaseRespModelJieAhsQidngWasdg<List<JieAhsQidngWasdgGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieAhsQidngWasdg<List<JieAhsQidngWasdgGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelJieAhsQidngWasdg<List<JieAhsQidngWasdgGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieAhsQidngWasdg<List<JieAhsQidngWasdgGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().goodsItemAdapterShouJiDai == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            StaticUtilJieAhsQidngWasdg.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieAhsQidngWasdg<List<JieAhsQidngWasdgGoodsModel>> gankResults) {
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

    public void productClick(JieAhsQidngWasdgGoodsModel model) {
            phone = JieAhsQidngWasdgSharedPreferencesUtilis.getStringFromPref("phone");
            ApiJieAhsQidngWasdg.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModelJieAhsQidngWasdg>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieAhsQidngWasdg>getScheduler())
                    .compose(getV().<BaseRespModelJieAhsQidngWasdg>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieAhsQidngWasdg>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticUtilJieAhsQidngWasdg.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieAhsQidngWasdg gankResults) {
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
