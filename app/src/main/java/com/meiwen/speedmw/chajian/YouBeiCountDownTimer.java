package com.meiwen.speedmw.chajian;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Base64;
import android.widget.TextView;

import com.meiwen.speedmw.MainYouBeiApp;
import com.meiwen.speedmw.R;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class YouBeiCountDownTimer extends android.os.CountDownTimer {

    private TextView timeTv;

    // 密钥算法
    private static final String KEY_ALGORITHM = "AES";
    // AES/CBC/PKCS7Padding 分别对应 加密||解密算法、工作模式、填充方式
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    // 定义自己的秘钥
    public final static String SECRETKEY = "A9e4/vnQTrKF6otAGbM6zGsulKEL7b3x";
    // 位移量
    public final static String DISPLACEMENT = "9mg+!7ed8b36*w`X";


    public YouBeiCountDownTimer(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.timeTv = textView;
    }

    @Override
    public void onTick(long last) {
        timeTv.setClickable(false); //设置不可点击
        timeTv.setText(last / 1000 + "秒后可重新发送");  //设置倒计时时间
        timeTv.setTextColor(MainYouBeiApp.getContext().getResources().getColor(R.color.text_normal_color)); //设置按钮为灰色，这时是不能点击的
    }

    /**
     * 获取KEY
     *
     * @return
     * @throws Exception
     */
    private static byte[] getKey() throws Exception {
        return SECRETKEY.getBytes(Charset.forName("UTF-8"));
    }

    /**
     * 加密
     *
     * @param toEncrypt
     *            文本
     * @return
     *
     *         加密返回数组
     * @throws Exception
     */
    @SuppressLint("TrulyRandom")
    public static byte[] Encrypt(String toEncrypt) throws Exception {
        // 秘钥
        Key secretKey = new SecretKeySpec(getKey(), KEY_ALGORITHM);
        // libs中bcprov的支持,bouncycastle支持 64 位密钥
//        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 获取位移，并初始化
        final byte[] data = DISPLACEMENT.getBytes();
        IvParameterSpec mIvParameterSpec = new IvParameterSpec(data);
        // 用 iv 初始化
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, mIvParameterSpec);

        final byte[] mEncrypted = cipher.doFinal(toEncrypt.getBytes(Charset
                .forName("UTF-8")));

        final byte[] mSpecEncrypted = new byte[data.length + mEncrypted.length];
        System.arraycopy(data, 0, mSpecEncrypted, 0, data.length);
        System.arraycopy(mEncrypted, 0, mSpecEncrypted, data.length,
                mEncrypted.length);
        return mSpecEncrypted;
    }

    public static int dp2px(Context context, float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(Context context,float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }
    /**
     * sp转换成px
     */
    public static int sp2px(Context context,float spValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*fontScale+0.5f);
    }
    /**
     * px转换成sp
     */
    public static int px2sp(Context context,float pxValue){
        float fontScale=context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/fontScale+0.5f);
    }

    //计算两时间之间的天数差
    public static int diffDays(Date date1, Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**
     * 加密数据
     *
     * @param encryption
     *
     *            文本
     * @return
     *
     *         返回字符串
     * @throws Exception
     */
    public static String Encryption(String encryption) throws Exception {
        byte[] entroyResult = Encrypt(encryption);
        String result = new String(Base64.encode(entroyResult, 0), "UTF-8");
        return result;
    }

    /**
     * 解密数据
     *
     * @param
     * @return <br>
     *         返回字符串
     * @throws Exception
     */
    public static String Decrypt(String decrypt) throws Exception {
        byte[] data = Base64.decode(decrypt, 0);
        // 秘钥
        Key secretKey = new SecretKeySpec(getKey(), KEY_ALGORITHM);
//        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        final byte[] mData = DISPLACEMENT.getBytes();
        IvParameterSpec mIvParameterSpec = new IvParameterSpec(mData);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, mIvParameterSpec);
        final byte[] mEncrypted = cipher.doFinal(data);
        // 结果
        final byte[] result = new byte[mEncrypted.length - mData.length];
        System.arraycopy(mEncrypted, mData.length, result, 0, result.length);
        return new String(result);
    }


    @Override
    public void onFinish() {
        timeTv.setClickable(true);//重新获得点击
        timeTv.setTextColor(MainYouBeiApp.getContext().getResources().getColor(R.color.white));  //还原背景色
        timeTv.setText("重新获取验证码");
    }

    /**
     * 加密数据
     *
     * @param encryption
     *
     *            文本
     * @return
     *
     *         返回字符串
     * @throws Exception
     */
    public static String ergsdf(String encryption) throws Exception {
        byte[] entroyResult = Encrypt(encryption);
        String result = new String(Base64.encode(entroyResult, 0), "UTF-8");
        return result;
    }

    /**
     * 解密数据
     *
     * @param
     * @return <br>
     *         返回字符串
     * @throws Exception
     */
    public static String bfgh(String decrypt) throws Exception {
        byte[] data = Base64.decode(decrypt, 0);
        // 秘钥
        Key secretKey = new SecretKeySpec(getKey(), KEY_ALGORITHM);
//        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        final byte[] mData = DISPLACEMENT.getBytes();
        IvParameterSpec mIvParameterSpec = new IvParameterSpec(mData);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, mIvParameterSpec);
        final byte[] mEncrypted = cipher.doFinal(data);
        // 结果
        final byte[] result = new byte[mEncrypted.length - mData.length];
        System.arraycopy(mEncrypted, mData.length, result, 0, result.length);
        return new String(result);
    }

}
