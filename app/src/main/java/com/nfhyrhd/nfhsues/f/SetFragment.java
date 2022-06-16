package com.nfhyrhd.nfhsues.f;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nfhyrhd.nfhsues.R;
import com.nfhyrhd.nfhsues.a.AboutInfoActivity;
import com.nfhyrhd.nfhsues.a.DlActivity;
import com.nfhyrhd.nfhsues.a.FeedbackActivity;
import com.nfhyrhd.nfhsues.a.JumpH5Activity;
import com.nfhyrhd.nfhsues.a.SetItemAdapter;
import com.nfhyrhd.nfhsues.a.ZhuXiaoActivity;
import com.nfhyrhd.nfhsues.api.HttpApi;
import com.nfhyrhd.nfhsues.m.BaseModel;
import com.nfhyrhd.nfhsues.m.ProductModel;
import com.nfhyrhd.nfhsues.m.SetModel;
import com.nfhyrhd.nfhsues.mvp.XFragment;
import com.nfhyrhd.nfhsues.net.ApiSubscriber;
import com.nfhyrhd.nfhsues.net.NetError;
import com.nfhyrhd.nfhsues.net.XApi;
import com.nfhyrhd.nfhsues.u.MyToast;
import com.nfhyrhd.nfhsues.u.OpenUtil;
import com.nfhyrhd.nfhsues.u.PreferencesOpenUtil;
import com.nfhyrhd.nfhsues.w.RemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.set_list_1)
    RecyclerView setList1;
    @BindView(R.id.banner_sl)
    View bannerSl;

    private ProductModel productModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapter setItemAdapter, setItemAdapter1;

    private RemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesOpenUtil.getString("app_mail");
        phone = PreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetModel model = new SetModel(R.drawable.mqet, "注册协议");
        SetModel model1 = new SetModel(R.drawable.jhguyt, "隐私协议");
        SetModel model2 = new SetModel(R.drawable.xvrf, "意见反馈");
        SetModel model3 = new SetModel(R.drawable.hhywe, "关于我们");
        SetModel model4 = new SetModel(R.drawable.wwtrrt, "个性化推荐");
        SetModel model5 = new SetModel(R.drawable.rryuj, "投诉邮箱");
        SetModel model6 = new SetModel(R.drawable.gtsvc, "注销账户");
        SetModel model7 = new SetModel(R.drawable.nngut, "退出登录");
        List<SetModel> list = new ArrayList<>();
        List<SetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list1.add(model4);
        list1.add(model5);
        list1.add(model6);
        list1.add(model7);
        setItemAdapter = new SetItemAdapter(R.layout.adpater_set_item, list);
        setItemAdapter1 = new SetItemAdapter(R.layout.adpater_set_item, list1);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenUtil.jumpPage(getActivity(), JumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenUtil.jumpPage(getActivity(), JumpH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenUtil.jumpPage(getActivity(), FeedbackActivity.class);
                    break;
                case 3:
                    OpenUtil.jumpPage(getActivity(), AboutInfoActivity.class);
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        setList.setAdapter(setItemAdapter);
        setItemAdapter1.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    dialog = new RemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 1:
                    dialog = new RemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                    dialog.show();
                    break;
                case 2:
                    OpenUtil.jumpPage(getActivity(), ZhuXiaoActivity.class);
                    break;
                case 3:
                    dialog = new RemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtil.saveString("phone", "");
                            OpenUtil.jumpPage(getActivity(), DlActivity.class);
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
        setList1.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        setList1.setAdapter(setItemAdapter1);
        bannerSl.setOnClickListener(v -> {
            productClick(productModel);
        });
    }

    public void toWeb(ProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenUtil.jumpPage(getActivity(), JumpH5Activity.class, bundle);
        }
    }

    public void productList() {
        mobileType = PreferencesOpenUtil.getInt("mobileType");
        HttpApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<List<ProductModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseModel<List<ProductModel>> baseModel) {
                        if (baseModel != null) {
                            if (baseModel.getCode() == 200 && baseModel.getData() != null) {
                                if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                    productModel = baseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductModel model) {
        if (model == null) {
            return;
        }
        phone = PreferencesOpenUtil.getString("phone");
        HttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        toWeb(model);
                    }
                });
    }
}
