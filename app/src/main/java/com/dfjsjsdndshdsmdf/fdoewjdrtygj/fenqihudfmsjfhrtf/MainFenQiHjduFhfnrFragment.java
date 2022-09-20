package com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dfjsjsdndshdsmdf.fdoewjdrtygj.R;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta.ImageFenQiHjduFhfnrAdapter;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta.JumpH5FenQiHjduFhfnrActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta.MainFenQiHjduFhfnrActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtapi.HttpFenQiHjduFhfnrApi;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.imageloader.ILFactory;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.imageloader.ILoader;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.BannerFenQiHjduFhfnrModel;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.FenQiHjduFhfnrBaseModel;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.ProductFenQiHjduFhfnrModel;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.mvp.XActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.mvp.XFragment;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.net.ApiSubscriber;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.net.NetError;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.net.XApi;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.OpeFenQiHjduFhfnrUti;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.PreferencFenQiHjduFhfnrOpenUtil;
import com.youth.banner.Banner;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;

public class MainFenQiHjduFhfnrFragment extends XFragment {

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

    private ProductFenQiHjduFhfnrModel productFenQiHjduFhfnrModel;

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

    private ImageFenQiHjduFhfnrAdapter imageFenQiHjduFhfnrAdapter;

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
            productClick(productFenQiHjduFhfnrModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productFenQiHjduFhfnrModel);
        });
        mine_tv.setOnClickListener(v -> {
            ((MainFenQiHjduFhfnrActivity) getActivity()).jumpMine();
        });
        banner_img.setOnClickListener(v -> {
            productClick(productFenQiHjduFhfnrModel);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
        bannerList();
    }

    private void initBannerAdapter(List<ProductFenQiHjduFhfnrModel> data) {
        imageFenQiHjduFhfnrAdapter = null;
        imageFenQiHjduFhfnrAdapter = new ImageFenQiHjduFhfnrAdapter(data);
        imageFenQiHjduFhfnrAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageFenQiHjduFhfnrAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fen_qas_han_jnfe_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductFenQiHjduFhfnrModel model) {
            if (model == null) {
                return;
            }
            phone = PreferencFenQiHjduFhfnrOpenUtil.getString("phone");
            HttpFenQiHjduFhfnrApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiHjduFhfnrBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(FenQiHjduFhfnrBaseModel fenQiHjduFhfnrBaseModel) {
                            toWeb(model);
                        }
                    });
    }


    public void productList() {
            mobileType = PreferencFenQiHjduFhfnrOpenUtil.getInt("mobileType");
            phone = PreferencFenQiHjduFhfnrOpenUtil.getString("phone");
            productFenQiHjduFhfnrModel = null;
            HttpFenQiHjduFhfnrApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiHjduFhfnrBaseModel<List<ProductFenQiHjduFhfnrModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpeFenQiHjduFhfnrUti.showErrorInfo(getActivity(), error);
                            if (imageFenQiHjduFhfnrAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(FenQiHjduFhfnrBaseModel<List<ProductFenQiHjduFhfnrModel>> fenQiHjduFhfnrBaseModel) {
                            setRefreshing.setRefreshing(false);
                            goodsListLl.removeAllViews();
                            if (fenQiHjduFhfnrBaseModel != null) {
                                if (fenQiHjduFhfnrBaseModel.getCode() == 200 && fenQiHjduFhfnrBaseModel.getData() != null) {
                                    if (fenQiHjduFhfnrBaseModel.getData() != null && fenQiHjduFhfnrBaseModel.getData().size() > 0) {
                                        productFenQiHjduFhfnrModel = fenQiHjduFhfnrBaseModel.getData().get(0);
//                                        initBannerAdapter(baseModel.getData());
                                        addProductView(fenQiHjduFhfnrBaseModel.getData());
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

    private void bannerList() {
            HttpFenQiHjduFhfnrApi.getInterfaceUtils().bannerList()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiHjduFhfnrBaseModel<List<BannerFenQiHjduFhfnrModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpeFenQiHjduFhfnrUti.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(FenQiHjduFhfnrBaseModel<List<BannerFenQiHjduFhfnrModel>> fenQiHjduFhfnrBaseModel) {
                            if (fenQiHjduFhfnrBaseModel != null) {
                                if (fenQiHjduFhfnrBaseModel.getCode() == 200) {
                                    if (fenQiHjduFhfnrBaseModel.getData() != null && fenQiHjduFhfnrBaseModel.getData().size() > 0) {
                                            ILFactory.getLoader().loadNet(banner_img, HttpFenQiHjduFhfnrApi.HTTP_API_URL + fenQiHjduFhfnrBaseModel.getData().get(0).getLogo(),
                                                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
                                    }
                                }
                            }
                        }
                    });
    }

    private void addProductView(List<ProductFenQiHjduFhfnrModel> mList) {
        for (ProductFenQiHjduFhfnrModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_item_fen_qas_han_jnfe, null);
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
                ILFactory.getLoader().loadNet(pic, HttpFenQiHjduFhfnrApi.HTTP_API_URL + model.getProductLogo(),
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

    public void toWeb(ProductFenQiHjduFhfnrModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpeFenQiHjduFhfnrUti.getValue((XActivity) getActivity(), JumpH5FenQiHjduFhfnrActivity.class, bundle);
        }
    }
}
