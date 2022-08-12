package com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.jiekuanzhijiafragment;

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

import com.google.gson.Gson;
import com.jieuacnisdoert.naweodfigety.R;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaadapter.JieKuanZhiJiaMineAdapter;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaadapter.JieKuanZhiJiaMineAdapter1;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.BaseRespModelJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.CompanyInfoModelJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.JieKuanZhiJiaGoodsModel;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.MineItemModelJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel.JieKuanZhiJiaRequModel;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.JieKuanZhiJiaApi;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.ApiSubscriber;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.NetError;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.XApi;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.WebViewActivityJieKuanZhiJia;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.jiekuanzhijiaactivity.JieKuanZhiJiaCancellationAccountActivity;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.jiekuanzhijiaactivity.SettingJieKuanZhiJiaActivity;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaSharedPreferencesUtilis;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaStaticUtil;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiautils.JieKuanZhiJiaToastUtil;
import com.jieuacnisdoert.naweodfigety.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.jieuacnisdoert.naweodfigety.jiekuanzhijiawidget.JieKuanZhiJiaNormalDialog;
import com.jieuacnisdoert.naweodfigety.mvp.XFragment;
import com.jieuacnisdoert.naweodfigety.jiekuanzhijiaui.jiekuanzhijiaactivity.JieKuanZhiJiaAboutUsActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import okhttp3.RequestBody;

public class MineFragmentJieKuanZhiJia extends XFragment {

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
    @BindView(R.id.edu_tv)
    TextView edu_tv;

    private JieKuanZhiJiaMineAdapter jieKuanZhiJiaMineAdapter;
    private JieKuanZhiJiaMineAdapter1 jieKuanZhiJiaMineAdapter1;
    private List<MineItemModelJieKuanZhiJia> list, list1;
    private int[] imgRes = {R.drawable.xbvndrtu, R.drawable.eryrtu, R.drawable.vxgvjtdrfu, R.drawable.rtysru, R.drawable.rrtsufgj};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "系统设置", "注销账户"};
    private Bundle bundle;
    private JieKuanZhiJiaNormalDialog jieKuanZhiJiaNormalDialog;
    private String mailStr = "", phone = "", token = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        getCompanyInfo();
        aindex();
        phone = JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            MineItemModelJieKuanZhiJia model = new MineItemModelJieKuanZhiJia();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            if (i < 2) {
                list.add(model);
            } else {
                list1.add(model);
            }
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
            JieKuanZhiJiaToastUtil.showShort("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_jie_kuan_zhi_jia;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (jieKuanZhiJiaMineAdapter == null) {
            jieKuanZhiJiaMineAdapter = new JieKuanZhiJiaMineAdapter(getActivity());
            jieKuanZhiJiaMineAdapter.setData(list);
            jieKuanZhiJiaMineAdapter.setHasStableIds(true);
            jieKuanZhiJiaMineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelJieKuanZhiJia, JieKuanZhiJiaMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelJieKuanZhiJia model, int tag, JieKuanZhiJiaMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", JieKuanZhiJiaApi.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityJieKuanZhiJia.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", JieKuanZhiJiaApi.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityJieKuanZhiJia.class)
                                    .data(bundle)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(jieKuanZhiJiaMineAdapter);
        }
        if (jieKuanZhiJiaMineAdapter1 == null) {
            jieKuanZhiJiaMineAdapter1 = new JieKuanZhiJiaMineAdapter1(getActivity());
            jieKuanZhiJiaMineAdapter1.setData(list1);
            jieKuanZhiJiaMineAdapter1.setHasStableIds(true);
            jieKuanZhiJiaMineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModelJieKuanZhiJia, JieKuanZhiJiaMineAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelJieKuanZhiJia model, int tag, JieKuanZhiJiaMineAdapter1.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(JieKuanZhiJiaAboutUsActivity.class)
                                    .launch();
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(SettingJieKuanZhiJiaActivity.class)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(JieKuanZhiJiaCancellationAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(jieKuanZhiJiaMineAdapter1);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            JieKuanZhiJiaApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>getScheduler())
                    .compose(this.<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelJieKuanZhiJia<CompanyInfoModelJieKuanZhiJia> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    JieKuanZhiJiaSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    public void aindex() {
        if (!TextUtils.isEmpty(JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            token = JieKuanZhiJiaSharedPreferencesUtilis.getStringFromPref("token");
            JieKuanZhiJiaRequModel model = new JieKuanZhiJiaRequModel();
            model.setToken(token);
            RequestBody body = JieKuanZhiJiaStaticUtil.createBody(new Gson().toJson(model));
            JieKuanZhiJiaApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>getScheduler())
                    .compose(this.<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelJieKuanZhiJia<List<JieKuanZhiJiaGoodsModel>> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        edu_tv.setText(gankResults.getData().get(0).getMax_money());
                                    }
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (jieKuanZhiJiaNormalDialog != null) {
            jieKuanZhiJiaNormalDialog.dismiss();
            jieKuanZhiJiaNormalDialog = null;
        }
        super.onDestroy();
    }
}
