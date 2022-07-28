package com.terdfhdfu.wefgvzdhtru.xianjinui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.terdfhdfu.wefgvzdhtru.R;
import com.terdfhdfu.wefgvzdhtru.adapterxianjin.XianJinFenQiMineAdapter;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.BaseRespModelXianJinFenQi;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.XianJinFenQiCompanyInfoModel;
import com.terdfhdfu.wefgvzdhtru.xianjinmodel.MineItemXianJinFenQiModel;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.ApiSubscriber;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.NetError;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.XApi;
import com.terdfhdfu.wefgvzdhtru.xianjinui.WebViewActivityXianJinFenQi;
import com.terdfhdfu.wefgvzdhtru.xianjinui.activity.XianJinFenQiSettingActivity;
import com.terdfhdfu.wefgvzdhtru.utilsxianjin.XianJinFenQiSharedPreferencesUtilis;
import com.terdfhdfu.wefgvzdhtru.utilsxianjin.XianJinFenQiToastUtil;
import com.terdfhdfu.wefgvzdhtru.xianjinnet.XianJinFenQiApi;
import com.terdfhdfu.wefgvzdhtru.router.Router;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.terdfhdfu.wefgvzdhtru.xinajinwidget.NormalXianJinFenQiDialog;
import com.terdfhdfu.wefgvzdhtru.mvp.XFragment;
import com.terdfhdfu.wefgvzdhtru.xianjinui.activity.AboutUsXianJinFenQiActivity;
import com.terdfhdfu.wefgvzdhtru.xianjinui.activity.CancellationAccountActivityXianJinFenQi;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class XianJinFenQiMineFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.zhuxiao_tv)
    View zhuxiao_tv;
    @BindView(R.id.zcxy_sl)
    View zcxy_sl;
    @BindView(R.id.yszc_sl)
    View yszc_sl;

    private XianJinFenQiMineAdapter xianJinFenQiMineAdapter;
    private List<MineItemXianJinFenQiModel> list;
    private int[] imgRes = {R.drawable.wd_icon_gywm, R.drawable.wd_icon_tsyx, R.drawable.wd_icon_gbgxhts};
    private String[] tvRes = {"关于我们", "投诉邮箱", "系统设置"};
    private Bundle bundle;
    private NormalXianJinFenQiDialog normalXianJinFenQiDialog;
    private String mailStr = "", phone = "";

    public Bitmap createBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap createBitmapOnHide(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap createBitmapTransparentBg(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] createBitmapOnHideToBytes(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        getCompanyInfo();
        phone = XianJinFenQiSharedPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 3; i++) {
            MineItemXianJinFenQiModel model = new MineItemXianJinFenQiModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        zhuxiao_tv.setOnClickListener(v -> {
            Router.newIntent(getActivity())
                    .to(CancellationAccountActivityXianJinFenQi.class)
                    .launch();
        });
        zcxy_sl.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 1);
            bundle.putString("url", XianJinFenQiApi.getZc());
            Router.newIntent(getActivity())
                    .to(WebViewActivityXianJinFenQi.class)
                    .data(bundle)
                    .launch();
        });
        yszc_sl.setOnClickListener(v -> {
            bundle = new Bundle();
            bundle.putInt("tag", 2);
            bundle.putString("url", XianJinFenQiApi.getYs());
            Router.newIntent(getActivity())
                    .to(WebViewActivityXianJinFenQi.class)
                    .data(bundle)
                    .launch();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_xian_jin_mine;
    }

    public Bitmap pidhjydu(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap zcxvty(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap nmfiouyju(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] ertnxftruy(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (xianJinFenQiMineAdapter == null) {
            xianJinFenQiMineAdapter = new XianJinFenQiMineAdapter(getActivity());
            xianJinFenQiMineAdapter.setData(list);
            xianJinFenQiMineAdapter.setHasStableIds(true);
            xianJinFenQiMineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemXianJinFenQiModel, XianJinFenQiMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemXianJinFenQiModel model, int tag, XianJinFenQiMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    if (tag == 2) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText(null, mailStr);
                        clipboard.setPrimaryClip(clipData);
                        XianJinFenQiToastUtil.showShort("复制成功");
                        return;
                    }
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutUsXianJinFenQiActivity.class)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(XianJinFenQiSettingActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(xianJinFenQiMineAdapter);
        }
    }

    public Bitmap mghjkytidjh(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap dfgrtuyjgf(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap pujdjdty(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] yjgfigcnjgd(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(XianJinFenQiSharedPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            XianJinFenQiApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelXianJinFenQi<XianJinFenQiCompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianJinFenQi<XianJinFenQiCompanyInfoModel>>getScheduler())
                    .compose(this.<BaseRespModelXianJinFenQi<XianJinFenQiCompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianJinFenQi<XianJinFenQiCompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelXianJinFenQi<XianJinFenQiCompanyInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    XianJinFenQiSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                    if (xianJinFenQiMineAdapter != null){
                                        xianJinFenQiMineAdapter.notifyDataSetChanged();
                                    } else {
                                        initAdapter();
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public Bitmap puikhkjdghk(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap bsdfghrtujf(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap rturtdiugfxh(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] ertyhsnbfxgh(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

    @Override
    public void onDestroy() {
        if (normalXianJinFenQiDialog != null) {
            normalXianJinFenQiDialog.dismiss();
            normalXianJinFenQiDialog = null;
        }
        super.onDestroy();
    }

    public Bitmap bfsttujhs(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap puiiyhjgj(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap dsfgurtujdf(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] reytnbsfht(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

}
