package com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaif;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dshqbzansdk.vpcvlsdksdhayjtop.R;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaia.AboutInfoActivityQingSongDai;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaia.DlQingSongDaiActivity;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaia.FeedbackQingSongDaiActivity;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaia.JumpH5QingSongDaiActivity;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaia.SetItemQingSongDaiAdapter;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaia.QingSongDaiZhuXiaoActivity;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiapi.HttpApiQingSongDai;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaim.BaseQingSongDaiModel;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaim.ConfigEntityQingSongDai;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaim.ProductQingSongDaiModel;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaim.SetQingSongDaiModel;
import com.dshqbzansdk.vpcvlsdksdhayjtop.mvp.XFragment;
import com.dshqbzansdk.vpcvlsdksdhayjtop.net.ApiSubscriber;
import com.dshqbzansdk.vpcvlsdksdhayjtop.net.NetError;
import com.dshqbzansdk.vpcvlsdksdhayjtop.net.XApi;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiu.MyQingSongDaiToast;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiu.OpenQingSongDaiUtil;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiu.PreferencesOpenUtilQingSongDai;
import com.dshqbzansdk.vpcvlsdksdhayjtop.qingsongdaiw.RemindQingSongDaiDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetQingSongDaiFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductQingSongDaiModel productQingSongDaiModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemQingSongDaiAdapter setItemQingSongDaiAdapter;

    private RemindQingSongDaiDialog dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferencesOpenUtilQingSongDai.getString("app_mail");
        phone = PreferencesOpenUtilQingSongDai.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_qing_song_dai_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetQingSongDaiModel model = new SetQingSongDaiModel(R.drawable.jawdfhsaery, "注册协议");
        SetQingSongDaiModel model1 = new SetQingSongDaiModel(R.drawable.jkserygaer, "隐私协议");
        SetQingSongDaiModel model2 = new SetQingSongDaiModel(R.drawable.josrtaery, "意见反馈");
        SetQingSongDaiModel model3 = new SetQingSongDaiModel(R.drawable.jaerytdfh, "关于我们");
        SetQingSongDaiModel model4 = new SetQingSongDaiModel(R.drawable.jjsfhaer, "个性化推荐");
        SetQingSongDaiModel model5 = new SetQingSongDaiModel(R.drawable.jaserydry, "投诉邮箱");
        SetQingSongDaiModel model6 = new SetQingSongDaiModel(R.drawable.jaertfchae, "注销账户");
        SetQingSongDaiModel model7 = new SetQingSongDaiModel(R.drawable.jaerydfhf, "退出登录");
        List<SetQingSongDaiModel> list = new ArrayList<>();
        List<SetQingSongDaiModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemQingSongDaiAdapter = new SetItemQingSongDaiAdapter(R.layout.adpater_set_item_qing_song_dai, list);
        setItemQingSongDaiAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiQingSongDai.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenQingSongDaiUtil.jumpPage(getActivity(), JumpH5QingSongDaiActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiQingSongDai.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenQingSongDaiUtil.jumpPage(getActivity(), JumpH5QingSongDaiActivity.class, webBundle);
                    break;
                case 2:
                    OpenQingSongDaiUtil.jumpPage(getActivity(), FeedbackQingSongDaiActivity.class);
                    break;
                case 3:
                    OpenQingSongDaiUtil.jumpPage(getActivity(), AboutInfoActivityQingSongDai.class);
                    break;
                case 4:
                    dialog = new RemindQingSongDaiDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindQingSongDaiDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyQingSongDaiToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyQingSongDaiToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpenQingSongDaiUtil.jumpPage(getActivity(), QingSongDaiZhuXiaoActivity.class);
                    break;
                case 7:
                    dialog = new RemindQingSongDaiDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindQingSongDaiDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilQingSongDai.saveString("phone", "");
                            OpenQingSongDaiUtil.jumpPage(getActivity(), DlQingSongDaiActivity.class);
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
        setList.setAdapter(setItemQingSongDaiAdapter);
    }

    public void getConfig() {
            HttpApiQingSongDai.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQingSongDaiModel<ConfigEntityQingSongDai>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseQingSongDaiModel<ConfigEntityQingSongDai> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilQingSongDai.saveString("app_mail", mailStr);
                                    dialog = new RemindQingSongDaiDialog(getActivity()).setCancelText("取消")
                                            .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new RemindQingSongDaiDialog.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            MyQingSongDaiToast.showShort("复制成功");
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

    public void toWeb(ProductQingSongDaiModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenQingSongDaiUtil.jumpPage(getActivity(), JumpH5QingSongDaiActivity.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesOpenUtilQingSongDai.getInt("mobileType");
            phone = PreferencesOpenUtilQingSongDai.getString("phone");
            HttpApiQingSongDai.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQingSongDaiModel<List<ProductQingSongDaiModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenQingSongDaiUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseQingSongDaiModel<List<ProductQingSongDaiModel>> baseQingSongDaiModel) {
                            if (baseQingSongDaiModel != null) {
                                if (baseQingSongDaiModel.getCode() == 200 && baseQingSongDaiModel.getData() != null) {
                                    if (baseQingSongDaiModel.getData() != null && baseQingSongDaiModel.getData().size() > 0) {
                                        productQingSongDaiModel = baseQingSongDaiModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductQingSongDaiModel model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilQingSongDai.getString("phone");
            HttpApiQingSongDai.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseQingSongDaiModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseQingSongDaiModel baseQingSongDaiModel) {
                            toWeb(model);
                        }
                    });
    }
}
