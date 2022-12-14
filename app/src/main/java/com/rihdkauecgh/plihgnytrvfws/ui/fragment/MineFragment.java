package com.rihdkauecgh.plihgnytrvfws.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.adapter.MineAdapter;
import com.rihdkauecgh.plihgnytrvfws.model.BaseRespModel;
import com.rihdkauecgh.plihgnytrvfws.model.ConfigModel;
import com.rihdkauecgh.plihgnytrvfws.model.MineItemModel;
import com.rihdkauecgh.plihgnytrvfws.mvp.XActivity;
import com.rihdkauecgh.plihgnytrvfws.net.ApiSubscriber;
import com.rihdkauecgh.plihgnytrvfws.net.NetError;
import com.rihdkauecgh.plihgnytrvfws.net.XApi;
import com.rihdkauecgh.plihgnytrvfws.ui.LoginActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.WebViewActivity;
import com.rihdkauecgh.plihgnytrvfws.utils.SharedPreferencesUtilis;
import com.rihdkauecgh.plihgnytrvfws.utils.StaticUtil;
import com.rihdkauecgh.plihgnytrvfws.utils.ToastUtil;
import com.rihdkauecgh.plihgnytrvfws.net.Api;
import com.rihdkauecgh.plihgnytrvfws.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.rihdkauecgh.plihgnytrvfws.widget.NormalDialog;
import com.rihdkauecgh.plihgnytrvfws.mvp.XFragment;
import com.rihdkauecgh.plihgnytrvfws.ui.activity.AboutUsActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.activity.CancellationAccountActivity;
import com.rihdkauecgh.plihgnytrvfws.ui.activity.FeedBackActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private MineAdapter mineAdapter;
    private List<MineItemModel> list;
    private int[] imgRes = {R.drawable.wd_icon_zcxy, R.drawable.wd_icon_ysxy, R.drawable.wd_icon_yjfk, R.drawable.wd_icon_gywm,
            R.drawable.wd_icon_xxts, R.drawable.wd_tsyx, R.drawable.wd_icon_zcz, R.drawable.wd_icon_zczh};
    private String[] tvRes = {"????????????", "????????????", "????????????", "????????????", "???????????????", "????????????", "????????????", "????????????"};
    private Bundle bundle;
    private NormalDialog normalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        mailStr = SharedPreferencesUtilis.getStringFromPref("APP_MAIL");
        phone = SharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 8; i++) {
            MineItemModel model = new MineItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineAdapter == null) {
            mineAdapter = new MineAdapter(getActivity());
            mineAdapter.setData(list);
            mineAdapter.setHasStableIds(true);
            mineAdapter.setRecItemClick(new RecyclerItemCallback<MineItemModel, MineAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemModel model, int tag, MineAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("AGREEMENT"))) {
                                bundle = new Bundle();
                                bundle.putInt("tag", 1);
                                bundle.putString("url", SharedPreferencesUtilis.getStringFromPref("AGREEMENT") + Api.PRIVACY_POLICY);
                                StaticUtil.getValue((XActivity) getActivity(), WebViewActivity.class, bundle);
                            }
                            break;
                        case 1:
                            if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("AGREEMENT"))) {
                                bundle = new Bundle();
                                bundle.putInt("tag", 2);
                                bundle.putString("url", SharedPreferencesUtilis.getStringFromPref("AGREEMENT") + Api.USER_SERVICE_AGREEMENT);
                                StaticUtil.getValue((XActivity) getActivity(), WebViewActivity.class, bundle);
                            }
                            break;
                        case 2:
                            StaticUtil.getValue((XActivity) getActivity(), FeedBackActivity.class, null);
                            break;
                        case 3:
                            StaticUtil.getValue((XActivity) getActivity(), AboutUsActivity.class, null);
                            break;
                        case 4:
                            normalDialog = new NormalDialog(getActivity());
                            normalDialog.setTitle("????????????")
                                    .setContent("?????????????????????")
                                    .setCancelText("??????")
                                    .setLeftListener(v -> {
                                        ToastUtil.showShort("????????????");
                                        normalDialog.dismiss();
                                    })
                                    .setConfirmText("??????")
                                    .setRightListener(v -> {
                                        ToastUtil.showShort("????????????");
                                        normalDialog.dismiss();
                                    }).show();
                            break;
                        case 5:
                            getGankData();
                            break;
                        case 6:
                            StaticUtil.getValue((XActivity) getActivity(), CancellationAccountActivity.class, null);
                            break;
                        case 7:
                            normalDialog = new NormalDialog(getActivity());
                            normalDialog.setTitle("????????????")
                                    .setContent("????????????????????????")
                                    .setCancelText("??????")
                                    .setLeftListener(v -> {
                                        normalDialog.dismiss();
                                    })
                                    .setConfirmText("??????")
                                    .setRightListener(v -> {
                                        normalDialog.dismiss();
                                        SharedPreferencesUtilis.saveStringIntoPref("phone", "");
                                        StaticUtil.getValue((XActivity) getActivity(), LoginActivity.class, null, true);
                                    }).show();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter);
        }
    }

    @Override
    public void onDestroy() {
        if (normalDialog != null) {
            normalDialog.dismiss();
            normalDialog = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilis.getStringFromPref("HTTP_API_URL"))) {
            Api.getGankService().getGankData()
                    .compose(XApi.<BaseRespModel<ConfigModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModel<ConfigModel>>getScheduler())
                    .compose(this.<BaseRespModel<ConfigModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModel<ConfigModel>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseRespModel<ConfigModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    mailStr = gankResults.getData().getAppMail();
                                    SharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                    normalDialog = new NormalDialog(getActivity());
                                    normalDialog.setTitle("????????????")
                                            .setContent(mailStr)
                                            .showOnlyBtn().show();
                                }
                            }
                        }
                    });
        }
    }
}
