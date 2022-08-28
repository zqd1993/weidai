package com.dfgdervhw.erterqweq.anyijieqiansuipian;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dfgdervhw.erterqweq.R;
import com.dfgdervhw.erterqweq.anyijieqiankongjian.RemindAnYiJieQianHwDialog;
import com.dfgdervhw.erterqweq.anyijieqianyemian.JumpH5AnYiJieQianHwActivity;
import com.dfgdervhw.erterqweq.mvp.XActivity;
import com.dfgdervhw.erterqweq.anyijieqianshiti.ConfigAnYiJieQianHwEntity;
import com.dfgdervhw.erterqweq.anyijieqianyemian.AboutAnYiJieQianHwInfoActivity;
import com.dfgdervhw.erterqweq.anyijieqianyemian.DlAnYiJieQianHwActivity;
import com.dfgdervhw.erterqweq.anyijieqianyemian.FeedbackAnYiJieQianHwActivity;
import com.dfgdervhw.erterqweq.anyijieqianyemian.SetItemAnYiJieQianHwAdapter;
import com.dfgdervhw.erterqweq.anyijieqianyemian.ZhuXiaoAnYiJieQianHwActivity;
import com.dfgdervhw.erterqweq.anyijieqianjiekou.AnYiJieQianHwApi;
import com.dfgdervhw.erterqweq.anyijieqianshiti.BaseAnYiJieQianHwModel;
import com.dfgdervhw.erterqweq.anyijieqianshiti.ProductAnYiJieQianHwModel;
import com.dfgdervhw.erterqweq.anyijieqianshiti.SetModelAnYiJieQianHw;
import com.dfgdervhw.erterqweq.mvp.XFragment;
import com.dfgdervhw.erterqweq.net.ApiSubscriber;
import com.dfgdervhw.erterqweq.net.NetError;
import com.dfgdervhw.erterqweq.net.XApi;
import com.dfgdervhw.erterqweq.anyijieqiangongju.OpenAnYiJieQianHwUtil;
import com.dfgdervhw.erterqweq.anyijieqiangongju.PreferencesAnYiJieQianHwOpenUtil;

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
    @BindView(R.id.zcxy_ll)
    View zcxyLl;
    @BindView(R.id.ysxy_ll)
    View ysxy_ll;
    @BindView(R.id.yjfk_ll)
    View yjfk_ll;

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
        zcxyLl.setOnClickListener(v -> {
            webBundle = new Bundle();
            webBundle.putString("url", AnYiJieQianHwApi.ZCXY);
            webBundle.putString("biaoti", getResources().getString(R.string.yryvb));
            OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), JumpH5AnYiJieQianHwActivity.class, webBundle);
        });
        ysxy_ll.setOnClickListener(v -> {
            webBundle = new Bundle();
            webBundle.putString("url", AnYiJieQianHwApi.YSXY);
            webBundle.putString("biaoti", getResources().getString(R.string.retert));
            OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), JumpH5AnYiJieQianHwActivity.class, webBundle);
        });
        yjfk_ll.setOnClickListener(v -> {
            OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), FeedbackAnYiJieQianHwActivity.class, null);
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
        SetModelAnYiJieQianHw model3 = new SetModelAnYiJieQianHw(R.drawable.wd_icon_gywm, "关于我们");
//        SetModelAnYiJieQianHw model4 = new SetModelAnYiJieQianHw(R.drawable.wd_icon_yjfk, "个性化推荐");
        SetModelAnYiJieQianHw model5 = new SetModelAnYiJieQianHw(R.drawable.wd_tsyx, "联系客服");
        SetModelAnYiJieQianHw model6 = new SetModelAnYiJieQianHw(R.drawable.wd_icon_xxts, "注销账户");
        SetModelAnYiJieQianHw model7 = new SetModelAnYiJieQianHw(R.drawable.wd_icon_zczh, "退出登录");
        List<SetModelAnYiJieQianHw> list = new ArrayList<>();
        list.add(model3);
//        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapter = new SetItemAnYiJieQianHwAdapter(R.layout.an_yi_jie_qian_adpater_set_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), AboutAnYiJieQianHwInfoActivity.class, null);
                    break;
//                case 1:
//                    dialog = new RemindAnYiJieQianHwDialog(getActivity()).setCancelText("开启")
//                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
//                    dialog.setOnButtonClickListener(new RemindAnYiJieQianHwDialog.OnButtonClickListener() {
//                        @Override
//                        public void onSureClicked() {
//                            AnYiJieQianHwMyToast.showShort("关闭成功");
//                            dialog.dismiss();
//                        }
//
//                        @Override
//                        public void onCancelClicked() {
//                            AnYiJieQianHwMyToast.showShort("开启成功");
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                    break;
                case 1:
                    getConfig();
                    break;
                case 2:
                    OpenAnYiJieQianHwUtil.getValue((XActivity) getActivity(), ZhuXiaoAnYiJieQianHwActivity.class, null);
                    break;
                case 3:
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
