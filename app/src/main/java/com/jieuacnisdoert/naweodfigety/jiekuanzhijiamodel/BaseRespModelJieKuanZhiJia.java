package com.jieuacnisdoert.naweodfigety.jiekuanzhijiamodel;

import com.jieuacnisdoert.naweodfigety.jiekuanzhijianet.IModel;

public class BaseRespModelJieKuanZhiJia<T> implements IModel {

    private int code;

    private String msg;

    private T data;

    private JieKuanZhiJiaGoodsModel top;

    public JieKuanZhiJiaGoodsModel getTop() {
        return top;
    }

    public void setTop(JieKuanZhiJiaGoodsModel top) {
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
