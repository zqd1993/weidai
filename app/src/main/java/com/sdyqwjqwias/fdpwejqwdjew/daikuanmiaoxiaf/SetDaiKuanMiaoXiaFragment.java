package com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaf;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa.AboutInfoActivityDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa.DlDaiKuanMiaoXiaActivity;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa.FeedbackDaiKuanMiaoXiaActivity;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa.JumpH5ActivityDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa.SetItemAdapterDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaa.ZhuXiaoDaiKuanMiaoXiaActivity;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaapi.DaiKuanMiaoXiaHttpApi;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.DaiKuanMiaoXiaBaseModel;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.ConfigEntityDaiKuanMiaoXia;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.ProductDaiKuanMiaoXiaModel;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiam.SetDaiKuanMiaoXiaModel;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiaw.RemindDaiKuanMiaoXiaDialog;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XFragment;
import com.sdyqwjqwias.fdpwejqwdjew.net.ApiSubscriber;
import com.sdyqwjqwias.fdpwejqwdjew.net.NetError;
import com.sdyqwjqwias.fdpwejqwdjew.net.XApi;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.MyDaiKuanMiaoXiaToast;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.OpenDaiKuanMiaoXiaUtil;
import com.sdyqwjqwias.fdpwejqwdjew.daikuanmiaoxiau.PreferencesOpenUtilDaiKuanMiaoXia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetDaiKuanMiaoXiaFragment extends XFragment {
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductDaiKuanMiaoXiaModel productDaiKuanMiaoXiaModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterDaiKuanMiaoXia setItemAdapterDaiKuanMiaoXia;

    private RemindDaiKuanMiaoXiaDialog dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferencesOpenUtilDaiKuanMiaoXia.getString("app_mail");
        phone = PreferencesOpenUtilDaiKuanMiaoXia.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dai_kuan_miao_xia_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetDaiKuanMiaoXiaModel model = new SetDaiKuanMiaoXiaModel(R.drawable.jjsrtusdfu, "注册协议");
        SetDaiKuanMiaoXiaModel model1 = new SetDaiKuanMiaoXiaModel(R.drawable.xfcbnsrtusr, "隐私协议");
        SetDaiKuanMiaoXiaModel model2 = new SetDaiKuanMiaoXiaModel(R.drawable.weertryxf, "意见反馈");
        SetDaiKuanMiaoXiaModel model3 = new SetDaiKuanMiaoXiaModel(R.drawable.sfxfghsrtu, "关于我们");
        SetDaiKuanMiaoXiaModel model4 = new SetDaiKuanMiaoXiaModel(R.drawable.rtsyrtxfus, "个性化推荐");
        SetDaiKuanMiaoXiaModel model5 = new SetDaiKuanMiaoXiaModel(R.drawable.bnsrtuseu, "投诉邮箱");
        SetDaiKuanMiaoXiaModel model6 = new SetDaiKuanMiaoXiaModel(R.drawable.vbnxchjsftu, "注销账户");
        SetDaiKuanMiaoXiaModel model7 = new SetDaiKuanMiaoXiaModel(R.drawable.jjsrtyuaeru, "退出登录");
        List<SetDaiKuanMiaoXiaModel> list = new ArrayList<>();
        List<SetDaiKuanMiaoXiaModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapterDaiKuanMiaoXia = new SetItemAdapterDaiKuanMiaoXia(R.layout.adpater_set_item_dai_kuan_miao_xia, list);
        setItemAdapterDaiKuanMiaoXia.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", DaiKuanMiaoXiaHttpApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenDaiKuanMiaoXiaUtil.jumpPage(getActivity(), JumpH5ActivityDaiKuanMiaoXia.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", DaiKuanMiaoXiaHttpApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenDaiKuanMiaoXiaUtil.jumpPage(getActivity(), JumpH5ActivityDaiKuanMiaoXia.class, webBundle);
                    break;
                case 2:
                    OpenDaiKuanMiaoXiaUtil.jumpPage(getActivity(), FeedbackDaiKuanMiaoXiaActivity.class);
                    break;
                case 3:
                    OpenDaiKuanMiaoXiaUtil.jumpPage(getActivity(), AboutInfoActivityDaiKuanMiaoXia.class);
                    break;
                case 4:
                    dialog = new RemindDaiKuanMiaoXiaDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDaiKuanMiaoXiaDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyDaiKuanMiaoXiaToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyDaiKuanMiaoXiaToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpenDaiKuanMiaoXiaUtil.jumpPage(getActivity(), ZhuXiaoDaiKuanMiaoXiaActivity.class);
                    break;
                case 7:
                    dialog = new RemindDaiKuanMiaoXiaDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindDaiKuanMiaoXiaDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilDaiKuanMiaoXia.saveString("phone", "");
                            OpenDaiKuanMiaoXiaUtil.jumpPage(getActivity(), DlDaiKuanMiaoXiaActivity.class);
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
        setList.setAdapter(setItemAdapterDaiKuanMiaoXia);
    }

    public void getConfig() {
            DaiKuanMiaoXiaHttpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<DaiKuanMiaoXiaBaseModel<ConfigEntityDaiKuanMiaoXia>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(DaiKuanMiaoXiaBaseModel<ConfigEntityDaiKuanMiaoXia> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilDaiKuanMiaoXia.saveString("app_mail", mailStr);
                                    dialog = new RemindDaiKuanMiaoXiaDialog(getActivity()).setCancelText("取消")
                                            .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new RemindDaiKuanMiaoXiaDialog.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            MyDaiKuanMiaoXiaToast.showShort("复制成功");
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

    public void toWeb(ProductDaiKuanMiaoXiaModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenDaiKuanMiaoXiaUtil.jumpPage(getActivity(), JumpH5ActivityDaiKuanMiaoXia.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesOpenUtilDaiKuanMiaoXia.getInt("mobileType");
            phone = PreferencesOpenUtilDaiKuanMiaoXia.getString("phone");
            DaiKuanMiaoXiaHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<DaiKuanMiaoXiaBaseModel<List<ProductDaiKuanMiaoXiaModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenDaiKuanMiaoXiaUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(DaiKuanMiaoXiaBaseModel<List<ProductDaiKuanMiaoXiaModel>> daiKuanMiaoXiaBaseModel) {
                            if (daiKuanMiaoXiaBaseModel != null) {
                                if (daiKuanMiaoXiaBaseModel.getCode() == 200 && daiKuanMiaoXiaBaseModel.getData() != null) {
                                    if (daiKuanMiaoXiaBaseModel.getData() != null && daiKuanMiaoXiaBaseModel.getData().size() > 0) {
                                        productDaiKuanMiaoXiaModel = daiKuanMiaoXiaBaseModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductDaiKuanMiaoXiaModel model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilDaiKuanMiaoXia.getString("phone");
            DaiKuanMiaoXiaHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<DaiKuanMiaoXiaBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(DaiKuanMiaoXiaBaseModel daiKuanMiaoXiaBaseModel) {
                            toWeb(model);
                        }
                    });
    }
}
