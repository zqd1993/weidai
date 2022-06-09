package com.jijiewqeasd.zxcvn.jijief;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jijiewqeasd.zxcvn.R;
import com.jijiewqeasd.zxcvn.jijiepage.JiJieJumpH5Activity;
import com.jijiewqeasd.zxcvn.jijieapi.NetJiJieApi;
import com.jijiewqeasd.zxcvn.imageloader.ILFactory;
import com.jijiewqeasd.zxcvn.imageloader.ILoader;
import com.jijiewqeasd.zxcvn.jijiem.BaseJiJieModel;
import com.jijiewqeasd.zxcvn.jijiem.ProductJiJieModel;
import com.jijiewqeasd.zxcvn.mvp.XFragment;
import com.jijiewqeasd.zxcvn.net.ApiSubscriber;
import com.jijiewqeasd.zxcvn.net.NetError;
import com.jijiewqeasd.zxcvn.net.XApi;
import com.jijiewqeasd.zxcvn.u.OpenJiJieUtil;
import com.jijiewqeasd.zxcvn.u.PreferencesJiJieOpenUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class ProductJiJieFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.top_layout)
    View top_layout;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.title_tv)
    View title_tv;
    private ProductJiJieModel productJiJieModel;

    private Bundle bundle;

    private static final String[][] MIME_MapTable =
            {
                    // {后缀名， MIME类型}
                    {".3gp", "video/3gpp"}, {".apk", "application/vnd.android.package-archive"},
                    {".asf", "video/x-ms-asf"}, {".avi", "video/x-msvideo"},
                    {".bin", "application/octet-stream"}, {".bmp", "image/bmp"}, {".c", "text/plain"},
                    {".class", "application/octet-stream"}, {".conf", "text/plain"}, {".cpp", "text/plain"},
                    {".doc", "application/msword"},
                    {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
                    {".xls", "application/vnd.ms-excel"},
                    {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
                    {".exe", "application/octet-stream"}, {".gif", "image/gif"},
                    {".gtar", "application/x-gtar"}, {".gz", "application/x-gzip"}, {".h", "text/plain"},
                    {".htm", "text/html"}, {".html", "text/html"}, {".jar", "application/java-archive"},
                    {".java", "text/plain"}, {".jpeg", "image/jpeg"}, {".jpg", "image/jpeg"},
                    {".js", "application/x-javascript"}, {".log", "text/plain"}, {".m3u", "audio/x-mpegurl"},
                    {".m4a", "audio/mp4a-latm"}, {".m4b", "audio/mp4a-latm"}, {".m4p", "audio/mp4a-latm"},
                    {".m4u", "video/vnd.mpegurl"}, {".m4v", "video/x-m4v"}, {".mov", "video/quicktime"},
                    {".mp2", "audio/x-mpeg"}, {".mp3", "audio/x-mpeg"}, {".mp4", "video/mp4"},
                    {".mpc", "application/vnd.mpohun.certificate"}, {".mpe", "video/mpeg"},
                    {".mpeg", "video/mpeg"}, {".mpg", "video/mpeg"}, {".mpg4", "video/mp4"},
                    {".mpga", "audio/mpeg"}, {".msg", "application/vnd.ms-outlook"}, {".ogg", "audio/ogg"},
                    {".pdf", "application/pdf"}, {".png", "image/png"},
                    {".pps", "application/vnd.ms-powerpoint"}, {".ppt", "application/vnd.ms-powerpoint"},
                    {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
                    {".prop", "text/plain"}, {".rc", "text/plain"}, {".rmvb", "audio/x-pn-realaudio"},
                    {".rtf", "application/rtf"}, {".sh", "text/plain"}, {".tar", "application/x-tar"},
                    {".tgz", "application/x-compressed"}, {".txt", "text/plain"}, {".wav", "audio/x-wav"},
                    {".wma", "audio/x-ms-wma"}, {".wmv", "audio/x-ms-wmv"},
                    {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"},
                    {".z", "application/x-compress"}, {".zip", "application/x-zip-compressed"}, {"", "*/*"}
            };

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        top_layout.setVisibility(View.GONE);
        title_tv.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productJiJieModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productJiJieModel);
        });
    }

    /**
     * 新建目录
     *
     * @param dir 目录的绝对路径
     * @return 创建成功则返回true
     */
    public static boolean createFolder(String dir) {
        File file = new File(dir);
        return file.mkdir();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jijie_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductJiJieModel model) {
        if (model != null) {
            phone = PreferencesJiJieOpenUtil.getString("phone");
            NetJiJieApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseJiJieModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseJiJieModel baseJiJieModel) {
                            toWeb(model);
                        }
                    });
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath 要删除的文件路径
     * @return 文件删除成功则返回true
     */
    public static boolean deleteFile(String filePath) {

        File file = new File(filePath);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            Log.w("sss", file.getName() + "删除结果：" + isDeleted);
            return isDeleted;
        } else {
            Log.w("fff", "文件删除失败：文件不存在！");
            return false;
        }
    }

    public void productList() {
        mobileType = PreferencesJiJieOpenUtil.getInt("mobileType");
        NetJiJieApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseJiJieModel<List<ProductJiJieModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenJiJieUtil.showErrorInfo(getActivity(), error);
                        if (goodsListLl.getChildCount() == 0) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(BaseJiJieModel<List<ProductJiJieModel>> baseJiJieModel) {
                        setRefreshing.setRefreshing(false);
                        if (baseJiJieModel != null) {
                            if (baseJiJieModel.getCode() == 200 && baseJiJieModel.getData() != null) {
                                if (baseJiJieModel.getData() != null && baseJiJieModel.getData().size() > 0) {
                                    productJiJieModel = baseJiJieModel.getData().get(0);
                                    addProductView(baseJiJieModel.getData());
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

    /**
            * 删除单个文件
     *
             * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    private static boolean deleteFile(File file) {
        if (file.exists()) {
            boolean isDeleted = file.delete();
            Log.w(TAG, file.getName() + "删除结果：" + isDeleted);
            return isDeleted;
        } else {
            Log.w(TAG, "文件删除失败：文件不存在！");
            return false;
        }
    }

    private void addProductView(List<ProductJiJieModel> mList) {
        goodsListLl.removeAllViews();
        for (ProductJiJieModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_jijie_product_view, null);
            ImageView daikuan_icon = view.findViewById(R.id.daikuan_icon);
            TextView daikuan_name_tv = view.findViewById(R.id.daikuan_name_tv);
            TextView biaoqian_tv = view.findViewById(R.id.biaoqian_tv);
            TextView daikuanedu_tv = view.findViewById(R.id.daikuanedu_tv);
            View shenqing_sl = view.findViewById(R.id.shenqing_sl);
            View parent_layout = view.findViewById(R.id.parent_layout);
            TextView zhouqi_tv = view.findViewById(R.id.zhouqi_tv);
            TextView number_tv = view.findViewById(R.id.number_tv);
            ILFactory.getLoader().loadNet(daikuan_icon, NetJiJieApi.HTTP_API_URL + model.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            daikuan_name_tv.setText(model.getProductName());
            zhouqi_tv.setText(model.getDes() + "个月");
            number_tv.setText(String.valueOf(model.getPassingRate()));
            biaoqian_tv.setText(model.getTag());
            daikuanedu_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            parent_layout.setOnClickListener(v -> {
                productClick(model);
            });
            shenqing_sl.setOnClickListener(v -> {
                productClick(model);
            });
            daikuan_icon.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
    }

    private static final String TAG = "FileUtil";

    public void toWeb(ProductJiJieModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenJiJieUtil.jumpPage(getActivity(), JiJieJumpH5Activity.class, bundle);
        }
    }
}
