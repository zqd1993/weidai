package com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.fragment;

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

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiadapter.MineKuaiDianFenQiDaiAdapter;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiadapter.MineKuaiDianFenQiDaiAdapter1;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.BaseRespModelKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.CompanyKuaiDianFenQiDaiInfoModel;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaimodel.MineKuaiDianFenQiDaiItemModel;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.ApiSubscriber;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.KuaiDianFenQiDaiWebViewActivity;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.activity.CancellationKuaiDianFenQiDaiAccountActivity;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.activity.KuaiDianFenQiDaiSettingActivity;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.SharedPreferencesKuaiDianFenQiDaiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiutils.ToastUtilKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidainet.ApiKuaiDianFenQiDai;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiwidget.NormalKuaiDianFenQiDaiDialog;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XFragment;
import com.nfsthjsrtuae.fghserytuxfh.kuaidaifenqidaiui.activity.AboutUsKuaiDianFenQiDaiActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class KuaiDianFenQiDaiMineFragment extends XFragment {

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
    @BindView(R.id.zcxy_tv)
    View zcxy_tv;
    @BindView(R.id.ysxy_tv)
    View ysxy_tv;

    private MineKuaiDianFenQiDaiAdapter mineAdapter;
    private MineKuaiDianFenQiDaiAdapter1 mineKuaiDianFenQiDaiAdapter1;
    private List<MineKuaiDianFenQiDaiItemModel> list, list1;
    private int[] imgRes = {R.drawable.poucvtyd, R.drawable.rrsdrvh, R.drawable.cvrtydrtfyu};
    private String[] tvRes = {"关于我们", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalKuaiDianFenQiDaiDialog normalKuaiDianFenQiDaiDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        getCompanyInfo();
        phone = SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 3; i++) {
            MineKuaiDianFenQiDaiItemModel model = new MineKuaiDianFenQiDaiItemModel();
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
            ToastUtilKuaiDianFenQiDai.showShort("复制成功");
        });
        zcxy_tv.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 1);
            bundle.putString("url", ApiKuaiDianFenQiDai.getZc());
            Router.newIntent(getActivity())
                    .to(KuaiDianFenQiDaiWebViewActivity.class)
                    .data(bundle)
                    .launch();
        });
        ysxy_tv.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 2);
            bundle.putString("url", ApiKuaiDianFenQiDai.getYs());
            Router.newIntent(getActivity())
                    .to(KuaiDianFenQiDaiWebViewActivity.class)
                    .data(bundle)
                    .launch();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kuai_dian_fen_qi_dai_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineKuaiDianFenQiDaiAdapter1 == null) {
            mineKuaiDianFenQiDaiAdapter1 = new MineKuaiDianFenQiDaiAdapter1(getActivity());
            mineKuaiDianFenQiDaiAdapter1.setData(list);
            mineKuaiDianFenQiDaiAdapter1.setHasStableIds(true);
            mineKuaiDianFenQiDaiAdapter1.setRecItemClick(new RecyclerItemCallback<MineKuaiDianFenQiDaiItemModel, MineKuaiDianFenQiDaiAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineKuaiDianFenQiDaiItemModel model, int tag, MineKuaiDianFenQiDaiAdapter1.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutUsKuaiDianFenQiDaiActivity.class)
                                    .launch();
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(KuaiDianFenQiDaiSettingActivity.class)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(CancellationKuaiDianFenQiDaiAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineKuaiDianFenQiDaiAdapter1);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesKuaiDianFenQiDaiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiKuaiDianFenQiDai.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai<CompanyKuaiDianFenQiDaiInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelKuaiDianFenQiDai<CompanyKuaiDianFenQiDaiInfoModel>>getScheduler())
                    .compose(this.<BaseRespModelKuaiDianFenQiDai<CompanyKuaiDianFenQiDaiInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKuaiDianFenQiDai<CompanyKuaiDianFenQiDaiInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelKuaiDianFenQiDai<CompanyKuaiDianFenQiDaiInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedPreferencesKuaiDianFenQiDaiUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (normalKuaiDianFenQiDaiDialog != null) {
            normalKuaiDianFenQiDaiDialog.dismiss();
            normalKuaiDianFenQiDaiDialog = null;
        }
        super.onDestroy();
    }
}
