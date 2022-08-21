package com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.fragmentjiujijiedai;

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

import com.jiujjidaikuansdafjer.fgbnsrtyeasy.R;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.adapterjiujijiedai.JiuJiJieDaiMineAdapter;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.adapterjiujijiedai.JiuJiJieDaiMineAdapter1;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiBaseRespModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiCompanyInfoModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.modeljiujijiedai.JiuJiJieDaiMineItemModel;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.JiuJiJieDaiApi;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.ApiSubscriber;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.NetError;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.netjiujijiedai.XApi;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.JiuJiJieDaiWebViewActivity;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.activityjiujijiedai.JiuJiJieDaiCancellationAccountActivity;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.activityjiujijiedai.JiuJiJieDaiAboutUsActivity;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.uijiujijiedai.activityjiujijiedai.JiuJiJieDaiSettingActivity;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiSharedPreferencesUtilis;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.utilsjiujijiedai.JiuJiJieDaiToastUtil;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.jiujjidaikuansdafjer.fgbnsrtyeasy.widgetjiujijiedai.JiuJiJieDaiNormalDialog;
import com.jiujjidaikuansdafjer.fgbnsrtyeasy.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class JiuJiJieDaiMineFragment extends XFragment {

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
    private int[] imgRes = {R.drawable.lpyucv, R.drawable.wqdfgdx, R.drawable.xvbnsr, R.drawable.dtyisr, R.drawable.cvbnsdrt, R.drawable.mnbnxft};
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
        if (jiuJiJieDaiMineAdapter1 == null) {
            jiuJiJieDaiMineAdapter1 = new JiuJiJieDaiMineAdapter1(getActivity());
            jiuJiJieDaiMineAdapter1.setData(list);
            jiuJiJieDaiMineAdapter1.setHasStableIds(true);
            jiuJiJieDaiMineAdapter1.setRecItemClick(new RecyclerItemCallback<JiuJiJieDaiMineItemModel, JiuJiJieDaiMineAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, JiuJiJieDaiMineItemModel model, int tag, JiuJiJieDaiMineAdapter1.ViewHolder holder) {
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
                        case 3:
                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText(null, mailStr);
                            clipboard.setPrimaryClip(clipData);
                            JiuJiJieDaiToastUtil.showShort("复制成功");
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(JiuJiJieDaiSettingActivity.class)
                                    .launch();
                            break;
                        case 5:
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
