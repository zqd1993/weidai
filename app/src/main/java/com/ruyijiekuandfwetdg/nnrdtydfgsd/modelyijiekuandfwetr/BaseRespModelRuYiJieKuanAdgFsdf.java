package com.ruyijiekuandfwetdg.nnrdtydfgsd.modelyijiekuandfwetr;

import com.ruyijiekuandfwetdg.nnrdtydfgsd.netyijiekuandfwetr.IModel;

public class BaseRespModelRuYiJieKuanAdgFsdf<T> implements IModel {

    private int code;

    private String msg;

    private T data;

    private RuYiJieKuanAdgFsdfGoodsModel top;

    public RuYiJieKuanAdgFsdfGoodsModel getTop() {
        return top;
    }

    public void setTop(RuYiJieKuanAdgFsdfGoodsModel top) {
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
