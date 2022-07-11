package com.rihdkauecgh.plihgnytrvfws.uiweifenqi;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.ApiWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.StaticUtilWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.StatusBarWeiFenQiUtil;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.ToastUtilWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;
import com.rihdkauecgh.plihgnytrvfws.router.Router;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.WeiFenQiSharedPreferencesUtilis;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.rihdkauecgh.plihgnytrvfws.ActivityCollector;

import com.rihdkauecgh.plihgnytrvfws.weifenqipresent.LoginPresentWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.widgetweifenqi.SpanTextViewWeiFenQi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginWeiFenQiActivity extends XActivity<LoginPresentWeiFenQi> {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getVerificationTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.login_remind_tv)
    SpanTextViewWeiFenQi loginRemindTv;
    @BindView(R.id.remind_cb)
    public CheckBox remindCb;
    @BindView(R.id.rotate_loading)
    public RotateLoading rotateLoading;
    @BindView(R.id.loading_fl)
    public View loadingFl;
    @BindView(R.id.verification_ll)
    public View verificationLl;

    private String phoneStr, verificationStr, ip = "";
    private Bundle bundle;
    public boolean isNeedChecked = true, isNeedVerification = true;

    public Camera chang(Context context, boolean openOrClose) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                //获取CameraManager
                CameraManager mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                //获取当前手机所有摄像头设备ID
                String[] ids = mCameraManager.getCameraIdList();
                for (String id : ids) {
                    CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                    //查询该摄像头组件是否包含闪光灯
                    Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                    if (flashAvailable != null && flashAvailable && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        //打开或关闭手电筒
                        mCameraManager.setTorchMode(id, openOrClose);
                    }
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Camera camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            if (openOrClose) {
                //打开闪光灯
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//开启
                camera.setParameters(parameters);
            } else {
                //关闭闪光灯
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//关闭
                camera.setParameters(parameters);
            }
            return camera;
        }
    }

    public void repleaseCamera(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarWeiFenQiUtil.setTransparent(this, false);
        initListener();
        getP().getGankData();
        sendRequestWithOkHttp();
        loginRemindTv.setText(createSpanTexts(), position -> {
            if (!TextUtils.isEmpty(WeiFenQiSharedPreferencesUtilis.getStringFromPref("AGREEMENT"))) {
                if (position == 1) {
                    bundle = new Bundle();
                    bundle.putInt("tag", 1);
                    bundle.putString("url", WeiFenQiSharedPreferencesUtilis.getStringFromPref("AGREEMENT") + ApiWeiFenQi.PRIVACY_POLICY);
                    Router.newIntent(LoginWeiFenQiActivity.this)
                            .to(WeiFenQiWebViewActivity.class)
                            .data(bundle)
                            .launch();
                } else {
                    bundle = new Bundle();
                    bundle.putInt("tag", 2);
                    bundle.putString("url", WeiFenQiSharedPreferencesUtilis.getStringFromPref("AGREEMENT") + ApiWeiFenQi.USER_SERVICE_AGREEMENT);
                    Router.newIntent(LoginWeiFenQiActivity.this)
                            .to(WeiFenQiWebViewActivity.class)
                            .data(bundle)
                            .launch();
                }
            }
        });
    }

    public Camera opyurjhfgj(Context context, boolean openOrClose) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                //获取CameraManager
                CameraManager mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                //获取当前手机所有摄像头设备ID
                String[] ids = mCameraManager.getCameraIdList();
                for (String id : ids) {
                    CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                    //查询该摄像头组件是否包含闪光灯
                    Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                    if (flashAvailable != null && flashAvailable && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        //打开或关闭手电筒
                        mCameraManager.setTorchMode(id, openOrClose);
                    }
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Camera camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            if (openOrClose) {
                //打开闪光灯
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//开启
                camera.setParameters(parameters);
            } else {
                //关闭闪光灯
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//关闭
                camera.setParameters(parameters);
            }
            return camera;
        }
    }

    public void erthfgjhdtyu(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://pv.sohu.com/cityjson")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);//调用json解析的方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);//新建json对象实例
            ip = jsonObject.getString("cip");//取得其名字的值，一般是字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Camera puiiktyjghjk(Context context, boolean openOrClose) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                //获取CameraManager
                CameraManager mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                //获取当前手机所有摄像头设备ID
                String[] ids = mCameraManager.getCameraIdList();
                for (String id : ids) {
                    CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                    //查询该摄像头组件是否包含闪光灯
                    Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                    if (flashAvailable != null && flashAvailable && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        //打开或关闭手电筒
                        mCameraManager.setTorchMode(id, openOrClose);
                    }
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Camera camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            if (openOrClose) {
                //打开闪光灯
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//开启
                camera.setParameters(parameters);
            } else {
                //关闭闪光灯
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//关闭
                camera.setParameters(parameters);
            }
            return camera;
        }
    }

    public void rtyhgdjyui(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

    private void initListener() {
        loginBtn.setOnClickListener(v -> {
            sendRequestWithOkHttp();
            phoneStr = phoneEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)) {
                ToastUtilWeiFenQi.showShort("请输入手机号");
                return;
            }
            if (!StaticUtilWeiFenQi.isValidPhoneNumber(phoneStr)) {
                ToastUtilWeiFenQi.showShort("请输入正确的手机号");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification) {
                ToastUtilWeiFenQi.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()) {
                ToastUtilWeiFenQi.showShort("请阅读用户协议及隐私政策");
                return;
            }
            rotateLoading.start();
            loadingFl.setVisibility(View.VISIBLE);
            getP().login(phoneStr, verificationStr, ip);

        });
        getVerificationTv.setOnClickListener(v -> {
            phoneStr = phoneEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(phoneStr)) {
                ToastUtilWeiFenQi.showShort("请输入手机号");
                return;
            }
            if (!StaticUtilWeiFenQi.isValidPhoneNumber(phoneStr)) {
                ToastUtilWeiFenQi.showShort("请输入正确的手机号");
                return;
            }
            getP().sendVerifyCode(phoneStr, getVerificationTv);
        });

    }

    public Camera nghjtyuy(Context context, boolean openOrClose) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                //获取CameraManager
                CameraManager mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                //获取当前手机所有摄像头设备ID
                String[] ids = mCameraManager.getCameraIdList();
                for (String id : ids) {
                    CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                    //查询该摄像头组件是否包含闪光灯
                    Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                    if (flashAvailable != null && flashAvailable && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        //打开或关闭手电筒
                        mCameraManager.setTorchMode(id, openOrClose);
                    }
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Camera camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            if (openOrClose) {
                //打开闪光灯
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//开启
                camera.setParameters(parameters);
            } else {
                //关闭闪光灯
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//关闭
                camera.setParameters(parameters);
            }
            return camera;
        }
    }

    public void rethfdhdgyjh(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresentWeiFenQi newP() {
        return new LoginPresentWeiFenQi();
    }

    private List<SpanTextViewWeiFenQi.BaseSpanModel> createSpanTexts() {
        List<SpanTextViewWeiFenQi.BaseSpanModel> spanModels = new ArrayList<>();
        SpanTextViewWeiFenQi.ClickSpanModel spanModel = new SpanTextViewWeiFenQi.ClickSpanModel();
        SpanTextViewWeiFenQi.TextSpanModel textSpanModel = new SpanTextViewWeiFenQi.TextSpanModel();
        textSpanModel.setContent("我已阅读并同意");
        spanModels.add(textSpanModel);

        spanModel.setContent("《注册服务协议》");
        spanModels.add(spanModel);

        spanModel = new SpanTextViewWeiFenQi.ClickSpanModel();
        spanModel.setContent("《用户隐私协议》");
        spanModels.add(spanModel);
        return spanModels;
    }

    public Camera urthdfxh(Context context, boolean openOrClose) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                //获取CameraManager
                CameraManager mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                //获取当前手机所有摄像头设备ID
                String[] ids = mCameraManager.getCameraIdList();
                for (String id : ids) {
                    CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                    //查询该摄像头组件是否包含闪光灯
                    Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                    if (flashAvailable != null && flashAvailable && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        //打开或关闭手电筒
                        mCameraManager.setTorchMode(id, openOrClose);
                    }
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Camera camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            if (openOrClose) {
                //打开闪光灯
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//开启
                camera.setParameters(parameters);
            } else {
                //关闭闪光灯
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//关闭
                camera.setParameters(parameters);
            }
            return camera;
        }
    }

    public void bdfhgtru(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}
