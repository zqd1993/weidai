package com.nfhyrhd.nfhsues.a;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nfhyrhd.nfhsues.MainBeiYongApp;
import com.nfhyrhd.nfhsues.R;
import com.nfhyrhd.nfhsues.u.OpenBeiYongUtil;
import com.nfhyrhd.nfhsues.u.StatusBarUtil;

import java.math.BigDecimal;

public class AboutInfoActivityBeiYong extends AppCompatActivity {

    public static BigDecimal getdoubleString(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager setRvHorizontal(RecyclerView Rv, Context context) {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    /**
     * 把丢进来的recyclerView 设置成横向
     * <p>
     * 并且不可滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager setRvHorizontalNoScroll(RecyclerView Rv, Context context) {

        LinearLayoutManager layoutmanager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_about_info);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        TextView version_code_tv = findViewById(R.id.version_code_tv);
        version_code_tv.setText("当前版本号：v" + OpenBeiYongUtil.getAppVersion(MainBeiYongApp.getContext()));
        textView.setText("关于我们");
    }

    public static BigDecimal wervdfg(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager ynfvder(RecyclerView Rv, Context context) {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    /**
     * 把丢进来的recyclerView 设置成横向
     * <p>
     * 并且不可滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager yterff(RecyclerView Rv, Context context) {

        LinearLayoutManager layoutmanager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    public static BigDecimal dfbdfgr(double d) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 把丢进来的recyclerView 设置成横向滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager sdfrtwe(RecyclerView Rv, Context context) {
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context);
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

    /**
     * 把丢进来的recyclerView 设置成横向
     * <p>
     * 并且不可滚动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager dsfere(RecyclerView Rv, Context context) {

        LinearLayoutManager layoutmanager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutmanager);
        return layoutmanager;
    }

}
