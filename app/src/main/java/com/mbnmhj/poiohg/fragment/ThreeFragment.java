package com.mbnmhj.poiohg.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mbnmhj.poiohg.R;
import com.mbnmhj.poiohg.entity.CFEntity;
import com.mbnmhj.poiohg.page.NetPageActivity;
import com.mbnmhj.poiohg.page.UsActivity;
import com.mbnmhj.poiohg.page.TwoActivity;
import com.mbnmhj.poiohg.page.BackActivity;
import com.mbnmhj.poiohg.page.OurAdapter;
import com.mbnmhj.poiohg.page.RegActivity;
import com.mbnmhj.poiohg.net.NetApi;
import com.mbnmhj.poiohg.entity.MainModel;
import com.mbnmhj.poiohg.entity.MoreModel;
import com.mbnmhj.poiohg.entity.SettingModel;
import com.mbnmhj.poiohg.mvp.XFragment;
import com.mbnmhj.poiohg.net.ApiSubscriber;
import com.mbnmhj.poiohg.net.NetError;
import com.mbnmhj.poiohg.net.XApi;
import com.mbnmhj.poiohg.util.NewToast;
import com.mbnmhj.poiohg.util.AllUtil;
import com.mbnmhj.poiohg.util.SpUtil;
import com.mbnmhj.poiohg.view.RemindDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class ThreeFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.set_list_1)
    RecyclerView setList1;

    private MoreModel moreModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private OurAdapter setItemAdapter, setItemAdapter1;

    private RemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = SpUtil.getString("app_mail");
        phone = SpUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_our;
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date Date类型时间
     * @return 时间字符串
     */
    public static String date2String(final Date date) {
        return "";
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SettingModel model = new SettingModel(R.drawable.vdfez, "注册协议");
        SettingModel model1 = new SettingModel(R.drawable.bdfgc, "隐私协议");
        SettingModel model2 = new SettingModel(R.drawable.hkyuerv, "意见反馈");
        SettingModel model3 = new SettingModel(R.drawable.vcerf, "关于我们");
        SettingModel model4 = new SettingModel(R.drawable.lyufwcq, "个性化推荐");
        SettingModel model5 = new SettingModel(R.drawable.bgsfvc, "投诉邮箱");
        SettingModel model6 = new SettingModel(R.drawable.wefdv, "注销账户");
        SettingModel model7 = new SettingModel(R.drawable.fefcdv, "退出登录");
        List<SettingModel> list = new ArrayList<>();
        List<SettingModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list1.add(model2);
        list1.add(model3);
        list1.add(model4);
        list1.add(model5);
        list1.add(model6);
        list1.add(model7);
        setItemAdapter = new OurAdapter(R.layout.adpater_setting_item, list);
        setItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", NetApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.zcxy));
                    AllUtil.jumpPage(getActivity(), NetPageActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", NetApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.yszc));
                    AllUtil.jumpPage(getActivity(), NetPageActivity.class, webBundle);
                    break;
            }
        });
        setList.setLayoutManager(new LinearLayoutManager(getActivity()));
        setList.setAdapter(setItemAdapter);
        setItemAdapter1 = new OurAdapter(R.layout.adpater_setting_item, list1);
        setItemAdapter1.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    AllUtil.jumpPage(getActivity(), BackActivity.class);
                    break;
                case 1:
                    AllUtil.jumpPage(getActivity(), UsActivity.class);
                    break;
                case 2:
                    dialog = new RemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            NewToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            NewToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 3:
                    getConfig();
                    break;
                case 4:
                    AllUtil.jumpPage(getActivity(), RegActivity.class);
                    break;
                case 5:
                    dialog = new RemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            SpUtil.saveString("phone", "");
                            AllUtil.jumpPage(getActivity(), TwoActivity.class);
                            getActivity().finish();
                        }

                        @Override
                        public void onCancelClicked() {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
            }
        });
        setList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        setList1.setAdapter(setItemAdapter1);
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(SpUtil.getString("HTTP_API_URL"))) {
            NetApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<MainModel<CFEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(MainModel<CFEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    SpUtil.saveString("app_mail", mailStr);
                                    dialog = new RemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为format</p>
     *
     * @param date   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }

    public void toWeb(MoreModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            AllUtil.jumpPage(getActivity(), NetPageActivity.class, bundle);
        }
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param date Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(final Date date) {
        return date.getTime();
    }

    public void productList() {
        if (!TextUtils.isEmpty(SpUtil.getString("HTTP_API_URL"))) {
            mobileType = SpUtil.getInt("mobileType");
            NetApi.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MainModel<List<MoreModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            AllUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(MainModel<List<MoreModel>> mainModel) {
                            if (mainModel != null) {
                                if (mainModel.getCode() == 200 && mainModel.getData() != null) {
                                    if (mainModel.getData() != null && mainModel.getData().size() > 0) {
                                        moreModel = mainModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param millis 毫秒时间戳
     * @return Date类型时间
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    public void productClick(MoreModel model) {
        if (!TextUtils.isEmpty(SpUtil.getString("HTTP_API_URL"))) {
            if (model == null) {
                return;
            }
            phone = SpUtil.getString("phone");
            NetApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<MainModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(MainModel mainModel) {
                            toWeb(model);
                        }
                    });
        }
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time0和time1格式都为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time0 时间字符串0
     * @param time1 时间字符串1
     * @param unit  单位类型
     *              <ul>
     *              </ul>
     * @return unit时间戳
     */

}
