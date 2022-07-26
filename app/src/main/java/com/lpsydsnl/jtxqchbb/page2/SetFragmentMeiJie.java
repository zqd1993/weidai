package com.lpsydsnl.jtxqchbb.page2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lpsydsnl.jtxqchbb.R;
import com.lpsydsnl.jtxqchbb.mvp.XActivity;
import com.lpsydsnl.jtxqchbb.page.AboutInfoActivityMeiJie;
import com.lpsydsnl.jtxqchbb.page.DlMeiJieActivity;
import com.lpsydsnl.jtxqchbb.page.MeiJieFeedbackActivity;
import com.lpsydsnl.jtxqchbb.page.MeiJieJumpH5Activity;
import com.lpsydsnl.jtxqchbb.page.SetItemMeiJieAdapter;
import com.lpsydsnl.jtxqchbb.page.ZhuXiaoActivityMeiJie;
import com.lpsydsnl.jtxqchbb.net.HttpMeiJieApi;
import com.lpsydsnl.jtxqchbb.model.MeiJieBaseModel;
import com.lpsydsnl.jtxqchbb.model.ConfigMeiJieEntity;
import com.lpsydsnl.jtxqchbb.model.ProductMeiJieModel;
import com.lpsydsnl.jtxqchbb.model.SetMeiJieModel;
import com.lpsydsnl.jtxqchbb.mvp.XFragment;
import com.lpsydsnl.jtxqchbb.net.ApiSubscriber;
import com.lpsydsnl.jtxqchbb.net.NetError;
import com.lpsydsnl.jtxqchbb.net.XApi;
import com.lpsydsnl.jtxqchbb.use.MyToastMeiJie;
import com.lpsydsnl.jtxqchbb.use.OpenMeiJieUtil;
import com.lpsydsnl.jtxqchbb.use.MeiJiePreferencesOpenUtil;
import com.lpsydsnl.jtxqchbb.view.MeiJieRemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFragmentMeiJie extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.set_list_1)
    RecyclerView setList1;

    private ProductMeiJieModel productMeiJieModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemMeiJieAdapter setItemMeiJieAdapter, setItemMeiJieAdapter1;

    private MeiJieRemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = MeiJiePreferencesOpenUtil.getString("app_mail");
        phone = MeiJiePreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set_meijie;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetMeiJieModel model = new SetMeiJieModel(R.drawable.liudfj, "注册协议");
        SetMeiJieModel model1 = new SetMeiJieModel(R.drawable.liudfj, "隐私协议");
        SetMeiJieModel model2 = new SetMeiJieModel(R.drawable.liudfj, "意见反馈");
        SetMeiJieModel model3 = new SetMeiJieModel(R.drawable.liudfj, "关于我们");
        SetMeiJieModel model4 = new SetMeiJieModel(R.drawable.qwedsc, "个性化推荐");
        SetMeiJieModel model5 = new SetMeiJieModel(R.drawable.zdvrt4, "投诉邮箱");
        SetMeiJieModel model6 = new SetMeiJieModel(R.drawable.jgfb, "注销账户");
        SetMeiJieModel model7 = new SetMeiJieModel(R.drawable.vdfa, "退出登录");
        List<SetMeiJieModel> list = new ArrayList<>();
        List<SetMeiJieModel> list1 = new ArrayList<>();
        list1.add(model);
        list1.add(model1);
        list1.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemMeiJieAdapter1 = new SetItemMeiJieAdapter(R.layout.adpater_new_set_meijie, list1);
        setItemMeiJieAdapter1.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpMeiJieApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenMeiJieUtil.getValue((XActivity) getActivity(), MeiJieJumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpMeiJieApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenMeiJieUtil.getValue((XActivity) getActivity(), MeiJieJumpH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenMeiJieUtil.getValue((XActivity) getActivity(), MeiJieFeedbackActivity.class, null);
                    break;
            }
        });
        setList1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList1.setAdapter(setItemMeiJieAdapter1);
        setItemMeiJieAdapter = new SetItemMeiJieAdapter(R.layout.adpater_set_item_meijie, list);
        setItemMeiJieAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    OpenMeiJieUtil.getValue((XActivity) getActivity(), AboutInfoActivityMeiJie.class, null);
                    break;
                case 1:
                    dialog = new MeiJieRemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new MeiJieRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToastMeiJie.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToastMeiJie.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    OpenMeiJieUtil.getValue((XActivity) getActivity(), ZhuXiaoActivityMeiJie.class, null);
                    break;
                case 4:
                    dialog = new MeiJieRemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new MeiJieRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            MeiJiePreferencesOpenUtil.saveString("phone", "");
                            OpenMeiJieUtil.getValue((XActivity) getActivity(), DlMeiJieActivity.class, null, true);
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
        setList.setAdapter(setItemMeiJieAdapter);
    }

    public void toWeb(ProductMeiJieModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenMeiJieUtil.getValue((XActivity) getActivity(), MeiJieJumpH5Activity.class, bundle);
        }
    }

    public void productList() {
        mobileType = MeiJiePreferencesOpenUtil.getInt("mobileType");
        HttpMeiJieApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<MeiJieBaseModel<List<ProductMeiJieModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenMeiJieUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(MeiJieBaseModel<List<ProductMeiJieModel>> meiJieBaseModel) {
                        if (meiJieBaseModel != null) {
                            if (meiJieBaseModel.getCode() == 200 && meiJieBaseModel.getData() != null) {
                                if (meiJieBaseModel.getData() != null && meiJieBaseModel.getData().size() > 0) {
                                    productMeiJieModel = meiJieBaseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductMeiJieModel model) {
        if (model == null) {
            return;
        }
        phone = MeiJiePreferencesOpenUtil.getString("phone");
        HttpMeiJieApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<MeiJieBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(MeiJieBaseModel meiJieBaseModel) {
                        toWeb(model);
                    }
                });
    }

    public void getConfig() {
        HttpMeiJieApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<MeiJieBaseModel<ConfigMeiJieEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(MeiJieBaseModel<ConfigMeiJieEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                MeiJiePreferencesOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
                                dialog = new MeiJieRemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                dialog.show();
                            }
                        }
                    }
                });
    }
}
