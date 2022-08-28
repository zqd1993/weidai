package com.xiaoniukaudiakuandsfwet.vsdaetgerat.fragmentcxiaoniukuaidaikuanserwet;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoniukaudiakuandsfwet.vsdaetgerat.R;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewAboutInfoActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewJumpActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewDlActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewFeedbackActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewSetItemAdapter;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.activitycxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewZhuXiaoActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.apicxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewHttpApi;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewBaseModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewConfigEntity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewProductModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.modelcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewSetModel;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.mvp.XActivity;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.mvp.XFragment;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.ApiSubscriber;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.NetError;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.net.XApi;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewMyToast;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewOpenUtil;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.utilscxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil;
import com.xiaoniukaudiakuandsfwet.vsdaetgerat.weidgtcxiaoniukuaidaikuanserwet.XiaoNiuHuaDaiKuanOpNewRemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class XiaoNiuHuaDaiKuanOpNewSetFragment extends XFragment {

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

    private XiaoNiuHuaDaiKuanOpNewProductModel xiaoNiuHuaDaiKuanOpNewProductModel;

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

    private XiaoNiuHuaDaiKuanOpNewSetItemAdapter xiaoNiuHuaDaiKuanOpNewSetItemAdapter;

    private XiaoNiuHuaDaiKuanOpNewRemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getString("app_mail");
        phone = XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_xiao_niu_hua_dai_kuan_op_new_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        XiaoNiuHuaDaiKuanOpNewSetModel model = new XiaoNiuHuaDaiKuanOpNewSetModel(R.drawable.zdfgery, "注册协议");
        XiaoNiuHuaDaiKuanOpNewSetModel model1 = new XiaoNiuHuaDaiKuanOpNewSetModel(R.drawable.keryxfjh, "隐私协议");
        XiaoNiuHuaDaiKuanOpNewSetModel model2 = new XiaoNiuHuaDaiKuanOpNewSetModel(R.drawable.qerrfzhj, "意见反馈");
        XiaoNiuHuaDaiKuanOpNewSetModel model3 = new XiaoNiuHuaDaiKuanOpNewSetModel(R.drawable.zgewtgxdy, "关于我们");
        XiaoNiuHuaDaiKuanOpNewSetModel model4 = new XiaoNiuHuaDaiKuanOpNewSetModel(R.drawable.lyuisrthxfgj, "个性化推荐");
        XiaoNiuHuaDaiKuanOpNewSetModel model5 = new XiaoNiuHuaDaiKuanOpNewSetModel(R.drawable.lergfu, "投诉邮箱");
        List<XiaoNiuHuaDaiKuanOpNewSetModel> list = new ArrayList<>();
        List<XiaoNiuHuaDaiKuanOpNewSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        xiaoNiuHuaDaiKuanOpNewSetItemAdapter = new XiaoNiuHuaDaiKuanOpNewSetItemAdapter(R.layout.adpater_xiao_niu_hua_dai_kuan_op_new_set_item, list);
        xiaoNiuHuaDaiKuanOpNewSetItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", XiaoNiuHuaDaiKuanOpNewHttpApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    XiaoNiuHuaDaiKuanOpNewOpenUtil.getValue((XActivity) getActivity(), XiaoNiuHuaDaiKuanOpNewJumpActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", XiaoNiuHuaDaiKuanOpNewHttpApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    XiaoNiuHuaDaiKuanOpNewOpenUtil.getValue((XActivity) getActivity(), XiaoNiuHuaDaiKuanOpNewJumpActivity.class, webBundle);
                    break;
                case 2:
                    XiaoNiuHuaDaiKuanOpNewOpenUtil.getValue((XActivity) getActivity(), XiaoNiuHuaDaiKuanOpNewFeedbackActivity.class, null);
                    break;
                case 3:
                    XiaoNiuHuaDaiKuanOpNewOpenUtil.getValue((XActivity) getActivity(), XiaoNiuHuaDaiKuanOpNewAboutInfoActivity.class, null);
                    break;
                case 4:
                    dialog = new XiaoNiuHuaDaiKuanOpNewRemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new XiaoNiuHuaDaiKuanOpNewRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            XiaoNiuHuaDaiKuanOpNewMyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            XiaoNiuHuaDaiKuanOpNewMyToast.showShort("开启成功");
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
        setList.setAdapter(xiaoNiuHuaDaiKuanOpNewSetItemAdapter);
        zhuxiao_ll.setOnClickListener(v -> {
            XiaoNiuHuaDaiKuanOpNewOpenUtil.getValue((XActivity) getActivity(), XiaoNiuHuaDaiKuanOpNewZhuXiaoActivity.class, null);
        });
        tuichu_ll.setOnClickListener(v -> {
            dialog = new XiaoNiuHuaDaiKuanOpNewRemindDialog(getActivity()).setCancelText("取消")
                    .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
            dialog.setOnButtonClickListener(new XiaoNiuHuaDaiKuanOpNewRemindDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.saveString("phone", "");
                    XiaoNiuHuaDaiKuanOpNewOpenUtil.getValue((XActivity) getActivity(), XiaoNiuHuaDaiKuanOpNewDlActivity.class, null, true);
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
            XiaoNiuHuaDaiKuanOpNewHttpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(XiaoNiuHuaDaiKuanOpNewBaseModel<XiaoNiuHuaDaiKuanOpNewConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.saveString("app_mail", mailStr);
                                    dialog = new XiaoNiuHuaDaiKuanOpNewRemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
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

    public void toWeb(XiaoNiuHuaDaiKuanOpNewProductModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            XiaoNiuHuaDaiKuanOpNewOpenUtil.getValue((XActivity) getActivity(), XiaoNiuHuaDaiKuanOpNewJumpActivity.class, bundle);
        }
    }

    public void productList() {
            mobileType = XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getInt("mobileType");
            phone = XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getString("phone");
            XiaoNiuHuaDaiKuanOpNewHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<XiaoNiuHuaDaiKuanOpNewBaseModel<List<XiaoNiuHuaDaiKuanOpNewProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            XiaoNiuHuaDaiKuanOpNewOpenUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(XiaoNiuHuaDaiKuanOpNewBaseModel<List<XiaoNiuHuaDaiKuanOpNewProductModel>> xiaoNiuHuaDaiKuanOpNewBaseModel) {
                            if (xiaoNiuHuaDaiKuanOpNewBaseModel != null) {
                                if (xiaoNiuHuaDaiKuanOpNewBaseModel.getCode() == 200 && xiaoNiuHuaDaiKuanOpNewBaseModel.getData() != null) {
                                    if (xiaoNiuHuaDaiKuanOpNewBaseModel.getData() != null && xiaoNiuHuaDaiKuanOpNewBaseModel.getData().size() > 0) {
                                        xiaoNiuHuaDaiKuanOpNewProductModel = xiaoNiuHuaDaiKuanOpNewBaseModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(XiaoNiuHuaDaiKuanOpNewProductModel model) {
            if (model == null) {
                return;
            }
            phone = XiaoNiuHuaDaiKuanOpNewPreferencesOpenUtil.getString("phone");
            XiaoNiuHuaDaiKuanOpNewHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<XiaoNiuHuaDaiKuanOpNewBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(XiaoNiuHuaDaiKuanOpNewBaseModel xiaoNiuHuaDaiKuanOpNewBaseModel) {
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
