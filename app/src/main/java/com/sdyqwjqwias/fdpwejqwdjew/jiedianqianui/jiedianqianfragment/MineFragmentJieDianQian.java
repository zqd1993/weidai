package com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.jiedianqianfragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianadapter.MineJieDianQianAdapter;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.BaseRespModelJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.ConfigJieDianQianModel;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.MineItemJieDianQianModel;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.JieDianQianLoginActivity;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.WebViewActivityJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.jiedianqianactivity.AboutUsJieDianQianActivity;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.jiedianqianactivity.FeedBackActivityJieDianQian;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianwidget.JieDianQianNormalDialog;
import com.sdyqwjqwias.fdpwejqwdjew.mvp.XActivity;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.ApiSubscriber;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.NetError;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.XApi;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.JieDianQianSharedPreferencesUtilis;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianutils.StaticJieDianQianUtil;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqiannet.JieDianQianApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.sdyqwjqwias.fdpwejqwdjew.mvp.XFragment;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianui.jiedianqianactivity.JieDianQianCancellationAccountActivity;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragmentJieDianQian extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private MineJieDianQianAdapter mineJieDianQianAdapter;
    private List<MineItemJieDianQianModel> list;
    private int[] imgRes = {R.drawable.wd_icon_zcxy, R.drawable.wd_icon_ysxy, R.drawable.wd_icon_yjfk, R.drawable.wd_icon_gywm,
            R.drawable.wd_tsyx, R.drawable.wd_icon_zcz, R.drawable.wd_icon_zczh};
    private String[] tvRes = {"注册协议", "隐私协议", "意见反馈", "关于我们", "联系客服", "注销账户", "退出登录"};
    private Bundle bundle;
    private JieDianQianNormalDialog jieDianQianNormalDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        mailStr = JieDianQianSharedPreferencesUtilis.getStringFromPref("APP_MAIL");
        phone = JieDianQianSharedPreferencesUtilis.getStringFromPref("phone");
        phoneTv.setText(phone);
        for (int i = 0; i < 7; i++) {
            MineItemJieDianQianModel model = new MineItemJieDianQianModel();
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
        if (mineJieDianQianAdapter == null) {
            mineJieDianQianAdapter = new MineJieDianQianAdapter(getActivity());
            mineJieDianQianAdapter.setData(list);
            mineJieDianQianAdapter.setHasStableIds(true);
            mineJieDianQianAdapter.setRecItemClick(new RecyclerItemCallback<MineItemJieDianQianModel, MineJieDianQianAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemJieDianQianModel model, int tag, MineJieDianQianAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", JieDianQianApi.PRIVACY_POLICY);
                            StaticJieDianQianUtil.getValue((XActivity) getActivity(), WebViewActivityJieDianQian.class, bundle);
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", JieDianQianApi.USER_SERVICE_AGREEMENT);
                            StaticJieDianQianUtil.getValue((XActivity) getActivity(), WebViewActivityJieDianQian.class, bundle);
                            break;
                        case 2:
                            StaticJieDianQianUtil.getValue((XActivity) getActivity(), FeedBackActivityJieDianQian.class, null);
                            break;
                        case 3:
                            StaticJieDianQianUtil.getValue((XActivity) getActivity(), AboutUsJieDianQianActivity.class, null);
                            break;
//                        case 4:
//                            normalDialog = new NormalDialog(getActivity());
//                            normalDialog.setTitle("温馨提示")
//                                    .setContent("关闭或开启推送")
//                                    .setCancelText("开启")
//                                    .setLeftListener(v -> {
//                                        ToastUtil.showShort("开启成功");
//                                        normalDialog.dismiss();
//                                    })
//                                    .setConfirmText("关闭")
//                                    .setRightListener(v -> {
//                                        ToastUtil.showShort("关闭成功");
//                                        normalDialog.dismiss();
//                                    }).show();
//                            break;
                        case 4:
                            getGankData();
                            break;
                        case 5:
                            StaticJieDianQianUtil.getValue((XActivity) getActivity(), JieDianQianCancellationAccountActivity.class, bundle);
                            break;
                        case 6:
                            jieDianQianNormalDialog = new JieDianQianNormalDialog(getActivity());
                            jieDianQianNormalDialog.setTitle("温馨提示")
                                    .setContent("确定退出当前登录")
                                    .setCancelText("取消")
                                    .setLeftListener(v -> {
                                        jieDianQianNormalDialog.dismiss();
                                    })
                                    .setConfirmText("退出")
                                    .setRightListener(v -> {
                                        jieDianQianNormalDialog.dismiss();
                                        JieDianQianSharedPreferencesUtilis.saveStringIntoPref("phone", "");
                                        StaticJieDianQianUtil.getValue((XActivity) getActivity(), JieDianQianLoginActivity.class, null, true);
                                    }).show();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineJieDianQianAdapter);
        }
    }

    @Override
    public void onDestroy() {
        if (jieDianQianNormalDialog != null) {
            jieDianQianNormalDialog.dismiss();
            jieDianQianNormalDialog = null;
        }
        super.onDestroy();
    }

    public void getGankData() {
            JieDianQianApi.getGankService().getGankData()
                    .compose(XApi.<BaseRespModelJieDianQian<ConfigJieDianQianModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelJieDianQian<ConfigJieDianQianModel>>getScheduler())
                    .compose(this.<BaseRespModelJieDianQian<ConfigJieDianQianModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelJieDianQian<ConfigJieDianQianModel>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(BaseRespModelJieDianQian<ConfigJieDianQianModel> gankResults) {
                            if (gankResults != null) {
                                if (gankResults.getData() != null) {
                                    mailStr = gankResults.getData().getAppMail();
                                    JieDianQianSharedPreferencesUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                    jieDianQianNormalDialog = new JieDianQianNormalDialog(getActivity());
                                    jieDianQianNormalDialog.setTitle("温馨提示")
                                            .setContent(mailStr)
                                            .showOnlyBtn().show();
                                }
                            }
                        }
                    });
    }
}
