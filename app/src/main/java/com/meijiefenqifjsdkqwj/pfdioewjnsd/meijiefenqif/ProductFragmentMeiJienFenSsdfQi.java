package com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqif;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.meijiefenqifjsdkqwj.pfdioewjnsd.R;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqia.JumpH5ActivityMeiJienFenSsdfQi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiapi.HttpMeiJienFenSsdfQiApi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.imageloader.ILFactory;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.imageloader.ILoader;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.BaseMeiJienFenSsdfQiModel;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.ProductMeiJienFenSsdfQiModel;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.mvp.XActivity;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.mvp.XFragment;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.net.ApiSubscriber;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.net.NetError;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.net.XApi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiu.MeiJienFenSsdfQiOpenUtil;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiu.PreferencesMeiJienFenSsdfQiOpenUtil;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;

public class ProductFragmentMeiJienFenSsdfQi extends XFragment {

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
    @BindView(R.id.click_fl)
    View click_fl;

    private ProductMeiJienFenSsdfQiModel productMeiJienFenSsdfQiModel;

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void fixInputMethodMemoryLeak(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Bundle bundle;

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_img.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productMeiJienFenSsdfQiModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productMeiJienFenSsdfQiModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productMeiJienFenSsdfQiModel);
        });
        noDataTv.setOnClickListener(v -> {
            productList();
        });
        click_fl.setOnClickListener(v -> {
            productClick(productMeiJienFenSsdfQiModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_mei_fen_qi;
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void pujdtgjghj(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void urthdfj(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductMeiJienFenSsdfQiModel model) {
            if (model != null) {
                phone = PreferencesMeiJienFenSsdfQiOpenUtil.getString("phone");
                HttpMeiJienFenSsdfQiApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseMeiJienFenSsdfQiModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseMeiJienFenSsdfQiModel baseMeiJienFenSsdfQiModel) {
                                toWeb(model);
                            }
                        });
            }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void erthxfgj(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void dtndtyujd(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void productList() {
            mobileType = PreferencesMeiJienFenSsdfQiOpenUtil.getInt("mobileType");
            phone = PreferencesMeiJienFenSsdfQiOpenUtil.getString("phone");
            productMeiJienFenSsdfQiModel = null;
            HttpMeiJienFenSsdfQiApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseMeiJienFenSsdfQiModel<List<ProductMeiJienFenSsdfQiModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            MeiJienFenSsdfQiOpenUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseMeiJienFenSsdfQiModel<List<ProductMeiJienFenSsdfQiModel>> baseMeiJienFenSsdfQiModel) {
                            goodsListLl.removeAllViews();
                            setRefreshing.setRefreshing(false);
                            if (baseMeiJienFenSsdfQiModel != null) {
                                if (baseMeiJienFenSsdfQiModel.getCode() == 200 && baseMeiJienFenSsdfQiModel.getData() != null) {
                                    if (baseMeiJienFenSsdfQiModel.getData() != null && baseMeiJienFenSsdfQiModel.getData().size() > 0) {
                                        productMeiJienFenSsdfQiModel = baseMeiJienFenSsdfQiModel.getData().get(0);
                                        addProductView(baseMeiJienFenSsdfQiModel.getData());
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
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void jergzdfhh(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void perxfngu(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addProductView(List<ProductMeiJienFenSsdfQiModel> mList) {
        for (ProductMeiJienFenSsdfQiModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_mei_jie_fen_qi_product_item, null);
            ImageView pic = view.findViewById(R.id.goods_pic);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            View yjsq_sl = view.findViewById(R.id.yjsq_sl);
                ILFactory.getLoader().loadNet(pic, HttpMeiJienFenSsdfQiApi.HTTP_API_URL + model.getProductLogo(),
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
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void sgrtuyhf(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void yhsrtyf(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void toWeb(ProductMeiJienFenSsdfQiModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            MeiJienFenSsdfQiOpenUtil.getValue((XActivity) getActivity(), JumpH5ActivityMeiJienFenSsdfQi.class, bundle);
        }
    }
}
