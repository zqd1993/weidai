package com.xiangfencqiasfew.ertaehrstyu.xiangfenqifragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiangfencqiasfew.ertaehrstyu.R;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity.AboutInfoActivityXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity.XiangFenQiDlActivity;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity.FeedbackXiangFenQiActivity;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity.XiangFenQiJumpH5Activity;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity.SetItemAdapterXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiactivity.ZhuXiaoXiangFenQiActivity;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiapi.XiangFenQiHttpApi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.BaseModelXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.ConfigXiangFenQiEntity;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.ProductModelXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqimodel.SetXiangFenQiModel;
import com.xiangfencqiasfew.ertaehrstyu.mvp.XActivity;
import com.xiangfencqiasfew.ertaehrstyu.mvp.XFragment;
import com.xiangfencqiasfew.ertaehrstyu.net.ApiSubscriber;
import com.xiangfencqiasfew.ertaehrstyu.net.NetError;
import com.xiangfencqiasfew.ertaehrstyu.net.XApi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.MyXiangFenQiToast;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.OpenXiangFenQiUtil;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiutils.PreferencesOpenUtilXiangFenQi;
import com.xiangfencqiasfew.ertaehrstyu.xiangfenqiweidgt.RemindXiangFenQiDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetXiangFenQiFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.set_list_1)
    RecyclerView setList1;

    private ProductModelXiangFenQi productModelXiangFenQi;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterXiangFenQi setItemAdapterXiangFenQi, setItemAdapterXiangFenQi1;

    private RemindXiangFenQiDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesOpenUtilXiangFenQi.getString("app_mail");
        phone = PreferencesOpenUtilXiangFenQi.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_xiang_fen_qi_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetXiangFenQiModel model = new SetXiangFenQiModel(R.drawable.irtieysteaty, "注册协议");
        SetXiangFenQiModel model1 = new SetXiangFenQiModel(R.drawable.xcxcbsrty, "隐私协议");
        SetXiangFenQiModel model2 = new SetXiangFenQiModel(R.drawable.kkryraeyr, "意见反馈");
        SetXiangFenQiModel model3 = new SetXiangFenQiModel(R.drawable.tysrta, "关于我们");
        SetXiangFenQiModel model4 = new SetXiangFenQiModel(R.drawable.nzdryhsery, "个性化推荐");
        SetXiangFenQiModel model5 = new SetXiangFenQiModel(R.drawable.ftsery, "投诉邮箱");
        SetXiangFenQiModel model6 = new SetXiangFenQiModel(R.drawable.cxvbsaery, "注销账户");
        SetXiangFenQiModel model7 = new SetXiangFenQiModel(R.drawable.vnxvsrtaryu, "退出登录");
        List<SetXiangFenQiModel> list = new ArrayList<>();
        List<SetXiangFenQiModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list1.add(model4);
        list1.add(model5);
        list1.add(model6);
        list1.add(model7);
        setItemAdapterXiangFenQi = new SetItemAdapterXiangFenQi(R.layout.adpater_set_item_xiang_fen_qi, list);
        setItemAdapterXiangFenQi.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", XiangFenQiHttpApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenXiangFenQiUtil.getValue((XActivity) getActivity(), XiangFenQiJumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", XiangFenQiHttpApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenXiangFenQiUtil.getValue((XActivity) getActivity(), XiangFenQiJumpH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenXiangFenQiUtil.getValue((XActivity) getActivity(), FeedbackXiangFenQiActivity.class, null);
                    break;
                case 3:
                    OpenXiangFenQiUtil.getValue((XActivity) getActivity(), AboutInfoActivityXiangFenQi.class, null);
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        setList.setAdapter(setItemAdapterXiangFenQi);
        setItemAdapterXiangFenQi1 = new SetItemAdapterXiangFenQi(R.layout.adpater_set_item_1_xiang_fen_qi, list1);
        setItemAdapterXiangFenQi1.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    dialog = new RemindXiangFenQiDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindXiangFenQiDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyXiangFenQiToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyXiangFenQiToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 1:
                    getConfig();
                    break;
                case 2:
                    OpenXiangFenQiUtil.getValue((XActivity) getActivity(), ZhuXiaoXiangFenQiActivity.class, null);
                    break;
                case 3:
                    dialog = new RemindXiangFenQiDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindXiangFenQiDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesOpenUtilXiangFenQi.saveString("phone", "");
                            OpenXiangFenQiUtil.getValue((XActivity) getActivity(), XiangFenQiDlActivity.class, null, true);
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
        setList1.setAdapter(setItemAdapterXiangFenQi1);
    }

    public void getConfig() {
        XiangFenQiHttpApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi<ConfigXiangFenQiEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi<ConfigXiangFenQiEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferencesOpenUtilXiangFenQi.saveString("app_mail", mailStr);
                                dialog = new RemindXiangFenQiDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                dialog.show();
                            }
                        }
                    }
                });
    }

    public void toWeb(ProductModelXiangFenQi model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenXiangFenQiUtil.getValue((XActivity) getActivity(), XiangFenQiJumpH5Activity.class, bundle);
        }
    }

    public void productList() {
        mobileType = PreferencesOpenUtilXiangFenQi.getInt("mobileType");
        phone = PreferencesOpenUtilXiangFenQi.getString("phone");
        XiangFenQiHttpApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi<List<ProductModelXiangFenQi>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenXiangFenQiUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi<List<ProductModelXiangFenQi>> baseModelXiangFenQi) {
                        if (baseModelXiangFenQi != null) {
                            if (baseModelXiangFenQi.getCode() == 200 && baseModelXiangFenQi.getData() != null) {
                                if (baseModelXiangFenQi.getData() != null && baseModelXiangFenQi.getData().size() > 0) {
                                    productModelXiangFenQi = baseModelXiangFenQi.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductModelXiangFenQi model) {
        if (model == null) {
            return;
        }
        phone = PreferencesOpenUtilXiangFenQi.getString("phone");
        XiangFenQiHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModelXiangFenQi>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseModelXiangFenQi baseModelXiangFenQi) {
                        toWeb(model);
                    }
                });
    }
}
