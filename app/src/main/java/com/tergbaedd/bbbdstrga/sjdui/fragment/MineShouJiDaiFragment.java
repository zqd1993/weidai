package com.tergbaedd.bbbdstrga.sjdui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tergbaedd.bbbdstrga.R;
import com.tergbaedd.bbbdstrga.mvp.XActivity;
import com.tergbaedd.bbbdstrga.sjdadapter.ShouJiDaiMineAdapter;
import com.tergbaedd.bbbdstrga.sjdui.LoginShouJiDaiActivity;
import com.tergbaedd.bbbdstrga.sjdui.activity.CancellationShouJiDaiAccountActivity;
import com.tergbaedd.bbbdstrga.sjdui.activity.ShouJiDaiFeedBackActivity;
import com.tergbaedd.bbbdstrga.sjdutils.StaticUtilShouJiDai;
import com.tergbaedd.bbbdstrga.sjdwidget.ShouJiDaiNormalDialog;
import com.tergbaedd.bbbdstrga.sldmodel.BaseRespModelShouJiDai;
import com.tergbaedd.bbbdstrga.sldmodel.ConfigShouJiDaiModel;
import com.tergbaedd.bbbdstrga.sldmodel.MineItemModelShouJiDai;
import com.tergbaedd.bbbdstrga.sjdnet.ApiSubscriber;
import com.tergbaedd.bbbdstrga.sjdnet.NetError;
import com.tergbaedd.bbbdstrga.sjdnet.XApi;
import com.tergbaedd.bbbdstrga.sjdui.ShouJiDaiWebViewActivity;
import com.tergbaedd.bbbdstrga.sjdutils.ShouJiDaiSharedPreferencesUtilis;
import com.tergbaedd.bbbdstrga.sjdutils.ShouJiDaiToastUtil;
import com.tergbaedd.bbbdstrga.sjdnet.ApiShouJiDai;
import com.tergbaedd.bbbdstrga.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.tergbaedd.bbbdstrga.mvp.XFragment;
import com.tergbaedd.bbbdstrga.sjdui.activity.AboutUsActivityShouJiDai;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineShouJiDaiFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;
    @BindView(R.id.rvy_2)
    RecyclerView rvy2;

    private ShouJiDaiMineAdapter shouJiDaiMineAdapter, shouJiDaiMineAdapter1, shouJiDaiMineAdapter2;
    private List<MineItemModelShouJiDai> list, list1, list2;
    private int[] imgRes = {R.drawable.vcxzrt, R.drawable.qwevdagg, R.drawable.luitfs, R.drawable.luitity,
            R.drawable.zxcsrwe, R.drawable.retbs, R.drawable.pergad, R.drawable.mmdghf};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "个性化推荐", "联系客服", "注销账户", "退出登录"};
    private Bundle bundle;
    private ShouJiDaiNormalDialog shouJiDaiNormalDialog;
    private String mailStr = "", phone = "";

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

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        mailStr = ShouJiDaiSharedPreferencesUtilis.getStringFromPref("APP_MAIL");
        phone = ShouJiDaiSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            MineItemModelShouJiDai model = new MineItemModelShouJiDai();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            if (i < 3) {
                list.add(model);
            }
            if (i >= 3 && i < 6) {
                list1.add(model);
            }
            if (i >= 6 && i < 8) {
                list2.add(model);
            }
        }
        initAdapter();
        initAdapter1();
        initAdapter2();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_shou_ji_dai;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void nfgdf(Context context, String key, Object object) {

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
    public Object werf(Context context, String key, Object defaultObject) {
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

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (shouJiDaiMineAdapter == null) {
            shouJiDaiMineAdapter = new ShouJiDaiMineAdapter(getActivity());
            shouJiDaiMineAdapter.setData(list);
            shouJiDaiMineAdapter.setHasStableIds(true);
            shouJiDaiMineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelShouJiDai, ShouJiDaiMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelShouJiDai model, int tag, ShouJiDaiMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiShouJiDai.PRIVACY_POLICY);
                            StaticUtilShouJiDai.getValue((XActivity) getActivity(), ShouJiDaiWebViewActivity.class, bundle);
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiShouJiDai.USER_SERVICE_AGREEMENT);
                            StaticUtilShouJiDai.getValue((XActivity) getActivity(), ShouJiDaiWebViewActivity.class, bundle);
                            break;
                        case 2:
                            StaticUtilShouJiDai.getValue((XActivity) getActivity(), ShouJiDaiFeedBackActivity.class, null);
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(shouJiDaiMineAdapter);
        }
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void ewfsdf(Context context, String key, Object object) {

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
    public Object bdfgdfg(Context context, String key, Object defaultObject) {
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

    private void initAdapter1() {
        if (shouJiDaiMineAdapter1 == null) {
            shouJiDaiMineAdapter1 = new ShouJiDaiMineAdapter(getActivity());
            shouJiDaiMineAdapter1.setData(list1);
            shouJiDaiMineAdapter1.setHasStableIds(true);
            shouJiDaiMineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModelShouJiDai, ShouJiDaiMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelShouJiDai model, int tag, ShouJiDaiMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            StaticUtilShouJiDai.getValue((XActivity) getActivity(), AboutUsActivityShouJiDai.class, null);
                            break;
                        case 1:
                            shouJiDaiNormalDialog = new ShouJiDaiNormalDialog(getActivity());
                            shouJiDaiNormalDialog.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        ShouJiDaiToastUtil.showShort("开启成功");
                                        shouJiDaiNormalDialog.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        ShouJiDaiToastUtil.showShort("关闭成功");
                                        shouJiDaiNormalDialog.dismiss();
                                    }).show();
                            break;
                        case 2:
                            getGankData();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(shouJiDaiMineAdapter1);
        }
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void wecvsd(Context context, String key, Object object) {

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
    public Object rtfgd(Context context, String key, Object defaultObject) {
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

    private void initAdapter2() {
        if (shouJiDaiMineAdapter2 == null) {
            shouJiDaiMineAdapter2 = new ShouJiDaiMineAdapter(getActivity());
            shouJiDaiMineAdapter2.setData(list2);
            shouJiDaiMineAdapter2.setHasStableIds(true);
            shouJiDaiMineAdapter2.setRecItemClick(new RecyclerItemCallback<MineItemModelShouJiDai, ShouJiDaiMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelShouJiDai model, int tag, ShouJiDaiMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            StaticUtilShouJiDai.getValue((XActivity) getActivity(), CancellationShouJiDaiAccountActivity.class, null);
                            break;
                        case 1:
                            shouJiDaiNormalDialog = new ShouJiDaiNormalDialog(getActivity());
                            shouJiDaiNormalDialog.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        shouJiDaiNormalDialog.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        shouJiDaiNormalDialog.dismiss();
                                        ShouJiDaiSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                                        StaticUtilShouJiDai.getValue((XActivity) getActivity(), LoginShouJiDaiActivity.class, null, true);
                                    }).show();
                            break;
                    }
                }
            });
            rvy2.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy2.setHasFixedSize(true);
            rvy2.setAdapter(shouJiDaiMineAdapter2);
        }
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void esfdsf(Context context, String key, Object object) {

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
    public Object nfdbdf(Context context, String key, Object defaultObject) {
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

    @Override
    public void onDestroy() {
        if (shouJiDaiNormalDialog != null) {
            shouJiDaiNormalDialog.dismiss();
            shouJiDaiNormalDialog = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
        ApiShouJiDai.getGankService().getGankData()
                .compose(XApi.<BaseRespModelShouJiDai<ConfigShouJiDaiModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelShouJiDai<ConfigShouJiDaiModel>>getScheduler())
                .compose(this.<BaseRespModelShouJiDai<ConfigShouJiDaiModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelShouJiDai<ConfigShouJiDaiModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespModelShouJiDai<ConfigShouJiDaiModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                mailStr = gankResults.getData().getAppMail();
                                ShouJiDaiSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                shouJiDaiNormalDialog = new ShouJiDaiNormalDialog(getActivity());
                                shouJiDaiNormalDialog.setTitle("温馨提示")
                                        .setContent(mailStr)
                                        .showOnlyBtn().show();
                            }
                        }
                    }
                });
    }

    /**
     * 保存在手机里面的文件名
     */
    public final String oyujkgh = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public void wsdv(Context context, String key, Object object) {

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
    public Object ndfsdf(Context context, String key, Object defaultObject) {
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
