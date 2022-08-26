package com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.uuuu.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.R;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.aaaa.MineGeiNiHuaAdapter;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.mmmm.BaseRespModelGeiNiHua;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.mmmm.ConfigGeiNiHuaModel;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.mmmm.MineItemGeiNiHuaModel;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.mvp.XActivity;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.nnnn.ApiSubscriber;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.nnnn.NetError;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.nnnn.XApi;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.utils.GeiNiHuaStaticUtil;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.uuuu.GeiNiHuaWebViewActivity;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.uuuu.LoginGeiNiHuaActivity;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.uuuu.activity.CancellationGeiNiHuaAccountActivity;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.uuuu.activity.GeiNiHuaFeedBackActivity;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.utils.SharedPreferencesUtilisGeiNiHua;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.nnnn.ApiGeiNiHua;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.wwww.NormalDialogGeiNiHua;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.mvp.XFragment;
import com.rtyhdfhdlnsg.mnzdfgdsgytjwhbm.uuuu.activity.AboutUsActivityGeiNiHua;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineGeiNiHuaFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private MineGeiNiHuaAdapter mineGeiNiHuaAdapter;
    private List<MineItemGeiNiHuaModel> list;
    private int[] imgRes = {R.drawable.ewdfb, R.drawable.lhjjfgh, R.drawable.rdfhgfhsd, R.drawable.wd_icon_gywm,
            R.drawable.wevbsdg, R.drawable.lhjkghfg, R.drawable.xzvserfs};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "联系客服", "注销账户", "退出登录"};
    private Bundle bundle;
    private NormalDialogGeiNiHua normalDialogGeiNiHua;
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
        mailStr = SharedPreferencesUtilisGeiNiHua.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesUtilisGeiNiHua.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 7; i++) {
            MineItemGeiNiHuaModel model = new MineItemGeiNiHuaModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_geinihua_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineGeiNiHuaAdapter == null) {
            mineGeiNiHuaAdapter = new MineGeiNiHuaAdapter(getActivity());
            mineGeiNiHuaAdapter.setData(list);
            mineGeiNiHuaAdapter.setHasStableIds(true);
            mineGeiNiHuaAdapter.setRecItemClick(new RecyclerItemCallback<MineItemGeiNiHuaModel, MineGeiNiHuaAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemGeiNiHuaModel model, int tag, MineGeiNiHuaAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiGeiNiHua.PRIVACY_POLICY);
                            GeiNiHuaStaticUtil.getValue((XActivity) getActivity(), GeiNiHuaWebViewActivity.class, bundle);
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiGeiNiHua.USER_SERVICE_AGREEMENT);
                            GeiNiHuaStaticUtil.getValue((XActivity) getActivity(), GeiNiHuaWebViewActivity.class, bundle);
                            break;
                        case 2:
                            GeiNiHuaStaticUtil.getValue((XActivity) getActivity(), GeiNiHuaFeedBackActivity.class, null);
                            break;
                        case 3:
                            GeiNiHuaStaticUtil.getValue((XActivity) getActivity(), AboutUsActivityGeiNiHua.class, null);
                            break;
                        case 4:
                            getGankData();
                            break;
                        case 5:
                            GeiNiHuaStaticUtil.getValue((XActivity) getActivity(), CancellationGeiNiHuaAccountActivity.class, null);
                            break;
                        case 6:
                            normalDialogGeiNiHua = new NormalDialogGeiNiHua(getActivity());
                            normalDialogGeiNiHua.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        normalDialogGeiNiHua.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        normalDialogGeiNiHua.dismiss();
                                        SharedPreferencesUtilisGeiNiHua.saveStringIntoPref("phone", "");
                                        GeiNiHuaStaticUtil.getValue((XActivity) getActivity(), LoginGeiNiHuaActivity.class, null, true);
                                    }).show();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineGeiNiHuaAdapter);
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
        if (normalDialogGeiNiHua != null) {
            normalDialogGeiNiHua.dismiss();
            normalDialogGeiNiHua = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
            ApiGeiNiHua.getGankService().getGankData()
                    .compose(XApi.<BaseRespModelGeiNiHua<ConfigGeiNiHuaModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelGeiNiHua<ConfigGeiNiHuaModel>>getScheduler())
                    .compose(this.<BaseRespModelGeiNiHua<ConfigGeiNiHuaModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelGeiNiHua<ConfigGeiNiHuaModel>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseRespModelGeiNiHua<ConfigGeiNiHuaModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    mailStr = gankResults.getData().getAppMail();
                                    SharedPreferencesUtilisGeiNiHua.saveStringIntoPref("APP_MAIL", mailStr);
                                    normalDialogGeiNiHua = new NormalDialogGeiNiHua(getActivity());
                                    normalDialogGeiNiHua.setTitle("温馨提示")
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
