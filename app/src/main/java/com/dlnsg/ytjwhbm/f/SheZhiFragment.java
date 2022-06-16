package com.dlnsg.ytjwhbm.f;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dlnsg.ytjwhbm.R;
import com.dlnsg.ytjwhbm.a.AppMsgActivity;
import com.dlnsg.ytjwhbm.a.DengLuActivity;
import com.dlnsg.ytjwhbm.a.WangYeActivity;
import com.dlnsg.ytjwhbm.a.ConfigItemAdapter;
import com.dlnsg.ytjwhbm.a.ZXActivity;
import com.dlnsg.ytjwhbm.api.MyApi;
import com.dlnsg.ytjwhbm.m.MainModel;
import com.dlnsg.ytjwhbm.m.ShangPinModel;
import com.dlnsg.ytjwhbm.m.SheZhiModel;
import com.dlnsg.ytjwhbm.mvp.XFragment;
import com.dlnsg.ytjwhbm.net.ApiSubscriber;
import com.dlnsg.ytjwhbm.net.NetError;
import com.dlnsg.ytjwhbm.net.XApi;
import com.dlnsg.ytjwhbm.u.BaseToast;
import com.dlnsg.ytjwhbm.u.BaseUtil;
import com.dlnsg.ytjwhbm.u.PreferencesStaticOpenUtil;
import com.dlnsg.ytjwhbm.w.TshiDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SheZhiFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.wd_bg)
    View wdBg;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.logout_tv)
    TextView logoutTv;

    private ShangPinModel shangPinModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private ConfigItemAdapter setItemAdapter;

    private TshiDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesStaticOpenUtil.getString("app_mail");
        phone = PreferencesStaticOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        logoutTv.setOnClickListener(v -> {
            dialog = new TshiDialog(getActivity()).setCancelText("取消")
                    .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录？");
            dialog.setOnButtonClickListener(new TshiDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    PreferencesStaticOpenUtil.saveString("phone", "");
                    BaseUtil.jumpPage(getActivity(), DengLuActivity.class);
                    getActivity().finish();
                }

                @Override
                public void onCancelClicked() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
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
        SheZhiModel model = new SheZhiModel(R.drawable.xcgdfg, "注册协议");
        SheZhiModel model1 = new SheZhiModel(R.drawable.cbrd, "隐私协议");
//        SetModel model2 = new SetModel(R.drawable.s, "意见反馈");
        SheZhiModel model3 = new SheZhiModel(R.drawable.xvdfhg, "关于我们");
        SheZhiModel model4 = new SheZhiModel(R.drawable.zxfsdt, "个性化推荐");
        SheZhiModel model5 = new SheZhiModel(R.drawable.rterfx, "投诉邮箱");
        SheZhiModel model6 = new SheZhiModel(R.drawable.qweas, "注销账户");
//        SetModel model7 = new SetModel(R.drawable.p, "退出登录");
        List<SheZhiModel> list = new ArrayList<>();
        list.add(model);
        list.add(model1);
//        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
//        list.add(model7);
        setItemAdapter = new ConfigItemAdapter(R.layout.adpater_set_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", MyApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    BaseUtil.jumpPage(getActivity(), WangYeActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", MyApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    BaseUtil.jumpPage(getActivity(), WangYeActivity.class, webBundle);
                    break;
//                case 2:
//                    OpenUtil.jumpPage(getActivity(), FeedbackActivity.class);
//                    break;
                case 2:
                    BaseUtil.jumpPage(getActivity(), AppMsgActivity.class);
                    break;
                case 3:
                    dialog = new TshiDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new TshiDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            BaseToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            BaseToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 4:
                    dialog = new TshiDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                    dialog.show();
                    break;
                case 5:
                    BaseUtil.jumpPage(getActivity(), ZXActivity.class);
                    break;
//                case 5:
//                    dialog = new TshiDialog(getActivity()).setCancelText("取消")
//                            .setConfirmText("注销").setTitle("温馨提示").setContent("确定注销并退出当前登录");
//                    dialog.setOnButtonClickListener(new TshiDialog.OnButtonClickListener() {
//                        @Override
//                        public void onSureClicked() {
//                            dialog.dismiss();
//                            PreferencesStaticOpenUtil.saveString("phone", "");
//                            BaseUtil.jumpPage(getActivity(), DengLuActivity.class);
//                            getActivity().finish();
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
        setList.setAdapter(setItemAdapter);
    }

    public void productList() {
        mobileType = PreferencesStaticOpenUtil.getInt("mobileType");
        MyApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<MainModel<List<ShangPinModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        BaseUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(MainModel<List<ShangPinModel>> mainModel) {
                        if (mainModel != null) {
                            if (mainModel.getCode() == 200 && mainModel.getData() != null) {
                                if (mainModel.getData() != null && mainModel.getData().size() > 0) {
                                    shangPinModel = mainModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }
}
