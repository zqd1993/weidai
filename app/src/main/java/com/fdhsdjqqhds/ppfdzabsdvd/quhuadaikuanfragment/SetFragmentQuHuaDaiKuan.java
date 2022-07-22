package com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanfragment;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fdhsdjqqhds.ppfdzabsdvd.R;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanactivity.AboutInfoActivityQuHuaDaiKuan;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanactivity.JumpH5QuHuaDaiKuanActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanactivity.QuHuaDaiKuanDlActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanactivity.FeedbackQuHuaDaiKuanActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanactivity.QuHuaDaiKuanSetItemAdapter;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanactivity.ZhuXiaoQuHuaDaiKuanActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanapi.HttpApiQuHuaDaiKuan;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.QuHuaDaiKuanBaseModel;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.ConfigQuHuaDaiKuanEntity;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.QuHuaDaiKuanProductModel;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanmodel.QuHuaDaiKuanSetModel;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XActivity;
import com.fdhsdjqqhds.ppfdzabsdvd.mvp.XFragment;
import com.fdhsdjqqhds.ppfdzabsdvd.net.ApiSubscriber;
import com.fdhsdjqqhds.ppfdzabsdvd.net.NetError;
import com.fdhsdjqqhds.ppfdzabsdvd.net.XApi;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanutils.MyToastQuHuaDaiKuan;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanutils.OpenQuHuaDaiKuanUtil;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanutils.PreferencesOpenUtilQuHuaDaiKuan;
import com.fdhsdjqqhds.ppfdzabsdvd.quhuadaikuanweidgt.QuHuaDaiKuanRemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFragmentQuHuaDaiKuan extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.zhuxiao_ll)
    View zhuxiao_ll;
    @BindView(R.id.tuichu_ll)
    View tuichu_ll;

    private QuHuaDaiKuanProductModel quHuaDaiKuanProductModel;

    private Bundle bundle, webBundle;

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private int mobileType;

    private String phone;

    private QuHuaDaiKuanSetItemAdapter quHuaDaiKuanSetItemAdapter;

    private QuHuaDaiKuanRemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesOpenUtilQuHuaDaiKuan.getString("app_mail");
        phone = PreferencesOpenUtilQuHuaDaiKuan.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_qu_hua_dai_kuan_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        QuHuaDaiKuanSetModel model = new QuHuaDaiKuanSetModel(R.drawable.zdfgery, "注册协议");
        QuHuaDaiKuanSetModel model1 = new QuHuaDaiKuanSetModel(R.drawable.keryxfjh, "隐私协议");
        QuHuaDaiKuanSetModel model2 = new QuHuaDaiKuanSetModel(R.drawable.qerrfzhj, "意见反馈");
        QuHuaDaiKuanSetModel model3 = new QuHuaDaiKuanSetModel(R.drawable.zgewtgxdy, "关于我们");
        QuHuaDaiKuanSetModel model4 = new QuHuaDaiKuanSetModel(R.drawable.lyuisrthxfgj, "个性化推荐");
        QuHuaDaiKuanSetModel model5 = new QuHuaDaiKuanSetModel(R.drawable.lergfu, "投诉邮箱");
        List<QuHuaDaiKuanSetModel> list = new ArrayList<>();
        List<QuHuaDaiKuanSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        quHuaDaiKuanSetItemAdapter = new QuHuaDaiKuanSetItemAdapter(R.layout.adpater_qu_hua_dai_kuan_set_item, list);
        quHuaDaiKuanSetItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiQuHuaDaiKuan.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenQuHuaDaiKuanUtil.getValue((XActivity) getActivity(), JumpH5QuHuaDaiKuanActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiQuHuaDaiKuan.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenQuHuaDaiKuanUtil.getValue((XActivity) getActivity(), JumpH5QuHuaDaiKuanActivity.class, webBundle);
                    break;
                case 2:
                    OpenQuHuaDaiKuanUtil.getValue((XActivity) getActivity(), FeedbackQuHuaDaiKuanActivity.class, null);
                    break;
                case 3:
                    OpenQuHuaDaiKuanUtil.getValue((XActivity) getActivity(), AboutInfoActivityQuHuaDaiKuan.class, null);
                    break;
                case 4:
                    dialog = new QuHuaDaiKuanRemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new QuHuaDaiKuanRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToastQuHuaDaiKuan.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToastQuHuaDaiKuan.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
            }
        });
        setList.setLayoutManager(new LinearLayoutManager(getActivity()));
        setList.setAdapter(quHuaDaiKuanSetItemAdapter);
        zhuxiao_ll.setOnClickListener(v -> {
            OpenQuHuaDaiKuanUtil.getValue((XActivity) getActivity(), ZhuXiaoQuHuaDaiKuanActivity.class, null);
        });
        tuichu_ll.setOnClickListener(v -> {
            dialog = new QuHuaDaiKuanRemindDialog(getActivity()).setCancelText("取消")
                    .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
            dialog.setOnButtonClickListener(new QuHuaDaiKuanRemindDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    PreferencesOpenUtilQuHuaDaiKuan.saveString("phone", "");
                    OpenQuHuaDaiKuanUtil.getValue((XActivity) getActivity(), QuHuaDaiKuanDlActivity.class, null, true);
                }

                @Override
                public void onCancelClicked() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int mtktdyhx(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int ertgxdh(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int ugfjdfjk(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public void getConfig() {
        HttpApiQuHuaDaiKuan.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<QuHuaDaiKuanBaseModel<ConfigQuHuaDaiKuanEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(QuHuaDaiKuanBaseModel<ConfigQuHuaDaiKuanEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferencesOpenUtilQuHuaDaiKuan.saveString("app_mail", mailStr);
                                dialog = new QuHuaDaiKuanRemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                dialog.show();
                            }
                        }
                    }
                });
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int zxzdrtyruy(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int wqersty(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int retygdfh(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public void toWeb(QuHuaDaiKuanProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenQuHuaDaiKuanUtil.getValue((XActivity) getActivity(), JumpH5QuHuaDaiKuanActivity.class, bundle);
        }
    }

    public void productList() {
        mobileType = PreferencesOpenUtilQuHuaDaiKuan.getInt("mobileType");
        phone = PreferencesOpenUtilQuHuaDaiKuan.getString("phone");
        HttpApiQuHuaDaiKuan.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<QuHuaDaiKuanBaseModel<List<QuHuaDaiKuanProductModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenQuHuaDaiKuanUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(QuHuaDaiKuanBaseModel<List<QuHuaDaiKuanProductModel>> quHuaDaiKuanBaseModel) {
                        if (quHuaDaiKuanBaseModel != null) {
                            if (quHuaDaiKuanBaseModel.getCode() == 200 && quHuaDaiKuanBaseModel.getData() != null) {
                                if (quHuaDaiKuanBaseModel.getData() != null && quHuaDaiKuanBaseModel.getData().size() > 0) {
                                    quHuaDaiKuanProductModel = quHuaDaiKuanBaseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(QuHuaDaiKuanProductModel model) {
        if (model == null) {
            return;
        }
        phone = PreferencesOpenUtilQuHuaDaiKuan.getString("phone");
        HttpApiQuHuaDaiKuan.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<QuHuaDaiKuanBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(QuHuaDaiKuanBaseModel quHuaDaiKuanBaseModel) {
                        toWeb(model);
                    }
                });
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int otyujghjdty(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int wetxdfyhty(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int rtyhfxghdrtu(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
