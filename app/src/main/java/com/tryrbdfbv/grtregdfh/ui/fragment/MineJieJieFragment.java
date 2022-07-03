package com.tryrbdfbv.grtregdfh.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tryrbdfbv.grtregdfh.R;
import com.tryrbdfbv.grtregdfh.adapter.MineJieJieAdapter;
import com.tryrbdfbv.grtregdfh.model.BaseRespModelJieJie;
import com.tryrbdfbv.grtregdfh.model.CompanyJieJieInfoModel;
import com.tryrbdfbv.grtregdfh.model.MineItemModelJieJie;
import com.tryrbdfbv.grtregdfh.net.ApiJieJie;
import com.tryrbdfbv.grtregdfh.net.ApiSubscriber;
import com.tryrbdfbv.grtregdfh.net.NetError;
import com.tryrbdfbv.grtregdfh.net.XApi;
import com.tryrbdfbv.grtregdfh.ui.WebViewJieJieActivity;
import com.tryrbdfbv.grtregdfh.ui.activity.AboutUsJieJieActivity;
import com.tryrbdfbv.grtregdfh.ui.activity.CancellationAccountActivityJieJie;
import com.tryrbdfbv.grtregdfh.ui.activity.SettingJieJieActivity;
import com.tryrbdfbv.grtregdfh.utils.SharedPreferencesUtilisJieJie;
import com.tryrbdfbv.grtregdfh.utils.ToastUtilJieJie;
import com.tryrbdfbv.grtregdfh.router.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import com.tryrbdfbv.grtregdfh.widget.NormalJieJieDialog;
import com.tryrbdfbv.grtregdfh.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineJieJieFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MineJieJieAdapter mineJieJieAdapter;
    private List<MineItemModelJieJie> list;
    private int[] imgRes = {R.drawable.dxgzhadg, R.drawable.ewrgbfgjnhsr, R.drawable.awehftgh,
            R.drawable.ergbfgjng, R.drawable.mfgdrt, R.drawable.nmsfhdzt};
    private String[] tvRes = {"关于我们", "隐私协议", "注册协议", "投诉邮箱", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalJieJieDialog normalJieJieDialog;
    private String mailStr = "", phone = "";

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
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
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> parseJsonToList(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        phone = SharedPreferencesUtilisJieJie.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 6; i++) {
            MineItemModelJieJie model = new MineItemModelJieJie();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_jiejie;
    }

    @Override
    public Object newP() {
        return null;
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> kjhsdfh(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> rehsdfh(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> nfgsdffgsd(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    private void initAdapter() {
        if (mineJieJieAdapter == null) {
            mineJieJieAdapter = new MineJieJieAdapter(getActivity());
            mineJieJieAdapter.setData(list);
            mineJieJieAdapter.setHasStableIds(true);
            mineJieJieAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelJieJie, MineJieJieAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelJieJie model, int tag, MineJieJieAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutUsJieJieActivity.class)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiJieJie.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewJieJieActivity.class)
                                    .data(bundle)
                                    .launch();
                        case 2:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiJieJie.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewJieJieActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 3:
                            getCompanyInfo();
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(SettingJieJieActivity.class)
                                    .launch();
                            break;
                        case 5:
                            Router.newIntent(getActivity())
                                    .to(CancellationAccountActivityJieJie.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineJieJieAdapter);
        }
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> kljhfgh(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> drfcvnfg(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> bsdfgser(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisJieJie.getStringFromPref("API_BASE_URL"))) {
            ApiJieJie.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelJieJie<CompanyJieJieInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieJie<CompanyJieJieInfoModel>>getScheduler())
                    .compose(this.<BaseRespModelJieJie<CompanyJieJieInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieJie<CompanyJieJieInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelJieJie<CompanyJieJieInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    SharedPreferencesUtilisJieJie.saveStringIntoPref("APP_MAIL", mailStr);
                                    normalJieJieDialog = new NormalJieJieDialog(getActivity());
                                    normalJieJieDialog.setTitle("投诉邮箱")
                                            .setContent(mailStr)
                                            .setCancelText("复制")
                                            .setLeftListener(v -> {
                                                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                                ClipData clipData = ClipData.newPlainText(null, mailStr);
                                                clipboard.setPrimaryClip(clipData);
                                                ToastUtilJieJie.showShort("复制成功");
                                                normalJieJieDialog.dismiss();
                                            })
                                            .setConfirmText("取消")
                                            .setRightListener(v -> {
                                                normalJieJieDialog.dismiss();
                                            }).show();
                                }
                            }
                        }
                    });
        }
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> mgjdfgh(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转List<Map<String, T>集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> ertbfds(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d("changeGsonToListMaps", "gson转Map集合异常: "+e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> nsdgbvb(String json, int  type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d("changeGsonToListMaps", "gson转实体类异常: "+e.getMessage());
        }
        return list;
    }

    @Override
    public void onDestroy() {
        if (normalJieJieDialog != null) {
            normalJieJieDialog.dismiss();
            normalJieJieDialog = null;
        }
        super.onDestroy();
    }
}
