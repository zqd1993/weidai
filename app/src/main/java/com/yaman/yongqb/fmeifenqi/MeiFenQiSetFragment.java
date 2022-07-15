package com.yaman.yongqb.fmeifenqi;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yaman.yongqb.R;
import com.yaman.yongqb.ameifenqi.AboutInfoActivityMeiFenQi;
import com.yaman.yongqb.ameifenqi.MeiFenQiDlActivity;
import com.yaman.yongqb.ameifenqi.FeedbackMeiFenQiActivity;
import com.yaman.yongqb.ameifenqi.JumpH5ActivityMeiFenQi;
import com.yaman.yongqb.ameifenqi.MeiFenQiSetItemAdapter;
import com.yaman.yongqb.ameifenqi.ZhuXiaoActivityMeiFenQi;
import com.yaman.yongqb.apimeifenqi.MeiFenQiHttpApi;
import com.yaman.yongqb.mmeifenqi.BaseModelMeiFenQi;
import com.yaman.yongqb.mmeifenqi.ConfigMeiFenQiEntity;
import com.yaman.yongqb.mmeifenqi.ProductModelMeiFenQi;
import com.yaman.yongqb.mmeifenqi.MeiFenQiSetModel;
import com.yaman.yongqb.mvp.XActivity;
import com.yaman.yongqb.mvp.XFragment;
import com.yaman.yongqb.net.ApiSubscriber;
import com.yaman.yongqb.net.NetError;
import com.yaman.yongqb.net.XApi;
import com.yaman.yongqb.umeifenqi.MyToastMeiFenQi;
import com.yaman.yongqb.umeifenqi.OpenMeiFenQiUtil;
import com.yaman.yongqb.umeifenqi.PreferencesOpenUtilMeiFenQi;
import com.yaman.yongqb.wmeifenqi.RemindMeiFenQiDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MeiFenQiSetFragment extends XFragment {

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

    private ProductModelMeiFenQi productModelMeiFenQi;

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

    private MeiFenQiSetItemAdapter meiFenQiSetItemAdapter, meiFenQiSetItemAdapter1;

    private RemindMeiFenQiDialog dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesOpenUtilMeiFenQi.getString("app_mail");
        phone = PreferencesOpenUtilMeiFenQi.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
        zhuxiao_ll.setOnClickListener(v -> {
            OpenMeiFenQiUtil.getValue((XActivity) getActivity(), ZhuXiaoActivityMeiFenQi.class, null);
        });
        logout_ll.setOnClickListener(v -> {
            dialog = new RemindMeiFenQiDialog(getActivity()).setCancelText("取消")
                    .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
            dialog.setOnButtonClickListener(new RemindMeiFenQiDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    PreferencesOpenUtilMeiFenQi.saveString("phone", "");
                    OpenMeiFenQiUtil.getValue((XActivity) getActivity(), MeiFenQiDlActivity.class, null, true);
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
        return R.layout.fragment_set_mei_fen_qi;
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
        MeiFenQiSetModel model = new MeiFenQiSetModel(R.drawable.cvcxhtuy, "注册协议");
        MeiFenQiSetModel model1 = new MeiFenQiSetModel(R.drawable.kzxdrhfgxh, "隐私协议");
        MeiFenQiSetModel model2 = new MeiFenQiSetModel(R.drawable.zcxbvdfy, "意见反馈");
        MeiFenQiSetModel model3 = new MeiFenQiSetModel(R.drawable.qfgvzdfbhyh, "关于我们");
        MeiFenQiSetModel model4 = new MeiFenQiSetModel(R.drawable.wergdzb, "个性化推荐");
        MeiFenQiSetModel model5 = new MeiFenQiSetModel(R.drawable.vzdgfthg, "投诉邮箱");
        List<MeiFenQiSetModel> list = new ArrayList<>();
        List<MeiFenQiSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list1.add(model3);
        list1.add(model4);
        list1.add(model5);
        meiFenQiSetItemAdapter = new MeiFenQiSetItemAdapter(R.layout.adpater_set_mei_fen_qi_item, list);
        meiFenQiSetItemAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    if (!TextUtils.isEmpty(PreferencesOpenUtilMeiFenQi.getString("AGREEMENT"))) {
                        webBundle = new Bundle();
                        webBundle.putString("url", PreferencesOpenUtilMeiFenQi.getString("AGREEMENT") + MeiFenQiHttpApi.ZCXY);
                        webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                        OpenMeiFenQiUtil.getValue((XActivity) getActivity(), JumpH5ActivityMeiFenQi.class, webBundle);
                    }
                    break;
                case 1:
                    if (!TextUtils.isEmpty(PreferencesOpenUtilMeiFenQi.getString("AGREEMENT"))) {
                        webBundle = new Bundle();
                        webBundle.putString("url", PreferencesOpenUtilMeiFenQi.getString("AGREEMENT") + MeiFenQiHttpApi.YSXY);
                        webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                        OpenMeiFenQiUtil.getValue((XActivity) getActivity(), JumpH5ActivityMeiFenQi.class, webBundle);
                    }
                    break;
                case 2:
                    OpenMeiFenQiUtil.getValue((XActivity) getActivity(), FeedbackMeiFenQiActivity.class, null);
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(meiFenQiSetItemAdapter);

        meiFenQiSetItemAdapter1 = new MeiFenQiSetItemAdapter(R.layout.adpater_set_mei_fen_qi_item, list1);
        meiFenQiSetItemAdapter1.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    OpenMeiFenQiUtil.getValue((XActivity) getActivity(), AboutInfoActivityMeiFenQi.class, null);
                    break;
                case 1:
                    dialog = new RemindMeiFenQiDialog(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindMeiFenQiDialog.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            MyToastMeiFenQi.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            MyToastMeiFenQi.showShort("开启成功");
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
        setList1.setAdapter(meiFenQiSetItemAdapter1);
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
        if (!TextUtils.isEmpty(PreferencesOpenUtilMeiFenQi.getString("HTTP_API_URL"))) {
            MeiFenQiHttpApi.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelMeiFenQi<ConfigMeiFenQiEntity>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseModelMeiFenQi<ConfigMeiFenQiEntity> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencesOpenUtilMeiFenQi.saveString("app_mail", mailStr);
                                    dialog = new RemindMeiFenQiDialog(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
        }
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

    public void toWeb(ProductModelMeiFenQi model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenMeiFenQiUtil.getValue((XActivity) getActivity(), JumpH5ActivityMeiFenQi.class, bundle);
        }
    }

    public void productList() {
        if (!TextUtils.isEmpty(PreferencesOpenUtilMeiFenQi.getString("HTTP_API_URL"))) {
            mobileType = PreferencesOpenUtilMeiFenQi.getInt("mobileType");
            phone = PreferencesOpenUtilMeiFenQi.getString("phone");
            MeiFenQiHttpApi.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelMeiFenQi<List<ProductModelMeiFenQi>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenMeiFenQiUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(BaseModelMeiFenQi<List<ProductModelMeiFenQi>> baseModelMeiFenQi) {
                            if (baseModelMeiFenQi != null) {
                                if (baseModelMeiFenQi.getCode() == 200 && baseModelMeiFenQi.getData() != null) {
                                    if (baseModelMeiFenQi.getData() != null && baseModelMeiFenQi.getData().size() > 0) {
                                        productModelMeiFenQi = baseModelMeiFenQi.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
        }
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

    public void productClick(ProductModelMeiFenQi model) {
        if (!TextUtils.isEmpty(PreferencesOpenUtilMeiFenQi.getString("HTTP_API_URL"))) {
            if (model == null) {
                return;
            }
            phone = PreferencesOpenUtilMeiFenQi.getString("phone");
            MeiFenQiHttpApi.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModelMeiFenQi>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(BaseModelMeiFenQi baseModelMeiFenQi) {
                            toWeb(model);
                        }
                    });
        }
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
