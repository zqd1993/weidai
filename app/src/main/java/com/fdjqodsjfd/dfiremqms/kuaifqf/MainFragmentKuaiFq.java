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
import com.fdjqodsjfd.dfiremqms.kuaifqa.ImageKuaiFqAdapter;
import com.fdjqodsjfd.dfiremqms.kuaifqa.KuaiFqJumpH5Activity;
import com.fdjqodsjfd.dfiremqms.kuaifqa.MainActivityKuaiFq;
import com.fdjqodsjfd.dfiremqms.kuaifqapi.HttpApiKuaiFq;
import com.fdjqodsjfd.dfiremqms.imageloader.ILFactory;
import com.fdjqodsjfd.dfiremqms.imageloader.ILoader;
import com.fdjqodsjfd.dfiremqms.kuaifqm.BannerKuaiFqModel;
import com.fdjqodsjfd.dfiremqms.kuaifqm.KuaiFqBaseModel;
import com.fdjqodsjfd.dfiremqms.kuaifqm.ProductKuaiFqModel;
import com.fdjqodsjfd.dfiremqms.mvp.XActivity;
import com.fdjqodsjfd.dfiremqms.mvp.XFragment;
import com.fdjqodsjfd.dfiremqms.net.ApiSubscriber;
import com.fdjqodsjfd.dfiremqms.net.NetError;
import com.fdjqodsjfd.dfiremqms.net.XApi;
import com.fdjqodsjfd.dfiremqms.kuaifqu.OpeKuaiFqnUti;
import com.fdjqodsjfd.dfiremqms.kuaifqu.PreferencKuaiFqOpenUtil;
import com.youth.banner.Banner;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;

public class MainFragmentKuaiFq extends XFragment {

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
    @BindView(R.id.goods_banner)
    Banner banner;
    @BindView(R.id.banner_fl)
    View banner_fl;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.banner_img)
    ImageView banner_img;
    @BindView(R.id.mine_tv)
    TextView mine_tv;

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

    private ImageKuaiFqAdapter imageKuaiFqAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
//        banner_fl.setVisibility(View.VISIBLE);
        goodsListLl.setVisibility(View.VISIBLE);
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
        mine_tv.setOnClickListener(v -> {
            ((MainActivityKuaiFq) getActivity()).jumpMine();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
        bannerList();
    }

    private void initBannerAdapter(List<ProductKuaiFqModel> data) {
        imageKuaiFqAdapter = null;
        imageKuaiFqAdapter = new ImageKuaiFqAdapter(data);
        imageKuaiFqAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageKuaiFqAdapter);
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
        if (!TextUtils.isEmpty(PreferencKuaiFqOpenUtil.getString("HTTP_API_URL"))) {
            if (model == null) {
                return;
            }
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
        if (!TextUtils.isEmpty(PreferencKuaiFqOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = PreferencKuaiFqOpenUtil.getInt("mobileType");
            phone = PreferencKuaiFqOpenUtil.getString("phone");
            productKuaiFqModel = null;
            HttpApiKuaiFq.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiFqBaseModel<List<ProductKuaiFqModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpeKuaiFqnUti.showErrorInfo(getActivity(), error);
                            if (imageKuaiFqAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(KuaiFqBaseModel<List<ProductKuaiFqModel>> kuaiFqBaseModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (kuaiFqBaseModel != null) {
                                if (kuaiFqBaseModel.getCode() == 200 && kuaiFqBaseModel.getData() != null) {
                                    if (kuaiFqBaseModel.getData() != null && kuaiFqBaseModel.getData().size() > 0) {
                                        productKuaiFqModel = kuaiFqBaseModel.getData().get(0);
//                                        initBannerAdapter(baseModel.getData());
                                        addProductView(kuaiFqBaseModel.getData());
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
    }

    private void bannerList() {
        if (!TextUtils.isEmpty(PreferencKuaiFqOpenUtil.getString("HTTP_API_URL"))) {
            HttpApiKuaiFq.getInterfaceUtils().bannerList()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiFqBaseModel<List<BannerKuaiFqModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpeKuaiFqnUti.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(KuaiFqBaseModel<List<BannerKuaiFqModel>> kuaiFqBaseModel) {
                            if (kuaiFqBaseModel != null) {
                                if (kuaiFqBaseModel.getCode() == 200) {
                                    if (kuaiFqBaseModel.getData() != null && kuaiFqBaseModel.getData().size() > 0) {
                                        if (!TextUtils.isEmpty(PreferencKuaiFqOpenUtil.getString("HTTP_API_URL"))) {
                                            ILFactory.getLoader().loadNet(banner_img, PreferencKuaiFqOpenUtil.getString("HTTP_API_URL") + kuaiFqBaseModel.getData().get(0).getLogo(),
                                                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    private void addProductView(List<ProductKuaiFqModel> mList) {
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
            shijian_tv.setText(model.getDes());
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
            if (!TextUtils.isEmpty(PreferencKuaiFqOpenUtil.getString("HTTP_API_URL"))) {
                ILFactory.getLoader().loadNet(pic, PreferencKuaiFqOpenUtil.getString("HTTP_API_URL") + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            }
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
            bundle.putString("biaoti", model.getProductName());
            OpeKuaiFqnUti.getValue((XActivity) getActivity(), KuaiFqJumpH5Activity.class, bundle);
        }
    }
}
