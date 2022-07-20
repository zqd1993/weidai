package com.suishijie.ndjufgjhdrty.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.suishijie.ndjufgjhdrty.R;
import com.suishijie.ndjufgjhdrty.adapter.MineAdapter;
import com.suishijie.ndjufgjhdrty.model.BaseRespModel;
import com.suishijie.ndjufgjhdrty.model.CompanyInfoModel;
import com.suishijie.ndjufgjhdrty.model.MineItemModel;
import com.suishijie.ndjufgjhdrty.net.ApiSubscriber;
import com.suishijie.ndjufgjhdrty.net.NetError;
import com.suishijie.ndjufgjhdrty.net.XApi;
import com.suishijie.ndjufgjhdrty.ui.WebViewActivity;
import com.suishijie.ndjufgjhdrty.ui.activity.SettingActivity;
import com.suishijie.ndjufgjhdrty.utils.SharedPreferencesUtilis;
import com.suishijie.ndjufgjhdrty.utils.ToastUtil;
import com.suishijie.ndjufgjhdrty.net.Api;
import com.suishijie.ndjufgjhdrty.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.suishijie.ndjufgjhdrty.widget.NormalDialog;
import com.suishijie.ndjufgjhdrty.mvp.XFragment;
import com.suishijie.ndjufgjhdrty.ui.activity.AboutUsActivity;
import com.suishijie.ndjufgjhdrty.ui.activity.CancellationAccountActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mail_tv)
    TextView mail_tv;

    private MineAdapter mineAdapter, mineAdapter1;
    private List<MineItemModel> list, list1;
    private int[] imgRes = {R.drawable.erfdsv, R.drawable.bbfdr, R.drawable.sdsda,
            R.drawable.mmghjy, R.drawable.sdddfg};
    private String[] tvRes = {"关于我们", "隐私协议", "注册协议", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalDialog normalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        getCompanyInfo();
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
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
        mail_tv.setOnClickListener(v -> {
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
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivity.class)
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
                        case 2:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", Api.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter);
        }

        if (mineAdapter1 == null) {
            mineAdapter1 = new MineAdapter(getActivity());
            mineAdapter1.setData(list1);
            mineAdapter1.setHasStableIds(true);
            mineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModel, MineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModel model, int tag, MineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(SettingActivity.class)
                                    .launch();
                            break;
                        case 1:
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
