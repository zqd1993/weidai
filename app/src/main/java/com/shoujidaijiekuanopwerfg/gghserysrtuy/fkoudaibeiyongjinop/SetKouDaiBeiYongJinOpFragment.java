package com.shoujidaijiekuanopwerfg.gghserysrtuy.fkoudaibeiyongjinop;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoujidaijiekuanopwerfg.gghserysrtuy.R;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop.KouDaiBeiYongJinOpAboutInfoActivity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop.DlKouDaiBeiYongJinOpActivity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop.FeedbackActivityKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop.JumpKouDaiBeiYongJinOpH5Activity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop.SetItemAdapterKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.akoudaibeiyongjinop.ZhuXiaoKouDaiBeiYongJinOpActivity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.apikoudaibeiyongjinop.HttpKouDaiBeiYongJinOpApi;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.BaseKouDaiBeiYongJinOpModel;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.ConfigKouDaiBeiYongJinOpEntity;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.ProductModelKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mkoudaibeiyongjinop.SetKouDaiBeiYongJinOpModel;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.mvp.XFragment;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.ApiSubscriber;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.NetError;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.net.XApi;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.MyKouDaiBeiYongJinOpToast;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.OpenKouDaiBeiYongJinOpUtil;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.ukoudaibeiyongjinop.PreferencesOpenUtilKouDaiBeiYongJinOp;
import com.shoujidaijiekuanopwerfg.gghserysrtuy.wkoudaibeiyongjinop.RemindDialogKouDaiBeiYongJinOp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetKouDaiBeiYongJinOpFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductModelKouDaiBeiYongJinOp productModelKouDaiBeiYongJinOp;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterKouDaiBeiYongJinOp setItemAdapterKouDaiBeiYongJinOp;

    private RemindDialogKouDaiBeiYongJinOp dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferencesOpenUtilKouDaiBeiYongJinOp.getString("app_mail");
        phone = PreferencesOpenUtilKouDaiBeiYongJinOp.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kou_dai_bei_yong_jin_op_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetKouDaiBeiYongJinOpModel model = new SetKouDaiBeiYongJinOpModel(R.drawable.mnxrtyse, "注册协议");
        SetKouDaiBeiYongJinOpModel model1 = new SetKouDaiBeiYongJinOpModel(R.drawable.eertyxfjhzs, "隐私协议");
        SetKouDaiBeiYongJinOpModel model2 = new SetKouDaiBeiYongJinOpModel(R.drawable.tuxfgser, "意见反馈");
        SetKouDaiBeiYongJinOpModel model3 = new SetKouDaiBeiYongJinOpModel(R.drawable.mnxfveyase, "关于我们");
        SetKouDaiBeiYongJinOpModel model4 = new SetKouDaiBeiYongJinOpModel(R.drawable.bxcvbesrya, "个性化推荐");
        SetKouDaiBeiYongJinOpModel model5 = new SetKouDaiBeiYongJinOpModel(R.drawable.mmvbnsertys, "投诉邮箱");
        SetKouDaiBeiYongJinOpModel model6 = new SetKouDaiBeiYongJinOpModel(R.drawable.rrxdfhareyy, "注销账户");
        SetKouDaiBeiYongJinOpModel model7 = new SetKouDaiBeiYongJinOpModel(R.drawable.rtyfghsrsa, "退出登录");
        List<SetKouDaiBeiYongJinOpModel> list = new ArrayList<>();
        List<SetKouDaiBeiYongJinOpModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapterKouDaiBeiYongJinOp = new SetItemAdapterKouDaiBeiYongJinOp(R.layout.adpater_set_item_kou_dai_bei_yong_jin_op, list);
        setItemAdapterKouDaiBeiYongJinOp.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpKouDaiBeiYongJinOpApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenKouDaiBeiYongJinOpUtil.jumpPage(getActivity(), JumpKouDaiBeiYongJinOpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpKouDaiBeiYongJinOpApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenKouDaiBeiYongJinOpUtil.jumpPage(getActivity(), JumpKouDaiBeiYongJinOpH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenKouDaiBeiYongJinOpUtil.jumpPage(getActivity(), FeedbackActivityKouDaiBeiYongJinOp.class);
                    break;
                case 3:
                    OpenKouDaiBeiYongJinOpUtil.jumpPage(getActivity(), KouDaiBeiYongJinOpAboutInfoActivity.class);
                    break;
                case 4:
                    dialog = new RemindDialogKouDaiBeiYongJinOp(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialogKouDaiBeiYongJinOp.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyKouDaiBeiYongJinOpToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyKouDaiBeiYongJinOpToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpenKouDaiBeiYongJinOpUtil.jumpPage(getActivity(), ZhuXiaoKouDaiBeiYongJinOpActivity.class);
                    break;
                case 7:
                    dialog = new RemindDialogKouDaiBeiYongJinOp(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindDialogKouDaiBeiYongJinOp.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilKouDaiBeiYongJinOp.saveString("phone", "");
                            OpenKouDaiBeiYongJinOpUtil.jumpPage(getActivity(), DlKouDaiBeiYongJinOpActivity.class);
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
        setList.setAdapter(setItemAdapterKouDaiBeiYongJinOp);
    }

    public void getConfig() {
            HttpKouDaiBeiYongJinOpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinOpModel<ConfigKouDaiBeiYongJinOpEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinOpModel<ConfigKouDaiBeiYongJinOpEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilKouDaiBeiYongJinOp.saveString("app_mail", mailStr);
                                    dialog = new RemindDialogKouDaiBeiYongJinOp(getActivity()).setCancelText("取消")
                                            .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new RemindDialogKouDaiBeiYongJinOp.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            MyKouDaiBeiYongJinOpToast.showShort("复制成功");
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

    public void toWeb(ProductModelKouDaiBeiYongJinOp model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenKouDaiBeiYongJinOpUtil.jumpPage(getActivity(), JumpKouDaiBeiYongJinOpH5Activity.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesOpenUtilKouDaiBeiYongJinOp.getInt("mobileType");
            phone = PreferencesOpenUtilKouDaiBeiYongJinOp.getString("phone");
            HttpKouDaiBeiYongJinOpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinOpModel<List<ProductModelKouDaiBeiYongJinOp>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKouDaiBeiYongJinOpUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinOpModel<List<ProductModelKouDaiBeiYongJinOp>> baseKouDaiBeiYongJinOpModel) {
                            if (baseKouDaiBeiYongJinOpModel != null) {
                                if (baseKouDaiBeiYongJinOpModel.getCode() == 200 && baseKouDaiBeiYongJinOpModel.getData() != null) {
                                    if (baseKouDaiBeiYongJinOpModel.getData() != null && baseKouDaiBeiYongJinOpModel.getData().size() > 0) {
                                        productModelKouDaiBeiYongJinOp = baseKouDaiBeiYongJinOpModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductModelKouDaiBeiYongJinOp model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilKouDaiBeiYongJinOp.getString("phone");
            HttpKouDaiBeiYongJinOpApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKouDaiBeiYongJinOpModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseKouDaiBeiYongJinOpModel baseKouDaiBeiYongJinOpModel) {
                            toWeb(model);
                        }
                    });
    }
}
