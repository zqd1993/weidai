package com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkafragment;

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

import com.nsryryasdt.ioerdfjrtu.R;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaadapter.MineZhouZhuanXinYongKaAdapter;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.BaseRespModelZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.CompanyInfoZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkamodel.MineItemZhouZhuanXinYongKaModel;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ApiSubscriber;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.NetError;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.XApi;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.WebViewActivityZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkaactivity.AboutZhouZhuanXinYongKaUsActivity;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkaactivity.SettingZhouZhuanXinYongKaActivity;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.SharedPreferencesUtilisZhouZhuanXinYongKa;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkautils.ToastZhouZhuanXinYongKaUtil;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongka.ZhouZhuanXinYongKaApi;
import com.nsryryasdt.ioerdfjrtu.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkawidget.NormalZhouZhuanXinYongKaDialog;
import com.nsryryasdt.ioerdfjrtu.mvp.XFragment;
import com.nsryryasdt.ioerdfjrtu.zhouzhuanxinyongkaui.zhouzhuanxinyongkaactivity.CancellationAccountActivityZhouZhuanXinYongKa;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class MineFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mail_tv)
    TextView mail_tv;
    @BindView(R.id.copy_mail_tv)
    View copy_mail_tv;

    private MineZhouZhuanXinYongKaAdapter mineZhouZhuanXinYongKaAdapter;
    private List<MineItemZhouZhuanXinYongKaModel> list;
    private int[] imgRes = {R.drawable.dfhseryurtu, R.drawable.wwyhxftu, R.drawable.fgfchrtuy,
            R.drawable.ergcjsrui, R.drawable.vvbndrurtu};
    private String[] tvRes = {"关于我们", "隐私协议", "注册协议", "系统设置", "注销账户"};
    private Bundle bundle;
    private NormalZhouZhuanXinYongKaDialog normalZhouZhuanXinYongKaDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        getCompanyInfo();
        phone = SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 5; i++) {
            MineItemZhouZhuanXinYongKaModel model = new MineItemZhouZhuanXinYongKaModel();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        copy_mail_tv.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            ToastZhouZhuanXinYongKaUtil.showShort("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_zhou_zhuan_xin_yong_ka;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        if (mineZhouZhuanXinYongKaAdapter == null) {
            mineZhouZhuanXinYongKaAdapter = new MineZhouZhuanXinYongKaAdapter(getActivity());
            mineZhouZhuanXinYongKaAdapter.setData(list);
            mineZhouZhuanXinYongKaAdapter.setHasStableIds(true);
            mineZhouZhuanXinYongKaAdapter.setRecItemClick(new RecyclerItemCallback<MineItemZhouZhuanXinYongKaModel, MineZhouZhuanXinYongKaAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MineItemZhouZhuanXinYongKaModel model, int tag, MineZhouZhuanXinYongKaAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (position) {
                        case 0:
                            Router.newIntent(getActivity())
                                    .to(AboutZhouZhuanXinYongKaUsActivity.class)
                                    .launch();
                            break;
                        case 1:
                            bundle = new Bundle();
                            bundle.putInt("tag", 2);
                            bundle.putString("url", ZhouZhuanXinYongKaApi.getYs());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityZhouZhuanXinYongKa.class)
                                    .data(bundle)
                                    .launch();
                        case 2:
                            bundle = new Bundle();
                            bundle.putInt("tag", 1);
                            bundle.putString("url", ZhouZhuanXinYongKaApi.getZc());
                            Router.newIntent(getActivity())
                                    .to(WebViewActivityZhouZhuanXinYongKa.class)
                                    .data(bundle)
                                    .launch();
                            break;
                        case 3:
                            Router.newIntent(getActivity())
                                    .to(SettingZhouZhuanXinYongKaActivity.class)
                                    .launch();
                            break;
                        case 4:
                            Router.newIntent(getActivity())
                                    .to(CancellationAccountActivityZhouZhuanXinYongKa.class)
                                    .launch();
                            break;
                    }
                }
            });
            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            rvy.setHasFixedSize(true);
            rvy.setAdapter(mineZhouZhuanXinYongKaAdapter);
        }
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisZhouZhuanXinYongKa.getStringFromPref("API_BASE_URL"))) {
            ZhouZhuanXinYongKaApi.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<CompanyInfoZhouZhuanXinYongKaModel>>getApiTransformer())
                    .compose(XApi.<BaseRespModelZhouZhuanXinYongKa<CompanyInfoZhouZhuanXinYongKaModel>>getScheduler())
                    .compose(this.<BaseRespModelZhouZhuanXinYongKa<CompanyInfoZhouZhuanXinYongKaModel>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespModelZhouZhuanXinYongKa<CompanyInfoZhouZhuanXinYongKaModel>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespModelZhouZhuanXinYongKa<CompanyInfoZhouZhuanXinYongKaModel> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedPreferencesUtilisZhouZhuanXinYongKa.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (normalZhouZhuanXinYongKaDialog != null) {
            normalZhouZhuanXinYongKaDialog.dismiss();
            normalZhouZhuanXinYongKaDialog = null;
        }
        super.onDestroy();
    }
}
