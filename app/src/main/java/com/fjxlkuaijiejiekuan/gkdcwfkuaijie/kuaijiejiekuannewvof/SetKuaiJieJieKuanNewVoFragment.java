package com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvof;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.R;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa.KuaiJieJieKuanNewVoAboutInfoActivity;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa.DlKuaiJieJieKuanNewVoActivity;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa.FeedbackActivityKuaiJieJieKuanNewVo;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa.JumpKuaiJieJieKuanNewVoH5Activity;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa.SetItemAdapterKuaiJieJieKuanNewVo;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoa.ZhuXiaoKuaiJieJieKuanNewVoActivity;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvoapi.HttpKuaiJieJieKuanNewVoApi;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.BaseKuaiJieJieKuanNewVoModel;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.ConfigKuaiJieJieKuanNewVoEntity;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.ProductModelKuaiJieJieKuanNewVo;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvom.SetKuaiJieJieKuanNewVoModel;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.mvp.XFragment;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.ApiSubscriber;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.NetError;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.net.XApi;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.MyKuaiJieJieKuanNewVoToast;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.OpenKuaiJieJieKuanNewVoUtil;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvou.PreferencesOpenUtilKuaiJieJieKuanNewVo;
import com.fjxlkuaijiejiekuan.gkdcwfkuaijie.kuaijiejiekuannewvow.RemindDialogKuaiJieJieKuanNewVo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetKuaiJieJieKuanNewVoFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductModelKuaiJieJieKuanNewVo productModelKuaiJieJieKuanNewVo;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterKuaiJieJieKuanNewVo setItemAdapterKouDaiBeiYongJinOp;

    private RemindDialogKuaiJieJieKuanNewVo dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferencesOpenUtilKuaiJieJieKuanNewVo.getString("app_mail");
        phone = PreferencesOpenUtilKuaiJieJieKuanNewVo.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kuai_jie_jie_kuan_new_op_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetKuaiJieJieKuanNewVoModel model = new SetKuaiJieJieKuanNewVoModel(R.drawable.mnxrtyse, "注册协议");
        SetKuaiJieJieKuanNewVoModel model1 = new SetKuaiJieJieKuanNewVoModel(R.drawable.eertyxfjhzs, "隐私协议");
        SetKuaiJieJieKuanNewVoModel model2 = new SetKuaiJieJieKuanNewVoModel(R.drawable.tuxfgser, "意见反馈");
        SetKuaiJieJieKuanNewVoModel model3 = new SetKuaiJieJieKuanNewVoModel(R.drawable.mnxfveyase, "关于我们");
        SetKuaiJieJieKuanNewVoModel model4 = new SetKuaiJieJieKuanNewVoModel(R.drawable.bxcvbesrya, "个性化推荐");
        SetKuaiJieJieKuanNewVoModel model5 = new SetKuaiJieJieKuanNewVoModel(R.drawable.mmvbnsertys, "投诉邮箱");
        SetKuaiJieJieKuanNewVoModel model6 = new SetKuaiJieJieKuanNewVoModel(R.drawable.rrxdfhareyy, "注销账户");
        SetKuaiJieJieKuanNewVoModel model7 = new SetKuaiJieJieKuanNewVoModel(R.drawable.rtyfghsrsa, "退出登录");
        List<SetKuaiJieJieKuanNewVoModel> list = new ArrayList<>();
        List<SetKuaiJieJieKuanNewVoModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapterKouDaiBeiYongJinOp = new SetItemAdapterKuaiJieJieKuanNewVo(R.layout.adpater_set_item_kuai_jie_jie_kuan_new_op, list);
        setItemAdapterKouDaiBeiYongJinOp.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpKuaiJieJieKuanNewVoApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenKuaiJieJieKuanNewVoUtil.jumpPage(getActivity(), JumpKuaiJieJieKuanNewVoH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpKuaiJieJieKuanNewVoApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenKuaiJieJieKuanNewVoUtil.jumpPage(getActivity(), JumpKuaiJieJieKuanNewVoH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenKuaiJieJieKuanNewVoUtil.jumpPage(getActivity(), FeedbackActivityKuaiJieJieKuanNewVo.class);
                    break;
                case 3:
                    OpenKuaiJieJieKuanNewVoUtil.jumpPage(getActivity(), KuaiJieJieKuanNewVoAboutInfoActivity.class);
                    break;
                case 4:
                    dialog = new RemindDialogKuaiJieJieKuanNewVo(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialogKuaiJieJieKuanNewVo.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyKuaiJieJieKuanNewVoToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyKuaiJieJieKuanNewVoToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpenKuaiJieJieKuanNewVoUtil.jumpPage(getActivity(), ZhuXiaoKuaiJieJieKuanNewVoActivity.class);
                    break;
                case 7:
                    dialog = new RemindDialogKuaiJieJieKuanNewVo(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindDialogKuaiJieJieKuanNewVo.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilKuaiJieJieKuanNewVo.saveString("phone", "");
                            OpenKuaiJieJieKuanNewVoUtil.jumpPage(getActivity(), DlKuaiJieJieKuanNewVoActivity.class);
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
            HttpKuaiJieJieKuanNewVoApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKuaiJieJieKuanNewVoModel<ConfigKuaiJieJieKuanNewVoEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseKuaiJieJieKuanNewVoModel<ConfigKuaiJieJieKuanNewVoEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilKuaiJieJieKuanNewVo.saveString("app_mail", mailStr);
                                    dialog = new RemindDialogKuaiJieJieKuanNewVo(getActivity()).setCancelText("取消")
                                            .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new RemindDialogKuaiJieJieKuanNewVo.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            MyKuaiJieJieKuanNewVoToast.showShort("复制成功");
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

    public void toWeb(ProductModelKuaiJieJieKuanNewVo model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenKuaiJieJieKuanNewVoUtil.jumpPage(getActivity(), JumpKuaiJieJieKuanNewVoH5Activity.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesOpenUtilKuaiJieJieKuanNewVo.getInt("mobileType");
            phone = PreferencesOpenUtilKuaiJieJieKuanNewVo.getString("phone");
            HttpKuaiJieJieKuanNewVoApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKuaiJieJieKuanNewVoModel<List<ProductModelKuaiJieJieKuanNewVo>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenKuaiJieJieKuanNewVoUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseKuaiJieJieKuanNewVoModel<List<ProductModelKuaiJieJieKuanNewVo>> baseKuaiJieJieKuanNewVoModel) {
                            if (baseKuaiJieJieKuanNewVoModel != null) {
                                if (baseKuaiJieJieKuanNewVoModel.getCode() == 200 && baseKuaiJieJieKuanNewVoModel.getData() != null) {
                                    if (baseKuaiJieJieKuanNewVoModel.getData() != null && baseKuaiJieJieKuanNewVoModel.getData().size() > 0) {
                                        productModelKuaiJieJieKuanNewVo = baseKuaiJieJieKuanNewVoModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductModelKuaiJieJieKuanNewVo model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilKuaiJieJieKuanNewVo.getString("phone");
            HttpKuaiJieJieKuanNewVoApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseKuaiJieJieKuanNewVoModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseKuaiJieJieKuanNewVoModel baseKuaiJieJieKuanNewVoModel) {
                            toWeb(model);
                        }
                    });
    }
}
