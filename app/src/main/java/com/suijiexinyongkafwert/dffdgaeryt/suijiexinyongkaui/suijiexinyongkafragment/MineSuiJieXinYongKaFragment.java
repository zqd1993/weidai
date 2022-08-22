package com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkafragment;

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

import com.suijiexinyongkafwert.dffdgaeryt.R;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaadapter.MineAdapterSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaadapter.MineAdapter1SuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.SuiJieXinYongKaBaseRespModel;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.CompanyInfoModelSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkamodel.MineItemModelSuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.ApiSubscriber;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.NetError;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.XApi;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.WebViewSuiJieXinYongKaActivity;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkaactivity.AboutSuiJieXinYongKaUsActivity;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkaactivity.CancellationAccountActivitySuiJieXinYongKa;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkaui.suijiexinyongkaactivity.SettingSuiJieXinYongKaActivity;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.SuiJieXinYongKaSharedPreferencesUtilis;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkautils.ToastSuiJieXinYongKaUtil;
import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkanet.SuiJieXinYongKaApi;
import com.suijiexinyongkafwert.dffdgaeryt.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.suijiexinyongkafwert.dffdgaeryt.suijiexinyongkawidget.NormalSuiJieXinYongKaDialog;
import com.suijiexinyongkafwert.dffdgaeryt.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineSuiJieXinYongKaFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mail_tv)
    TextView mail_tv;
    @BindView(R.id.zcxy_img)
    View zcxy_img;
    @BindView(R.id.ysxy_img)
    View ysxy_img;

    private MineAdapterSuiJieXinYongKa mineAdapterSuiJieXinYongKa;
    private MineAdapter1SuiJieXinYongKa mineAdapter1SuiJieXinYongKa;
    private List<MineItemModelSuiJieXinYongKa> list, list1;
    private int[] imgRes = {R.drawable.gjsfhaery, R.drawable.lyupfjkfgj, R.drawable.dsrtaerytru};
    private String[] tvRes = {"关于我们", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalSuiJieXinYongKaDialog normalSuiJieXinYongKaDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        getCompanyInfo();
        phone = SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 3; i++) {
            MineItemModelSuiJieXinYongKa model = new MineItemModelSuiJieXinYongKa();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        mail_tv.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            ToastSuiJieXinYongKaUtil.showShort("复制成功");
        });
        zcxy_img.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 1);
            bundle.putString("url", SuiJieXinYongKaApi.getZc());
            Router.newIntent(getActivity())
                    .to(WebViewSuiJieXinYongKaActivity.class)
                    .data(bundle)
                    .launch();
        });
        ysxy_img.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 2);
            bundle.putString("url", SuiJieXinYongKaApi.getYs());
            Router.newIntent(getActivity())
                    .to(WebViewSuiJieXinYongKaActivity.class)
                    .data(bundle)
                    .launch();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_sui_jie_xin_yong_ka;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineAdapterSuiJieXinYongKa == null) {
            mineAdapterSuiJieXinYongKa = new MineAdapterSuiJieXinYongKa(getActivity());
            mineAdapterSuiJieXinYongKa.setData(list);
            mineAdapterSuiJieXinYongKa.setHasStableIds(true);
            mineAdapterSuiJieXinYongKa.setRecItemClick(new RecyclerItemCallback<MineItemModelSuiJieXinYongKa, MineAdapterSuiJieXinYongKa.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelSuiJieXinYongKa model, int tag, MineAdapterSuiJieXinYongKa.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutSuiJieXinYongKaUsActivity.class)
                                    .launch();
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(SettingSuiJieXinYongKaActivity.class)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(CancellationAccountActivitySuiJieXinYongKa.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapterSuiJieXinYongKa);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SuiJieXinYongKaSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            SuiJieXinYongKaApi.getGankService().getCompanyInfo()
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel<CompanyInfoModelSuiJieXinYongKa>>getApiTransformer())
                    .compose(XApi.<SuiJieXinYongKaBaseRespModel<CompanyInfoModelSuiJieXinYongKa>>getScheduler())
                    .compose(this.<SuiJieXinYongKaBaseRespModel<CompanyInfoModelSuiJieXinYongKa>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<SuiJieXinYongKaBaseRespModel<CompanyInfoModelSuiJieXinYongKa>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(SuiJieXinYongKaBaseRespModel<CompanyInfoModelSuiJieXinYongKa> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SuiJieXinYongKaSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (normalSuiJieXinYongKaDialog != null) {
            normalSuiJieXinYongKaDialog.dismiss();
            normalSuiJieXinYongKaDialog = null;
        }
        super.onDestroy();
    }
}
