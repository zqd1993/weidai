package com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.jiejijietiaofragment;

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

import com.jiujijietiaodsfwet.bsdwefhert.R;
import com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.WebViewActivityJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.jiejijietiaoactivity.JiuJiJieTiaojghsdfCancellationAccountActivity;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoadapter.DiXiJiuJiJieTiaojghsdAdapter;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoadapter.MineAdapterJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.BaseRespModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.CompanyJiuJiJieTiaojghsdfInfoModel;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaomodel.MineItemModelJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.ApiSubscriber;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.JiuJiJieTiaojghsdfApi;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.NetError;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaonet.XApi;
import com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.jiejijietiaoactivity.AboutUsJiuJiJieTiaojghsdfActivity;
import com.jiujijietiaodsfwet.bsdwefhert.dixidaikuanui.jiejijietiaoactivity.SettingActivityJiuJiJieTiaojghsdf;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.JiuJiJieTiaojghsdfToastUtil;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaoutils.SharedJiuJiJieTiaojghsdfPreferencesUtilis;
import com.jiujijietiaodsfwet.bsdwefhert.jiejijietiaowidget.JiuJiJieTiaojghsdfNormalDialog;
import com.jiujijietiaodsfwet.bsdwefhert.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.jiujijietiaodsfwet.bsdwefhert.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class JiuJiJieTiaojghsdfMineFragment extends XFragment {

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

    private DiXiJiuJiJieTiaojghsdAdapter diXiJiuJiJieTiaojghsdAdapter;
    private MineAdapterJiuJiJieTiaojghsdf mineAdapterJiuJiJieTiaojghsdf;
    private List<MineItemModelJiuJiJieTiaojghsdf> list, list1;
    private int[] imgRes = {R.drawable.wwyhxfgjj, R.drawable.rehxfghw, R.drawable.gbnxrtusvfn, R.drawable.eerdxfhbeasry,
            R.drawable.qqetdfhn, R.drawable.kyfuodtrhfvbn};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "投诉邮箱", "系统设置", "注销账户"};
    private Bundle bundle;
    private JiuJiJieTiaojghsdfNormalDialog jiuJiJieTiaojghsdfNormalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        phone = SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 6; i++) {
            MineItemModelJiuJiJieTiaojghsdf model = new MineItemModelJiuJiJieTiaojghsdf();
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
            JiuJiJieTiaojghsdfToastUtil.showShort("复制成功");
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getCompanyInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jiu_ji_jie_tiao_boss_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        mineAdapterJiuJiJieTiaojghsdf = new MineAdapterJiuJiJieTiaojghsdf(getActivity());
        mineAdapterJiuJiJieTiaojghsdf.setData(list);
        mineAdapterJiuJiJieTiaojghsdf.setHasStableIds(true);
        mineAdapterJiuJiJieTiaojghsdf.setRecItemClick(new RecyclerItemCallback<MineItemModelJiuJiJieTiaojghsdf, MineAdapterJiuJiJieTiaojghsdf.ViewHolder>() {
            @Override
            public void onItemClick(int position, MineItemModelJiuJiJieTiaojghsdf model, int tag, MineAdapterJiuJiJieTiaojghsdf.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (position) {
                    case 0:
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", JiuJiJieTiaojghsdfApi.getZc());
                        Router.newIntent(getActivity())
                                .to(WebViewActivityJiuJiJieTiaojghsdf.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putInt("tag", 2);
                        bundle.putString("url", JiuJiJieTiaojghsdfApi.getYs());
                        Router.newIntent(getActivity())
                                .to(WebViewActivityJiuJiJieTiaojghsdf.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 2:
                        Router.newIntent(getActivity())
                                .to(AboutUsJiuJiJieTiaojghsdfActivity.class)
                                .launch();
                        break;
                }
            }
        });
        rvy1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvy1.setHasFixedSize(true);
        rvy1.setAdapter(mineAdapterJiuJiJieTiaojghsdf);
        if (diXiJiuJiJieTiaojghsdAdapter == null) {
            diXiJiuJiJieTiaojghsdAdapter = new DiXiJiuJiJieTiaojghsdAdapter(getActivity());
            diXiJiuJiJieTiaojghsdAdapter.setData(list1);
            diXiJiuJiJieTiaojghsdAdapter.setHasStableIds(true);
            diXiJiuJiJieTiaojghsdAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelJiuJiJieTiaojghsdf, DiXiJiuJiJieTiaojghsdAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelJiuJiJieTiaojghsdf model, int tag, DiXiJiuJiJieTiaojghsdAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText(null, mailStr);
                            clipboard.setPrimaryClip(clipData);
                            JiuJiJieTiaojghsdfToastUtil.showShort("复制成功");
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(SettingActivityJiuJiJieTiaojghsdf.class)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(JiuJiJieTiaojghsdfCancellationAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(diXiJiuJiJieTiaojghsdAdapter);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedJiuJiJieTiaojghsdfPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JiuJiJieTiaojghsdfApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<CompanyJiuJiJieTiaojghsdfInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJiuJiJieTiaojghsdf<CompanyJiuJiJieTiaojghsdfInfoModel>>getScheduler())
                    .compose(this.<BaseRespModelJiuJiJieTiaojghsdf<CompanyJiuJiJieTiaojghsdfInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJiuJiJieTiaojghsdf<CompanyJiuJiJieTiaojghsdfInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelJiuJiJieTiaojghsdf<CompanyJiuJiJieTiaojghsdfInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedJiuJiJieTiaojghsdfPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (jiuJiJieTiaojghsdfNormalDialog != null) {
            jiuJiJieTiaojghsdfNormalDialog.dismiss();
            jiuJiJieTiaojghsdfNormalDialog = null;
        }
        super.onDestroy();
    }
}
