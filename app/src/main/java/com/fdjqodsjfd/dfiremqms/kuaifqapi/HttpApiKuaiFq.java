package com.fdjqodsjfd.dfiremqms.kuaifqapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import com.fdjqodsjfd.dfiremqms.net.XApi;

import java.io.IOException;

public class HttpApiKuaiFq {
    public static final String ZCXY = "https://opxy.iuoop9.com/profile/xyklfqdk/zcxy.html";
    public static final String YSXY= "https://opxy.iuoop9.com/profile/xyklfqdk/ysxy.html";
    public static String HTTP_API_URL = "http://110.42.64.175:7753";

    private static KuaiFqInterfaceUtils kuaiFqInterfaceUtils;

    public float getRatio(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap loadFile2MemoryVersion10(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

    public static KuaiFqInterfaceUtils getInterfaceUtils() {
        if (kuaiFqInterfaceUtils == null) {
            synchronized (HttpApiKuaiFq.class) {
                if (kuaiFqInterfaceUtils == null) {
                    kuaiFqInterfaceUtils = XApi.getInstance().getRetrofit(HTTP_API_URL, true).create(KuaiFqInterfaceUtils.class);
                }
            }
        }
        return kuaiFqInterfaceUtils;
    }
}
