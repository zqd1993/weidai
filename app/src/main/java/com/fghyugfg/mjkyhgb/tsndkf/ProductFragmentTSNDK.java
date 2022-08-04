package com.fghyugfg.mjkyhgb.tsndkf;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fghyugfg.mjkyhgb.mvp.XActivity;
import com.fghyugfg.mjkyhgb.tsndka.TSNDKJumpH5Activity;
import com.google.android.material.appbar.AppBarLayout;
import com.fghyugfg.mjkyhgb.TSNDKMainApp;
import com.fghyugfg.mjkyhgb.R;
import com.fghyugfg.mjkyhgb.tsndkapi.HttpApiTSNDK;
import com.fghyugfg.mjkyhgb.imageloader.ILFactory;
import com.fghyugfg.mjkyhgb.imageloader.ILoader;
import com.fghyugfg.mjkyhgb.tsndkm.BaseTSNDKModel;
import com.fghyugfg.mjkyhgb.tsndkm.ProductModelTSNDK;
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
import java.util.List;

import butterknife.BindView;

public class ProductFragmentTSNDK extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    private ProductModelTSNDK productModelTSNDK;

    private Bundle bundle;

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
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_img.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productModelTSNDK);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productModelTSNDK);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productModelTSNDK);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_three_six_one;
    }

    /**
     * 设置appbar偏移量
     *
     * @param appBar
     * @param offset
     */
    public void puikifuyjfgh(AppBarLayout appBar, int offset) {
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
    public int[] vcbxfgtu(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public boolean rthgfdzh() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public File mghjdre(Uri uri) {
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

    public void productClick(ProductModelTSNDK model) {
            if (model != null) {
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
                            setRefreshing.setRefreshing(false);
                            TSNDKOpenUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseTSNDKModel<List<ProductModelTSNDK>> baseTSNDKModel) {
                            setRefreshing.setRefreshing(false);
                            if (baseTSNDKModel != null) {
                                if (baseTSNDKModel.getCode() == 200 && baseTSNDKModel.getData() != null) {
                                    if (baseTSNDKModel.getData() != null && baseTSNDKModel.getData().size() > 0) {
                                        productModelTSNDK = baseTSNDKModel.getData().get(0);
                                        addProductView(baseTSNDKModel.getData());
                                    } else {
                                        if (goodsListLl.getChildCount() == 0) {
                                            noDataTv.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (goodsListLl.getChildCount() == 0) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (goodsListLl.getChildCount() == 0) {
                                    noDataTv.setVisibility(View.VISIBLE);
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
    public void puikdjk(AppBarLayout appBar, int offset) {
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
    public int[] mgfdhfgh(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public boolean ertufhfgh() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public File mgjhryr(Uri uri) {
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

    private void addProductView(List<ProductModelTSNDK> mList) {
        goodsListLl.removeAllViews();
        for (ProductModelTSNDK model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_three_six_one, null);
            ImageView pic = view.findViewById(R.id.goods_pic);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            View yjsq_sl = view.findViewById(R.id.yjsq_sl);
                ILFactory.getLoader().loadNet(pic, HttpApiTSNDK.HTTP_API_URL + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            shijian_tv.setText(model.getDes() + "个月");
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            parentFl.setOnClickListener(v -> {
                productClick(model);
            });
            pic.setOnClickListener(v -> {
                productClick(model);
            });
            yjsq_sl.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
    }

    /**
     * 设置appbar偏移量
     *
     * @param appBar
     * @param offset
     */
    public void zvczxrtrt(AppBarLayout appBar, int offset) {
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
    public int[] lkhoyudt(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public boolean ewrthjf() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public File nbgfftyfhfg(Uri uri) {
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
            bundle.putString("title", model.getProductName());
            TSNDKOpenUtil.getValue((XActivity) getActivity(), TSNDKJumpH5Activity.class, bundle);
        }
    }

    /**
     * 设置appbar偏移量
     *
     * @param appBar
     * @param offset
     */
    public void zcvfdrtyry(AppBarLayout appBar, int offset) {
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
    public int[] khdyhfg(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public boolean ertzbfh() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P /*&& Environment.isExternalStorageLegacy()*/;
    }

    public File mgiyhfg(Uri uri) {
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
