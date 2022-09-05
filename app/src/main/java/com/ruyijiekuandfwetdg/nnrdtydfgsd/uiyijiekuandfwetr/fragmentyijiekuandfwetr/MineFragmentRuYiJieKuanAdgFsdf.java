package com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.fragmentyijiekuandfwetr;

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
import com.ruyijiekuandfwetdg.nnrdtydfgsd.R;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.adapterruyijiekuandfwetr.RuYiJieKuanAdgFsdfMineAdapter;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.BaseRespModelRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.CompanyInfoModelRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfGoodsModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.MineItemModelRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr.RuYiJieKuanAdgFsdfRequModel;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.RuYiJieKuanAdgFsdfApi;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.ApiSubscriber;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.NetError;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.XApi;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.WebViewActivityRuYiJieKuanAdgFsdf;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.activityyijiekuandfwetr.RuYiJieKuanAdgFsdfCancellationAccountActivity;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.activityyijiekuandfwetr.SettingRuYiJieKuanAdgFsdfActivity;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfSharedPreferencesUtilis;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfStaticUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.utilsyijiekuandfwetr.RuYiJieKuanAdgFsdfToastUtil;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.widgetyijiekuandfwetr.RuYiJieKuanAdgFsdfNormalDialog;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.mvp.XFragment;
import com.ruyijiekuandfwetdg.nnrdtydfgsd.uiyijiekuandfwetr.activityyijiekuandfwetr.RuYiJieKuanAdgFsdfAboutUsActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import okhttp3.RequestBody;

public class MineFragmentRuYiJieKuanAdgFsdf extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mail_tv)
    TextView mail_tv;
    @BindView(R.id.mail_sl)
    View mail_sl;
    @BindView(R.id.rvy_1)
    RecyclerView rvy_1;

    private RuYiJieKuanAdgFsdfMineAdapter ruYiJieKuanAdgFsdfMineAdapter, ruYiJieKuanAdgFsdfMineAdapter1;
    private List<MineItemModelRuYiJieKuanAdgFsdf> list, list1;
    private int[] imgRes = {R.drawable.lpyt, R.drawable.rtfghj, R.drawable.cvbsd, R.drawable.ikrhsd, R.drawable.ehfghj};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "系统设置", "注销账户"};
    private Bundle bundle;
    private RuYiJieKuanAdgFsdfNormalDialog ruYiJieKuanAdgFsdfNormalDialog;
    private String mailStr = "", phone = "", token = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        getCompanyInfo();
        aindex();
        phone = RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            MineItemModelRuYiJieKuanAdgFsdf model = new MineItemModelRuYiJieKuanAdgFsdf();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            if (i < 3) {
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
            RuYiJieKuanAdgFsdfToastUtil.showShort("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_ru_yi_jie_kuan_dfs_wetg;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (ruYiJieKuanAdgFsdfMineAdapter == null) {
            ruYiJieKuanAdgFsdfMineAdapter = new RuYiJieKuanAdgFsdfMineAdapter(getActivity());
            ruYiJieKuanAdgFsdfMineAdapter.setData(list);
            ruYiJieKuanAdgFsdfMineAdapter.setHasStableIds(true);
            ruYiJieKuanAdgFsdfMineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelRuYiJieKuanAdgFsdf, RuYiJieKuanAdgFsdfMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelRuYiJieKuanAdgFsdf model, int tag, RuYiJieKuanAdgFsdfMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", RuYiJieKuanAdgFsdfApi.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityRuYiJieKuanAdgFsdf.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", RuYiJieKuanAdgFsdfApi.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityRuYiJieKuanAdgFsdf.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(RuYiJieKuanAdgFsdfAboutUsActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(ruYiJieKuanAdgFsdfMineAdapter);
        }
            ruYiJieKuanAdgFsdfMineAdapter1 = new RuYiJieKuanAdgFsdfMineAdapter(getActivity());
            ruYiJieKuanAdgFsdfMineAdapter1.setData(list1);
            ruYiJieKuanAdgFsdfMineAdapter1.setHasStableIds(true);
            ruYiJieKuanAdgFsdfMineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModelRuYiJieKuanAdgFsdf, RuYiJieKuanAdgFsdfMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelRuYiJieKuanAdgFsdf model, int tag, RuYiJieKuanAdgFsdfMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(SettingRuYiJieKuanAdgFsdfActivity.class)
                                    .launch();
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(RuYiJieKuanAdgFsdfCancellationAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy_1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy_1.setHasFixedSize(true);
            rvy_1.setAdapter(ruYiJieKuanAdgFsdfMineAdapter1);
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            RuYiJieKuanAdgFsdfApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<CompanyInfoModelRuYiJieKuanAdgFsdf>>getApiTransformer())
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<CompanyInfoModelRuYiJieKuanAdgFsdf>>getScheduler())
                    .compose(this.<BaseRespModelRuYiJieKuanAdgFsdf<CompanyInfoModelRuYiJieKuanAdgFsdf>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelRuYiJieKuanAdgFsdf<CompanyInfoModelRuYiJieKuanAdgFsdf>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelRuYiJieKuanAdgFsdf<CompanyInfoModelRuYiJieKuanAdgFsdf> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    RuYiJieKuanAdgFsdfSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    public void aindex() {
        if (!TextUtils.isEmpty(RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = RuYiJieKuanAdgFsdfSharedPreferencesUtilis.getStringFromPref("token");
            RuYiJieKuanAdgFsdfRequModel model = new RuYiJieKuanAdgFsdfRequModel();
            model.setToken(token);
            RequestBody body = RuYiJieKuanAdgFsdfStaticUtil.createBody(new Gson().toJson(model));
            RuYiJieKuanAdgFsdfApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>getScheduler())
                    .compose(this.<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelRuYiJieKuanAdgFsdf<List<RuYiJieKuanAdgFsdfGoodsModel>> gankResults) {
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
        if (ruYiJieKuanAdgFsdfNormalDialog != null) {
            ruYiJieKuanAdgFsdfNormalDialog.dismiss();
            ruYiJieKuanAdgFsdfNormalDialog = null;
        }
        super.onDestroy();
    }
}
