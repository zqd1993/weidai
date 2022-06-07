package com.mbnmhj.poiohg.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mbnmhj.poiohg.R;
import com.mbnmhj.poiohg.util.AllUtil;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPageRemindDialog extends Dialog {

    @BindView(R.id.two_btn)
    TextView twoBtn;
    @BindView(R.id.tv_txt)
    ClickTextView tvTxt;
    @BindView(R.id.one_btn)
    TextView oneBtn;

    private OnListener onListener;

    public StartPageRemindDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
    }

    /**
     * 获取唯一id
     *
     * @return
     */
    public void getAndroidID() {
//        if (TextUtils.isEmpty("")) {
//            AndroidID = SharedPreferencesUtilis.getStringFromPref(StaticUtils.OAID);
//            SharedPreferencesUtilis.saveStringIntoPref(StaticUtils.OAIDS, AndroidID);
//            if (TextUtils.isEmpty(AndroidID)) {
//                AndroidID = UUID.randomUUID().toString();
//                SharedPreferencesUtilis.saveStringIntoPref(StaticUtils.OAID, AndroidID);
//                SharedPreferencesUtilis.saveStringIntoPref(StaticUtils.OAIDS, AndroidID);
//            }
//        } else {
//            if (TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref(StaticUtils.OAIDS)))
//                SharedPreferencesUtilis.saveStringIntoPref(StaticUtils.OAIDS, AndroidID);
//        }
//        return AndroidID;
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

        tvTxt.setText(AllUtil.createSpanTexts(), position -> {
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

    @Override
    public void show() {
        super.show();
    }

    /**
     * 1--店铺，2--自提点，3--既是店铺也是自提点 4 员工
     *
     * @return
     */
    public void getShopType() {
//        if (shopType == 0) {
//            shopType = SharedPreferencesUtilis.getIntFromPref(UNITID);
//            if (shopType == 0) {
//                ShopLite shopLite = LitePalUtils.getShopInfo();
//                if (shopLite == null)
//                    shopType = 0;
//                else
//                    shopType = shopLite.getType();
//            }
//        }
//        return shopType;
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    public static String formatPrice(BigDecimal bigDecimal) {
        if (bigDecimal == null)
            return "0";
        else
            return bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

    public interface OnListener {
        void oneBtnClicked();

        void twoBtnClicked();

        void zcxyClicked();

        void ysxyClicked();
    }

}
