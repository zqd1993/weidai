package com.mbnmhj.poiohg.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.widget.Toast;

import com.mbnmhj.poiohg.BaseApp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewToast {

    private static long lastClick = 0;

    /***
     * 从相册中取图片
     */
    public static void pickPhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, 999);
    }

    public static boolean isFast() {
        boolean flag = true;
        long currentClick = System.currentTimeMillis();
        if ((currentClick - lastClick) >= 400) {
            flag = false;
        }
        lastClick = currentClick;
        return flag;
    }

    /**
     * @param
     * @description 裁剪图片
     * @author ldm
     * @time 2016/11/30 15:19
     */
    public static void startPhotoZoom(Activity activity, Uri uri, int REQUE_CODE_CROP) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("outputX", (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 105, BaseApp.getContext().getResources().getDisplayMetrics()));
        intent.putExtra("outputY", (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 105, BaseApp.getContext().getResources().getDisplayMetrics()));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        String picPath = Environment.getExternalStorageDirectory() + "/" + ".png";
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file://" + picPath));
//        EventConstants.picCutEvent(picPath);
        activity.startActivityForResult(intent, REQUE_CODE_CROP);
    }


    private NewToast() {
    }

    public static String getCharacterAndNumber() {
        String rel = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        rel = formatter.format(curDate);
        return rel;
    }

    public static void showShort(String message) {
        if (isFast()) {
            return;
        }
        Toast.makeText(BaseApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
