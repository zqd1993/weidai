package com.bdfhrthdfh.nzfdhredfgh.koudaiui.fragment;

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

import com.bdfhrthdfh.nzfdhredfgh.R;
import com.bdfhrthdfh.nzfdhredfgh.adapterkoudai.KouDaiMineAdapter;
import com.bdfhrthdfh.nzfdhredfgh.koudaimodel.BaseRespModelKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaimodel.CompanyKouDaiInfoModel;
import com.bdfhrthdfh.nzfdhredfgh.koudaimodel.KouDaiMineItemModel;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.ApiSubscriber;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.NetError;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.XApi;
import com.bdfhrthdfh.nzfdhredfgh.koudaiui.WebViewActivityKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaiui.activity.SettingActivityKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaiutils.SharedPreferencesUtilisKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaiutils.ToastUtilKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.KouDaiApi;
import com.bdfhrthdfh.nzfdhredfgh.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.bdfhrthdfh.nzfdhredfgh.koudaiwidget.KouDaiNormalDialog;
import com.bdfhrthdfh.nzfdhredfgh.mvp.XFragment;
import com.bdfhrthdfh.nzfdhredfgh.koudaiui.activity.AboutUsActivityKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaiui.activity.KouDaiCancellationAccountActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineKouDaiFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.phone_fl)
    View phone_fl;
    @BindView(R.id.mail_fl)
    View mail_fl;
    @BindView(R.id.mail_tv)
    TextView mail_tv;

    private KouDaiMineAdapter mineAdapter;
    private List<KouDaiMineItemModel> list;
    private int[] imgRes = {R.drawable.mrtufhfxgjh, R.drawable.mdrhgzdgzesrt, R.drawable.mrtuygzdh,
            R.drawable.lthyfgjrt, R.drawable.mnzdrgtzdg};
    private String[] tvRes = {"关于我们", "隐私协议", "注册协议", "系统设置", "注销账户"};
    private Bundle bundle;
    private KouDaiNormalDialog kouDaiNormalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        getCompanyInfo();
        phone = SharedPreferencesUtilisKouDai.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            KouDaiMineItemModel model = new KouDaiMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        phone_fl.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, phone);
            clipboard.setPrimaryClip(clipData);
            ToastUtilKouDai.showShort("复制成功");
        });
        mail_fl.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            ToastUtilKouDai.showShort("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_kou_dai;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineAdapter == null) {
            mineAdapter = new KouDaiMineAdapter(getActivity());
            mineAdapter.setData(list);
            mineAdapter.setHasStableIds(true);
            mineAdapter.setRecItemClick(new RecyclerItemCallback<KouDaiMineItemModel, KouDaiMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, KouDaiMineItemModel model, int tag, KouDaiMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivityKouDai.class)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", KouDaiApi.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityKouDai.class)
                                    .data(bundle)
                                    .launch();
                        case 2:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", KouDaiApi.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityKouDai.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 3:
                            Router.newIntent(getActivity())
                                    .to(SettingActivityKouDai.class)
                                    .launch();
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(KouDaiCancellationAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisKouDai.getStringFromPref("API_BASE_URL"))) {
            KouDaiApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelKouDai<CompanyKouDaiInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelKouDai<CompanyKouDaiInfoModel>>getScheduler())
                    .compose(this.<BaseRespModelKouDai<CompanyKouDaiInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKouDai<CompanyKouDaiInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelKouDai<CompanyKouDaiInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedPreferencesUtilisKouDai.saveStringIntoPref("APP_MAIL", mailStr);
                                    if (mineAdapter != null) {
                                        mineAdapter.notifyDataSetChanged();
                                    } else {
                                        initAdapter();
                                    }
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (kouDaiNormalDialog != null) {
            kouDaiNormalDialog.dismiss();
            kouDaiNormalDialog = null;
        }
        super.onDestroy();
    }
}
