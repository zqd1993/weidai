package com.srysfghsrty.mkdtyusaert.wanrongxinyongkamodel;

import com.srysfghsrty.mkdtyusaert.wanrongxinyongkanet.IModel;

public class LoginStatusWanRongXinYongKaModel implements IModel {

    private String is_code_register;

    private String is_agree_check;

    private String msg;

    public String getIs_code_register() {
        return is_code_register;
    }

    public void setIs_code_register(String is_code_register) {
        this.is_code_register = is_code_register;
    }

    public String getIs_agree_check() {
        return is_agree_check;
    }

    public void setIs_agree_check(String is_agree_check) {
        this.is_agree_check = is_agree_check;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
