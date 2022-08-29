package com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.fragmentdkmiaoxia;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qznagsdnfdkdjdf.cvpdfms.R;
import com.qznagsdnfdkdjdf.cvpdfms.aaaadkmiaoxia.MineDKMiaoXiaOpAdapter;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.BaseRespModelDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.ConfigDKMiaoXiaOpModel;
import com.qznagsdnfdkdjdf.cvpdfms.mmmmdkmiaoxia.MineItemDKMiaoXiaOpModel;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.ApiSubscriber;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.NetError;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.XApi;
import com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.DKMiaoXiaOpWebViewActivity;
import com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.LoginDKMiaoXiaOpActivity;
import com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.activitydkmiaoxia.CancellationDKMiaoXiaOpAccountActivity;
import com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.activitydkmiaoxia.DKMiaoXiaOpFeedBackActivity;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.SharedPreferencesUtilisDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.utilsdkmiaoxia.ToastUtilDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.nnnndkmiaoxia.ApiDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.qznagsdnfdkdjdf.cvpdfms.wwwwdkmiaoxia.NormalDialogDKMiaoXiaOp;
import com.qznagsdnfdkdjdf.cvpdfms.mvp.XFragment;
import com.qznagsdnfdkdjdf.cvpdfms.uuuudkmiaoxia.activitydkmiaoxia.AboutUsActivityDKMiaoXiaOp;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineDKMiaoXiaOpFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private MineDKMiaoXiaOpAdapter mineDKMiaoXiaOpAdapter;
    private List<MineItemDKMiaoXiaOpModel> list;
    private int[] imgRes = {R.drawable.ewdfb, R.drawable.lhjjfgh, R.drawable.rdfhgfhsd, R.drawable.wd_icon_gywm,
            R.drawable.kghfgh, R.drawable.wevbsdg, R.drawable.lhjkghfg, R.drawable.xzvserfs};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "个性化推荐", "投诉邮箱", "注销账户", "退出登录"};
    private Bundle bundle;
    private NormalDialogDKMiaoXiaOp normalDialogDKMiaoXiaOp;
    private String mailStr = "", phone = "";

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

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        mailStr = SharedPreferencesUtilisDKMiaoXiaOp.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesUtilisDKMiaoXiaOp.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            MineItemDKMiaoXiaOpModel model = new MineItemDKMiaoXiaOpModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dk_miao_xia_op_new_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineDKMiaoXiaOpAdapter == null) {
            mineDKMiaoXiaOpAdapter = new MineDKMiaoXiaOpAdapter(getActivity());
            mineDKMiaoXiaOpAdapter.setData(list);
            mineDKMiaoXiaOpAdapter.setHasStableIds(true);
            mineDKMiaoXiaOpAdapter.setRecItemClick(new RecyclerItemCallback<MineItemDKMiaoXiaOpModel, MineDKMiaoXiaOpAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemDKMiaoXiaOpModel model, int tag, MineDKMiaoXiaOpAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiDKMiaoXiaOp.PRIVACY_POLICY);
                            Router.newIntent(getActivity())
                                    .to(DKMiaoXiaOpWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiDKMiaoXiaOp.USER_SERVICE_AGREEMENT);
                            Router.newIntent(getActivity())
                                    .to(DKMiaoXiaOpWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(DKMiaoXiaOpFeedBackActivity.class)
                                    .launch();
                            break;
                        case 3:
                            Router.newIntent(getActivity())
                                    .to(AboutUsActivityDKMiaoXiaOp.class)
                                    .launch();
                            break;
                        case 4:
                            normalDialogDKMiaoXiaOp = new NormalDialogDKMiaoXiaOp(getActivity());
                            normalDialogDKMiaoXiaOp.setTitle("温馨提示")
                                    .setContent("关闭或开启推送")
                                    .setCancelText("开启")
                                    .setLeftListener(v -> {
                                        ToastUtilDKMiaoXiaOp.showShort("开启成功");
                                        normalDialogDKMiaoXiaOp.dismiss();
                                    })
                                    .setConfirmText("关闭")
                                    .setRightListener(v -> {
                                        ToastUtilDKMiaoXiaOp.showShort("关闭成功");
                                        normalDialogDKMiaoXiaOp.dismiss();
                                    }).show();
                            break;
                        case 5:
                            getGankData();
                            break;
                        case 6:
                            Router.newIntent(getActivity())
                                    .to(CancellationDKMiaoXiaOpAccountActivity.class)
                                    .launch();
                            break;
                        case 7:
                            normalDialogDKMiaoXiaOp = new NormalDialogDKMiaoXiaOp(getActivity());
                            normalDialogDKMiaoXiaOp.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalDialogDKMiaoXiaOp.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        normalDialogDKMiaoXiaOp.dismiss();
                                        SharedPreferencesUtilisDKMiaoXiaOp.saveStringIntoPref("phone", "");
                                        Router.newIntent(getActivity())
                                                .to(LoginDKMiaoXiaOpActivity.class)
                                                .launch();
                                        getActivity().finish();
                                    }).show();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineDKMiaoXiaOpAdapter);
        }
    }

    public static String nhxfhgzdfg(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double zedfx(Object o) {

        return toDouble(o, 0);
    }

    public static double tysthfgh(Object o, int defaultValue) {
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

    public static long bzdfhyrty(Object o, long defaultValue) {
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
        if (normalDialogDKMiaoXiaOp != null) {
            normalDialogDKMiaoXiaOp.dismiss();
            normalDialogDKMiaoXiaOp = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
        ApiDKMiaoXiaOp.getGankService().getGankData()
                .compose(XApi.<BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel>>getApiTransformer())
                .compose(XApi.<BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel>>getScheduler())
                .compose(this.<BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseRespModelDKMiaoXiaOp<ConfigDKMiaoXiaOpModel> gankResults) {
                        if (gankResults != null) {
                            if (gankResults.getData() != null) {
                                mailStr = gankResults.getData().getAppMail();
                                SharedPreferencesUtilisDKMiaoXiaOp.saveStringIntoPref("APP_MAIL", mailStr);
                                normalDialogDKMiaoXiaOp = new NormalDialogDKMiaoXiaOp(getActivity());
                                normalDialogDKMiaoXiaOp.setTitle("温馨提示")
                                        .setContent(mailStr)
                                        .showOnlyBtn().show();
                            }
                        }
                    }
                });
    }

    public static String ewrrdf(Object o) {
        String value = "";
        try {
            value = o.toString();
        } catch (Exception e) {
        }

        return value;
    }


    public static double mfghxfh(Object o) {

        return toDouble(o, 0);
    }

    public static double ewtdfs(Object o, int defaultValue) {
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

    public static long nzgzdg(Object o, long defaultValue) {
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
}
