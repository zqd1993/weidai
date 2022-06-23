package com.ufaofqsbxo.uunllhykas.w;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class DianjiTextView extends androidx.appcompat.widget.AppCompatTextView {

    private List<SpanModel> models;
    private SpanClickListener listener;

    public DianjiTextView(Context context) {
        super(context);
    }

    public DianjiTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DianjiTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static class SpanModel {

        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

    private static byte[] base64DecodeChars = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60,
            61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1,
            -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};


    public static class ClickSpanModel extends SpanModel {
        private int id;

        public ClickSpanModel() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    private SpannableString getClickableSpan(int i, ClickSpanModel spanModel) {
        SpannableString spannableString = new SpannableString(spanModel.getStr());
        int start = 0;
        int end = spannableString.length();
        spannableString.setSpan(new SampleClickableSpan(i), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#f9906d")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new NoUnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setText(List<SpanModel> models, SpanClickListener listener) {
        this.setClickable(true);
        this.models = models;
        this.listener = listener;
        this.setMovementMethod(LinkMovementMethod.getInstance());
        for (int i = 0; i < models.size(); i++) {
            SpanModel baseSpanModel = models.get(i);
            SpannableString spannableString;
            if (baseSpanModel instanceof ClickSpanModel) {
                spannableString = getClickableSpan(i, (ClickSpanModel) baseSpanModel);
            } else {
                spannableString = new SpannableString(baseSpanModel.getStr());
            }
            if (i == 0) {
                this.setText(spannableString);
            } else {
                this.append(spannableString);
            }
        }
    }

    /**
     * @param str
     */
    public static byte[] decode(String str) throws Exception {
        byte[] data = str.getBytes("UTF-8");
        int len = data.length;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
        int i = 0;
        int b1, b2, b3, b4;

        while (i < len) {

            /* b1 */
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) {
                break;
            }

            /* b2 */
            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) {
                break;
            }
            buf.write((b1 << 2) | ((b2 & 0x30) >>> 4));

            /* b3 */
            do {
                b3 = data[i++];
                if (b3 == 61) {
                    return buf.toByteArray();
                }
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1) {
                break;
            }
            buf.write(((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2));

            /* b4 */
            do {
                b4 = data[i++];
                if (b4 == 61) {
                    return buf.toByteArray();
                }
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1) {
                break;
            }
            buf.write(((b3 & 0x03) << 6) | b4);
        }
        return buf.toByteArray();
    }

    class SampleClickableSpan extends ClickableSpan implements OnClickListener {
        private int position;

        public SampleClickableSpan(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.OnClickListener(position);
            }
            setText(models, listener);
        }
    }

    public class NoUnderlineSpan extends UnderlineSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }

    public interface SpanClickListener {
        void OnClickListener(int position);
    }

}
