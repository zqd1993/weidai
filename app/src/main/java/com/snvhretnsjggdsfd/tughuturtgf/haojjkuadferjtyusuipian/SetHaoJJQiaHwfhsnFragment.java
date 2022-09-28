package com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyusuipian;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snvhretnsjggdsfd.tughuturtgf.R;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyukongjian.RemindHaoJJQiaHwfhsnDialog;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian.JumpH5HaoJJQiaHwfhsnActivity;
import com.snvhretnsjggdsfd.tughuturtgf.mvp.XActivity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.ConfigHaoJJQiaHwfhsnEntity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian.AboutHaoJJQiaHwfhsnInfoActivity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian.DlHaoJJQiaHwfhsnActivity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian.FeedbackHaoJJQiaHwfhsnActivity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian.SetItemHaoJJQiaHwfhsnAdapter;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian.ZhuXiaoHaoJJQiaHwfhsnActivity;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuapi.HaoJJQiaHwfhsnApi;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.BaseHaoJJQiaHwfhsnModel;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.ProductHaoJJQiaHwfhsnModel;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.SetModelHaoJJQiaHwfhsn;
import com.snvhretnsjggdsfd.tughuturtgf.mvp.XFragment;
import com.snvhretnsjggdsfd.tughuturtgf.net.ApiSubscriber;
import com.snvhretnsjggdsfd.tughuturtgf.net.NetError;
import com.snvhretnsjggdsfd.tughuturtgf.net.XApi;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuu.HaoJJQiaHwfhsnMyToast;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuu.OpenHaoJJQiaHwfhsnUtil;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuu.PreferenceHaoJJQiaHwfhsnOpenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetHaoJJQiaHwfhsnFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.click_view)
    View click_view;

    private ProductHaoJJQiaHwfhsnModel productHaoJJQiaHwfhsnModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemHaoJJQiaHwfhsnAdapter setItemAdapter;

    private RemindHaoJJQiaHwfhsnDialog dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferenceHaoJJQiaHwfhsnOpenUtil.getString("app_mail");
        phone = PreferenceHaoJJQiaHwfhsnOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        initSetAdapter();
        click_view.setOnClickListener(v -> {
            productClick(productHaoJJQiaHwfhsnModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.hao_jjqian_dfjr_uert_hw_fragment_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetModelHaoJJQiaHwfhsn model1 = new SetModelHaoJJQiaHwfhsn(R.drawable.klfyupaeryrt, "注册协议");
        SetModelHaoJJQiaHwfhsn model2 = new SetModelHaoJJQiaHwfhsn(R.drawable.zzrysrtu, "隐私协议");
        SetModelHaoJJQiaHwfhsn model4 = new SetModelHaoJJQiaHwfhsn(R.drawable.dtyksrjxfg, "意见反馈");
        SetModelHaoJJQiaHwfhsn model3 = new SetModelHaoJJQiaHwfhsn(R.drawable.werhdfgj, "关于我们");
        SetModelHaoJJQiaHwfhsn model5 = new SetModelHaoJJQiaHwfhsn(R.drawable.rsrtuyxfgj, "联系客服");
        SetModelHaoJJQiaHwfhsn model6 = new SetModelHaoJJQiaHwfhsn(R.drawable.zzcfhaeru, "注销账户");
        SetModelHaoJJQiaHwfhsn model7 = new SetModelHaoJJQiaHwfhsn(R.drawable.xxfghsrtu, "退出登录");
        List<SetModelHaoJJQiaHwfhsn> list = new ArrayList<>();
        list.add(model1);
        list.add(model2);
        list.add(model4);
        list.add(model3);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapter = new SetItemHaoJJQiaHwfhsnAdapter(R.layout.hao_jjqian_dfjr_uert_hw_adpater_set_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HaoJJQiaHwfhsnApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.yryvb));
                    OpenHaoJJQiaHwfhsnUtil.getValue((XActivity) getActivity(), JumpH5HaoJJQiaHwfhsnActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HaoJJQiaHwfhsnApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.retert));
                    OpenHaoJJQiaHwfhsnUtil.getValue((XActivity) getActivity(), JumpH5HaoJJQiaHwfhsnActivity.class, webBundle);
                    break;
                case 2:
                    OpenHaoJJQiaHwfhsnUtil.getValue((XActivity) getActivity(), FeedbackHaoJJQiaHwfhsnActivity.class, null);
                    break;
                case 3:
                    OpenHaoJJQiaHwfhsnUtil.getValue((XActivity) getActivity(), AboutHaoJJQiaHwfhsnInfoActivity.class, null);
                    break;
                case 4:
                    getConfig();
                    break;
                case 5:
                    OpenHaoJJQiaHwfhsnUtil.getValue((XActivity) getActivity(), ZhuXiaoHaoJJQiaHwfhsnActivity.class, null);
                    break;
                case 6:
                    dialog = new RemindHaoJJQiaHwfhsnDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindHaoJJQiaHwfhsnDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferenceHaoJJQiaHwfhsnOpenUtil.saveString("phone", "");
                            OpenHaoJJQiaHwfhsnUtil.getValue((XActivity) getActivity(), DlHaoJJQiaHwfhsnActivity.class, null, true);
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
        setList.setAdapter(setItemAdapter);
    }

    public void toWeb(ProductHaoJJQiaHwfhsnModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenHaoJJQiaHwfhsnUtil.getValue((XActivity) getActivity(), JumpH5HaoJJQiaHwfhsnActivity.class, bundle);
        }
    }

    public void getConfig() {
        HaoJJQiaHwfhsnApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHaoJJQiaHwfhsnModel<ConfigHaoJJQiaHwfhsnEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseHaoJJQiaHwfhsnModel<ConfigHaoJJQiaHwfhsnEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferenceHaoJJQiaHwfhsnOpenUtil.saveString("app_mail", mailStr);
                                dialog = new RemindHaoJJQiaHwfhsnDialog(getActivity()).setCancelText("取消")
                                        .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                dialog.setOnButtonClickListener(new RemindHaoJJQiaHwfhsnDialog.OnButtonClickListener() {
                                    @Override
                                    public void onSureClicked() {
                                        clipData = ClipData.newPlainText(null, mailStr);
                                        clipboard.setPrimaryClip(clipData);
                                        HaoJJQiaHwfhsnMyToast.showShort("复制成功");
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
        mobileType = PreferenceHaoJJQiaHwfhsnOpenUtil.getInt("mobileType");
        phone = PreferenceHaoJJQiaHwfhsnOpenUtil.getString("phone");
        HaoJJQiaHwfhsnApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHaoJJQiaHwfhsnModel<List<ProductHaoJJQiaHwfhsnModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenHaoJJQiaHwfhsnUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseHaoJJQiaHwfhsnModel<List<ProductHaoJJQiaHwfhsnModel>> baseModel) {
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productHaoJJQiaHwfhsnModel = baseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductHaoJJQiaHwfhsnModel model) {
        if (model == null) {
            return;
        }
        phone = PreferenceHaoJJQiaHwfhsnOpenUtil.getString("phone");
        HaoJJQiaHwfhsnApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHaoJJQiaHwfhsnModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseHaoJJQiaHwfhsnModel baseModel) {
                        toWeb(model);
                    }
                });
    }
}
