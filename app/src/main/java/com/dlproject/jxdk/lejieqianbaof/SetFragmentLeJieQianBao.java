package com.dlproject.jxdk.lejieqianbaof;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dlproject.jxdk.R;
import com.dlproject.jxdk.lejieqianbaoa.AboutInfoActivityLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaoa.JumpH5ActivityLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaoa.LeJieQianBaoDlActivity;
import com.dlproject.jxdk.lejieqianbaoa.FeedbackActivityLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaoa.SetItemAdapterLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaoa.LeJieQianBaoZhuXiaoActivity;
import com.dlproject.jxdk.lejieqianbaoapi.HttpApiLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.BaseModelLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.ConfigEntityLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.ProductModelLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaom.SetModelLeJieQianBao;
import com.dlproject.jxdk.mvp.XFragment;
import com.dlproject.jxdk.net.ApiSubscriber;
import com.dlproject.jxdk.net.NetError;
import com.dlproject.jxdk.net.XApi;
import com.dlproject.jxdk.lejieqianbaou.MyToastLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaou.LeJieQianBaoOpenUtil;
import com.dlproject.jxdk.lejieqianbaou.PreferencesOpenUtilLeJieQianBao;
import com.dlproject.jxdk.lejieqianbaow.RemindLeJieQianBaoDialog;

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
        productList();
        initSetAdapter();
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
        SetModelLeJieQianBao model = new SetModelLeJieQianBao(R.drawable.klfyudtyi, "注册协议");
        SetModelLeJieQianBao model1 = new SetModelLeJieQianBao(R.drawable.wrtfgjdrtu, "隐私协议");
        SetModelLeJieQianBao model2 = new SetModelLeJieQianBao(R.drawable.wtrdxyudrtu, "意见反馈");
        SetModelLeJieQianBao model3 = new SetModelLeJieQianBao(R.drawable.ertyxftu, "关于我们");
        SetModelLeJieQianBao model4 = new SetModelLeJieQianBao(R.drawable.xfghjsrtu, "个性化推荐");
        SetModelLeJieQianBao model5 = new SetModelLeJieQianBao(R.drawable.rdtufgcj, "投诉邮箱");
        SetModelLeJieQianBao model6 = new SetModelLeJieQianBao(R.drawable.xxvbndrtu, "注销账户");
        SetModelLeJieQianBao model7 = new SetModelLeJieQianBao(R.drawable.zvbrsdtusr, "退出登录");
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
        setList.setLayoutManager(new LinearLayoutManager(getActivity()));
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
