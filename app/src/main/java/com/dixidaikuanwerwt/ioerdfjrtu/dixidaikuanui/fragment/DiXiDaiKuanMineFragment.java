package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.fragment;

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

import com.dixidaikuanwerwt.ioerdfjrtu.R;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanadapter.DiXiDaiKuanMineAdapter;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanadapter.MineAdapter1DiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.BaseRespModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.CompanyDiXiDaiKuanInfoModel;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.MineItemModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.ApiSubscriber;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.DiXiDaiKuanApi;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.NetError;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuannet.XApi;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.WebViewActivityDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.activity.AboutUsDiXiDaiKuanActivity;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.activity.DiXiDaiKuanCancellationAccountActivity;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanui.activity.SettingActivityDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.DiXiDaiKuanToastUtil;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.SharedDiXiDaiKuanPreferencesUtilis;
import com.dixidaikuanwerwt.ioerdfjrtu.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanwidget.DiXiDaiKuanNormalDialog;
import com.dixidaikuanwerwt.ioerdfjrtu.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class DiXiDaiKuanMineFragment extends XFragment {

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

    private DiXiDaiKuanMineAdapter diXiDaiKuanMineAdapter;
    private MineAdapter1DiXiDaiKuan mineAdapter1DiXiDaiKuan;
    private List<MineItemModelDiXiDaiKuan> list, list1;
    private int[] imgRes = {R.drawable.dfgdfhseru, R.drawable.kktdysrtu, R.drawable.eesdtery, R.drawable.kkdtyusery,
            R.drawable.xzdfhsertu, R.drawable.xfghfghjrtu};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "投诉邮箱", "系统设置", "注销账户"};
    private Bundle bundle;
    private DiXiDaiKuanNormalDialog diXiDaiKuanNormalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        phone = SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 6; i++) {
            MineItemModelDiXiDaiKuan model = new MineItemModelDiXiDaiKuan();
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
            DiXiDaiKuanToastUtil.showShort("复制成功");
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getCompanyInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_di_xi_dai_kuan_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        mineAdapter1DiXiDaiKuan = new MineAdapter1DiXiDaiKuan(getActivity());
        mineAdapter1DiXiDaiKuan.setData(list);
        mineAdapter1DiXiDaiKuan.setHasStableIds(true);
        mineAdapter1DiXiDaiKuan.setRecItemClick(new RecyclerItemCallback<MineItemModelDiXiDaiKuan, MineAdapter1DiXiDaiKuan.ViewHolder>() {
            @Override
            public void onItemClick(int position, MineItemModelDiXiDaiKuan model, int tag, MineAdapter1DiXiDaiKuan.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (position) {
                    case 0:
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", DiXiDaiKuanApi.getZc());
                        Router.newIntent(getActivity())
                                .to(WebViewActivityDiXiDaiKuan.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putInt("tag", 2);
                        bundle.putString("url", DiXiDaiKuanApi.getYs());
                        Router.newIntent(getActivity())
                                .to(WebViewActivityDiXiDaiKuan.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 2:
                        Router.newIntent(getActivity())
                                .to(AboutUsDiXiDaiKuanActivity.class)
                                .launch();
                        break;
                }
            }
        });
        rvy1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvy1.setHasFixedSize(true);
        rvy1.setAdapter(mineAdapter1DiXiDaiKuan);
        if (diXiDaiKuanMineAdapter == null) {
            diXiDaiKuanMineAdapter = new DiXiDaiKuanMineAdapter(getActivity());
            diXiDaiKuanMineAdapter.setData(list1);
            diXiDaiKuanMineAdapter.setHasStableIds(true);
            diXiDaiKuanMineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelDiXiDaiKuan, DiXiDaiKuanMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelDiXiDaiKuan model, int tag, DiXiDaiKuanMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText(null, mailStr);
                            clipboard.setPrimaryClip(clipData);
                            DiXiDaiKuanToastUtil.showShort("复制成功");
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(SettingActivityDiXiDaiKuan.class)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(DiXiDaiKuanCancellationAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(diXiDaiKuanMineAdapter);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            DiXiDaiKuanApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelDiXiDaiKuan<CompanyDiXiDaiKuanInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDiXiDaiKuan<CompanyDiXiDaiKuanInfoModel>>getScheduler())
                    .compose(this.<BaseRespModelDiXiDaiKuan<CompanyDiXiDaiKuanInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDiXiDaiKuan<CompanyDiXiDaiKuanInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelDiXiDaiKuan<CompanyDiXiDaiKuanInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedDiXiDaiKuanPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (diXiDaiKuanNormalDialog != null) {
            diXiDaiKuanNormalDialog.dismiss();
            diXiDaiKuanNormalDialog = null;
        }
        super.onDestroy();
    }
}
