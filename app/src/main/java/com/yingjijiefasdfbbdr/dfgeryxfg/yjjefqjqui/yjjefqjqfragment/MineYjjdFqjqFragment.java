package com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqfragment;

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

import com.google.gson.Gson;
import com.yingjijiefasdfbbdr.dfgeryxfg.R;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqadapter.MineAdapter1YjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqadapter.MineYjjdFqjqAdapter;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.GoodsModelYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.RequYjjdFqjqModel;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.YjjdFqjqApi;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.YjjdFqjqWebViewActivity;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqactivity.CancellationAccountActivityYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.YjjdFqjqBaseRespModel;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.CompanyInfoModelYjjdFqjq;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqmodel.YjjdFqjqMineItemModel;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.ApiSubscriber;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.NetError;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqnet.XApi;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqactivity.AboutUsYjjdFqjqActivity;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqui.yjjefqjqactivity.YjjdFqjqSettingActivity;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.StaticYjjdFqjqUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.ToastYjjdFqjqUtil;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqutils.YjjdFqjqSharedPreferencesUtilis;
import com.yingjijiefasdfbbdr.dfgeryxfg.yjjefqjqwidget.NormalYjjdFqjqDialog;
import com.yingjijiefasdfbbdr.dfgeryxfg.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.yingjijiefasdfbbdr.dfgeryxfg.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import okhttp3.RequestBody;

public class MineYjjdFqjqFragment extends XFragment {

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
    @BindView(R.id.edu_tv)
    TextView edu_tv;
    @BindView(R.id.shenqing_ll)
    View shenqing_lll;

    private MineYjjdFqjqAdapter mineYjjdFqjqAdapter;
    private MineAdapter1YjjdFqjq mineAdapter1YjjdFqjq;
    private List<YjjdFqjqMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.llyuopthxfgh, R.drawable.cvbmntseufg, R.drawable.vcbnerucgj,
            R.drawable.lklyuoptj, R.drawable.sdfhftiucv};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalYjjdFqjqDialog normalYjjdFqjqDialog;
    private String mailStr = "", phone = "", token = "";
    public GoodsModelYjjdFqjq topGoodsModelYjjdFqjq;

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        phone = YjjdFqjqSharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            YjjdFqjqMineItemModel model = new YjjdFqjqMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
            aindex();
        });
        mail_sl.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            ToastYjjdFqjqUtil.showShort("复制成功");
        });
        shenqing_lll.setOnClickListener(v -> {
            jumpWebActivity(topGoodsModelYjjdFqjq);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getCompanyInfo();
        aindex();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_yjjdfqjq;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        mineAdapter1YjjdFqjq = new MineAdapter1YjjdFqjq(getActivity());
        mineAdapter1YjjdFqjq.setData(list);
        mineAdapter1YjjdFqjq.setHasStableIds(true);
        mineAdapter1YjjdFqjq.setRecItemClick(new RecyclerItemCallback<YjjdFqjqMineItemModel, MineAdapter1YjjdFqjq.ViewHolder>() {
            @Override
            public void onItemClick(int position, YjjdFqjqMineItemModel model, int tag, MineAdapter1YjjdFqjq.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (position) {
                    case 0:
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", YjjdFqjqApi.getZc());
                        Router.newIntent(getActivity())
                                .to(YjjdFqjqWebViewActivity.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putInt("tag", 2);
                        bundle.putString("url", YjjdFqjqApi.getYs());
                        Router.newIntent(getActivity())
                                .to(YjjdFqjqWebViewActivity.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 2:
                        Router.newIntent(getActivity())
                                .to(AboutUsYjjdFqjqActivity.class)
                                .launch();
                        break;
                    case 3:
                        Router.newIntent(getActivity())
                                .to(YjjdFqjqSettingActivity.class)
                                .launch();
                        break;
                    case 4:
                        Router.newIntent(getActivity())
                                .to(CancellationAccountActivityYjjdFqjq.class)
                                .launch();
                        break;
                }
            }
        });
        rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvy.setHasFixedSize(true);
        rvy.setAdapter(mineAdapter1YjjdFqjq);
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            YjjdFqjqApi.getGankService().getCompanyInfo()
                    .compose(XApi.<YjjdFqjqBaseRespModel<CompanyInfoModelYjjdFqjq>>getApiTransformer())
                    .compose(XApi.<YjjdFqjqBaseRespModel<CompanyInfoModelYjjdFqjq>>getScheduler())
                    .compose(this.<YjjdFqjqBaseRespModel<CompanyInfoModelYjjdFqjq>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YjjdFqjqBaseRespModel<CompanyInfoModelYjjdFqjq>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(YjjdFqjqBaseRespModel<CompanyInfoModelYjjdFqjq> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    YjjdFqjqSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    public void jumpWebActivity(GoodsModelYjjdFqjq model) {
        if (model != null) {
            Bundle webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(YjjdFqjqWebViewActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public void aindex() {
        if (!TextUtils.isEmpty(YjjdFqjqSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = YjjdFqjqSharedPreferencesUtilis.getStringFromPref("token");
            RequYjjdFqjqModel model = new RequYjjdFqjqModel();
            model.setToken(token);
            RequestBody body = StaticYjjdFqjqUtil.createBody(new Gson().toJson(model));
            YjjdFqjqApi.getGankService().aindex(body)
                    .compose(XApi.<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>getApiTransformer())
                    .compose(XApi.<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>getScheduler())
                    .compose(this.<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(YjjdFqjqBaseRespModel<List<GoodsModelYjjdFqjq>> gankResults) {

                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            topGoodsModelYjjdFqjq = gankResults.getTop();
                                            edu_tv.setText("申请额度  " + gankResults.getTop().getMax_money());
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (normalYjjdFqjqDialog != null) {
            normalYjjdFqjqDialog.dismiss();
            normalYjjdFqjqDialog = null;
        }
        super.onDestroy();
    }
}
