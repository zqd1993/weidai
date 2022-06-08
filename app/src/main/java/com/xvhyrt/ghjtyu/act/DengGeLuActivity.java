package com.xvhyrt.ghjtyu.act;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xvhyrt.ghjtyu.net.WangLuoApi;
import com.xvhyrt.ghjtyu.R;
import com.xvhyrt.ghjtyu.bean.ParentModel;
import com.xvhyrt.ghjtyu.bean.PeiZhiEntity;
import com.xvhyrt.ghjtyu.bean.DengGeLuModel;
import com.xvhyrt.ghjtyu.mvp.XActivity;
import com.xvhyrt.ghjtyu.net.ApiSubscriber;
import com.xvhyrt.ghjtyu.net.NetError;
import com.xvhyrt.ghjtyu.net.XApi;
import com.xvhyrt.ghjtyu.uti.TiShi;
import com.xvhyrt.ghjtyu.uti.GongJuLei;
import com.xvhyrt.ghjtyu.uti.SPFile;
import com.xvhyrt.ghjtyu.uti.ZhuangTaiLanUtil;
import com.xvhyrt.ghjtyu.wei.DianJiView;
import com.xvhyrt.ghjtyu.wei.DaoJiShiTimer;

import org.json.JSONObject;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DengGeLuActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private DianJiView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    @Override
    public void initData(Bundle savedInstanceState) {
        ZhuangTaiLanUtil.setTransparent(this, false);
        xStateController = this.findViewById(R.id.content_layout);
        mobileEt = this.findViewById(R.id.mobile_et);
        yzmEt = this.findViewById(R.id.yzm_et);
        getYzmTv = this.findViewById(R.id.get_yzm_tv);
        dlBtn = this.findViewById(R.id.dl_btn);
        remindCb = this.findViewById(R.id.remind_cb);
        readTv = this.findViewById(R.id.read_tv);
        yzmCv = this.findViewById(R.id.yzm_cv);
        getIp();
        xStateController.loadingView(View.inflate(this, R.layout.view_zaijia, null));
        getConfig();
        readTv.setText(GongJuLei.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 0) {
                bundle.putString("url", WangLuoApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
            } else {
                bundle.putString("url", WangLuoApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
            }
            GongJuLei.jumpPage(DengGeLuActivity.this, JumpH5Activity.class, bundle);
        });

        getYzmTv.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            if (phoneStr.isEmpty()) {
                TiShi.showShort("请输入手机号");
            } else {
                getYzm(phoneStr);
            }
        });

        dlBtn.setOnClickListener(v -> {
            phoneStr = mobileEt.getText().toString().trim();
            yzmStr = yzmEt.getText().toString().trim();
            if (phoneStr.isEmpty() && isNeedYzm) {
                TiShi.showShort("请输入手机号码");
                return;
            }
            if (yzmStr.isEmpty() && isNeedYzm) {
                TiShi.showShort("请输入验证码");
                return;
            }
            if (!remindCb.isChecked() && isChecked){
                TiShi.showShort("请阅读并勾选注册及隐私协议");
                return;
            }
            login(phoneStr,yzmStr);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_denglu;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
        WangLuoApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<ParentModel<PeiZhiEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                        GongJuLei.showErrorInfo(DengGeLuActivity.this, error);
                    }

                    @Override
                    public void onNext(ParentModel<PeiZhiEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                SPFile.saveString("app_mail", configEntity.getData().getAppMail());
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

    public void login(String phone, String verificationStr) {
        if (xStateController != null)
            xStateController.showLoading();
        WangLuoApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<ParentModel<DengGeLuModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        GongJuLei.showErrorInfo(DengGeLuActivity.this, error);
                        if (xStateController != null)
                            xStateController.showContent();
                    }

                    @Override
                    public void onNext(ParentModel<DengGeLuModel> dlModel) {
                        if (xStateController != null)
                            xStateController.showContent();
                        if (dlModel != null && dlModel.getCode() == 200) {
                            if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                GongJuLei.jumpPage(DengGeLuActivity.this, ZhongYaoActivity.class);
                                int mobileType = dlModel.getData().getMobileType();
                                SPFile.saveString("ip", ip);
                                SPFile.saveString("phone", phone);
                                SPFile.saveInt("mobileType", mobileType);
                                finish();
                            }
                        } else {
                            if (dlModel.getCode() == 500) {
                                TiShi.showShort(dlModel.getMsg());
                            }
                        }
                    }
                });
    }

    public void getYzm(String phone) {
        WangLuoApi.getInterfaceUtils().sendVerifyCode(phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<ParentModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        GongJuLei.showErrorInfo(DengGeLuActivity.this, error);
                    }

                    @Override
                    public void onNext(ParentModel parentModel) {
                        if (parentModel != null) {
                            if (parentModel.getCode() == 200) {
                                TiShi.showShort("验证码发送成功");
                                DaoJiShiTimer cdt = new DaoJiShiTimer(getYzmTv, 60000, 1000);
                                cdt.start();
                            }
                        }
                    }
                });
    }
}
