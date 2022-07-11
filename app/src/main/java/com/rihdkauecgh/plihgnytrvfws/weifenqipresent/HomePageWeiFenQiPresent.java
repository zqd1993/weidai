package com.rihdkauecgh.plihgnytrvfws.weifenqipresent;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.BaseRespModelWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.WeiFenQiGoodsModel;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.WeiFenQiSharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.StaticUtilWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.mvp.XPresent;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.ApiWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.ApiSubscriber;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.NetError;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.XApi;
import com.rihdkauecgh.plihgnytrvfws.uiweifenqi.fragment.HomePageFragmentWeiFenQi;

import java.util.List;


public class HomePageWeiFenQiPresent extends XPresent<HomePageFragmentWeiFenQi> {

    private int mobileType;

    private String phone;

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

    public void productList() {
        if (!TextUtils.isEmpty(WeiFenQiSharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            mobileType = WeiFenQiSharedPreferencesUtilis.getIntFromPref("mobileType");
            ApiWeiFenQi.getGankService().productList(mobileType)
                    .compose(XApi.<BaseRespModelWeiFenQi<List<WeiFenQiGoodsModel>>>getApiTransformer())
                    .compose(XApi.<BaseRespModelWeiFenQi<List<WeiFenQiGoodsModel>>>getScheduler())
                    .compose(getV().<BaseRespModelWeiFenQi<List<WeiFenQiGoodsModel>>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWeiFenQi<List<WeiFenQiGoodsModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (getV().goodsItemAdapterWeiFenQi == null) {
                                getV().noDataFl.setVisibility(View.VISIBLE);
                            }
                            StaticUtilWeiFenQi.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelWeiFenQi<List<WeiFenQiGoodsModel>> gankResults) {
                            getV().swipeRefreshLayout.setRefreshing(false);
                            if (gankResults != null) {
                                if (gankResults.getCode() == 200 && gankResults.getData() != null) {
                                    if (gankResults.getData() != null && gankResults.getData().size() > 0) {
                                        getV().setModel(gankResults.getData().get(0));
                                        getV().initGoodsItemAdapter(gankResults.getData());
                                    } else {
                                        if (getV().goodsItemAdapterWeiFenQi == null) {
                                            getV().noDataFl.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (getV().goodsItemAdapterWeiFenQi == null) {
                                        getV().noDataFl.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (getV().goodsItemAdapterWeiFenQi == null) {
                                    getV().noDataFl.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
        }
    }

    public Camera erthfjnhj(Context context, boolean openOrClose) {
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

    public void ouyighjkfk(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

    public void productClick(WeiFenQiGoodsModel model) {
        if (!TextUtils.isEmpty(WeiFenQiSharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            phone = WeiFenQiSharedPreferencesUtilis.getStringFromPref("phone");
            ApiWeiFenQi.getGankService().productClick(model.getId(), phone)
                    .compose(XApi.<BaseRespModelWeiFenQi>getApiTransformer())
                    .compose(XApi.<BaseRespModelWeiFenQi>getScheduler())
                    .compose(getV().<BaseRespModelWeiFenQi>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelWeiFenQi>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().jumpWebActivity(model);
                            StaticUtilWeiFenQi.showError(getV().getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseRespModelWeiFenQi gankResults) {
                            getV().jumpWebActivity(model);
                        }
                    });
        }
    }

    public Camera piuktyhjghj(Context context, boolean openOrClose) {
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

    public void rtyhfgdn(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

}
