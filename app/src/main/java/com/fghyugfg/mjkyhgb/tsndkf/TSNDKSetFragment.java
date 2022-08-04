package com.fghyugfg.mjkyhgb.tsndkf;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fghyugfg.mjkyhgb.mvp.XActivity;
import com.fghyugfg.mjkyhgb.tsndkw.TSNDKRemindDialog;
import com.google.android.material.appbar.AppBarLayout;
import com.fghyugfg.mjkyhgb.TSNDKMainApp;
import com.fghyugfg.mjkyhgb.R;
import com.fghyugfg.mjkyhgb.tsndka.AboutInfoActivityTSNDK;
import com.fghyugfg.mjkyhgb.tsndka.DlTSNDKActivity;
import com.fghyugfg.mjkyhgb.tsndka.TSNDKFeedbackActivity;
import com.fghyugfg.mjkyhgb.tsndka.TSNDKJumpH5Activity;
import com.fghyugfg.mjkyhgb.tsndka.SetActivityTSNDK;
import com.fghyugfg.mjkyhgb.tsndka.SetItemTSNDKAdapter;
import com.fghyugfg.mjkyhgb.tsndkapi.HttpApiTSNDK;
import com.fghyugfg.mjkyhgb.tsndkm.BaseTSNDKModel;
import com.fghyugfg.mjkyhgb.tsndkm.ConfigTSNDKEntity;
import com.fghyugfg.mjkyhgb.tsndkm.ProductModelTSNDK;
import com.fghyugfg.mjkyhgb.tsndkm.SetTSNDKModel;
import com.fghyugfg.mjkyhgb.mvp.XFragment;
import com.fghyugfg.mjkyhgb.net.ApiSubscriber;
import com.fghyugfg.mjkyhgb.net.NetError;
import com.fghyugfg.mjkyhgb.net.XApi;
import com.fghyugfg.mjkyhgb.tsndku.TSNDKOpenUtil;
import com.fghyugfg.mjkyhgb.tsndku.PreferencesTSNDKOpenUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TSNDKSetFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.logout_tv)
    TextView logout_tv;

    private ProductModelTSNDK productModelTSNDK;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemTSNDKAdapter setItemTSNDKAdapter;

    private TSNDKRemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesTSNDKOpenUtil.getString("app_mail");
        phone = PreferencesTSNDKOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        logout_tv.setOnClickListener(v -> {
            dialog = new TSNDKRemindDialog(getActivity()).setCancelText("取消")
                    .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
            dialog.setOnButtonClickListener(new TSNDKRemindDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    PreferencesTSNDKOpenUtil.saveString("phone", "");
                    TSNDKOpenUtil.getValue((XActivity) getActivity(), DlTSNDKActivity.class, null, true);
                }

                @Override
                public void onCancelClicked() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set_three_six_one;
    }

    /**
     * 设置appbar偏移量
     *
     * @param appBar
     * @param offset
     */
    public void setAppBarLayoutOffset(AppBarLayout appBar, int offset) {
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset != offset) {
                appBarLayoutBehavior.setTopAndBottomOffset(offset);
//                appBarLayoutBehavior.onNestedPreScroll(cl, appBar, view, 0, ScreenUtil.dp2px(view.getTop()), new int[]{0, 0}, 1);
            }
        }
    }

    /**
     * 获取view坐标
     *
     * @param view
     * @return
     */
    public int[] getViewLoaction(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public boolean isOpenVersion10NewStore() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public File uriToFileApiQ(Uri uri) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = TSNDKMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(TSNDKMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
//                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetTSNDKModel model = new SetTSNDKModel(R.drawable.ertfhn, "注册协议");
        SetTSNDKModel model1 = new SetTSNDKModel(R.drawable.mghretras, "隐私协议");
        SetTSNDKModel model2 = new SetTSNDKModel(R.drawable.xcbtry, "意见反馈");
        SetTSNDKModel model3 = new SetTSNDKModel(R.drawable.sfdhjs, "关于我们");
        SetTSNDKModel model4 = new SetTSNDKModel(R.drawable.aertfh, "系统设置");
        SetTSNDKModel model5 = new SetTSNDKModel(R.drawable.ljhetgdfh, "投诉邮箱");
        List<SetTSNDKModel> list = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        setItemTSNDKAdapter = new SetItemTSNDKAdapter(R.layout.adpater_set_item_three_six_one, list);
        setItemTSNDKAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiTSNDK.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    TSNDKOpenUtil.getValue((XActivity) getActivity(), TSNDKJumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiTSNDK.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    TSNDKOpenUtil.getValue((XActivity) getActivity(), TSNDKJumpH5Activity.class, webBundle);
                    break;
                case 2:
                    TSNDKOpenUtil.getValue((XActivity) getActivity(), TSNDKFeedbackActivity.class, null);
                    break;
                case 3:
                    TSNDKOpenUtil.getValue((XActivity) getActivity(), AboutInfoActivityTSNDK.class, null);
                    break;
                case 4:
                    TSNDKOpenUtil.getValue((XActivity) getActivity(), SetActivityTSNDK.class, null);
                    break;
                case 5:
                    getConfig();
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemTSNDKAdapter);
    }

    public void getConfig() {
            HttpApiTSNDK.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTSNDKModel<ConfigTSNDKEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseTSNDKModel<ConfigTSNDKEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesTSNDKOpenUtil.saveString("app_mail", mailStr);
                                    dialog = new TSNDKRemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
    }

    /**
     * 设置appbar偏移量
     *
     * @param appBar
     * @param offset
     */
    public void pujdgjhs(AppBarLayout appBar, int offset) {
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset != offset) {
                appBarLayoutBehavior.setTopAndBottomOffset(offset);
//                appBarLayoutBehavior.onNestedPreScroll(cl, appBar, view, 0, ScreenUtil.dp2px(view.getTop()), new int[]{0, 0}, 1);
            }
        }
    }

    /**
     * 获取view坐标
     *
     * @param view
     * @return
     */
    public int[] dgfjhg(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public boolean rtyfghfg() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public File mjhkyfiutdi(Uri uri) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = TSNDKMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(TSNDKMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
//                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public void toWeb(ProductModelTSNDK model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            TSNDKOpenUtil.getValue((XActivity) getActivity(), TSNDKJumpH5Activity.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesTSNDKOpenUtil.getInt("mobileType");
            HttpApiTSNDK.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTSNDKModel<List<ProductModelTSNDK>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            TSNDKOpenUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseTSNDKModel<List<ProductModelTSNDK>> baseTSNDKModel) {
                            if (baseTSNDKModel != null) {
                                if (baseTSNDKModel.getCode() == 200 && baseTSNDKModel.getData() != null) {
                                    if (baseTSNDKModel.getData() != null && baseTSNDKModel.getData().size() > 0) {
                                        productModelTSNDK = baseTSNDKModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    /**
     * 设置appbar偏移量
     *
     * @param appBar
     * @param offset
     */
    public void zvzdyry(AppBarLayout appBar, int offset) {
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset != offset) {
                appBarLayoutBehavior.setTopAndBottomOffset(offset);
//                appBarLayoutBehavior.onNestedPreScroll(cl, appBar, view, 0, ScreenUtil.dp2px(view.getTop()), new int[]{0, 0}, 1);
            }
        }
    }

    /**
     * 获取view坐标
     *
     * @param view
     * @return
     */
    public int[] lgkiuoyjf(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public boolean rtusfhfxgh() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public File nfghfyt(Uri uri) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = TSNDKMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(TSNDKMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
//                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public void productClick(ProductModelTSNDK model) {
            if (model == null) {
                return;
            }
            phone = PreferencesTSNDKOpenUtil.getString("phone");
            HttpApiTSNDK.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseTSNDKModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseTSNDKModel baseTSNDKModel) {
                            toWeb(model);
                        }
                    });
    }

    /**
     * 设置appbar偏移量
     *
     * @param appBar
     * @param offset
     */
    public void ertyuzhgfjh(AppBarLayout appBar, int offset) {
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset != offset) {
                appBarLayoutBehavior.setTopAndBottomOffset(offset);
//                appBarLayoutBehavior.onNestedPreScroll(cl, appBar, view, 0, ScreenUtil.dp2px(view.getTop()), new int[]{0, 0}, 1);
            }
        }
    }

    /**
     * 获取view坐标
     *
     * @param view
     * @return
     */
    public int[] lghiyujgh(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public boolean erthzxgh() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public File mfgjyidu(Uri uri) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = TSNDKMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(TSNDKMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
//                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

}
