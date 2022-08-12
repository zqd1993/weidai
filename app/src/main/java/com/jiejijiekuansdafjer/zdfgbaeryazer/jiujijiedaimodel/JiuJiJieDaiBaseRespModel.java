package com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedaimodel;

import com.jiejijiekuansdafjer.zdfgbaeryazer.jiujijiedainet.IModel;

public class JiuJiJieDaiBaseRespModel<T> implements IModel {

    private int code;

    private String msg;

    private T data;

    private JiuJiJieDaiGoodsModel top;

    public JiuJiJieDaiGoodsModel getTop() {
        return top;
    }

    public void setTop(JiuJiJieDaiGoodsModel top) {
        this.top = top;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}
