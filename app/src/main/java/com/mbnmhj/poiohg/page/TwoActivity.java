package com.mbnmhj.poiohg.page;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mbnmhj.poiohg.net.NetApi;
import com.mbnmhj.poiohg.R;
import com.mbnmhj.poiohg.entity.MainModel;
import com.mbnmhj.poiohg.entity.CFEntity;
import com.mbnmhj.poiohg.entity.DengLuModel;
import com.mbnmhj.poiohg.mvp.XActivity;
import com.mbnmhj.poiohg.net.ApiSubscriber;
import com.mbnmhj.poiohg.net.NetError;
import com.mbnmhj.poiohg.net.XApi;
import com.mbnmhj.poiohg.util.NewToast;
import com.mbnmhj.poiohg.util.AllUtil;
import com.mbnmhj.poiohg.util.SpUtil;
import com.mbnmhj.poiohg.util.SBarUtil;
import com.mbnmhj.poiohg.view.ClickTextView;
import com.mbnmhj.poiohg.view.CountDownTimer;

import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import cn.droidlover.xstatecontroller.XStateController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TwoActivity extends XActivity {

    private XStateController xStateController;
    private EditText mobileEt, yzmEt;
    private TextView getYzmTv, dlBtn;
    private CheckBox remindCb;
    private ClickTextView readTv;
    private View yzmCv;

    private String phoneStr, yzmStr, ip = "";
    private Bundle bundle;
    public boolean isChecked = true, isNeedYzm = true;

    /**
     * 设置输入框只能有两位小数
     *
     * @param editText editText
     */
    public static void setPricePoint(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(".")) {
                    editText.setText("");
                    return;
                }
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().indexOf(".") != 1 && s.toString().endsWith(".")) {
                    s = s.toString().subSequence(0, s.length() - 1);
                    editText.setText(s);
                    editText.setSelection(s.length());
                    return;
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        SBarUtil.setTransparent(this, false);
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
        new Handler().postDelayed(() -> {
            getConfig();
        }, 200);
        readTv.setText(AllUtil.createDlSpanTexts(), position -> {
            bundle = new Bundle();
            if (position == 1) {
                bundle.putString("url", NetApi.ZCXY);
                bundle.putString("biaoti", getResources().getString(R.string.zcxy));
            } else {
                bundle.putString("url", NetApi.YSXY);
                bundle.putString("biaoti", getResources().getString(R.string.yszc));
            }
            AllUtil.jumpPage(TwoActivity.this, NetPageActivity.class, bundle);
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

    /**
     * 格式化价格，四舍五入保留两位小数
     *
     * @param price 价格
     * @return 位小数四舍五入保留两
     * * 小数计算（四舍五入）：解决DecimalFormat("#.00")使用时小数点后第三位值为5的时候无法进位的问题
     * * @param
     * * @return
     */
    public static String formatPrice(Double price) {
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String startStr = df.format(price);
        String startDecimal = startStr.split("\\.")[1];
        Double jishu = 0.01;
        Double endVal = 0.0;
        if (startDecimal.length() > 2 && "5".equals(String.valueOf(startDecimal.charAt(2)))) {
            endVal = Double.valueOf(startStr.substring(0, startStr.length() - 1)) + jishu;
        } else {
            endVal = Double.valueOf(df.format(price));
        }
        return df.format(endVal);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_one;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getConfig() {
        NetApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<MainModel<CFEntity>>() {
                    @Override
                    protected void onFail(NetError error) {
                        AllUtil.showErrorInfo(TwoActivity.this, error);
                    }

                    @Override
                    public void onNext(MainModel<CFEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                SpUtil.saveString("app_mail", configEntity.getData().getAppMail());
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

    /**
     * 自动补0
     *
     * @param price 价格
     * @return
     */
    public static String formatDouble(Double price) {
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(Double.valueOf(df.format(price)));
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

    /**
     * json字符串数据转化成Map
     *
     * @return json对应的map
     **/
    /*public static HashMap<String, String> toLinkedHashMap(String object) {
        try {

            HashMap<String, String> data = new LinkedHashMap<String, String>();
            // 将json字符串转换成jsonObject
            JSONObject jsonObject = JSONObject.fromObject(object);
            Iterator it = jsonObject.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                Object value = jsonObject.get(key);
                if (value != null) {
                    data.put(key, value.toString());
                } else {
                    data.put(key, "");
                }
            }
            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<String, String>();
        }
    }*/
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
        NetApi.getInterfaceUtils().login(phone, verificationStr, "", ip)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<MainModel<DengLuModel>>() {
                    @Override
                    protected void onFail(NetError error) {
                        AllUtil.showErrorInfo(TwoActivity.this, error);
                        if (xStateController != null)
                            xStateController.showContent();
                    }

                    @Override
                    public void onNext(MainModel<DengLuModel> dlModel) {
                        if (xStateController != null)
                            xStateController.showContent();
                        if (dlModel != null && dlModel.getCode() == 200) {
                            if (dlModel.getData() != null && dlModel.getCode() == 200) {
                                AllUtil.jumpPage(TwoActivity.this, WorkActivity.class);
                                int mobileType = dlModel.getData().getMobileType();
                                SpUtil.saveString("ip", ip);
                                SpUtil.saveString("phone", phone);
                                SpUtil.saveInt("mobileType", mobileType);
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

    public void getYzm(String phone) {
        NetApi.getInterfaceUtils().sendVerifyCode(phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<MainModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        AllUtil.showErrorInfo(TwoActivity.this, error);
                    }

                    @Override
                    public void onNext(MainModel mainModel) {
                        if (mainModel != null) {
                            if (mainModel.getCode() == 200) {
                                NewToast.showShort("验证码发送成功");
                                CountDownTimer cdt = new CountDownTimer(getYzmTv, 60000, 1000);
                                cdt.start();
                            }
                        }
                    }
                });
    }
}
