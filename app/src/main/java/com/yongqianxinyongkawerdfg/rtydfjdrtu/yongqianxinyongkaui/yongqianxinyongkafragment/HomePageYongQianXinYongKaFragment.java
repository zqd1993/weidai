package com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.yongqianxinyongkafragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yongqianxinyongkawerdfg.rtydfjdrtu.R;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaadapter.GoodsItemAdapterYongQianXinYongKa;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaGoodsModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.WebViewYongQianXinYongKaActivity;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.mvp.XFragment;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkapresent.YongQianXinYongKaHomePagePresent;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.router.Router;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class HomePageYongQianXinYongKaFragment extends XFragment<YongQianXinYongKaHomePagePresent> {

    @BindView(R.id.product_bg)
    View productBg;
    @BindView(R.id.home_page_bg)
    View homePageBg;
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.top_layout)
    View topLayout;
    @BindView(R.id.click_view_1)
    View click_view_1;
    @BindView(R.id.top_img)
    public ImageView topImg;

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

    private Bundle webBundle;
    public GoodsItemAdapterYongQianXinYongKa goodsItemAdapterYongQianXinYongKa;
    public YongQianXinYongKaGoodsModel yongQianXinYongKaGoodsModel, topYongQianXinYongKaGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
//        getP().productList();
//        ToastUtil.showShort("API_BASE_URL = " + SharedPreferencesUtilis.getStringFromPref("API_BASE_URL") +
//                "API_BASE_URL_1 = " + Api.API_BASE_URL);
        getP().aindex();
        swipeRefreshLayout.setOnRefreshListener(() -> {
//            getP().productList();
            getP().aindex();
        });
//        productBg.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        swipeRefreshLayout.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        topLayout.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        click_view.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
//        click_view_1.setOnClickListener(v -> {
//            productClick(goodsModel);
//        });
        topImg.setOnClickListener(v -> {
            productClick(topYongQianXinYongKaGoodsModel);
        });
    }

    public float wertdfg(String imageFile) {
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

    public Bitmap aertdfhd(Context context, Uri uri, int w, int h) {
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_xin_yong_page;
    }

    @Override
    public YongQianXinYongKaHomePagePresent newP() {
        return new YongQianXinYongKaHomePagePresent();
    }

    private void productClick(YongQianXinYongKaGoodsModel model) {
        jumpWebActivity(model);
    }

    public void jumpWebActivity(YongQianXinYongKaGoodsModel model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            Router.newIntent(getActivity())
                    .to(WebViewYongQianXinYongKaActivity.class)
                    .data(webBundle)
                    .launch();
        }
    }

    public float rtyfghfxgh(String imageFile) {
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

    public Bitmap bbdhyty(Context context, Uri uri, int w, int h) {
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

    public void initGoodsItemAdapter(List<YongQianXinYongKaGoodsModel> mData) {
        if (goodsItemAdapterYongQianXinYongKa == null) {
            goodsItemAdapterYongQianXinYongKa = new GoodsItemAdapterYongQianXinYongKa(getActivity());
            goodsItemAdapterYongQianXinYongKa.setRecItemClick(new RecyclerItemCallback<YongQianXinYongKaGoodsModel, GoodsItemAdapterYongQianXinYongKa.ViewHolder>() {
                @Override
                public void onItemClick(int position, YongQianXinYongKaGoodsModel model, int tag, GoodsItemAdapterYongQianXinYongKa.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    productClick(model);
                }
            });
            goodsItemAdapterYongQianXinYongKa.setHasStableIds(true);
            goodsItemAdapterYongQianXinYongKa.setData(mData);
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(goodsItemAdapterYongQianXinYongKa);
        } else {
            goodsItemAdapterYongQianXinYongKa.setData(mData);
        }
    }

    public void setModel(YongQianXinYongKaGoodsModel yongQianXinYongKaGoodsModel) {
        this.yongQianXinYongKaGoodsModel = yongQianXinYongKaGoodsModel;
    }

    public float werzdsg(String imageFile) {
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

    public Bitmap rtysfd(Context context, Uri uri, int w, int h) {
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
}
