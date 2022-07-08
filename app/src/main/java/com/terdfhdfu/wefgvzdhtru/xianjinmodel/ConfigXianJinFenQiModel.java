package com.terdfhdfu.wefgvzdhtru.xianjinmodel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;

public class ConfigXianJinFenQiModel {

    @SerializedName("COMPANY_NAME")
    private String companyName;

    @SerializedName("IS_SELECT_LOGIN")
    private String isSelectLogin;

    @SerializedName("IS_CODE_LOGIN")
    private String isCodeLogin;

    @SerializedName("APP_MAIL")
    private String appMail;

    @SerializedName("APP_NAME")
    private String appName;

    @SerializedName("IS_ONEBUTTON_LOGIN")
    private String isOneButtonLogin;

    @SerializedName("COMPANY_ADDRESS")
    private String companyAddress;

    @SerializedName("DOMAIN_NAME")
    private String domainName;

    public Bitmap createBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap createBitmapOnHide(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap createBitmapTransparentBg(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] createBitmapOnHideToBytes(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }

    public String getIsSelectLogin() {
        return isSelectLogin;
    }

    public void setIsSelectLogin(String isSelectLogin) {
        this.isSelectLogin = isSelectLogin;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIsCodeLogin() {
        return isCodeLogin;
    }

    public void setIsCodeLogin(String isCodeLogin) {
        this.isCodeLogin = isCodeLogin;
    }

    public String getAppMail() {
        return appMail;
    }

    public void setAppMail(String appMail) {
        this.appMail = appMail;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIsOneButtonLogin() {
        return isOneButtonLogin;
    }

    public void setIsOneButtonLogin(String isOneButtonLogin) {
        this.isOneButtonLogin = isOneButtonLogin;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Bitmap retgdzbxbn(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        view.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能
        return bitmap;
    }

    public Bitmap ouifuyijhgkjhk(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public Bitmap uhggfjhgfdh(View v, int shareSize) {
        int w = v.getWidth();
        int h = v.getHeight() - shareSize;
        if (h < 0) {
            h = 0;
        }
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        v.draw(c);
        v.setLayerType(View.LAYER_TYPE_NONE, null);
        return bmp;
    }

    public byte[] rethxfghngh(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(0x00ffffff);
        v.layout(0, 0, w, h);
        v.draw(c);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        bmp.recycle();
        return bytes;
    }
}
