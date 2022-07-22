package com.xg.qdk.f;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xg.qdk.R;
import com.xg.qdk.a.AppMsgActivity;
import com.xg.qdk.a.DengLuActivity;
import com.xg.qdk.a.WangYeActivity;
import com.xg.qdk.a.ConfigItemAdapter;
import com.xg.qdk.a.ZXActivity;
import com.xg.qdk.api.MyApi;
import com.xg.qdk.m.MainModel;
import com.xg.qdk.m.SetEntity;
import com.xg.qdk.m.ShangPinModel;
import com.xg.qdk.m.SheZhiModel;
import com.xg.qdk.mvp.XActivity;
import com.xg.qdk.mvp.XFragment;
import com.xg.qdk.net.ApiSubscriber;
import com.xg.qdk.net.NetError;
import com.xg.qdk.net.XApi;
import com.xg.qdk.u.BaseToast;
import com.xg.qdk.u.BaseUtil;
import com.xg.qdk.u.PreferencesStaticOpenUtil;
import com.xg.qdk.w.TshiDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SheZhiFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.wd_bg)
    View wdBg;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.logout_tv)
    TextView logoutTv;

    private ShangPinModel shangPinModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private ConfigItemAdapter setItemAdapter;

    private TshiDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesStaticOpenUtil.getString("app_mail");
        phone = PreferencesStaticOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        logoutTv.setOnClickListener(v -> {
            dialog = new TshiDialog(getActivity()).setCancelText("取消")
                    .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录？");
            dialog.setOnButtonClickListener(new TshiDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    PreferencesStaticOpenUtil.saveString("phone", "");
                    BaseUtil.getValue((XActivity) getActivity(), DengLuActivity.class, null, true);
                }

                @Override
                public void onCancelClicked() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
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
        SheZhiModel model = new SheZhiModel(R.drawable.xcgdfg, "注册协议");
        SheZhiModel model1 = new SheZhiModel(R.drawable.cbrd, "隐私协议");
//        SetModel model2 = new SetModel(R.drawable.s, "意见反馈");
        SheZhiModel model3 = new SheZhiModel(R.drawable.xvdfhg, "关于我们");
//        SheZhiModel model4 = new SheZhiModel(R.drawable.zxfsdt, "个性化推荐");
        SheZhiModel model5 = new SheZhiModel(R.drawable.rterfx, "联系客服");
        SheZhiModel model6 = new SheZhiModel(R.drawable.qweas, "注销账户");
//        SetModel model7 = new SetModel(R.drawable.p, "退出登录");
        List<SheZhiModel> list = new ArrayList<>();
        list.add(model);
        list.add(model1);
//        list.add(model2);
        list.add(model3);
//        list.add(model4);
        list.add(model5);
        list.add(model6);
//        list.add(model7);
        setItemAdapter = new ConfigItemAdapter(R.layout.adpater_set_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    if (!TextUtils.isEmpty(PreferencesStaticOpenUtil.getString("AGREEMENT"))) {
                        webBundle = new Bundle();
                        webBundle.putString("url", PreferencesStaticOpenUtil.getString("AGREEMENT") + MyApi.ZCXY);
                        webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                        BaseUtil.getValue((XActivity) getActivity(), WangYeActivity.class, webBundle);
                    }
                    break;
                case 1:
                    if (!TextUtils.isEmpty(PreferencesStaticOpenUtil.getString("AGREEMENT"))) {
                        webBundle = new Bundle();
                        webBundle.putString("url", PreferencesStaticOpenUtil.getString("AGREEMENT") + MyApi.YSXY);
                        webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                        BaseUtil.getValue((XActivity) getActivity(), WangYeActivity.class, webBundle);
                    }
                    break;
//                case 2:
//                    OpenUtil.jumpPage(getActivity(), FeedbackActivity.class);
//                    break;
                case 2:
                    BaseUtil.getValue((XActivity) getActivity(), AppMsgActivity.class, null);
                    break;
//                case 3:
//                    dialog = new TshiDialog(getActivity()).setCancelText("开启")
//                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
//                    dialog.setOnButtonClickListener(new TshiDialog.OnButtonClickListener() {
//                        @Override
//                        public void onSureClicked() {
//                            BaseToast.showShort("关闭成功");
//                            dialog.dismiss();
//                        }
//
//                        @Override
//                        public void onCancelClicked() {
//                            BaseToast.showShort("开启成功");
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                    break;
                case 3:
                    getConfig();
                    break;
                case 4:
                    BaseUtil.getValue((XActivity) getActivity(), ZXActivity.class, null);
                    break;
//                case 5:
//                    dialog = new TshiDialog(getActivity()).setCancelText("取消")
//                            .setConfirmText("注销").setTitle("温馨提示").setContent("确定注销并退出当前登录");
//                    dialog.setOnButtonClickListener(new TshiDialog.OnButtonClickListener() {
//                        @Override
//                        public void onSureClicked() {
//                            dialog.dismiss();
//                            PreferencesStaticOpenUtil.saveString("phone", "");
//                            BaseUtil.jumpPage(getActivity(), DengLuActivity.class);
//                            getActivity().finish();
//                        }
//
//                        @Override
//                        public void onCancelClicked() {
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemAdapter);
    }

    public void productList() {
        if (!TextUtils.isEmpty(PreferencesStaticOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = PreferencesStaticOpenUtil.getInt("mobileType");
            phone = PreferencesStaticOpenUtil.getString("phone");
            MyApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MainModel<List<ShangPinModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            BaseUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(MainModel<List<ShangPinModel>> mainModel) {
                            if (mainModel != null) {
                                if (mainModel.getCode() == 200 && mainModel.getData() != null) {
                                    if (mainModel.getData() != null && mainModel.getData().size() > 0) {
                                        shangPinModel = mainModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(PreferencesStaticOpenUtil.getString("HTTP_API_URL"))) {
            MyApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<MainModel<SetEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(MainModel<SetEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesStaticOpenUtil.saveString("app_mail", mailStr);
                                    dialog = new TshiDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
        }
    }

}
