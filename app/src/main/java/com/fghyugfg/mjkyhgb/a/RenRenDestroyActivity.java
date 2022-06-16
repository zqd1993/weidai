package com.fghyugfg.mjkyhgb.a;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fghyugfg.mjkyhgb.R;
import com.fghyugfg.mjkyhgb.u.StatusBarUtil;
import com.fghyugfg.mjkyhgb.w.RemindDialog;

import java.util.List;

public class RenRenDestroyActivity extends AppCompatActivity {

    private RemindDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_destory);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("注销账户");
        TextView commit_btn = findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(v -> {
            dialog = new RemindDialog(this).setCancelText("取消")
                    .setConfirmText("注销账号").setTitle("温馨提示").setContent("是否注销账号？注销后将不能恢复");
            dialog.setOnButtonClickListener(new RemindDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    finish();
                }

                @Override
                public void onCancelClicked() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }

    private static int getNotchSizeXiaoMiWidth(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            int resourceId = activity.getResources().getIdentifier("notch_width", "dimen", "android");
            if (resourceId > 0) {
                return activity.getResources().getDimensionPixelSize(resourceId);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                WindowInsets windowInsets = null;
                DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                List<Rect> boundingRects = displayCutout.getBoundingRects();
                Rect rect = boundingRects.get(0);
                return rect.right - rect.left;
            } catch (Exception e) {

            }
        }
        return 0;
    }

}
