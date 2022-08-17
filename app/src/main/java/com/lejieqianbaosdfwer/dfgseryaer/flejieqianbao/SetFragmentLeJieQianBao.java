package com.lejieqianbaosdfwer.dfgseryaer.flejieqianbao;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lejieqianbaosdfwer.dfgseryaer.R;
import com.lejieqianbaosdfwer.dfgseryaer.alejieqianbao.AboutInfoActivityLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.alejieqianbao.JumpH5ActivityLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.alejieqianbao.LeJieQianBaoDlActivity;
import com.lejieqianbaosdfwer.dfgseryaer.alejieqianbao.FeedbackActivityLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.alejieqianbao.SetItemAdapterLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.alejieqianbao.LeJieQianBaoZhuXiaoActivity;
import com.lejieqianbaosdfwer.dfgseryaer.apilejieqianbao.HttpApiLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.mlejieqianbao.BaseModelLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.mlejieqianbao.ConfigEntityLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.mlejieqianbao.ProductModelLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.mlejieqianbao.SetModelLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.mvp.XFragment;
import com.lejieqianbaosdfwer.dfgseryaer.net.ApiSubscriber;
import com.lejieqianbaosdfwer.dfgseryaer.net.NetError;
import com.lejieqianbaosdfwer.dfgseryaer.net.XApi;
import com.lejieqianbaosdfwer.dfgseryaer.ulejieqianbao.MyToastLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.ulejieqianbao.LeJieQianBaoOpenUtil;
import com.lejieqianbaosdfwer.dfgseryaer.ulejieqianbao.PreferencesOpenUtilLeJieQianBao;
import com.lejieqianbaosdfwer.dfgseryaer.wlejieqianbao.RemindLeJieQianBaoDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFragmentLeJieQianBao extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.goods_im)
    View goods_im;

    private ProductModelLeJieQianBao productModelLeJieQianBao;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterLeJieQianBao setItemAdapterLeJieQianBao;

    private RemindLeJieQianBaoDialog dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferencesOpenUtilLeJieQianBao.getString("app_mail");
        phone = PreferencesOpenUtilLeJieQianBao.getString("phone");
        userPhoneTv.setText(phone);
        initSetAdapter();
        goods_im.setOnClickListener(v -> {
            productClick(productModelLeJieQianBao);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_le_jie_qian_bao_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetModelLeJieQianBao model = new SetModelLeJieQianBao(R.drawable.xfvberyaey, "注册协议");
        SetModelLeJieQianBao model1 = new SetModelLeJieQianBao(R.drawable.xcvbdry, "隐私协议");
        SetModelLeJieQianBao model2 = new SetModelLeJieQianBao(R.drawable.esrrtsyxfgjh, "意见反馈");
        SetModelLeJieQianBao model3 = new SetModelLeJieQianBao(R.drawable.dfgaerysry, "关于我们");
        SetModelLeJieQianBao model4 = new SetModelLeJieQianBao(R.drawable.jkdrtusra, "个性化推荐");
        SetModelLeJieQianBao model5 = new SetModelLeJieQianBao(R.drawable.wweerrsuy, "投诉邮箱");
        SetModelLeJieQianBao model6 = new SetModelLeJieQianBao(R.drawable.rstyszdhf, "注销账户");
        SetModelLeJieQianBao model7 = new SetModelLeJieQianBao(R.drawable.cvbneryaey, "退出登录");
        List<SetModelLeJieQianBao> list = new ArrayList<>();
        List<SetModelLeJieQianBao> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapterLeJieQianBao = new SetItemAdapterLeJieQianBao(R.layout.adpater_set_item_le_jie_qian_bao, list);
        setItemAdapterLeJieQianBao.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiLeJieQianBao.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    LeJieQianBaoOpenUtil.jumpPage(getActivity(), JumpH5ActivityLeJieQianBao.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiLeJieQianBao.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    LeJieQianBaoOpenUtil.jumpPage(getActivity(), JumpH5ActivityLeJieQianBao.class, webBundle);
                    break;
                case 2:
                    LeJieQianBaoOpenUtil.jumpPage(getActivity(), FeedbackActivityLeJieQianBao.class);
                    break;
                case 3:
                    LeJieQianBaoOpenUtil.jumpPage(getActivity(), AboutInfoActivityLeJieQianBao.class);
                    break;
                case 4:
                    dialog = new RemindLeJieQianBaoDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindLeJieQianBaoDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToastLeJieQianBao.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToastLeJieQianBao.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    LeJieQianBaoOpenUtil.jumpPage(getActivity(), LeJieQianBaoZhuXiaoActivity.class);
                    break;
                case 7:
                    dialog = new RemindLeJieQianBaoDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindLeJieQianBaoDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilLeJieQianBao.saveString("phone", "");
                            LeJieQianBaoOpenUtil.jumpPage(getActivity(), LeJieQianBaoDlActivity.class);
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
        setList.setAdapter(setItemAdapterLeJieQianBao);
    }

    public void getConfig() {
            HttpApiLeJieQianBao.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeJieQianBao<ConfigEntityLeJieQianBao>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseModelLeJieQianBao<ConfigEntityLeJieQianBao> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilLeJieQianBao.saveString("app_mail", mailStr);
                                    dialog = new RemindLeJieQianBaoDialog(getActivity()).setCancelText("取消")
                                            .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new RemindLeJieQianBaoDialog.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            MyToastLeJieQianBao.showShort("复制成功");
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

    public void toWeb(ProductModelLeJieQianBao model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            LeJieQianBaoOpenUtil.jumpPage(getActivity(), JumpH5ActivityLeJieQianBao.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesOpenUtilLeJieQianBao.getInt("mobileType");
            phone = PreferencesOpenUtilLeJieQianBao.getString("phone");
            HttpApiLeJieQianBao.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeJieQianBao<List<ProductModelLeJieQianBao>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            LeJieQianBaoOpenUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseModelLeJieQianBao<List<ProductModelLeJieQianBao>> baseModelLeJieQianBao) {
                            if (baseModelLeJieQianBao != null) {
                                if (baseModelLeJieQianBao.getCode() == 200 && baseModelLeJieQianBao.getData() != null) {
                                    if (baseModelLeJieQianBao.getData() != null && baseModelLeJieQianBao.getData().size() > 0) {
                                        productModelLeJieQianBao = baseModelLeJieQianBao.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductModelLeJieQianBao model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilLeJieQianBao.getString("phone");
            HttpApiLeJieQianBao.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeJieQianBao>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseModelLeJieQianBao baseModelLeJieQianBao) {
                            toWeb(model);
                        }
                    });
    }
}
