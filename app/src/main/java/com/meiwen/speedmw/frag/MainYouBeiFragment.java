package com.meiwen.speedmw.frag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.meiwen.speedmw.R;
import com.meiwen.speedmw.yemian.ImageYouBeiAdapter;
import com.meiwen.speedmw.yemian.JumpH5YouBeiActivity;
import com.meiwen.speedmw.jiekou.HttpYouBeiApi;
import com.meiwen.speedmw.moxing.BaseYouBeiModel;
import com.meiwen.speedmw.moxing.ProductYouBeiModel;
import com.meiwen.speedmw.mvp.XFragment;
import com.meiwen.speedmw.net.ApiSubscriber;
import com.meiwen.speedmw.net.NetError;
import com.meiwen.speedmw.net.XApi;
import com.meiwen.speedmw.gongju.OpenYouBeiUtil;
import com.meiwen.speedmw.gongju.PreferencesYouBeiOpenUtil;
import com.meiwen.speedmw.yemian.MainYouBeiActivity;
import com.youth.banner.Banner;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;

public class MainYouBeiFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.goods_banner)
    Banner banner;
    @BindView(R.id.msg_layout)
    View msgLayout;
    @BindView(R.id.parent_fl)
    View parent_fl;
    @BindView(R.id.click_view)
    View click_view;
    @BindView(R.id.click_view_1)
    View click_view_1;
    @BindView(R.id.click_view_2)
    View click_view_2;
    @BindView(R.id.click_view_3)
    View click_view_3;
    // 密钥算法
    private static final String KEY_ALGORITHM = "AES";
    // AES/CBC/PKCS7Padding 分别对应 加密||解密算法、工作模式、填充方式
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    // 定义自己的秘钥
    public final static String SECRETKEY = "A9e4/vnQTrKF6otAGbM6zGsulKEL7b3x";
    // 位移量
    public final static String DISPLACEMENT = "9mg+!7ed8b36*w`X";
    private ProductYouBeiModel productYouBeiModel;

    private Bundle bundle;

    private ImageYouBeiAdapter imageAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        msgLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            productList();
        }, 200);
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
        parent_fl.setOnClickListener(v -> {
            productClick(productYouBeiModel);
        });
        click_view_1.setOnClickListener(v -> {
            ((MainYouBeiActivity) getActivity()).changePage();
        });
        click_view_2.setOnClickListener(v -> {
            ((MainYouBeiActivity) getActivity()).changePage();
        });
        click_view_3.setOnClickListener(v -> {
            ((MainYouBeiActivity) getActivity()).changePage();
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


    private void initBannerAdapter(List<ProductYouBeiModel> data) {
        imageAdapter = null;
        imageAdapter = new ImageYouBeiAdapter(data, getActivity());
        imageAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapter);
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
        if (!TextUtils.isEmpty(HttpYouBeiApi.HTTP_API_URL)) {
            if (model == null) {
                return;
            }
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
        if (!TextUtils.isEmpty(HttpYouBeiApi.HTTP_API_URL)) {
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
                            if (imageAdapter == null) {
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
                                        initBannerAdapter(baseYouBeiModel.getData());
                                    } else {
                                        if (imageAdapter == null) {
                                            noDataTv.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {
                                    if (imageAdapter == null) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (imageAdapter == null) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
        }
    }


    public void toWeb(ProductYouBeiModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenYouBeiUtil.jumpPage(getActivity(), JumpH5YouBeiActivity.class, bundle);
        }
    }
}
