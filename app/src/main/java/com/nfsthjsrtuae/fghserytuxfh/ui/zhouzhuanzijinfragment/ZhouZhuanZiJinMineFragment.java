package com.nfsthjsrtuae.fghserytuxfh.ui.zhouzhuanzijinfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.ui.ZhouZhuanZiJinWebViewActivity;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinadapter.ZhouZhuanZiJinMineAdapter;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinadapter.ZhouZhuanZiJinMineAdapter1;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinBaseRespModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinCompanyInfoModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinmodel.ZhouZhuanZiJinMineItemModel;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.ZhouZhuanZiJinApi;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.ApiSubscriber;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinnet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.ui.zhouzhuanzijinactivity.ZhouZhuanZiJinCancellationAccountActivity;
import com.nfsthjsrtuae.fghserytuxfh.ui.zhouzhuanzijinactivity.ZhouZhuanZiJinSettingActivity;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinutils.ZhouZhuanZiJinSharedPreferencesUtilis;
import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinutils.ZhouZhuanZiJinToastUtil;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.nfsthjsrtuae.fghserytuxfh.zhouzhuanzijinwidget.NormalDialog;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XFragment;
import com.nfsthjsrtuae.fghserytuxfh.ui.zhouzhuanzijinactivity.AboutUsActivityZhouZhuanZiJin;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ZhouZhuanZiJinMineFragment extends XFragment {

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

    private ZhouZhuanZiJinMineAdapter zhouZhuanZiJinMineAdapter;
    private ZhouZhuanZiJinMineAdapter1 zhouZhuanZiJinMineAdapter1;
    private List<ZhouZhuanZiJinMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.xxfhrtu, R.drawable.weteryrtu, R.drawable.xtyhsrfxgjh, R.drawable.ertyfghyr, R.drawable.edhtudr, R.drawable.xxfdghrtuy};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "投诉邮箱", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalDialog normalDialog;
    private String mailStr = "", phone = "", token = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        getCompanyInfo();
        phone = ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 6; i++) {
            ZhouZhuanZiJinMineItemModel model = new ZhouZhuanZiJinMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list1.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        mail_sl.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            ZhouZhuanZiJinToastUtil.showShort("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_zhou_zhuan_zi_jin;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
//        if (mineAdapter == null) {
//            mineAdapter = new MineAdapter(getActivity());
//            mineAdapter.setData(list);
//            mineAdapter.setHasStableIds(true);
//            mineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModel, MineAdapter.ViewHolder>() {
//                @Override
//                public void onItemClick(int position, MineItemModel model, int tag, MineAdapter.ViewHolder holder) {
//                    super.onItemClick(position, model, tag, holder);
//                    switch (position) {
//                        case 0:
//                            bundle = new Bundle();
//                            bundle.putInt("tag", 1);
//                            bundle.putString("url", Api.getZc());
//                            Router.newIntent(getActivity())
//                                    .to(WebViewActivity.class)
//                                    .data(bundle)
//                                    .launch();
//                            break;
//                        case 1:
//                            bundle = new Bundle();
//                            bundle.putInt("tag", 2);
//                            bundle.putString("url", Api.getYs());
//                            Router.newIntent(getActivity())
//                                    .to(WebViewActivity.class)
//                                    .data(bundle)
//                                    .launch();
//                            break;
//                        case 2:
//                            Router.newIntent(getActivity())
//                                    .to(AboutUsActivity.class)
//                                    .launch();
//                            break;
//                    }
//                }
//            });
//            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//            rvy.setHasFixedSize(true);
//            rvy.setAdapter(mineAdapter);
//        }
        if (zhouZhuanZiJinMineAdapter1 == null) {
            zhouZhuanZiJinMineAdapter1 = new ZhouZhuanZiJinMineAdapter1(getActivity());
            zhouZhuanZiJinMineAdapter1.setData(list1);
            zhouZhuanZiJinMineAdapter1.setHasStableIds(true);
            zhouZhuanZiJinMineAdapter1.setRecItemClick(new RecyclerItemCallback<ZhouZhuanZiJinMineItemModel, ZhouZhuanZiJinMineAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, ZhouZhuanZiJinMineItemModel model, int tag, ZhouZhuanZiJinMineAdapter1.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ZhouZhuanZiJinApi.getZc());
                            Router.newIntent(getActivity())
                                    .to(ZhouZhuanZiJinWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ZhouZhuanZiJinApi.getYs());
                            Router.newIntent(getActivity())
                                    .to(ZhouZhuanZiJinWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivityZhouZhuanZiJin.class)
                                    .launch();
                            break;
                        case 3:
                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText(null, mailStr);
                            clipboard.setPrimaryClip(clipData);
                            ZhouZhuanZiJinToastUtil.showShort("复制成功");
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(ZhouZhuanZiJinSettingActivity.class)
                                    .launch();
                            break;
                        case 5:
                            Router.newIntent(getActivity())
                                    .to(ZhouZhuanZiJinCancellationAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(zhouZhuanZiJinMineAdapter1);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(ZhouZhuanZiJinSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanZiJinApi.getGankService().getCompanyInfo()
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinCompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinCompanyInfoModel>>getScheduler())
                    .compose(this.<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinCompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinCompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(ZhouZhuanZiJinBaseRespModel<ZhouZhuanZiJinCompanyInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    ZhouZhuanZiJinSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
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
