package com.wolai.dai.suipian;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wolai.dai.R;
import com.wolai.dai.kongjian.JixinRemindDialog;
import com.wolai.dai.mvp.XActivity;
import com.wolai.dai.shiti.JixinConfigEntity;
import com.wolai.dai.yemian.JixinAboutInfoActivity;
import com.wolai.dai.yemian.JixinDlActivity;
import com.wolai.dai.yemian.JixinFeedbackActivity;
import com.wolai.dai.yemian.JixinJumpH5Activity;
import com.wolai.dai.yemian.JixinSetItemAdapter;
import com.wolai.dai.yemian.JixinZhuXiaoActivity;
import com.wolai.dai.jiekou.JiXinApi;
import com.wolai.dai.shiti.JixinBaseModel;
import com.wolai.dai.shiti.JixinProductModel;
import com.wolai.dai.shiti.JixinSetModel;
import com.wolai.dai.mvp.XFragment;
import com.wolai.dai.net.ApiSubscriber;
import com.wolai.dai.net.NetError;
import com.wolai.dai.net.XApi;
import com.wolai.dai.gongju.JiXinMyToast;
import com.wolai.dai.gongju.JiXinOpenUtil;
import com.wolai.dai.gongju.JiXinPreferencesOpenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class JixinSetFragment extends XFragment {

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

    private JixinProductModel jixinProductModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private JixinSetItemAdapter setItemAdapter;

    private JixinRemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = JiXinPreferencesOpenUtil.getString("app_mail");
        phone = JiXinPreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        zcxyLl.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("AGREEMENT"))) {
                webBundle = new Bundle();
                webBundle.putString("url", JiXinPreferencesOpenUtil.getString("AGREEMENT") + JiXinApi.ZCXY);
                webBundle.putString("biaoti", getResources().getString(R.string.yryvb));
                JiXinOpenUtil.getValue((XActivity) getActivity(), JixinJumpH5Activity.class, webBundle);
            }
        });
        ysxy_ll.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("AGREEMENT"))) {
                webBundle = new Bundle();
                webBundle.putString("url", JiXinPreferencesOpenUtil.getString("AGREEMENT") + JiXinApi.YSXY);
                webBundle.putString("biaoti", getResources().getString(R.string.retert));
                JiXinOpenUtil.getValue((XActivity) getActivity(), JixinJumpH5Activity.class, webBundle);
            }
        });
        yjfk_ll.setOnClickListener(v -> {
            JiXinOpenUtil.getValue((XActivity) getActivity(), JixinFeedbackActivity.class, null);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.jixin_fragment_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        JixinSetModel model3 = new JixinSetModel(R.drawable.zxcerghh, "关于我们");
        JixinSetModel model4 = new JixinSetModel(R.drawable.bgdeed, "个性化推荐");
        JixinSetModel model5 = new JixinSetModel(R.drawable.tererf, "投诉邮箱");
        JixinSetModel model6 = new JixinSetModel(R.drawable.fgfsf, "注销账户");
        JixinSetModel model7 = new JixinSetModel(R.drawable.luyth, "退出登录");
        List<JixinSetModel> list = new ArrayList<>();
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapter = new JixinSetItemAdapter(R.layout.jixin_adpater_set_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    JiXinOpenUtil.getValue((XActivity) getActivity(), JixinAboutInfoActivity.class, null);
                    break;
                case 1:
                    dialog = new JixinRemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new JixinRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            JiXinMyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            JiXinMyToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    JiXinOpenUtil.getValue((XActivity) getActivity(), JixinZhuXiaoActivity.class, null);
                    break;
                case 4:
                    dialog = new JixinRemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new JixinRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            JiXinPreferencesOpenUtil.saveString("phone", "");
                            JiXinOpenUtil.getValue((XActivity) getActivity(), JixinDlActivity.class, null, true);
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
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        setList.setAdapter(setItemAdapter);
    }

    public void toWeb(JixinProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            JiXinOpenUtil.getValue((XActivity) getActivity(), JixinJumpH5Activity.class, bundle);
        }
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("API_BASE_URL"))) {
            JiXinApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<JixinBaseModel<JixinConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(JixinBaseModel<JixinConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    JiXinPreferencesOpenUtil.saveString("app_mail", mailStr);
                                    dialog = new JixinRemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
        }
    }

    public void productList() {
        if (!TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("API_BASE_URL"))) {
            mobileType = JiXinPreferencesOpenUtil.getInt("mobileType");
            JiXinApi.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<JixinBaseModel<List<JixinProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            JiXinOpenUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(JixinBaseModel<List<JixinProductModel>> baseModel) {
                            if (baseModel != null) {
                                if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                    if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                        jixinProductModel = baseModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public void productClick(JixinProductModel model) {
        if (!TextUtils.isEmpty(JiXinPreferencesOpenUtil.getString("API_BASE_URL"))) {
            if (model == null) {
                return;
            }
            phone = JiXinPreferencesOpenUtil.getString("phone");
            JiXinApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<JixinBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(JixinBaseModel baseModel) {
                            toWeb(model);
                        }
                    });
        }
    }
}
