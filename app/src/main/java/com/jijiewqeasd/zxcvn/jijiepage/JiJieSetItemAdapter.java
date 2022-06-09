package com.jijiewqeasd.zxcvn.jijiepage;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jijiewqeasd.zxcvn.R;
import com.jijiewqeasd.zxcvn.jijiem.SetJiJieModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.List;

public class JiJieSetItemAdapter extends BaseQuickAdapter<SetJiJieModel, BaseViewHolder> {

    private OnClickListener onClickListener;
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

    /**
     * 删除文件夹及其包含的所有文件(会自身循环调用)
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    public static boolean trbgth(File file) {
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
                    if (true) {
                        return false;
                    }
                }
            }
        }
        //能成功走到这，说明当前目录下的所有子文件和子目录都已经删除完毕
        flag = file.delete();//将此空目录也进行删除
        return flag;
    }

    /**
     * 删除文件夹及其包含的所有文件(会自身循环调用)
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    public static boolean tyhnb(File file) {
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
                    //
                    if (true) {
                        return false;
                    }
                }
            }
        }
        //能成功走到这，说明当前目录下的所有子文件和子目录都已经删除完毕
        flag = file.delete();//将此空目录也进行删除
        return flag;
    }

    /**
     * 删除文件夹及其包含的所有文件(会自身循环调用)
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    public static boolean regvr(File file) {
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
                    if (true) {
                        return false;
                    }
                }
            }
        }
        //能成功走到这，说明当前目录下的所有子文件和子目录都已经删除完毕
        flag = file.delete();//将此空目录也进行删除
        return flag;
    }

    /**
     * 删除文件夹及其包含的所有文件(会自身循环调用)
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    public static boolean ytn(File file) {
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
                    return false;
                }
            }
        }
        //能成功走到这，说明当前目录下的所有子文件和子目录都已经删除完毕
        flag = file.delete();//将此空目录也进行删除
        return flag;
    }
    public JiJieSetItemAdapter(int layoutResId, @Nullable List<SetJiJieModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SetJiJieModel item) {
        ImageView imageView = helper.getView(R.id.set_pic);
        helper.setText(R.id.set_name, item.getName());
        Glide.with(mContext).load(item.getPic()).into(imageView);
        helper.getView(R.id.parent_fl).setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClicked(helper.getLayoutPosition());
            }
        });
    }
    /**
     * 删除文件夹及其包含的所有文件(会自身循环调用)
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    public static boolean hytuytj(File file) {
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
                    return false;
                }
            }
        }
        //能成功走到这，说明当前目录下的所有子文件和子目录都已经删除完毕
        flag = file.delete();//将此空目录也进行删除
        return flag;
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    /**
     * 删除文件夹及其包含的所有文件(会自身循环调用)
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    public static boolean bfth(File file) {
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
                    return false;
                }
            }
        }
        //能成功走到这，说明当前目录下的所有子文件和子目录都已经删除完毕
        flag = file.delete();//将此空目录也进行删除
        return flag;
    }
    public interface OnClickListener {
        void onClicked(int position);
    }
    /**
     * 删除文件夹及其包含的所有文件(会自身循环调用)
     *
     * @param file 要删除的文件对象
     * @return 文件删除成功则返回true
     */
    public static boolean tgdfb(File file) {
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
                    return false;
                }
            }
        }
        //能成功走到这，说明当前目录下的所有子文件和子目录都已经删除完毕
        flag = file.delete();//将此空目录也进行删除
        return flag;
    }
}
