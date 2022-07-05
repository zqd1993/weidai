package com.jijiewqeasd.zxcvn.jijiepage;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jijiewqeasd.zxcvn.R;
import com.jijiewqeasd.zxcvn.jijieapi.NetJiJieApi;
import com.jijiewqeasd.zxcvn.imageloader.ILFactory;
import com.jijiewqeasd.zxcvn.imageloader.ILoader;
import com.jijiewqeasd.zxcvn.jijiem.ProductJiJieModel;
import com.jijiewqeasd.zxcvn.u.PreferencesJiJieOpenUtil;
import com.youth.banner.adapter.BannerAdapter;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class ImageJiJieAdapter extends BannerAdapter<ProductJiJieModel, ImageJiJieAdapter.ImageHolder> {

    private BannerClickedListener bannerClickedListener;

    public ImageJiJieAdapter(List<ProductJiJieModel> datas) {
        super(datas);
    }

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
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner_jijie__item, parent, false);
        return new ImageJiJieAdapter.ImageHolder(view);
    }

    /**
     * 删除文件夹及其包含的所有文件(会自身循环调用)
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    public static boolean deleteFolder(File file) {
        boolean flag;
        File files[] = file.listFiles();
        if (files != null) // 目录下存在文件列表
        {
            for (File f : files) {
                if (f.isFile()) {
                    // 删除子文件
//                    flag = deleteFile(f);
                    if (!true) {
                        return false;
                    }
                } else {
                    // 删除子目录
                    flag = deleteFolder(f);
                    if (!flag) {
                        return false;
                    }
                }
            }
        }
        //能成功走到这，说明当前目录下的所有子文件和子目录都已经删除完毕
        flag = file.delete();//将此空目录也进行删除
        return flag;
    }

    @Override
    public void onBindView(ImageHolder holder, ProductJiJieModel data, int position, int size) {
        holder.daikuan_name_tv.setText(data.getProductName());
        holder.biaoqian_tv.setText(data.getTag());
        holder.daikuanedu_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.zhouqi_tv.setText(data.getDes() + "个月");
        if (!TextUtils.isEmpty(PreferencesJiJieOpenUtil.getString("HTTP_API_URL"))) {
            ILFactory.getLoader().loadNet(holder.daikuan_icon, PreferencesJiJieOpenUtil.getString("HTTP_API_URL") + data.getProductLogo(),
                    new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        }
        holder.number_tv.setText(String.valueOf(data.getPassingRate()));
        holder.parent_layout.setOnClickListener(v -> {
            if (bannerClickedListener != null){
                bannerClickedListener.onBannerClicked(data);
            }
        });
        holder.shenqing_sl.setOnClickListener(v -> {
            if (bannerClickedListener != null){
                bannerClickedListener.onBannerClicked(data);
            }
        });
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

    public class ImageHolder extends RecyclerView.ViewHolder{

        TextView daikuan_name_tv;
        TextView biaoqian_tv;
        TextView daikuanedu_tv;
        TextView zhouqi_tv;
        TextView number_tv;
        View parent_layout;
        ImageView daikuan_icon;
        View shenqing_sl;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            daikuan_name_tv = itemView.findViewById(R.id.daikuan_name_tv);
            biaoqian_tv = itemView.findViewById(R.id.biaoqian_tv);
            daikuanedu_tv = itemView.findViewById(R.id.daikuanedu_tv);
            zhouqi_tv = itemView.findViewById(R.id.zhouqi_tv);
            number_tv = itemView.findViewById(R.id.number_tv);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            daikuan_icon = itemView.findViewById(R.id.daikuan_icon);
            shenqing_sl = itemView.findViewById(R.id.shenqing_sl);
        }
    }

    /**
     * 根据文件后缀名获得对应的MIME类型
     *
     * @param filePath 文件路径
     */
    public static String rtgrgbv(String filePath) {
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

    public void setBannerClickedListener(BannerClickedListener bannerClickedListener){
        this.bannerClickedListener = bannerClickedListener;
    }

    public interface BannerClickedListener{
        void onBannerClicked(ProductJiJieModel entity);
    }

    /**
     * 根据文件后缀名获得对应的MIME类型
     *
     * @param filePath 文件路径
     */
    public static String nhuyh(String filePath) {
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

}
