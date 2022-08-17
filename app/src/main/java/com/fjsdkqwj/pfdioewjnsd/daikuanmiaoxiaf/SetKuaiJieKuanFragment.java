package com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaf;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fjsdkqwj.pfdioewjnsd.R;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaa.AboutInfoActivityKuaiJieKuan;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaa.DlKuaiJieKuanActivity;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaa.FeedbackKuaiJieKuanActivity;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaa.JumpH5ActivityKuaiJieKuan;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaa.SetItemAdapterKuaiJieKuan;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaa.ZhuXiaoKuaiJieKuanActivity;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaapi.KuaiJieKuanHttpApi;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiam.KuaiJieKuanBaseModel;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiam.ConfigEntityKuaiJieKuan;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiam.ProductKuaiJieKuanModel;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiam.SetKuaiJieKuanModel;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiaw.RemindKuaiJieKuanDialog;
import com.fjsdkqwj.pfdioewjnsd.mvp.XFragment;
import com.fjsdkqwj.pfdioewjnsd.net.ApiSubscriber;
import com.fjsdkqwj.pfdioewjnsd.net.NetError;
import com.fjsdkqwj.pfdioewjnsd.net.XApi;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiau.MyKuaiJieKuanToast;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiau.OpenKuaiJieKuanUtil;
import com.fjsdkqwj.pfdioewjnsd.daikuanmiaoxiau.PreferencesOpenUtilKuaiJieKuan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetKuaiJieKuanFragment extends XFragment {
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductKuaiJieKuanModel productKuaiJieKuanModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterKuaiJieKuan setItemAdapterKuaiJieKuan;

    private RemindKuaiJieKuanDialog dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferencesOpenUtilKuaiJieKuan.getString("app_mail");
        phone = PreferencesOpenUtilKuaiJieKuan.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kuai_jie_kuan_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetKuaiJieKuanModel model = new SetKuaiJieKuanModel(R.drawable.jjsrtusdfu, "注册协议");
        SetKuaiJieKuanModel model1 = new SetKuaiJieKuanModel(R.drawable.xfcbnsrtusr, "隐私协议");
        SetKuaiJieKuanModel model2 = new SetKuaiJieKuanModel(R.drawable.weertryxf, "意见反馈");
        SetKuaiJieKuanModel model3 = new SetKuaiJieKuanModel(R.drawable.sfxfghsrtu, "关于我们");
//        SetKuaiJieKuanModel model4 = new SetKuaiJieKuanModel(R.drawable.rtsyrtxfus, "个性化推荐");
        SetKuaiJieKuanModel model5 = new SetKuaiJieKuanModel(R.drawable.bnsrtuseu, "联系客服");
        SetKuaiJieKuanModel model6 = new SetKuaiJieKuanModel(R.drawable.vbnxchjsftu, "注销账户");
        SetKuaiJieKuanModel model7 = new SetKuaiJieKuanModel(R.drawable.jjsrtyuaeru, "退出登录");
        List<SetKuaiJieKuanModel> list = new ArrayList<>();
        List<SetKuaiJieKuanModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
//        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapterKuaiJieKuan = new SetItemAdapterKuaiJieKuan(R.layout.adpater_set_item_kuai_jie_kuan, list);
        setItemAdapterKuaiJieKuan.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", KuaiJieKuanHttpApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenKuaiJieKuanUtil.jumpPage(getActivity(), JumpH5ActivityKuaiJieKuan.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", KuaiJieKuanHttpApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenKuaiJieKuanUtil.jumpPage(getActivity(), JumpH5ActivityKuaiJieKuan.class, webBundle);
                    break;
                case 2:
                    OpenKuaiJieKuanUtil.jumpPage(getActivity(), FeedbackKuaiJieKuanActivity.class);
                    break;
                case 3:
                    OpenKuaiJieKuanUtil.jumpPage(getActivity(), AboutInfoActivityKuaiJieKuan.class);
                    break;
//                case 4:
//                    dialog = new RemindKuaiJieKuanDialog(getActivity()).setCancelText("开启")
//                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
//                    dialog.setOnButtonClickListener(new RemindKuaiJieKuanDialog.OnButtonClickListener() {
//                        @Override
//                        public void onSureClicked() {
//                            MyKuaiJieKuanToast.showShort("关闭成功");
//                            dialog.dismiss();
//                        }
//
//                        @Override
//                        public void onCancelClicked() {
//                            MyKuaiJieKuanToast.showShort("开启成功");
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                    break;
                case 4:
                    getConfig();
                    break;
                case 5:
                    OpenKuaiJieKuanUtil.jumpPage(getActivity(), ZhuXiaoKuaiJieKuanActivity.class);
                    break;
                case 6:
                    dialog = new RemindKuaiJieKuanDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindKuaiJieKuanDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilKuaiJieKuan.saveString("phone", "");
                            OpenKuaiJieKuanUtil.jumpPage(getActivity(), DlKuaiJieKuanActivity.class);
                            getActivity().finish();
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
        setList.setAdapter(setItemAdapterKuaiJieKuan);
    }

    public void getConfig() {
            KuaiJieKuanHttpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiJieKuanBaseModel<ConfigEntityKuaiJieKuan>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(KuaiJieKuanBaseModel<ConfigEntityKuaiJieKuan> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilKuaiJieKuan.saveString("app_mail", mailStr);
                                    dialog = new RemindKuaiJieKuanDialog(getActivity()).setCancelText("取消")
                                            .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new RemindKuaiJieKuanDialog.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            MyKuaiJieKuanToast.showShort("复制成功");
                                            dialog.dismiss();
                                        }

                                        @Override
                                        public void onCancelClicked() {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                }
                            }
                        }
                    });
    }

    public void toWeb(ProductKuaiJieKuanModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenKuaiJieKuanUtil.jumpPage(getActivity(), JumpH5ActivityKuaiJieKuan.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesOpenUtilKuaiJieKuan.getInt("mobileType");
            phone = PreferencesOpenUtilKuaiJieKuan.getString("phone");
            KuaiJieKuanHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiJieKuanBaseModel<List<ProductKuaiJieKuanModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKuaiJieKuanUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(KuaiJieKuanBaseModel<List<ProductKuaiJieKuanModel>> kuaiJieKuanBaseModel) {
                            if (kuaiJieKuanBaseModel != null) {
                                if (kuaiJieKuanBaseModel.getCode() == 200 && kuaiJieKuanBaseModel.getData() != null) {
                                    if (kuaiJieKuanBaseModel.getData() != null && kuaiJieKuanBaseModel.getData().size() > 0) {
                                        productKuaiJieKuanModel = kuaiJieKuanBaseModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductKuaiJieKuanModel model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilKuaiJieKuan.getString("phone");
            KuaiJieKuanHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiJieKuanBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(KuaiJieKuanBaseModel kuaiJieKuanBaseModel) {
                            toWeb(model);
                        }
                    });
    }
}
