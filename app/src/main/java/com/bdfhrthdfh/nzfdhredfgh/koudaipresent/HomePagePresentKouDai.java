package com.bdfhrthdfh.nzfdhredfgh.koudaipresent;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.bdfhrthdfh.nzfdhredfgh.koudaimodel.BaseRespModelKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaimodel.GoodsModelKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaimodel.RequModelKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaiutils.SharedPreferencesUtilisKouDai;
import com.bdfhrthdfh.nzfdhredfgh.koudaiutils.StaticKouDaiUtil;
import com.bdfhrthdfh.nzfdhredfgh.mvp.XPresent;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.KouDaiApi;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.ApiSubscriber;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.NetError;
import com.bdfhrthdfh.nzfdhredfgh.koudainet.XApi;
import com.bdfhrthdfh.nzfdhredfgh.koudaiui.fragment.KouDaiHomePageFragment;

import java.util.List;

import okhttp3.RequestBody;


public class HomePagePresentKouDai extends XPresent<KouDaiHomePageFragment> {

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

    private void fewtdfghfdgh(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
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
    public void bzdrtgrey(EditText mEditText, Context mContext, View v) {
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

    public void aindex() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisKouDai.getStringFromPref("API_BASE_URL"))) {
            token = SharedPreferencesUtilisKouDai.getStringFromPref("token");
            RequModelKouDai model = new RequModelKouDai();
            model.setToken(token);
            RequestBody body = StaticKouDaiUtil.createBody(new Gson().toJson(model));
            KouDaiApi.getGankService().aindex(body)
                    .compose(XApi.<BaseRespModelKouDai<List<GoodsModelKouDai>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelKouDai<List<GoodsModelKouDai>>>getScheduler())
                    .compose(getV().<BaseRespModelKouDai<List<GoodsModelKouDai>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelKouDai<List<GoodsModelKouDai>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
//                        StaticUtil.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelKouDai<List<GoodsModelKouDai>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {

                            }
                            if (gankResults != null) {
                                if (gankResults.getCode() == 0) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    }
                                    if (gankResults.getTop() != null) {
                                        if (!TextUtils.isEmpty(gankResults.getTop().getImgs())) {
                                            getV().topGoodsModelKouDai = gankResults.getTop();
                                            if (!TextUtils.isEmpty(SharedPreferencesUtilisKouDai.getStringFromPref("API_BASE_URL"))) {
                                                Glide.with(getV()).load(SharedPreferencesUtilisKouDai.getStringFromPref("API_BASE_URL") + gankResults.getTop().getImgs()).into(getV().topImg);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    private void erwtdfhfgh(ViewGroup.LayoutParams layoutParams, int top, int bottom) {
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
    public void vsrgterhgdfh(EditText mEditText, Context mContext, View v) {
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
