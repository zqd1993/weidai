package com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.jiujijiedaifragment;

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

import com.jiejijiekuansdafjer.zdfgbaeryazer.R;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiadapter.JiuJiJieDaiMineAdapter;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiadapter.JiuJiJieDaiMineAdapter1;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaimodel.JiuJiJieDaiBaseRespModel;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaimodel.JiuJiJieDaiCompanyInfoModel;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaimodel.JiuJiJieDaiMineItemModel;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.JiuJiJieDaiApi;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.ApiSubscriber;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.NetError;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.XApi;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.JiuJiJieDaiWebViewActivity;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.jiujijiedaiactivity.JiuJiJieDaiCancellationAccountActivity;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.jiujijiedaiactivity.JiuJiJieDaiAboutUsActivity;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiui.jiujijiedaiactivity.JiuJiJieDaiSettingActivity;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiSharedPreferencesUtilis;
import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiutils.JiuJiJieDaiToastUtil;
import com.jiejijiekuansdafjer.zdfgbaeryazer.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaiwidget.JiuJiJieDaiNormalDialog;
import com.jiejijiekuansdafjer.zdfgbaeryazer.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class JiuJiJieDaiMineFragment extends XFragment {

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

    private JiuJiJieDaiMineAdapter jiuJiJieDaiMineAdapter;
    private JiuJiJieDaiMineAdapter1 jiuJiJieDaiMineAdapter1;
    private List<JiuJiJieDaiMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.hzdryzu, R.drawable.zrxdhyfxgu, R.drawable.eryhftu, R.drawable.xghtxruru, R.drawable.mxftujzru, R.drawable.fghgfdurtu};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "投诉邮箱", "系统设置", "注销账户"};
    private Bundle bundle;
    private JiuJiJieDaiNormalDialog jiuJiJieDaiNormalDialog;
    private String mailStr = "", phone = "", token = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        getCompanyInfo();
        phone = JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 6; i++) {
            JiuJiJieDaiMineItemModel model = new JiuJiJieDaiMineItemModel();
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
            JiuJiJieDaiToastUtil.showShort("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_jiu_ji_jie_dai;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (jiuJiJieDaiMineAdapter == null) {
            jiuJiJieDaiMineAdapter = new JiuJiJieDaiMineAdapter(getActivity());
            jiuJiJieDaiMineAdapter.setData(list);
            jiuJiJieDaiMineAdapter.setHasStableIds(true);
            jiuJiJieDaiMineAdapter.setRecItemClick(new RecyclerItemCallback<JiuJiJieDaiMineItemModel, JiuJiJieDaiMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, JiuJiJieDaiMineItemModel model, int tag, JiuJiJieDaiMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", JiuJiJieDaiApi.getZc());
                            Router.newIntent(getActivity())
                                    .to(JiuJiJieDaiWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", JiuJiJieDaiApi.getYs());
                            Router.newIntent(getActivity())
                                    .to(JiuJiJieDaiWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(JiuJiJieDaiAboutUsActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(jiuJiJieDaiMineAdapter);
        }
        if (jiuJiJieDaiMineAdapter1 == null) {
            jiuJiJieDaiMineAdapter1 = new JiuJiJieDaiMineAdapter1(getActivity());
            jiuJiJieDaiMineAdapter1.setData(list1);
            jiuJiJieDaiMineAdapter1.setHasStableIds(true);
            jiuJiJieDaiMineAdapter1.setRecItemClick(new RecyclerItemCallback<JiuJiJieDaiMineItemModel, JiuJiJieDaiMineAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, JiuJiJieDaiMineItemModel model, int tag, JiuJiJieDaiMineAdapter1.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText(null, mailStr);
                            clipboard.setPrimaryClip(clipData);
                            JiuJiJieDaiToastUtil.showShort("复制成功");
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(JiuJiJieDaiSettingActivity.class)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(JiuJiJieDaiCancellationAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(jiuJiJieDaiMineAdapter1);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(JiuJiJieDaiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieDaiApi.getGankService().getCompanyInfo()
                    .compose(XApi.<JiuJiJieDaiBaseRespModel<JiuJiJieDaiCompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<JiuJiJieDaiBaseRespModel<JiuJiJieDaiCompanyInfoModel>>getScheduler())
                    .compose(this.<JiuJiJieDaiBaseRespModel<JiuJiJieDaiCompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<JiuJiJieDaiBaseRespModel<JiuJiJieDaiCompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(JiuJiJieDaiBaseRespModel<JiuJiJieDaiCompanyInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    JiuJiJieDaiSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (jiuJiJieDaiNormalDialog != null) {
            jiuJiJieDaiNormalDialog.dismiss();
            jiuJiJieDaiNormalDialog = null;
        }
        super.onDestroy();
    }
}
