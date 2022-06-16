package com.fghyugfg.mjkyhgb.f;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fghyugfg.mjkyhgb.R;
import com.fghyugfg.mjkyhgb.a.ImageAdapter;
import com.fghyugfg.mjkyhgb.a.JumpH5Activity;
import com.fghyugfg.mjkyhgb.a.MainActivity;
import com.fghyugfg.mjkyhgb.api.NewApi;
import com.fghyugfg.mjkyhgb.m.RenRenBaseModel;
import com.fghyugfg.mjkyhgb.m.RenRenGoodsModel;
import com.fghyugfg.mjkyhgb.mvp.XFragment;
import com.fghyugfg.mjkyhgb.net.ApiSubscriber;
import com.fghyugfg.mjkyhgb.net.NetError;
import com.fghyugfg.mjkyhgb.net.XApi;
import com.fghyugfg.mjkyhgb.u.OpenMethodUtil;
import com.fghyugfg.mjkyhgb.u.SPOpenUtil;
import com.youth.banner.Banner;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;

public class HomeMainFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.banner1)
    Banner banner;
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.hot_fl)
    View hotFl;

    private RenRenGoodsModel renRenGoodsModel;

    private Bundle bundle;

    private ImageAdapter imageAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        banner.setBannerGalleryEffect(50, 10);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(renRenGoodsModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(renRenGoodsModel);
        });
        parentFl.setOnClickListener(v -> {
            productClick(renRenGoodsModel);
        });
        hotFl.setOnClickListener(v -> {
            Activity activity = getActivity();
            if (activity instanceof MainActivity){
                ((MainActivity) activity).setCurrent();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(RenRenGoodsModel model) {
        if (model == null){
            return;
        }
        phone = SPOpenUtil.getString("phone");
        NewApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<RenRenBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(RenRenBaseModel renRenBaseModel) {
                        toWeb(model);
                    }
                });
    }

    private static int[] getNotchSizeHw(Activity activity) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = activity.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (Exception e) {
        } finally {
            return ret;
        }
    }

    public void productList() {
        mobileType = SPOpenUtil.getInt("mobileType");
        NewApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<RenRenBaseModel<List<RenRenGoodsModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenMethodUtil.showErrorInfo(getActivity(), error);
                        if (imageAdapter == null) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(RenRenBaseModel<List<RenRenGoodsModel>> renRenBaseModel) {
                        setRefreshing.setRefreshing(false);
                        if (renRenBaseModel != null) {
                            if (renRenBaseModel.getCode() == 200 && renRenBaseModel.getData() != null) {
                                if (renRenBaseModel.getData() != null && renRenBaseModel.getData().size() > 0) {
                                    renRenGoodsModel = renRenBaseModel.getData().get(0);
                                    initBannerAdapter(renRenBaseModel.getData());
                                } else {
                                    if (imageAdapter == null) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (imageAdapter == null) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (imageAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    /**
     * 判断xiaomi是否有刘海屏
     * https://dev.mi.com/console/doc/detail?pId=1293
     *
     * @param activity
     * @return
     */
    private static boolean hasNotchXiaoMi(Activity activity) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("getInt", String.class, int.class);
            return (int) (get.invoke(c, "ro.miui.notch", 0)) == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void initBannerAdapter(List<RenRenGoodsModel> data) {
        imageAdapter = null;
        imageAdapter = new ImageAdapter(data, getActivity());
        imageAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapter);
    }

    public void toWeb(RenRenGoodsModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenMethodUtil.jumpPage(getActivity(), JumpH5Activity.class, bundle);
        }
    }

    /**
     * 判断华为是否有刘海屏
     * https://devcenter-test.huawei.com/consumer/cn/devservice/doc/50114
     *
     * @param activity
     * @return
     */
    private static boolean hasNotchHw(Activity activity) {

        try {
            ClassLoader cl = activity.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            return (boolean) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            return false;
        }
    }

}
