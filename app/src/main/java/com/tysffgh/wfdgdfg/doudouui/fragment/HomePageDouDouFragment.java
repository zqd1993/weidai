package com.tysffgh.wfdgdfg.doudouui.fragment;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tysffgh.wfdgdfg.R;
import com.tysffgh.wfdgdfg.doudouadapter.GoodsItemAdapterDouDou;
import com.tysffgh.wfdgdfg.doudoumodel.DouDouGoodsModel;
import com.tysffgh.wfdgdfg.doudouui.WebViewActivityDouDou;
import com.tysffgh.wfdgdfg.mvp.XFragment;
import com.tysffgh.wfdgdfg.doudoupresent.DouDouHomePagePresent;
import com.tysffgh.wfdgdfg.router.Router;

import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageDouDouFragment extends XFragment<DouDouHomePagePresent> {

    @BindView(R.id.product_bg)
    View productBg;
    @BindView(R.id.home_page_bg)
    View homePageBg;
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.top_img)
//    public ImageView topImg;
    @BindView(R.id.money_num_tv)
    public TextView money_num_tv;
    @BindView(R.id.click_view_1)
    View click_view_1;
    @BindView(R.id.time_tv)
    public TextView time_tv;
    @BindView(R.id.rililv_tv)
    public TextView rililv_tv;
    @BindView(R.id.people_num_tv)
    public TextView people_num_tv;

    private Bundle webBundle;
    public GoodsItemAdapterDouDou goodsItemAdapterDouDou;
    public DouDouGoodsModel douDouGoodsModel, topDouDouGoodsModel;

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
    public void initData(Bundle savedInstanceState) {
//        getP().productList();
//        ToastUtil.showShort("API_BASE_URL = " + SharedPreferencesUtilis.getStringFromPref("API_BASE_URL") +
//                "API_BASE_URL_1 = " + Api.API_BASE_URL);
        getP().aindex();
        swipeRefreshLayout.setOnRefreshListener(() -> {
//            getP().productList();
            getP().aindex();
        });
        click_view_1.setOnClickListener(v -> {
            productClick(topDouDouGoodsModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_doudou_home_page;
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String mnghdtydfg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String wergfdsn() {
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
    private String tregfdfgh(@NonNull Context context) {
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
    public DouDouHomePagePresent newP() {
        return new DouDouHomePagePresent();
    }

    private void productClick(DouDouGoodsModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(DouDouGoodsModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewActivityDouDou.class)
                    .data(webBundle)
                    .launch();
        }
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String rethgfsh() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String mdgdfgsr() {
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
    private String sfvbdrtrt(@NonNull Context context) {
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

    public void initGoodsItemAdapter(List<DouDouGoodsModel> mData) {
        if (goodsItemAdapterDouDou == null) {
            goodsItemAdapterDouDou = new GoodsItemAdapterDouDou(getActivity());
            goodsItemAdapterDouDou.setRecItemClick(new RecyclerItemCallback<DouDouGoodsModel, GoodsItemAdapterDouDou.ViewHolder>() {
                @Override
                public void onItemClick(int position, DouDouGoodsModel model, int tag, GoodsItemAdapterDouDou.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterDouDou.setHasStableIds(true);
            goodsItemAdapterDouDou.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterDouDou);
        } else {
            goodsItemAdapterDouDou.setData(mData);
        }
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private String wergfbfgb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Application.getProcessName();
        }
        return null;
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    private String nfdrgdfgs() {
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
    private String werfgdagh(@NonNull Context context) {
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

    public void setModel(DouDouGoodsModel douDouGoodsModel) {
        this.douDouGoodsModel = douDouGoodsModel;
    }
}
