package com.xvhyrt.ghjtyu.api;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.u.PreferencesOpenUtil;

import java.lang.reflect.Field;

public class HttpApi {
    public static final String ZCXY = "https://xy.hgy5kg.com/profile/dzdk/zcxy.html";
    public static final String YSXY= "https://xy.hgy5kg.com/profile/dzdk/ysxy.html";
    public static String HTTP_API_URL = "";

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

    private static InterfaceUtils interfaceUtils;

    public static InterfaceUtils getInterfaceUtils() {
        if (interfaceUtils == null && !TextUtils.isEmpty(PreferencesOpenUtil.getString("HTTP_API_URL"))) {
            synchronized (HttpApi.class) {
                if (interfaceUtils == null) {
                    interfaceUtils = XApi.getInstance().getRetrofit(PreferencesOpenUtil.getString("HTTP_API_URL"), true).create(InterfaceUtils.class);
                }
            }
        }
        return interfaceUtils;
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public void ryhzdfhjfsj(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    //修复输入法导致的内存泄露
    public void nfshsrtuy(Context context) {
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
