package com.dlproject.bkdk.fra;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dlproject.bkdk.R;
import com.dlproject.bkdk.act.WoMenActivity;
import com.dlproject.bkdk.act.DengGeLuActivity;
import com.dlproject.bkdk.act.HuiFangActivity;
import com.dlproject.bkdk.act.JumpH5Activity;
import com.dlproject.bkdk.act.SheZhiLieBiaoAdapter;
import com.dlproject.bkdk.act.ZhangHaoActivity;
import com.dlproject.bkdk.net.WangLuoApi;
import com.dlproject.bkdk.bean.ParentModel;
import com.dlproject.bkdk.bean.ChanPinModel;
import com.dlproject.bkdk.bean.SheZhiModel;
import com.dlproject.bkdk.mvp.XFragment;
import com.dlproject.bkdk.net.ApiSubscriber;
import com.dlproject.bkdk.net.NetError;
import com.dlproject.bkdk.net.XApi;
import com.dlproject.bkdk.uti.TiShi;
import com.dlproject.bkdk.uti.GongJuLei;
import com.dlproject.bkdk.uti.SPFile;
import com.dlproject.bkdk.wei.TiShiDialog;

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


    /**
     * 历史地址
     */
    public final static String HISTORYADDRESS = "HISTORYADDRESS";
    /**
     * 扫描类型
     */
    public final static String QRCODETYPE = "QRCODETYPE";

    public final static String BARCODE = "BARCODE";

    //    获取默认文件存储目录
    public static String getUpdateFileDir() {
        return "amez/apk/";
    }

    public final static String INTENT_KEY = "update_dialog_values";

    // 360手机助手
    public static final String MARKET = "com.qihoo.appstore";
    // 淘宝手机助手
    public static final String MARKET_TAOBAO = "com.taobao.appcenter";
    // 应用宝
    public static final String MARKET_QQDOWNLOADER = "com.tencent.Android.qqdownloader";
    // 安卓市场
    public static final String MARKET_HIAPK = "com.hiapk.marketpho";
    // 安智市场
    public static final String MARKET_GOAPK = "cn.goapk.market";
    // 包名
    public static final String APP_PACKAGE_NAME = "com.*.*";



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
                            TiShi.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            TiShi.showShort("开启成功");
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

    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 启动App
     *
     * @param context
     */
    public static void launchapp(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager()
                    .getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 跳转到应用市场
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
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

    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean tbgf(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 启动App
     *
     * @param context
     */
    public static void hnghj(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager()
                    .getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 跳转到应用市场
     */
    public static void ikyuk(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
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

    /**
     * 检测某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean rebdfgt(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    /**
     * 启动App
     *
     * @param context
     */
    public static void nhguuj(Context context) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, APP_PACKAGE_NAME)) {
            context.startActivity(context.getPackageManager()
                    .getLaunchIntentForPackage(APP_PACKAGE_NAME));
        } else {
            goToMarket(context, APP_PACKAGE_NAME);
        }
    }

    /**
     * 跳转到应用市场
     */
    public static void wcdf(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }

}
