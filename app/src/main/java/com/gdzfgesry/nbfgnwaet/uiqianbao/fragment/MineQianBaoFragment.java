package com.gdzfgesry.nbfgnwaet.uiqianbao.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gdzfgesry.nbfgnwaet.R;
import com.gdzfgesry.nbfgnwaet.adapterqianbao.MineQianBaoAdapter;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.BaseRespQianBaoModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.QianBaoCompanyInfoModel;
import com.gdzfgesry.nbfgnwaet.qianbaomodel.MineItemModelQianBao;
import com.gdzfgesry.nbfgnwaet.netqianbao.ApiSubscriber;
import com.gdzfgesry.nbfgnwaet.netqianbao.NetError;
import com.gdzfgesry.nbfgnwaet.netqianbao.XApi;
import com.gdzfgesry.nbfgnwaet.uiqianbao.QianBaoWebViewActivity;
import com.gdzfgesry.nbfgnwaet.uiqianbao.activity.SettingQianBaoActivity;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.SharedQianBaoPreferencesUtilis;
import com.gdzfgesry.nbfgnwaet.utilsqianbao.ToastQianBaoUtil;
import com.gdzfgesry.nbfgnwaet.netqianbao.QianBaoApi;
import com.gdzfgesry.nbfgnwaet.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.gdzfgesry.nbfgnwaet.qianbaowidget.NormalDialogQianBao;
import com.gdzfgesry.nbfgnwaet.mvp.XFragment;
import com.gdzfgesry.nbfgnwaet.uiqianbao.activity.QianBaoAboutUsActivity;
import com.gdzfgesry.nbfgnwaet.uiqianbao.activity.CancellationQianBaoAccountActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineQianBaoFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mail_str_tv)
    TextView mail_str_tv;
    @BindView(R.id.mail_fl)
    View mail_fl;

    public static String toString(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double toDouble(Object o) {

        return toDouble(o, 0);
    }

    public static double toDouble(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long toLong(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    private MineQianBaoAdapter mineQianBaoAdapter;
    private List<MineItemModelQianBao> list;
    private int[] imgRes = {R.drawable.mkzdbifg, R.drawable.lhjjfgh, R.drawable.ewdfb, R.drawable.kghfgh, R.drawable.xzvserfs};
    private String[] tvRes = {"关于我们", "隐私协议", "注册协议", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalDialogQianBao normalDialogQianBao;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        getCompanyInfo();
        phone = SharedQianBaoPreferencesUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            MineItemModelQianBao model = new MineItemModelQianBao();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        mail_fl.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            ToastQianBaoUtil.showShort("复制成功");
        });
    }

    public static String twetgxjui(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double hzrgzdfh(Object o) {

        return toDouble(o, 0);
    }

    public static double cvdzrtgfh(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long kkerxfgh(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_qian_bao;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineQianBaoAdapter == null) {
            mineQianBaoAdapter = new MineQianBaoAdapter(getActivity());
            mineQianBaoAdapter.setData(list);
            mineQianBaoAdapter.setHasStableIds(true);
            mineQianBaoAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModelQianBao, MineQianBaoAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModelQianBao model, int tag, MineQianBaoAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(QianBaoAboutUsActivity.class)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", QianBaoApi.getYs());
                            Router.newIntent(getActivity())
                                    .to(QianBaoWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                        case 2:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", QianBaoApi.getZc());
                            Router.newIntent(getActivity())
                                    .to(QianBaoWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 3:
                            Router.newIntent(getActivity())
                                    .to(SettingQianBaoActivity.class)
                                    .launch();
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(CancellationQianBaoAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineQianBaoAdapter);
        }
    }

    public static String zseghxdfh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double bbzrt(Object o) {

        return toDouble(o, 0);
    }

    public static double tyzdfhxf(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long jkjkrtzre(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedQianBaoPreferencesUtilis.getStringFromPref("API_BASE_URL"))) {
            QianBaoApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespQianBaoModel<QianBaoCompanyInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespQianBaoModel<QianBaoCompanyInfoModel>>getScheduler())
                    .compose(this.<BaseRespQianBaoModel<QianBaoCompanyInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQianBaoModel<QianBaoCompanyInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespQianBaoModel<QianBaoCompanyInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_str_tv.setText(mailStr);
                                    SharedQianBaoPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    public static String wethzxdfh(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double nzretzhdf(Object o) {

        return toDouble(o, 0);
    }

    public static double yyyrexfhgj(Object o, int defaultValue) {
        if (o == null) {
            return defaultValue;
        }

        double value;
        try {
            value = Double.valueOf(o.toString().trim());
        } catch (Exception e) {
            value = defaultValue;
        }

        return value;
    }

    public static long bvdryrtyae(Object o, long defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        long value = 0;
        try {
            String s = o.toString().trim();
            if (s.contains(".")) {
                value = Long.valueOf(s.substring(0, s.lastIndexOf(".")));
            } else {
                value = Long.valueOf(s);
            }
        } catch (Exception e) {
            value = defaultValue;
        }


        return value;
    }

    @Override
    public void onDestroy() {
        if (normalDialogQianBao != null) {
            normalDialogQianBao.dismiss();
            normalDialogQianBao = null;
        }
        super.onDestroy();
    }
}
