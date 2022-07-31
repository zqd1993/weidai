package com.nadryeary.msftusertyu.wxxyhsuipian;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nadryeary.msftusertyu.R;
import com.nadryeary.msftusertyu.wxxyhkongjian.RemindWeiXxyongHuaDialog;
import com.nadryeary.msftusertyu.mvp.XActivity;
import com.nadryeary.msftusertyu.wxxyhshiti.ConfigWeiXxyongHuaEntity;
import com.nadryeary.msftusertyu.wxxyhyemian.AboutWeiXxyongHuaInfoActivity;
import com.nadryeary.msftusertyu.wxxyhyemian.DlWeiXxyongHuaActivity;
import com.nadryeary.msftusertyu.wxxyhyemian.FeedbackWeiXxyongHuaActivity;
import com.nadryeary.msftusertyu.wxxyhyemian.JumpH5WeiXxyongHuaActivity;
import com.nadryeary.msftusertyu.wxxyhyemian.SetItemWeiXxyongHuaAdapter;
import com.nadryeary.msftusertyu.wxxyhyemian.ZhuXiaoWeiXxyongHuaActivity;
import com.nadryeary.msftusertyu.wxxyhjiekou.WeiXxyongHuaApi;
import com.nadryeary.msftusertyu.wxxyhshiti.BaseWeiXxyongHuaModel;
import com.nadryeary.msftusertyu.wxxyhshiti.ProductWeiXxyongHuaModel;
import com.nadryeary.msftusertyu.wxxyhshiti.SetModelWeiXxyongHua;
import com.nadryeary.msftusertyu.mvp.XFragment;
import com.nadryeary.msftusertyu.net.ApiSubscriber;
import com.nadryeary.msftusertyu.net.NetError;
import com.nadryeary.msftusertyu.net.XApi;
import com.nadryeary.msftusertyu.wxxyhgongju.WeiXxyongHuaMyToast;
import com.nadryeary.msftusertyu.wxxyhgongju.OpenWeiXxyongHuaUtil;
import com.nadryeary.msftusertyu.wxxyhgongju.PreferencesWeiXxyongHuaOpenUtil;

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

    @Override
    public void initData(Bundle savedInstanceState) {
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
                                dialog = new RemindWeiXxyongHuaDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                dialog.show();
                            }
                        }
                    }
                });
    }

    public void productList() {
        mobileType = PreferencesWeiXxyongHuaOpenUtil.getInt("mobileType");
        WeiXxyongHuaApi.getInterfaceUtils().productList(mobileType)
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
