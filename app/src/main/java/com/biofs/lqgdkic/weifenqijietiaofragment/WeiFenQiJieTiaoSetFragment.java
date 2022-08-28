package com.biofs.lqgdkic.weifenqijietiaofragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.biofs.lqgdkic.R;
import com.biofs.lqgdkic.weifenqijietiaoactivity.AboutInfoActivityWeiFenQiJieTiaoNews;
import com.biofs.lqgdkic.weifenqijietiaoactivity.DlWeiFenQiJieTiaoActivity;
import com.biofs.lqgdkic.weifenqijietiaoactivity.JumpWeiFenQiJieTiaoH5Activity;
import com.biofs.lqgdkic.weifenqijietiaoactivity.SetItemAdapterWeiFenQiJieTiao;
import com.biofs.lqgdkic.weifenqijietiaoactivity.ZhuXiaoActivityWeiFenQiJieTiao;
import com.biofs.lqgdkic.weifenqijietiaoapi.WeiFenQiJieTiaoApi;
import com.biofs.lqgdkic.weifenqijietiaomodel.BaseModelWeiFenQiJieTiao;
import com.biofs.lqgdkic.weifenqijietiaomodel.SetWeiFenQiJieTiaoModel;
import com.biofs.lqgdkic.weifenqijietiaomodel.WeiFenQiJieTiaoConfigEntity;
import com.biofs.lqgdkic.weifenqijietiaomodel.ProductWeiFenQiJieTiaoModel;
import com.biofs.lqgdkic.mvp.XActivity;
import com.biofs.lqgdkic.mvp.XFragment;
import com.biofs.lqgdkic.net.ApiSubscriber;
import com.biofs.lqgdkic.net.NetError;
import com.biofs.lqgdkic.net.XApi;
import com.biofs.lqgdkic.weifenqijietiaoutils.MyToastWeiFenQiJieTiao;
import com.biofs.lqgdkic.weifenqijietiaoutils.OpenWeiFenQiJieTiaoUtil;
import com.biofs.lqgdkic.weifenqijietiaoutils.WeiFenQiJieTiaoPreferencesOpenUtil;
import com.biofs.lqgdkic.weifenqijietiaoweidgt.RemindDialogWeiFenQiJieTiao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WeiFenQiJieTiaoSetFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.logout_tv)
    View logout_tv;

    private ProductWeiFenQiJieTiaoModel productWeiFenQiJieTiaoModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterWeiFenQiJieTiao setItemAdapterWeiFenQiJieTiao, setItemAdapterWeiFenQiJieTiao1;

    private RemindDialogWeiFenQiJieTiao dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = WeiFenQiJieTiaoPreferencesOpenUtil.getString("app_mail");
        phone = WeiFenQiJieTiaoPreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        initSetAdapter();
        logout_tv.setOnClickListener(v -> {
            dialog = new RemindDialogWeiFenQiJieTiao(getActivity()).setCancelText("取消")
                    .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
            dialog.setOnButtonClickListener(new RemindDialogWeiFenQiJieTiao.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    WeiFenQiJieTiaoPreferencesOpenUtil.saveString("phone", "");
                    OpenWeiFenQiJieTiaoUtil.getValue((XActivity) getActivity(), DlWeiFenQiJieTiaoActivity.class, null, true);
                }

                @Override
                public void onCancelClicked() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wei_fen_qi_jie_tiao_set;
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetWeiFenQiJieTiaoModel model = new SetWeiFenQiJieTiaoModel(R.drawable.etxfghxfh, "注册协议");
        SetWeiFenQiJieTiaoModel model1 = new SetWeiFenQiJieTiaoModel(R.drawable.wsgxdfy, "隐私协议");
//        SetWeiFenQiJieTiaoModel model2 = new SetWeiFenQiJieTiaoModel(R.drawable.lrthsetdf, "意见反馈");
        SetWeiFenQiJieTiaoModel model3 = new SetWeiFenQiJieTiaoModel(R.drawable.bbdryserty, "关于我们");
        SetWeiFenQiJieTiaoModel model4 = new SetWeiFenQiJieTiaoModel(R.drawable.cvberyaery, "个性化推荐");
        SetWeiFenQiJieTiaoModel model5 = new SetWeiFenQiJieTiaoModel(R.drawable.bzbcfgherzry, "投诉邮箱");
        SetWeiFenQiJieTiaoModel model6 = new SetWeiFenQiJieTiaoModel(R.drawable.aertygfxghsr, "注销账户");
//        SetWeiFenQiJieTiaoModel model7 = new SetWeiFenQiJieTiaoModel(R.drawable.ltyhfgxj, "退出登录");
        List<SetWeiFenQiJieTiaoModel> list = new ArrayList<>();
        List<SetWeiFenQiJieTiaoModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
//        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
//        list1.add(model7);
        setItemAdapterWeiFenQiJieTiao = new SetItemAdapterWeiFenQiJieTiao(R.layout.adpater_set_item_1, list);
        setItemAdapterWeiFenQiJieTiao.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", WeiFenQiJieTiaoApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenWeiFenQiJieTiaoUtil.getValue((XActivity) getActivity(), JumpWeiFenQiJieTiaoH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", WeiFenQiJieTiaoApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenWeiFenQiJieTiaoUtil.getValue((XActivity) getActivity(), JumpWeiFenQiJieTiaoH5Activity.class, webBundle);
                    break;
//                case 2:
//                    OpenWeiFenQiJieTiaoUtil.getValue((XActivity) getActivity(), WeiFenQiJieTiaoFeedbackActivity.class, null);
//                    break;
                case 2:
                    OpenWeiFenQiJieTiaoUtil.getValue((XActivity) getActivity(), AboutInfoActivityWeiFenQiJieTiaoNews.class, null);
                    break;
                case 3:
                    dialog = new RemindDialogWeiFenQiJieTiao(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialogWeiFenQiJieTiao.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToastWeiFenQiJieTiao.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToastWeiFenQiJieTiao.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 4:
                    getConfig();
                    break;
                case 5:
                    OpenWeiFenQiJieTiaoUtil.getValue((XActivity) getActivity(), ZhuXiaoActivityWeiFenQiJieTiao.class, null);
                    break;
//                case 6:
//                    dialog = new RemindDialogWeiFenQiJieTiao(getActivity()).setCancelText("取消")
//                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
//                    dialog.setOnButtonClickListener(new RemindDialogWeiFenQiJieTiao.OnButtonClickListener() {
//                        @Override
//                        public void onSureClicked() {
//                            dialog.dismiss();
//                            WeiFenQiJieTiaoPreferencesOpenUtil.saveString("phone", "");
//                            OpenWeiFenQiJieTiaoUtil.getValue((XActivity) getActivity(), DlWeiFenQiJieTiaoActivity.class, null, true);
//                        }
//
//                        @Override
//                        public void onCancelClicked() {
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemAdapterWeiFenQiJieTiao);
    }

    public void getConfig() {
        WeiFenQiJieTiaoApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelWeiFenQiJieTiao<WeiFenQiJieTiaoConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseModelWeiFenQiJieTiao<WeiFenQiJieTiaoConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                WeiFenQiJieTiaoPreferencesOpenUtil.saveString("app_mail", mailStr);
                                dialog = new RemindDialogWeiFenQiJieTiao(getActivity()).setCancelText("取消")
                                        .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                dialog.setOnButtonClickListener(new RemindDialogWeiFenQiJieTiao.OnButtonClickListener() {
                                    @Override
                                    public void onSureClicked() {
                                        clipData = ClipData.newPlainText(null, mailStr);
                                        clipboard.setPrimaryClip(clipData);
                                        MyToastWeiFenQiJieTiao.showShort("复制成功");
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

    public void toWeb(ProductWeiFenQiJieTiaoModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenWeiFenQiJieTiaoUtil.getValue((XActivity) getActivity(), JumpWeiFenQiJieTiaoH5Activity.class, bundle);
        }
    }

    public void productList() {
        mobileType = WeiFenQiJieTiaoPreferencesOpenUtil.getInt("mobileType");
        phone = WeiFenQiJieTiaoPreferencesOpenUtil.getString("phone");
        WeiFenQiJieTiaoApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelWeiFenQiJieTiao<List<ProductWeiFenQiJieTiaoModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenWeiFenQiJieTiaoUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseModelWeiFenQiJieTiao<List<ProductWeiFenQiJieTiaoModel>> baseModelWeiFenQiJieTiao) {
                        if (baseModelWeiFenQiJieTiao != null) {
                            if (baseModelWeiFenQiJieTiao.getCode() == 200 && baseModelWeiFenQiJieTiao.getData() != null) {
                                if (baseModelWeiFenQiJieTiao.getData() != null && baseModelWeiFenQiJieTiao.getData().size() > 0) {
                                    productWeiFenQiJieTiaoModel = baseModelWeiFenQiJieTiao.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductWeiFenQiJieTiaoModel model) {
        if (model == null) {
            return;
        }
        phone = WeiFenQiJieTiaoPreferencesOpenUtil.getString("phone");
        WeiFenQiJieTiaoApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelWeiFenQiJieTiao>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseModelWeiFenQiJieTiao baseModelWeiFenQiJieTiao) {
                        toWeb(model);
                    }
                });
    }
}
