package com.jijiewqeasd.zxcvn.jijief;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jijiewqeasd.zxcvn.R;
import com.jijiewqeasd.zxcvn.jijiem.ConfigJiJieEntity;
import com.jijiewqeasd.zxcvn.jijiepage.JiJieAboutInfoActivity;
import com.jijiewqeasd.zxcvn.jijiepage.JiJieDlActivity;
import com.jijiewqeasd.zxcvn.jijiepage.JiJieFeedbackActivity;
import com.jijiewqeasd.zxcvn.jijiepage.JiJieJumpH5Activity;
import com.jijiewqeasd.zxcvn.jijiepage.JiJieSetItemAdapter;
import com.jijiewqeasd.zxcvn.jijiepage.ZhuXiaoJiJieActivity;
import com.jijiewqeasd.zxcvn.jijieapi.NetJiJieApi;
import com.jijiewqeasd.zxcvn.jijiem.BaseJiJieModel;
import com.jijiewqeasd.zxcvn.jijiem.ProductJiJieModel;
import com.jijiewqeasd.zxcvn.jijiem.SetJiJieModel;
import com.jijiewqeasd.zxcvn.mvp.XFragment;
import com.jijiewqeasd.zxcvn.net.ApiSubscriber;
import com.jijiewqeasd.zxcvn.net.NetError;
import com.jijiewqeasd.zxcvn.net.XApi;
import com.jijiewqeasd.zxcvn.u.MyJiJieToast;
import com.jijiewqeasd.zxcvn.u.OpenJiJieUtil;
import com.jijiewqeasd.zxcvn.u.PreferencesJiJieOpenUtil;
import com.jijiewqeasd.zxcvn.w.RemindJiJieDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetJiJieFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.banner_iv)
    View banner_iv;

    private ProductJiJieModel productJiJieModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private JiJieSetItemAdapter setItemAdapter;

    private RemindJiJieDialog dialog;

    private String mailStr = "";

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
        mailStr = PreferencesJiJieOpenUtil.getString("app_mail");
        phone = PreferencesJiJieOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        banner_iv.setOnClickListener(v -> {
            productClick(productJiJieModel);
        });
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
    public int getLayoutId() {
        return R.layout.fragment_jijie_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetJiJieModel model = new SetJiJieModel(R.drawable.kliikl, "注册协议");
        SetJiJieModel model1 = new SetJiJieModel(R.drawable.jhkhju, "隐私协议");
        SetJiJieModel model2 = new SetJiJieModel(R.drawable.ikhjm, "意见反馈");
        SetJiJieModel model3 = new SetJiJieModel(R.drawable.gfhrth, "关于我们");
        SetJiJieModel model4 = new SetJiJieModel(R.drawable.fdsfgfdg, "个性化推荐");
        SetJiJieModel model5 = new SetJiJieModel(R.drawable.hjkujh, "投诉邮箱");
        SetJiJieModel model6 = new SetJiJieModel(R.drawable.ngfbf, "注销账户");
        SetJiJieModel model7 = new SetJiJieModel(R.drawable.ghjhky, "退出登录");
        List<SetJiJieModel> list = new ArrayList<>();
        List<SetJiJieModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapter = new JiJieSetItemAdapter(R.layout.adpater_jijie_set_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", NetJiJieApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenJiJieUtil.jumpPage(getActivity(), JiJieJumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", NetJiJieApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenJiJieUtil.jumpPage(getActivity(), JiJieJumpH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenJiJieUtil.jumpPage(getActivity(), JiJieFeedbackActivity.class);
                    break;
                case 3:
                    OpenJiJieUtil.jumpPage(getActivity(), JiJieAboutInfoActivity.class);
                    break;
                case 4:
                    dialog = new RemindJiJieDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindJiJieDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyJiJieToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyJiJieToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpenJiJieUtil.jumpPage(getActivity(), ZhuXiaoJiJieActivity.class);
                    break;
                case 7:
                    dialog = new RemindJiJieDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindJiJieDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencesJiJieOpenUtil.saveString("phone", "");
                            OpenJiJieUtil.jumpPage(getActivity(), JiJieDlActivity.class);
                            getActivity().finish();
                        }

                        @Override
                        public void onCancelClicked() {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemAdapter);
    }

    /*************************************复制/重命名操作********************************************

     /**
     * 复制文件
     *
     * @param srcPath 源文件绝对路径
     * @param destDir 目标文件所在目录
     * @return boolean 复制成功则返回true
     */
    public static boolean copyFile(String srcPath, String destDir) {
        boolean flag = false;
        File srcFile = new File(srcPath); // 源文件
        if (!srcFile.exists()) {
            // 源文件不存在
            Log.w(TAG, "copyFile: 源文件不存在");
            return false;
        }
        // 获取待复制文件的文件名
        String fileName = srcPath.substring(srcPath.lastIndexOf(File.separator));
        String destPath = destDir + fileName;
        if (destPath.equals(srcPath)) {
            // 源文件路径和目标文件路径重复
            Log.w(TAG, "copyFile: 源文件路径和目标文件路径重复");
            return false;
        }

        File destFile = new File(destPath); // 目标文件
        if (destFile.exists() && destFile.isFile()) {
            // 该路径下已经有一个同名文件
            Log.w(TAG, "copyFile: 目标目录下已有同名文件!");
            return false;
        }

        File destFileDir = new File(destDir);
        destFileDir.mkdirs();
        try {
            FileInputStream fis = new FileInputStream(srcPath);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buf = new byte[1024];
            int c;
            while ((c = fis.read(buf)) != -1) {
                fos.write(buf, 0, c);
            }
            fis.close();
            fos.close();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flag) {
            Log.w(TAG, "copyFile: 复制文件成功!");
        }
        return flag;
    }

    public void toWeb(ProductJiJieModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenJiJieUtil.jumpPage(getActivity(), JiJieJumpH5Activity.class, bundle);
        }
    }

    public void productList() {
        if (!TextUtils.isEmpty(PreferencesJiJieOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = PreferencesJiJieOpenUtil.getInt("mobileType");
            NetJiJieApi.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseJiJieModel<List<ProductJiJieModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenJiJieUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseJiJieModel<List<ProductJiJieModel>> baseJiJieModel) {
                            if (baseJiJieModel != null) {
                                if (baseJiJieModel.getCode() == 200 && baseJiJieModel.getData() != null) {
                                    if (baseJiJieModel.getData() != null && baseJiJieModel.getData().size() > 0) {
                                        productJiJieModel = baseJiJieModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
        }
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

                        }

                        @Override
                        public void onNext(BaseJiJieModel<ConfigJiJieEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesJiJieOpenUtil.saveString("app_mail", mailStr);
                                    dialog = new RemindJiJieDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
        }
    }

    public void productClick(ProductJiJieModel model) {
        if (!TextUtils.isEmpty(PreferencesJiJieOpenUtil.getString("HTTP_API_URL"))) {
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
    }

    /**
     * 复制目录
     *
     * @param srcDir  源文件夹目录
     * @param destDir 目标文件夹所在目录
     * @return 复制成功则返回true
     */
    public static boolean copyFolder(String srcDir, String destDir) {
        Log.w(TAG, "copyFolder: 复制文件夹开始!");
        boolean flag = false;

        File srcFile = new File(srcDir);
        if (!srcFile.exists()) {
            // 源文件夹不存在
            Log.w(TAG, "copyFolder: 源文件夹不存在");
            return false;
        }
        String dirName = ""; // 获得待复制的文件夹的名字，比如待复制的文件夹为"E://dir"则获取的名字为"dir"

        String destPath = destDir ; // 如果想连同源目录名拷贝则String destPath = destDir　+ File.separator + dirName
        // Util.toast("目标文件夹的完整路径为：" + destPath);
        if (destPath.equals(srcDir)) {
            Log.w(TAG, "copyFolder: 目标文件夹与源文件夹重复");
            return false;
        }
        File destDirFile = new File(destPath);
        if (!destDirFile.exists()) {
            destDirFile.mkdirs(); // 如果指定目录不存在则生成目录
        }

        File[] files = srcFile.listFiles(); // 获取源文件夹下的子文件和子文件夹
        Log.i(TAG, "copyFolder: -----files,length::"+files.length+"      files::"+files);
        if (files.length == 0) {
            // 如果源文件夹为空目录则直接设置flag为true，这一步非常隐蔽，debug了很久
            flag = true;
        } else {
            for (File temp : files) {
                if (temp.isFile()) {
                    // 文件
                    flag = copyFile(temp.getAbsolutePath(), destPath);
                } else if (temp.isDirectory()) {
                    // 文件夹
                    flag = copyFolder(temp.getAbsolutePath(), destPath);
                }
                if (!flag) {
                    break;
                }
            }
        }
        if (flag) {
            Log.w(TAG, "copyFolder: 复制文件夹成功!");
        }
        return flag;
    }

}
