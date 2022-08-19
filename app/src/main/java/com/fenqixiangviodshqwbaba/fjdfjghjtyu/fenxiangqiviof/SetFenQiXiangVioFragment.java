package com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviof;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fenqixiangviodshqwbaba.fjdfjghjtyu.R;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa.AboutInfoActivityFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa.DlFenQiXiangVioActivity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa.FeedbackFenQiXiangVioActivity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa.JumpH5FenQiXiangVioActivity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa.FenQiXiangVioSetItemAdapter;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioa.FenQiXiangVioZhuXiaoActivity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqivioapi.HttpApiFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioBaseModel;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioConfigEntity;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.ProductModelFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviom.FenQiXiangVioSetModel;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.mvp.XFragment;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.ApiSubscriber;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.NetError;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.net.XApi;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.MyFenQiXiangVioToast;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.FenQiXiangVioOpenUtil;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviou.PreferencesOpenUtilFenQiXiangVio;
import com.fenqixiangviodshqwbaba.fjdfjghjtyu.fenxiangqiviow.FenQiXiangVioRemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFenQiXiangVioFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductModelFenQiXiangVio productModelFenQiXiangVio;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private FenQiXiangVioSetItemAdapter fenQiXiangVioSetItemAdapter;

    private FenQiXiangVioRemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesOpenUtilFenQiXiangVio.getString("app_mail");
        phone = PreferencesOpenUtilFenQiXiangVio.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fen_xiang_qi_vio_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        FenQiXiangVioSetModel model = new FenQiXiangVioSetModel(R.drawable.jsrtyxfh, "注册协议");
        FenQiXiangVioSetModel model1 = new FenQiXiangVioSetModel(R.drawable.ewrtdzhgsz, "隐私协议");
        FenQiXiangVioSetModel model2 = new FenQiXiangVioSetModel(R.drawable.bbsrtydrtu, "意见反馈");
        FenQiXiangVioSetModel model3 = new FenQiXiangVioSetModel(R.drawable.koytdjs, "关于我们");
        FenQiXiangVioSetModel model4 = new FenQiXiangVioSetModel(R.drawable.wretgxdryh, "个性化推荐");
        FenQiXiangVioSetModel model5 = new FenQiXiangVioSetModel(R.drawable.rtyrthjxj, "投诉邮箱");
        FenQiXiangVioSetModel model6 = new FenQiXiangVioSetModel(R.drawable.ergxfhsrt, "注销账户");
        FenQiXiangVioSetModel model7 = new FenQiXiangVioSetModel(R.drawable.xcbsrts, "退出登录");
        List<FenQiXiangVioSetModel> list = new ArrayList<>();
        List<FenQiXiangVioSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        fenQiXiangVioSetItemAdapter = new FenQiXiangVioSetItemAdapter(R.layout.adpater_fen_xiang_qi_vio_set_item, list);
        fenQiXiangVioSetItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiFenQiXiangVio.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    FenQiXiangVioOpenUtil.jumpPage(getActivity(), JumpH5FenQiXiangVioActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiFenQiXiangVio.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    FenQiXiangVioOpenUtil.jumpPage(getActivity(), JumpH5FenQiXiangVioActivity.class, webBundle);
                    break;
                case 2:
                    FenQiXiangVioOpenUtil.jumpPage(getActivity(), FeedbackFenQiXiangVioActivity.class);
                    break;
                case 3:
                    FenQiXiangVioOpenUtil.jumpPage(getActivity(), AboutInfoActivityFenQiXiangVio.class);
                    break;
                case 4:
                    dialog = new FenQiXiangVioRemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new FenQiXiangVioRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyFenQiXiangVioToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyFenQiXiangVioToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    FenQiXiangVioOpenUtil.jumpPage(getActivity(), FenQiXiangVioZhuXiaoActivity.class);
                    break;
                case 7:
                    dialog = new FenQiXiangVioRemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new FenQiXiangVioRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilFenQiXiangVio.saveString("phone", "");
                            FenQiXiangVioOpenUtil.jumpPage(getActivity(), DlFenQiXiangVioActivity.class);
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
        setList.setLayoutManager(new LinearLayoutManager(getActivity()));
        setList.setAdapter(fenQiXiangVioSetItemAdapter);
    }

    public void getConfig() {
            HttpApiFenQiXiangVio.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(FenQiXiangVioBaseModel<FenQiXiangVioConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilFenQiXiangVio.saveString("app_mail", mailStr);
                                    dialog = new FenQiXiangVioRemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
    }

    public void toWeb(ProductModelFenQiXiangVio model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            FenQiXiangVioOpenUtil.jumpPage(getActivity(), JumpH5FenQiXiangVioActivity.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesOpenUtilFenQiXiangVio.getInt("mobileType");
            phone = PreferencesOpenUtilFenQiXiangVio.getString("phone");
            HttpApiFenQiXiangVio.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel<List<ProductModelFenQiXiangVio>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            FenQiXiangVioOpenUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(FenQiXiangVioBaseModel<List<ProductModelFenQiXiangVio>> fenQiXiangVioBaseModel) {
                            if (fenQiXiangVioBaseModel != null) {
                                if (fenQiXiangVioBaseModel.getCode() == 200 && fenQiXiangVioBaseModel.getData() != null) {
                                    if (fenQiXiangVioBaseModel.getData() != null && fenQiXiangVioBaseModel.getData().size() > 0) {
                                        productModelFenQiXiangVio = fenQiXiangVioBaseModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductModelFenQiXiangVio model) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilFenQiXiangVio.getString("phone");
            HttpApiFenQiXiangVio.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiXiangVioBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(FenQiXiangVioBaseModel fenQiXiangVioBaseModel) {
                            toWeb(model);
                        }
                    });
    }
}
