package com.xvhyrt.ghjtyu.fra;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.act.WoMenActivity;
import com.xvhyrt.ghjtyu.act.DengGeLuActivity;
import com.xvhyrt.ghjtyu.act.HuiFangActivity;
import com.xvhyrt.ghjtyu.act.JumpH5Activity;
import com.xvhyrt.ghjtyu.act.SheZhiLieBiaoAdapter;
import com.xvhyrt.ghjtyu.act.ZhangHaoActivity;
import com.xvhyrt.ghjtyu.net.WangLuoApi;
import com.xvhyrt.ghjtyu.bean.ParentModel;
import com.xvhyrt.ghjtyu.bean.ChanPinModel;
import com.xvhyrt.ghjtyu.bean.SheZhiModel;
import com.xvhyrt.ghjtyu.mvp.XFragment;
import com.xvhyrt.ghjtyu.net.ApiSubscriber;
import com.xvhyrt.ghjtyu.net.NetError;
import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.uti.TiShi;
import com.xvhyrt.ghjtyu.uti.GongJuLei;
import com.xvhyrt.ghjtyu.uti.SPFile;
import com.xvhyrt.ghjtyu.wei.TiShiDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SheZhiFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.title_iv)
    View titleIv;

    private ChanPinModel chanPinModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SheZhiLieBiaoAdapter setItemAdapter;

    private TiShiDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = SPFile.getString("app_mail");
        phone = SPFile.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        titleIv.setOnClickListener(v -> {
            productClick(chanPinModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shezhi;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SheZhiModel model = new SheZhiModel(R.drawable.rfvnhu, "注册协议");
        SheZhiModel model1 = new SheZhiModel(R.drawable.vvbfg, "隐私协议");
        SheZhiModel model2 = new SheZhiModel(R.drawable.yhvbrf, "意见反馈");
        SheZhiModel model3 = new SheZhiModel(R.drawable.bghnj, "关于我们");
        SheZhiModel model4 = new SheZhiModel(R.drawable.yhgdv, "个性化推荐");
        SheZhiModel model5 = new SheZhiModel(R.drawable.kuvcbf, "投诉邮箱");
        SheZhiModel model6 = new SheZhiModel(R.drawable.kioluih, "注销账户");
        SheZhiModel model7 = new SheZhiModel(R.drawable.xcvfgtert, "退出登录");
        List<SheZhiModel> list = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapter = new SheZhiLieBiaoAdapter(R.layout.adpater_shezhi, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", WangLuoApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    GongJuLei.jumpPage(getActivity(), JumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", WangLuoApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    GongJuLei.jumpPage(getActivity(), JumpH5Activity.class, webBundle);
                    break;
                case 2:
                    GongJuLei.jumpPage(getActivity(), HuiFangActivity.class);
                    break;
                case 3:
                    GongJuLei.jumpPage(getActivity(), WoMenActivity.class);
                    break;
                case 4:
                    dialog = new TiShiDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new TiShiDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            TiShi.showShort("开启成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            TiShi.showShort("关闭成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    dialog = new TiShiDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                    dialog.show();
                    break;
                case 6:
                    GongJuLei.jumpPage(getActivity(), ZhangHaoActivity.class);
                    break;
                case 7:
                    dialog = new TiShiDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new TiShiDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            SPFile.saveString("phone", "");
                            GongJuLei.jumpPage(getActivity(), DengGeLuActivity.class);
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
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        setList.setAdapter(setItemAdapter);
    }

    public void toWeb(ChanPinModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            GongJuLei.jumpPage(getActivity(), JumpH5Activity.class, bundle);
        }
    }

    public void productList() {
        mobileType = SPFile.getInt("mobileType");
        WangLuoApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<ParentModel<List<ChanPinModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        GongJuLei.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(ParentModel<List<ChanPinModel>> parentModel) {
                        if (parentModel != null) {
                            if (parentModel.getCode() == 200 && parentModel.getData() != null) {
                                if (parentModel.getData() != null && parentModel.getData().size() > 0) {
                                    chanPinModel = parentModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ChanPinModel model) {
        if (model == null) {
            return;
        }
        phone = SPFile.getString("phone");
        WangLuoApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<ParentModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(ParentModel parentModel) {
                        toWeb(model);
                    }
                });
    }
}
