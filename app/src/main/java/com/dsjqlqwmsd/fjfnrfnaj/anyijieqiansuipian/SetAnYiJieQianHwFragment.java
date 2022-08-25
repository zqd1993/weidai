package com.dsjqlqwmsd.fjfnrfnaj.anyijieqiansuipian;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsjqlqwmsd.fjfnrfnaj.R;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiankongjian.RemindAnYiJieQianHwDialog;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianyemian.JumpH5AnYiJieQianHwActivity;
import com.dsjqlqwmsd.fjfnrfnaj.mvp.XActivity;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianshiti.ConfigAnYiJieQianHwEntity;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianyemian.AboutAnYiJieQianHwInfoActivity;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianyemian.DlAnYiJieQianHwActivity;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianyemian.FeedbackAnYiJieQianHwActivity;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianyemian.SetItemAnYiJieQianHwAdapter;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianyemian.ZhuXiaoAnYiJieQianHwActivity;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianjiekou.AnYiJieQianHwApi;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianshiti.BaseAnYiJieQianHwModel;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianshiti.ProductAnYiJieQianHwModel;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqianshiti.SetModelAnYiJieQianHw;
import com.dsjqlqwmsd.fjfnrfnaj.mvp.XFragment;
import com.dsjqlqwmsd.fjfnrfnaj.net.ApiSubscriber;
import com.dsjqlqwmsd.fjfnrfnaj.net.NetError;
import com.dsjqlqwmsd.fjfnrfnaj.net.XApi;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiangongju.AnYiJieQianHwMyToast;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiangongju.OpenAnYiJieQianHwUtil;
import com.dsjqlqwmsd.fjfnrfnaj.anyijieqiangongju.PreferencesAnYiJieQianHwOpenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetAnYiJieQianHwFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.logout_btn)
    View logout_btn;

    private ProductAnYiJieQianHwModel productAnYiJieQianHwModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAnYiJieQianHwAdapter setItemAdapter;

    private RemindAnYiJieQianHwDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesAnYiJieQianHwOpenUtil.getString("app_mail");
        phone = PreferencesAnYiJieQianHwOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        logout_btn.setOnClickListener(v -> {
            dialog = new RemindAnYiJieQianHwDialog(getActivity()).setCancelText("取消")
                    .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
            dialog.setOnButtonClickListener(new RemindAnYiJieQianHwDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    PreferencesAnYiJieQianHwOpenUtil.saveString("phone", "");
                    OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), DlAnYiJieQianHwActivity.class, null, true);
                }

                @Override
                public void onCancelClicked() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.an_yi_jie_qian_fragment_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetModelAnYiJieQianHw model3 = new SetModelAnYiJieQianHw(R.drawable.qrewrzdhg, "关于我们");
        SetModelAnYiJieQianHw model1 = new SetModelAnYiJieQianHw(R.drawable.qrewrzdhg, "隐私协议");
        SetModelAnYiJieQianHw model2 = new SetModelAnYiJieQianHw(R.drawable.qrewrzdhg, "注册协议");
        SetModelAnYiJieQianHw model5 = new SetModelAnYiJieQianHw(R.drawable.qrewrzdhg, "联系客服");
        SetModelAnYiJieQianHw model6 = new SetModelAnYiJieQianHw(R.drawable.qrewrzdhg, "注销账户");
//        SetModelAnYiJieQianHw model7 = new SetModelAnYiJieQianHw(R.drawable.qrewrzdhg, "退出登录");
        List<SetModelAnYiJieQianHw> list = new ArrayList<>();
        list.add(model3);
        list.add(model1);
        list.add(model2);
        list.add(model5);
        list.add(model6);
        setItemAdapter = new SetItemAnYiJieQianHwAdapter(R.layout.an_yi_jie_qian_adpater_set_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), AboutAnYiJieQianHwInfoActivity.class, null);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", AnYiJieQianHwApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.retert));
                    OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), JumpH5AnYiJieQianHwActivity.class, webBundle);
                    break;
                case 2:
                    webBundle = new Bundle();
                    webBundle.putString("url", AnYiJieQianHwApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.yryvb));
                    OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), JumpH5AnYiJieQianHwActivity.class, webBundle);
                    break;
                case 3:
                    getConfig();
                    break;
                case 4:
                    OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), ZhuXiaoAnYiJieQianHwActivity.class, null);
                    break;
            }
        });
        setList.setLayoutManager(new LinearLayoutManager(getActivity()));
        setList.setAdapter(setItemAdapter);
    }

    public void toWeb(ProductAnYiJieQianHwModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), JumpH5AnYiJieQianHwActivity.class, bundle);
        }
    }

    public void getConfig() {
        AnYiJieQianHwApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseAnYiJieQianHwModel<ConfigAnYiJieQianHwEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseAnYiJieQianHwModel<ConfigAnYiJieQianHwEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferencesAnYiJieQianHwOpenUtil.saveString("app_mail", mailStr);
                                dialog = new RemindAnYiJieQianHwDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                dialog.show();
                            }
                        }
                    }
                });
    }

    public void productList() {
        mobileType = PreferencesAnYiJieQianHwOpenUtil.getInt("mobileType");
        phone = PreferencesAnYiJieQianHwOpenUtil.getString("phone");
        AnYiJieQianHwApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseAnYiJieQianHwModel<List<ProductAnYiJieQianHwModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenAnYiJieQianHwUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseAnYiJieQianHwModel<List<ProductAnYiJieQianHwModel>> baseModel) {
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productAnYiJieQianHwModel = baseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductAnYiJieQianHwModel model) {
        if (model == null) {
            return;
        }
        phone = PreferencesAnYiJieQianHwOpenUtil.getString("phone");
        AnYiJieQianHwApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseAnYiJieQianHwModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseAnYiJieQianHwModel baseModel) {
                        toWeb(model);
                    }
                });
    }
}
