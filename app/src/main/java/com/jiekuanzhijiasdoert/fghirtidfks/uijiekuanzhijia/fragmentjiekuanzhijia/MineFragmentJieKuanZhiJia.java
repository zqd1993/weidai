package com.jiekuanzhijiasdoert.fghirtidfks.uijiekuanzhijia.fragmentjiekuanzhijia;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.jiekuanzhijiasdoert.fghirtidfks.R;
import com.jiekuanzhijiasdoert.fghirtidfks.adapterjiekuanzhijia.JieKuanZhiJiaMineAdapter;
import com.jiekuanzhijiasdoert.fghirtidfks.adapterjiekuanzhijia.JieKuanZhiJiaMineAdapter1;
import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.BaseRespModelJieKuanZhiJia;
import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.CompanyInfoModelJieKuanZhiJia;
import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.JieKuanZhiJiaGoodsModel;
import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.MineItemModelJieKuanZhiJia;
import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.JieKuanZhiJiaRequModel;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.JieKuanZhiJiaApi;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.ApiSubscriber;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.NetError;
import com.jiekuanzhijiasdoert.fghirtidfks.netjiekuanzhijia.XApi;
import com.jiekuanzhijiasdoert.fghirtidfks.uijiekuanzhijia.WebViewActivityJieKuanZhiJia;
import com.jiekuanzhijiasdoert.fghirtidfks.uijiekuanzhijia.activityjiekuanzhijia.JieKuanZhiJiaCancellationAccountActivity;
import com.jiekuanzhijiasdoert.fghirtidfks.uijiekuanzhijia.activityjiekuanzhijia.SettingJieKuanZhiJiaActivity;
import com.jiekuanzhijiasdoert.fghirtidfks.utilsjiekuanzhijia.JieKuanZhiJiaSharedPreferencesUtilis;
import com.jiekuanzhijiasdoert.fghirtidfks.utilsjiekuanzhijia.JieKuanZhiJiaStaticUtil;
import com.jiekuanzhijiasdoert.fghirtidfks.utilsjiekuanzhijia.JieKuanZhiJiaToastUtil;
import com.jiekuanzhijiasdoert.fghirtidfks.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.jiekuanzhijiasdoert.fghirtidfks.widgetjiekuanzhijia.JieKuanZhiJiaNormalDialog;
import com.jiekuanzhijiasdoert.fghirtidfks.mvp.XFragment;
import com.jiekuanzhijiasdoert.fghirtidfks.uijiekuanzhijia.activityjiekuanzhijia.JieKuanZhiJiaAboutUsActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import okhttp3.RequestBody;

public class MineFragmentJieKuanZhiJia extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mail_tv)
    TextView mail_tv;
    @BindView(R.id.mail_sl)
    View mail_sl;

    private JieKuanZhiJiaMineAdapter jieKuanZhiJiaMineAdapter;
    private JieKuanZhiJiaMineAdapter1 jieKuanZhiJiaMineAdapter1;
    private List<MineItemModelJieKuanZhiJia> list, list1;
    private int[] imgRes = {R.drawable.weryxfgjdr, R.drawable.lpyfukdsrtu, R.drawable.xcvbnfgj, R.drawable.gjdfuji, R.drawable.llyfuiodtyui};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "系统设置", "注销账户"};
    private Bundle bundle;
    private JieKuanZhiJiaNormalDialog jieKuanZhiJiaNormalDialog;
    private String mailStr = "", phone = "", token = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        getCompanyInfo();
        aindex();
        phone = JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            MineItemModelJieKuanZhiJia model = new MineItemModelJieKuanZhiJia();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            if (i < 2) {
                list.add(model);
            } else {
                list1.add(model);
            }
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
            aindex();
        });
        mail_sl.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            JieKuanZhiJiaToastUtil.showShort("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_jie_kuan_zhi_jia;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (jieKuanZhiJiaMineAdapter == null) {
            jieKuanZhiJiaMineAdapter = new JieKuanZhiJiaMineAdapter(getActivity());
            jieKuanZhiJiaMineAdapter.setData(list);
            jieKuanZhiJiaMineAdapter.setHasStableIds(true);
            jieKuanZhiJiaMineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelJieKuanZhiJia, JieKuanZhiJiaMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelJieKuanZhiJia model, int tag, JieKuanZhiJiaMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", JieKuanZhiJiaApi.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityJieKuanZhiJia.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", JieKuanZhiJiaApi.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityJieKuanZhiJia.class)
                                    .data(bundle)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(jieKuanZhiJiaMineAdapter);
        }
        if (jieKuanZhiJiaMineAdapter1 == null) {
            jieKuanZhiJiaMineAdapter1 = new JieKuanZhiJiaMineAdapter1(getActivity());
            jieKuanZhiJiaMineAdapter1.setData(list1);
            jieKuanZhiJiaMineAdapter1.setHasStableIds(true);
            jieKuanZhiJiaMineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModelJieKuanZhiJia, JieKuanZhiJiaMineAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelJieKuanZhiJia model, int tag, JieKuanZhiJiaMineAdapter1.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(JieKuanZhiJiaAboutUsActivity.class)
                                    .launch();
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(SettingJieKuanZhiJiaActivity.class)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(JieKuanZhiJiaCancellationAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(jieKuanZhiJiaMineAdapter1);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JieKuanZhiJiaApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>getScheduler())
                    .compose(this.<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    JieKuanZhiJiaSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    public void aindex() {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("token");
            JieKuanZhiJiaRequModel model = new JieKuanZhiJiaRequModel();
            model.setToken(token);
            RequestBody body = JieKuanZhiJiaStaticUtil.createBody(new Gson().toJson(model));
            JieKuanZhiJiaApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>getScheduler())
                    .compose(this.<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
//                                        edu_tv.setText(gankResults.getData().get(0).getMax_money());
                                    }
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (jieKuanZhiJiaNormalDialog != null) {
            jieKuanZhiJiaNormalDialog.dismiss();
            jieKuanZhiJiaNormalDialog = null;
        }
        super.onDestroy();
    }
}
