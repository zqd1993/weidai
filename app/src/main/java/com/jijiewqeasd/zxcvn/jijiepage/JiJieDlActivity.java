package com.jijiewqeasd.zxcvn.jijiepage;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jijiewqeasd.zxcvn.jijieapi.NetJiJieApi;
import com.jijiewqeasd.zxcvn.R;
import com.jijiewqeasd.zxcvn.jijiem.BaseJiJieModel;
import com.jijiewqeasd.zxcvn.jijiem.ConfigJiJieEntity;
import com.jijiewqeasd.zxcvn.jijiem.DlJiJieModel;
import com.jijiewqeasd.zxcvn.mvp.XActivity;
import com.jijiewqeasd.zxcvn.net.ApiSubscriber;
import com.jijiewqeasd.zxcvn.net.NetError;
import com.jijiewqeasd.zxcvn.net.XApi;
import com.jijiewqeasd.zxcvn.u.MyJiJieToast;
import com.jijiewqeasd.zxcvn.u.OpenJiJieUtil;
import com.jijiewqeasd.zxcvn.u.PreferencesJiJieOpenUtil;
import com.jijiewqeasd.zxcvn.u.StatusJiJieBarUtil;
import com.jijiewqeasd.zxcvn.w.ClickJiJieTextView;
import com.jijiewqeasd.zxcvn.w.CountDownJiJieTimer;

import org.json.JSONObject;

import java.io.File;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JiJieDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickJiJieTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;
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
    public void initData(Bundle savedInstanceState) {
        StatusJiJieBarUtil.setTransparent(this, false);
        xStateController = this.findViewById(R.id.content_layout);
        mobileEt = this.findViewById(R.id.mobile_et);
        yzmEt = this.findViewById(R.id.yzm_et);
        getYzmTv = this.findViewById(R.id.get_yzm_tv);
        dlBtn = this.findViewById(R.id.dl_btn);
        remindCb = this.findViewById(R.id.remind_cb);
        readTv = this.findViewById(R.id.read_tv);
        yzmCv = this.findViewById(R.id.yzm_cv);
        getIp();
        xStateController.loadingView(View.inflate(this, R.layout.view_jijie_loading, null));
        getConfig();
        readTv.setText(OpenJiJieUtil.createDlSpanTexts(), position -> {
            if (!TextUtils.isEmpty(PreferencesJiJieOpenUtil.getString("AGREEMENT"))) {
                bundle = new Bundle();
                if (position == 1) {
                    bundle.putString("url", PreferencesJiJieOpenUtil.getString("AGREEMENT") + NetJiJieApi.ZCXY);
                    bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                } else {
                    bundle.putString("url", PreferencesJiJieOpenUtil.getString("AGREEMENT") + NetJiJieApi.YSXY);
                    bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                }
                OpenJiJieUtil.jumpPage(JiJieDlActivity.this, JiJieJumpH5Activity.class, bundle);
            }
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyJiJieToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                MyJiJieToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                MyJiJieToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                MyJiJieToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_jijie_dl;
    }

    @Override
    public Object newP() {
        return null;
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

    public void getConfig() {
        if (!TextUtils.isEmpty(PreferencesJiJieOpenUtil.getString("HTTP_API_URL"))) {
            NetJiJieApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseJiJieModel<ConfigJiJieEntity>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenJiJieUtil.showErrorInfo(JiJieDlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseJiJieModel<ConfigJiJieEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    PreferencesJiJieOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
                                    if ("0".equals(configEntity.getData().getIsCodeLogin())) {
                                        yzmCv.setVisibility(View.GONE);
                                    } else {
                                        yzmCv.setVisibility(View.VISIBLE);
                                    }
                                    isNeedYzm = "1".equals(configEntity.getData().getIsCodeLogin());
                                    isChecked = "1".equals(configEntity.getData().getIsSelectLogin());
                                    remindCb.setChecked(isChecked);
                                }
                            }
                        }
                    });
        }
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

    private void getIp() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://pv.sohu.com/cityjson")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);
            ip = jsonObject.getString("cip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(String phone, String verificationStr) {
        if (!TextUtils.isEmpty(PreferencesJiJieOpenUtil.getString("HTTP_API_URL"))) {
            if (xStateController != null)
                xStateController.showLoading();
            NetJiJieApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseJiJieModel<DlJiJieModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenJiJieUtil.showErrorInfo(JiJieDlActivity.this, error);
                            if (xStateController != null)
                                xStateController.showContent();
                        }

                        @Override
                        public void onNext(BaseJiJieModel<DlJiJieModel> dlModel) {
                            if (xStateController != null)
                                xStateController.showContent();
                            if (dlModel != null && dlModel.getCode() == 200) {
                                if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                    OpenJiJieUtil.jumpPage(JiJieDlActivity.this, JiJieMainActivity.class);
                                    int mobileType = dlModel.getData().getMobileType();
                                    PreferencesJiJieOpenUtil.saveString("ip", ip);
                                    PreferencesJiJieOpenUtil.saveString("phone", phone);
                                    PreferencesJiJieOpenUtil.saveInt("mobileType", mobileType);
                                    finish();
                                }
                            } else {
                                if (dlModel.getCode() == 500) {
                                    MyJiJieToast.showShort(dlModel.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public void getYzm(String phone) {
        if (!TextUtils.isEmpty(PreferencesJiJieOpenUtil.getString("HTTP_API_URL"))) {
            NetJiJieApi.getInterfaceUtils().sendVerifyCode(phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseJiJieModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenJiJieUtil.showErrorInfo(JiJieDlActivity.this, error);
                        }

                        @Override
                        public void onNext(BaseJiJieModel baseJiJieModel) {
                            if (baseJiJieModel != null) {
                                if (baseJiJieModel.getCode() == 200) {
                                    MyJiJieToast.showShort("验证码发送成功");
                                    CountDownJiJieTimer cdt = new CountDownJiJieTimer(getYzmTv, 60000, 1000);
                                    cdt.start();
                                }
                            }
                        }
                    });
        }
    }
}
