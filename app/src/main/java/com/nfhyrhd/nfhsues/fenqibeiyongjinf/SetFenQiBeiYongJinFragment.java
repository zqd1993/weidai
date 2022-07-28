package com.nfhyrhd.nfhsues.fenqibeiyongjinf;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nfhyrhd.nfhsues.R;
import com.nfhyrhd.nfhsues.fenqibeiyongjina.AboutInfoActivityFenQiBeiYongJin;
import com.nfhyrhd.nfhsues.fenqibeiyongjina.FenQiBeiYongJinDlActivity;
import com.nfhyrhd.nfhsues.fenqibeiyongjina.FeedbackActivityFenQiBeiYongJin;
import com.nfhyrhd.nfhsues.fenqibeiyongjina.FenQiBeiYongJinJumpH5Activity;
import com.nfhyrhd.nfhsues.fenqibeiyongjina.SetItemAdapterFenQiBeiYongJin;
import com.nfhyrhd.nfhsues.fenqibeiyongjina.SettingFenQiBeiYongJinActivity;
import com.nfhyrhd.nfhsues.fenqibeiyongjinapi.HttpApiFenQiBeiYongJin;
import com.nfhyrhd.nfhsues.fenqibeiyongjinm.FenQiBeiYongJinBaseModel;
import com.nfhyrhd.nfhsues.fenqibeiyongjinm.ProductModelFenQiBeiYongJin;
import com.nfhyrhd.nfhsues.fenqibeiyongjinm.SetModelFenQiBeiYongJin;
import com.nfhyrhd.nfhsues.mvp.XActivity;
import com.nfhyrhd.nfhsues.mvp.XFragment;
import com.nfhyrhd.nfhsues.net.ApiSubscriber;
import com.nfhyrhd.nfhsues.net.NetError;
import com.nfhyrhd.nfhsues.net.XApi;
import com.nfhyrhd.nfhsues.fenqibeiyongjinu.OpenFenQiBeiYongJinUtil;
import com.nfhyrhd.nfhsues.fenqibeiyongjinu.PreferencesFenQiBeiYongJinOpenUtil;
import com.nfhyrhd.nfhsues.fenqibeiyongjinw.RemindFenQiBeiYongJinDialog;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFenQiBeiYongJinFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.logout_btn)
    View logoutBtn;

    private ProductModelFenQiBeiYongJin productModelFenQiBeiYongJin;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemAdapterFenQiBeiYongJin setItemAdapterFenQiBeiYongJin;

    private RemindFenQiBeiYongJinDialog dialog;

    private String mailStr = "";

    /**
     * 异步的Get请求
     *
     * @param urlStr
     * @param callBack
     */
    public void doGetAsyn(final String urlStr, final OpenFenQiBeiYongJinUtil.CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();
    }

    /**
     * 异步的Post请求
     *
     * @param urlStr
     * @param params
     * @param callBack
     * @throws Exception
     */
    public void doPostAsyn(final String urlStr, final String params,
                           final OpenFenQiBeiYongJinUtil.CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();

    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencesFenQiBeiYongJinOpenUtil.getString("app_mail");
        phone = PreferencesFenQiBeiYongJinOpenUtil.getString("phone");
        userPhoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        productList();
        initSetAdapter();
        logoutBtn.setOnClickListener(v -> {
            dialog = new RemindFenQiBeiYongJinDialog(getActivity()).setCancelText("取消")
                    .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
            dialog.setOnButtonClickListener(new RemindFenQiBeiYongJinDialog.OnButtonClickListener() {
                @Override
                public void onSureClicked() {
                    dialog.dismiss();
                    PreferencesFenQiBeiYongJinOpenUtil.saveString("phone", "");
                    OpenFenQiBeiYongJinUtil.getValue((XActivity) getActivity(), FenQiBeiYongJinDlActivity.class, null, true);
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
        return R.layout.fragment_set_fen_qi_bei_yong_jin;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initSetAdapter() {
        SetModelFenQiBeiYongJin model = new SetModelFenQiBeiYongJin(R.drawable.xcvbnrtusr, "注册协议");
        SetModelFenQiBeiYongJin model1 = new SetModelFenQiBeiYongJin(R.drawable.xbrtujsr, "隐私协议");
        SetModelFenQiBeiYongJin model2 = new SetModelFenQiBeiYongJin(R.drawable.iodtyusrj, "意见反馈");
        SetModelFenQiBeiYongJin model3 = new SetModelFenQiBeiYongJin(R.drawable.seryxfudrt, "关于我们");
        SetModelFenQiBeiYongJin model4 = new SetModelFenQiBeiYongJin(R.drawable.lyuodrt, "系统设置");
        List<SetModelFenQiBeiYongJin> list = new ArrayList<>();
        List<SetModelFenQiBeiYongJin> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        setItemAdapterFenQiBeiYongJin = new SetItemAdapterFenQiBeiYongJin(R.layout.adpater_fen_qi_bei_yong_jin_set_item, list);
        setItemAdapterFenQiBeiYongJin.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiFenQiBeiYongJin.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpenFenQiBeiYongJinUtil.getValue((XActivity) getActivity(), FenQiBeiYongJinJumpH5Activity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpApiFenQiBeiYongJin.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpenFenQiBeiYongJinUtil.getValue((XActivity) getActivity(), FenQiBeiYongJinJumpH5Activity.class, webBundle);
                    break;
                case 2:
                    OpenFenQiBeiYongJinUtil.getValue((XActivity) getActivity(), FeedbackActivityFenQiBeiYongJin.class, null);
                    break;
                case 3:
                    OpenFenQiBeiYongJinUtil.getValue((XActivity) getActivity(), AboutInfoActivityFenQiBeiYongJin.class, null);
                    break;
                case 4:
                    OpenFenQiBeiYongJinUtil.getValue((XActivity) getActivity(), SettingFenQiBeiYongJinActivity.class, null);
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setList.setAdapter(setItemAdapterFenQiBeiYongJin);
    }

    /**
     * 异步的Get请求
     *
     * @param urlStr
     * @param callBack
     */
    public void kktdyusrtyu(final String urlStr, final OpenFenQiBeiYongJinUtil.CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();
    }

    /**
     * 异步的Post请求
     *
     * @param urlStr
     * @param params
     * @param callBack
     * @throws Exception
     */
    public void zdryrtyu(final String urlStr, final String params,
                           final OpenFenQiBeiYongJinUtil.CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();

    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public String mmdtykdtu(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public String jjrstyasery(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public void toWeb(ProductModelFenQiBeiYongJin model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpenFenQiBeiYongJinUtil.getValue((XActivity) getActivity(), FenQiBeiYongJinJumpH5Activity.class, bundle);
        }
    }

    public void productList() {
            mobileType = PreferencesFenQiBeiYongJinOpenUtil.getInt("mobileType");
            phone = PreferencesFenQiBeiYongJinOpenUtil.getString("phone");
            HttpApiFenQiBeiYongJin.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiBeiYongJinBaseModel<List<ProductModelFenQiBeiYongJin>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpenFenQiBeiYongJinUtil.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(FenQiBeiYongJinBaseModel<List<ProductModelFenQiBeiYongJin>> fenQiBeiYongJinBaseModel) {
                            if (fenQiBeiYongJinBaseModel != null) {
                                if (fenQiBeiYongJinBaseModel.getCode() == 200 && fenQiBeiYongJinBaseModel.getData() != null) {
                                    if (fenQiBeiYongJinBaseModel.getData() != null && fenQiBeiYongJinBaseModel.getData().size() > 0) {
                                        productModelFenQiBeiYongJin = fenQiBeiYongJinBaseModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
    }

    public void productClick(ProductModelFenQiBeiYongJin model) {
            if (model == null) {
                return;
            }
            phone = PreferencesFenQiBeiYongJinOpenUtil.getString("phone");
            HttpApiFenQiBeiYongJin.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<FenQiBeiYongJinBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(FenQiBeiYongJinBaseModel fenQiBeiYongJinBaseModel) {
                            toWeb(model);
                        }
                    });
    }

    /**
     * 异步的Get请求
     *
     * @param urlStr
     * @param callBack
     */
    public void xxfhrdtu(final String urlStr, final OpenFenQiBeiYongJinUtil.CallBack callBack) {
        new Thread() {
            public void run() {
                try {
                    String result = doGet(urlStr);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();
    }

    /**
     * 异步的Post请求
     *
     * @param urlStr
     * @param params
     * @param callBack
     * @throws Exception
     */
    public void fxghfdturtu(final String urlStr, final String params,
                           final OpenFenQiBeiYongJinUtil.CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();

    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public String ertertyfdgyu(String urlStr) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;

    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public String tyjdtyudrtu(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
