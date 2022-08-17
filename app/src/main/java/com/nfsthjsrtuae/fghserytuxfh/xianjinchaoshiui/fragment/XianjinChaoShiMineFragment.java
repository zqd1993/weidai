package com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nfsthjsrtuae.fghserytuxfh.R;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiadapter.MineXianjinChaoShiDaiAdapter;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiadapter.XianjinChaoShiDaiAdapter1;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.BaseRespModelXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.CompanyXianjinChaoShiInfoModel;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshimodel.MineXianjinChaoShiItemModel;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.ApiSubscriber;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.NetError;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.XApi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.XianjinChaoShiWebViewActivity;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.activity.AboutUsXianjinChaoShiActivity;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.activity.CancellationXianjinChaoShiAccountActivity;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiui.activity.XianjinChaoShiSettingActivity;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.SharedPreferencesXianjinChaoShiUtilis;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiutils.ToastUtilXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshinet.ApiXianjinChaoShi;
import com.nfsthjsrtuae.fghserytuxfh.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.nfsthjsrtuae.fghserytuxfh.xianjinchaoshiwidget.NormalXianjinChaoShiDialog;
import com.nfsthjsrtuae.fghserytuxfh.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class XianjinChaoShiMineFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MineXianjinChaoShiDaiAdapter mineAdapter;
    private XianjinChaoShiDaiAdapter1 xianjinChaoShiDaiAdapter1;
    private List<MineXianjinChaoShiItemModel> list;
    private int[] imgRes = {R.drawable.lpyfsdrtusaru, R.drawable.wwetgrey, R.drawable.klfyoidrtu, R.drawable.xxtruysrtu, R.drawable.zbzxreasu, R.drawable.xxdrydrtu};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们", "投诉邮箱", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalXianjinChaoShiDialog normalXianjinChaoShiDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        phone = SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 6; i++) {
            MineXianjinChaoShiItemModel model = new MineXianjinChaoShiItemModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_xian_jin_chao_shi_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCompanyInfo();
    }

    private void initAdapter() {
        if (mineAdapter == null) {
            mineAdapter = new MineXianjinChaoShiDaiAdapter(getActivity());
            mineAdapter.setData(list);
            mineAdapter.setHasStableIds(true);
            mineAdapter.setRecItemClick(new RecyclerItemCallback<MineXianjinChaoShiItemModel, MineXianjinChaoShiDaiAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineXianjinChaoShiItemModel model, int tag, MineXianjinChaoShiDaiAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ApiXianjinChaoShi.getZc());
                            Router.newIntent(getActivity())
                                    .to(XianjinChaoShiWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ApiXianjinChaoShi.getYs());
                            Router.newIntent(getActivity())
                                    .to(XianjinChaoShiWebViewActivity.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 2:
                            Router.newIntent(getActivity())
                                    .to(AboutUsXianjinChaoShiActivity.class)
                                    .launch();
                            break;
                        case 3:
                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText(null, mailStr);
                            clipboard.setPrimaryClip(clipData);
                            ToastUtilXianjinChaoShi.showShort("复制成功");
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(XianjinChaoShiSettingActivity.class)
                                    .launch();
                            break;
                        case 5:
                            Router.newIntent(getActivity())
                                    .to(CancellationXianjinChaoShiAccountActivity.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineAdapter);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesXianjinChaoShiUtilis.getStringFromPref("API_BASE_URL"))) {
            ApiXianjinChaoShi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelXianjinChaoShi<CompanyXianjinChaoShiInfoModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelXianjinChaoShi<CompanyXianjinChaoShiInfoModel>>getScheduler())
                    .compose(this.<BaseRespModelXianjinChaoShi<CompanyXianjinChaoShiInfoModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelXianjinChaoShi<CompanyXianjinChaoShiInfoModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelXianjinChaoShi<CompanyXianjinChaoShiInfoModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    SharedPreferencesXianjinChaoShiUtilis.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (normalXianjinChaoShiDialog != null) {
            normalXianjinChaoShiDialog.dismiss();
            normalXianjinChaoShiDialog = null;
        }
        super.onDestroy();
    }
}
