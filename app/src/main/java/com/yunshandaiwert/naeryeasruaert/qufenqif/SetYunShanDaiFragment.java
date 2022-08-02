package com.yunshandaiwert.naeryeasruaert.qufenqif;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yunshandaiwert.naeryeasruaert.R;
import com.yunshandaiwert.naeryeasruaert.qufenqia.AboutInfoYunShanDaiActivity;
import com.yunshandaiwert.naeryeasruaert.qufenqia.DlYunShanDaiActivity;
import com.yunshandaiwert.naeryeasruaert.qufenqia.FeedbackYunShanDaiActivity;
import com.yunshandaiwert.naeryeasruaert.qufenqia.JumpYunShanDaiH5Activity;
import com.yunshandaiwert.naeryeasruaert.qufenqia.SetItemAdapterYunShanDai;
import com.yunshandaiwert.naeryeasruaert.qufenqia.ZhuXiaoYunShanDaiActivity;
import com.yunshandaiwert.naeryeasruaert.qufenqiapi.HttpYunShanDaiApi;
import com.yunshandaiwert.naeryeasruaert.qufenqim.YunShanDaiBaseModel;
import com.yunshandaiwert.naeryeasruaert.qufenqim.ConfigYunShanDaiEntity;
import com.yunshandaiwert.naeryeasruaert.qufenqim.YunShanDaiProductModel;
import com.yunshandaiwert.naeryeasruaert.qufenqim.YunShanDaiSetModel;
import com.yunshandaiwert.naeryeasruaert.mvp.XActivity;
import com.yunshandaiwert.naeryeasruaert.mvp.XFragment;
import com.yunshandaiwert.naeryeasruaert.net.ApiSubscriber;
import com.yunshandaiwert.naeryeasruaert.net.NetError;
import com.yunshandaiwert.naeryeasruaert.net.XApi;
import com.yunshandaiwert.naeryeasruaert.qufenqiu.YunShanDaiMyToast;
import com.yunshandaiwert.naeryeasruaert.qufenqiu.YunShanDaiOpenUtil;
import com.yunshandaiwert.naeryeasruaert.qufenqiu.YunShanDaiPreferencesOpenUtil;
import com.yunshandaiwert.naeryeasruaert.qufenqiw.YunShanDaiRemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetYunShanDaiFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private YunShanDaiProductModel yunShanDaiProductModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterYunShanDai setItemAdapterYunShanDai;

    private YunShanDaiRemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = YunShanDaiPreferencesOpenUtil.getString("app_mail");
        phone = YunShanDaiPreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set_yun_shan_dai;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        YunShanDaiSetModel model = new YunShanDaiSetModel(R.drawable.lldrtsrti, "注册协议");
        YunShanDaiSetModel model1 = new YunShanDaiSetModel(R.drawable.xfxcfudrti, "隐私协议");
        YunShanDaiSetModel model2 = new YunShanDaiSetModel(R.drawable.mmmxdftujrtui, "意见反馈");
        YunShanDaiSetModel model3 = new YunShanDaiSetModel(R.drawable.qwqtderxu, "关于我们");
        YunShanDaiSetModel model4 = new YunShanDaiSetModel(R.drawable.xgnxrtursui, "个性化推荐");
        YunShanDaiSetModel model5 = new YunShanDaiSetModel(R.drawable.llfyptyi, "投诉邮箱");
        YunShanDaiSetModel model6 = new YunShanDaiSetModel(R.drawable.xgxurtsui, "注销账户");
        YunShanDaiSetModel model7 = new YunShanDaiSetModel(R.drawable.mzxfjrtirs, "退出登录");
        List<YunShanDaiSetModel> list = new ArrayList<>();
        List<YunShanDaiSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapterYunShanDai = new SetItemAdapterYunShanDai(R.layout.adpater_yun_shan_dai_set_item, list);
        setItemAdapterYunShanDai.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpYunShanDaiApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    YunShanDaiOpenUtil.getValue((XActivity) getActivity(), JumpYunShanDaiH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpYunShanDaiApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    YunShanDaiOpenUtil.getValue((XActivity) getActivity(), JumpYunShanDaiH5Activity.class, webBundle);
                    break;
                case 2:
                    YunShanDaiOpenUtil.getValue((XActivity) getActivity(), FeedbackYunShanDaiActivity.class, null);
                    break;
                case 3:
                    YunShanDaiOpenUtil.getValue((XActivity) getActivity(), AboutInfoYunShanDaiActivity.class, null);
                    break;
                case 4:
                    dialog = new YunShanDaiRemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new YunShanDaiRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            YunShanDaiMyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            YunShanDaiMyToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    YunShanDaiOpenUtil.getValue((XActivity) getActivity(), ZhuXiaoYunShanDaiActivity.class, null);
                    break;
                case 7:
                    dialog = new YunShanDaiRemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new YunShanDaiRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            YunShanDaiPreferencesOpenUtil.saveString("phone", "");
                            YunShanDaiOpenUtil.getValue((XActivity) getActivity(), DlYunShanDaiActivity.class, null, true);
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
        setList.setAdapter(setItemAdapterYunShanDai);
    }

    public void getConfig() {
        HttpYunShanDaiApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<YunShanDaiBaseModel<ConfigYunShanDaiEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(YunShanDaiBaseModel<ConfigYunShanDaiEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                YunShanDaiPreferencesOpenUtil.saveString("app_mail", mailStr);
                                dialog = new YunShanDaiRemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                dialog.show();
                            }
                        }
                    }
                });
    }

    public void toWeb(YunShanDaiProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            YunShanDaiOpenUtil.getValue((XActivity) getActivity(), JumpYunShanDaiH5Activity.class, bundle);
        }
    }

    public void productList() {
        mobileType = YunShanDaiPreferencesOpenUtil.getInt("mobileType");
        phone = YunShanDaiPreferencesOpenUtil.getString("phone");
        HttpYunShanDaiApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<YunShanDaiBaseModel<List<YunShanDaiProductModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        YunShanDaiOpenUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(YunShanDaiBaseModel<List<YunShanDaiProductModel>> yunShanDaiBaseModel) {
                        if (yunShanDaiBaseModel != null) {
                            if (yunShanDaiBaseModel.getCode() == 200 && yunShanDaiBaseModel.getData() != null) {
                                if (yunShanDaiBaseModel.getData() != null && yunShanDaiBaseModel.getData().size() > 0) {
                                    yunShanDaiProductModel = yunShanDaiBaseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(YunShanDaiProductModel model) {
        if (model == null) {
            return;
        }
        phone = YunShanDaiPreferencesOpenUtil.getString("phone");
        HttpYunShanDaiApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<YunShanDaiBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(YunShanDaiBaseModel yunShanDaiBaseModel) {
                        toWeb(model);
                    }
                });
    }
}
