package com.qwbasvsd.zmnxcmdsjsdk.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qwbasvsd.zmnxcmdsjsdk.R;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiactivity.AboutInfoActivityLeFenQiNews;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiactivity.DlLeFenQiNewsActivity;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiactivity.JumpLeFenQiNewsH5Activity;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiactivity.LeFenQiNewsFeedbackActivity;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiactivity.SetItemAdapterLeFenQiNews;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiactivity.ZhuXiaoActivityLeFenQiNews;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiapi.HttpLeFenQiNewsApi;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqimodel.BaseModelLeFenQiNews;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqimodel.LeFenQiNewsConfigEntity;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqimodel.ProductLeFenQiNewsModel;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqimodel.SetLeFenQiNewsModel;
import com.qwbasvsd.zmnxcmdsjsdk.mvp.XActivity;
import com.qwbasvsd.zmnxcmdsjsdk.mvp.XFragment;
import com.qwbasvsd.zmnxcmdsjsdk.net.ApiSubscriber;
import com.qwbasvsd.zmnxcmdsjsdk.net.NetError;
import com.qwbasvsd.zmnxcmdsjsdk.net.XApi;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.MyToastLeFenQiNews;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.OpenLeFenQiNewsUtil;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiutils.LeFenQiNewsPreferencesOpenUtil;
import com.qwbasvsd.zmnxcmdsjsdk.lefenqiweidgt.RemindDialogLeFenQiNews;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LeFenQiNewsSetFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.set_list_1)
    RecyclerView setList1;
    @BindView(R.id.edu_tv)
    TextView edu_tv;

    private ProductLeFenQiNewsModel productLeFenQiNewsModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterLeFenQiNews setItemAdapterLeFenQiNews, setItemAdapterLeFenQiNews1;

    private RemindDialogLeFenQiNews dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = LeFenQiNewsPreferencesOpenUtil.getString("app_mail");
        phone = LeFenQiNewsPreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_le_fen_qi_set;
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetLeFenQiNewsModel model = new SetLeFenQiNewsModel(R.drawable.kksrtgzdfh, "注册协议");
        SetLeFenQiNewsModel model1 = new SetLeFenQiNewsModel(R.drawable.dsertgxdfh, "隐私协议");
        SetLeFenQiNewsModel model2 = new SetLeFenQiNewsModel(R.drawable.lrthsetdf, "意见反馈");
        SetLeFenQiNewsModel model3 = new SetLeFenQiNewsModel(R.drawable.zgegdh, "关于我们");
        SetLeFenQiNewsModel model4 = new SetLeFenQiNewsModel(R.drawable.mertgdh, "个性化推荐");
        SetLeFenQiNewsModel model5 = new SetLeFenQiNewsModel(R.drawable.wwetgfdzfh, "投诉邮箱");
        SetLeFenQiNewsModel model6 = new SetLeFenQiNewsModel(R.drawable.jjaergtdfh, "注销账户");
        SetLeFenQiNewsModel model7 = new SetLeFenQiNewsModel(R.drawable.ltyhfgxj, "退出登录");
        List<SetLeFenQiNewsModel> list = new ArrayList<>();
        List<SetLeFenQiNewsModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list1.add(model3);
        list1.add(model4);
        list1.add(model5);
        list1.add(model6);
        list1.add(model7);
        setItemAdapterLeFenQiNews = new SetItemAdapterLeFenQiNews(R.layout.adpater_set_item_1, list);
        setItemAdapterLeFenQiNews.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpLeFenQiNewsApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenLeFenQiNewsUtil.getValue((XActivity) getActivity(), JumpLeFenQiNewsH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpLeFenQiNewsApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenLeFenQiNewsUtil.getValue((XActivity) getActivity(), JumpLeFenQiNewsH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenLeFenQiNewsUtil.getValue((XActivity) getActivity(), LeFenQiNewsFeedbackActivity.class, null);
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemAdapterLeFenQiNews);
        setItemAdapterLeFenQiNews1 = new SetItemAdapterLeFenQiNews(R.layout.adpater_le_fen_qi_set_item, list1);
        setItemAdapterLeFenQiNews1.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    OpenLeFenQiNewsUtil.getValue((XActivity) getActivity(), AboutInfoActivityLeFenQiNews.class, null);
                    break;
                case 1:
                    dialog = new RemindDialogLeFenQiNews(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialogLeFenQiNews.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToastLeFenQiNews.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToastLeFenQiNews.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 2:
                    getConfig();
                    break;
                case 3:
                    OpenLeFenQiNewsUtil.getValue((XActivity) getActivity(), ZhuXiaoActivityLeFenQiNews.class, null);
                    break;
                case 4:
                    dialog = new RemindDialogLeFenQiNews(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindDialogLeFenQiNews.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            LeFenQiNewsPreferencesOpenUtil.saveString("phone", "");
                            OpenLeFenQiNewsUtil.getValue((XActivity) getActivity(), DlLeFenQiNewsActivity.class, null, true);
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
        setList1.setAdapter(setItemAdapterLeFenQiNews1);
    }

    public void getConfig() {
            HttpLeFenQiNewsApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeFenQiNews<LeFenQiNewsConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseModelLeFenQiNews<LeFenQiNewsConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    LeFenQiNewsPreferencesOpenUtil.saveString("app_mail", mailStr);
                                    dialog = new RemindDialogLeFenQiNews(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
    }

    public void toWeb(ProductLeFenQiNewsModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenLeFenQiNewsUtil.getValue((XActivity) getActivity(), JumpLeFenQiNewsH5Activity.class, bundle);
        }
    }

    public void productList() {
            mobileType = LeFenQiNewsPreferencesOpenUtil.getInt("mobileType");
            phone = LeFenQiNewsPreferencesOpenUtil.getString("phone");
            HttpLeFenQiNewsApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeFenQiNews<List<ProductLeFenQiNewsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenLeFenQiNewsUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseModelLeFenQiNews<List<ProductLeFenQiNewsModel>> baseModelLeFenQiNews) {
                            if (baseModelLeFenQiNews != null) {
                                if (baseModelLeFenQiNews.getCode() == 200 && baseModelLeFenQiNews.getData() != null) {
                                    if (baseModelLeFenQiNews.getData() != null && baseModelLeFenQiNews.getData().size() > 0) {
                                        productLeFenQiNewsModel = baseModelLeFenQiNews.getData().get(0);
                                        edu_tv.setText(productLeFenQiNewsModel.getMinAmount() + "-" + productLeFenQiNewsModel.getMaxAmount());
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductLeFenQiNewsModel model) {
            if (model == null) {
                return;
            }
            phone = LeFenQiNewsPreferencesOpenUtil.getString("phone");
            HttpLeFenQiNewsApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelLeFenQiNews>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseModelLeFenQiNews baseModelLeFenQiNews) {
                            toWeb(model);
                        }
                    });
    }
}
