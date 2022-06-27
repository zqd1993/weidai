package com.jwcf.zdclm.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jwcf.zdclm.R;
import com.jwcf.zdclm.ada.MineAdapterYingJi;
import com.jwcf.zdclm.mod.YingJiBaseRespModel;
import com.jwcf.zdclm.mod.ConfigYingJiModel;
import com.jwcf.zdclm.mod.MineItemModelYingJi;
import com.jwcf.zdclm.net.ApiSubscriber;
import com.jwcf.zdclm.net.NetError;
import com.jwcf.zdclm.net.XApi;
import com.jwcf.zdclm.ui.WebViewYingJiActivity;
import com.jwcf.zdclm.ui.YingJiLoginActivity;
import com.jwcf.zdclm.ui.activity.CancellationAccountActivityYingJi;
import com.jwcf.zdclm.ui.activity.FeedBackYingJiActivity;
import com.jwcf.zdclm.utils.YingJiSharedPreferencesUtilis;
import com.jwcf.zdclm.utils.ToastYingJiUtil;
import com.jwcf.zdclm.net.ApiYingJi;
import com.jwcf.zdclm.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.jwcf.zdclm.widget.NormalYingJiDialog;
import com.jwcf.zdclm.mvp.XFragment;
import com.jwcf.zdclm.ui.activity.YingJiAboutUsActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineYingJiFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;

    private MineAdapterYingJi mineAdapterYingJi, mineAdapterYingJi1;
    private List<MineItemModelYingJi> list, list1;
    private int[] imgRes = {R.drawable.fankuilai, R.drawable.zhuyi, R.drawable.yinxie, R.drawable.youjian,
            R.drawable.songtui, R.drawable.guanus, R.drawable.zhuxiao, R.drawable.dengchu};
    private String[] tvRes = {"我要反馈", "注册协议", "隐私协议", "投诉邮箱", "关闭个性化推荐", "关于我们", "注销账户", "退出登录"};
    private Bundle bundle;
    private NormalYingJiDialog normalYingJiDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mailStr = YingJiSharedPreferencesUtilis.getStringFromPref("APP_MAIL");
        phone = YingJiSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            MineItemModelYingJi model = new MineItemModelYingJi();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            if (i < 2) {
                list.add(model);
            } else {
                list1.add(model);
            }
        }
        initAdapter();
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
        mineAdapterYingJi = new MineAdapterYingJi(getActivity());
        mineAdapterYingJi.setData(list);
        mineAdapterYingJi.setHasStableIds(true);
        mineAdapterYingJi.setRecItemClick(new RecyclerItemCallback<MineItemModelYingJi, MineAdapterYingJi.ViewHolder>() {
            @Override
            public void onItemClick(int position, MineItemModelYingJi model, int tag, MineAdapterYingJi.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (position) {
                    case 0:
                        Router.newIntent(getActivity())
                                .to(FeedBackYingJiActivity.class)
                                .launch();
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", ApiYingJi.ZCXY);
                        Router.newIntent(getActivity())
                                .to(WebViewYingJiActivity.class)
                                .data(bundle)
                                .launch();
                        break;
                }
            }
        });
        rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvy.setHasFixedSize(true);
        rvy.setAdapter(mineAdapterYingJi);
        mineAdapterYingJi1 = new MineAdapterYingJi(getActivity());
        mineAdapterYingJi1.setData(list1);
        mineAdapterYingJi1.setHasStableIds(true);
        mineAdapterYingJi1.setRecItemClick(new RecyclerItemCallback<MineItemModelYingJi, MineAdapterYingJi.ViewHolder>() {
            @Override
            public void onItemClick(int position, MineItemModelYingJi model, int tag, MineAdapterYingJi.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (position) {
                    case 0:
                        bundle = new Bundle();
                        bundle.putInt("tag", 2);
                        bundle.putString("url", ApiYingJi.YSZC);
                        Router.newIntent(getActivity())
                                .to(WebViewYingJiActivity.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 1:
                        getGankData();
                        break;
                    case 2:
                        normalYingJiDialog = new NormalYingJiDialog(getActivity());
                        normalYingJiDialog.setTitle("温馨提示")
                                .setContent("关闭或开启推送")
                                .setCancelText("开启")
                                .setLeftListener(v -> {
                                    ToastYingJiUtil.showShort("开启成功");
                                    normalYingJiDialog.dismiss();
                                })
                                .setConfirmText("关闭")
                                .setRightListener(v -> {
                                    ToastYingJiUtil.showShort("关闭成功");
                                    normalYingJiDialog.dismiss();
                                }).show();
                        break;
                    case 3:
                        Router.newIntent(getActivity())
                                .to(YingJiAboutUsActivity.class)
                                .launch();
                        break;
                    case 4:
                        Router.newIntent(getActivity())
                                .to(CancellationAccountActivityYingJi.class)
                                .launch();
                        break;
                    case 5:
                        normalYingJiDialog = new NormalYingJiDialog(getActivity());
                        normalYingJiDialog.setTitle("温馨提示")
                                .setContent("确定退出当前登录")
                                .setCancelText("取消")
                                .setLeftListener(v -> {
                                    normalYingJiDialog.dismiss();
                                })
                                .setConfirmText("退出")
                                .setRightListener(v -> {
                                    normalYingJiDialog.dismiss();
                                    YingJiSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                                    Router.newIntent(getActivity())
                                            .to(YingJiLoginActivity.class)
                                            .launch();
                                    getActivity().finish();
                                }).show();
                        break;
                }
            }
        });
        rvy1.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvy1.setHasFixedSize(true);
        rvy1.setAdapter(mineAdapterYingJi1);
    }

    @Override
    public void onDestroy() {
        if (normalYingJiDialog != null) {
            normalYingJiDialog.dismiss();
            normalYingJiDialog = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(YingJiSharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            ApiYingJi.getGankService().getGankData()
                    .compose(XApi.<YingJiBaseRespModel<ConfigYingJiModel>>getApiTransformer())
                    .compose(XApi.<YingJiBaseRespModel<ConfigYingJiModel>>getScheduler())
                    .compose(this.<YingJiBaseRespModel<ConfigYingJiModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YingJiBaseRespModel<ConfigYingJiModel>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(YingJiBaseRespModel<ConfigYingJiModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    mailStr = gankResults.getData().getAppMail();
                                    YingJiSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                    normalYingJiDialog = new NormalYingJiDialog(getActivity());
                                    normalYingJiDialog.setTitle("温馨提示")
                                            .setContent(mailStr)
                                            .showOnlyBtn().show();
                                }
                            }
                        }
                    });
        }
    }
}
