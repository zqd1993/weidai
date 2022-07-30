package com.fdhsdjqqhds.ppfdzabsdvd.qufenqif;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqia.AboutInfoActivityQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqia.QuFenQiDlActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqia.FeedbackQuFenQiActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqia.QuFenQiJumpH5Activity;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqia.SetItemQuFenQiAdapter;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqia.ZhuXiaoQuFenQiActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiapi.HttpApiQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.BaseQuFenQiModel;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.QuFenQiConfigEntity;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.ProductModelQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqim.QuFenQiSetModel;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XFragment;
import com.fdhsdjqqhds.ppfdzabsdvd.net.ApiSubscriber;
import com.fdhsdjqqhds.ppfdzabsdvd.net.NetError;
import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.QuFenQiMyToast;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.OpenUtilQuFenQi;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiu.PreferencesQuFenQiOpenUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.qufenqiw.RemindQuFenQiDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuFenQiSetFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductModelQuFenQi productModelQuFenQi;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemQuFenQiAdapter setItemQuFenQiAdapter;

    private RemindQuFenQiDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesQuFenQiOpenUtil.getString("app_mail");
        phone = PreferencesQuFenQiOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_qu_fen_qi_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        QuFenQiSetModel model = new QuFenQiSetModel(R.drawable.jhjartgdb, "注册协议");
        QuFenQiSetModel model1 = new QuFenQiSetModel(R.drawable.nnsftujhtrshz, "隐私协议");
        QuFenQiSetModel model2 = new QuFenQiSetModel(R.drawable.eegzdrhtuy, "意见反馈");
        QuFenQiSetModel model3 = new QuFenQiSetModel(R.drawable.gggadgrehz, "关于我们");
        QuFenQiSetModel model4 = new QuFenQiSetModel(R.drawable.mmsrthydfb, "个性化推荐");
        QuFenQiSetModel model5 = new QuFenQiSetModel(R.drawable.jjstfghxf, "投诉邮箱");
        QuFenQiSetModel model6 = new QuFenQiSetModel(R.drawable.zzzvdtr, "注销账户");
        QuFenQiSetModel model7 = new QuFenQiSetModel(R.drawable.xzxxbxthf, "退出登录");
        List<QuFenQiSetModel> list = new ArrayList<>();
        List<QuFenQiSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemQuFenQiAdapter = new SetItemQuFenQiAdapter(R.layout.adpater_qu_fen_qi_set_item, list);
        setItemQuFenQiAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiQuFenQi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenUtilQuFenQi.getValue((XActivity) getActivity(), QuFenQiJumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiQuFenQi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenUtilQuFenQi.getValue((XActivity) getActivity(), QuFenQiJumpH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenUtilQuFenQi.getValue((XActivity) getActivity(), FeedbackQuFenQiActivity.class, null);
                    break;
                case 3:
                    OpenUtilQuFenQi.getValue((XActivity) getActivity(), AboutInfoActivityQuFenQi.class, null);
                    break;
                case 4:
                    dialog = new RemindQuFenQiDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindQuFenQiDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            QuFenQiMyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            QuFenQiMyToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpenUtilQuFenQi.getValue((XActivity) getActivity(), ZhuXiaoQuFenQiActivity.class, null);
                    break;
                case 7:
                    dialog = new RemindQuFenQiDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindQuFenQiDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesQuFenQiOpenUtil.saveString("phone", "");
                            OpenUtilQuFenQi.getValue((XActivity) getActivity(), QuFenQiDlActivity.class, null, true);
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
        setList.setAdapter(setItemQuFenQiAdapter);
    }

    public void getConfig() {
        HttpApiQuFenQi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseQuFenQiModel<QuFenQiConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseQuFenQiModel<QuFenQiConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferencesQuFenQiOpenUtil.saveString("app_mail", mailStr);
                                dialog = new RemindQuFenQiDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                dialog.show();
                            }
                        }
                    }
                });
    }

    public void toWeb(ProductModelQuFenQi model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenUtilQuFenQi.getValue((XActivity) getActivity(), QuFenQiJumpH5Activity.class, bundle);
        }
    }

    public void productList() {
        mobileType = PreferencesQuFenQiOpenUtil.getInt("mobileType");
        phone = PreferencesQuFenQiOpenUtil.getString("phone");
        HttpApiQuFenQi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseQuFenQiModel<List<ProductModelQuFenQi>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenUtilQuFenQi.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseQuFenQiModel<List<ProductModelQuFenQi>> baseQuFenQiModel) {
                        if (baseQuFenQiModel != null) {
                            if (baseQuFenQiModel.getCode() == 200 && baseQuFenQiModel.getData() != null) {
                                if (baseQuFenQiModel.getData() != null && baseQuFenQiModel.getData().size() > 0) {
                                    productModelQuFenQi = baseQuFenQiModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductModelQuFenQi model) {
        if (model == null) {
            return;
        }
        phone = PreferencesQuFenQiOpenUtil.getString("phone");
        HttpApiQuFenQi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseQuFenQiModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseQuFenQiModel baseQuFenQiModel) {
                        toWeb(model);
                    }
                });
    }
}
