package com.qznagsdnfdkdjdf.cvpdfms.ppppdkmiaoxia;

import android.view.View;

import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.BaseRespModelDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.DKMiaoXiaOpGoodsModel;
import com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.fragmentdkmiaoxia.HomePageFragmentDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.SharedPreferencesUtilisDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.DKMiaoXiaOpStaticUtil;
import com.qznagsdnfdkdjdf.cvpdfms.mvp.XPresent;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.ApiDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.ApiSubscriber;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.NetError;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.XApi;

import java.util.List;


public class HomePagePresentDKMiaoXiaOp extends XPresent<HomePageFragmentDKMiaoXiaOp> {

    private int mobileType;

    private String phone;

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    public void productList() {
            mobileType = SharedPreferencesUtilisDKMiaoXiaOp.getIntFromPref("mobileType");
            ApiDKMiaoXiaOp.getGankService().productList(mobileType)
                    .compose(XApi.<BaseRespModelDKMiaoXiaOp<List<DKMiaoXiaOpGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelDKMiaoXiaOp<List<DKMiaoXiaOpGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelDKMiaoXiaOp<List<DKMiaoXiaOpGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDKMiaoXiaOp<List<DKMiaoXiaOpGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().goodsItemAdapterDKMiaoXiaOp == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            DKMiaoXiaOpStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDKMiaoXiaOp<List<DKMiaoXiaOpGoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    } else {
                                        if (getV().goodsItemAdapterDKMiaoXiaOp == null) {
                                            getV().noDataFl.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (getV().goodsItemAdapterDKMiaoXiaOp == null) {
                                        getV().noDataFl.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (getV().goodsItemAdapterDKMiaoXiaOp == null) {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
    }

    public void productClick(DKMiaoXiaOpGoodsModel model) {
            phone = SharedPreferencesUtilisDKMiaoXiaOp.getStringFromPref("phone");
            ApiDKMiaoXiaOp.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModelDKMiaoXiaOp>getApiTransformer())
                    .compose(XApi.<BaseRespModelDKMiaoXiaOp>getScheduler())
                    .compose(getV().<BaseRespModelDKMiaoXiaOp>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelDKMiaoXiaOp>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            DKMiaoXiaOpStaticUtil.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelDKMiaoXiaOp gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
    }

    public static String vzdfgrg(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double ytjfh(Object o) {

        return toDouble(o, 0);
    }

    public static double eawtrfd(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long ngsdhft(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

}
