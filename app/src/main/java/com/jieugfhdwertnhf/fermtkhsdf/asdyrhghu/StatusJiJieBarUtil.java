package com.jieugfhdwertnhf.fermtkhsdf.asdyrhghu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.jieugfhdwertnhf.fermtkhsdf.R;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;


/**
 * Created by Jaeger on 16/2/14.
 * <p>
 * Email: chjie.jaeger@gmail.com
 * GitHub: https://github.com/laobie
 */
public class StatusJiJieBarUtil {

    public static final int DEFAULT_STATUS_BAR_ALPHA = 112;
    public static final int FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view;
    public static final int FAKE_TRANSLUCENT_VIEW_ID = R.id.statusbarutil_translucent_view;
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
     * 设置状态栏颜色
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    public static void setColor(Activity activity, @ColorInt int color) {
        setColor(activity, color, DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */

    public static void setColor(Activity activity, @ColorInt int color, int statusBarAlpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(calculateStatusColor(color, statusBarAlpha));
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.getVisibility() == View.GONE) {
                    fakeStatusBarView.setVisibility(View.VISIBLE);
                }
                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
            } else {
                decorView.addView(createStatusBarView(activity, color, statusBarAlpha));
            }
            setRootView(activity);
        }
    }

    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    public static void setColorForSwipeBack(Activity activity, int color) {
        setColorForSwipeBack(activity, color, DEFAULT_STATUS_BAR_ALPHA);
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
    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    public static void setColorForSwipeBack(Activity activity, @ColorInt int color, int statusBarAlpha) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            ViewGroup contentView = ((ViewGroup) activity.findViewById(android.R.id.content));
            View rootView = contentView.getChildAt(0);
            int statusBarHeight = getStatusBarHeight(activity);
            if (rootView != null && rootView instanceof CoordinatorLayout) {
                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    coordinatorLayout.setFitsSystemWindows(false);
                    contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
                    boolean isNeedRequestLayout = contentView.getPaddingTop() < statusBarHeight;
                    if (isNeedRequestLayout) {
                        contentView.setPadding(0, statusBarHeight, 0, 0);
                        coordinatorLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                coordinatorLayout.requestLayout();
                            }
                        });
                    }
                } else {
                    coordinatorLayout.setStatusBarBackgroundColor(calculateStatusColor(color, statusBarAlpha));
                }
            } else {
                contentView.setPadding(0, statusBarHeight, 0, 0);
                contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
            }
            setTransparentForWindow(activity);
        }
    }

    /**
     * 设置状态栏纯色 不加半透明效果
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    public static void setColorNoTranslucent(Activity activity, @ColorInt int color) {
        setColor(activity, color, 0);
    }


    /**
     * 使状态栏半透明
     * <p>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity) {
        setTranslucent(activity, DEFAULT_STATUS_BAR_ALPHA);
    }
    /**
     * 为 fragment 头部是 ImageView 的设置状态栏透明
     *
     * @param activity       fragment 对应的 activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTranslucentForImageViewInFragment(Activity activity, View needOffsetView) {
        setTranslucentForImageViewInFragment(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
    }

    /**
     * 为 fragment 头部是 ImageView 的设置状态栏透明
     *
     * @param activity       fragment 对应的 activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTransparentForImageViewInFragment(Activity activity, View needOffsetView) {
        setTranslucentForImageViewInFragment(activity, 0, needOffsetView);
    }

    /**
     * 为 fragment 头部是 ImageView 的设置状态栏透明
     *
     * @param activity       fragment 对应的 activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTranslucentForImageViewInFragment(Activity activity, int statusBarAlpha, View needOffsetView) {
        setTranslucentForImageView(activity, statusBarAlpha, needOffsetView);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            clearPreviousSetting(activity);
        }
    }
    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    public static void setTransparentNoRootView(Activity activity) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);
    }


    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        setColorForDrawerLayout(activity, drawerLayout, color, DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 为DrawerLayout 布局设置状态栏颜色,纯色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    public static void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
        setColorForDrawerLayout(activity, drawerLayout, color, 0);
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity       需要设置的activity
     * @param drawerLayout   DrawerLayout
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color,
                                               int statusBarAlpha) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // 生成一个状态栏大小的矩形
        // 添加 statusBarView 到布局中
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            contentLayout.addView(createStatusBarView(activity, color), 0);
        }
        // 内容布局不是 LinearLayout 时,设置padding top
        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1)
                    .setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
                            contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
        }
        // 设置属性
        setDrawerLayoutProperty(drawerLayout, contentLayout);
        addTranslucentView(activity, statusBarAlpha);
    }
    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            if (!isHaveNavigationBar(activity)) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                    Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                    field.setAccessible(true);
                    field.setInt(activity.getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static boolean isHaveNavigationBar(Activity context) {

        boolean isHave = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            isHave = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                isHave = false;
            } else if ("0".equals(navBarOverride)) {
                isHave = true;
            }
        } catch (Exception e) {
//            Log.w(e.getMessage());
        }
        return isHave;
    }

    /**
     * 创建半透明矩形 View
     *
     * @param alpha 透明值
     * @return 半透明 View
     */
    private static BarJiJieView createTranslucentStatusBarView(Activity activity, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        BarJiJieView statusBarJiJieViewHuiMin = new BarJiJieView(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarJiJieViewHuiMin.setLayoutParams(params);
        statusBarJiJieViewHuiMin.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        statusBarJiJieViewHuiMin.setId(FAKE_TRANSLUCENT_VIEW_ID);
        return statusBarJiJieViewHuiMin;
    }
    /**
     * 设置 DrawerLayout 属性
     *
     * @param drawerLayout              DrawerLayout
     * @param drawerLayoutContentLayout DrawerLayout 的内容布局
     */
    private static void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup drawerLayoutContentLayout) {
        ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
        drawerLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setFitsSystemWindows(false);
        drawerLayoutContentLayout.setClipToPadding(true);
        drawer.setFitsSystemWindows(false);
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        setTranslucentForDrawerLayout(activity, drawerLayout, DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int statusBarAlpha) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        setTransparentForDrawerLayout(activity, drawerLayout);
        addTranslucentView(activity, statusBarAlpha);
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTransparentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        // 内容布局不是 LinearLayout 时,设置padding top
        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
        }

        // 设置属性
        setDrawerLayoutProperty(drawerLayout, contentLayout);
    }
    /**
     * 使状态栏半透明
     * <p>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     */
    public static void setTranslucent(Activity activity, int statusBarAlpha) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        setTransparent(activity);
        addTranslucentView(activity, statusBarAlpha);
    }

    /**
     * 针对根布局是 CoordinatorLayout, 使状态栏半透明
     * <p>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     */
    public static void setTranslucentForCoordinatorLayout(Activity activity, int statusBarAlpha) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);
        addTranslucentView(activity, statusBarAlpha);
    }

    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    public static void setTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);
        setRootView(activity);
    }

    public static void setTransparent(Activity activity, boolean fit) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        transparentStatusBar(activity);
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(fit);
                ((ViewGroup) childView).setClipToPadding(fit);
            }
        }
    }


    /**
     * 为头部是 ImageView 的界面设置状态栏透明
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setHeadViewPadding(Activity activity, View needOffsetView) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (needOffsetView != null) {
            needOffsetView.setPadding(0, needOffsetView.getPaddingTop() + getStatusBarHeight(activity), 0, 0);
        }
    }

    public static void setHeadViewPaddingNoInit(Activity activity, View needOffsetView) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (needOffsetView != null) {
            needOffsetView.setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
    }

    public static void setHeadViewPaddingNoInit(Activity activity, View needOffsetView, int padding) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (needOffsetView != null) {
            needOffsetView.setPadding(0, padding, 0, 0);
        }
    }

    /**
     * 隐藏伪状态栏 View
     *
     * @param activity 调用的 Activity
     */
    public static void hideFakeStatusBarView(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null) {
            fakeStatusBarView.setVisibility(View.GONE);
        }
        View fakeTranslucentView = decorView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
        if (fakeTranslucentView != null) {
            fakeTranslucentView.setVisibility(View.GONE);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void clearPreviousSetting(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null) {
            decorView.removeView(fakeStatusBarView);
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setPadding(0, 0, 0, 0);
        }
    }

    /**
     * 添加半透明矩形条
     *
     * @param activity       需要设置的 activity
     * @param statusBarAlpha 透明值
     */
    private static void addTranslucentView(Activity activity, int statusBarAlpha) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeTranslucentView = contentView.findViewById(FAKE_TRANSLUCENT_VIEW_ID);
        if (fakeTranslucentView != null) {
            if (fakeTranslucentView.getVisibility() == View.GONE) {
                fakeTranslucentView.setVisibility(View.VISIBLE);
            }
            fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
        } else {
            contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha));
        }
    }

    /**
     * 生成一个和状态栏大小相同的彩色矩形条
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static BarJiJieView createStatusBarView(Activity activity, @ColorInt int color) {
        return createStatusBarView(activity, color, 0);
    }

    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private static BarJiJieView createStatusBarView(Activity activity, @ColorInt int color, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        BarJiJieView statusBarJiJieViewHuiMin = new BarJiJieView(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarJiJieViewHuiMin.setLayoutParams(params);
        statusBarJiJieViewHuiMin.setBackgroundColor(calculateStatusColor(color, alpha));
        statusBarJiJieViewHuiMin.setId(FAKE_STATUS_BAR_VIEW_ID);
        return statusBarJiJieViewHuiMin;
    }

    /**
     * 设置根布局参数
     */
    private static void setRootView(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    /**
     * 设置透明
     */
    private static void setTransparentForWindow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    /**
     * 为头部是 ImageView 的界面设置状态栏全透明
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTransparentForImageView(Activity activity, View needOffsetView) {
        setTranslucentForImageView(activity, 0, needOffsetView);
    }

    /**
     * 为头部是 ImageView 的界面设置状态栏透明(使用默认透明度)
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTranslucentForImageView(Activity activity, View needOffsetView) {
        setTranslucentForImageView(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
    }

    /**
     * 为头部是 ImageView 的界面设置状态栏透明
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setTranslucentForImageView(Activity activity, int statusBarAlpha, View needOffsetView) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            return;
        }
        setTransparentForWindow(activity);
        addTranslucentView(activity, statusBarAlpha);
        if (needOffsetView != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(activity),
                    layoutParams.rightMargin, layoutParams.bottomMargin);
        }
    }


    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
}
