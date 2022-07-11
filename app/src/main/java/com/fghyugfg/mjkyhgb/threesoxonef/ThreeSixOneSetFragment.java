package com.fghyugfg.mjkyhgb.threesoxonef;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.fghyugfg.mjkyhgb.ThreeSixOneMainApp;
import com.fghyugfg.mjkyhgb.R;
import com.fghyugfg.mjkyhgb.threesoxonea.AboutInfoActivityThreeSixOne;
import com.fghyugfg.mjkyhgb.threesoxonea.DlThreeSixOneActivity;
import com.fghyugfg.mjkyhgb.threesoxonea.ThreeSixOneFeedbackActivity;
import com.fghyugfg.mjkyhgb.threesoxonea.ThreeSixOneJumpH5Activity;
import com.fghyugfg.mjkyhgb.threesoxonea.SetActivityThreeSixOne;
import com.fghyugfg.mjkyhgb.threesoxonea.SetItemThreeSixOneAdapter;
import com.fghyugfg.mjkyhgb.apithreesoxone.HttpApiThreeSixOne;
import com.fghyugfg.mjkyhgb.threesoxonem.BaseThreeSixOneModel;
import com.fghyugfg.mjkyhgb.threesoxonem.ConfigThreeSixOneEntity;
import com.fghyugfg.mjkyhgb.threesoxonem.ProductModelThreeSixOne;
import com.fghyugfg.mjkyhgb.threesoxonem.SetThreeSixOneModel;
import com.fghyugfg.mjkyhgb.mvp.XFragment;
import com.fghyugfg.mjkyhgb.net.ApiSubscriber;
import com.fghyugfg.mjkyhgb.net.NetError;
import com.fghyugfg.mjkyhgb.net.XApi;
import com.fghyugfg.mjkyhgb.threesoxoneu.ThreeSixOneOpenUtil;
import com.fghyugfg.mjkyhgb.threesoxoneu.PreferencesThreeSixOneOpenUtil;
import com.fghyugfg.mjkyhgb.threesoxonew.ThreeSixOneRemindDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ThreeSixOneSetFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.logout_tv)
    TextView logout_tv;

    private ProductModelThreeSixOne productModelThreeSixOne;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemThreeSixOneAdapter setItemThreeSixOneAdapter;

    private ThreeSixOneRemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesThreeSixOneOpenUtil.getString("app_mail");
        phone = PreferencesThreeSixOneOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        logout_tv.setOnClickListener(v -> {
            dialog = new ThreeSixOneRemindDialog(getActivity()).setCancelText("取消")
                    .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
            dialog.setOnButtonClickListener(new ThreeSixOneRemindDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    PreferencesThreeSixOneOpenUtil.saveString("phone", "");
                    ThreeSixOneOpenUtil.jumpPage(getActivity(), DlThreeSixOneActivity.class);
                    getActivity().finish();
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
            ContentResolver contentResolver = ThreeSixOneMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(ThreeSixOneMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
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
        SetThreeSixOneModel model = new SetThreeSixOneModel(R.drawable.ertfhn, "注册协议");
        SetThreeSixOneModel model1 = new SetThreeSixOneModel(R.drawable.mghretras, "隐私协议");
        SetThreeSixOneModel model2 = new SetThreeSixOneModel(R.drawable.xcbtry, "意见反馈");
        SetThreeSixOneModel model3 = new SetThreeSixOneModel(R.drawable.sfdhjs, "关于我们");
        SetThreeSixOneModel model4 = new SetThreeSixOneModel(R.drawable.aertfh, "系统设置");
        SetThreeSixOneModel model5 = new SetThreeSixOneModel(R.drawable.ljhetgdfh, "投诉邮箱");
        List<SetThreeSixOneModel> list = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        setItemThreeSixOneAdapter = new SetItemThreeSixOneAdapter(R.layout.adpater_set_item_three_six_one, list);
        setItemThreeSixOneAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    if (!TextUtils.isEmpty(PreferencesThreeSixOneOpenUtil.getString("AGREEMENT"))) {
                        webBundle = new Bundle();
                        webBundle.putString("url", PreferencesThreeSixOneOpenUtil.getString("AGREEMENT") + HttpApiThreeSixOne.ZCXY);
                        webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                        ThreeSixOneOpenUtil.jumpPage(getActivity(), ThreeSixOneJumpH5Activity.class, webBundle);
                    }
                    break;
                case 1:
                    if (!TextUtils.isEmpty(PreferencesThreeSixOneOpenUtil.getString("AGREEMENT"))) {
                        webBundle = new Bundle();
                        webBundle.putString("url", PreferencesThreeSixOneOpenUtil.getString("AGREEMENT") + HttpApiThreeSixOne.YSXY);
                        webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                        ThreeSixOneOpenUtil.jumpPage(getActivity(), ThreeSixOneJumpH5Activity.class, webBundle);
                    }
                    break;
                case 2:
                    ThreeSixOneOpenUtil.jumpPage(getActivity(), ThreeSixOneFeedbackActivity.class);
                    break;
                case 3:
                    ThreeSixOneOpenUtil.jumpPage(getActivity(), AboutInfoActivityThreeSixOne.class);
                    break;
                case 4:
                    ThreeSixOneOpenUtil.jumpPage(getActivity(), SetActivityThreeSixOne.class);
                    break;
                case 5:
                    getConfig();
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemThreeSixOneAdapter);
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(PreferencesThreeSixOneOpenUtil.getString("HTTP_API_URL"))) {
            HttpApiThreeSixOne.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseThreeSixOneModel<ConfigThreeSixOneEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseThreeSixOneModel<ConfigThreeSixOneEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesThreeSixOneOpenUtil.saveString("app_mail", mailStr);
                                    dialog = new ThreeSixOneRemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
        }
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
            ContentResolver contentResolver = ThreeSixOneMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(ThreeSixOneMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
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

    public void toWeb(ProductModelThreeSixOne model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            ThreeSixOneOpenUtil.jumpPage(getActivity(), ThreeSixOneJumpH5Activity.class, bundle);
        }
    }

    public void productList() {
        if (!TextUtils.isEmpty(PreferencesThreeSixOneOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = PreferencesThreeSixOneOpenUtil.getInt("mobileType");
            HttpApiThreeSixOne.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseThreeSixOneModel<List<ProductModelThreeSixOne>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            ThreeSixOneOpenUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseThreeSixOneModel<List<ProductModelThreeSixOne>> baseThreeSixOneModel) {
                            if (baseThreeSixOneModel != null) {
                                if (baseThreeSixOneModel.getCode() == 200 && baseThreeSixOneModel.getData() != null) {
                                    if (baseThreeSixOneModel.getData() != null && baseThreeSixOneModel.getData().size() > 0) {
                                        productModelThreeSixOne = baseThreeSixOneModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
        }
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
            ContentResolver contentResolver = ThreeSixOneMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(ThreeSixOneMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
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

    public void productClick(ProductModelThreeSixOne model) {
        if (!TextUtils.isEmpty(PreferencesThreeSixOneOpenUtil.getString("HTTP_API_URL"))) {
            if (model == null) {
                return;
            }
            phone = PreferencesThreeSixOneOpenUtil.getString("phone");
            HttpApiThreeSixOne.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseThreeSixOneModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseThreeSixOneModel baseThreeSixOneModel) {
                            toWeb(model);
                        }
                    });
        }
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
            ContentResolver contentResolver = ThreeSixOneMainApp.getContext().getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(ThreeSixOneMainApp.getContext().getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
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
