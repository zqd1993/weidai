package com.rihdkauecgh.plihgnytrvfws.adapterweifenqi;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.base.SimpleRecAdapter;
import com.rihdkauecgh.plihgnytrvfws.kit.KnifeKit;
import com.rihdkauecgh.plihgnytrvfws.weifenqimodel.MineItemModelWeiFenQi;

import butterknife.BindView;

public class MineWeiFenQiAdapter extends SimpleRecAdapter<MineItemModelWeiFenQi, MineWeiFenQiAdapter.ViewHolder> {

    public MineWeiFenQiAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_wei_fen_qi_mine_item;
    }

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
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.parentLl.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, data.get(i), 1, viewHolder);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_title_tv)
        TextView tvItem;
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.parent_ll)
        View parentLl;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    public Camera zvcryfhg(Context context, boolean openOrClose) {
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

    public void uytjgj(Camera camera) {
        if (camera != null) {
            camera.release();
        }
    }

}
