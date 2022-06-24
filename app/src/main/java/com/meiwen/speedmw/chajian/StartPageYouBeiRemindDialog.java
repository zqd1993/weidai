package com.meiwen.speedmw.chajian;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.meiwen.speedmw.R;
import com.meiwen.speedmw.gongju.OpenYouBeiUtil;

import java.nio.charset.Charset;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPageYouBeiRemindDialog extends Dialog {

    @BindView(R.id.two_btn)
    TextView twoBtn;
    @BindView(R.id.tv_txt)
    YouBeiClickTextView tvTxt;
    @BindView(R.id.one_btn)
    TextView oneBtn;

    private OnListener onListener;

    // 密钥算法
    private static final String KEY_ALGORITHM = "AES";
    // AES/CBC/PKCS7Padding 分别对应 加密||解密算法、工作模式、填充方式
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    // 定义自己的秘钥
    public final static String SECRETKEY = "A9e4/vnQTrKF6otAGbM6zGsulKEL7b3x";
    // 位移量
    public final static String DISPLACEMENT = "9mg+!7ed8b36*w`X";

    public StartPageYouBeiRemindDialog(@NonNull Context context) {
        super(context, R.style.tran_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_page);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

        oneBtn.setOnClickListener(v -> {
            if (onListener != null) {
                onListener.oneBtnClicked();
            }
        });

        twoBtn.setOnClickListener(v -> {
            if (onListener != null) {
                onListener.twoBtnClicked();
            }
        });

        tvTxt.setText(OpenYouBeiUtil.createSpanTexts(), position -> {
            switch (position) {
                case 1:
                    if (onListener != null) {
                        onListener.zcxyClicked();
                    }
                    break;
                default:
                    if (onListener != null) {
                        onListener.ysxyClicked();
                    }
                    break;
            }
        });

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


    @Override
    public void show() {
        super.show();
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    public interface OnListener {
        void oneBtnClicked();

        void twoBtnClicked();

        void zcxyClicked();

        void ysxyClicked();
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

}
