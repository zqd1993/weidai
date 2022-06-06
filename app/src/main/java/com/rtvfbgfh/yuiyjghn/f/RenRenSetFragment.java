package com.rtvfbgfh.yuiyjghn.f;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtvfbgfh.yuiyjghn.R;
import com.rtvfbgfh.yuiyjghn.a.InfoAboutActivity;
import com.rtvfbgfh.yuiyjghn.a.ShowOneDlActivity;
import com.rtvfbgfh.yuiyjghn.a.RenRenFeedbackActivity;
import com.rtvfbgfh.yuiyjghn.a.JumpH5Activity;
import com.rtvfbgfh.yuiyjghn.a.RenRenSetItemAdapter;
import com.rtvfbgfh.yuiyjghn.a.RenRenDestroyActivity;
import com.rtvfbgfh.yuiyjghn.api.NewApi;
import com.rtvfbgfh.yuiyjghn.m.RenRenBaseModel;
import com.rtvfbgfh.yuiyjghn.m.RenRenGoodsModel;
import com.rtvfbgfh.yuiyjghn.m.RenRenMineModel;
import com.rtvfbgfh.yuiyjghn.mvp.XFragment;
import com.rtvfbgfh.yuiyjghn.net.ApiSubscriber;
import com.rtvfbgfh.yuiyjghn.net.NetError;
import com.rtvfbgfh.yuiyjghn.net.XApi;
import com.rtvfbgfh.yuiyjghn.u.NewToast;
import com.rtvfbgfh.yuiyjghn.u.OpenMethodUtil;
import com.rtvfbgfh.yuiyjghn.u.SPOpenUtil;
import com.rtvfbgfh.yuiyjghn.w.RemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RenRenSetFragment extends XFragment implements View.OnClickListener {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.wd_bg)
    View wdBg;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.top_img)
    ImageView topImg;

    private RenRenGoodsModel renRenGoodsModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private RenRenSetItemAdapter renRenSetItemAdapter;

    private RemindDialog dialog;

    private String mailStr = "";

    private static int getNotchSizeXiaoMiHeight(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            int resourceId = activity.getResources().getIdentifier("notch_height", "dimen", "android");
            if (resourceId > 0) {
                return activity.getResources().getDimensionPixelSize(resourceId);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                WindowInsets windowInsets = null;
                DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //竖屏
                    return displayCutout.getSafeInsetTop();
                } else {
                    return displayCutout.getSafeInsetLeft();
                }
            } catch (Exception e) {

            }
        }
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = SPOpenUtil.getString("app_mail");
        phone = SPOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        parentView.setOnClickListener(this);
        userPhoneTv.setOnClickListener(this);
        wdBg.setOnClickListener(this);
        topImg.setOnClickListener(v -> {
            if (renRenGoodsModel != null) {
                productClick(renRenGoodsModel);
            }
        });
    }

    public String formatInteger(int num) {
        if (num < 0) {
            num = 0;
        }
        if (num == 0) {
            return "零";
        }
        char[] val = String.valueOf(num).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String m = val[i] + "";
            int n = Integer.valueOf(m);
            boolean isZero = n == 0;
            String unit = "";
            if (isZero) {
                if ('0' == val[i]) {
                    continue;
                } else {
//                    sb.append(numArray[n]);
                }
            } else {
//                sb.append(numArray[n]);
                sb.append(unit);
            }
        }
        return sb.toString();
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_setting;
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

    public String formatDecimal(double decimal) {
        String decimals = String.valueOf(decimal);
        int decIndex = decimals.indexOf(".");
        int integ = Integer.valueOf(decimals.substring(0, decIndex));
        int dec = Integer.valueOf(decimals.substring(decIndex + 1));
        String result = formatInteger(integ) + ".";
        return result;
    }

    private void initSetAdapter() {
        RenRenMineModel model = new RenRenMineModel(R.drawable.gfht, "注册协议");
        RenRenMineModel model1 = new RenRenMineModel(R.drawable.xcv5, "隐私协议");
        RenRenMineModel model2 = new RenRenMineModel(R.drawable.rt45, "意见反馈");
        RenRenMineModel model3 = new RenRenMineModel(R.drawable.hyt, "关于我们");
        RenRenMineModel model4 = new RenRenMineModel(R.drawable.cxvdf, "个性化推荐");
        RenRenMineModel model5 = new RenRenMineModel(R.drawable.rtegfc, "投诉邮箱");
        RenRenMineModel model6 = new RenRenMineModel(R.drawable.tyuter, "注销账户");
        RenRenMineModel model7 = new RenRenMineModel(R.drawable.fgh, "退出登录");
        List<RenRenMineModel> list = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        renRenSetItemAdapter = new RenRenSetItemAdapter(R.layout.adpater_setting_mine, list);
        renRenSetItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", NewApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenMethodUtil.jumpPage(getActivity(), JumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", NewApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenMethodUtil.jumpPage(getActivity(), JumpH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenMethodUtil.jumpPage(getActivity(), RenRenFeedbackActivity.class);
                    break;
                case 3:
                    OpenMethodUtil.jumpPage(getActivity(), InfoAboutActivity.class);
                    break;
                case 4:
                    dialog = new RemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            NewToast.showShort("开启成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            NewToast.showShort("关闭成功");
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
                    OpenMethodUtil.jumpPage(getActivity(), RenRenDestroyActivity.class);
                    break;
                case 7:
                    dialog = new RemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            SPOpenUtil.saveString("phone", "");
                            OpenMethodUtil.jumpPage(getActivity(), ShowOneDlActivity.class);
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
        setList.setAdapter(renRenSetItemAdapter);
    }

    public void toWeb(RenRenGoodsModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenMethodUtil.jumpPage(getActivity(), JumpH5Activity.class, bundle);
        }
    }

    public String formatFractionalPart(int decimal) {
        char[] val = String.valueOf(decimal).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int n = Integer.valueOf(val[i] + "");
//            sb.append(numArray[n]);
        }
        return sb.toString();
    }

    public void productList() {
        mobileType = SPOpenUtil.getInt("mobileType");
        NewApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<RenRenBaseModel<List<RenRenGoodsModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenMethodUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(RenRenBaseModel<List<RenRenGoodsModel>> renRenBaseModel) {
                        if (renRenBaseModel != null) {
                            if (renRenBaseModel.getCode() == 200 && renRenBaseModel.getData() != null) {
                                if (renRenBaseModel.getData() != null && renRenBaseModel.getData().size() > 0) {
                                    renRenGoodsModel = renRenBaseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public static boolean isOpenVersion10NewStore() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public void productClick(RenRenGoodsModel model) {
        phone = SPOpenUtil.getString("phone");
        NewApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<RenRenBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(RenRenBaseModel renRenBaseModel) {
                        toWeb(model);
                    }
                });
    }
}
