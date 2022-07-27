package com.octone.pelicnenwo.f;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.octone.pelicnenwo.R;
import com.octone.pelicnenwo.huiminjiekuana.AboutInfoActivityHuiMinJieKuan;
import com.octone.pelicnenwo.huiminjiekuana.HuiMinJieKuanDlActivity;
import com.octone.pelicnenwo.huiminjiekuana.JumpH5HuiMinJieKuanActivity;
import com.octone.pelicnenwo.huiminjiekuana.SetItemAdapterHuiMinJieKuan;
import com.octone.pelicnenwo.huiminjiekuana.HuiMinJieKuanZhuXiaoActivity;
import com.octone.pelicnenwo.huiminjiekuanapi.HttpApiHuiMinJieKuan;
import com.octone.pelicnenwo.huiminjiekuanm.HuiMinJieKuanBaseModel;
import com.octone.pelicnenwo.huiminjiekuanm.HuiMinJieKuanConfigEntity;
import com.octone.pelicnenwo.huiminjiekuanm.HuiMinJieKuanProductModel;
import com.octone.pelicnenwo.huiminjiekuanm.SetModelHuiMinJieKuan;
import com.octone.pelicnenwo.mvp.XActivity;
import com.octone.pelicnenwo.mvp.XFragment;
import com.octone.pelicnenwo.net.ApiSubscriber;
import com.octone.pelicnenwo.net.NetError;
import com.octone.pelicnenwo.net.XApi;
import com.octone.pelicnenwo.huiminjiekuanu.HuiMinJieKuanMyToast;
import com.octone.pelicnenwo.huiminjiekuanu.OpenUtilHuiMinJieKuan;
import com.octone.pelicnenwo.huiminjiekuanu.HuiMinJieKuanPreferencesOpenUtil;
import com.octone.pelicnenwo.huiminjiekuanw.RemindHuiMinJieKuanDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetHuiMinJieKuanFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.mail_tv)
    TextView mail_tv;

    private HuiMinJieKuanProductModel huiMinJieKuanProductModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int srthxfghrdtu(Context context)
    {

        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap dxzfdxfhrtur(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 300;
        int height = 400;
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
    public Bitmap erfghdfhsrtu(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = 500;
        int height = 400;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    private String phone;

    private SetItemAdapterHuiMinJieKuan setItemAdapterHuiMinJieKuan;

    private RemindHuiMinJieKuanDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = HuiMinJieKuanPreferencesOpenUtil.getString("app_mail");
        mail_tv.setText(mailStr);
        phone = HuiMinJieKuanPreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        initSetAdapter();
        mail_tv.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            HuiMinJieKuanMyToast.showShort("复制成功");
        });
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int rtyusrturtu(Context context)
    {

        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap ssryrturtdu(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 300;
        int height = 400;
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
    public Bitmap zxrtysrty(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = 500;
        int height = 400;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    @Override
    public void onResume() {
        super.onResume();
        getConfig();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hui_min_jie_kuan_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetModelHuiMinJieKuan model = new SetModelHuiMinJieKuan(R.drawable.xrtutrjhxfg, "注册协议");
        SetModelHuiMinJieKuan model1 = new SetModelHuiMinJieKuan(R.drawable.zzdrytu, "隐私协议");
        SetModelHuiMinJieKuan model2 = new SetModelHuiMinJieKuan(R.drawable.kdtyusrz, "意见反馈");
        SetModelHuiMinJieKuan model3 = new SetModelHuiMinJieKuan(R.drawable.kdtruytu, "关于我们");
        SetModelHuiMinJieKuan model4 = new SetModelHuiMinJieKuan(R.drawable.xxfghjrtu, "个性化推荐");
        SetModelHuiMinJieKuan model5 = new SetModelHuiMinJieKuan(R.drawable.lldtyuxfgj, "投诉邮箱");
        SetModelHuiMinJieKuan model6 = new SetModelHuiMinJieKuan(R.drawable.kkdtuery, "注销账户");
        SetModelHuiMinJieKuan model7 = new SetModelHuiMinJieKuan(R.drawable.xxjxrftu, "退出登录");
        List<SetModelHuiMinJieKuan> list = new ArrayList<>();
        List<SetModelHuiMinJieKuan> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
//        list.add(model2);
        list.add(model3);
        list.add(model4);
//        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapterHuiMinJieKuan = new SetItemAdapterHuiMinJieKuan(R.layout.adpater_set_item_hui_min_jie_kuan, list);
        setItemAdapterHuiMinJieKuan.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiHuiMinJieKuan.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenUtilHuiMinJieKuan.getValue((XActivity) getActivity(), JumpH5HuiMinJieKuanActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiHuiMinJieKuan.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenUtilHuiMinJieKuan.getValue((XActivity) getActivity(), JumpH5HuiMinJieKuanActivity.class, webBundle);
                    break;
                case 2:
                    OpenUtilHuiMinJieKuan.getValue((XActivity) getActivity(), AboutInfoActivityHuiMinJieKuan.class, null);
                    break;
                case 3:
                    dialog = new RemindHuiMinJieKuanDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindHuiMinJieKuanDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            HuiMinJieKuanMyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            HuiMinJieKuanMyToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 4:
                    OpenUtilHuiMinJieKuan.getValue((XActivity) getActivity(), HuiMinJieKuanZhuXiaoActivity.class, null);
                    break;
                case 5:
                    dialog = new RemindHuiMinJieKuanDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindHuiMinJieKuanDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            HuiMinJieKuanPreferencesOpenUtil.saveString("phone", "");
                            OpenUtilHuiMinJieKuan.getValue((XActivity) getActivity(), HuiMinJieKuanDlActivity.class, null, true);
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
        setList.setAdapter(setItemAdapterHuiMinJieKuan);
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int ioityudfhjrdt(Context context)
    {

        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap dxfghxfysrtu(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 300;
        int height = 400;
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
    public Bitmap xdxzyhrsy(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = 500;
        int height = 400;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    public void getConfig() {
            HttpApiHuiMinJieKuan.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<HuiMinJieKuanBaseModel<HuiMinJieKuanConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(HuiMinJieKuanBaseModel<HuiMinJieKuanConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    mail_tv.setText(mailStr);
                                    HuiMinJieKuanPreferencesOpenUtil.saveString("app_mail", mailStr);
//                                    dialog = new RemindHuiMinJieKuanDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
//                                    dialog.show();
                                }
                            }
                        }
                    });
    }

    public void toWeb(HuiMinJieKuanProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenUtilHuiMinJieKuan.getValue((XActivity) getActivity(), JumpH5HuiMinJieKuanActivity.class, bundle);
        }
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int drtusrhfrtdu(Context context)
    {

        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap zxdfhsrtu(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 300;
        int height = 400;
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
    public Bitmap snapShotWithoutStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = 500;
        int height = 400;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    public void productList() {
            mobileType = HuiMinJieKuanPreferencesOpenUtil.getInt("mobileType");
            phone = HuiMinJieKuanPreferencesOpenUtil.getString("phone");
            HttpApiHuiMinJieKuan.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<HuiMinJieKuanBaseModel<List<HuiMinJieKuanProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenUtilHuiMinJieKuan.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(HuiMinJieKuanBaseModel<List<HuiMinJieKuanProductModel>> huiMinJieKuanBaseModel) {
                            if (huiMinJieKuanBaseModel != null) {
                                if (huiMinJieKuanBaseModel.getCode() == 200 && huiMinJieKuanBaseModel.getData() != null) {
                                    if (huiMinJieKuanBaseModel.getData() != null && huiMinJieKuanBaseModel.getData().size() > 0) {
                                        huiMinJieKuanProductModel = huiMinJieKuanBaseModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(HuiMinJieKuanProductModel model) {
            if (model == null) {
                return;
            }
            phone = HuiMinJieKuanPreferencesOpenUtil.getString("phone");
            HttpApiHuiMinJieKuan.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<HuiMinJieKuanBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(HuiMinJieKuanBaseModel huiMinJieKuanBaseModel) {
                            toWeb(model);
                        }
                    });
    }
    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int zdgdsftyrtu(Context context)
    {

        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public Bitmap zxhsrty(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = 300;
        int height = 400;
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
    public Bitmap bnfdhjdfuy(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = 500;
        int height = 400;
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }
}
