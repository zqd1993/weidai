package com.retdfhxfghj.bdrhrtuyfgj.ui.xinyongfragment;

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

import com.retdfhxfghj.bdrhrtuyfgj.R;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongadapter.XinYongMineAdapter;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.XinYongBaseRespModel;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.CompanyInfoModelXinYong;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongmodel.XinYongMineItemModel;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.ApiSubscriber;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.NetError;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.XApi;
import com.retdfhxfghj.bdrhrtuyfgj.ui.WebViewXinYongActivity;
import com.retdfhxfghj.bdrhrtuyfgj.ui.xinyongactivity.AboutUsActivityXinYong;
import com.retdfhxfghj.bdrhrtuyfgj.ui.xinyongactivity.SettingActivityXinYong;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongutils.SharedPreferencesXinYongUtilis;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongutils.XinYongToastUtil;
import com.retdfhxfghj.bdrhrtuyfgj.xinyongnet.XinYongApi;
import com.retdfhxfghj.bdrhrtuyfgj.router.Router;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.retdfhxfghj.bdrhrtuyfgj.xinyongwidget.XinYongNormalDialog;
import com.retdfhxfghj.bdrhrtuyfgj.mvp.XFragment;
import com.retdfhxfghj.bdrhrtuyfgj.ui.xinyongactivity.CancellationXinYongAccountActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragmentXinYong extends XFragment {

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

    private XinYongMineAdapter mineAdapter;
    private List<XinYongMineItemModel> list;
    private int[] imgRes = {R.drawable.bghnj, R.drawable.vvbfg, R.drawable.rfvnhu, R.drawable.yhgdv, R.drawable.xcvfgtert};
    private String[] tvRes = {"关于我们", "隐私协议", "注册协议", "系统设置", "注销账户"};
    private Bundle bundle;
    private XinYongNormalDialog xinYongNormalDialog;
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
        phone = SharedPreferencesXinYongUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            XinYongMineItemModel model = new XinYongMineItemModel();
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
            XinYongToastUtil.showShort("复制成功");
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
            mineAdapter = new XinYongMineAdapter(getActivity());
            mineAdapter.setData(list);
            mineAdapter.setHasStableIds(true);
            mineAdapter.setRecItemClick(new RecyclerItemCallback<XinYongMineItemModel, XinYongMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, XinYongMineItemModel model, int tag, XinYongMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivityXinYong.class)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", XinYongApi.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewXinYongActivity.class)
                                    .data(bundle)
                                    .launch();
                        case 2:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", XinYongApi.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewXinYongActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 3:
                            Router.newIntent(getActivity())
                                    .to(SettingActivityXinYong.class)
                                    .launch();
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(CancellationXinYongAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
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
        if (!TextUtils.isEmpty(SharedPreferencesXinYongUtilis.getStringFromPref("API_BASE_URL"))) {
            XinYongApi.getGankService().getCompanyInfo()
                    .compose(XApi.<XinYongBaseRespModel<CompanyInfoModelXinYong>>getApiTransformer())
                    .compose(XApi.<XinYongBaseRespModel<CompanyInfoModelXinYong>>getScheduler())
                    .compose(this.<XinYongBaseRespModel<CompanyInfoModelXinYong>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<XinYongBaseRespModel<CompanyInfoModelXinYong>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(XinYongBaseRespModel<CompanyInfoModelXinYong> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedPreferencesXinYongUtilis.saveStringIntoPref("APP_MAIL", mailStr);
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
        if (xinYongNormalDialog != null) {
            xinYongNormalDialog.dismiss();
            xinYongNormalDialog = null;
        }
        super.onDestroy();
    }
}
