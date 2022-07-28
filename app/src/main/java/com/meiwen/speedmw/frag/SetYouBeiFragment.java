package com.meiwen.speedmw.frag;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meiwen.speedmw.R;
import com.meiwen.speedmw.chajian.YouBeiRemindDialog;
import com.meiwen.speedmw.moxing.ConfigYouBeiEntity;
import com.meiwen.speedmw.mvp.XActivity;
import com.meiwen.speedmw.yemian.AboutInfoYouBeiActivity;
import com.meiwen.speedmw.yemian.DlYouBeiActivity;
import com.meiwen.speedmw.yemian.FeedbackYouBeiActivity;
import com.meiwen.speedmw.yemian.JumpH5YouBeiActivity;
import com.meiwen.speedmw.yemian.SetItemYouBeiAdapter;
import com.meiwen.speedmw.yemian.ZhuXiaoYouBeiActivity;
import com.meiwen.speedmw.jiekou.HttpYouBeiApi;
import com.meiwen.speedmw.moxing.BaseYouBeiModel;
import com.meiwen.speedmw.moxing.ProductYouBeiModel;
import com.meiwen.speedmw.moxing.SetYouBeiModel;
import com.meiwen.speedmw.mvp.XFragment;
import com.meiwen.speedmw.net.ApiSubscriber;
import com.meiwen.speedmw.net.NetError;
import com.meiwen.speedmw.net.XApi;
import com.meiwen.speedmw.gongju.MyYouBeiToast;
import com.meiwen.speedmw.gongju.OpenYouBeiUtil;
import com.meiwen.speedmw.gongju.PreferencesYouBeiOpenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetYouBeiFragment extends XFragment {

    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.label_iv)
    ImageView labelIv;

    private ProductYouBeiModel productYouBeiModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemYouBeiAdapter setItemAdapter;

    private YouBeiRemindDialog dialog;

    private String mailStr = "";

    // 密钥算法
    private static final String KEY_ALGORITHM = "AES";
    // AES/CBC/PKCS7Padding 分别对应 加密||解密算法、工作模式、填充方式
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    // 定义自己的秘钥
    public final static String SECRETKEY = "A9e4/vnQTrKF6otAGbM6zGsulKEL7b3x";
    // 位移量
    public final static String DISPLACEMENT = "9mg+!7ed8b36*w`X";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesYouBeiOpenUtil.getString("app_mail");
        phone = PreferencesYouBeiOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        labelIv.setOnClickListener(v -> {
            productClick(productYouBeiModel);
        });
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
        SetYouBeiModel model = new SetYouBeiModel(R.drawable.sdsda, "注册协议");
        SetYouBeiModel model1 = new SetYouBeiModel(R.drawable.bbfdr, "隐私协议");
        SetYouBeiModel model2 = new SetYouBeiModel(R.drawable.vxdvd, "意见反馈");
        SetYouBeiModel model3 = new SetYouBeiModel(R.drawable.erfdsv, "关于我们");
        SetYouBeiModel model4 = new SetYouBeiModel(R.drawable.mmghjy, "个性化推荐");
        SetYouBeiModel model5 = new SetYouBeiModel(R.drawable.hhhtg, "投诉邮箱");
        SetYouBeiModel model6 = new SetYouBeiModel(R.drawable.aaqwr, "注销账户");
        SetYouBeiModel model7 = new SetYouBeiModel(R.drawable.sdddfg, "退出登录");
        List<SetYouBeiModel> list = new ArrayList<>();
        List<SetYouBeiModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapter = new SetItemYouBeiAdapter(R.layout.adpater_set_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpYouBeiApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenYouBeiUtil.getValue((XActivity) getActivity(), JumpH5YouBeiActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpYouBeiApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenYouBeiUtil.getValue((XActivity) getActivity(), JumpH5YouBeiActivity.class, webBundle);
                    break;
                case 2:
                    OpenYouBeiUtil.getValue((XActivity) getActivity(), FeedbackYouBeiActivity.class, null);
                    break;
                case 3:
                    OpenYouBeiUtil.getValue((XActivity) getActivity(), AboutInfoYouBeiActivity.class, null);
                    break;
                case 4:
                    dialog = new YouBeiRemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new YouBeiRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyYouBeiToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyYouBeiToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpenYouBeiUtil.getValue((XActivity) getActivity(), ZhuXiaoYouBeiActivity.class, null);
                    break;
                case 7:
                    dialog = new YouBeiRemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new YouBeiRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesYouBeiOpenUtil.saveString("phone", "");
                            OpenYouBeiUtil.getValue((XActivity) getActivity(), DlYouBeiActivity.class, null, true);
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

    public void toWeb(ProductYouBeiModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenYouBeiUtil.getValue((XActivity) getActivity(), JumpH5YouBeiActivity.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesYouBeiOpenUtil.getInt("mobileType");
            HttpYouBeiApi.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseYouBeiModel<List<ProductYouBeiModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenYouBeiUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseYouBeiModel<List<ProductYouBeiModel>> baseYouBeiModel) {
                            if (baseYouBeiModel != null) {
                                if (baseYouBeiModel.getCode() == 200 && baseYouBeiModel.getData() != null) {
                                    if (baseYouBeiModel.getData() != null && baseYouBeiModel.getData().size() > 0) {
                                        productYouBeiModel = baseYouBeiModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductYouBeiModel model) {
            if (model == null) {
                return;
            }
            phone = PreferencesYouBeiOpenUtil.getString("phone");
            HttpYouBeiApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseYouBeiModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseYouBeiModel baseYouBeiModel) {
                            toWeb(model);
                        }
                    });
    }

    public void getConfig() {
            HttpYouBeiApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseYouBeiModel<ConfigYouBeiEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseYouBeiModel<ConfigYouBeiEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesYouBeiOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
                                    dialog = new YouBeiRemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
    }
}
