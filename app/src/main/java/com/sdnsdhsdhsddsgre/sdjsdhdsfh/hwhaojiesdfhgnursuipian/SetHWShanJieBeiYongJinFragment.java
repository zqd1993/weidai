package com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnursuipian;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdnsdhsdhsddsgre.sdjsdhdsfh.R;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurkongjian.RemindHWShanJieBeiYongJinDialog;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnuryemian.JumpH5HWShanJieBeiYongJinActivity;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.mvp.XActivity;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurshiti.ConfigHWShanJieBeiYongJinEntity;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnuryemian.AboutHWShanJieBeiYongJinInfoActivity;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnuryemian.DlHWShanJieBeiYongJinActivity;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnuryemian.FeedbackHWShanJieBeiYongJinActivity;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnuryemian.SetItemHWShanJieBeiYongJinAdapter;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnuryemian.ZhuXiaoHWShanJieBeiYongJinActivity;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurapi.HWShanJieBeiYongJinApi;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurshiti.BaseHWShanJieBeiYongJinModel;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurshiti.ProductHWShanJieBeiYongJinModel;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurshiti.SetModelHWShanJieBeiYongJin;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.mvp.XFragment;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.net.ApiSubscriber;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.net.NetError;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.net.XApi;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurgongju.HWShanJieBeiYongJinMyToast;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurgongju.OpenHWShanJieBeiYongJinUtil;
import com.sdnsdhsdhsddsgre.sdjsdhdsfh.hwhaojiesdfhgnurgongju.PreferenceHWShanJieBeiYongJinOpenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetHWShanJieBeiYongJinFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.click_view)
    View click_view;

    private ProductHWShanJieBeiYongJinModel productHWShanJieBeiYongJinModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemHWShanJieBeiYongJinAdapter setItemAdapter;

    private RemindHWShanJieBeiYongJinDialog dialog;

    private String mailStr = "";

    private ClipboardManager clipboard;

    private ClipData clipData;

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = PreferenceHWShanJieBeiYongJinOpenUtil.getString("app_mail");
        phone = PreferenceHWShanJieBeiYongJinOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        initSetAdapter();
        click_view.setOnClickListener(v -> {
            productClick(productHWShanJieBeiYongJinModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.hw_shan_jie_bei_yong_jie_fragment_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetModelHWShanJieBeiYongJin model1 = new SetModelHWShanJieBeiYongJin(R.drawable.klfyupaeryrt, "注册协议");
        SetModelHWShanJieBeiYongJin model2 = new SetModelHWShanJieBeiYongJin(R.drawable.zzrysrtu, "隐私协议");
        SetModelHWShanJieBeiYongJin model4 = new SetModelHWShanJieBeiYongJin(R.drawable.dtyksrjxfg, "意见反馈");
        SetModelHWShanJieBeiYongJin model3 = new SetModelHWShanJieBeiYongJin(R.drawable.werhdfgj, "关于我们");
        SetModelHWShanJieBeiYongJin model5 = new SetModelHWShanJieBeiYongJin(R.drawable.rsrtuyxfgj, "联系客服");
        SetModelHWShanJieBeiYongJin model6 = new SetModelHWShanJieBeiYongJin(R.drawable.zzcfhaeru, "注销账户");
        SetModelHWShanJieBeiYongJin model7 = new SetModelHWShanJieBeiYongJin(R.drawable.xxfghsrtu, "退出登录");
        List<SetModelHWShanJieBeiYongJin> list = new ArrayList<>();
        list.add(model1);
        list.add(model2);
        list.add(model4);
        list.add(model3);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapter = new SetItemHWShanJieBeiYongJinAdapter(R.layout.hw_shan_jie_bei_yong_jie_adpater_set_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HWShanJieBeiYongJinApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.yryvb));
                    OpenHWShanJieBeiYongJinUtil.getValue((XActivity) getActivity(), JumpH5HWShanJieBeiYongJinActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HWShanJieBeiYongJinApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.retert));
                    OpenHWShanJieBeiYongJinUtil.getValue((XActivity) getActivity(), JumpH5HWShanJieBeiYongJinActivity.class, webBundle);
                    break;
                case 2:
                    OpenHWShanJieBeiYongJinUtil.getValue((XActivity) getActivity(), FeedbackHWShanJieBeiYongJinActivity.class, null);
                    break;
                case 3:
                    OpenHWShanJieBeiYongJinUtil.getValue((XActivity) getActivity(), AboutHWShanJieBeiYongJinInfoActivity.class, null);
                    break;
                case 4:
                    getConfig();
                    break;
                case 5:
                    OpenHWShanJieBeiYongJinUtil.getValue((XActivity) getActivity(), ZhuXiaoHWShanJieBeiYongJinActivity.class, null);
                    break;
                case 6:
                    dialog = new RemindHWShanJieBeiYongJinDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindHWShanJieBeiYongJinDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferenceHWShanJieBeiYongJinOpenUtil.saveString("phone", "");
                            OpenHWShanJieBeiYongJinUtil.getValue((XActivity) getActivity(), DlHWShanJieBeiYongJinActivity.class, null, true);
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

    public void toWeb(ProductHWShanJieBeiYongJinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenHWShanJieBeiYongJinUtil.getValue((XActivity) getActivity(), JumpH5HWShanJieBeiYongJinActivity.class, bundle);
        }
    }

    public void getConfig() {
        HWShanJieBeiYongJinApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHWShanJieBeiYongJinModel<ConfigHWShanJieBeiYongJinEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseHWShanJieBeiYongJinModel<ConfigHWShanJieBeiYongJinEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferenceHWShanJieBeiYongJinOpenUtil.saveString("app_mail", mailStr);
                                dialog = new RemindHWShanJieBeiYongJinDialog(getActivity()).setCancelText("取消")
                                        .setConfirmText("复制").setTitle("温馨提示").setContent(mailStr);
                                dialog.setOnButtonClickListener(new RemindHWShanJieBeiYongJinDialog.OnButtonClickListener() {
                                    @Override
                                    public void onSureClicked() {
                                        clipData = ClipData.newPlainText(null, mailStr);
                                        clipboard.setPrimaryClip(clipData);
                                        HWShanJieBeiYongJinMyToast.showShort("复制成功");
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
        mobileType = PreferenceHWShanJieBeiYongJinOpenUtil.getInt("mobileType");
        phone = PreferenceHWShanJieBeiYongJinOpenUtil.getString("phone");
        HWShanJieBeiYongJinApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHWShanJieBeiYongJinModel<List<ProductHWShanJieBeiYongJinModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenHWShanJieBeiYongJinUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseHWShanJieBeiYongJinModel<List<ProductHWShanJieBeiYongJinModel>> baseModel) {
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productHWShanJieBeiYongJinModel = baseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductHWShanJieBeiYongJinModel model) {
        if (model == null) {
            return;
        }
        phone = PreferenceHWShanJieBeiYongJinOpenUtil.getString("phone");
        HWShanJieBeiYongJinApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseHWShanJieBeiYongJinModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseHWShanJieBeiYongJinModel baseModel) {
                        toWeb(model);
                    }
                });
    }
}
