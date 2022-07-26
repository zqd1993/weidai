package com.chenqi.lecheng.shandaipage.shandaifragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chenqi.lecheng.R;
import com.chenqi.lecheng.adaptershandai.MineAdapterHaoJie;
import com.chenqi.lecheng.mvp.XActivity;
import com.chenqi.lecheng.shadnaimodel.BaseRespHaoJieModel;
import com.chenqi.lecheng.shadnaimodel.ConfigHaoJieModel;
import com.chenqi.lecheng.shadnaimodel.MineItemModelHaoJie;
import com.chenqi.lecheng.shadnaihttp.ApiHaoJie;
import com.chenqi.lecheng.shadnaihttp.ApiSubscriber;
import com.chenqi.lecheng.shadnaihttp.NetError;
import com.chenqi.lecheng.shadnaihttp.XApi;
import com.chenqi.lecheng.shandaipage.activityshandai.HaoJieWebActivity;
import com.chenqi.lecheng.shandaipage.activityshandai.LoginHaoJieActivity;
import com.chenqi.lecheng.shandaipage.activityshandai.CancellationUserHaoJieActivity;
import com.chenqi.lecheng.shandaipage.activityshandai.MoreInfoHaoJieActivity;
import com.chenqi.lecheng.utilsshandai.SharedPreferencesHaoJieUtilis;
import com.chenqi.lecheng.utilsshandai.StaticHaoJieUtil;
import com.chenqi.lecheng.utilsshandai.ToastHaoJieUtil;
import com.chenqi.lecheng.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.chenqi.lecheng.widgetshandai.NormalHaoJieDialog;
import com.chenqi.lecheng.mvp.XFragment;
import com.chenqi.lecheng.shandaipage.activityshandai.FeedBackHaoJieActivity;
import com.google.android.material.snackbar.Snackbar;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineHaoJieFragment extends XFragment {

    @BindView(R.id.rvy_2)
    RecyclerView rvy2;
    @BindView(R.id.rvy_1)
    RecyclerView rvy1;
    @BindView(R.id.phone_tv)
    TextView phoneTv;


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void SnackbarAddView(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    private MineAdapterHaoJie miaoJieMineAdapter1, miaoJieMineAdapter2;
    private List<MineItemModelHaoJie> list1, list2;
    private NormalHaoJieDialog normalHaoJieDialog;
    private String mailStr = "", phone = "";
    private int[] imgRes = {R.drawable.hgunzdfgll,
            R.drawable.rhuygbzdfgn, R.drawable.yttyrnbgzld, R.drawable.rygujxnflg, R.drawable.nrugnjzg, R.drawable.trbngzmdfgj};
    private String[] tvRes = {"意见反馈", "个性化推荐", "联系客服", "注销账户", "退出登录", "更多信息"};


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_haojie;
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void iutyufgn(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void bdfghrt(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void ersdvsd(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (miaoJieMineAdapter1 == null) {
            miaoJieMineAdapter1 = new MineAdapterHaoJie(getActivity());
            miaoJieMineAdapter1.setData(list1);
            miaoJieMineAdapter1.setHasStableIds(true);
            miaoJieMineAdapter1.setRecItemClick(new RecyclerItemCallback<MineItemModelHaoJie, MineAdapterHaoJie.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelHaoJie model, int tag, MineAdapterHaoJie.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            StaticHaoJieUtil.getValue((XActivity) getActivity(), FeedBackHaoJieActivity.class, null);
                            break;
                        case 1:
                            normalHaoJieDialog = new NormalHaoJieDialog(getActivity());
                            normalHaoJieDialog.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        ToastHaoJieUtil.showShort("开启成功");
                                        normalHaoJieDialog.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        ToastHaoJieUtil.showShort("关闭成功");
                                        normalHaoJieDialog.dismiss();
                                    }).show();
                            break;
                    }
                }
            });
            rvy1.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy1.setHasFixedSize(true);
            rvy1.setAdapter(miaoJieMineAdapter1);
        }
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void mfghfgh(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void ertyhdfb(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void ewrsdvsd(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        mailStr = SharedPreferencesHaoJieUtilis.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesHaoJieUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 6; i++) {
            MineItemModelHaoJie model = new MineItemModelHaoJie();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            if (i < 2) {
                list1.add(model);
            } else {
                list2.add(model);
            }
        }
        initAdapter();
        initAdapter2();
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void vdffsd(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void utyhfgb(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void qwervs(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    private void initAdapter2() {
        if (miaoJieMineAdapter2 == null) {
            miaoJieMineAdapter2 = new MineAdapterHaoJie(getActivity());
            miaoJieMineAdapter2.setData(list2);
            miaoJieMineAdapter2.setHasStableIds(true);
            miaoJieMineAdapter2.setRecItemClick(new RecyclerItemCallback<MineItemModelHaoJie, MineAdapterHaoJie.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelHaoJie model, int tag, MineAdapterHaoJie.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            getGankData();
                            break;
                        case 1:
                            StaticHaoJieUtil.getValue((XActivity) getActivity(), CancellationUserHaoJieActivity.class, null);
                            break;
                        case 2:
                            normalHaoJieDialog = new NormalHaoJieDialog(getActivity());
                            normalHaoJieDialog.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalHaoJieDialog.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        normalHaoJieDialog.dismiss();
                                        SharedPreferencesHaoJieUtilis.saveStringIntoPref("phone", "");
                                        StaticHaoJieUtil.getValue((XActivity) getActivity(), LoginHaoJieActivity.class, null, true);
                                    }).show();
                            break;
                        case 3:
                            StaticHaoJieUtil.getValue((XActivity) getActivity(), MoreInfoHaoJieActivity.class, null);
                            break;
                    }
                }
            });
            rvy2.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy2.setHasFixedSize(true);
            rvy2.setAdapter(miaoJieMineAdapter2);
        }
    }

    public void getGankData() {
            ApiHaoJie.getGankService().getConfig()
                    .compose(XApi.<BaseRespHaoJieModel<ConfigHaoJieModel>>getApiTransformer())
                    .compose(XApi.<BaseRespHaoJieModel<ConfigHaoJieModel>>getScheduler())
                    .compose(this.<BaseRespHaoJieModel<ConfigHaoJieModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespHaoJieModel<ConfigHaoJieModel>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseRespHaoJieModel<ConfigHaoJieModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    mailStr = gankResults.getData().getAppMail();
                                    SharedPreferencesHaoJieUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                    normalHaoJieDialog = new NormalHaoJieDialog(getActivity());
                                    normalHaoJieDialog.setTitle("温馨提示")
                                            .setContent(mailStr)
                                            .showOnlyBtn().show();
                                }
                            }
                        }
                    });
    }


    /**
     * 设置Snackbar背景颜色
     * @param snackbar
     * @param backgroundColor
     */
    public static void uityjfg(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar文字和背景颜色
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    public static void vdftgre(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(backgroundColor);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 向Snackbar中添加view
     * @param snackbar
     * @param layoutId
     * @param index 新加布局在Snackbar中的位置
     */
    public static void werdvcd(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout=(Snackbar.SnackbarLayout)snackbarview;

        View add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId,null);

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity= Gravity.CENTER_VERTICAL;

        snackbarLayout.addView(add_view,index,p);
    }

    @Override
    public void onDestroy() {
        if (normalHaoJieDialog != null) {
            normalHaoJieDialog.dismiss();
            normalHaoJieDialog = null;
        }
        super.onDestroy();
    }
}
