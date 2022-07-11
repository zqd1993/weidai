package com.rihdkauecgh.plihgnytrvfws.weifenqipresent;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.BaseRespModelWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.ConfigWeiFenQiModel;
import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.WeiFenQiLoginRespModel;
import com.rihdkauecgh.plihgnytrvfws.uiweifenqi.HomePageActivityWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.uiweifenqi.LoginWeiFenQiActivity;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.ToastUtilWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.WeiFenQiSharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.StaticUtilWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.widgetweifenqi.WeiFenQiCountDownTimerUtils;
import com.rihdkauecgh.plihgnytrvfws.mvp.XPresent;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.ApiWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.NetError;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.XApi;
import com.rihdkauecgh.plihgnytrvfws.router.Router;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.ApiSubscriber;

public class LoginPresentWeiFenQi extends XPresent<LoginWeiFenQiActivity> {

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

    public void getGankData() {
        if (!TextUtils.isEmpty(WeiFenQiSharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            ApiWeiFenQi.getGankService().getGankData()
                    .compose(XApi.<BaseRespModelWeiFenQi<ConfigWeiFenQiModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelWeiFenQi<ConfigWeiFenQiModel>>getScheduler())
                    .compose(getV().<BaseRespModelWeiFenQi<ConfigWeiFenQiModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWeiFenQi<ConfigWeiFenQiModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilWeiFenQi.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelWeiFenQi<ConfigWeiFenQiModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    WeiFenQiSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", gankResults.getData().getAppMail());
                                    if ("0".equals(gankResults.getData().getIsCodeLogin())) {
                                        getV().verificationLl.setVisibility(View.GONE);
                                    } else {
                                        getV().verificationLl.setVisibility(View.VISIBLE);
                                    }
                                    getV().isNeedChecked = "1".equals(gankResults.getData().getIsSelectLogin());
                                    getV().isNeedVerification = "1".equals(gankResults.getData().getIsCodeLogin());
                                    getV().remindCb.setChecked(getV().isNeedChecked);
                                }
                            }
                        }
                    });
        }
    }

    public void login(String phone, String verificationStr, String ip) {
        if (!TextUtils.isEmpty(WeiFenQiSharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            ApiWeiFenQi.getGankService().login(phone, verificationStr, "", ip)
                    .compose(XApi.<BaseRespModelWeiFenQi<WeiFenQiLoginRespModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelWeiFenQi<WeiFenQiLoginRespModel>>getScheduler())
                    .compose(getV().<BaseRespModelWeiFenQi<WeiFenQiLoginRespModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWeiFenQi<WeiFenQiLoginRespModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            StaticUtilWeiFenQi.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelWeiFenQi<WeiFenQiLoginRespModel> gankResults) {
                            getV().loadingFl.setVisibility(View.GONE);
                            getV().rotateLoading.stop();
                            if (gankResults != null && gankResults.getCode() == 200) {
                                if (gankResults.getData() != null && gankResults.getCode() == 200) {
                                    WeiFenQiSharedPreferencesUtilis.saveStringIntoPref("phone", phone);
                                    WeiFenQiSharedPreferencesUtilis.saveIntIntoPref("mobileType", gankResults.getData().getMobileType());
                                    WeiFenQiSharedPreferencesUtilis.saveStringIntoPref("ip", ip);
                                    Router.newIntent(getV())
                                            .to(HomePageActivityWeiFenQi.class)
                                            .launch();
                                    getV().finish();
                                }
                            } else {
                                if (gankResults.getCode() == 500) {
                                    ToastUtilWeiFenQi.showShort(gankResults.getMsg());
                                }
                            }
                        }
                    });
        }
    }

    public Camera piuikytjgfgjn(Context context, boolean openOrClose) {
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

    public void tuyhfgjmnghj(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

    public void sendVerifyCode(String phone, TextView textView) {
        if (!TextUtils.isEmpty(WeiFenQiSharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            ApiWeiFenQi.getGankService().sendVerifyCode(phone)
                    .compose(XApi.<BaseRespModelWeiFenQi>getApiTransformer())
                    .compose(XApi.<BaseRespModelWeiFenQi>getScheduler())
                    .compose(getV().<BaseRespModelWeiFenQi>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWeiFenQi>() {
                        @Override
                        protected void onFail(NetError error) {
                            StaticUtilWeiFenQi.showError(getV(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelWeiFenQi gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200) {
                                    ToastUtilWeiFenQi.showShort("验证码发送成功");
                                    WeiFenQiCountDownTimerUtils mWeiFenQiCountDownTimerUtils = new WeiFenQiCountDownTimerUtils(textView, 60000, 1000);
                                    mWeiFenQiCountDownTimerUtils.start();
                                }
                            }
                        }
                    });
        }
    }

    public Camera bxfthtyui(Context context, boolean openOrClose) {
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

    public void wergrfdh(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

}
