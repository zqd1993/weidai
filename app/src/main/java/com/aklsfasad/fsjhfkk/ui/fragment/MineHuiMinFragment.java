package com.aklsfasad.fsjhfkk.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aklsfasad.fsjhfkk.R;
import com.aklsfasad.fsjhfkk.adapter.MineHuiMinAdapter;
import com.aklsfasad.fsjhfkk.model.BaseRespHuiMinModel;
import com.aklsfasad.fsjhfkk.model.ConfigHuiMinModel;
import com.aklsfasad.fsjhfkk.model.MineItemHuiMinModel;
import com.aklsfasad.fsjhfkk.mvp.XActivity;
import com.aklsfasad.fsjhfkk.net.ApiSubscriber;
import com.aklsfasad.fsjhfkk.net.NetError;
import com.aklsfasad.fsjhfkk.net.XApi;
import com.aklsfasad.fsjhfkk.ui.LoginActivityHuiMin;
import com.aklsfasad.fsjhfkk.ui.WebHuiMinActivity;
import com.aklsfasad.fsjhfkk.ui.activity.AboutActivityHuiMin;
import com.aklsfasad.fsjhfkk.ui.activity.CancellationUserActivityHuiMin;
import com.aklsfasad.fsjhfkk.ui.activity.MoreSettingActivity;
import com.aklsfasad.fsjhfkk.utils.SharedPreferencesUtilisHuiMin;
import com.aklsfasad.fsjhfkk.utils.StaticUtilHuiMin;
import com.aklsfasad.fsjhfkk.utils.ToastUtilHuiMin;
import com.aklsfasad.fsjhfkk.net.Api;
import com.aklsfasad.fsjhfkk.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.aklsfasad.fsjhfkk.widget.NormalDialogHuiMin;
import com.aklsfasad.fsjhfkk.mvp.XFragment;
import com.aklsfasad.fsjhfkk.ui.activity.FeedBackActivityHuiMin;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineHuiMinFragment extends XFragment {
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private MineHuiMinAdapter miaoJieMineAdapter1;
    private List<MineItemHuiMinModel> list1;
    private int[] imgRes = {R.drawable.wd_icon_zcxy, R.drawable.wd_icon_yjfk,
            R.drawable.wd_icon_xxts, R.drawable.wd_tsyx, R.drawable.wd_icon_zcz, R.drawable.wd_icon_zczh};
    private String[] tvRes = {"更多信息", "意见反馈", "个性化推荐", "投诉邮箱", "注销账户", "退出登录"};
    private Bundle bundle;
    private NormalDialogHuiMin normalDialogHuiMin;
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
            miaoJieMineAdapter1 = new MineHuiMinAdapter(getActivity());
            miaoJieMineAdapter1.setData(list1);
            miaoJieMineAdapter1.setHasStableIds(true);
            miaoJieMineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemHuiMinModel, MineHuiMinAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemHuiMinModel model, int tag, MineHuiMinAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            StaticUtilHuiMin.getValue((XActivity) getActivity(), MoreSettingActivity.class, null);
                            break;
                        case 1:
                            StaticUtilHuiMin.getValue((XActivity) getActivity(), FeedBackActivityHuiMin.class, null);
                            break;
                        case 2:
                            normalDialogHuiMin = new NormalDialogHuiMin(getActivity());
                            normalDialogHuiMin.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        ToastUtilHuiMin.showShort("开启成功");
                                        normalDialogHuiMin.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        ToastUtilHuiMin.showShort("关闭成功");
                                        normalDialogHuiMin.dismiss();
                                    }).show();
                            break;
                        case 3:
                            getGankData();
                            break;
                        case 4:
                            StaticUtilHuiMin.getValue((XActivity) getActivity(), CancellationUserActivityHuiMin.class, null);
                            break;
                        case 5:
                            normalDialogHuiMin = new NormalDialogHuiMin(getActivity());
                            normalDialogHuiMin.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalDialogHuiMin.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        normalDialogHuiMin.dismiss();
                                        SharedPreferencesUtilisHuiMin.saveStringIntoPref("phone", "");
                                        StaticUtilHuiMin.getValue((XActivity) getActivity(), LoginActivityHuiMin.class, null, true);
                                    }).show();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(miaoJieMineAdapter1);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list1 = new ArrayList<>();
        mailStr = SharedPreferencesUtilisHuiMin.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesUtilisHuiMin.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 6; i++) {
            MineItemHuiMinModel model = new MineItemHuiMinModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list1.add(model);
        }
        initAdapter();
    }

    @Override
    public void onDestroy() {
        if (normalDialogHuiMin != null) {
            normalDialogHuiMin.dismiss();
            normalDialogHuiMin = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisHuiMin.getStringFromPref("HTTP_API_URL"))) {
            Api.getGankService().getGankData()
                    .compose(XApi.<BaseRespHuiMinModel<ConfigHuiMinModel>>getApiTransformer())
                    .compose(XApi.<BaseRespHuiMinModel<ConfigHuiMinModel>>getScheduler())
                    .compose(this.<BaseRespHuiMinModel<ConfigHuiMinModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespHuiMinModel<ConfigHuiMinModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                        }

                        @Override
                        public void onNext(BaseRespHuiMinModel<ConfigHuiMinModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    mailStr = gankResults.getData().getAppMail();
                                    SharedPreferencesUtilisHuiMin.saveStringIntoPref("APP_MAIL", mailStr);
                                    normalDialogHuiMin = new NormalDialogHuiMin(getActivity());
                                    normalDialogHuiMin.setTitle("温馨提示")
                                            .setContent(mailStr)
                                            .showOnlyBtn().show();
                                }
                            }
                        }
                    });
        }
    }
}
