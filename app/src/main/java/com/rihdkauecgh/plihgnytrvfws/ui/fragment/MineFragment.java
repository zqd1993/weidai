package com.rihdkauecgh.plihgnytrvfws.ui.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.adapter.MineAdapter;
import com.rihdkauecgh.plihgnytrvfws.model.MineItemModel;
import com.rihdkauecgh.plihgnytrvfws.ui.LoginActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.WebViewActivity;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.ToastUtil;
import com.rihdkauecgh.plihgnytrvfws.net.Api;
import com.rihdkauecgh.plihgnytrvfws.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.rihdkauecgh.plihgnytrvfws.widget.NormalDialog;
import com.rihdkauecgh.plihgnytrvfws.mvp.XFragment;
import com.rihdkauecgh.plihgnytrvfws.ui.activity.AboutUsActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.activity.CancellationAccountActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.activity.FeedBackActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;

    private MineAdapter mineAdapter, mineAdapter1;
    private List<MineItemModel> list, list1;
    private int[] imgRes = {R.drawable.wd_tsyx, R.drawable.wd_icon_yjfk, R.drawable.wd_icon_ysxy, R.drawable.wd_icon_gywm,
            R.drawable.wd_icon_xxts, R.drawable.wd_icon_zczh};
    private String[] tvRes = {"投诉邮箱", "注册协议", "隐私协议", "关于我们", "关闭个性化推荐", "注销账户"};
    private Bundle bundle;
    private NormalDialog normalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mailStr = SharedPreferencesUtilis.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 6; i++) {
            MineItemModel model = new MineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            if (i < 2) {
                list.add(model);
            } else {
                list1.add(model);
            }
        }
        initAdapter();
        initAdapter1();
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
                            normalDialog = new NormalDialog(getActivity());
                            normalDialog.setTitle("温馨提示")
                                    .setContent(mailStr)
                                    .showOnlyBtn().show();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", Api.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter);
        }
    }

    private void initAdapter1() {
        if (mineAdapter1 == null) {
            mineAdapter1 = new MineAdapter(getActivity());
            mineAdapter1.setData(list1);
            mineAdapter1.setHasStableIds(true);
            mineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModel, MineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModel model, int tag, MineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", Api.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivity.class)
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
//                            Router.newIntent(getActivity())
//                                    .to(CancellationAccountActivity.class)
//                                    .launch();
                            normalDialog = new NormalDialog(getActivity());
                            normalDialog.setTitle("温馨提示")
                                    .setContent("确定注销账号并退出当前登录?")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalDialog.dismiss();
                                    })
                                    .setConfirmText("确定")
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
            rvy1.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(mineAdapter1);
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
