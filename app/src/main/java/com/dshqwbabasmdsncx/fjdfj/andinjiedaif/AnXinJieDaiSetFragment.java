package com.dshqwbabasmdsncx.fjdfj.andinjiedaif;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dshqwbabasmdsncx.fjdfj.R;
import com.dshqwbabasmdsncx.fjdfj.aanxinjiedai.AboutInfoActivityAnXinJieDai;
import com.dshqwbabasmdsncx.fjdfj.aanxinjiedai.AnXinJieDaiJumpH5Activity;
import com.dshqwbabasmdsncx.fjdfj.aanxinjiedai.DlActivityAnXinJieDai;
import com.dshqwbabasmdsncx.fjdfj.aanxinjiedai.SetItemAdapterAnXinJieDai;
import com.dshqwbabasmdsncx.fjdfj.aanxinjiedai.AnXinJieDaiZhuXiaoActivity;
import com.dshqwbabasmdsncx.fjdfj.andinjiedaiapi.HttpApiAnXinJieDai;
import com.dshqwbabasmdsncx.fjdfj.anxinjiadaim.AnXinJieDaiSetModel;
import com.dshqwbabasmdsncx.fjdfj.anxinjiadaim.BaseModelAnXinJieDai;
import com.dshqwbabasmdsncx.fjdfj.anxinjiadaim.AnXinJieDaiConfigEntity;
import com.dshqwbabasmdsncx.fjdfj.anxinjiadaim.ProductAnXinJieDaiModel;
import com.dshqwbabasmdsncx.fjdfj.mvp.XActivity;
import com.dshqwbabasmdsncx.fjdfj.mvp.XFragment;
import com.dshqwbabasmdsncx.fjdfj.net.ApiSubscriber;
import com.dshqwbabasmdsncx.fjdfj.net.NetError;
import com.dshqwbabasmdsncx.fjdfj.net.XApi;
import com.dshqwbabasmdsncx.fjdfj.anxinjiedaiu.AnXinJieDaiMyToast;
import com.dshqwbabasmdsncx.fjdfj.anxinjiedaiu.OpenAnXinJieDaiUtil;
import com.dshqwbabasmdsncx.fjdfj.anxinjiedaiu.AnXinJieDaiPreferencesOpenUtil;
import com.dshqwbabasmdsncx.fjdfj.anxinjiedaiw.AnXinJieDaiRemindDialog;
import com.dshqwbabasmdsncx.fjdfj.anxinjiedaiw.AnXinJieDaiSwitchButton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AnXinJieDaiSetFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.switch_btn)
    AnXinJieDaiSwitchButton switchBtn;
    @BindView(R.id.mail_tv)
    TextView mail_tv;

    private ProductAnXinJieDaiModel productAnXinJieDaiModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterAnXinJieDai setItemAdapterAnXinJieDai;

    private AnXinJieDaiRemindDialog dialog;

    private String mailStr = "";

    private boolean isPush = false;

    private ClipboardManager clipboard;

    private ClipData clipData;

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap mjkdfgjtdyi(byte[] bytes, int width, int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap xsdryrtu(Bitmap bitmap, int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mailStr = AnXinJieDaiPreferencesOpenUtil.getString("app_mail");
        phone = AnXinJieDaiPreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        switchBtn.setmOnCheckedChangeListener(new AnXinJieDaiSwitchButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                AnXinJieDaiPreferencesOpenUtil.saveBool("push", isChecked);
                AnXinJieDaiMyToast.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        mail_tv.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            AnXinJieDaiMyToast.showShort("复制成功");
        });
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set_an_xin_jie_dai;
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap jkkdtyuert(byte[] bytes, int width, int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap fghdtyudrtj(Bitmap bitmap, int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }

    @Override
    public void onResume() {
        super.onResume();
        productList();
        isPush = AnXinJieDaiPreferencesOpenUtil.getBool("push");
        switchBtn.setChecked(isPush);
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        AnXinJieDaiSetModel model = new AnXinJieDaiSetModel(R.drawable.metutei, "注册协议");
        AnXinJieDaiSetModel model1 = new AnXinJieDaiSetModel(R.drawable.sfgqrytrty, "隐私协议");
//        AnXinJieDaiSetModel model2 = new AnXinJieDaiSetModel(R.drawable.fdnsrtuw, "意见反馈");
        AnXinJieDaiSetModel model3 = new AnXinJieDaiSetModel(R.drawable.methwrtu, "关于我们");
//        AnXinJieDaiSetModel model4 = new AnXinJieDaiSetModel(R.drawable.zhbsrth, "个性化推荐");
        AnXinJieDaiSetModel model5 = new AnXinJieDaiSetModel(R.drawable.dfgertutru, "投诉邮箱");
        AnXinJieDaiSetModel model6 = new AnXinJieDaiSetModel(R.drawable.piuwrtu, "注销账户");
        AnXinJieDaiSetModel model7 = new AnXinJieDaiSetModel(R.drawable.fgbertuwr, "退出登录");
        List<AnXinJieDaiSetModel> list = new ArrayList<>();
        List<AnXinJieDaiSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
//        list.add(model2);
        list.add(model3);
//        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemAdapterAnXinJieDai = new SetItemAdapterAnXinJieDai(R.layout.adpater_set_item_an_xin_jie_dai, list);
        setItemAdapterAnXinJieDai.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiAnXinJieDai.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenAnXinJieDaiUtil.getValue((XActivity) getActivity(), AnXinJieDaiJumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiAnXinJieDai.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenAnXinJieDaiUtil.getValue((XActivity) getActivity(), AnXinJieDaiJumpH5Activity.class, webBundle);
                    break;
//                case 2:
//                    OpenAnXinJieDaiUtil.getValue((XActivity) getActivity(), AnXinJieDaiFeedbackActivity.class, null);
//                    break;
                case 2:
                    OpenAnXinJieDaiUtil.getValue((XActivity) getActivity(), AboutInfoActivityAnXinJieDai.class, null);
                    break;
//                case 4:
//                    dialog = new AnXinJieDaiRemindDialog(getActivity()).setCancelText("开启")
//                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
//                    dialog.setOnButtonClickListener(new AnXinJieDaiRemindDialog.OnButtonClickListener() {
//                        @Override
//                        public void onSureClicked() {
//                            AnXinJieDaiMyToast.showShort("关闭成功");
//                            dialog.dismiss();
//                        }
//
//                        @Override
//                        public void onCancelClicked() {
//                            AnXinJieDaiMyToast.showShort("开启成功");
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                    break;
                case 3:
                    getConfig();
                    break;
                case 4:
                    OpenAnXinJieDaiUtil.getValue((XActivity) getActivity(), AnXinJieDaiZhuXiaoActivity.class, null);
                    break;
                case 5:
                    dialog = new AnXinJieDaiRemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new AnXinJieDaiRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            AnXinJieDaiPreferencesOpenUtil.saveString("phone", "");
                            OpenAnXinJieDaiUtil.getValue((XActivity) getActivity(), DlActivityAnXinJieDai.class, null, true);
                        }

                        @Override
                        public void onCancelClicked() {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemAdapterAnXinJieDai);
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap mdgfhjdtyui(byte[] bytes, int width, int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap dghjdtyu(Bitmap bitmap, int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }

    public void getConfig() {
            HttpApiAnXinJieDai.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelAnXinJieDai<AnXinJieDaiConfigEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseModelAnXinJieDai<AnXinJieDaiConfigEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    mail_tv.setText(mailStr);
                                    AnXinJieDaiPreferencesOpenUtil.saveString("app_mail", mailStr);
                                    dialog = new AnXinJieDaiRemindDialog(getActivity()).setTitle("温馨提示").setCancelText("取消")
                                            .setConfirmText("复制").setContent(mailStr);
                                    dialog.setOnButtonClickListener(new AnXinJieDaiRemindDialog.OnButtonClickListener() {
                                        @Override
                                        public void onSureClicked() {
                                            dialog.dismiss();
                                            clipData = ClipData.newPlainText(null, mailStr);
                                            clipboard.setPrimaryClip(clipData);
                                            AnXinJieDaiMyToast.showShort("复制成功");
                                        }

                                        @Override
                                        public void onCancelClicked() {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                }
                            }
                        }
                    });
    }

    public void toWeb(ProductAnXinJieDaiModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenAnXinJieDaiUtil.getValue((XActivity) getActivity(), AnXinJieDaiJumpH5Activity.class, bundle);
        }
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap ScaleBitmap(byte[] bytes, int width, int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bitmap, int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }

    public void productList() {
            mobileType = AnXinJieDaiPreferencesOpenUtil.getInt("mobileType");
            phone = AnXinJieDaiPreferencesOpenUtil.getString("phone");
            HttpApiAnXinJieDai.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelAnXinJieDai<List<ProductAnXinJieDaiModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenAnXinJieDaiUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseModelAnXinJieDai<List<ProductAnXinJieDaiModel>> baseModelAnXinJieDai) {
                            if (baseModelAnXinJieDai != null) {
                                if (baseModelAnXinJieDai.getCode() == 200 && baseModelAnXinJieDai.getData() != null) {
                                    if (baseModelAnXinJieDai.getData() != null && baseModelAnXinJieDai.getData().size() > 0) {
                                        productAnXinJieDaiModel = baseModelAnXinJieDai.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductAnXinJieDaiModel model) {
            if (model == null) {
                return;
            }
            phone = AnXinJieDaiPreferencesOpenUtil.getString("phone");
            HttpApiAnXinJieDai.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelAnXinJieDai>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseModelAnXinJieDai baseModelAnXinJieDai) {
                            toWeb(model);
                        }
                    });
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public static Bitmap yytudfgjhty(byte[] bytes, int width, int height){
        //获取图片的解码参数设置
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true仅仅解码图片的边缘
        options.inJustDecodeBounds = true;
        //对图片进行解码,如果指定了inJustDecodeBounds=true，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int sampleSizeX = width/outWidth;
        int sampleSizeY = height/outHeight;
        int simpleSize = sampleSizeX < sampleSizeY ? sampleSizeX : sampleSizeY;
        //设置inJustDecodeBounds为false重新将图片读进内存中
        options.inJustDecodeBounds = false;
        //实际要进行缩放的比例
        options.inSampleSize = simpleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 图片质量压缩
     * @param bitmap  需要质量压缩的图片
     * @param size    指定最大要压缩成的大小，单位为k
     * @return
     */
    public static Bitmap nnsftyhusrtu(Bitmap bitmap, int size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //将压缩后的数据放入bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        int quality = 100;
        while(bos.toByteArray().length / 1024 > size){
            //循环判断如果压缩后的图片大于100k，则清空bos，质量压缩比减小10%
            bos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        //通过字节输入流转为bitmap
        return BitmapFactory.decodeStream(bis,null,null);
    }
}
