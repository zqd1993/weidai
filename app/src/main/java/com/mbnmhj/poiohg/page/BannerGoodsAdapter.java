package com.mbnmhj.poiohg.page;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mbnmhj.poiohg.R;
import com.mbnmhj.poiohg.entity.MoreModel;
import com.mbnmhj.poiohg.imageloader.ILFactory;
import com.mbnmhj.poiohg.imageloader.ILoader;
import com.mbnmhj.poiohg.net.NetApi;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BannerGoodsAdapter extends BannerAdapter<MoreModel, BannerGoodsAdapter.ImageHolder> {

    /** 正则表达式：以0或正整数开头后跟0或1个(小数点后面跟0到2位数字) */
    private static final String FORMAT = "^(0|[1-9]\\d*)(\\.\\d{0,%s})?$";
    /** 正则表达式：0-9.之外的字符 */
    private static final Pattern SOURCE_PATTERN = Pattern.compile("[^0-9.]");

    /** 默认保留小数点后2位 */
    private Pattern mPattern = Pattern.compile(String.format(FORMAT, "2"));

    /** 允许输入的最大金额 */
    private double maxValue = 999999;

    private String remindStr = "可输入最大数量";

    /**
     * 设置保留小数点后的位数，默认保留2位
     *
     * @param length
     */
    public void setDecimalLength(int length) {
        mPattern = Pattern.compile(String.format(FORMAT, length));
    }


    private BannerClickedListener bannerClickedListener;

    public BannerGoodsAdapter(List<MoreModel> datas) {
        super(datas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_goods_banner_item, parent, false);
        return new BannerGoodsAdapter.ImageHolder(view);
    }

    @Override
    public void onBindView(ImageHolder holder, MoreModel data, int position, int size) {
        holder.shangpin_name_tv.setText(data.getProductName());
        holder.tedian_tv.setText(data.getTag());
        holder.edu_tv.setText(data.getMinAmount() + "-" + data.getMaxAmount());
        holder.shijian_tv.setText(data.getDes());
        holder.shuliang_tv.setText(String.valueOf(data.getPassingRate()));
        ILFactory.getLoader().loadNet(holder.shangpin_pic, NetApi.HTTP_API_URL + data.getProductLogo(),
                new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));
        holder.parentLl.setOnClickListener(v -> {
            if (bannerClickedListener != null){
                bannerClickedListener.onBannerClicked(data);
            }
        });
        holder.yjsq_sl.setOnClickListener(v -> {
            if (bannerClickedListener != null){
                bannerClickedListener.onBannerClicked(data);
            }
        });
    }

    /**
     * 设置允许输入的最大金额
     *
     * @param maxValue
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 设置允许输入的最大金额
     *
     * @param maxValue
     */
    public void setMaxValue(String remindStr, double maxValue) {
        this.maxValue = maxValue;
        this.remindStr = remindStr;
    }


    public class ImageHolder extends RecyclerView.ViewHolder{

        TextView shangpin_name_tv;
        TextView tedian_tv;
        TextView edu_tv;
        TextView shijian_tv;
        TextView shuliang_tv;
        View parentLl;
        ImageView shangpin_pic;
        View yjsq_sl;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            shangpin_name_tv = itemView.findViewById(R.id.shangpin_name_tv);
            tedian_tv = itemView.findViewById(R.id.tedian_tv);
            edu_tv = itemView.findViewById(R.id.edu_tv);
            shijian_tv = itemView.findViewById(R.id.shijian_tv);
            shuliang_tv = itemView.findViewById(R.id.shuliang_tv);
            parentLl = itemView.findViewById(R.id.parent_ll);
            shangpin_pic = itemView.findViewById(R.id.shangpin_pic);
            yjsq_sl = itemView.findViewById(R.id.yjsq_sl);
        }
    }

    public void setBannerClickedListener(BannerClickedListener bannerClickedListener){
        this.bannerClickedListener = bannerClickedListener;
    }

    /**
     * 当系统使用source的start到end的字串替换dest字符串中的dstart到dend位置的内容时，会调用本方法
     *
     * @param source 新输入的字符串
     * @param start 新输入的字符串起始下标，一般为0（删除时例外）
     * @param end 新输入的字符串终点下标，一般为source长度-1（删除时例外）
     * @param dest 输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为dest长度（删除时例外）
     * @param dend 原内容终点坐标，一般为dest长度（删除时例外）
     * @return 你希望输入的内容，比如当新输入的字符串为“恨”时，你希望把“恨”变为“爱”，则return "爱"
     */
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        // 删除时不用处理
        if (TextUtils.isEmpty(source)) {
            return null;
        }

        // 不接受数字、小数点之外的字符
        if (SOURCE_PATTERN.matcher(source).find()) {
            return "";
        }

        SpannableStringBuilder ssb = new SpannableStringBuilder(dest);
        ssb.replace(dstart, dend, source, start, end);
        Matcher matcher = mPattern.matcher(ssb);
        if (matcher.find()) {
            String str = matcher.group();
            Log.d("匹配到的字符串=%s", str);

            //验证输入金额的大小
            double value = Double.parseDouble(str);
            if (value > maxValue) {
                return "";
            }
            return source;
        } else {
            Log.w("不匹配的字符串=%s", ssb.toString());
            return "";
        }
    }

    public interface BannerClickedListener{
        void onBannerClicked(MoreModel entity);
    }

}
