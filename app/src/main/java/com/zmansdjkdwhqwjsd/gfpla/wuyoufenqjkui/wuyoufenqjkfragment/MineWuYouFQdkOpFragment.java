package com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.wuyoufenqjkfragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zmansdjkdwhqwjsd.gfpla.R;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkadap.WuYouFQdkOpMineAdapter;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.BaseRespWuYouFQdkOpModel;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.ConfigModelWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkmodels.MineItemWuYouFQdkOpModel;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.ApiSubscriber;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.NetError;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.XApi;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.LoginWuYouFQdkOpActivity;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.WuYouFQdkOpWebViewActivity;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.wuyoufenqjkactivity.AboutUsActivityWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.wuyoufenqjkactivity.CancellationWuYouFQdkOpAccountActivity;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.SharedPreferencesWuYouFQdkOpUtilis;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkutils.ToasWuYouFQdkOptUtil;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkhttp.ApiWuYouFQdkOp;
import com.zmansdjkdwhqwjsd.gfpla.router.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkwidget.NormalWuYouFQdkOpDialog;
import com.zmansdjkdwhqwjsd.gfpla.mvp.XFragment;
import com.zmansdjkdwhqwjsd.gfpla.wuyoufenqjkui.wuyoufenqjkactivity.WuYouFQdkOpFeedBackActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineWuYouFQdkOpFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private WuYouFQdkOpMineAdapter mineAdapter;
    private List<MineItemWuYouFQdkOpModel> list;
    private int[] imgRes = {R.drawable.jrhdfhg, R.drawable.bfggtrert, R.drawable.refghns, R.drawable.qfdfbdf,
            R.drawable.wvdgd, R.drawable.mgbdfba, R.drawable.erhfxgzhgh, R.drawable.lfjsfhzd};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "个性化推荐", "投诉邮箱", "注销账户", "退出登录"};
    private Bundle bundle;
    private NormalWuYouFQdkOpDialog normalWuYouFQdkOpDialog;
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
        mailStr = SharedPreferencesWuYouFQdkOpUtilis.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesWuYouFQdkOpUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            MineItemWuYouFQdkOpModel model = new MineItemWuYouFQdkOpModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wu_you_fen_qi_jk_op_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineAdapter == null) {
            mineAdapter = new WuYouFQdkOpMineAdapter(getActivity());
            mineAdapter.setData(list);
            mineAdapter.setHasStableIds(true);
            mineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemWuYouFQdkOpModel, WuYouFQdkOpMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemWuYouFQdkOpModel model, int tag, WuYouFQdkOpMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiWuYouFQdkOp.PRIVACY_POLICY);
                            Router.newIntent(getActivity())
                                    .to(WuYouFQdkOpWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiWuYouFQdkOp.USER_SERVICE_AGREEMENT);
                            Router.newIntent(getActivity())
                                    .to(WuYouFQdkOpWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(WuYouFQdkOpFeedBackActivity.class)
                                    .launch();
                            break;
                        case 3:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivityWuYouFQdkOp.class)
                                    .launch();
                            break;
                        case 4:
                            normalWuYouFQdkOpDialog = new NormalWuYouFQdkOpDialog(getActivity());
                            normalWuYouFQdkOpDialog.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        ToasWuYouFQdkOptUtil.showShort("开启成功");
                                        normalWuYouFQdkOpDialog.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        ToasWuYouFQdkOptUtil.showShort("关闭成功");
                                        normalWuYouFQdkOpDialog.dismiss();
                                    }).show();
                            break;
                        case 5:
                            getGankData();
                            break;
                        case 6:
                            Router.newIntent(getActivity())
                                    .to(CancellationWuYouFQdkOpAccountActivity.class)
                                    .launch();
                            break;
                        case 7:
                            normalWuYouFQdkOpDialog = new NormalWuYouFQdkOpDialog(getActivity());
                            normalWuYouFQdkOpDialog.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalWuYouFQdkOpDialog.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        normalWuYouFQdkOpDialog.dismiss();
                                        SharedPreferencesWuYouFQdkOpUtilis.saveStringIntoPref("phone", "");
                                        Router.newIntent(getActivity())
                                                .to(LoginWuYouFQdkOpActivity.class)
                                                .launch();
                                        getActivity().finish();
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
        if (normalWuYouFQdkOpDialog != null) {
            normalWuYouFQdkOpDialog.dismiss();
            normalWuYouFQdkOpDialog = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
        ApiWuYouFQdkOp.getGankService().getGankData()
                .compose(XApi.<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>>getApiTransformer())
                .compose(XApi.<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>>getScheduler())
                .compose(this.<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespWuYouFQdkOpModel<ConfigModelWuYouFQdkOp> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                mailStr = gankResults.getData().getAppMail();
                                SharedPreferencesWuYouFQdkOpUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                normalWuYouFQdkOpDialog = new NormalWuYouFQdkOpDialog(getActivity());
                                normalWuYouFQdkOpDialog.setTitle("温馨提示")
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
