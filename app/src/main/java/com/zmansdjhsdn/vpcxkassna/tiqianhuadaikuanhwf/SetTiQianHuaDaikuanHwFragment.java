package com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwf;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zmansdjhsdn.vpcxkassna.R;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa.TiQianHuaDaikuanHwAboutInfoActivity;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa.DlTiQianHuaDaikuanHwActivity;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa.FeedbackActivityTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa.JumpTiQianHuaDaikuanHwH5Activity;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa.SetItemAdapterTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwa.ZhuXiaoTiQianHuaDaikuanHwActivity;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwapi.HttpTiQianHuaDaikuanHwApi;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.BaseTiQianHuaDaikuanHwModel;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.ConfigTiQianHuaDaikuanHwEntity;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.ProductModelTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwm.SetTiQianHuaDaikuanHwModel;
import com.zmansdjhsdn.vpcxkassna.mvp.XFragment;
import com.zmansdjhsdn.vpcxkassna.net.ApiSubscriber;
import com.zmansdjhsdn.vpcxkassna.net.NetError;
import com.zmansdjhsdn.vpcxkassna.net.XApi;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.MyTiQianHuaDaikuanHwToast;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.OpenTiQianHuaDaikuanHwUtil;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhwu.PreferencesOpenUtilTiQianHuaDaikuanHw;
import com.zmansdjhsdn.vpcxkassna.tiqianhuadaikuanhww.RemindDialogTiQianHuaDaikuanHw;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetTiQianHuaDaikuanHwFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductModelTiQianHuaDaikuanHw productModelTiQianHuaDaikuanHw;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterTiQianHuaDaikuanHw setItemAdapterTiQianHuaDaikuanHw;

    private RemindDialogTiQianHuaDaikuanHw dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferencesOpenUtilTiQianHuaDaikuanHw.getString("app_mail");
        phone = PreferencesOpenUtilTiQianHuaDaikuanHw.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ti_qian_hua_dai_kuan_hw_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetTiQianHuaDaikuanHwModel model = new SetTiQianHuaDaikuanHwModel(R.drawable.mnxrtyse, "注册协议");
        SetTiQianHuaDaikuanHwModel model1 = new SetTiQianHuaDaikuanHwModel(R.drawable.eertyxfjhzs, "隐私协议");
        SetTiQianHuaDaikuanHwModel model2 = new SetTiQianHuaDaikuanHwModel(R.drawable.tuxfgser, "意见反馈");
        SetTiQianHuaDaikuanHwModel model3 = new SetTiQianHuaDaikuanHwModel(R.drawable.mnxfveyase, "关于我们");
//        SetTiQianHuaDaikuanHwModel model4 = new SetTiQianHuaDaikuanHwModel(R.drawable.bxcvbesrya, "个性化推荐");
        SetTiQianHuaDaikuanHwModel model5 = new SetTiQianHuaDaikuanHwModel(R.drawable.mmvbnsertys, "联系客服");
        SetTiQianHuaDaikuanHwModel model6 = new SetTiQianHuaDaikuanHwModel(R.drawable.rrxdfhareyy, "注销账户");
        SetTiQianHuaDaikuanHwModel model7 = new SetTiQianHuaDaikuanHwModel(R.drawable.rtyfghsrsa, "退出登录");
        List<SetTiQianHuaDaikuanHwModel> list = new ArrayList<>();
        List<SetTiQianHuaDaikuanHwModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
//        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapterTiQianHuaDaikuanHw = new SetItemAdapterTiQianHuaDaikuanHw(R.layout.adpater_set_item_ti_qian_hua_dai_kuan_hw, list);
        setItemAdapterTiQianHuaDaikuanHw.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpTiQianHuaDaikuanHwApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenTiQianHuaDaikuanHwUtil.jumpPage(getActivity(), JumpTiQianHuaDaikuanHwH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpTiQianHuaDaikuanHwApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenTiQianHuaDaikuanHwUtil.jumpPage(getActivity(), JumpTiQianHuaDaikuanHwH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenTiQianHuaDaikuanHwUtil.jumpPage(getActivity(), FeedbackActivityTiQianHuaDaikuanHw.class);
                    break;
                case 3:
                    OpenTiQianHuaDaikuanHwUtil.jumpPage(getActivity(), TiQianHuaDaikuanHwAboutInfoActivity.class);
                    break;
//                case 4:
//                    dialog = new RemindDialogTiQianHuaDaikuanHw(getActivity()).setCancelText("开启")
//                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
//                    dialog.setOnButtonClickListener(new RemindDialogTiQianHuaDaikuanHw.OnButtonClickListener() {
//                        @Override
//                        public void onSureClicked() {
//                            MyTiQianHuaDaikuanHwToast.showShort("关闭成功");
//                            dialog.dismiss();
//                        }
//
//                        @Override
//                        public void onCancelClicked() {
//                            MyTiQianHuaDaikuanHwToast.showShort("开启成功");
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                    break;
                case 4:
                    getConfig();
                    break;
                case 5:
                    OpenTiQianHuaDaikuanHwUtil.jumpPage(getActivity(), ZhuXiaoTiQianHuaDaikuanHwActivity.class);
                    break;
                case 6:
                    dialog = new RemindDialogTiQianHuaDaikuanHw(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindDialogTiQianHuaDaikuanHw.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilTiQianHuaDaikuanHw.saveString("phone", "");
                            OpenTiQianHuaDaikuanHwUtil.jumpPage(getActivity(), DlTiQianHuaDaikuanHwActivity.class);
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
        setList.setAdapter(setItemAdapterTiQianHuaDaikuanHw);
    }

    public void getConfig() {
            HttpTiQianHuaDaikuanHwApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTiQianHuaDaikuanHwModel<ConfigTiQianHuaDaikuanHwEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseTiQianHuaDaikuanHwModel<ConfigTiQianHuaDaikuanHwEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilTiQianHuaDaikuanHw.saveString("app_mail", mailStr);
                                    dialog = new RemindDialogTiQianHuaDaikuanHw(getActivity()).setCancelText("取消")
                                            .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new RemindDialogTiQianHuaDaikuanHw.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            MyTiQianHuaDaikuanHwToast.showShort("复制成功");
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

    public void toWeb(ProductModelTiQianHuaDaikuanHw model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenTiQianHuaDaikuanHwUtil.jumpPage(getActivity(), JumpTiQianHuaDaikuanHwH5Activity.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesOpenUtilTiQianHuaDaikuanHw.getInt("mobileType");
            phone = PreferencesOpenUtilTiQianHuaDaikuanHw.getString("phone");
            HttpTiQianHuaDaikuanHwApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTiQianHuaDaikuanHwModel<List<ProductModelTiQianHuaDaikuanHw>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenTiQianHuaDaikuanHwUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseTiQianHuaDaikuanHwModel<List<ProductModelTiQianHuaDaikuanHw>> baseTiQianHuaDaikuanHwModel) {
                            if (baseTiQianHuaDaikuanHwModel != null) {
                                if (baseTiQianHuaDaikuanHwModel.getCode() == 200 && baseTiQianHuaDaikuanHwModel.getData() != null) {
                                    if (baseTiQianHuaDaikuanHwModel.getData() != null && baseTiQianHuaDaikuanHwModel.getData().size() > 0) {
                                        productModelTiQianHuaDaikuanHw = baseTiQianHuaDaikuanHwModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductModelTiQianHuaDaikuanHw model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilTiQianHuaDaikuanHw.getString("phone");
            HttpTiQianHuaDaikuanHwApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTiQianHuaDaikuanHwModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseTiQianHuaDaikuanHwModel baseTiQianHuaDaikuanHwModel) {
                            toWeb(model);
                        }
                    });
    }
}
