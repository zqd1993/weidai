package com.hgfgrfcv.utyhfgbrgf.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hgfgrfcv.utyhfgbrgf.R;
import com.hgfgrfcv.utyhfgbrgf.adapter.MineAdapter;
import com.hgfgrfcv.utyhfgbrgf.model.BaseRespModel;
import com.hgfgrfcv.utyhfgbrgf.model.CompanyInfoModel;
import com.hgfgrfcv.utyhfgbrgf.model.MineItemModel;
import com.hgfgrfcv.utyhfgbrgf.net.ApiSubscriber;
import com.hgfgrfcv.utyhfgbrgf.net.NetError;
import com.hgfgrfcv.utyhfgbrgf.net.XApi;
import com.hgfgrfcv.utyhfgbrgf.ui.WebViewActivity;
import com.hgfgrfcv.utyhfgbrgf.ui.activity.SettingActivity;
import com.hgfgrfcv.utyhfgbrgf.utils.SharedPreferencesUtilis;
import com.hgfgrfcv.utyhfgbrgf.utils.ToastUtil;
import com.hgfgrfcv.utyhfgbrgf.net.Api;
import com.hgfgrfcv.utyhfgbrgf.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.hgfgrfcv.utyhfgbrgf.widget.NormalDialog;
import com.hgfgrfcv.utyhfgbrgf.mvp.XFragment;
import com.hgfgrfcv.utyhfgbrgf.ui.activity.AboutUsActivity;
import com.hgfgrfcv.utyhfgbrgf.ui.activity.CancellationAccountActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MineAdapter mineAdapter;
    private List<MineItemModel> list;
    private int[] imgRes = {R.drawable.wd_icon_gywm, R.drawable.wd_icon_ysxy, R.drawable.wd_icon_yjfk,
            R.drawable.wd_tsyx, R.drawable.wd_icon_xxts, R.drawable.wd_icon_zczh};
    private String[] tvRes = {"关于我们", "隐私协议", "注册协议", "投诉邮箱", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalDialog normalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        getCompanyInfo();
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 6; i++) {
            MineItemModel model = new MineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
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
        if (mineAdapter == null) {
            mineAdapter = new MineAdapter(getActivity());
            mineAdapter.setData(list);
            mineAdapter.setHasStableIds(true);
            mineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModel, MineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModel model, int tag, MineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivity.class)
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
                        case 2:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", Api.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 3:
                            normalDialog = new NormalDialog(getActivity());
                            normalDialog.setTitle("投诉邮箱")
                                    .setContent(mailStr)
                                    .setCancelText("复制")
                                    .setLeftListener(v1 -> {
                                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clipData = ClipData.newPlainText(null, mailStr);
                                        clipboard.setPrimaryClip(clipData);
                                        ToastUtil.showShort("复制成功");
                                        normalDialog.dismiss();
                                    })
                                    .setConfirmText("取消")
                                    .setRightListener(v2 -> {
                                        normalDialog.dismiss();
                                    }).show();
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(SettingActivity.class)
                                    .launch();
                            break;
                        case 5:
                            Router.newIntent(getActivity())
                                    .to(CancellationAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(Api.API_BASE_URL)) {
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
                                    SharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
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
        if (normalDialog != null) {
            normalDialog.dismiss();
            normalDialog = null;
        }
        super.onDestroy();
    }
}
