package com.rihdkauecgh.plihgnytrvfws.uiweifenqi.fragment;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.adapterweifenqi.MineWeiFenQiAdapter;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;
import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.BaseRespModelWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.ConfigWeiFenQiModel;
import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.MineItemModelWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.ApiSubscriber;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.NetError;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.XApi;
import com.rihdkauecgh.plihgnytrvfws.uiweifenqi.LoginWeiFenQiActivity;
import com.rihdkauecgh.plihgnytrvfws.uiweifenqi.WeiFenQiWebViewActivity;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.StaticUtilWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.ToastUtilWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.weifenqiutils.WeiFenQiSharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.netweifenqi.ApiWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.rihdkauecgh.plihgnytrvfws.widgetweifenqi.NormalWeiFenQiDialog;
import com.rihdkauecgh.plihgnytrvfws.mvp.XFragment;
import com.rihdkauecgh.plihgnytrvfws.uiweifenqi.activity.AboutUsActivityWeiFenQi;
import com.rihdkauecgh.plihgnytrvfws.uiweifenqi.activity.CancellationWeiFenQiAccountActivity;
import com.rihdkauecgh.plihgnytrvfws.uiweifenqi.activity.WeiFenQiFeedBackActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineWeiFenQiFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private MineWeiFenQiAdapter mineWeiFenQiAdapter;
    private List<MineItemModelWeiFenQi> list;
    private int[] imgRes = {R.drawable.wd_icon_zcxy, R.drawable.wd_icon_ysxy, R.drawable.wd_icon_yjfk, R.drawable.wd_icon_gywm,
            R.drawable.wd_icon_xxts, R.drawable.wd_tsyx, R.drawable.wd_icon_zcz, R.drawable.wd_icon_zczh};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "个性化推荐", "投诉邮箱", "注销账户", "退出登录"};
    private Bundle bundle;
    private NormalWeiFenQiDialog normalWeiFenQiDialog;
    private String mailStr = "", phone = "";

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
        list = new ArrayList<>();
        mailStr = WeiFenQiSharedPreferencesUtilis.getStringFromPref("APP_MAIL");
        phone = WeiFenQiSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            MineItemModelWeiFenQi model = new MineItemModelWeiFenQi();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_wei_fen_qi_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineWeiFenQiAdapter == null) {
            mineWeiFenQiAdapter = new MineWeiFenQiAdapter(getActivity());
            mineWeiFenQiAdapter.setData(list);
            mineWeiFenQiAdapter.setHasStableIds(true);
            mineWeiFenQiAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelWeiFenQi, MineWeiFenQiAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelWeiFenQi model, int tag, MineWeiFenQiAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiWeiFenQi.PRIVACY_POLICY);
                            StaticUtilWeiFenQi.getValue((XActivity) getActivity(), WeiFenQiWebViewActivity.class, bundle);
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiWeiFenQi.USER_SERVICE_AGREEMENT);
                            StaticUtilWeiFenQi.getValue((XActivity) getActivity(), WeiFenQiWebViewActivity.class, bundle);
                            break;
                        case 2:
                            StaticUtilWeiFenQi.getValue((XActivity) getActivity(), WeiFenQiFeedBackActivity.class, null);
                            break;
                        case 3:
                            StaticUtilWeiFenQi.getValue((XActivity) getActivity(), AboutUsActivityWeiFenQi.class, null);
                            break;
                        case 4:
                            normalWeiFenQiDialog = new NormalWeiFenQiDialog(getActivity());
                            normalWeiFenQiDialog.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        ToastUtilWeiFenQi.showShort("开启成功");
                                        normalWeiFenQiDialog.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        ToastUtilWeiFenQi.showShort("关闭成功");
                                        normalWeiFenQiDialog.dismiss();
                                    }).show();
                            break;
                        case 5:
                            getGankData();
                            break;
                        case 6:
                            StaticUtilWeiFenQi.getValue((XActivity) getActivity(), CancellationWeiFenQiAccountActivity.class, null);
                            break;
                        case 7:
                            normalWeiFenQiDialog = new NormalWeiFenQiDialog(getActivity());
                            normalWeiFenQiDialog.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalWeiFenQiDialog.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        normalWeiFenQiDialog.dismiss();
                                        WeiFenQiSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                                        StaticUtilWeiFenQi.getValue((XActivity) getActivity(), LoginWeiFenQiActivity.class, null, true);
                                    }).show();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineWeiFenQiAdapter);
        }
    }

    public Camera piuikfhkhgj(Context context, boolean openOrClose) {
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

    public void eryfhfdj(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

    @Override
    public void onDestroy() {
        if (normalWeiFenQiDialog != null) {
            normalWeiFenQiDialog.dismiss();
            normalWeiFenQiDialog = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
        ApiWeiFenQi.getGankService().getGankData()
                .compose(XApi.<BaseRespModelWeiFenQi<ConfigWeiFenQiModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelWeiFenQi<ConfigWeiFenQiModel>>getScheduler())
                .compose(this.<BaseRespModelWeiFenQi<ConfigWeiFenQiModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelWeiFenQi<ConfigWeiFenQiModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespModelWeiFenQi<ConfigWeiFenQiModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                mailStr = gankResults.getData().getAppMail();
                                WeiFenQiSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                normalWeiFenQiDialog = new NormalWeiFenQiDialog(getActivity());
                                normalWeiFenQiDialog.setTitle("温馨提示")
                                        .setContent(mailStr)
                                        .showOnlyBtn().show();
                            }
                        }
                    }
                });
    }

    public Camera ouyighjkfg(Context context, boolean openOrClose) {
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

    public void reyhfgjgj(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

}
