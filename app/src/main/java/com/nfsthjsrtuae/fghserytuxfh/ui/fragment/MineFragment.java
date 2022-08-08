package com.nfsthjsrtuae.fghserytuxfh.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.adapter.MineAdapter;
import com.nfsthjsrtuae.fghserytuxfh.adapter.MineAdapter1;
import com.nfsthjsrtuae.fghserytuxfh.model.BaseRespModel;
import com.nfsthjsrtuae.fghserytuxfh.model.CompanyInfoModel;
import com.nfsthjsrtuae.fghserytuxfh.model.GoodsModel;
import com.nfsthjsrtuae.fghserytuxfh.model.MineItemModel;
import com.nfsthjsrtuae.fghserytuxfh.model.RequModel;
import com.nfsthjsrtuae.fghserytuxfh.net.Api;
import com.nfsthjsrtuae.fghserytuxfh.net.ApiSubscriber;
import com.nfsthjsrtuae.fghserytuxfh.net.NetError;
import com.nfsthjsrtuae.fghserytuxfh.net.XApi;
import com.nfsthjsrtuae.fghserytuxfh.ui.WebViewActivity;
import com.nfsthjsrtuae.fghserytuxfh.ui.activity.CancellationAccountActivity;
import com.nfsthjsrtuae.fghserytuxfh.ui.activity.SettingActivity;
import com.nfsthjsrtuae.fghserytuxfh.utils.SharedPreferencesUtilis;
import com.nfsthjsrtuae.fghserytuxfh.utils.StaticUtil;
import com.nfsthjsrtuae.fghserytuxfh.utils.ToastUtil;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.nfsthjsrtuae.fghserytuxfh.widget.NormalDialog;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XFragment;
import com.nfsthjsrtuae.fghserytuxfh.ui.activity.AboutUsActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import okhttp3.RequestBody;

public class MineFragment extends XFragment {

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

    private MineAdapter mineAdapter;
    private MineAdapter1 mineAdapter1;
    private List<MineItemModel> list, list1;
    private int[] imgRes = {R.drawable.hzdryzu, R.drawable.zrxdhyfxgu, R.drawable.eryhftu, R.drawable.xghtxruru, R.drawable.mxftujzru, R.drawable.fghgfdurtu};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "投诉邮箱", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalDialog normalDialog;
    private String mailStr = "", phone = "", token = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        getCompanyInfo();
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 6; i++) {
            MineItemModel model = new MineItemModel();
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
        });
        mail_sl.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            ToastUtil.showShort("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineAdapter == null) {
            mineAdapter = new MineAdapter(getActivity());
            mineAdapter.setData(list);
            mineAdapter.setHasStableIds(true);
            mineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModel, MineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModel model, int tag, MineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", Api.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", Api.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter);
        }
        if (mineAdapter1 == null) {
            mineAdapter1 = new MineAdapter1(getActivity());
            mineAdapter1.setData(list1);
            mineAdapter1.setHasStableIds(true);
            mineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModel, MineAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModel model, int tag, MineAdapter1.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText(null, mailStr);
                            clipboard.setPrimaryClip(clipData);
                            ToastUtil.showShort("复制成功");
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(SettingActivity.class)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(CancellationAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(mineAdapter1);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            Api.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModel<CompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<CompanyInfoModel>>getScheduler())
                    .compose(this.<BaseRespModel<CompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<CompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModel<CompanyInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (normalDialog != null) {
            normalDialog.dismiss();
            normalDialog = null;
        }
        super.onDestroy();
    }
}
