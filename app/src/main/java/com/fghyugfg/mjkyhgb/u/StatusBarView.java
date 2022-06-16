package com.fghyugfg.mjkyhgb.u;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.View;

import com.fghyugfg.mjkyhgb.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class StatusBarView extends View {
    public StatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusBarView(Context context) {
        super(context);
    }

    /**
     * 格式化价格，四舍五入保留两位小数
     *
     * @param price 价格
     * @return 位小数四舍五入保留两
     * * 小数计算（四舍五入）：解决DecimalFormat("#.00")使用时小数点后第三位值为5的时候无法进位的问题
     * * @param
     * * @return
     */
    public static String formatPrice(Double price) {
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String startStr = df.format(price);
        String startDecimal = startStr.split("\\.")[1];
        Double jishu = 0.01;
        Double endVal = 0.0;
        if (startDecimal.length() > 2 && "5".equals(String.valueOf(startDecimal.charAt(2)))) {
            endVal = Double.valueOf(startStr.substring(0, startStr.length() - 1)) + jishu;
        } else {
            endVal = Double.valueOf(df.format(price));
        }
        return df.format(endVal);
    }

    public static SpannableString formatDoublePrice(Double price) {
        String str = formatPrice(price);
        SpannableString spannableString = new SpannableString(str);
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(0.7f);
        spannableString.setSpan(relativeSizeSpan, str.indexOf(".") + 1, str.indexOf(".") + 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString updateTextColor(int endIndex, String str, Context context) {
        SpannableString spannableString = new SpannableString(str);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.book_loading_background));
        spannableString.setSpan(foregroundColorSpan, 0, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
