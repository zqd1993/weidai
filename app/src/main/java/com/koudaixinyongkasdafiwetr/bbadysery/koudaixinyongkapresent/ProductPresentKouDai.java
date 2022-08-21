package com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkapresent;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkamodel.BaseRespModelKouDai;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkamodel.GoodsModelKouDai;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkamodel.RequModelKouDai;
import com.koudaixinyongkasdafiwetr.bbadysery.mvp.XPresent;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkanet.KouDaiApi;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkanet.ApiSubscriber;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkanet.NetError;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkanet.XApi;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkaui.koudaixinyongkafragment.ProductFragmentKouDai;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkautils.SharedPreferencesUtilisKouDai;
import com.koudaixinyongkasdafiwetr.bbadysery.koudaixinyongkautils.StaticKouDaiUtil;

import java.util.List;

import okhttp3.RequestBody;


public class ProductPresentKouDai extends XPresent<ProductFragmentKouDai> {

    private int mobileType;

    private String phone, token;

    private void resetViewRl(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams temp = (LinearLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void openKeybord(EditText mEditText, Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im3 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im4 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void productList() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisKouDai.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilisKouDai.getStringFromPref("token");
            RequModelKouDai model = new RequModelKouDai();
            model.setToken(token);
            RequestBody body = StaticKouDaiUtil.createBody(new Gson().toJson(model));
            KouDaiApi.getGankService().productList(body)
                    .compose(XApi.<BaseRespModelKouDai<List<GoodsModelKouDai>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelKouDai<List<GoodsModelKouDai>>>getScheduler())
                    .compose(getV().<BaseRespModelKouDai<List<GoodsModelKouDai>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKouDai<List<GoodsModelKouDai>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            StaticKouDaiUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelKouDai<List<GoodsModelKouDai>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                }
                            }
                        }
                    });
        }
    }

    private void yrghfdh(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams temp = (LinearLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void werdfhdh(EditText mEditText, Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im3 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im4 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void productClick(GoodsModelKouDai model) {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisKouDai.getStringFromPref("API_BASE_URL"))) {
            phone = SharedPreferencesUtilisKouDai.getStringFromPref("phone");
            KouDaiApi.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModelKouDai>getApiTransformer())
                    .compose(XApi.<BaseRespModelKouDai>getScheduler())
                    .compose(getV().<BaseRespModelKouDai>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKouDai>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticKouDaiUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelKouDai gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

    private void hhawetdfh(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams temp = (LinearLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams temp = (RelativeLayout.LayoutParams) layoutParams;
            temp.bottomMargin = bottom;
            temp.topMargin = top;
        }
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void wertdfgdfh(EditText mEditText, Context mContext, View v) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im3 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        InputMethodManager im4 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive()) {
            im.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
