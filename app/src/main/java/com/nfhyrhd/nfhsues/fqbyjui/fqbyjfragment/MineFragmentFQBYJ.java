package com.nfhyrhd.nfhsues.fqbyjui.fqbyjfragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nfhyrhd.nfhsues.R;
import com.nfhyrhd.nfhsues.fqbyjadapter.FQBYJMineAdapter;
import com.nfhyrhd.nfhsues.fqbyjmodel.BaseRespModelFQBYJ;
import com.nfhyrhd.nfhsues.fqbyjmodel.FQBYJConfigModel;
import com.nfhyrhd.nfhsues.fqbyjmodel.MineItemFQBYJModel;
import com.nfhyrhd.nfhsues.mvp.XActivity;
import com.nfhyrhd.nfhsues.fqbyjnet.ApiSubscriber;
import com.nfhyrhd.nfhsues.fqbyjnet.NetError;
import com.nfhyrhd.nfhsues.fqbyjnet.XApi;
import com.nfhyrhd.nfhsues.fqbyjui.FQBYJLoginActivity;
import com.nfhyrhd.nfhsues.fqbyjui.WebViewFQBYJActivity;
import com.nfhyrhd.nfhsues.fqbyjutils.SharedPreferencesUtilisFQBYJ;
import com.nfhyrhd.nfhsues.fqbyjutils.FQBYJStaticUtil;
import com.nfhyrhd.nfhsues.fqbyjutils.ToastUtilFQBYJ;
import com.nfhyrhd.nfhsues.fqbyjnet.FQBYJApi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.nfhyrhd.nfhsues.fqbyjwidget.NormalFQBYJDialog;
import com.nfhyrhd.nfhsues.mvp.XFragment;
import com.nfhyrhd.nfhsues.fqbyjui.fqbyjactivity.AboutUsActivityFQBYJ;
import com.nfhyrhd.nfhsues.fqbyjui.fqbyjactivity.FQBYJCancellationAccountActivity;
import com.nfhyrhd.nfhsues.fqbyjui.fqbyjactivity.FeedBackFQBYJActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragmentFQBYJ extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private FQBYJMineAdapter FQBYJMineAdapter;
    private List<MineItemFQBYJModel> list;
    private int[] imgRes = {R.drawable.kazdfvheru, R.drawable.kawdry, R.drawable.kodtyaeyu, R.drawable.ksjkrtu,
            R.drawable.kksrtui, R.drawable.kaeraeu, R.drawable.keryaeruy, R.drawable.kaseraeyu};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "个性化推荐", "投诉邮箱", "注销账户", "退出登录"};
    private Bundle bundle;
    private NormalFQBYJDialog normalFQBYJDialog;
    private String mailStr = "", phone = "";

    public Bitmap ftysrusrtu(Context context, Bitmap originBitmap, int radius){
        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript,originBitmap);
        Allocation output = Allocation.createTyped(renderScript,input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(originBitmap);

        return originBitmap;
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public Bitmap vghsfsrtu(byte[] bytes,int width,int height){
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
    public Bitmap fghsfusrtu(Bitmap bitmap, int size){
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
        list = new ArrayList<>();
        mailStr = SharedPreferencesUtilisFQBYJ.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesUtilisFQBYJ.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            MineItemFQBYJModel model = new MineItemFQBYJModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_f_q_b_y_j_mine;
    }

    public Bitmap nnsftyusrtu(Context context, Bitmap originBitmap, int radius){
        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript,originBitmap);
        Allocation output = Allocation.createTyped(renderScript,input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(originBitmap);

        return originBitmap;
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public Bitmap aaeryrtu(byte[] bytes,int width,int height){
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
    public Bitmap jkkdtyiosrtu(Bitmap bitmap, int size){
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
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (FQBYJMineAdapter == null) {
            FQBYJMineAdapter = new FQBYJMineAdapter(getActivity());
            FQBYJMineAdapter.setData(list);
            FQBYJMineAdapter.setHasStableIds(true);
            FQBYJMineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemFQBYJModel, FQBYJMineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemFQBYJModel model, int tag, FQBYJMineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", FQBYJApi.PRIVACY_POLICY);
                            FQBYJStaticUtil.getValue((XActivity) getActivity(), WebViewFQBYJActivity.class, bundle);
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", FQBYJApi.USER_SERVICE_AGREEMENT);
                            FQBYJStaticUtil.getValue((XActivity) getActivity(), WebViewFQBYJActivity.class, bundle);
                            break;
                        case 2:
                            FQBYJStaticUtil.getValue((XActivity) getActivity(), FeedBackFQBYJActivity.class, null);
                            break;
                        case 3:
                            FQBYJStaticUtil.getValue((XActivity) getActivity(), AboutUsActivityFQBYJ.class, null);
                            break;
                        case 4:
                            normalFQBYJDialog = new NormalFQBYJDialog(getActivity());
                            normalFQBYJDialog.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        ToastUtilFQBYJ.showShort("开启成功");
                                        normalFQBYJDialog.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        ToastUtilFQBYJ.showShort("关闭成功");
                                        normalFQBYJDialog.dismiss();
                                    }).show();
                            break;
                        case 5:
                            getGankData();
                            break;
                        case 6:
                            FQBYJStaticUtil.getValue((XActivity) getActivity(), FQBYJCancellationAccountActivity.class, null);
                            break;
                        case 7:
                            normalFQBYJDialog = new NormalFQBYJDialog(getActivity());
                            normalFQBYJDialog.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalFQBYJDialog.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        normalFQBYJDialog.dismiss();
                                        SharedPreferencesUtilisFQBYJ.saveStringIntoPref("phone", "");
                                        FQBYJStaticUtil.getValue((XActivity) getActivity(), FQBYJLoginActivity.class, null, true);
                                    }).show();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(FQBYJMineAdapter);
        }
    }

    public Bitmap fghsftusrtu(Context context, Bitmap originBitmap, int radius){
        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript,originBitmap);
        Allocation output = Allocation.createTyped(renderScript,input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(originBitmap);

        return originBitmap;
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public Bitmap fghsrtufgh(byte[] bytes,int width,int height){
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
    public Bitmap compressBitmap(Bitmap bitmap, int size){
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
    public void onDestroy() {
        if (normalFQBYJDialog != null) {
            normalFQBYJDialog.dismiss();
            normalFQBYJDialog = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
            FQBYJApi.getGankService().getGankData()
                    .compose(XApi.<BaseRespModelFQBYJ<FQBYJConfigModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelFQBYJ<FQBYJConfigModel>>getScheduler())
                    .compose(this.<BaseRespModelFQBYJ<FQBYJConfigModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelFQBYJ<FQBYJConfigModel>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseRespModelFQBYJ<FQBYJConfigModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    mailStr = gankResults.getData().getAppMail();
                                    SharedPreferencesUtilisFQBYJ.saveStringIntoPref("APP_MAIL", mailStr);
                                    normalFQBYJDialog = new NormalFQBYJDialog(getActivity());
                                    normalFQBYJDialog.setTitle("温馨提示")
                                            .setContent(mailStr)
                                            .showOnlyBtn().show();
                                }
                            }
                        }
                    });
    }

    public Bitmap handleGlassblur(Context context, Bitmap originBitmap, int radius){
        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript,originBitmap);
        Allocation output = Allocation.createTyped(renderScript,input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(originBitmap);

        return originBitmap;
    }

    /**
     * 根据指定的宽、高，对图片进行二次采样
     * @param bytes
     * @return
     */
    public Bitmap ScaleBitmap(byte[] bytes,int width,int height){
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
    public Bitmap fgfgshsryuey(Bitmap bitmap, int size){
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
