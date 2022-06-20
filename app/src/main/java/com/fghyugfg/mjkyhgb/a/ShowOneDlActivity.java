package com.fghyugfg.mjkyhgb.a;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fghyugfg.mjkyhgb.api.NewApi;
import com.fghyugfg.mjkyhgb.R;
import com.fghyugfg.mjkyhgb.m.RenRenBaseModel;
import com.fghyugfg.mjkyhgb.m.RenRenConfigEntity;
import com.fghyugfg.mjkyhgb.m.LoginRenRenModel;
import com.fghyugfg.mjkyhgb.mvp.XActivity;
import com.fghyugfg.mjkyhgb.net.ApiSubscriber;
import com.fghyugfg.mjkyhgb.net.NetError;
import com.fghyugfg.mjkyhgb.net.XApi;
import com.fghyugfg.mjkyhgb.u.NewToast;
import com.fghyugfg.mjkyhgb.u.OpenMethodUtil;
import com.fghyugfg.mjkyhgb.u.SPOpenUtil;
import com.fghyugfg.mjkyhgb.u.StatusBarUtil;
import com.fghyugfg.mjkyhgb.w.ClickTextView;
import com.fghyugfg.mjkyhgb.w.CountDownTimer;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowOneDlActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this, false);
        xStateController = this.findViewById(R.id.content_layout);
        mobileEt = this.findViewById(R.id.mobile_et);
        yzmEt = this.findViewById(R.id.yzm_et);
        getYzmTv = this.findViewById(R.id.get_yzm_tv);
        dlBtn = this.findViewById(R.id.dl_btn);
        remindCb = this.findViewById(R.id.remind_cb);
        readTv = this.findViewById(R.id.read_tv);
        yzmCv = this.findViewById(R.id.yzm_cv);
        getIp();
        xStateController.loadingView(View.inflate(this, R.layout.loading_view, null));
        getConfig();
        readTv.setText(OpenMethodUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 0) {
                bundle.putString("url", NewApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", NewApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            OpenMethodUtil.jumpPage(ShowOneDlActivity.this, JumpH5Activity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                NewToast.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty() && isNeedYzm) {
                NewToast.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                NewToast.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked()) {
                NewToast.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr, yzmStr);
        });
    }

    public static String getAllTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date(time);
        return format.format(now);
    }

    public static String getYmdWithDot(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date now = new Date(time);
        return format.format(now);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_one_renren;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
        NewApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<RenRenBaseModel<RenRenConfigEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenMethodUtil.showErrorInfo(ShowOneDlActivity.this, error);
                    }

                    @Override
                    public void onNext(RenRenBaseModel<RenRenConfigEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                SPOpenUtil.saveString("app_mail", configEntity.getData().getAppMail());
                                if ("0".equals(configEntity.getData().getIsCodeLogin())) {
                                    yzmCv.setVisibility(View.GONE);
                                } else {
                                    yzmCv.setVisibility(View.VISIBLE);
                                }
                                isNeedYzm = "1".equals(configEntity.getData().getIsCodeLogin());
                                isChecked = "1".equals(configEntity.getData().getIsSelectLogin());
                                remindCb.setChecked(isChecked);
                            }
                        }
                    }
                });
    }

    public static int getMonth(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        Date now = new Date(time);
        return Integer.parseInt(format.format(now));
    }

    private void getIp() {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://pv.sohu.com/cityjson")
                        .build();
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static String getYmdCh(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date now = new Date(time);
        return format.format(now);
    }

    public static String getYmChart(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
        Date now = new Date(time);
        return format.format(now);
    }


    private void parseJSONWithJSONObject(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);
            ip = jsonObject.getString("cip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getYmdChart(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date(time);
        return format.format(now);
    }

    public static String getYmdDliverCh(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年\nMM月dd日");
        Date now = new Date(time);
        return format.format(now);
    }

    public void login(String phone, String verificationStr) {
        if (xStateController != null)
            xStateController.showLoading();
        NewApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<RenRenBaseModel<LoginRenRenModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenMethodUtil.showErrorInfo(ShowOneDlActivity.this, error);
                        if (xStateController != null)
                            xStateController.showContent();
                    }

                    @Override
                    public void onNext(RenRenBaseModel<LoginRenRenModel> dlModel) {
                        if (xStateController != null)
                            xStateController.showContent();
                        if (dlModel != null && dlModel.getCode() == 200) {
                            if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                OpenMethodUtil.jumpPage(ShowOneDlActivity.this, MainActivity.class);
                                int mobileType = dlModel.getData().getMobileType();
                                SPOpenUtil.saveString("ip", ip);
                                SPOpenUtil.saveString("phone", phone);
                                SPOpenUtil.saveInt("mobileType", mobileType);
                                finish();
                            }
                        } else {
                            if (dlModel.getCode() == 500) {
                                NewToast.showShort(dlModel.getMsg());
                            }
                        }
                    }
                });
    }

    public static String getMdCh(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        Date now = new Date(time);
        return format.format(now);
    }

    public static Date getCalenderDate(String src) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date now = null;
        try {
            now = format.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (now == null) {
                Date date = new Date();
                date.setTime(System.currentTimeMillis());
                return date;
            } else {
                return now;
            }
        }
    }

    public void getYzm(String phone) {
        NewApi.getInterfaceUtils().sendVerifyCode(phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<RenRenBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpenMethodUtil.showErrorInfo(ShowOneDlActivity.this, error);
                    }

                    @Override
                    public void onNext(RenRenBaseModel renRenBaseModel) {
                        if (renRenBaseModel != null) {
                            if (renRenBaseModel.getCode() == 200) {
                                NewToast.showShort("验证码发送成功");
                                CountDownTimer cdt = new CountDownTimer(getYzmTv, 60000, 1000);
                                cdt.start();
                            }
                        }
                    }
                });
    }

    public static int getYear(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date now = new Date(time);
        return Integer.parseInt(format.format(now));
    }

}
