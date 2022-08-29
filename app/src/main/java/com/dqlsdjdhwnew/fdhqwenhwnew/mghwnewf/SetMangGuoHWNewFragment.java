package com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewf;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dqlsdjdhwnew.fdhqwenhwnew.R;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa.AboutInfoActivityMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa.DlMangGuoHWNewActivity;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa.FeedbackMangGuoHWNewActivity;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa.JumpH5MangGuoHWNewActivity;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa.MangGuoHWNewSetItemAdapter;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewa.MangGuoHWNewZhuXiaoActivity;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewapi.HttpApiMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewBaseModel;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewConfigEntity;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.ProductModelMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewm.MangGuoHWNewSetModel;
import com.dqlsdjdhwnew.fdhqwenhwnew.mvp.XFragment;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.ApiSubscriber;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.NetError;
import com.dqlsdjdhwnew.fdhqwenhwnew.net.XApi;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.MyMangGuoHWNewToast;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.MangGuoHWNewOpenUtil;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwnewu.PreferencesOpenUtilMangGuoHWNew;
import com.dqlsdjdhwnew.fdhqwenhwnew.mghwneww.MangGuoHWNewRemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetMangGuoHWNewFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductModelMangGuoHWNew productModelMangGuoHWNew;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private MangGuoHWNewSetItemAdapter mangGuoHWNewSetItemAdapter;

    private MangGuoHWNewRemindDialog dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferencesOpenUtilMangGuoHWNew.getString("app_mail");
        phone = PreferencesOpenUtilMangGuoHWNew.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mang_guo_hw_new_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        MangGuoHWNewSetModel model = new MangGuoHWNewSetModel(R.drawable.jsrtyxfh, "注册协议");
        MangGuoHWNewSetModel model1 = new MangGuoHWNewSetModel(R.drawable.ewrtdzhgsz, "隐私协议");
        MangGuoHWNewSetModel model2 = new MangGuoHWNewSetModel(R.drawable.bbsrtydrtu, "意见反馈");
        MangGuoHWNewSetModel model3 = new MangGuoHWNewSetModel(R.drawable.koytdjs, "关于我们");
//        MangGuoHWNewSetModel model4 = new MangGuoHWNewSetModel(R.drawable.wretgxdryh, "个性化推荐");
        MangGuoHWNewSetModel model5 = new MangGuoHWNewSetModel(R.drawable.rtyrthjxj, "联系客服");
        MangGuoHWNewSetModel model6 = new MangGuoHWNewSetModel(R.drawable.ergxfhsrt, "注销账户");
        MangGuoHWNewSetModel model7 = new MangGuoHWNewSetModel(R.drawable.xcbsrts, "退出登录");
        List<MangGuoHWNewSetModel> list = new ArrayList<>();
        List<MangGuoHWNewSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
//        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        mangGuoHWNewSetItemAdapter = new MangGuoHWNewSetItemAdapter(R.layout.adpater_mang_guo_hw_new_set_item, list);
        mangGuoHWNewSetItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiMangGuoHWNew.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    MangGuoHWNewOpenUtil.jumpPage(getActivity(), JumpH5MangGuoHWNewActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiMangGuoHWNew.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    MangGuoHWNewOpenUtil.jumpPage(getActivity(), JumpH5MangGuoHWNewActivity.class, webBundle);
                    break;
                case 2:
                    MangGuoHWNewOpenUtil.jumpPage(getActivity(), FeedbackMangGuoHWNewActivity.class);
                    break;
                case 3:
                    MangGuoHWNewOpenUtil.jumpPage(getActivity(), AboutInfoActivityMangGuoHWNew.class);
                    break;
//                case 4:
//                    dialog = new MangGuoHWNewRemindDialog(getActivity()).setCancelText("开启")
//                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
//                    dialog.setOnButtonClickListener(new MangGuoHWNewRemindDialog.OnButtonClickListener() {
//                        @Override
//                        public void onSureClicked() {
//                            MyMangGuoHWNewToast.showShort("关闭成功");
//                            dialog.dismiss();
//                        }
//
//                        @Override
//                        public void onCancelClicked() {
//                            MyMangGuoHWNewToast.showShort("开启成功");
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                    break;
                case 4:
                    getConfig();
                    break;
                case 5:
                    MangGuoHWNewOpenUtil.jumpPage(getActivity(), MangGuoHWNewZhuXiaoActivity.class);
                    break;
                case 6:
                    dialog = new MangGuoHWNewRemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new MangGuoHWNewRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilMangGuoHWNew.saveString("phone", "");
                            MangGuoHWNewOpenUtil.jumpPage(getActivity(), DlMangGuoHWNewActivity.class);
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
        setList.setAdapter(mangGuoHWNewSetItemAdapter);
    }

    public void getConfig() {
            HttpApiMangGuoHWNew.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<MangGuoHWNewBaseModel<MangGuoHWNewConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(MangGuoHWNewBaseModel<MangGuoHWNewConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilMangGuoHWNew.saveString("app_mail", mailStr);
                                    dialog = new MangGuoHWNewRemindDialog(getActivity()).setCancelText("取消")
                                            .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new MangGuoHWNewRemindDialog.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            MyMangGuoHWNewToast.showShort("复制成功");
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

    public void toWeb(ProductModelMangGuoHWNew model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            MangGuoHWNewOpenUtil.jumpPage(getActivity(), JumpH5MangGuoHWNewActivity.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesOpenUtilMangGuoHWNew.getInt("mobileType");
            phone = PreferencesOpenUtilMangGuoHWNew.getString("phone");
            HttpApiMangGuoHWNew.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MangGuoHWNewBaseModel<List<ProductModelMangGuoHWNew>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            MangGuoHWNewOpenUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(MangGuoHWNewBaseModel<List<ProductModelMangGuoHWNew>> mangGuoHWNewBaseModel) {
                            if (mangGuoHWNewBaseModel != null) {
                                if (mangGuoHWNewBaseModel.getCode() == 200 && mangGuoHWNewBaseModel.getData() != null) {
                                    if (mangGuoHWNewBaseModel.getData() != null && mangGuoHWNewBaseModel.getData().size() > 0) {
                                        productModelMangGuoHWNew = mangGuoHWNewBaseModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductModelMangGuoHWNew model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilMangGuoHWNew.getString("phone");
            HttpApiMangGuoHWNew.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MangGuoHWNewBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(MangGuoHWNewBaseModel mangGuoHWNewBaseModel) {
                            toWeb(model);
                        }
                    });
    }
}
