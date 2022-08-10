package com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fenqihuanqianbeifragment;

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

import com.mmaeryrusu.qqzdryty.R;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter.FenQiHuanQianBeiMineAdapter;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiadapter.MineAdapter1FenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.BaseRespFenQiHuanQianBeiModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.CompanyInfoModelFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeimodel.FenQiHuanQianBeiMineItemModel;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.FenQiHuanQianBeiApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.ApiSubscriber;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.NetError;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeinet.XApi;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.FenQiHuanQianBeiWebViewActivity;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fenqihuanqianbeiactivity.CancellationAccountActivityFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fenqihuanqianbeiactivity.FenQiHuanQianBeiAboutUsActivity;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiui.fenqihuanqianbeiactivity.SettingFenQiHuanQianBeiActivity;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.SharedPreferencesFenQiHuanQianBeiUtilis;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiutils.ToastUtilFenQiHuanQianBei;
import com.mmaeryrusu.qqzdryty.fenqihuanqianbeiwidget.NormalFenQiHuanQianBeiDialog;
import com.mmaeryrusu.qqzdryty.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.mmaeryrusu.qqzdryty.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFenQiHuanQianBeiFragment extends XFragment {

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
    private MineAdapter1FenQiHuanQianBei mineAdapter1FenQiHuanQianBei;
    private List<FenQiHuanQianBeiMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.xxbnftyutru, R.drawable.rtusrufgj, R.drawable.qqrrtu, R.drawable.xrtrurti, R.drawable.dtkdtyisrtu};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalFenQiHuanQianBeiDialog normalFenQiHuanQianBeiDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        getCompanyInfo();
        phone = SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
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
        return R.layout.fragment_mine_fen_qi_huan_qian;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineAdapter1FenQiHuanQianBei == null) {
            mineAdapter1FenQiHuanQianBei = new MineAdapter1FenQiHuanQianBei(getActivity());
            mineAdapter1FenQiHuanQianBei.setData(list);
            mineAdapter1FenQiHuanQianBei.setHasStableIds(true);
            mineAdapter1FenQiHuanQianBei.setRecItemClick(new RecyclerItemCallback<FenQiHuanQianBeiMineItemModel, MineAdapter1FenQiHuanQianBei.ViewHolder>() {
                @Override
                public void onItemClick(int position, FenQiHuanQianBeiMineItemModel model, int tag, MineAdapter1FenQiHuanQianBei.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", FenQiHuanQianBeiApi.getZc());
                            Router.newIntent(getActivity())
                                    .to(FenQiHuanQianBeiWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", FenQiHuanQianBeiApi.getYs());
                            Router.newIntent(getActivity())
                                    .to(FenQiHuanQianBeiWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(FenQiHuanQianBeiAboutUsActivity.class)
                                    .launch();
                            break;
                        case 3:
                            Router.newIntent(getActivity())
                                    .to(SettingFenQiHuanQianBeiActivity.class)
                                    .launch();
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(CancellationAccountActivityFenQiHuanQianBei.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter1FenQiHuanQianBei);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesFenQiHuanQianBeiUtilis.getStringFromPref("API_BASE_URL"))) {
            FenQiHuanQianBeiApi.getGankService().getCompanyInfo()
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
                                    SharedPreferencesFenQiHuanQianBeiUtilis.saveStringIntoPref("APP_MAIL", mailStr);
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
