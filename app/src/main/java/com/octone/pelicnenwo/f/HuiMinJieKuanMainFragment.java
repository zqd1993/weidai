package com.octone.pelicnenwo.f;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.octone.pelicnenwo.R;
import com.octone.pelicnenwo.huiminjiekuana.HuiMinJieKuanImageAdapter;
import com.octone.pelicnenwo.huiminjiekuana.HuiMinJieKuanMainActivity;
import com.octone.pelicnenwo.huiminjiekuana.JumpH5HuiMinJieKuanActivity;
import com.octone.pelicnenwo.huiminjiekuanapi.HttpApiHuiMinJieKuan;
import com.octone.pelicnenwo.imageloader.ILFactory;
import com.octone.pelicnenwo.imageloader.ILoader;
import com.octone.pelicnenwo.huiminjiekuanm.HuiMinJieKuanBaseModel;
import com.octone.pelicnenwo.huiminjiekuanm.HuiMinJieKuanProductModel;
import com.octone.pelicnenwo.mvp.XActivity;
import com.octone.pelicnenwo.mvp.XFragment;
import com.octone.pelicnenwo.net.ApiSubscriber;
import com.octone.pelicnenwo.net.NetError;
import com.octone.pelicnenwo.net.XApi;
import com.octone.pelicnenwo.huiminjiekuanu.OpenUtilHuiMinJieKuan;
import com.octone.pelicnenwo.huiminjiekuanu.HuiMinJieKuanPreferencesOpenUtil;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class HuiMinJieKuanMainFragment extends XFragment {

    private int mobileType;

    private String phone;

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int srsrthrtu(Context context)
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
    public Bitmap syhrhdfurtu(Activity activity)
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
    public Bitmap ssthrdtusr(Activity activity)
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

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.goods_banner)
    Banner banner;
    @BindView(R.id.click_fl)
    View click_fl;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.banner_iv)
    View banner_iv;
    @BindView(R.id.more_tv)
    View more_tv;
    @BindView(R.id.title_ll)
    View title_ll;

    private HuiMinJieKuanProductModel huiMinJieKuanProductModel;

    private Bundle bundle;

    private HuiMinJieKuanImageAdapter huiMinJieKuanImageAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        goodsListLl.setVisibility(View.VISIBLE);
        banner_iv.setVisibility(View.VISIBLE);
        title_ll.setVisibility(View.VISIBLE);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(huiMinJieKuanProductModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(huiMinJieKuanProductModel);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(huiMinJieKuanProductModel);
        });
        more_tv.setOnClickListener(v -> {
            ((HuiMinJieKuanMainActivity)getActivity()).changePage();
        });
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int iidtyjdfjdrt(Context context)
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
    public Bitmap zxdfhsrtuyrs(Activity activity)
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
    public Bitmap xfxfthrtu(Activity activity)
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
        productList();
    }

    private void initBannerAdapter(List<HuiMinJieKuanProductModel> data) {
        huiMinJieKuanImageAdapter = null;
        huiMinJieKuanImageAdapter = new HuiMinJieKuanImageAdapter(data);
        huiMinJieKuanImageAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(huiMinJieKuanImageAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_hui_min_jie_kuan;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int xxfgjdfgyudrtu(Context context)
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
    public Bitmap zxzzryrtfghj(Activity activity)
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
    public Bitmap srtudrtu(Activity activity)
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


    public void productList() {
            mobileType = HuiMinJieKuanPreferencesOpenUtil.getInt("mobileType");
            phone = HuiMinJieKuanPreferencesOpenUtil.getString("phone");
            huiMinJieKuanProductModel = null;
            HttpApiHuiMinJieKuan.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<HuiMinJieKuanBaseModel<List<HuiMinJieKuanProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenUtilHuiMinJieKuan.showErrorInfo(getActivity(), error);
                            if (huiMinJieKuanImageAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(HuiMinJieKuanBaseModel<List<HuiMinJieKuanProductModel>> huiMinJieKuanBaseModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (huiMinJieKuanBaseModel != null) {
                                if (huiMinJieKuanBaseModel.getCode() == 200 && huiMinJieKuanBaseModel.getData() != null) {
                                    if (huiMinJieKuanBaseModel.getData() != null && huiMinJieKuanBaseModel.getData().size() > 0) {
                                        huiMinJieKuanProductModel = huiMinJieKuanBaseModel.getData().get(0);
                                        initBannerAdapter(huiMinJieKuanBaseModel.getData());
//                                        addProductView(huiMinJieKuanBaseModel.getData());
                                    } else {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            } else {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    });
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int getStatusHuusrthxfueight(Context context)
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
    public Bitmap tyxfghrtdu(Activity activity)
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
    public Bitmap srhfghjdrt6u(Activity activity)
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

    private void addProductView(List<HuiMinJieKuanProductModel> mList) {
        for (HuiMinJieKuanProductModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_hui_min_jie_kuan_product_item, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            ILFactory.getLoader().loadNet(pic, HttpApiHuiMinJieKuan.HTTP_API_URL + model.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            shijian_tv.setText("周期" + model.getDes() + "个月");
            shuliang_tv.setText(String.valueOf(model.getPassingRate()) + "下载");
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            parentFl.setOnClickListener(v -> {
                productClick(model);
            });
            pic.setOnClickListener(v -> {
                productClick(model);
            });
            yjsqSl.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
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
    public int getStatusHeight(Context context)
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
    public Bitmap srsrrthrt(Activity activity)
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
    public Bitmap zdfgdhrt(Activity activity)
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
