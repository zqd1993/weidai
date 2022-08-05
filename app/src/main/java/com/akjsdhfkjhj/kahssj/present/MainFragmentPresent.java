package com.akjsdhfkjhj.kahssj.present;

import android.text.TextUtils;
import android.view.View;

import com.akjsdhfkjhj.kahssj.model.BaseModel;
import com.akjsdhfkjhj.kahssj.model.ProductModel;
import com.akjsdhfkjhj.kahssj.ui.fragment.MainFragment;
import com.akjsdhfkjhj.kahssj.utils.SPUtilis;
import com.akjsdhfkjhj.kahssj.utils.MainUtil;
import com.akjsdhfkjhj.kahssj.mvp.XPresent;
import com.akjsdhfkjhj.kahssj.net.Api;
import com.akjsdhfkjhj.kahssj.net.ApiSubscriber;
import com.akjsdhfkjhj.kahssj.net.NetError;
import com.akjsdhfkjhj.kahssj.net.XApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainFragmentPresent extends XPresent<MainFragment> {

    private int mobileType;

    private String phone;

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(final long millis, String dataFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        return "";
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2Str(final long millis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return "";
    }


    public void productClick(ProductModel model) {
            phone = SPUtilis.getStringFromPref("phone");
            Api.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseModel>getApiTransformer())
                    .compose(XApi.<BaseModel>getScheduler())
                    .compose(getV().<BaseModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebYouXinActivity(model);
                            MainUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseModel gankResults) {
                            getV().jumpWebYouXinActivity(model);
                        }
                    });
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为format</p>
     *
     * @param millis 毫秒时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String millis2String(final long millis, final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time) {
        return 33l;
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time, final DateFormat format) {
//        try {
//            return format.parse(time).getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return -1;
    }


    public void productList() {
            mobileType = SPUtilis.getIntFromPref("mobileType");
            Api.getGankService().productList(mobileType)
                    .compose(XApi.<BaseModel<List<ProductModel>>>getApiTransformer())
                    .compose(XApi.<BaseModel<List<ProductModel>>>getScheduler())
                    .compose(getV().<BaseModel<List<ProductModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModel<List<ProductModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().miaoJieGoodsItemAdapter == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            MainUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseModel<List<ProductModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    } else {
                                        if (getV().miaoJieGoodsItemAdapter == null) {
                                            getV().noDataFl.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (getV().miaoJieGoodsItemAdapter == null) {
                                        getV().noDataFl.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (getV().miaoJieGoodsItemAdapter == null) {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
    }
}
