package com.nfhyrhd.nfhsues.f;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nfhyrhd.nfhsues.R;
import com.nfhyrhd.nfhsues.a.AboutInfoActivityBeiYong;
import com.nfhyrhd.nfhsues.a.DlBeiYongActivity;
import com.nfhyrhd.nfhsues.a.BeiYongFeedbackActivity;
import com.nfhyrhd.nfhsues.a.BeiYongJumpH5Activity;
import com.nfhyrhd.nfhsues.a.SetItemBeiYongAdapter;
import com.nfhyrhd.nfhsues.a.ZhuXiaoBeiYongActivity;
import com.nfhyrhd.nfhsues.api.BeiYongHttpApi;
import com.nfhyrhd.nfhsues.m.BaseModelBeiYong;
import com.nfhyrhd.nfhsues.m.ConfigBeiYongEntity;
import com.nfhyrhd.nfhsues.m.ProductModelBeiYong;
import com.nfhyrhd.nfhsues.m.BeiYongSetModel;
import com.nfhyrhd.nfhsues.mvp.XFragment;
import com.nfhyrhd.nfhsues.net.ApiSubscriber;
import com.nfhyrhd.nfhsues.net.NetError;
import com.nfhyrhd.nfhsues.net.XApi;
import com.nfhyrhd.nfhsues.u.MyToastBeiYong;
import com.nfhyrhd.nfhsues.u.OpenBeiYongUtil;
import com.nfhyrhd.nfhsues.u.BeiYongPreferencesOpenUtil;
import com.nfhyrhd.nfhsues.w.BeiYongRemindDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFragmentBeiYong extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;

    private ProductModelBeiYong productModelBeiYong;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemBeiYongAdapter setItemBeiYongAdapter;

    private BeiYongRemindDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = BeiYongPreferencesOpenUtil.getString("app_mail");
        phone = BeiYongPreferencesOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager setRvVertical(RecyclerView Rv, Context context) {

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
    public static GridLayoutManager setRvGridLayout(RecyclerView Rv, Context context, int num) {
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
    public static GridLayoutManager setRvGridLayout(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    private void initSetAdapter() {
        BeiYongSetModel model = new BeiYongSetModel(R.drawable.fdvdwdfsdv, "注册协议");
        BeiYongSetModel model1 = new BeiYongSetModel(R.drawable.dsfqasc, "隐私协议");
        BeiYongSetModel model2 = new BeiYongSetModel(R.drawable.rgevvvd, "意见反馈");
        BeiYongSetModel model3 = new BeiYongSetModel(R.drawable.luigsdv, "关于我们");
        BeiYongSetModel model4 = new BeiYongSetModel(R.drawable.wefvdfr, "个性化推荐");
        BeiYongSetModel model5 = new BeiYongSetModel(R.drawable.kluyjgnvb, "投诉邮箱");
        BeiYongSetModel model6 = new BeiYongSetModel(R.drawable.luwezxv, "注销账户");
        BeiYongSetModel model7 = new BeiYongSetModel(R.drawable.zfcsdfvdf, "退出登录");
        List<BeiYongSetModel> list = new ArrayList<>();
        List<BeiYongSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        list.add(model6);
        list.add(model7);
        setItemBeiYongAdapter = new SetItemBeiYongAdapter(R.layout.adpater_set_item, list);
        setItemBeiYongAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", BeiYongHttpApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenBeiYongUtil.jumpPage(getActivity(), BeiYongJumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", BeiYongHttpApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenBeiYongUtil.jumpPage(getActivity(), BeiYongJumpH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenBeiYongUtil.jumpPage(getActivity(), BeiYongFeedbackActivity.class);
                    break;
                case 3:
                    OpenBeiYongUtil.jumpPage(getActivity(), AboutInfoActivityBeiYong.class);
                    break;
                case 4:
                    dialog = new BeiYongRemindDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new BeiYongRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToastBeiYong.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToastBeiYong.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 5:
                    getConfig();
                    break;
                case 6:
                    OpenBeiYongUtil.jumpPage(getActivity(), ZhuXiaoBeiYongActivity.class);
                    break;
                case 7:
                    dialog = new BeiYongRemindDialog(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new BeiYongRemindDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            BeiYongPreferencesOpenUtil.saveString("phone", "");
                            OpenBeiYongUtil.jumpPage(getActivity(), DlBeiYongActivity.class);
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
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemBeiYongAdapter);
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager bnfghrtgd(RecyclerView Rv, Context context) {

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
    public static GridLayoutManager retdfvfxs(RecyclerView Rv, Context context, int num) {
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
    public static GridLayoutManager tryfgbdfg(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    public void toWeb(ProductModelBeiYong model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenBeiYongUtil.jumpPage(getActivity(), BeiYongJumpH5Activity.class, bundle);
        }
    }

    public void productList() {
        if (!TextUtils.isEmpty(BeiYongPreferencesOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = BeiYongPreferencesOpenUtil.getInt("mobileType");
            BeiYongHttpApi.getInterfaceUtils().productList(mobileType)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelBeiYong<List<ProductModelBeiYong>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenBeiYongUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseModelBeiYong<List<ProductModelBeiYong>> baseModelBeiYong) {
                            if (baseModelBeiYong != null) {
                                if (baseModelBeiYong.getCode() == 200 && baseModelBeiYong.getData() != null) {
                                    if (baseModelBeiYong.getData() != null && baseModelBeiYong.getData().size() > 0) {
                                        productModelBeiYong = baseModelBeiYong.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager rvsdffg(RecyclerView Rv, Context context) {

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
    public static GridLayoutManager tyurtger(RecyclerView Rv, Context context, int num) {
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
    public static GridLayoutManager bgdfhrtgh(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    public void productClick(ProductModelBeiYong model) {
        if (!TextUtils.isEmpty(BeiYongPreferencesOpenUtil.getString("HTTP_API_URL"))) {
            if (model == null) {
                return;
            }
            phone = BeiYongPreferencesOpenUtil.getString("phone");
            BeiYongHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelBeiYong>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseModelBeiYong baseModelBeiYong) {
                            toWeb(model);
                        }
                    });
        }
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager rtervvdf(RecyclerView Rv, Context context) {

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
    public static GridLayoutManager pioyuk(RecyclerView Rv, Context context, int num) {
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
    public static GridLayoutManager nmghjuyi(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(BeiYongPreferencesOpenUtil.getString("HTTP_API_URL"))) {
            BeiYongHttpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelBeiYong<ConfigBeiYongEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseModelBeiYong<ConfigBeiYongEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    BeiYongPreferencesOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
                                    dialog = new BeiYongRemindDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 把丢进来的recyclerview  写成纵向滑动
     *
     * @param Rv
     * @param context
     */
    public static LinearLayoutManager urtdfhbdf(RecyclerView Rv, Context context) {

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
    public static GridLayoutManager mhjouio(RecyclerView Rv, Context context, int num) {
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
    public static GridLayoutManager wacxzff(RecyclerView Rv, Context context, int num, int space) {
        GridLayoutManager manager = new GridLayoutManager(context, num);
        Rv.setLayoutManager(manager);
        return manager;
    }
}
