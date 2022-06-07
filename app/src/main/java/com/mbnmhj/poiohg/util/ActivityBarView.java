package com.mbnmhj.poiohg.util;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityBarView extends View {
    public ActivityBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static void setTextStyle(String content, ImageSpan drawable, ImageSpan drawable2, TextView tv) {
        SpannableString spanText;
        if (drawable != null && drawable2 != null){
            spanText = new SpannableString(" " + " " + " " + " " + content);
            spanText.setSpan(drawable, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            spanText.setSpan(drawable2, 2, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if (drawable == null && drawable2 == null){
            spanText = new SpannableString(content);
        } else {
            spanText = new SpannableString(" " + " " + content);
            if (drawable != null) {
                spanText.setSpan(drawable, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (drawable2 != null) {
                spanText.setSpan(drawable2, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        tv.setText(spanText);
    }

    public ActivityBarView(Context context) {
        super(context);
    }

    /**
     * 输入金额 最多两位小数（和input="numberDecimal"一起使用）
     *
     * @param editText
     */
    public static void PriceEditText(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().equals(".")) {
                    editText.setText("");
                    return;
                }
                if (s.toString().contains(".")) {
                    if (s.toString().length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.toString().length());
                    }
                }
                if (s.toString().startsWith("0") && s.toString().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
    }

}
