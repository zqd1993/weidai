package com.xvhyrt.ghjtyu.f;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.a.AboutInfoActivity;
import com.xvhyrt.ghjtyu.a.DlActivity;
import com.xvhyrt.ghjtyu.a.FeedbackActivity;
import com.xvhyrt.ghjtyu.a.JumpH5Activity;
import com.xvhyrt.ghjtyu.a.SetItemAdapter;
import com.xvhyrt.ghjtyu.a.ZhuXiaoActivity;
import com.xvhyrt.ghjtyu.api.HttpApi;
import com.xvhyrt.ghjtyu.m.BaseModel;
import com.xvhyrt.ghjtyu.m.ProductModel;
import com.xvhyrt.ghjtyu.m.SetModel;
import com.xvhyrt.ghjtyu.mvp.XFragment;
import com.xvhyrt.ghjtyu.net.ApiSubscriber;
import com.xvhyrt.ghjtyu.net.NetError;
import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.u.MyToast;
import com.xvhyrt.ghjtyu.u.OpenUtil;
import com.xvhyrt.ghjtyu.u.PreferencesOpenUtil;
import com.xvhyrt.ghjtyu.w.RemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFragment extends XFragment implements View.OnClickListener {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.wd_bg)
    View wdBg;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductModel productModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapter setItemAdapter;

    private RemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesOpenUtil.getString("app_mail");
        phone = PreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        parentView.setOnClickListener(this);
        userPhoneTv.setOnClickListener(this);
        wdBg.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.parent_view:
            case R.id.user_phone_tv:
            case R.id.wd_bg:
//                if (productModel != null) {
//                    productClick(productModel);
//                }
                break;
        }
    }

    private void initSetAdapter() {
        SetModel model = new SetModel(R.drawable.gfht, "注册协议");
        SetModel model1 = new SetModel(R.drawable.xcv5, "隐私协议");
        SetModel model2 = new SetModel(R.drawable.rt45, "意见反馈");
        SetModel model3 = new SetModel(R.drawable.hyt, "关于我们");
        SetModel model4 = new SetModel(R.drawable.cxvdf, "个性化推荐");
        SetModel model5 = new SetModel(R.drawable.rtegfc, "投诉邮箱");
        SetModel model6 = new SetModel(R.drawable.tyuter, "注销账户");
        SetModel model7 = new SetModel(R.drawable.fgh, "退出登录");
        List<SetModel> list = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapter = new SetItemAdapter(R.layout.adpater_set_item, list);
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
                case 4:
                    dialog = new RemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToast.showShort("开启成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    dialog = new RemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                    dialog.show();
                    break;
                case 6:
                    OpenUtil.jumpPage(getActivity(), ZhuXiaoActivity.class);
                    break;
                case 7:
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
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemAdapter);
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
