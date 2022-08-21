package com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.yongqianxinyongkafragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yongqianxinyongkawerdfg.rtydfjdrtu.R;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaadapter.GoodsItemAdapterYongQianXinYongKa;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaGoodsModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.mvp.XFragment;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkapresent.ProductYongQianXinYongKaPresent;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.router.Router;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.WebViewYongQianXinYongKaActivity;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class ProductYongQianXinYongKaFragment extends XFragment<ProductYongQianXinYongKaPresent> {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.parent_ll)
    View parentLl;

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
    private YongQianXinYongKaGoodsModel yongQianXinYongKaGoodsModel;

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().productList();
        swipeRefreshLayout.setOnRefreshListener(() -> getP().productList());
        parentLl.setOnClickListener(v -> {
            productClick(yongQianXinYongKaGoodsModel);
        });
    }

    public float aertdhf(String imageFile) {
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

    public Bitmap reatzdfh(Context context, Uri uri, int w, int h) {
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
        return R.layout.fragment_product_xin_yong;
    }

    @Override
    public ProductYongQianXinYongKaPresent newP() {
        return new ProductYongQianXinYongKaPresent();
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

    public float iyujfgjg(String imageFile) {
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

    public Bitmap bdfhxfytr(Context context, Uri uri, int w, int h) {
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

    public float wergdzf(String imageFile) {
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

    public Bitmap uyhfgjhfg(Context context, Uri uri, int w, int h) {
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

    public void setModel(YongQianXinYongKaGoodsModel yongQianXinYongKaGoodsModel) {
        this.yongQianXinYongKaGoodsModel = yongQianXinYongKaGoodsModel;
    }
}
