package com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqif;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meijiefenqifjsdkqwj.pfdioewjnsd.R;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqia.AboutInfoMeiJienFenSsdfQiActivity;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqia.DlMeiJienFenSsdfQiActivity;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqia.FeedbackActivityMeiJienFenSsdfQi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqia.JumpH5ActivityMeiJienFenSsdfQi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqia.SetItemMeiJienFenSsdfQiAdapter;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqia.MeiJienFenSsdfQiZhuXiaoActivity;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiapi.HttpMeiJienFenSsdfQiApi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.BaseMeiJienFenSsdfQiModel;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.ConfigEntityMeiJienFenSsdfQi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.ProductMeiJienFenSsdfQiModel;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqim.SetModelMeiJienFenSsdfQi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.mvp.XActivity;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.mvp.XFragment;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.net.ApiSubscriber;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.net.NetError;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.net.XApi;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiu.MyMeiJienFenSsdfQiToast;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiu.MeiJienFenSsdfQiOpenUtil;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiu.PreferencesMeiJienFenSsdfQiOpenUtil;
import com.meijiefenqifjsdkqwj.pfdioewjnsd.meijiefenqiw.RemindDialogMeiJienFenSsdfQi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetMeiJienFenSsdfQiFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.zhuxiao_ll)
    View zhuxiao_ll;
    @BindView(R.id.logout_ll)
    View logout_ll;
    @BindView(R.id.set_list_1)
    RecyclerView setList1;

    private ProductMeiJienFenSsdfQiModel productMeiJienFenSsdfQiModel;

    private Bundle bundle, webBundle;

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void fixInputMethodMemoryLeak(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int mobileType;

    private String phone;

    private SetItemMeiJienFenSsdfQiAdapter setItemMeiJienFenSsdfQiAdapter, setItemMeiJienFenSsdfQiAdapter1;

    private RemindDialogMeiJienFenSsdfQi dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesMeiJienFenSsdfQiOpenUtil.getString("app_mail");
        phone = PreferencesMeiJienFenSsdfQiOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        zhuxiao_ll.setOnClickListener(v -> {
            MeiJienFenSsdfQiOpenUtil.getValue((XActivity) getActivity(), MeiJienFenSsdfQiZhuXiaoActivity.class, null);
        });
        logout_ll.setOnClickListener(v -> {
            dialog = new RemindDialogMeiJienFenSsdfQi(getActivity()).setCancelText("取消")
                    .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
            dialog.setOnButtonClickListener(new RemindDialogMeiJienFenSsdfQi.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    PreferencesMeiJienFenSsdfQiOpenUtil.saveString("phone", "");
                    MeiJienFenSsdfQiOpenUtil.getValue((XActivity) getActivity(), DlMeiJienFenSsdfQiActivity.class, null, true);
                }

                @Override
                public void onCancelClicked() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mei_jie_fen_qi_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void putydjdfj(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void yrtygfhry(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initSetAdapter() {
        SetModelMeiJienFenSsdfQi model = new SetModelMeiJienFenSsdfQi(R.drawable.cvcxhtuy, "注册协议");
        SetModelMeiJienFenSsdfQi model1 = new SetModelMeiJienFenSsdfQi(R.drawable.kzxdrhfgxh, "隐私协议");
        SetModelMeiJienFenSsdfQi model2 = new SetModelMeiJienFenSsdfQi(R.drawable.zcxbvdfy, "意见反馈");
        SetModelMeiJienFenSsdfQi model3 = new SetModelMeiJienFenSsdfQi(R.drawable.qfgvzdfbhyh, "关于我们");
        SetModelMeiJienFenSsdfQi model4 = new SetModelMeiJienFenSsdfQi(R.drawable.wergdzb, "个性化推荐");
        SetModelMeiJienFenSsdfQi model5 = new SetModelMeiJienFenSsdfQi(R.drawable.vzdgfthg, "投诉邮箱");
        List<SetModelMeiJienFenSsdfQi> list = new ArrayList<>();
        List<SetModelMeiJienFenSsdfQi> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list1.add(model3);
        list1.add(model4);
        list1.add(model5);
        setItemMeiJienFenSsdfQiAdapter = new SetItemMeiJienFenSsdfQiAdapter(R.layout.adpater_set_item_mei_jie_fen_qi, list);
        setItemMeiJienFenSsdfQiAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpMeiJienFenSsdfQiApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    MeiJienFenSsdfQiOpenUtil.getValue((XActivity) getActivity(), JumpH5ActivityMeiJienFenSsdfQi.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpMeiJienFenSsdfQiApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    MeiJienFenSsdfQiOpenUtil.getValue((XActivity) getActivity(), JumpH5ActivityMeiJienFenSsdfQi.class, webBundle);
                    break;
                case 2:
                    MeiJienFenSsdfQiOpenUtil.getValue((XActivity) getActivity(), FeedbackActivityMeiJienFenSsdfQi.class, null);
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemMeiJienFenSsdfQiAdapter);

        setItemMeiJienFenSsdfQiAdapter1 = new SetItemMeiJienFenSsdfQiAdapter(R.layout.adpater_set_item_mei_jie_fen_qi, list1);
        setItemMeiJienFenSsdfQiAdapter1.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    MeiJienFenSsdfQiOpenUtil.getValue((XActivity) getActivity(), AboutInfoMeiJienFenSsdfQiActivity.class, null);
                    break;
                case 1:
                    dialog = new RemindDialogMeiJienFenSsdfQi(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialogMeiJienFenSsdfQi.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyMeiJienFenSsdfQiToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyMeiJienFenSsdfQiToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 2:
                    getConfig();
                    break;
            }
        });
        setList1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList1.setAdapter(setItemMeiJienFenSsdfQiAdapter1);
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void qwredfhfg(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void nsfhsrtu(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getConfig() {
        HttpMeiJienFenSsdfQiApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseMeiJienFenSsdfQiModel<ConfigEntityMeiJienFenSsdfQi>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseMeiJienFenSsdfQiModel<ConfigEntityMeiJienFenSsdfQi> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferencesMeiJienFenSsdfQiOpenUtil.saveString("app_mail", mailStr);
                                dialog = new RemindDialogMeiJienFenSsdfQi(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                dialog.show();
                            }
                        }
                    }
                });
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void sfgsruyj(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void nshrttj(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void toWeb(ProductMeiJienFenSsdfQiModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            MeiJienFenSsdfQiOpenUtil.getValue((XActivity) getActivity(), JumpH5ActivityMeiJienFenSsdfQi.class, bundle);
        }
    }

    public void productList() {
        mobileType = PreferencesMeiJienFenSsdfQiOpenUtil.getInt("mobileType");
        phone = PreferencesMeiJienFenSsdfQiOpenUtil.getString("phone");
        HttpMeiJienFenSsdfQiApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseMeiJienFenSsdfQiModel<List<ProductMeiJienFenSsdfQiModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        MeiJienFenSsdfQiOpenUtil.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(BaseMeiJienFenSsdfQiModel<List<ProductMeiJienFenSsdfQiModel>> baseMeiJienFenSsdfQiModel) {
                        if (baseMeiJienFenSsdfQiModel != null) {
                            if (baseMeiJienFenSsdfQiModel.getCode() == 200 && baseMeiJienFenSsdfQiModel.getData() != null) {
                                if (baseMeiJienFenSsdfQiModel.getData() != null && baseMeiJienFenSsdfQiModel.getData().size() > 0) {
                                    productMeiJienFenSsdfQiModel = baseMeiJienFenSsdfQiModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void sgsretyrtu(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void rtuhfgjd(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void productClick(ProductMeiJienFenSsdfQiModel model) {
        if (model == null) {
            return;
        }
        phone = PreferencesMeiJienFenSsdfQiOpenUtil.getString("phone");
        HttpMeiJienFenSsdfQiApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseMeiJienFenSsdfQiModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(BaseMeiJienFenSsdfQiModel baseMeiJienFenSsdfQiModel) {
                        toWeb(model);
                    }
                });
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void nfhrtuj(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void pityjdfju(Context context) {
        if (context == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        String[] viewArr = new String[]{"mCurRootView", "mServedView", "mNextServedView", "mLastSrvView"};
        Field field;
        Object fieldObj;
        for (String view : viewArr) {
            try {
                field = inputMethodManager.getClass().getDeclaredField(view);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldObj = field.get(inputMethodManager);
                if (fieldObj != null && fieldObj instanceof View) {
                    View fieldView = (View) fieldObj;
                    if (fieldView.getContext() == context) {// 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(inputMethodManager, null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
