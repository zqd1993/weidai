package com.pwekqha.itmgs.koudaibeiyongjinf;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pwekqha.itmgs.R;
import com.pwekqha.itmgs.koudaibeiyongjina.AboutInfoActivityKouDaiBeiYongJin;
import com.pwekqha.itmgs.koudaibeiyongjina.DlKouDaiBeiYongJinActivity;
import com.pwekqha.itmgs.koudaibeiyongjina.FeedbackActivityKouDaiBeiYongJin;
import com.pwekqha.itmgs.koudaibeiyongjina.JumpH5KouDaiBeiYongJinActivity;
import com.pwekqha.itmgs.koudaibeiyongjina.SetItemKouDaiBeiYongJinAdapter;
import com.pwekqha.itmgs.koudaibeiyongjina.ZhuXiaoKouDaiBeiYongJinActivity;
import com.pwekqha.itmgs.koudaibeiyongjinapi.HttpKouDaiBeiYongJinApi;
import com.pwekqha.itmgs.koudaibeiyongjinm.BaseKouDaiBeiYongJinModel;
import com.pwekqha.itmgs.koudaibeiyongjinm.ConfigKouDaiBeiYongJinEntity;
import com.pwekqha.itmgs.koudaibeiyongjinm.ProductKouDaiBeiYongJinModel;
import com.pwekqha.itmgs.koudaibeiyongjinm.SetKouDaiBeiYongJinModel;
import com.pwekqha.itmgs.mvp.XFragment;
import com.pwekqha.itmgs.net.ApiSubscriber;
import com.pwekqha.itmgs.net.NetError;
import com.pwekqha.itmgs.net.XApi;
import com.pwekqha.itmgs.koudaibeiyongjinu.MyKouDaiBeiYongJinToast;
import com.pwekqha.itmgs.koudaibeiyongjinu.OpenUtilKouDaiBeiYongJin;
import com.pwekqha.itmgs.koudaibeiyongjinu.KouDaiBeiYongJinPreferencesOpenUtil;
import com.pwekqha.itmgs.koudaibeiyongjinw.RemindKouDaiBeiYongJinDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetKouDaiBeiYongJinFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductKouDaiBeiYongJinModel productKouDaiBeiYongJinModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemKouDaiBeiYongJinAdapter setItemKouDaiBeiYongJinAdapter;

    private RemindKouDaiBeiYongJinDialog dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = KouDaiBeiYongJinPreferencesOpenUtil.getString("app_mail");
        phone = KouDaiBeiYongJinPreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set_kou_dai_bei_yong_jin;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetKouDaiBeiYongJinModel model = new SetKouDaiBeiYongJinModel(R.drawable.rtygchjdr, "注册协议");
        SetKouDaiBeiYongJinModel model1 = new SetKouDaiBeiYongJinModel(R.drawable.eersruyrdu, "隐私协议");
        SetKouDaiBeiYongJinModel model2 = new SetKouDaiBeiYongJinModel(R.drawable.tytusrusr, "意见反馈");
        SetKouDaiBeiYongJinModel model3 = new SetKouDaiBeiYongJinModel(R.drawable.srtyxfgjh, "关于我们");
        SetKouDaiBeiYongJinModel model4 = new SetKouDaiBeiYongJinModel(R.drawable.rereyxf, "个性化推荐");
        SetKouDaiBeiYongJinModel model5 = new SetKouDaiBeiYongJinModel(R.drawable.rsryxfghs, "投诉邮箱");
        SetKouDaiBeiYongJinModel model6 = new SetKouDaiBeiYongJinModel(R.drawable.nnseraey, "注销账户");
        SetKouDaiBeiYongJinModel model7 = new SetKouDaiBeiYongJinModel(R.drawable.lpfyseryrs, "退出登录");
        List<SetKouDaiBeiYongJinModel> list = new ArrayList<>();
        List<SetKouDaiBeiYongJinModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemKouDaiBeiYongJinAdapter = new SetItemKouDaiBeiYongJinAdapter(R.layout.adpater_kou_dai_bei_yong_jin_set_item, list);
        setItemKouDaiBeiYongJinAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpKouDaiBeiYongJinApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenUtilKouDaiBeiYongJin.jumpPage(getActivity(), JumpH5KouDaiBeiYongJinActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpKouDaiBeiYongJinApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenUtilKouDaiBeiYongJin.jumpPage(getActivity(), JumpH5KouDaiBeiYongJinActivity.class, webBundle);
                    break;
                case 2:
                    OpenUtilKouDaiBeiYongJin.jumpPage(getActivity(), FeedbackActivityKouDaiBeiYongJin.class);
                    break;
                case 3:
                    OpenUtilKouDaiBeiYongJin.jumpPage(getActivity(), AboutInfoActivityKouDaiBeiYongJin.class);
                    break;
                case 4:
                    dialog = new RemindKouDaiBeiYongJinDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindKouDaiBeiYongJinDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyKouDaiBeiYongJinToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyKouDaiBeiYongJinToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpenUtilKouDaiBeiYongJin.jumpPage(getActivity(), ZhuXiaoKouDaiBeiYongJinActivity.class);
                    break;
                case 7:
                    dialog = new RemindKouDaiBeiYongJinDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindKouDaiBeiYongJinDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            KouDaiBeiYongJinPreferencesOpenUtil.saveString("phone", "");
                            OpenUtilKouDaiBeiYongJin.jumpPage(getActivity(), DlKouDaiBeiYongJinActivity.class);
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
        setList.setAdapter(setItemKouDaiBeiYongJinAdapter);
    }

    public void getConfig() {
            HttpKouDaiBeiYongJinApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinModel<ConfigKouDaiBeiYongJinEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinModel<ConfigKouDaiBeiYongJinEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    KouDaiBeiYongJinPreferencesOpenUtil.saveString("app_mail", mailStr);
                                    dialog = new RemindKouDaiBeiYongJinDialog(getActivity()).setCancelText("取消")
                                            .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new RemindKouDaiBeiYongJinDialog.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            MyKouDaiBeiYongJinToast.showShort("复制成功");
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

    public void toWeb(ProductKouDaiBeiYongJinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenUtilKouDaiBeiYongJin.jumpPage(getActivity(), JumpH5KouDaiBeiYongJinActivity.class, bundle);
        }
    }

    public void productList() {
            mobileType = KouDaiBeiYongJinPreferencesOpenUtil.getInt("mobileType");
            phone = KouDaiBeiYongJinPreferencesOpenUtil.getString("phone");
            HttpKouDaiBeiYongJinApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinModel<List<ProductKouDaiBeiYongJinModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtilKouDaiBeiYongJin.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinModel<List<ProductKouDaiBeiYongJinModel>> baseKouDaiBeiYongJinModel) {
                            if (baseKouDaiBeiYongJinModel != null) {
                                if (baseKouDaiBeiYongJinModel.getCode() == 200 && baseKouDaiBeiYongJinModel.getData() != null) {
                                    if (baseKouDaiBeiYongJinModel.getData() != null && baseKouDaiBeiYongJinModel.getData().size() > 0) {
                                        productKouDaiBeiYongJinModel = baseKouDaiBeiYongJinModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductKouDaiBeiYongJinModel model) {
            if (model == null) {
                return;
            }
            phone = KouDaiBeiYongJinPreferencesOpenUtil.getString("phone");
            HttpKouDaiBeiYongJinApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinModel baseKouDaiBeiYongJinModel) {
                            toWeb(model);
                        }
                    });
    }
}
