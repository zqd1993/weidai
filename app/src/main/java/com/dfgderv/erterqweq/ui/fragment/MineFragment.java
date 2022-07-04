package com.dfgderv.erterqweq.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dfgderv.erterqweq.R;
import com.dfgderv.erterqweq.adapter.MiaoJieMineAdapter;
import com.dfgderv.erterqweq.model.BaseRespModel;
import com.dfgderv.erterqweq.model.ConfigModel;
import com.dfgderv.erterqweq.model.MineItemModel;
import com.dfgderv.erterqweq.net.ApiSubscriber;
import com.dfgderv.erterqweq.net.NetError;
import com.dfgderv.erterqweq.net.XApi;
import com.dfgderv.erterqweq.ui.LoginActivity;
import com.dfgderv.erterqweq.ui.WebViewActivity;
import com.dfgderv.erterqweq.ui.activity.AboutMiaoJieActivity;
import com.dfgderv.erterqweq.ui.activity.CancellationUserActivity;
import com.dfgderv.erterqweq.utils.SharedPreferencesUtilis;
import com.dfgderv.erterqweq.utils.StaticUtil;
import com.dfgderv.erterqweq.utils.ToastUtil;
import com.dfgderv.erterqweq.net.Api;
import com.dfgderv.erterqweq.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.dfgderv.erterqweq.widget.NormalDialog;
import com.dfgderv.erterqweq.mvp.XFragment;
import com.dfgderv.erterqweq.ui.activity.FeedBackActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragment extends XFragment {

    @BindView(R.id.rvy_2)
    RecyclerView rvy2;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private MiaoJieMineAdapter miaoJieMineAdapter1, miaoJieMineAdapter2;
    private List<MineItemModel> list1, list2;
    private int[] imgRes = {R.drawable.wd_icon_zcxy, R.drawable.wd_icon_ysxy, R.drawable.wd_icon_yjfk, R.drawable.wd_icon_gywm,
            R.drawable.wd_icon_xxts, R.drawable.wd_tsyx, R.drawable.wd_icon_zcz, R.drawable.wd_icon_zczh};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "个性化推荐", "投诉邮箱", "注销账户", "退出登录"};
    private Bundle bundle;
    private NormalDialog normalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        mailStr = SharedPreferencesUtilis.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            MineItemModel model = new MineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            if (i < 2) {
                list1.add(model);
            } else {
                list2.add(model);
            }
        }
        initAdapter();
        initAdapter2();
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
        if (miaoJieMineAdapter1 == null) {
            miaoJieMineAdapter1 = new MiaoJieMineAdapter(getActivity());
            miaoJieMineAdapter1.setData(list1);
            miaoJieMineAdapter1.setHasStableIds(true);
            miaoJieMineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModel, MiaoJieMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModel model, int tag, MiaoJieMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", Api.PRIVACY_POLICY);
                            Router.newIntent(getActivity())
                                    .to(WebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", Api.USER_SERVICE_AGREEMENT);
                            Router.newIntent(getActivity())
                                    .to(WebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(miaoJieMineAdapter1);
        }
    }

    private void initAdapter2() {
        if (miaoJieMineAdapter2 == null) {
            miaoJieMineAdapter2 = new MiaoJieMineAdapter(getActivity());
            miaoJieMineAdapter2.setData(list2);
            miaoJieMineAdapter2.setHasStableIds(true);
            miaoJieMineAdapter2.setRecItemClick(new RecyclerItemCallback<MineItemModel, MiaoJieMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModel model, int tag, MiaoJieMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(FeedBackActivity.class)
                                    .launch();
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(AboutMiaoJieActivity.class)
                                    .launch();
                            break;
                        case 2:
                            normalDialog = new NormalDialog(getActivity());
                            normalDialog.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        ToastUtil.showShort("开启成功");
                                        normalDialog.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        ToastUtil.showShort("关闭成功");
                                        normalDialog.dismiss();
                                    }).show();
                            break;
                        case 3:
                            getGankData();
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(CancellationUserActivity.class)
                                    .launch();
                            break;
                        case 5:
                            normalDialog = new NormalDialog(getActivity());
                            normalDialog.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalDialog.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        normalDialog.dismiss();
                                        SharedPreferencesUtilis.saveStringIntoPref("phone", "");
                                        Router.newIntent(getActivity())
                                                .to(LoginActivity.class)
                                                .launch();
                                        getActivity().finish();
                                    }).show();
                            break;
                    }
                }
            });
            rvy2.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy2.setHasFixedSize(true);
            rvy2.setAdapter(miaoJieMineAdapter2);
        }
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            Api.getGankService().getGankData()
                    .compose(XApi.<BaseRespModel<ConfigModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<ConfigModel>>getScheduler())
                    .compose(this.<BaseRespModel<ConfigModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<ConfigModel>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseRespModel<ConfigModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    mailStr = gankResults.getData().getAppMail();
                                    SharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
                                    normalDialog = new NormalDialog(getActivity());
                                    normalDialog.setTitle("温馨提示")
                                            .setContent(mailStr)
                                            .showOnlyBtn().show();
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
