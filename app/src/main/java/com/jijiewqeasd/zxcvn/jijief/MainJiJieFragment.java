package com.jijiewqeasd.zxcvn.jijief;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jijiewqeasd.zxcvn.R;
import com.jijiewqeasd.zxcvn.jijiepage.ImageJiJieAdapter;
import com.jijiewqeasd.zxcvn.jijiepage.JiJieJumpH5Activity;
import com.jijiewqeasd.zxcvn.jijieapi.NetJiJieApi;
import com.jijiewqeasd.zxcvn.jijiem.BaseJiJieModel;
import com.jijiewqeasd.zxcvn.jijiem.ProductJiJieModel;
import com.jijiewqeasd.zxcvn.jijiepage.JiJieMainActivity;
import com.jijiewqeasd.zxcvn.mvp.XActivity;
import com.jijiewqeasd.zxcvn.mvp.XFragment;
import com.jijiewqeasd.zxcvn.net.ApiSubscriber;
import com.jijiewqeasd.zxcvn.net.NetError;
import com.jijiewqeasd.zxcvn.net.XApi;
import com.jijiewqeasd.zxcvn.u.OpenJiJieUtil;
import com.jijiewqeasd.zxcvn.u.PreferencesJiJieOpenUtil;
import com.youth.banner.Banner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class MainJiJieFragment extends XFragment {

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
    @BindView(R.id.parent_fl)
    View parentFl;
    @BindView(R.id.more_ll)
    View moreLl;
    @BindView(R.id.more_tv)
    View more_tv;

    private ProductJiJieModel productJiJieModel;

    private Bundle bundle;

    private ImageJiJieAdapter imageAdapter;

    private static final String TAG = "FileUtil";
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
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productJiJieModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productJiJieModel);
        });
        parentFl.setOnClickListener(v -> {
            productClick(productJiJieModel);
        });
        moreLl.setOnClickListener(v -> {
            ((JiJieMainActivity)getActivity()).jumpPage();
        });
        more_tv.setOnClickListener(v -> {
            ((JiJieMainActivity)getActivity()).jumpPage();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
    }

    /**
     * 根据文件后缀名获得对应的MIME类型
     *
     * @param filePath 文件路径
     */
    public static String getMIMEType(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            Log.e(TAG, "getMIMEType: 文件不存在");
            return "";
        }

        if (!file.isFile()) {
            Log.e(TAG, "getMIMEType: 当前文件类型是目录");
            return "";
        }
        String type = "*/*";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf("."); // 获取后缀名前的分隔符"."在fileName中的位置
        if (dotIndex < 0) {
            return type;
        }

        String end = fileName.substring(dotIndex, fileName.length()).toLowerCase(Locale.getDefault()); // 获取文件的后缀名
        if (end.length() == 0) {
            return type;
        }

        // 在MIME和文件类型的匹配表中找到对应的MIME类型
        for (String[] aMIME_MapTable : MIME_MapTable) {
            if (end.equals(aMIME_MapTable[0])) {
                type = aMIME_MapTable[1];
            }
        }
        return type;
    }

    private void initBannerAdapter(List<ProductJiJieModel> data) {
        imageAdapter = null;
        imageAdapter = new ImageJiJieAdapter(data);
        imageAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jijie_main;
    }

    /**
     * 获取设备的sd根路径
     */
    public static String getSDPath() {
        File sdDir = null;
        String sdPath;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        assert sdDir != null;
        sdPath = sdDir.toString();
        Log.w(TAG, "getSDPath:" + sdPath);
        return sdPath;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductJiJieModel model) {
            if (model == null) {
                return;
            }
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

    /**
     * 根据文件名获得文件的扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名（不带点）
     */
    public static String getFileSuffix(String fileName) {
        Log.w(TAG, "getFileSuffix: fileName::" + fileName);
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1, fileName.length());
    }

    public void productList() {
        mobileType = PreferencesJiJieOpenUtil.getInt("mobileType");
        productJiJieModel = null;
        NetJiJieApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseJiJieModel<List<ProductJiJieModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        OpenJiJieUtil.showErrorInfo(getActivity(), error);
                        if (imageAdapter == null) {
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
                                    initBannerAdapter(baseJiJieModel.getData());
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

    public void toWeb(ProductJiJieModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenJiJieUtil.getValue((XActivity) getActivity(), JiJieJumpH5Activity.class, bundle);
        }
    }

    /**
     * 创建文件
     *
     * @param dirPath  文件所在目录的目录名，如/java/test/1.txt,要在当前目录下创建一个文件名为1.txt的文件
     *                 则path为/java/test，fileName为1.txt（也可以封装成直接传递文件的绝对路径）
     * @param fileName 文件名
     * @return 文件新建成功则返回true
     */
    public static boolean createFile(String dirPath, String fileName) {
        String filePath = dirPath + File.separator + fileName;
        Log.w(TAG, "createFile: filePath::" + filePath + "  File.separator ::" + File.separator);
        File file = new File(filePath);
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
            Log.w(TAG, "createFile: 文件所在目录不存在，创建目录成功");
        }

        if (file.exists()) {
            Log.e(TAG, "新建文件失败：file.exist()=" + file.exists());
            return false;
        } else {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
