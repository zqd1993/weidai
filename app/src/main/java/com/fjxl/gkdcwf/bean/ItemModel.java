package com.fjxl.gkdcwf.bean;

public class ItemModel {

    private String name;

    private boolean isChecked;

    private String amount;

    private boolean aomuntChecked;

    public boolean isAomuntChecked() {
        return aomuntChecked;
    }

    public void setAomuntChecked(boolean aomuntChecked) {
        this.aomuntChecked = aomuntChecked;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
