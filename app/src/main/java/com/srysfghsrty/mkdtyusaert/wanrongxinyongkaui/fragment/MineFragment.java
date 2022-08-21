package com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.srysfghsrty.mkdtyusaert.R;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaadapter.MineWanRongXinYongKaAdapter;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.BaseRespModelWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.CompanyInfoWanRongXinYongKaModel;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel.MineWanRongXinYongKaItemModel;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.ApiSubscriber;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.ApiWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.NetError;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.XApi;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.WebViewWanRongXinYongKaActivity;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.activity.AboutWanRongXinYongKaUsActivity;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.activity.CancellationAccountActivityWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkaui.activity.SettingWanRongXinYongKaActivity;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.SharedPreferencesUtilisWanRongXinYongKa;
import com.srysfghsrty.mkdtyusaert.wanrongxinyongkautils.ToastWanRongXinYongKaUtil;
import com.srysfghsrty.mkdtyusaert.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.srysfghsrty.mkdtyusaert.wanrongxinyongkawidget.NormalWanRongXinYongKaDialog;
import com.srysfghsrty.mkdtyusaert.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragment extends XFragment {

    @BindView(R.id.rvy_1)
    RecyclerView rvy1;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mail_tv)
    TextView mail_tv;
    @BindView(R.id.copy_mail_tv)
    TextView copy_mail_tv;

    private MineWanRongXinYongKaAdapter mineWanRongXinYongKaAdapter;
    private List<MineWanRongXinYongKaItemModel> list;
    private int[] imgRes = {R.drawable.ddrthxfn, R.drawable.wqwxf, R.drawable.xccvbnsryu,
            R.drawable.hjsdhxcvh, R.drawable.kdhxfghj};
    private String[] tvRes = {"关于我们", "隐私协议", "注册协议", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalWanRongXinYongKaDialog normalWanRongXinYongKaDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        getCompanyInfo();
        phone = SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("phone");
        mailStr = SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("APP_MAIL");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            MineWanRongXinYongKaItemModel model = new MineWanRongXinYongKaItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        copy_mail_tv.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            ToastWanRongXinYongKaUtil.showShort("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wan_rong_xin_yong_ka_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineWanRongXinYongKaAdapter == null) {
            mineWanRongXinYongKaAdapter = new MineWanRongXinYongKaAdapter(getActivity());
            mineWanRongXinYongKaAdapter.setData(list);
            mineWanRongXinYongKaAdapter.setHasStableIds(true);
            mineWanRongXinYongKaAdapter.setRecItemClick(new RecyclerItemCallback<MineWanRongXinYongKaItemModel, MineWanRongXinYongKaAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineWanRongXinYongKaItemModel model, int tag, MineWanRongXinYongKaAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutWanRongXinYongKaUsActivity.class)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiWanRongXinYongKa.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewWanRongXinYongKaActivity.class)
                                    .data(bundle)
                                    .launch();
                        case 2:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiWanRongXinYongKa.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewWanRongXinYongKaActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 3:
                            Router.newIntent(getActivity())
                                    .to(SettingWanRongXinYongKaActivity.class)
                                    .launch();
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(CancellationAccountActivityWanRongXinYongKa.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(mineWanRongXinYongKaAdapter);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisWanRongXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ApiWanRongXinYongKa.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelWanRongXinYongKa<CompanyInfoWanRongXinYongKaModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelWanRongXinYongKa<CompanyInfoWanRongXinYongKaModel>>getScheduler())
                    .compose(this.<BaseRespModelWanRongXinYongKa<CompanyInfoWanRongXinYongKaModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWanRongXinYongKa<CompanyInfoWanRongXinYongKaModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelWanRongXinYongKa<CompanyInfoWanRongXinYongKaModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedPreferencesUtilisWanRongXinYongKa.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (normalWanRongXinYongKaDialog != null) {
            normalWanRongXinYongKaDialog.dismiss();
            normalWanRongXinYongKaDialog = null;
        }
        super.onDestroy();
    }
}
