package com.jryqhdgreyey.bzdrghsrtuy.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jryqhdgreyey.bzdrghsrtuy.R;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaactivity.AboutInfoActivityJinRiYouQianHua;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaactivity.DlJinRiYouQianHuaActivity;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaactivity.JinRiYouQianHuaFeedbackActivity;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaactivity.JumpH5ActivityJinRiYouQianHua;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaactivity.JinRiYouQianHuaSetItemAdapter;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaactivity.JinRiYouQianHuaZhuXiaoActivity;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaapi.JinRiYouQianHuaHttpApi;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.JinRiYouQianHuaBaseModel;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.JinRiYouQianHuaConfigEntity;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.JinRiYouQianHuaProductModel;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuamodel.JinRiYouQianHuaSetModel;
import com.jryqhdgreyey.bzdrghsrtuy.mvp.XActivity;
import com.jryqhdgreyey.bzdrghsrtuy.mvp.XFragment;
import com.jryqhdgreyey.bzdrghsrtuy.net.ApiSubscriber;
import com.jryqhdgreyey.bzdrghsrtuy.net.NetError;
import com.jryqhdgreyey.bzdrghsrtuy.net.XApi;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuautils.JinRiYouQianHuaMyToast;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuautils.OpenUtilJinRiYouQianHua;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuautils.PreferencesJinRiYouQianHuaOpenUtil;
import com.jryqhdgreyey.bzdrghsrtuy.jinriyouqianhuaweidgt.RemindJinRiYouQianHuaDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFragmentJinRiYouQianHua extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private JinRiYouQianHuaProductModel jinRiYouQianHuaProductModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private JinRiYouQianHuaSetItemAdapter jinRiYouQianHuaSetItemAdapter;

    private RemindJinRiYouQianHuaDialog dialog;

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
        mailStr = PreferencesJinRiYouQianHuaOpenUtil.getString("app_mail");
        phone = PreferencesJinRiYouQianHuaOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jin_ri_you_qian_hua_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        JinRiYouQianHuaSetModel model = new JinRiYouQianHuaSetModel(R.drawable.jkherghdfh, "注册协议");
        JinRiYouQianHuaSetModel model1 = new JinRiYouQianHuaSetModel(R.drawable.vcbxfghrt, "隐私协议");
        JinRiYouQianHuaSetModel model2 = new JinRiYouQianHuaSetModel(R.drawable.asdfster, "意见反馈");
        JinRiYouQianHuaSetModel model3 = new JinRiYouQianHuaSetModel(R.drawable.lytuery, "关于我们");
        JinRiYouQianHuaSetModel model4 = new JinRiYouQianHuaSetModel(R.drawable.ltyujdrtu, "个性化推荐");
        JinRiYouQianHuaSetModel model5 = new JinRiYouQianHuaSetModel(R.drawable.luiorujdstru, "投诉邮箱");
        JinRiYouQianHuaSetModel model6 = new JinRiYouQianHuaSetModel(R.drawable.nndghgh, "注销账户");
        JinRiYouQianHuaSetModel model7 = new JinRiYouQianHuaSetModel(R.drawable.werzhyery, "退出登录");
        List<JinRiYouQianHuaSetModel> list = new ArrayList<>();
        List<JinRiYouQianHuaSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        jinRiYouQianHuaSetItemAdapter = new JinRiYouQianHuaSetItemAdapter(R.layout.adpater_set_item_jin_ri_you_qian_hua, list);
        jinRiYouQianHuaSetItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", JinRiYouQianHuaHttpApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenUtilJinRiYouQianHua.getValue((XActivity) getActivity(), JumpH5ActivityJinRiYouQianHua.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", JinRiYouQianHuaHttpApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenUtilJinRiYouQianHua.getValue((XActivity) getActivity(), JumpH5ActivityJinRiYouQianHua.class, webBundle);
                    break;
                case 2:
                    OpenUtilJinRiYouQianHua.getValue((XActivity) getActivity(), JinRiYouQianHuaFeedbackActivity.class, null);
                    break;
                case 3:
                    OpenUtilJinRiYouQianHua.getValue((XActivity) getActivity(), AboutInfoActivityJinRiYouQianHua.class, null);
                    break;
                case 4:
                    dialog = new RemindJinRiYouQianHuaDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindJinRiYouQianHuaDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            JinRiYouQianHuaMyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            JinRiYouQianHuaMyToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpenUtilJinRiYouQianHua.getValue((XActivity) getActivity(), JinRiYouQianHuaZhuXiaoActivity.class, null);
                    break;
                case 7:
                    dialog = new RemindJinRiYouQianHuaDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindJinRiYouQianHuaDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesJinRiYouQianHuaOpenUtil.saveString("phone", "");
                            OpenUtilJinRiYouQianHua.getValue((XActivity) getActivity(), DlJinRiYouQianHuaActivity.class, null, true);
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
        setList.setAdapter(jinRiYouQianHuaSetItemAdapter);
    }

    public void getConfig() {
        JinRiYouQianHuaHttpApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<JinRiYouQianHuaBaseModel<JinRiYouQianHuaConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(JinRiYouQianHuaBaseModel<JinRiYouQianHuaConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferencesJinRiYouQianHuaOpenUtil.saveString("app_mail", mailStr);
                                dialog = new RemindJinRiYouQianHuaDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
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

    public void toWeb(JinRiYouQianHuaProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenUtilJinRiYouQianHua.getValue((XActivity) getActivity(), JumpH5ActivityJinRiYouQianHua.class, bundle);
        }
    }

    public void productList() {
        mobileType = PreferencesJinRiYouQianHuaOpenUtil.getInt("mobileType");
        phone = PreferencesJinRiYouQianHuaOpenUtil.getString("phone");
        JinRiYouQianHuaHttpApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<JinRiYouQianHuaBaseModel<List<JinRiYouQianHuaProductModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenUtilJinRiYouQianHua.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(JinRiYouQianHuaBaseModel<List<JinRiYouQianHuaProductModel>> jinRiYouQianHuaBaseModel) {
                        if (jinRiYouQianHuaBaseModel != null) {
                            if (jinRiYouQianHuaBaseModel.getCode() == 200 && jinRiYouQianHuaBaseModel.getData() != null) {
                                if (jinRiYouQianHuaBaseModel.getData() != null && jinRiYouQianHuaBaseModel.getData().size() > 0) {
                                    jinRiYouQianHuaProductModel = jinRiYouQianHuaBaseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(JinRiYouQianHuaProductModel model) {
        if (model == null) {
            return;
        }
        phone = PreferencesJinRiYouQianHuaOpenUtil.getString("phone");
        JinRiYouQianHuaHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<JinRiYouQianHuaBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(JinRiYouQianHuaBaseModel jinRiYouQianHuaBaseModel) {
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
