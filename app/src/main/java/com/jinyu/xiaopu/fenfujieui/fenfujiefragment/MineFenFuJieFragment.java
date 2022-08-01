package com.jinyu.xiaopu.fenfujieui.fenfujiefragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jinyu.xiaopu.R;
import com.jinyu.xiaopu.fenfujieadapter.FenFuJieMineAdapter;
import com.jinyu.xiaopu.fenfujieadapter.MineFenFuJieAdapter1;
import com.jinyu.xiaopu.fenfujiemodel.BaseRespModelFenFuJie;
import com.jinyu.xiaopu.fenfujiemodel.FenFuJieConfigModel;
import com.jinyu.xiaopu.fenfujiemodel.FenFuJieMineItemModel;
import com.jinyu.xiaopu.mvp.XActivity;
import com.jinyu.xiaopu.fenfujienet.ApiSubscriber;
import com.jinyu.xiaopu.fenfujienet.NetError;
import com.jinyu.xiaopu.fenfujienet.XApi;
import com.jinyu.xiaopu.fenfujieui.LoginFenFuJieActivity;
import com.jinyu.xiaopu.fenfujieui.WebViewActivityFenFuJie;
import com.jinyu.xiaopu.fenfujieutils.SharedPreferencesUtilisFenFuJie;
import com.jinyu.xiaopu.fenfujieutils.StaticFenFuJieUtil;
import com.jinyu.xiaopu.fenfujieutils.ToastFenFuJieUtil;
import com.jinyu.xiaopu.fenfujienet.FenFuJieApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.jinyu.xiaopu.fenfujiewidget.NormalDialogFenFuJie;
import com.jinyu.xiaopu.mvp.XFragment;
import com.jinyu.xiaopu.fenfujieui.fenfujieactivity.FenFuJieAboutUsActivity;
import com.jinyu.xiaopu.fenfujieui.fenfujieactivity.CancellationAccountActivityFenFuJie;
import com.jinyu.xiaopu.fenfujieui.fenfujieactivity.FeedBackFenFuJieActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFenFuJieFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private FenFuJieMineAdapter fenFuJieMineAdapter;
    private MineFenFuJieAdapter1 mineFenFuJieAdapter1;
    private List<FenFuJieMineItemModel> list, list1;
    private int[] imgRes = {R.drawable.xfhsrtu, R.drawable.qwetgzdry, R.drawable.xeryrtu, R.drawable.fstsryurtu,
            R.drawable.llfyisrt, R.drawable.xfghrstusr, R.drawable.xxdfghsrtu, R.drawable.xfghsrtus};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "个性化推荐", "投诉邮箱", "注销账户", "退出登录"};
    private Bundle bundle;
    private NormalDialogFenFuJie normalDialogFenFuJie;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mailStr = SharedPreferencesUtilisFenFuJie.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesUtilisFenFuJie.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            FenFuJieMineItemModel model = new FenFuJieMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            if (i < 4) {
                list.add(model);
            } else {
                list1.add(model);
            }
        }
        initAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_fen_fu_jie;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (fenFuJieMineAdapter == null) {
            fenFuJieMineAdapter = new FenFuJieMineAdapter(getActivity());
            fenFuJieMineAdapter.setData(list);
            fenFuJieMineAdapter.setHasStableIds(true);
            fenFuJieMineAdapter.setRecItemClick(new RecyclerItemCallback<FenFuJieMineItemModel, FenFuJieMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, FenFuJieMineItemModel model, int tag, FenFuJieMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", FenFuJieApi.PRIVACY_POLICY);
                            StaticFenFuJieUtil.getValue((XActivity) getActivity(), WebViewActivityFenFuJie.class, bundle);
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", FenFuJieApi.USER_SERVICE_AGREEMENT);
                            StaticFenFuJieUtil.getValue((XActivity) getActivity(), WebViewActivityFenFuJie.class, bundle);
                            break;
                        case 2:
                            StaticFenFuJieUtil.getValue((XActivity) getActivity(), FeedBackFenFuJieActivity.class, null);
                            break;
                        case 3:
                            StaticFenFuJieUtil.getValue((XActivity) getActivity(), FenFuJieAboutUsActivity.class, null);
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(fenFuJieMineAdapter);
        }
        if (mineFenFuJieAdapter1 == null) {
            mineFenFuJieAdapter1 = new MineFenFuJieAdapter1(getActivity());
            mineFenFuJieAdapter1.setData(list1);
            mineFenFuJieAdapter1.setHasStableIds(true);
            mineFenFuJieAdapter1.setRecItemClick(new RecyclerItemCallback<FenFuJieMineItemModel, MineFenFuJieAdapter1.ViewHolder>() {
                @Override
                public void onItemClick(int position, FenFuJieMineItemModel model, int tag, MineFenFuJieAdapter1.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            normalDialogFenFuJie = new NormalDialogFenFuJie(getActivity());
                            normalDialogFenFuJie.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        ToastFenFuJieUtil.showShort("开启成功");
                                        normalDialogFenFuJie.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        ToastFenFuJieUtil.showShort("关闭成功");
                                        normalDialogFenFuJie.dismiss();
                                    }).show();
                            break;
                        case 1:
                            getGankData();
                            break;
                        case 2:
                            StaticFenFuJieUtil.getValue((XActivity) getActivity(), CancellationAccountActivityFenFuJie.class, null);
                            break;
                        case 3:
                            normalDialogFenFuJie = new NormalDialogFenFuJie(getActivity());
                            normalDialogFenFuJie.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalDialogFenFuJie.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        normalDialogFenFuJie.dismiss();
                                        SharedPreferencesUtilisFenFuJie.saveStringIntoPref("phone", "");
                                        StaticFenFuJieUtil.getValue((XActivity) getActivity(), LoginFenFuJieActivity.class, null, true);
                                    }).show();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(mineFenFuJieAdapter1);
        }
    }

    @Override
    public void onDestroy() {
        if (normalDialogFenFuJie != null) {
            normalDialogFenFuJie.dismiss();
            normalDialogFenFuJie = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
        FenFuJieApi.getGankService().getGankData()
                .compose(XApi.<BaseRespModelFenFuJie<FenFuJieConfigModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelFenFuJie<FenFuJieConfigModel>>getScheduler())
                .compose(this.<BaseRespModelFenFuJie<FenFuJieConfigModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelFenFuJie<FenFuJieConfigModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespModelFenFuJie<FenFuJieConfigModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                mailStr = gankResults.getData().getAppMail();
                                SharedPreferencesUtilisFenFuJie.saveStringIntoPref("APP_MAIL", mailStr);
                                normalDialogFenFuJie = new NormalDialogFenFuJie(getActivity());
                                normalDialogFenFuJie.setTitle("温馨提示")
                                        .setContent(mailStr)
                                        .showOnlyBtn().show();
                            }
                        }
                    }
                });
    }
}
