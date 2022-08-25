package com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqif;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.meijiefenqifjsdkqwj.pfdioewjnsd.R;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqia.ImageMeiJienFenSsdfQiAdapter;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqia.JumpH5ActivityMeiJienFenSsdfQi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiapi.HttpMeiJienFenSsdfQiApi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.BaseMeiJienFenSsdfQiModel;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.ProductMeiJienFenSsdfQiModel;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.mvp.XActivity;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.mvp.XFragment;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.net.ApiSubscriber;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.net.NetError;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.net.XApi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiu.MeiJienFenSsdfQiOpenUtil;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiu.PreferencesMeiJienFenSsdfQiOpenUtil;
import com.youth.banner.Banner;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;

public class MainMeiJienFenSsdfQiFragment extends XFragment {

    private int mobileType;

    private String phone;

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
    @BindView(R.id.banner_fl)
    View banner_fl;
    @BindView(R.id.click_fl)
    View click_fl;

    private ProductMeiJienFenSsdfQiModel productMeiJienFenSsdfQiModel;

    private Bundle bundle;

    private ImageMeiJienFenSsdfQiAdapter imageMeiJienFenSsdfQiAdapter;

    private String[] msg = {"恭喜187****5758用户领取87000元额度", "恭喜138****5666用户领取36000元额度", "恭喜199****5009用户领取49000元额度",
            "恭喜137****6699用户领取69000元额度", "恭喜131****8889用户领取18000元额度", "恭喜177****8899用户领取26000元额度",
            "恭喜155****6789用户领取58000元额度", "恭喜166****5335用户领取29000元额度", "恭喜163****2299用户领取92000元额度",
            "恭喜130****8866用户领取86000元额度"};

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void urshfdhh(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void bdszyhtrh(Context context) {
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
    public void initData(Bundle savedInstanceState) {
        banner_fl.setVisibility(View.VISIBLE);
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

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void jfhstyrty(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void ertgfxhfh(Context context) {
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

    private void initBannerAdapter(List<ProductMeiJienFenSsdfQiModel> data) {
        imageMeiJienFenSsdfQiAdapter = null;
        imageMeiJienFenSsdfQiAdapter = new ImageMeiJienFenSsdfQiAdapter(data);
        imageMeiJienFenSsdfQiAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageMeiJienFenSsdfQiAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_mei_fen_qi;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void werghgh(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void irtyhsfgj(Context context) {
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

    public void productClick(ProductMeiJienFenSsdfQiModel model) {
            if (model == null) {
                return;
            }
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

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void ewthsdfh(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void dgjsrtyfgh(Context context) {
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
                            if (imageMeiJienFenSsdfQiAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseMeiJienFenSsdfQiModel<List<ProductMeiJienFenSsdfQiModel>> baseMeiJienFenSsdfQiModel) {
                            setRefreshing.setRefreshing(false);
                            if (baseMeiJienFenSsdfQiModel != null) {
                                if (baseMeiJienFenSsdfQiModel.getCode() == 200 && baseMeiJienFenSsdfQiModel.getData() != null) {
                                    if (baseMeiJienFenSsdfQiModel.getData() != null && baseMeiJienFenSsdfQiModel.getData().size() > 0) {
                                        productMeiJienFenSsdfQiModel = baseMeiJienFenSsdfQiModel.getData().get(0);
                                        initBannerAdapter(baseMeiJienFenSsdfQiModel.getData());
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
    public void nbdfjty(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void oyserthfdtj(Context context) {
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
            bundle.putString("biaoti", model.getProductName());
            MeiJienFenSsdfQiOpenUtil.getValue((XActivity) getActivity(), JumpH5ActivityMeiJienFenSsdfQi.class, bundle);
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void dsfgrtury(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void mdfhsrtu(Context context) {
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
}
