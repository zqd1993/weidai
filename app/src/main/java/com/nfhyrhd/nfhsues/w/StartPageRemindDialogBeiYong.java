package com.nfhyrhd.nfhsues.w;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nfhyrhd.nfhsues.R;
import com.nfhyrhd.nfhsues.u.OpenBeiYongUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPageRemindDialogBeiYong extends Dialog {

    @BindView(R.id.two_btn)
    TextView twoBtn;
    @BindView(R.id.tv_txt)
    ClickTextViewBeiYong tvTxt;
    @BindView(R.id.one_btn)
    TextView oneBtn;

    private OnListener onListener;

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager nmghjty(RecyclerView Rv, Context context) {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager tyurngfd(RecyclerView Rv, Context context, int num) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager tytrbfg(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    public StartPageRemindDialogBeiYong(@NonNull Context context) {
        super(context, R.style.tran_dialog);
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

        tvTxt.setText(OpenBeiYongUtil.createSpanTexts(), position -> {
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

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager mhjmyj(RecyclerView Rv, Context context) {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager wersdv(RecyclerView Rv, Context context, int num) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager bgfht(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    @Override
    public void show() {
        super.show();
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }

    public interface OnListener {
        void oneBtnClicked();

        void twoBtnClicked();

        void zcxyClicked();

        void ysxyClicked();
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager ouiyj(RecyclerView Rv, Context context) {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager zfdgty(RecyclerView Rv, Context context, int num) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        Rv.getItemAnimator().setChangeDuration(0);
        return manager;
    }

    /**
     * 设置recyclerview位gridview的样式
     *
     * @param Rv
     * @param context
     * @param num     每一行的列数
     * @return
     */
    public static GridLayoutManager wersdvsdff(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

}
