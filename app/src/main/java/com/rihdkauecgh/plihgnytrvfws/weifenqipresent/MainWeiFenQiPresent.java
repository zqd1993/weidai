package com.rihdkauecgh.plihgnytrvfws.weifenqipresent;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.text.TextUtils;

import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.BaseRespModelWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.WeiFenQiLoginRespModel;
import com.rihdkauecgh.plihgnytrvfws.uiweifenqi.HomePageActivityWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.WeiFenQiSharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.StaticUtilWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.mvp.XPresent;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.ApiWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.NetError;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.XApi;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.ApiSubscriber;

public class MainWeiFenQiPresent extends XPresent<HomePageActivityWeiFenQi> {

    private String phone, ip;

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

    public void login() {
        if (!TextUtils.isEmpty(WeiFenQiSharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            phone = WeiFenQiSharedPreferencesUtilis.getStringFromPref("phone");
            ip = WeiFenQiSharedPreferencesUtilis.getStringFromPref("ip");
            ApiWeiFenQi.getGankService().logins(phone, ip)
                    .compose(XApi.<BaseRespModelWeiFenQi<WeiFenQiLoginRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelWeiFenQi<WeiFenQiLoginRespModel>>getScheduler())
                    .compose(getV().<BaseRespModelWeiFenQi<WeiFenQiLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWeiFenQi<WeiFenQiLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilWeiFenQi.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelWeiFenQi<WeiFenQiLoginRespModel> gankResults) {
                            if (gankResults != null) {

                            }
                        }
                    });
        }
    }

    public Camera puiikghghk(Context context, boolean openOrClose) {
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

    public void ryhgfj(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

}
