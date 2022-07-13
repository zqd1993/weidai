package com.tryrbdfbv.grtregdfh.ui.fragment;

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

import com.tryrbdfbv.grtregdfh.R;
import com.tryrbdfbv.grtregdfh.adapter.MineAdapter;
import com.tryrbdfbv.grtregdfh.model.BaseRespModel;
import com.tryrbdfbv.grtregdfh.model.CompanyInfoModel;
import com.tryrbdfbv.grtregdfh.model.MineItemModel;
import com.tryrbdfbv.grtregdfh.net.ApiSubscriber;
import com.tryrbdfbv.grtregdfh.net.NetError;
import com.tryrbdfbv.grtregdfh.net.XApi;
import com.tryrbdfbv.grtregdfh.ui.WebViewActivity;
import com.tryrbdfbv.grtregdfh.ui.activity.SettingActivity;
import com.tryrbdfbv.grtregdfh.utils.SharedPreferencesUtilis;
import com.tryrbdfbv.grtregdfh.utils.ToastUtil;
import com.tryrbdfbv.grtregdfh.net.Api;
import com.tryrbdfbv.grtregdfh.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.tryrbdfbv.grtregdfh.widget.NormalDialog;
import com.tryrbdfbv.grtregdfh.mvp.XFragment;
import com.tryrbdfbv.grtregdfh.ui.activity.AboutUsActivity;
import com.tryrbdfbv.grtregdfh.ui.activity.CancellationAccountActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mail_tv)
    TextView mail_tv;
    @BindView(R.id.mail_ll)
    View mail_ll;

    private MineAdapter mineAdapter;
    private List<MineItemModel> list;
    private int[] imgRes = {R.drawable.bghnj, R.drawable.vvbfg, R.drawable.rfvnhu, R.drawable.yhgdv, R.drawable.xcvfgtert};
    private String[] tvRes = {"关于我们", "隐私协议", "注册协议", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalDialog normalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        getCompanyInfo();
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            MineItemModel model = new MineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        mail_ll.setOnClickListener(v -> {
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
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(SettingActivity.class)
                                    .launch();
                            break;
                        case 5:
                            Router.newIntent(getActivity())
                                    .to(CancellationAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter);
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
