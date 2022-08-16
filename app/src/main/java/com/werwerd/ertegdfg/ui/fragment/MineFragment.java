package com.werwerd.ertegdfg.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.werwerd.ertegdfg.R;
import com.werwerd.ertegdfg.adapter.MineAdapterYouXin;
import com.werwerd.ertegdfg.model.BaseRespYouXinModel;
import com.werwerd.ertegdfg.model.ConfigYouXinModel;
import com.werwerd.ertegdfg.model.MineItemModelYouXin;
import com.werwerd.ertegdfg.net.ApiSubscriber;
import com.werwerd.ertegdfg.net.NetError;
import com.werwerd.ertegdfg.net.XApi;
import com.werwerd.ertegdfg.ui.LoginYouXinActivity;
import com.werwerd.ertegdfg.ui.WebActivity;
import com.werwerd.ertegdfg.ui.activity.AboutYouXinActivity;
import com.werwerd.ertegdfg.ui.activity.CancellationUserYouXinActivity;
import com.werwerd.ertegdfg.ui.activity.MoreInfoActivity;
import com.werwerd.ertegdfg.utils.SharedPreferencesYouXinUtilis;
import com.werwerd.ertegdfg.utils.StaticYouXinUtil;
import com.werwerd.ertegdfg.utils.ToastYouXinUtil;
import com.werwerd.ertegdfg.net.Api;
import com.werwerd.ertegdfg.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.werwerd.ertegdfg.widget.NormalYouXinDialog;
import com.werwerd.ertegdfg.mvp.XFragment;
import com.werwerd.ertegdfg.ui.activity.FeedBackYouXinActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragment extends XFragment {

    @BindView(R.id.rvy_2)
    RecyclerView rvy2;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private MineAdapterYouXin miaoJieMineAdapter1, miaoJieMineAdapter2;
    private List<MineItemModelYouXin> list1, list2;
    private int[] imgRes = {R.drawable.wd_icon_yjfk,
            R.drawable.wd_icon_xxts, R.drawable.wd_tsyx, R.drawable.wd_icon_zcz, R.drawable.wd_icon_zczh, R.drawable.wd_icon_gywm};
    private String[] tvRes = {"意见反馈", "个性化推荐", "投诉邮箱", "注销账户", "退出登录", "更多信息"};
    private Bundle bundle;
    private NormalYouXinDialog normalYouXinDialog;
    private String mailStr = "", phone = "";


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
            miaoJieMineAdapter1 = new MineAdapterYouXin(getActivity());
            miaoJieMineAdapter1.setData(list1);
            miaoJieMineAdapter1.setHasStableIds(true);
            miaoJieMineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModelYouXin, MineAdapterYouXin.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelYouXin model, int tag, MineAdapterYouXin.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(FeedBackYouXinActivity.class)
                                    .launch();
                            break;
                        case 1:
                            normalYouXinDialog = new NormalYouXinDialog(getActivity());
                            normalYouXinDialog.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        ToastYouXinUtil.showShort("开启成功");
                                        normalYouXinDialog.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        ToastYouXinUtil.showShort("关闭成功");
                                        normalYouXinDialog.dismiss();
                                    }).show();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(miaoJieMineAdapter1);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        mailStr = SharedPreferencesYouXinUtilis.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesYouXinUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 6; i++) {
            MineItemModelYouXin model = new MineItemModelYouXin();
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


    private void initAdapter2() {
        if (miaoJieMineAdapter2 == null) {
            miaoJieMineAdapter2 = new MineAdapterYouXin(getActivity());
            miaoJieMineAdapter2.setData(list2);
            miaoJieMineAdapter2.setHasStableIds(true);
            miaoJieMineAdapter2.setRecItemClick(new RecyclerItemCallback<MineItemModelYouXin, MineAdapterYouXin.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelYouXin model, int tag, MineAdapterYouXin.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            getGankData();
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(CancellationUserYouXinActivity.class)
                                    .launch();
                            break;
                        case 2:
                            normalYouXinDialog = new NormalYouXinDialog(getActivity());
                            normalYouXinDialog.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalYouXinDialog.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        normalYouXinDialog.dismiss();
                                        SharedPreferencesYouXinUtilis.saveStringIntoPref("phone", "");
                                        Router.newIntent(getActivity())
                                                .to(LoginYouXinActivity.class)
                                                .launch();
                                        getActivity().finish();
                                    }).show();
                            break;
                        case 3:
                            Router.newIntent(getActivity())
                                    .to(MoreInfoActivity.class)
                                    .launch();
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
            Api.getGankService().getGankData()
                    .compose(XApi.<BaseRespYouXinModel<ConfigYouXinModel>>getApiTransformer())
                    .compose(XApi.<BaseRespYouXinModel<ConfigYouXinModel>>getScheduler())
                    .compose(this.<BaseRespYouXinModel<ConfigYouXinModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespYouXinModel<ConfigYouXinModel>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseRespYouXinModel<ConfigYouXinModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    mailStr = gankResults.getData().getAppMail();
                                    SharedPreferencesYouXinUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                    normalYouXinDialog = new NormalYouXinDialog(getActivity());
                                    normalYouXinDialog.setTitle("温馨提示")
                                            .setContent(mailStr)
                                            .showOnlyBtn().show();
                                }
                            }
                        }
                    });
    }

    @Override
    public void onDestroy() {
        if (normalYouXinDialog != null) {
            normalYouXinDialog.dismiss();
            normalYouXinDialog = null;
        }
        super.onDestroy();
    }
}
