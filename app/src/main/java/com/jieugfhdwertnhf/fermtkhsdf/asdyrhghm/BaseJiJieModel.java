package com.jieugfhdwertnhf.fermtkhsdf.asdyrhghm;


import com.jieugfhdwertnhf.fermtkhsdf.net.IModel;

import java.io.File;

public class BaseJiJieModel<T> implements IModel {

    private int code;

    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
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
}
