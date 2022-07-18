package com.tysffgh.wfdgdfg.doudouui.fragment;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tysffgh.wfdgdfg.R;
import com.tysffgh.wfdgdfg.doudouadapter.MineDouDouAdapter;
import com.tysffgh.wfdgdfg.doudouadapter.MineItemAdapter;
import com.tysffgh.wfdgdfg.doudoumodel.BaseRespModelDouDou;
import com.tysffgh.wfdgdfg.doudoumodel.CompanDouDouyInfoModel;
import com.tysffgh.wfdgdfg.doudoumodel.MineItemModelDouDou;
import com.tysffgh.wfdgdfg.doudounet.ApiSubscriber;
import com.tysffgh.wfdgdfg.doudounet.NetError;
import com.tysffgh.wfdgdfg.doudounet.XApi;
import com.tysffgh.wfdgdfg.doudouui.WebViewActivityDouDou;
import com.tysffgh.wfdgdfg.doudouui.activity.AboutUsActivityDouDou;
import com.tysffgh.wfdgdfg.doudouui.activity.SettingActivityDouDou;
import com.tysffgh.wfdgdfg.doudouutils.SharedDouDouPreferencesUtilis;
import com.tysffgh.wfdgdfg.doudouutils.ToastUtilDouDou;
import com.tysffgh.wfdgdfg.doudounet.ApiDouDou;
import com.tysffgh.wfdgdfg.router.Router;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.tysffgh.wfdgdfg.doudouwidget.NormalDouDouDialog;
import com.tysffgh.wfdgdfg.mvp.XFragment;
import com.tysffgh.wfdgdfg.doudouui.activity.CancellationDouDouAccountActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class DouDouMineFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;

    private MineDouDouAdapter mineDouDouAdapter;
    private MineItemAdapter mineItemAdapter;
    private List<MineItemModelDouDou> list, list1;
    private int[] imgRes = {R.drawable.wd_zcxy, R.drawable.wd_ysxy, R.drawable.wd_icon_gywm,
            R.drawable.wd_icon_tsyx, R.drawable.wd_icon_gbgxhts, R.drawable.wd_icon_txdl};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "投诉邮箱", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalDouDouDialog normalDouDouDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        phone = SharedDouDouPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 6; i++) {
            MineItemModelDouDou model = new MineItemModelDouDou();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            if (i < 3) {
                list1.add(model);
            } else {
                list.add(model);
            }
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String getCurrentProcessNameByApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String getCurrentProcessNameByActivityThread() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    private String getCurrentProcessNameByActivityManager(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_doudou;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineDouDouAdapter == null) {
            mineDouDouAdapter = new MineDouDouAdapter(getActivity());
            mineDouDouAdapter.setData(list);
            mineDouDouAdapter.setHasStableIds(true);
            mineDouDouAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelDouDou, MineDouDouAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelDouDou model, int tag, MineDouDouAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    if (tag == 2) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText(null, mailStr);
                        clipboard.setPrimaryClip(clipData);
                        ToastUtilDouDou.showShort("复制成功");
                        return;
                    }
                    switch (position) {
                        case 1:
                            Router.newIntent(getActivity())
                                    .to(SettingActivityDouDou.class)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(CancellationDouDouAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineDouDouAdapter);
            getCompanyInfo();
        }
        if (mineItemAdapter == null) {
            mineItemAdapter = new MineItemAdapter(getActivity());
            mineItemAdapter.setData(list1);
            mineItemAdapter.setHasStableIds(true);
            mineItemAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelDouDou, MineItemAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelDouDou model, int tag, MineItemAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiDouDou.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityDouDou.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiDouDou.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityDouDou.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivityDouDou.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(mineItemAdapter);
        }
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String uwehfgds() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String erthfg() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    private String regffh(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedDouDouPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiDouDou.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelDouDou<CompanDouDouyInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDouDou<CompanDouDouyInfoModel>>getScheduler())
                    .compose(this.<BaseRespModelDouDou<CompanDouDouyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDouDou<CompanDouDouyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelDouDou<CompanDouDouyInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    SharedDouDouPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                    if (mineDouDouAdapter != null){
                                        mineDouDouAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (normalDouDouDialog != null) {
            normalDouDouDialog.dismiss();
            normalDouDouDialog = null;
        }
        super.onDestroy();
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String mfhgzdgfdg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String werhnfgh() {
        String processName = null;
        try {
            final Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader())
                    .getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            final Object invoke = declaredMethod.invoke(null, new Object[0]);
            if (invoke instanceof String) {
                processName = (String) invoke;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processName;
    }

    /**
     * 通过ActivityManager 获取进程名，需要IPC通信
     */
    private String trgffyed(@NonNull Context context) {
        if (context == null) {
            return null;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
            if (runningAppList != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
                    if (processInfo.pid == pid) {
                        return processInfo.processName;
                    }
                }
            }
        }
        return null;
    }

}
