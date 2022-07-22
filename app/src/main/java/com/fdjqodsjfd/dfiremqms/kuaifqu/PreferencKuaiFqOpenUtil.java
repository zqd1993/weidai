package com.fdjqodsjfd.dfiremqms.kuaifqu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import com.fdjqodsjfd.dfiremqms.KuaiFqMainApp;

import java.io.IOException;

public class PreferencKuaiFqOpenUtil {

    public static void saveInt(String key, int value) {
        KuaiFqMainApp.getPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return KuaiFqMainApp.getPreferences().getInt(key, 0);
    }

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

    public static void saveBool(String key, boolean value) {
        KuaiFqMainApp.getPreferences().edit().putBoolean(key, value).commit();
    }

    public static void saveString(String key, String value) {
        KuaiFqMainApp.getPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return KuaiFqMainApp.getPreferences().getString(key, "");
    }

    public static boolean getBool(String key) {
        return KuaiFqMainApp.getPreferences().getBoolean(key, false);
    }

}
