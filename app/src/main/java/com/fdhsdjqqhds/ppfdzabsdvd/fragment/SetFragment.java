package com.fdhsdjqqhds.ppfdzabsdvd.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.activity.AboutInfoActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.activity.DlActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.activity.FeedbackActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.activity.JumpH5Activity;
import com.fdhsdjqqhds.ppfdzabsdvd.activity.SetItemAdapter;
import com.fdhsdjqqhds.ppfdzabsdvd.activity.ZhuXiaoActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.api.HttpApi;
import com.fdhsdjqqhds.ppfdzabsdvd.model.BaseModel;
import com.fdhsdjqqhds.ppfdzabsdvd.model.ConfigEntity;
import com.fdhsdjqqhds.ppfdzabsdvd.model.ProductModel;
import com.fdhsdjqqhds.ppfdzabsdvd.model.SetModel;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XFragment;
import com.fdhsdjqqhds.ppfdzabsdvd.net.ApiSubscriber;
import com.fdhsdjqqhds.ppfdzabsdvd.net.NetError;
import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;
import com.fdhsdjqqhds.ppfdzabsdvd.utils.MyToast;
import com.fdhsdjqqhds.ppfdzabsdvd.utils.OpenUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.utils.PreferencesOpenUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.weidgt.RemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductModel productModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapter setItemAdapter;

    private RemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesOpenUtil.getString("app_mail");
        phone = PreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetModel model = new SetModel(R.drawable.jhjartgdb, "注册协议");
        SetModel model1 = new SetModel(R.drawable.nnsftujhtrshz, "隐私协议");
        SetModel model2 = new SetModel(R.drawable.eegzdrhtuy, "意见反馈");
        SetModel model3 = new SetModel(R.drawable.gggadgrehz, "关于我们");
        SetModel model4 = new SetModel(R.drawable.mmsrthydfb, "个性化推荐");
        SetModel model5 = new SetModel(R.drawable.jjstfghxf, "投诉邮箱");
        SetModel model6 = new SetModel(R.drawable.zzzvdtr, "注销账户");
        SetModel model7 = new SetModel(R.drawable.xzxxbxthf, "退出登录");
        List<SetModel> list = new ArrayList<>();
        List<SetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapter = new SetItemAdapter(R.layout.adpater_set_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenUtil.getValue((XActivity) getActivity(), JumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenUtil.getValue((XActivity) getActivity(), JumpH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenUtil.getValue((XActivity) getActivity(), FeedbackActivity.class, null);
                    break;
                case 3:
                    OpenUtil.getValue((XActivity) getActivity(), AboutInfoActivity.class, null);
                    break;
                case 4:
                    dialog = new RemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpenUtil.getValue((XActivity) getActivity(), ZhuXiaoActivity.class, null);
                    break;
                case 7:
                    dialog = new RemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtil.saveString("phone", "");
                            OpenUtil.getValue((XActivity) getActivity(), DlActivity.class, null, true);
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
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemAdapter);
    }

    public void getConfig() {
        HttpApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<ConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseModel<ConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferencesOpenUtil.saveString("app_mail", mailStr);
                                dialog = new RemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                dialog.show();
                            }
                        }
                    }
                });
    }

    public void toWeb(ProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenUtil.getValue((XActivity) getActivity(), JumpH5Activity.class, bundle);
        }
    }

    public void productList() {
        mobileType = PreferencesOpenUtil.getInt("mobileType");
        phone = PreferencesOpenUtil.getString("phone");
        HttpApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<List<ProductModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseModel<List<ProductModel>> baseModel) {
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productModel = baseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductModel model) {
        if (model == null) {
            return;
        }
        phone = PreferencesOpenUtil.getString("phone");
        HttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        toWeb(model);
                    }
                });
    }
}
