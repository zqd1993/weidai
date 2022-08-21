package com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.yongqianxinyongkafragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yongqianxinyongkawerdfg.rtydfjdrtu.R;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaadapter.YongQianXinYongKaMineAdapter;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaBaseRespModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.CompanyInfoModelYongQianXinYongKa;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkamodel.YongQianXinYongKaMineItemModel;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.ApiSubscriber;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.NetError;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.XApi;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.WebViewYongQianXinYongKaActivity;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.yongqianxinyongkaactivity.AboutUsActivityYongQianXinYongKa;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.yongqianxinyongkaactivity.CancellationYongQianXinYongKaAccountActivity;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkaui.yongqianxinyongkaactivity.SettingActivityYongQianXinYongKa;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkautils.SharedPreferencesYongQianXinYongKaUtilis;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkautils.YongQianXinYongKaToastUtil;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkanet.YongQianXinYongKaApi;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.router.Router;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.yongqianxinyongkawerdfg.rtydfjdrtu.yongqianxinyongkawidget.YongQianXinYongKaNormalDialog;
import com.yongqianxinyongkawerdfg.rtydfjdrtu.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragmentYongQianXinYongKa extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mail_tv)
    TextView mail_tv;
    @BindView(R.id.mail_ll)
    View mail_ll;

    private YongQianXinYongKaMineAdapter mineAdapter;
    private List<YongQianXinYongKaMineItemModel> list;
    private int[] imgRes = {R.drawable.eryxfhfj, R.drawable.wegdfhfxtu, R.drawable.fgntzeyhfh, R.drawable.vbnxzztzd, R.drawable.erfgjhxfgj};
    private String[] tvRes = {"关于我们", "隐私协议", "注册协议", "系统设置", "注销账户"};
    private Bundle bundle;
    private YongQianXinYongKaNormalDialog yongQianXinYongKaNormalDialog;
    private String mailStr = "", phone = "";

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

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        getCompanyInfo();
        phone = SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            YongQianXinYongKaMineItemModel model = new YongQianXinYongKaMineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        mail_ll.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            YongQianXinYongKaToastUtil.showShort("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_xin_yong_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    public float urtsyhfh(String imageFile) {
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

    public Bitmap bbdyrt(Context context, Uri uri, int w, int h) {
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

    private void initAdapter() {
        if (mineAdapter == null) {
            mineAdapter = new YongQianXinYongKaMineAdapter(getActivity());
            mineAdapter.setData(list);
            mineAdapter.setHasStableIds(true);
            mineAdapter.setRecItemClick(new RecyclerItemCallback<YongQianXinYongKaMineItemModel, YongQianXinYongKaMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, YongQianXinYongKaMineItemModel model, int tag, YongQianXinYongKaMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivityYongQianXinYongKa.class)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", YongQianXinYongKaApi.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewYongQianXinYongKaActivity.class)
                                    .data(bundle)
                                    .launch();
                        case 2:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", YongQianXinYongKaApi.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewYongQianXinYongKaActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 3:
                            Router.newIntent(getActivity())
                                    .to(SettingActivityYongQianXinYongKa.class)
                                    .launch();
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(CancellationYongQianXinYongKaAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter);
        }
    }

    public float ityhjfgj(String imageFile) {
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

    public Bitmap werdfhfj(Context context, Uri uri, int w, int h) {
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

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesYongQianXinYongKaUtilis.getStringFromPref("API_BASE_URL"))) {
            YongQianXinYongKaApi.getGankService().getCompanyInfo()
                    .compose(XApi.<YongQianXinYongKaBaseRespModel<CompanyInfoModelYongQianXinYongKa>>getApiTransformer())
                    .compose(XApi.<YongQianXinYongKaBaseRespModel<CompanyInfoModelYongQianXinYongKa>>getScheduler())
                    .compose(this.<YongQianXinYongKaBaseRespModel<CompanyInfoModelYongQianXinYongKa>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<YongQianXinYongKaBaseRespModel<CompanyInfoModelYongQianXinYongKa>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(YongQianXinYongKaBaseRespModel<CompanyInfoModelYongQianXinYongKa> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedPreferencesYongQianXinYongKaUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    public float ioyshfg(String imageFile) {
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

    public Bitmap ruyhfgjh(Context context, Uri uri, int w, int h) {
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
    public void onDestroy() {
        if (yongQianXinYongKaNormalDialog != null) {
            yongQianXinYongKaNormalDialog.dismiss();
            yongQianXinYongKaNormalDialog = null;
        }
        super.onDestroy();
    }
}
