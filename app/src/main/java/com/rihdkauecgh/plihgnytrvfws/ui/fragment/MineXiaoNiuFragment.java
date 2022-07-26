package com.rihdkauecgh.plihgnytrvfws.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.adap.XiaoNiuMineAdapter;
import com.rihdkauecgh.plihgnytrvfws.models.BaseRespXiaoNiuModel;
import com.rihdkauecgh.plihgnytrvfws.models.ConfigModelXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.models.MineItemXiaoNiuModel;
import com.rihdkauecgh.plihgnytrvfws.http.ApiSubscriber;
import com.rihdkauecgh.plihgnytrvfws.http.NetError;
import com.rihdkauecgh.plihgnytrvfws.http.XApi;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.LoginXiaoNiuActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.XiaoNiuWebViewActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.activity.AboutUsActivityXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.ui.activity.CancellationXiaoNiuAccountActivity;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesXiaoNiuUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StaticUtilXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.utils.ToasXiaoNiutUtil;
import com.rihdkauecgh.plihgnytrvfws.http.ApiXiaoNiu;
import com.rihdkauecgh.plihgnytrvfws.router.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import com.rihdkauecgh.plihgnytrvfws.widget.NormalXiaoNiuDialog;
import com.rihdkauecgh.plihgnytrvfws.mvp.XFragment;
import com.rihdkauecgh.plihgnytrvfws.ui.activity.XiaoNiuFeedBackActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineXiaoNiuFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private XiaoNiuMineAdapter mineAdapter;
    private List<MineItemXiaoNiuModel> list;
    private int[] imgRes = {R.drawable.jrhdfhg, R.drawable.bfggtrert, R.drawable.refghns, R.drawable.qfdfbdf,
            R.drawable.wvdgd, R.drawable.mgbdfba, R.drawable.erhfxgzhgh, R.drawable.lfjsfhzd};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "个性化推荐", "投诉邮箱", "注销账户", "退出登录"};
    private Bundle bundle;
    private NormalXiaoNiuDialog normalXiaoNiuDialog;
    private String mailStr = "", phone = "";

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T changeGsonToBean(String gsonString, Class<T> cls) {
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
    public <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: " + e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        mailStr = SharedPreferencesXiaoNiuUtilis.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesXiaoNiuUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            MineItemXiaoNiuModel model = new MineItemXiaoNiuModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_xiaoniu_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineAdapter == null) {
            mineAdapter = new XiaoNiuMineAdapter(getActivity());
            mineAdapter.setData(list);
            mineAdapter.setHasStableIds(true);
            mineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemXiaoNiuModel, XiaoNiuMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemXiaoNiuModel model, int tag, XiaoNiuMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiXiaoNiu.PRIVACY_POLICY);
                            StaticUtilXiaoNiu.getValue((XActivity) getActivity(), XiaoNiuWebViewActivity.class, bundle);
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiXiaoNiu.USER_SERVICE_AGREEMENT);
                            StaticUtilXiaoNiu.getValue((XActivity) getActivity(), XiaoNiuWebViewActivity.class, bundle);
                            break;
                        case 2:
                            StaticUtilXiaoNiu.getValue((XActivity) getActivity(), XiaoNiuFeedBackActivity.class, null);
                            break;
                        case 3:
                            StaticUtilXiaoNiu.getValue((XActivity) getActivity(), AboutUsActivityXiaoNiu.class, null);
                            break;
                        case 4:
                            normalXiaoNiuDialog = new NormalXiaoNiuDialog(getActivity());
                            normalXiaoNiuDialog.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        ToasXiaoNiutUtil.showShort("开启成功");
                                        normalXiaoNiuDialog.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        ToasXiaoNiutUtil.showShort("关闭成功");
                                        normalXiaoNiuDialog.dismiss();
                                    }).show();
                            break;
                        case 5:
                            getGankData();
                            break;
                        case 6:
                            StaticUtilXiaoNiu.getValue((XActivity) getActivity(), CancellationXiaoNiuAccountActivity.class, null);
                            break;
                        case 7:
                            normalXiaoNiuDialog = new NormalXiaoNiuDialog(getActivity());
                            normalXiaoNiuDialog.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalXiaoNiuDialog.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        normalXiaoNiuDialog.dismiss();
                                        SharedPreferencesXiaoNiuUtilis.saveStringIntoPref("phone", "");
                                        StaticUtilXiaoNiu.getValue((XActivity) getActivity(), LoginXiaoNiuActivity.class, null, true);
                                    }).show();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter);
        }
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T matewtfggh(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>o
    public <T> List<T> yuikhjkfh(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: " + e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> urtysdfhg(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> bfsgsdfg(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void onDestroy() {
        if (normalXiaoNiuDialog != null) {
            normalXiaoNiuDialog.dismiss();
            normalXiaoNiuDialog = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
            ApiXiaoNiu.getGankService().getGankData()
                    .compose(XApi.<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>>getApiTransformer())
                    .compose(XApi.<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>>getScheduler())
                    .compose(this.<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespXiaoNiuModel<ConfigModelXiaoNiu>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseRespXiaoNiuModel<ConfigModelXiaoNiu> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    mailStr = gankResults.getData().getAppMail();
                                    SharedPreferencesXiaoNiuUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                    normalXiaoNiuDialog = new NormalXiaoNiuDialog(getActivity());
                                    normalXiaoNiuDialog.setTitle("温馨提示")
                                            .setContent(mailStr)
                                            .showOnlyBtn().show();
                                }
                            }
                        }
                    });
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public <T> T itydxfh(String gsonString, Class<T> cls) {
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
    public <T> List<T> lkyjghj(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<T>集合异常: " + e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public <T> List<Map<String, T>> weqrdfgdfg(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转List<Map<String, T>集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public <T> Map<String, T> nsgdaf(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("GoodsItemAdapterXiaoNiu", "gson转Map集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
}
