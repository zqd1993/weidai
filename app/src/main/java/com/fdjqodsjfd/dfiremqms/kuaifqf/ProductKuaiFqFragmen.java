package com.fdjqodsjfd.dfiremqms.kuaifqf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fdjqodsjfd.dfiremqms.R;
import com.fdjqodsjfd.dfiremqms.kuaifqa.KuaiFqJumpH5Activity;
import com.fdjqodsjfd.dfiremqms.kuaifqapi.HttpApiKuaiFq;
import com.fdjqodsjfd.dfiremqms.imageloader.ILFactory;
import com.fdjqodsjfd.dfiremqms.imageloader.ILoader;
import com.fdjqodsjfd.dfiremqms.kuaifqm.KuaiFqBaseModel;
import com.fdjqodsjfd.dfiremqms.kuaifqm.ProductKuaiFqModel;
import com.fdjqodsjfd.dfiremqms.mvp.XActivity;
import com.fdjqodsjfd.dfiremqms.mvp.XFragment;
import com.fdjqodsjfd.dfiremqms.net.ApiSubscriber;
import com.fdjqodsjfd.dfiremqms.net.NetError;
import com.fdjqodsjfd.dfiremqms.net.XApi;
import com.fdjqodsjfd.dfiremqms.kuaifqu.OpeKuaiFqnUti;
import com.fdjqodsjfd.dfiremqms.kuaifqu.PreferencKuaiFqOpenUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;

public class ProductKuaiFqFragmen extends XFragment {

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
    private ProductKuaiFqModel productKuaiFqModel;

    public float getRatio(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap loadFile2MemoryVersion10(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

    private Bundle bundle;

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
            productClick(productKuaiFqModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productKuaiFqModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productKuaiFqModel);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kuai_fq_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductKuaiFqModel model) {
            if (model != null) {
                phone = PreferencKuaiFqOpenUtil.getString("phone");
                HttpApiKuaiFq.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<KuaiFqBaseModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(KuaiFqBaseModel kuaiFqBaseModel) {
                                toWeb(model);
                            }
                        });
            }
    }


    public void productList() {
            mobileType = PreferencKuaiFqOpenUtil.getInt("mobileType");
            phone = PreferencKuaiFqOpenUtil.getString("phone");
            HttpApiKuaiFq.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiFqBaseModel<List<ProductKuaiFqModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpeKuaiFqnUti.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(KuaiFqBaseModel<List<ProductKuaiFqModel>> kuaiFqBaseModel) {
                            setRefreshing.setRefreshing(false);
                            if (kuaiFqBaseModel != null) {
                                if (kuaiFqBaseModel.getCode() == 200 && kuaiFqBaseModel.getData() != null) {
                                    if (kuaiFqBaseModel.getData() != null && kuaiFqBaseModel.getData().size() > 0) {
                                        productKuaiFqModel = kuaiFqBaseModel.getData().get(0);
                                        addProductView(kuaiFqBaseModel.getData());
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

    public float awetdfhfgjh(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap eartgfdhfgjh(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

    private void addProductView(List<ProductKuaiFqModel> mList) {
        goodsListLl.removeAllViews();
        for (ProductKuaiFqModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_kuai_fq, null);
            ImageView pic = view.findViewById(R.id.product_img);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            TextView shijian_tv = view.findViewById(R.id.shijian_tv);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            shijian_tv.setText(model.getDes() + "个月");
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
                ILFactory.getLoader().loadNet(pic, HttpApiKuaiFq.HTTP_API_URL + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
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

    public void toWeb(ProductKuaiFqModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpeKuaiFqnUti.getValue((XActivity) getActivity(), KuaiFqJumpH5Activity.class, bundle);
        }
    }
}
