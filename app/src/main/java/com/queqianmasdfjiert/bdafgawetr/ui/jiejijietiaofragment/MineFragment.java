package com.queqianmasdfjiert.bdafgawetr.ui.jiejijietiaofragment;

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

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.adapter.MineAdapter;
import com.queqianmasdfjiert.bdafgawetr.adapter.MineAdapter1;
import com.queqianmasdfjiert.bdafgawetr.net.Api;
import com.queqianmasdfjiert.bdafgawetr.ui.WebViewActivity;
import com.queqianmasdfjiert.bdafgawetr.ui.jiejijietiaoactivity.CancellationAccountActivity;
import com.queqianmasdfjiert.bdafgawetr.model.BaseRespModel;
import com.queqianmasdfjiert.bdafgawetr.model.CompanyInfoModel;
import com.queqianmasdfjiert.bdafgawetr.model.MineItemModel;
import com.queqianmasdfjiert.bdafgawetr.net.ApiSubscriber;
import com.queqianmasdfjiert.bdafgawetr.net.NetError;
import com.queqianmasdfjiert.bdafgawetr.net.XApi;
import com.queqianmasdfjiert.bdafgawetr.ui.jiejijietiaoactivity.AboutUsActivity;
import com.queqianmasdfjiert.bdafgawetr.ui.jiejijietiaoactivity.SettingActivity;
import com.queqianmasdfjiert.bdafgawetr.utils.SharedPreferencesUtilis;
import com.queqianmasdfjiert.bdafgawetr.utils.ToastUtil;
import com.queqianmasdfjiert.bdafgawetr.widget.NormalDialog;
import com.queqianmasdfjiert.bdafgawetr.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.queqianmasdfjiert.bdafgawetr.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragment extends XFragment {

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

    private MineAdapter mineAdapter;
    private MineAdapter1 mineAdapter1;
    private List<MineItemModel> list, list1;
    private int[] imgRes = {R.drawable.llpyfujxfgh, R.drawable.qrxfgfgui, R.drawable.xcvnbsdrurtaehjfxg,
            R.drawable.xcvbseruaru, R.drawable.xcvberyrsuazdhxfgh};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalDialog normalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            MineItemModel model = new MineItemModel();
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
            ToastUtil.showShort("复制成功");
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getCompanyInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        mineAdapter1 = new MineAdapter1(getActivity());
        mineAdapter1.setData(list);
        mineAdapter1.setHasStableIds(true);
        mineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModel, MineAdapter1.ViewHolder>() {
            @Override
            public void onItemClick(int position, MineItemModel model, int tag, MineAdapter1.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (position) {
                    case 0:
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", Api.getZc());
                        Router.newIntent(getActivity())
                                .to(WebViewActivity.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putInt("tag", 2);
                        bundle.putString("url", Api.getYs());
                        Router.newIntent(getActivity())
                                .to(WebViewActivity.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 2:
                        Router.newIntent(getActivity())
                                .to(AboutUsActivity.class)
                                .launch();
                        break;
                    case 3:
                        Router.newIntent(getActivity())
                                .to(SettingActivity.class)
                                .launch();
                        break;
                    case 4:
                        Router.newIntent(getActivity())
                                .to(CancellationAccountActivity.class)
                                .launch();
                        break;
                }
            }
        });
        rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvy.setHasFixedSize(true);
        rvy.setAdapter(mineAdapter1);
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            Api.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModel<CompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<CompanyInfoModel>>getScheduler())
                    .compose(this.<BaseRespModel<CompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<CompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModel<CompanyInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (normalDialog != null) {
            normalDialog.dismiss();
            normalDialog = null;
        }
        super.onDestroy();
    }
}
