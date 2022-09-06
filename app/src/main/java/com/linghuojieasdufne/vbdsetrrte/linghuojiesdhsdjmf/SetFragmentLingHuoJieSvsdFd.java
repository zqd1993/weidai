package com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmf;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.linghuojieasdufne.vbdsetrrte.R;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma.AboutInfoActivityLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma.JumpH5ActivityLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma.LingHuoJieSvsdFdDlActivity;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma.FeedbackActivityLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma.SetItemAdapterLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjma.LingHuoJieSvsdFdZhuXiaoActivity;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmapi.HttpApiLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.BaseModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.ConfigEntityLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.ProductModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmm.SetModelLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmw.RemindLingHuoJieSvsdFdDialog;
import com.linghuojieasdufne.vbdsetrrte.mvp.XFragment;
import com.linghuojieasdufne.vbdsetrrte.net.ApiSubscriber;
import com.linghuojieasdufne.vbdsetrrte.net.NetError;
import com.linghuojieasdufne.vbdsetrrte.net.XApi;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.MyToastLingHuoJieSvsdFd;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.LingHuoJieSvsdFdOpenUtil;
import com.linghuojieasdufne.vbdsetrrte.linghuojiesdhsdjmu.PreferencesOpenUtilLingHuoJieSvsdFd;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFragmentLingHuoJieSvsdFd extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.goods_im)
    View goods_im;

    private ProductModelLingHuoJieSvsdFd productModelLingHuoJieSvsdFd;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterLingHuoJieSvsdFd setItemAdapterLingHuoJieSvsdFd;

    private RemindLingHuoJieSvsdFdDialog dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferencesOpenUtilLingHuoJieSvsdFd.getString("app_mail");
        phone = PreferencesOpenUtilLingHuoJieSvsdFd.getString("phone");
        userPhoneTv.setText(phone);
        initSetAdapter();
        goods_im.setOnClickListener(v -> {
            productClick(productModelLingHuoJieSvsdFd);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ling_huo_jie_djs_urng_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetModelLingHuoJieSvsdFd model = new SetModelLingHuoJieSvsdFd(R.drawable.xfvberyaey, "注册协议");
        SetModelLingHuoJieSvsdFd model1 = new SetModelLingHuoJieSvsdFd(R.drawable.xcvbdry, "隐私协议");
        SetModelLingHuoJieSvsdFd model2 = new SetModelLingHuoJieSvsdFd(R.drawable.esrrtsyxfgjh, "意见反馈");
        SetModelLingHuoJieSvsdFd model3 = new SetModelLingHuoJieSvsdFd(R.drawable.dfgaerysry, "关于我们");
        SetModelLingHuoJieSvsdFd model4 = new SetModelLingHuoJieSvsdFd(R.drawable.jkdrtusra, "个性化推荐");
        SetModelLingHuoJieSvsdFd model5 = new SetModelLingHuoJieSvsdFd(R.drawable.wweerrsuy, "投诉邮箱");
        SetModelLingHuoJieSvsdFd model6 = new SetModelLingHuoJieSvsdFd(R.drawable.rstyszdhf, "注销账户");
        SetModelLingHuoJieSvsdFd model7 = new SetModelLingHuoJieSvsdFd(R.drawable.cvbneryaey, "退出登录");
        List<SetModelLingHuoJieSvsdFd> list = new ArrayList<>();
        List<SetModelLingHuoJieSvsdFd> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapterLingHuoJieSvsdFd = new SetItemAdapterLingHuoJieSvsdFd(R.layout.adpater_set_item_ling_huo_jie_djs_urng, list);
        setItemAdapterLingHuoJieSvsdFd.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiLingHuoJieSvsdFd.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    LingHuoJieSvsdFdOpenUtil.jumpPage(getActivity(), JumpH5ActivityLingHuoJieSvsdFd.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiLingHuoJieSvsdFd.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    LingHuoJieSvsdFdOpenUtil.jumpPage(getActivity(), JumpH5ActivityLingHuoJieSvsdFd.class, webBundle);
                    break;
                case 2:
                    LingHuoJieSvsdFdOpenUtil.jumpPage(getActivity(), FeedbackActivityLingHuoJieSvsdFd.class);
                    break;
                case 3:
                    LingHuoJieSvsdFdOpenUtil.jumpPage(getActivity(), AboutInfoActivityLingHuoJieSvsdFd.class);
                    break;
                case 4:
                    dialog = new RemindLingHuoJieSvsdFdDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindLingHuoJieSvsdFdDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToastLingHuoJieSvsdFd.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToastLingHuoJieSvsdFd.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    LingHuoJieSvsdFdOpenUtil.jumpPage(getActivity(), LingHuoJieSvsdFdZhuXiaoActivity.class);
                    break;
                case 7:
                    dialog = new RemindLingHuoJieSvsdFdDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindLingHuoJieSvsdFdDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilLingHuoJieSvsdFd.saveString("phone", "");
                            LingHuoJieSvsdFdOpenUtil.jumpPage(getActivity(), LingHuoJieSvsdFdDlActivity.class);
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
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemAdapterLingHuoJieSvsdFd);
    }

    public void getConfig() {
            HttpApiLingHuoJieSvsdFd.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieSvsdFd<ConfigEntityLingHuoJieSvsdFd> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilLingHuoJieSvsdFd.saveString("app_mail", mailStr);
                                    dialog = new RemindLingHuoJieSvsdFdDialog(getActivity()).setCancelText("取消")
                                            .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new RemindLingHuoJieSvsdFdDialog.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            MyToastLingHuoJieSvsdFd.showShort("复制成功");
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

    public void toWeb(ProductModelLingHuoJieSvsdFd model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            LingHuoJieSvsdFdOpenUtil.jumpPage(getActivity(), JumpH5ActivityLingHuoJieSvsdFd.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesOpenUtilLingHuoJieSvsdFd.getInt("mobileType");
            phone = PreferencesOpenUtilLingHuoJieSvsdFd.getString("phone");
            HttpApiLingHuoJieSvsdFd.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieSvsdFd<List<ProductModelLingHuoJieSvsdFd>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            LingHuoJieSvsdFdOpenUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieSvsdFd<List<ProductModelLingHuoJieSvsdFd>> baseModelLingHuoJieSvsdFd) {
                            if (baseModelLingHuoJieSvsdFd != null) {
                                if (baseModelLingHuoJieSvsdFd.getCode() == 200 && baseModelLingHuoJieSvsdFd.getData() != null) {
                                    if (baseModelLingHuoJieSvsdFd.getData() != null && baseModelLingHuoJieSvsdFd.getData().size() > 0) {
                                        productModelLingHuoJieSvsdFd = baseModelLingHuoJieSvsdFd.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductModelLingHuoJieSvsdFd model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilLingHuoJieSvsdFd.getString("phone");
            HttpApiLingHuoJieSvsdFd.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieSvsdFd>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieSvsdFd baseModelLingHuoJieSvsdFd) {
                            toWeb(model);
                        }
                    });
    }
}
