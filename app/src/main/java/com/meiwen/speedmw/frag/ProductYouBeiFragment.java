package com.meiwen.speedmw.frag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.meiwen.speedmw.R;
import com.meiwen.speedmw.yemian.JumpH5YouBeiActivity;
import com.meiwen.speedmw.jiekou.HttpYouBeiApi;
import com.meiwen.speedmw.imageloader.ILFactory;
import com.meiwen.speedmw.imageloader.ILoader;
import com.meiwen.speedmw.moxing.BaseYouBeiModel;
import com.meiwen.speedmw.moxing.ProductYouBeiModel;
import com.meiwen.speedmw.mvp.XFragment;
import com.meiwen.speedmw.net.ApiSubscriber;
import com.meiwen.speedmw.net.NetError;
import com.meiwen.speedmw.net.XApi;
import com.meiwen.speedmw.gongju.OpenYouBeiUtil;
import com.meiwen.speedmw.gongju.PreferencesYouBeiOpenUtil;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;

public class ProductYouBeiFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.goods_list_ll)
    LinearLayout goodsListLl;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.msg_layout)
    View msg_layout;
    private ProductYouBeiModel productYouBeiModel;

    private Bundle bundle;

    // 密钥算法
    private static final String KEY_ALGORITHM = "AES";
    // AES/CBC/PKCS7Padding 分别对应 加密||解密算法、工作模式、填充方式
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    // 定义自己的秘钥
    public final static String SECRETKEY = "A9e4/vnQTrKF6otAGbM6zGsulKEL7b3x";
    // 位移量
    public final static String DISPLACEMENT = "9mg+!7ed8b36*w`X";

    @Override
    public void initData(Bundle savedInstanceState) {
        jx_bg.setVisibility(View.VISIBLE);
        main_top_img.setVisibility(View.GONE);
        goodsListLl.setVisibility(View.VISIBLE);
        msg_layout.setVisibility(View.GONE);
        productList();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(productYouBeiModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(productYouBeiModel);
        });
        goodsListLl.setOnClickListener(v -> {
            productClick(productYouBeiModel);
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
     * @param toEncrypt 文本
     * @return 加密返回数组
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
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void productClick(ProductYouBeiModel model) {
        if (!TextUtils.isEmpty(PreferencesYouBeiOpenUtil.getString("HTTP_API_URL"))) {
            if (model != null) {
                phone = PreferencesYouBeiOpenUtil.getString("phone");
                HttpYouBeiApi.getInterfaceUtils().productClick(model.getId(), phone)
                        .compose(XApi.getApiTransformer())
                        .compose(XApi.getScheduler())
                        .compose(bindToLifecycle())
                        .subscribe(new ApiSubscriber<BaseYouBeiModel>() {
                            @Override
                            protected void onFail(NetError error) {
                                toWeb(model);
                            }

                            @Override
                            public void onNext(BaseYouBeiModel baseYouBeiModel) {
                                toWeb(model);
                            }
                        });
            }
        }
    }

    /**
     * px转换成dp
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转换成px
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转换成sp
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    //计算两时间之间的天数差
    public static int diffDays(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 加密数据
     *
     * @param encryption 文本
     * @return 返回字符串
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
     * 返回字符串
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

    public void productList() {
        if (!TextUtils.isEmpty(PreferencesYouBeiOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = PreferencesYouBeiOpenUtil.getInt("mobileType");
            HttpYouBeiApi.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseYouBeiModel<List<ProductYouBeiModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            setRefreshing.setRefreshing(false);
                            OpenYouBeiUtil.showErrorInfo(getActivity(), error);
                            if (goodsListLl.getChildCount() == 0) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNext(BaseYouBeiModel<List<ProductYouBeiModel>> baseYouBeiModel) {
                            setRefreshing.setRefreshing(false);
                            if (baseYouBeiModel != null) {
                                if (baseYouBeiModel.getCode() == 200 && baseYouBeiModel.getData() != null) {
                                    if (baseYouBeiModel.getData() != null && baseYouBeiModel.getData().size() > 0) {
                                        productYouBeiModel = baseYouBeiModel.getData().get(0);
                                        addProductView(baseYouBeiModel.getData());
                                    } else {
                                        if (goodsListLl.getChildCount() == 0) {
                                            noDataTv.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (goodsListLl.getChildCount() == 0) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (goodsListLl.getChildCount() == 0) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
        }
    }

    private void addProductView(List<ProductYouBeiModel> mList) {
        goodsListLl.removeAllViews();
        for (ProductYouBeiModel model : mList) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_product_view, null);
            ImageView pic = view.findViewById(R.id.shangpin_img);
            TextView product_name_tv = view.findViewById(R.id.shangpin_name_tv);
            TextView remind_tv = view.findViewById(R.id.tedian_tv);
            TextView money_number_tv = view.findViewById(R.id.edu_tv);
            View parentFl = view.findViewById(R.id.parent_fl);
            View yjsqSl = view.findViewById(R.id.yjsq_sl);
            TextView shuliang_tv = view.findViewById(R.id.shuliang_tv);
            shuliang_tv.setText(String.valueOf(model.getPassingRate()));
            if (!TextUtils.isEmpty(PreferencesYouBeiOpenUtil.getString("HTTP_API_URL"))) {
                ILFactory.getLoader().loadNet(pic, PreferencesYouBeiOpenUtil.getString("HTTP_API_URL") + model.getProductLogo(),
                        new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
            }
            product_name_tv.setText(model.getProductName());
            remind_tv.setText(model.getTag());
            money_number_tv.setText(model.getMinAmount() + "-" + model.getMaxAmount());
            parentFl.setOnClickListener(v -> {
                productClick(model);
            });
            pic.setOnClickListener(v -> {
                productClick(model);
            });
            yjsqSl.setOnClickListener(v -> {
                productClick(model);
            });
            goodsListLl.addView(view);
        }
    }

    /**
     * px转换成dp
     */
    public static int wevfg(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转换成px
     */
    public static int tyutyuj(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转换成sp
     */
    public static int nhgjyuu(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    //计算两时间之间的天数差
    public static int zxcsd(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 加密数据
     *
     * @param encryption 文本
     * @return 返回字符串
     * @throws Exception
     */
    public static String rtdfbfgb(String encryption) throws Exception {
        byte[] entroyResult = Encrypt(encryption);
        String result = new String(Base64.encode(entroyResult, 0), "UTF-8");
        return result;
    }

    /**
     * 解密数据
     *
     * @param
     * @return <br>
     * 返回字符串
     * @throws Exception
     */
    public static String rbdfb(String decrypt) throws Exception {
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

    public void toWeb(ProductYouBeiModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("title", model.getProductName());
            OpenYouBeiUtil.jumpPage(getActivity(), JumpH5YouBeiActivity.class, bundle);
        }
    }
}
