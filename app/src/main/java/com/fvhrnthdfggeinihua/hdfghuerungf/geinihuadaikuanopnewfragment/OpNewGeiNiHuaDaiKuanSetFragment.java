package com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewfragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fvhrnthdfggeinihua.hdfghuerungf.R;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity.OpNewGeiNiHuaDaiKuanAboutInfoActivity;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity.OpNewGeiNiHuaDaiKuanDlActivity;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity.OpNewGeiNiHuaDaiKuanFeedbackActivity;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity.JumpH5ActivityJinRiYouQianHua;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity.OpNewGeiNiHuaDaiKuanSetItemAdapter;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewactivity.OpNewGeiNiHuaDaiKuanZhuXiaoActivity;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewaapi.OpNewGeiNiHuaDaiKuanHttpApi;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanBaseModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanConfigEntity;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanProductModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewmodel.OpNewGeiNiHuaDaiKuanSetModel;
import com.fvhrnthdfggeinihua.hdfghuerungf.mvp.XActivity;
import com.fvhrnthdfggeinihua.hdfghuerungf.mvp.XFragment;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.ApiSubscriber;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.NetError;
import com.fvhrnthdfggeinihua.hdfghuerungf.net.XApi;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanMyToast;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanOpenUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewutils.OpNewGeiNiHuaDaiKuanPreferencesOpenUtil;
import com.fvhrnthdfggeinihua.hdfghuerungf.geinihuadaikuanopnewweidgt.OpNewGeiNiHuaDaiKuanRemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OpNewGeiNiHuaDaiKuanSetFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private OpNewGeiNiHuaDaiKuanProductModel opNewGeiNiHuaDaiKuanProductModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private OpNewGeiNiHuaDaiKuanSetItemAdapter opNewGeiNiHuaDaiKuanSetItemAdapter;

    private OpNewGeiNiHuaDaiKuanRemindDialog dialog;

    private String mailStr = "";

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getString("app_mail");
        phone = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gei_ni_hua_dai_kuan_op_new_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        OpNewGeiNiHuaDaiKuanSetModel model = new OpNewGeiNiHuaDaiKuanSetModel(R.drawable.jkherghdfh, "注册协议");
        OpNewGeiNiHuaDaiKuanSetModel model1 = new OpNewGeiNiHuaDaiKuanSetModel(R.drawable.vcbxfghrt, "隐私协议");
        OpNewGeiNiHuaDaiKuanSetModel model2 = new OpNewGeiNiHuaDaiKuanSetModel(R.drawable.asdfster, "意见反馈");
        OpNewGeiNiHuaDaiKuanSetModel model3 = new OpNewGeiNiHuaDaiKuanSetModel(R.drawable.lytuery, "关于我们");
        OpNewGeiNiHuaDaiKuanSetModel model4 = new OpNewGeiNiHuaDaiKuanSetModel(R.drawable.ltyujdrtu, "个性化推荐");
        OpNewGeiNiHuaDaiKuanSetModel model5 = new OpNewGeiNiHuaDaiKuanSetModel(R.drawable.luiorujdstru, "投诉邮箱");
        OpNewGeiNiHuaDaiKuanSetModel model6 = new OpNewGeiNiHuaDaiKuanSetModel(R.drawable.nndghgh, "注销账户");
        OpNewGeiNiHuaDaiKuanSetModel model7 = new OpNewGeiNiHuaDaiKuanSetModel(R.drawable.werzhyery, "退出登录");
        List<OpNewGeiNiHuaDaiKuanSetModel> list = new ArrayList<>();
        List<OpNewGeiNiHuaDaiKuanSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        opNewGeiNiHuaDaiKuanSetItemAdapter = new OpNewGeiNiHuaDaiKuanSetItemAdapter(R.layout.adpater_set_item_gei_ni_hua_dai_kuan_op_new, list);
        opNewGeiNiHuaDaiKuanSetItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", OpNewGeiNiHuaDaiKuanHttpApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpNewGeiNiHuaDaiKuanOpenUtil.getValue((XActivity) getActivity(), JumpH5ActivityJinRiYouQianHua.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", OpNewGeiNiHuaDaiKuanHttpApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpNewGeiNiHuaDaiKuanOpenUtil.getValue((XActivity) getActivity(), JumpH5ActivityJinRiYouQianHua.class, webBundle);
                    break;
                case 2:
                    OpNewGeiNiHuaDaiKuanOpenUtil.getValue((XActivity) getActivity(), OpNewGeiNiHuaDaiKuanFeedbackActivity.class, null);
                    break;
                case 3:
                    OpNewGeiNiHuaDaiKuanOpenUtil.getValue((XActivity) getActivity(), OpNewGeiNiHuaDaiKuanAboutInfoActivity.class, null);
                    break;
                case 4:
                    dialog = new OpNewGeiNiHuaDaiKuanRemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new OpNewGeiNiHuaDaiKuanRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            OpNewGeiNiHuaDaiKuanMyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            OpNewGeiNiHuaDaiKuanMyToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpNewGeiNiHuaDaiKuanOpenUtil.getValue((XActivity) getActivity(), OpNewGeiNiHuaDaiKuanZhuXiaoActivity.class, null);
                    break;
                case 7:
                    dialog = new OpNewGeiNiHuaDaiKuanRemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new OpNewGeiNiHuaDaiKuanRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.saveString("phone", "");
                            OpNewGeiNiHuaDaiKuanOpenUtil.getValue((XActivity) getActivity(), OpNewGeiNiHuaDaiKuanDlActivity.class, null, true);
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
        setList.setAdapter(opNewGeiNiHuaDaiKuanSetItemAdapter);
    }

    public void getConfig() {
            OpNewGeiNiHuaDaiKuanHttpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(OpNewGeiNiHuaDaiKuanBaseModel<OpNewGeiNiHuaDaiKuanConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.saveString("app_mail", mailStr);
                                    dialog = new OpNewGeiNiHuaDaiKuanRemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap aertrsyrsj(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap hsrtyrtu(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    public void toWeb(OpNewGeiNiHuaDaiKuanProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpNewGeiNiHuaDaiKuanOpenUtil.getValue((XActivity) getActivity(), JumpH5ActivityJinRiYouQianHua.class, bundle);
        }
    }

    public void productList() {
            mobileType = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getInt("mobileType");
            phone = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getString("phone");
            OpNewGeiNiHuaDaiKuanHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<OpNewGeiNiHuaDaiKuanBaseModel<List<OpNewGeiNiHuaDaiKuanProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpNewGeiNiHuaDaiKuanOpenUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(OpNewGeiNiHuaDaiKuanBaseModel<List<OpNewGeiNiHuaDaiKuanProductModel>> opNewGeiNiHuaDaiKuanBaseModel) {
                            if (opNewGeiNiHuaDaiKuanBaseModel != null) {
                                if (opNewGeiNiHuaDaiKuanBaseModel.getCode() == 200 && opNewGeiNiHuaDaiKuanBaseModel.getData() != null) {
                                    if (opNewGeiNiHuaDaiKuanBaseModel.getData() != null && opNewGeiNiHuaDaiKuanBaseModel.getData().size() > 0) {
                                        opNewGeiNiHuaDaiKuanProductModel = opNewGeiNiHuaDaiKuanBaseModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(OpNewGeiNiHuaDaiKuanProductModel model) {
            if (model == null) {
                return;
            }
            phone = OpNewGeiNiHuaDaiKuanPreferencesOpenUtil.getString("phone");
            OpNewGeiNiHuaDaiKuanHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<OpNewGeiNiHuaDaiKuanBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(OpNewGeiNiHuaDaiKuanBaseModel opNewGeiNiHuaDaiKuanBaseModel) {
                            toWeb(model);
                        }
                    });
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap mrshfdhrsdtu(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap awetdgrstu(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = 500;
        int height = 500;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height);
        view.destroyDrawingCache();
        return bp;
    }
}
