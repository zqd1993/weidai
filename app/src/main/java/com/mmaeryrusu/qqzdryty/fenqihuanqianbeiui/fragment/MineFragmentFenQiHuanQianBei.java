package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter.FenQiHuanQianBeiMineAdapter;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter.MineFenQiHuanQianBeiAdapter1;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.BaseRespFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.CompanyInfoModelFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.FenQiHuanQianBeiMineItemModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiSubscriber;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.NetError;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.XApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.WebViewActivityFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.activity.SettingFenQiHuanQianBeiActivity;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.FenQiHuanQianBeiSharedPreferencesUtilis;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.ToastUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiwidget.NormalFenQiHuanQianBeiDialog;
import com.mmaeryrusu.qqzdryty.mvp.XFragment;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.activity.FenQiHuanQianBeiAboutUsActivity;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.activity.CancellationFenQiHuanQianBeiAccountActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragmentFenQiHuanQianBei extends XFragment {

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

    private FenQiHuanQianBeiMineAdapter fenQiHuanQianBeiMineAdapter;
    private MineFenQiHuanQianBeiAdapter1 mineFenQiHuanQianBeiAdapter1;
    private List<FenQiHuanQianBeiMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.xccfgutyui, R.drawable.urtyufcgj, R.drawable.erretdxyrtu,R.drawable.klkdtyrdtu,
            R.drawable.ccghjrtu, R.drawable.llyfuoryut};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "投诉邮箱", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalFenQiHuanQianBeiDialog normalFenQiHuanQianBeiDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        getCompanyInfo();
        phone = FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 6; i++) {
            FenQiHuanQianBeiMineItemModel model = new FenQiHuanQianBeiMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        mail_sl.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            ToastUtilFenQiHuanQianBei.showShort("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fen_qi_huan_qian_bei_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (fenQiHuanQianBeiMineAdapter == null) {
            fenQiHuanQianBeiMineAdapter = new FenQiHuanQianBeiMineAdapter(getActivity());
            fenQiHuanQianBeiMineAdapter.setData(list);
            fenQiHuanQianBeiMineAdapter.setHasStableIds(true);
            fenQiHuanQianBeiMineAdapter.setRecItemClick(new RecyclerItemCallback<FenQiHuanQianBeiMineItemModel, FenQiHuanQianBeiMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, FenQiHuanQianBeiMineItemModel model, int tag, FenQiHuanQianBeiMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiFenQiHuanQianBei.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityFenQiHuanQianBei.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiFenQiHuanQianBei.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityFenQiHuanQianBei.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(FenQiHuanQianBeiAboutUsActivity.class)
                                    .launch();
                            break;
                        case 3:
                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText(null, mailStr);
                            clipboard.setPrimaryClip(clipData);
                            ToastUtilFenQiHuanQianBei.showShort("复制成功");
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(SettingFenQiHuanQianBeiActivity.class)
                                    .launch();
                            break;
                        case 5:
                            Router.newIntent(getActivity())
                                    .to(CancellationFenQiHuanQianBeiAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(fenQiHuanQianBeiMineAdapter);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(FenQiHuanQianBeiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiFenQiHuanQianBei.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<CompanyInfoModelFenQiHuanQianBei>>getApiTransformer())
                    .compose(XApi.<BaseRespFenQiHuanQianBeiModel<CompanyInfoModelFenQiHuanQianBei>>getScheduler())
                    .compose(this.<BaseRespFenQiHuanQianBeiModel<CompanyInfoModelFenQiHuanQianBei>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespFenQiHuanQianBeiModel<CompanyInfoModelFenQiHuanQianBei>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespFenQiHuanQianBeiModel<CompanyInfoModelFenQiHuanQianBei> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    FenQiHuanQianBeiSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (normalFenQiHuanQianBeiDialog != null) {
            normalFenQiHuanQianBeiDialog.dismiss();
            normalFenQiHuanQianBeiDialog = null;
        }
        super.onDestroy();
    }
}
