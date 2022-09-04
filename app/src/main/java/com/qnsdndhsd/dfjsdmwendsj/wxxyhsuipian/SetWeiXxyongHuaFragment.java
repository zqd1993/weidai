package com.qnsdndhsd.dfjsdmwendsj.wxxyhsuipian;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qnsdndhsd.dfjsdmwendsj.R;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhkongjian.RemindWeiXxyongHuaDialog;
import com.qnsdndhsd.dfjsdmwendsj.mvp.XActivity;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhshiti.ConfigWeiXxyongHuaEntity;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhyemian.AboutWeiXxyongHuaInfoActivity;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhyemian.DlWeiXxyongHuaActivity;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhyemian.FeedbackWeiXxyongHuaActivity;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhyemian.JumpH5WeiXxyongHuaActivity;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhyemian.SetItemWeiXxyongHuaAdapter;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhyemian.ZhuXiaoWeiXxyongHuaActivity;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhjiekou.WeiXxyongHuaApi;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhshiti.BaseWeiXxyongHuaModel;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhshiti.ProductWeiXxyongHuaModel;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhshiti.SetModelWeiXxyongHua;
import com.qnsdndhsd.dfjsdmwendsj.mvp.XFragment;
import com.qnsdndhsd.dfjsdmwendsj.net.ApiSubscriber;
import com.qnsdndhsd.dfjsdmwendsj.net.NetError;
import com.qnsdndhsd.dfjsdmwendsj.net.XApi;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhgongju.WeiXxyongHuaMyToast;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhgongju.OpenWeiXxyongHuaUtil;
import com.qnsdndhsd.dfjsdmwendsj.wxxyhgongju.PreferencesWeiXxyongHuaOpenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetWeiXxyongHuaFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.zcxy_ll)
    View zcxyLl;
    @BindView(R.id.ysxy_ll)
    View ysxy_ll;
    @BindView(R.id.yjfk_ll)
    View yjfk_ll;

    private ProductWeiXxyongHuaModel productWeiXxyongHuaModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemWeiXxyongHuaAdapter setItemAdapter;

    private RemindWeiXxyongHuaDialog dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferencesWeiXxyongHuaOpenUtil.getString("app_mail");
        phone = PreferencesWeiXxyongHuaOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        zcxyLl.setOnClickListener(v -> {
            webBundle = new Bundle();
            webBundle.putString("url", WeiXxyongHuaApi.ZCXY);
            webBundle.putString("biaoti", getResources().getString(R.string.yryvb));
            OpenWeiXxyongHuaUtil.getValue((XActivity) getActivity(), JumpH5WeiXxyongHuaActivity.class, webBundle);
        });
        ysxy_ll.setOnClickListener(v -> {
            webBundle = new Bundle();
            webBundle.putString("url", WeiXxyongHuaApi.YSXY);
            webBundle.putString("biaoti", getResources().getString(R.string.retert));
            OpenWeiXxyongHuaUtil.getValue((XActivity) getActivity(), JumpH5WeiXxyongHuaActivity.class, webBundle);
        });
        yjfk_ll.setOnClickListener(v -> {
            OpenWeiXxyongHuaUtil.getValue((XActivity) getActivity(), FeedbackWeiXxyongHuaActivity.class, null);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.wxxyh_fragment_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetModelWeiXxyongHua model3 = new SetModelWeiXxyongHua(R.drawable.zxcerghh, "关于我们");
        SetModelWeiXxyongHua model4 = new SetModelWeiXxyongHua(R.drawable.bgdeed, "个性化推荐");
        SetModelWeiXxyongHua model5 = new SetModelWeiXxyongHua(R.drawable.tererf, "联系客服");
        SetModelWeiXxyongHua model6 = new SetModelWeiXxyongHua(R.drawable.fgfsf, "注销账户");
        SetModelWeiXxyongHua model7 = new SetModelWeiXxyongHua(R.drawable.luyth, "退出登录");
        List<SetModelWeiXxyongHua> list = new ArrayList<>();
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapter = new SetItemWeiXxyongHuaAdapter(R.layout.wxxyh_adpater_set_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    OpenWeiXxyongHuaUtil.getValue((XActivity) getActivity(), AboutWeiXxyongHuaInfoActivity.class, null);
                    break;
                case 1:
                    dialog = new RemindWeiXxyongHuaDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindWeiXxyongHuaDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            WeiXxyongHuaMyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            WeiXxyongHuaMyToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    OpenWeiXxyongHuaUtil.getValue((XActivity) getActivity(), ZhuXiaoWeiXxyongHuaActivity.class, null);
                    break;
                case 4:
                    dialog = new RemindWeiXxyongHuaDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindWeiXxyongHuaDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesWeiXxyongHuaOpenUtil.saveString("phone", "");
                            OpenWeiXxyongHuaUtil.getValue((XActivity) getActivity(), DlWeiXxyongHuaActivity.class, null, true);
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
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        setList.setAdapter(setItemAdapter);
    }

    public void toWeb(ProductWeiXxyongHuaModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenWeiXxyongHuaUtil.getValue((XActivity) getActivity(), JumpH5WeiXxyongHuaActivity.class, bundle);
        }
    }

    public void getConfig() {
        WeiXxyongHuaApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseWeiXxyongHuaModel<ConfigWeiXxyongHuaEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseWeiXxyongHuaModel<ConfigWeiXxyongHuaEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferencesWeiXxyongHuaOpenUtil.saveString("app_mail", mailStr);
                                dialog = new RemindWeiXxyongHuaDialog(getActivity()).setCancelText("取消")
                                        .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                dialog.setOnButtonClickListener(new RemindWeiXxyongHuaDialog.OnButtonClickListener() {
                                    @Override
                                    public void onSureClicked() {
                                        clipData = ClipData.newPlainText(null, mailStr);
                                        clipboard.setPrimaryClip(clipData);
                                        WeiXxyongHuaMyToast.showShort("复制成功");
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

    public void productList() {
        mobileType = PreferencesWeiXxyongHuaOpenUtil.getInt("mobileType");
        phone = PreferencesWeiXxyongHuaOpenUtil.getString("phone");
        WeiXxyongHuaApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseWeiXxyongHuaModel<List<ProductWeiXxyongHuaModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenWeiXxyongHuaUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseWeiXxyongHuaModel<List<ProductWeiXxyongHuaModel>> baseModel) {
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productWeiXxyongHuaModel = baseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductWeiXxyongHuaModel model) {
        if (model == null) {
            return;
        }
        phone = PreferencesWeiXxyongHuaOpenUtil.getString("phone");
        WeiXxyongHuaApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseWeiXxyongHuaModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseWeiXxyongHuaModel baseModel) {
                        toWeb(model);
                    }
                });
    }
}
