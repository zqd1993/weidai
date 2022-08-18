package com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaofragment;

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

import com.asdfgaery.nnaeryaery.R;
import com.asdfgaery.nnaeryaery.daikuanqianbaodapter.MineDaiKuanQianBaoAdapter1;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.BaseRespModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.CompanyDaiKuanQianBaoInfoModel;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.MineItemModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.ApiSubscriber;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.DaiKuanQianBaoApi;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.NetError;
import com.asdfgaery.nnaeryaery.daikuanqianbaonet.XApi;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.WebViewActivityDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaoactivity.AboutUsDaiKuanQianBaoActivity;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaoactivity.DaiKuanQianBaoCancellationAccountActivity;
import com.asdfgaery.nnaeryaery.daikuanqianbaoui.daikuanqianbaoactivity.SettingActivityDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.ToastUtilDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.SharedDaiKuanQianBaoPreferencesUtilis;
import com.asdfgaery.nnaeryaery.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.asdfgaery.nnaeryaery.daikuanqianbaowidget.DaiKuanQianBaoNormalDialog;
import com.asdfgaery.nnaeryaery.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class DaiKuanQianBaoMineFragment extends XFragment {

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

    private MineDaiKuanQianBaoAdapter1 mineDaiKuanQianBaoAdapter1;
    private List<MineItemModelDaiKuanQianBao> list;
    private int[] imgRes = {R.drawable.sshxrdy, R.drawable.zzxdryery, R.drawable.mmxczery,
            R.drawable.rtyzrjusx, R.drawable.zetfhsxruy};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "系统设置", "注销账户"};
    private Bundle bundle;
    private DaiKuanQianBaoNormalDialog daiKuanQianBaoNormalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        phone = SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            MineItemModelDaiKuanQianBao model = new MineItemModelDaiKuanQianBao();
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
            ToastUtilDaiKuanQianBao.showShort("复制成功");
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getCompanyInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dai_kuan_qian_bao_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        mineDaiKuanQianBaoAdapter1 = new MineDaiKuanQianBaoAdapter1(getActivity());
        mineDaiKuanQianBaoAdapter1.setData(list);
        mineDaiKuanQianBaoAdapter1.setHasStableIds(true);
        mineDaiKuanQianBaoAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModelDaiKuanQianBao, MineDaiKuanQianBaoAdapter1.ViewHolder>() {
            @Override
            public void onItemClick(int position, MineItemModelDaiKuanQianBao model, int tag, MineDaiKuanQianBaoAdapter1.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (position) {
                    case 0:
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", DaiKuanQianBaoApi.getZc());
                        Router.newIntent(getActivity())
                                .to(WebViewActivityDaiKuanQianBao.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putInt("tag", 2);
                        bundle.putString("url", DaiKuanQianBaoApi.getYs());
                        Router.newIntent(getActivity())
                                .to(WebViewActivityDaiKuanQianBao.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 2:
                        Router.newIntent(getActivity())
                                .to(AboutUsDaiKuanQianBaoActivity.class)
                                .launch();
                        break;
//                    case 3:
//                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//                        ClipData clipData = ClipData.newPlainText(null, mailStr);
//                        clipboard.setPrimaryClip(clipData);
//                        DiXiDaiKuanToastUtil.showShort("复制成功");
//                        break;
                    case 3:
                        Router.newIntent(getActivity())
                                .to(SettingActivityDaiKuanQianBao.class)
                                .launch();
                        break;
                    case 4:
                        Router.newIntent(getActivity())
                                .to(DaiKuanQianBaoCancellationAccountActivity.class)
                                .launch();
                        break;
                }
            }
        });
        rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvy.setAdapter(mineDaiKuanQianBaoAdapter1);
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DaiKuanQianBaoApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelDaiKuanQianBao<CompanyDaiKuanQianBaoInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDaiKuanQianBao<CompanyDaiKuanQianBaoInfoModel>>getScheduler())
                    .compose(this.<BaseRespModelDaiKuanQianBao<CompanyDaiKuanQianBaoInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDaiKuanQianBao<CompanyDaiKuanQianBaoInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelDaiKuanQianBao<CompanyDaiKuanQianBaoInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedDaiKuanQianBaoPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (daiKuanQianBaoNormalDialog != null) {
            daiKuanQianBaoNormalDialog.dismiss();
            daiKuanQianBaoNormalDialog = null;
        }
        super.onDestroy();
    }
}
