package com.linghuojiehuanopwesdf.dsfwethdfgwe.flinghuojiekuan;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.linghuojiehuanopwesdf.dsfwethdfgwe.R;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan.AboutInfoActivityLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan.JumpH5LingHuoJieHuanActivity;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan.LingHuoJieHuanDlActivity;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan.FeedbackActivityLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan.SetItemAdapterLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.alinghuojiekuan.LingHuoJieHuanZhuXiaoActivity;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.apilinghuojiekuan.HttpLingHuoJieHuanApi;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.BaseModelLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.ConfigEntityLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.ProductLingHuoJieHuanModel;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mlinghuojiekuan.SetLingHuoJieHuanModel;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.mvp.XFragment;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.ApiSubscriber;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.NetError;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.net.XApi;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.MyToastLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.LingHuoJieHuanOpenUtil;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.ulinghuojiekuan.PreferencesOpenUtilLingHuoJieHuan;
import com.linghuojiehuanopwesdf.dsfwethdfgwe.wlinghuojiekuan.RemindLingHuoJieHuanDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFragmentLingHuoJieHuan extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.goods_im)
    View goods_im;

    private ProductLingHuoJieHuanModel productLingHuoJieHuanModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterLingHuoJieHuan setItemAdapterLingHuoJieHuan;

    private RemindLingHuoJieHuanDialog dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferencesOpenUtilLingHuoJieHuan.getString("app_mail");
        phone = PreferencesOpenUtilLingHuoJieHuan.getString("phone");
        userPhoneTv.setText(phone);
        initSetAdapter();
        goods_im.setOnClickListener(v -> {
            productClick(productLingHuoJieHuanModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ling_huo_jie_huan_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetLingHuoJieHuanModel model = new SetLingHuoJieHuanModel(R.drawable.xfvberyaey, "注册协议");
        SetLingHuoJieHuanModel model1 = new SetLingHuoJieHuanModel(R.drawable.xcvbdry, "隐私协议");
        SetLingHuoJieHuanModel model2 = new SetLingHuoJieHuanModel(R.drawable.esrrtsyxfgjh, "意见反馈");
        SetLingHuoJieHuanModel model3 = new SetLingHuoJieHuanModel(R.drawable.dfgaerysry, "关于我们");
        SetLingHuoJieHuanModel model4 = new SetLingHuoJieHuanModel(R.drawable.jkdrtusra, "个性化推荐");
        SetLingHuoJieHuanModel model5 = new SetLingHuoJieHuanModel(R.drawable.wweerrsuy, "投诉邮箱");
        SetLingHuoJieHuanModel model6 = new SetLingHuoJieHuanModel(R.drawable.rstyszdhf, "注销账户");
        SetLingHuoJieHuanModel model7 = new SetLingHuoJieHuanModel(R.drawable.cvbneryaey, "退出登录");
        List<SetLingHuoJieHuanModel> list = new ArrayList<>();
        List<SetLingHuoJieHuanModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapterLingHuoJieHuan = new SetItemAdapterLingHuoJieHuan(R.layout.adpater_set_item_ling_huo_jie_huan, list);
        setItemAdapterLingHuoJieHuan.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpLingHuoJieHuanApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    LingHuoJieHuanOpenUtil.jumpPage(getActivity(), JumpH5LingHuoJieHuanActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpLingHuoJieHuanApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    LingHuoJieHuanOpenUtil.jumpPage(getActivity(), JumpH5LingHuoJieHuanActivity.class, webBundle);
                    break;
                case 2:
                    LingHuoJieHuanOpenUtil.jumpPage(getActivity(), FeedbackActivityLingHuoJieHuan.class);
                    break;
                case 3:
                    LingHuoJieHuanOpenUtil.jumpPage(getActivity(), AboutInfoActivityLingHuoJieHuan.class);
                    break;
                case 4:
                    dialog = new RemindLingHuoJieHuanDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindLingHuoJieHuanDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToastLingHuoJieHuan.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToastLingHuoJieHuan.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    LingHuoJieHuanOpenUtil.jumpPage(getActivity(), LingHuoJieHuanZhuXiaoActivity.class);
                    break;
                case 7:
                    dialog = new RemindLingHuoJieHuanDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindLingHuoJieHuanDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilLingHuoJieHuan.saveString("phone", "");
                            LingHuoJieHuanOpenUtil.jumpPage(getActivity(), LingHuoJieHuanDlActivity.class);
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
        setList.setAdapter(setItemAdapterLingHuoJieHuan);
    }

    public void getConfig() {
            HttpLingHuoJieHuanApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieHuan<ConfigEntityLingHuoJieHuan>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieHuan<ConfigEntityLingHuoJieHuan> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilLingHuoJieHuan.saveString("app_mail", mailStr);
                                    dialog = new RemindLingHuoJieHuanDialog(getActivity()).setCancelText("取消")
                                            .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new RemindLingHuoJieHuanDialog.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            MyToastLingHuoJieHuan.showShort("复制成功");
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

    public void toWeb(ProductLingHuoJieHuanModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            LingHuoJieHuanOpenUtil.jumpPage(getActivity(), JumpH5LingHuoJieHuanActivity.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesOpenUtilLingHuoJieHuan.getInt("mobileType");
            phone = PreferencesOpenUtilLingHuoJieHuan.getString("phone");
            HttpLingHuoJieHuanApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieHuan<List<ProductLingHuoJieHuanModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            LingHuoJieHuanOpenUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieHuan<List<ProductLingHuoJieHuanModel>> baseModelLingHuoJieHuan) {
                            if (baseModelLingHuoJieHuan != null) {
                                if (baseModelLingHuoJieHuan.getCode() == 200 && baseModelLingHuoJieHuan.getData() != null) {
                                    if (baseModelLingHuoJieHuan.getData() != null && baseModelLingHuoJieHuan.getData().size() > 0) {
                                        productLingHuoJieHuanModel = baseModelLingHuoJieHuan.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductLingHuoJieHuanModel model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilLingHuoJieHuan.getString("phone");
            HttpLingHuoJieHuanApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLingHuoJieHuan>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseModelLingHuoJieHuan baseModelLingHuoJieHuan) {
                            toWeb(model);
                        }
                    });
    }
}
