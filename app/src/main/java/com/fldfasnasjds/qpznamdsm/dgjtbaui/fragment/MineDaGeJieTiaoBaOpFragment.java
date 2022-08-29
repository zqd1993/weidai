package com.fldfasnasjds.qpznamdsm.dgjtbaui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fldfasnasjds.qpznamdsm.R;
import com.fldfasnasjds.qpznamdsm.dgjtbaadapter.DaGeJieTiaoBaOpMineAdapter;
import com.fldfasnasjds.qpznamdsm.dgjtbaui.DaGeJieTiaoBaOpWebViewActivity;
import com.fldfasnasjds.qpznamdsm.dgjtbaui.LoginDaGeJieTiaoBaOpActivity;
import com.fldfasnasjds.qpznamdsm.dgjtbaui.activity.AboutUsActivityDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbaui.activity.CancellationDaGeJieTiaoBaOpAccountActivity;
import com.fldfasnasjds.qpznamdsm.dgjtbaui.activity.DaGeJieTiaoBaOpFeedBackActivity;
import com.fldfasnasjds.qpznamdsm.dgjtbawidget.DaGeJieTiaoBaOpNormalDialog;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.BaseRespModelDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.ConfigDaGeJieTiaoBaOpModel;
import com.fldfasnasjds.qpznamdsm.dgjtbamodel.MineItemModelDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.ApiSubscriber;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.NetError;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.XApi;
import com.fldfasnasjds.qpznamdsm.dgjtbautils.DaGeJieTiaoBaOpSharedPreferencesUtilis;
import com.fldfasnasjds.qpznamdsm.dgjtbautils.DaGeJieTiaoBaOpToastUtil;
import com.fldfasnasjds.qpznamdsm.dgjtbanet.ApiDaGeJieTiaoBaOp;
import com.fldfasnasjds.qpznamdsm.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.fldfasnasjds.qpznamdsm.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineDaGeJieTiaoBaOpFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;
    @BindView(R.id.rvy_2)
    RecyclerView rvy2;

    private DaGeJieTiaoBaOpMineAdapter daGeJieTiaoBaOpMineAdapter, daGeJieTiaoBaOpMineAdapter1, daGeJieTiaoBaOpMineAdapter2;
    private List<MineItemModelDaGeJieTiaoBaOp> list, list1, list2;
    private int[] imgRes = {R.drawable.vcxzrt, R.drawable.qwevdagg, R.drawable.luitfs, R.drawable.luitity,
            R.drawable.zxcsrwe, R.drawable.retbs, R.drawable.pergad, R.drawable.mmdghf};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "个性化推荐", "投诉邮箱", "注销账户", "退出登录"};
    private Bundle bundle;
    private DaGeJieTiaoBaOpNormalDialog daGeJieTiaoBaOpNormalDialog;
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
        mailStr = DaGeJieTiaoBaOpSharedPreferencesUtilis.getStringFromPref("APP_MAIL");
        phone = DaGeJieTiaoBaOpSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            MineItemModelDaGeJieTiaoBaOp model = new MineItemModelDaGeJieTiaoBaOp();
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
        return R.layout.fragment_mine_da_ge_jie_tiao_ba_op;
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
        if (daGeJieTiaoBaOpMineAdapter == null) {
            daGeJieTiaoBaOpMineAdapter = new DaGeJieTiaoBaOpMineAdapter(getActivity());
            daGeJieTiaoBaOpMineAdapter.setData(list);
            daGeJieTiaoBaOpMineAdapter.setHasStableIds(true);
            daGeJieTiaoBaOpMineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelDaGeJieTiaoBaOp, DaGeJieTiaoBaOpMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelDaGeJieTiaoBaOp model, int tag, DaGeJieTiaoBaOpMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiDaGeJieTiaoBaOp.PRIVACY_POLICY);
                            Router.newIntent(getActivity())
                                    .to(DaGeJieTiaoBaOpWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiDaGeJieTiaoBaOp.USER_SERVICE_AGREEMENT);
                            Router.newIntent(getActivity())
                                    .to(DaGeJieTiaoBaOpWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(DaGeJieTiaoBaOpFeedBackActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(daGeJieTiaoBaOpMineAdapter);
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
        if (daGeJieTiaoBaOpMineAdapter1 == null) {
            daGeJieTiaoBaOpMineAdapter1 = new DaGeJieTiaoBaOpMineAdapter(getActivity());
            daGeJieTiaoBaOpMineAdapter1.setData(list1);
            daGeJieTiaoBaOpMineAdapter1.setHasStableIds(true);
            daGeJieTiaoBaOpMineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModelDaGeJieTiaoBaOp, DaGeJieTiaoBaOpMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelDaGeJieTiaoBaOp model, int tag, DaGeJieTiaoBaOpMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivityDaGeJieTiaoBaOp.class)
                                    .launch();
                            break;
                        case 1:
                            daGeJieTiaoBaOpNormalDialog = new DaGeJieTiaoBaOpNormalDialog(getActivity());
                            daGeJieTiaoBaOpNormalDialog.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        DaGeJieTiaoBaOpToastUtil.showShort("开启成功");
                                        daGeJieTiaoBaOpNormalDialog.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        DaGeJieTiaoBaOpToastUtil.showShort("关闭成功");
                                        daGeJieTiaoBaOpNormalDialog.dismiss();
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
            rvy1.setAdapter(daGeJieTiaoBaOpMineAdapter1);
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
        if (daGeJieTiaoBaOpMineAdapter2 == null) {
            daGeJieTiaoBaOpMineAdapter2 = new DaGeJieTiaoBaOpMineAdapter(getActivity());
            daGeJieTiaoBaOpMineAdapter2.setData(list2);
            daGeJieTiaoBaOpMineAdapter2.setHasStableIds(true);
            daGeJieTiaoBaOpMineAdapter2.setRecItemClick(new RecyclerItemCallback<MineItemModelDaGeJieTiaoBaOp, DaGeJieTiaoBaOpMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelDaGeJieTiaoBaOp model, int tag, DaGeJieTiaoBaOpMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(CancellationDaGeJieTiaoBaOpAccountActivity.class)
                                    .launch();
                            break;
                        case 1:
                            daGeJieTiaoBaOpNormalDialog = new DaGeJieTiaoBaOpNormalDialog(getActivity());
                            daGeJieTiaoBaOpNormalDialog.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        daGeJieTiaoBaOpNormalDialog.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        daGeJieTiaoBaOpNormalDialog.dismiss();
                                        DaGeJieTiaoBaOpSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                                        Router.newIntent(getActivity())
                                                .to(LoginDaGeJieTiaoBaOpActivity.class)
                                                .launch();
                                        getActivity().finish();
                                    }).show();
                            break;
                    }
                }
            });
            rvy2.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy2.setHasFixedSize(true);
            rvy2.setAdapter(daGeJieTiaoBaOpMineAdapter2);
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
        if (daGeJieTiaoBaOpNormalDialog != null) {
            daGeJieTiaoBaOpNormalDialog.dismiss();
            daGeJieTiaoBaOpNormalDialog = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
        ApiDaGeJieTiaoBaOp.getGankService().getGankData()
                .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel>>getScheduler())
                .compose(this.<BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespModelDaGeJieTiaoBaOp<ConfigDaGeJieTiaoBaOpModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                mailStr = gankResults.getData().getAppMail();
                                DaGeJieTiaoBaOpSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                daGeJieTiaoBaOpNormalDialog = new DaGeJieTiaoBaOpNormalDialog(getActivity());
                                daGeJieTiaoBaOpNormalDialog.setTitle("温馨提示")
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
