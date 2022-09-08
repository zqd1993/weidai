package com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newmazhaocaiewkfd.drngfs.R;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity.AboutInfoActivityZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity.ZhaoCaiAdfmDlActivity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity.FeedbackZhaoCaiAdfmActivity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity.ZhaoCaiAdfmJumpH5Activity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity.SetItemAdapterZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgactivity.ZhuXiaoXiangFenQiActivity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgapi.ZhaoCaiAdfmHttpApi;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.BaseModelZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.ConfigZhaoCaiAdfmEntity;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.ProductModelZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgmodel.SetZhaoCaiAdfmModel;
import com.newmazhaocaiewkfd.drngfs.mvp.XActivity;
import com.newmazhaocaiewkfd.drngfs.mvp.XFragment;
import com.newmazhaocaiewkfd.drngfs.net.ApiSubscriber;
import com.newmazhaocaiewkfd.drngfs.net.NetError;
import com.newmazhaocaiewkfd.drngfs.net.XApi;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.MyZhaoCaiAdfmToas;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.OpenZhaoCaiAdfmUtil;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgutils.PreferencesOpenUtilZhaoCaiAdfm;
import com.newmazhaocaiewkfd.drngfs.zhaocaiwedfgweidgt.RemindZhaoCaiAdfmDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetZhaoCaiAdfmFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.set_list_1)
    RecyclerView setList1;

    private ProductModelZhaoCaiAdfm productModelZhaoCaiAdfm;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterZhaoCaiAdfm setItemAdapterZhaoCaiAdfm, setItemAdapterZhaoCaiAdfm1;

    private RemindZhaoCaiAdfmDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesOpenUtilZhaoCaiAdfm.getString("app_mail");
        phone = PreferencesOpenUtilZhaoCaiAdfm.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhao_cai_endfi_weng_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetZhaoCaiAdfmModel model = new SetZhaoCaiAdfmModel(R.drawable.irtieysteaty, "注册协议");
        SetZhaoCaiAdfmModel model1 = new SetZhaoCaiAdfmModel(R.drawable.xcxcbsrty, "隐私协议");
        SetZhaoCaiAdfmModel model2 = new SetZhaoCaiAdfmModel(R.drawable.kkryraeyr, "意见反馈");
        SetZhaoCaiAdfmModel model3 = new SetZhaoCaiAdfmModel(R.drawable.tysrta, "关于我们");
        SetZhaoCaiAdfmModel model4 = new SetZhaoCaiAdfmModel(R.drawable.nzdryhsery, "个性化推荐");
        SetZhaoCaiAdfmModel model5 = new SetZhaoCaiAdfmModel(R.drawable.ftsery, "投诉邮箱");
        SetZhaoCaiAdfmModel model6 = new SetZhaoCaiAdfmModel(R.drawable.cxvbsaery, "注销账户");
        SetZhaoCaiAdfmModel model7 = new SetZhaoCaiAdfmModel(R.drawable.vnxvsrtaryu, "退出登录");
        List<SetZhaoCaiAdfmModel> list = new ArrayList<>();
        List<SetZhaoCaiAdfmModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list1.add(model4);
        list1.add(model5);
        list1.add(model6);
        list1.add(model7);
        setItemAdapterZhaoCaiAdfm = new SetItemAdapterZhaoCaiAdfm(R.layout.adpater_set_item_zhao_cai_endfi_weng, list);
        setItemAdapterZhaoCaiAdfm.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", ZhaoCaiAdfmHttpApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenZhaoCaiAdfmUtil.getValue((XActivity) getActivity(), ZhaoCaiAdfmJumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", ZhaoCaiAdfmHttpApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenZhaoCaiAdfmUtil.getValue((XActivity) getActivity(), ZhaoCaiAdfmJumpH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenZhaoCaiAdfmUtil.getValue((XActivity) getActivity(), FeedbackZhaoCaiAdfmActivity.class, null);
                    break;
                case 3:
                    OpenZhaoCaiAdfmUtil.getValue((XActivity) getActivity(), AboutInfoActivityZhaoCaiAdfm.class, null);
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        setList.setAdapter(setItemAdapterZhaoCaiAdfm);
        setItemAdapterZhaoCaiAdfm1 = new SetItemAdapterZhaoCaiAdfm(R.layout.adpater_set_item_1_zhao_cai_endfi_weng, list1);
        setItemAdapterZhaoCaiAdfm1.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    dialog = new RemindZhaoCaiAdfmDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindZhaoCaiAdfmDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyZhaoCaiAdfmToas.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyZhaoCaiAdfmToas.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 1:
                    getConfig();
                    break;
                case 2:
                    OpenZhaoCaiAdfmUtil.getValue((XActivity) getActivity(), ZhuXiaoXiangFenQiActivity.class, null);
                    break;
                case 3:
                    dialog = new RemindZhaoCaiAdfmDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindZhaoCaiAdfmDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilZhaoCaiAdfm.saveString("phone", "");
                            OpenZhaoCaiAdfmUtil.getValue((XActivity) getActivity(), ZhaoCaiAdfmDlActivity.class, null, true);
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
        setList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        setList1.setAdapter(setItemAdapterZhaoCaiAdfm1);
    }

    public void getConfig() {
        ZhaoCaiAdfmHttpApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelZhaoCaiAdfm<ConfigZhaoCaiAdfmEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseModelZhaoCaiAdfm<ConfigZhaoCaiAdfmEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferencesOpenUtilZhaoCaiAdfm.saveString("app_mail", mailStr);
                                dialog = new RemindZhaoCaiAdfmDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                dialog.show();
                            }
                        }
                    }
                });
    }

    public void toWeb(ProductModelZhaoCaiAdfm model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenZhaoCaiAdfmUtil.getValue((XActivity) getActivity(), ZhaoCaiAdfmJumpH5Activity.class, bundle);
        }
    }

    public void productList() {
        mobileType = PreferencesOpenUtilZhaoCaiAdfm.getInt("mobileType");
        phone = PreferencesOpenUtilZhaoCaiAdfm.getString("phone");
        ZhaoCaiAdfmHttpApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelZhaoCaiAdfm<List<ProductModelZhaoCaiAdfm>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenZhaoCaiAdfmUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseModelZhaoCaiAdfm<List<ProductModelZhaoCaiAdfm>> baseModelZhaoCaiAdfm) {
                        if (baseModelZhaoCaiAdfm != null) {
                            if (baseModelZhaoCaiAdfm.getCode() == 200 && baseModelZhaoCaiAdfm.getData() != null) {
                                if (baseModelZhaoCaiAdfm.getData() != null && baseModelZhaoCaiAdfm.getData().size() > 0) {
                                    productModelZhaoCaiAdfm = baseModelZhaoCaiAdfm.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductModelZhaoCaiAdfm model) {
        if (model == null) {
            return;
        }
        phone = PreferencesOpenUtilZhaoCaiAdfm.getString("phone");
        ZhaoCaiAdfmHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelZhaoCaiAdfm>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseModelZhaoCaiAdfm baseModelZhaoCaiAdfm) {
                        toWeb(model);
                    }
                });
    }
}
