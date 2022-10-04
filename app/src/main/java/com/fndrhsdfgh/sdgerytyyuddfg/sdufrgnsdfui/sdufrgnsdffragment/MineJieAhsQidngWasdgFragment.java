package com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui.sdufrgnsdffragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fndrhsdfgh.sdgerytyyuddfg.R;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfadapter.JieAhsQidngWasdgMineAdapter;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui.JieAhsQidngWasdgWebViewActivity;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui.LoginJieAhsQidngWasdgActivity;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui.sdufrgnsdfactivity.AboutUsActivityJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui.sdufrgnsdfactivity.CancellationJieAhsQidngWasdgAccountActivity;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfui.sdufrgnsdfactivity.JieAhsQidngWasdgFeedBackActivity;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfwidget.JieAhsQidngWasdgNormalDialog;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.BaseRespModelJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.ConfigJieAhsQidngWasdgModel;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfmodel.MineItemModelJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.ApiSubscriber;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.NetError;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.XApi;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfutils.JieAhsQidngWasdgSharedPreferencesUtilis;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfutils.JieAhsQidngWasdgToastUtil;
import com.fndrhsdfgh.sdgerytyyuddfg.sdufrgnsdfnet.ApiJieAhsQidngWasdg;
import com.fndrhsdfgh.sdgerytyyuddfg.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.fndrhsdfgh.sdgerytyyuddfg.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineJieAhsQidngWasdgFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;
    @BindView(R.id.rvy_2)
    RecyclerView rvy2;

    private JieAhsQidngWasdgMineAdapter jieAhsQidngWasdgMineAdapter, jieAhsQidngWasdgMineAdapter1, jieAhsQidngWasdgMineAdapter2;
    private List<MineItemModelJieAhsQidngWasdg> list, list1, list2;
    private int[] imgRes = {R.drawable.vcxzrt, R.drawable.qwevdagg, R.drawable.luitfs, R.drawable.luitity,
            R.drawable.zxcsrwe, R.drawable.retbs, R.drawable.pergad, R.drawable.mmdghf};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "个性化推荐", "投诉邮箱", "注销账户", "退出登录"};
    private Bundle bundle;
    private JieAhsQidngWasdgNormalDialog jieAhsQidngWasdgNormalDialog;
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
        mailStr = JieAhsQidngWasdgSharedPreferencesUtilis.getStringFromPref("APP_MAIL");
        phone = JieAhsQidngWasdgSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            MineItemModelJieAhsQidngWasdg model = new MineItemModelJieAhsQidngWasdg();
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
        return R.layout.fragment_mine_sdfrt_dfjret_kyihk_etfh;
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
        if (jieAhsQidngWasdgMineAdapter == null) {
            jieAhsQidngWasdgMineAdapter = new JieAhsQidngWasdgMineAdapter(getActivity());
            jieAhsQidngWasdgMineAdapter.setData(list);
            jieAhsQidngWasdgMineAdapter.setHasStableIds(true);
            jieAhsQidngWasdgMineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelJieAhsQidngWasdg, JieAhsQidngWasdgMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelJieAhsQidngWasdg model, int tag, JieAhsQidngWasdgMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiJieAhsQidngWasdg.PRIVACY_POLICY);
                            Router.newIntent(getActivity())
                                    .to(JieAhsQidngWasdgWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiJieAhsQidngWasdg.USER_SERVICE_AGREEMENT);
                            Router.newIntent(getActivity())
                                    .to(JieAhsQidngWasdgWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(JieAhsQidngWasdgFeedBackActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(jieAhsQidngWasdgMineAdapter);
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
        if (jieAhsQidngWasdgMineAdapter1 == null) {
            jieAhsQidngWasdgMineAdapter1 = new JieAhsQidngWasdgMineAdapter(getActivity());
            jieAhsQidngWasdgMineAdapter1.setData(list1);
            jieAhsQidngWasdgMineAdapter1.setHasStableIds(true);
            jieAhsQidngWasdgMineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModelJieAhsQidngWasdg, JieAhsQidngWasdgMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelJieAhsQidngWasdg model, int tag, JieAhsQidngWasdgMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivityJieAhsQidngWasdg.class)
                                    .launch();
                            break;
                        case 1:
                            jieAhsQidngWasdgNormalDialog = new JieAhsQidngWasdgNormalDialog(getActivity());
                            jieAhsQidngWasdgNormalDialog.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        JieAhsQidngWasdgToastUtil.showShort("开启成功");
                                        jieAhsQidngWasdgNormalDialog.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        JieAhsQidngWasdgToastUtil.showShort("关闭成功");
                                        jieAhsQidngWasdgNormalDialog.dismiss();
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
            rvy1.setAdapter(jieAhsQidngWasdgMineAdapter1);
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
        if (jieAhsQidngWasdgMineAdapter2 == null) {
            jieAhsQidngWasdgMineAdapter2 = new JieAhsQidngWasdgMineAdapter(getActivity());
            jieAhsQidngWasdgMineAdapter2.setData(list2);
            jieAhsQidngWasdgMineAdapter2.setHasStableIds(true);
            jieAhsQidngWasdgMineAdapter2.setRecItemClick(new RecyclerItemCallback<MineItemModelJieAhsQidngWasdg, JieAhsQidngWasdgMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelJieAhsQidngWasdg model, int tag, JieAhsQidngWasdgMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(CancellationJieAhsQidngWasdgAccountActivity.class)
                                    .launch();
                            break;
                        case 1:
                            jieAhsQidngWasdgNormalDialog = new JieAhsQidngWasdgNormalDialog(getActivity());
                            jieAhsQidngWasdgNormalDialog.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        jieAhsQidngWasdgNormalDialog.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        jieAhsQidngWasdgNormalDialog.dismiss();
                                        JieAhsQidngWasdgSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                                        Router.newIntent(getActivity())
                                                .to(LoginJieAhsQidngWasdgActivity.class)
                                                .launch();
                                        getActivity().finish();
                                    }).show();
                            break;
                    }
                }
            });
            rvy2.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy2.setHasFixedSize(true);
            rvy2.setAdapter(jieAhsQidngWasdgMineAdapter2);
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
        if (jieAhsQidngWasdgNormalDialog != null) {
            jieAhsQidngWasdgNormalDialog.dismiss();
            jieAhsQidngWasdgNormalDialog = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
        ApiJieAhsQidngWasdg.getGankService().getGankData()
                .compose(XApi.<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>>getScheduler())
                .compose(this.<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespModelJieAhsQidngWasdg<ConfigJieAhsQidngWasdgModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                mailStr = gankResults.getData().getAppMail();
                                JieAhsQidngWasdgSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                jieAhsQidngWasdgNormalDialog = new JieAhsQidngWasdgNormalDialog(getActivity());
                                jieAhsQidngWasdgNormalDialog.setTitle("温馨提示")
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
