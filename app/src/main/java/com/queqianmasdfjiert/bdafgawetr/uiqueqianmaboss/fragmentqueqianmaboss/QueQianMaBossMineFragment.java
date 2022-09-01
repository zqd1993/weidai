package com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.fragmentqueqianmaboss;

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

import com.queqianmasdfjiert.bdafgawetr.R;
import com.queqianmasdfjiert.bdafgawetr.adapterqueqianmaboss.MineQueQianMaBossAdapter;
import com.queqianmasdfjiert.bdafgawetr.adapterqueqianmaboss.MineAdapterQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.ApiQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.QueQianMaBossWebViewActivity;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.activityqueqianmaboss.AboutUsQueQianMaBossActivity;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.activityqueqianmaboss.CancellationAccountActivityQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.BaseRespQueQianMaBossModel;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.CompanyInfoModelQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.modelqueqianmaboss.MineItemModelQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.ApiSubscriber;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.NetError;
import com.queqianmasdfjiert.bdafgawetr.netqueqianmaboss.XApi;
import com.queqianmasdfjiert.bdafgawetr.uiqueqianmaboss.activityqueqianmaboss.QueQianMaBossSettingActivity;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.SharedPreferencesUtilisQueQianMaBoss;
import com.queqianmasdfjiert.bdafgawetr.utilsqueqianmaboss.ToastQueQianMaBossUtil;
import com.queqianmasdfjiert.bdafgawetr.widgetqueqianmaboss.NormalQueQianMaBossDialog;
import com.queqianmasdfjiert.bdafgawetr.router.Router;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.queqianmasdfjiert.bdafgawetr.mvp.XFragment;

import cn.droidlover.xrecyclerview.RecyclerItemCallback;

public class QueQianMaBossMineFragment extends XFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mail_tv)
    TextView mail_tv;
    @BindView(R.id.mail_sl)
    View mail_sl;
    @BindView(R.id.set_tv)
    View set_tv;
    @BindView(R.id.zhuxiao_tv)
    View zhuxiao_tv;

    private MineQueQianMaBossAdapter mineQueQianMaBossAdapter;
    private MineAdapterQueQianMaBoss mineAdapterQueQianMaBoss;
    private List<MineItemModelQueQianMaBoss> list, list1;
    private int[] imgRes = {R.drawable.wdbrstfu, R.drawable.bdfhaery, R.drawable.lpyukjdfg,};
    private String[] tvRes = {"注册协议", "隐私协议", "关于我们"};
    private Bundle bundle;
    private NormalQueQianMaBossDialog normalQueQianMaBossDialog;
    private String mailStr = "", phone = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        phone = SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        for (int i = 0; i < 3; i++) {
            MineItemModelQueQianMaBoss model = new MineItemModelQueQianMaBoss();
            model.setImgRes(imgRes[i]);
            model.setItemTv(tvRes[i]);
            list.add(model);
        }
        initAdapter();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getCompanyInfo();
        });
        mail_sl.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(null, mailStr);
            clipboard.setPrimaryClip(clipData);
            ToastQueQianMaBossUtil.showShort("复制成功");
        });
        set_tv.setOnClickListener(v -> {
            Router.newIntent(getActivity())
                    .to(QueQianMaBossSettingActivity.class)
                    .launch();
        });
        zhuxiao_tv.setOnClickListener(v -> {
            Router.newIntent(getActivity())
                    .to(CancellationAccountActivityQueQianMaBoss.class)
                    .launch();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getCompanyInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_que_qian_ma_boss_mine;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void initAdapter() {
        mineAdapterQueQianMaBoss = new MineAdapterQueQianMaBoss(getActivity());
        mineAdapterQueQianMaBoss.setData(list);
        mineAdapterQueQianMaBoss.setHasStableIds(true);
        mineAdapterQueQianMaBoss.setRecItemClick(new RecyclerItemCallback<MineItemModelQueQianMaBoss, MineAdapterQueQianMaBoss.ViewHolder>() {
            @Override
            public void onItemClick(int position, MineItemModelQueQianMaBoss model, int tag, MineAdapterQueQianMaBoss.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                switch (position) {
                    case 0:
                        bundle = new Bundle();
                        bundle.putInt("tag", 1);
                        bundle.putString("url", ApiQueQianMaBoss.getZc());
                        Router.newIntent(getActivity())
                                .to(QueQianMaBossWebViewActivity.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putInt("tag", 2);
                        bundle.putString("url", ApiQueQianMaBoss.getYs());
                        Router.newIntent(getActivity())
                                .to(QueQianMaBossWebViewActivity.class)
                                .data(bundle)
                                .launch();
                        break;
                    case 2:
                        Router.newIntent(getActivity())
                                .to(AboutUsQueQianMaBossActivity.class)
                                .launch();
                        break;
                }
            }
        });
        rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvy.setHasFixedSize(true);
        rvy.setAdapter(mineAdapterQueQianMaBoss);
    }

    public void getCompanyInfo() {
        if (!TextUtils.isEmpty(SharedPreferencesUtilisQueQianMaBoss.getStringFromPref("API_BASE_URL"))) {
            ApiQueQianMaBoss.getGankService().getCompanyInfo()
                    .compose(XApi.<BaseRespQueQianMaBossModel<CompanyInfoModelQueQianMaBoss>>getApiTransformer())
                    .compose(XApi.<BaseRespQueQianMaBossModel<CompanyInfoModelQueQianMaBoss>>getScheduler())
                    .compose(this.<BaseRespQueQianMaBossModel<CompanyInfoModelQueQianMaBoss>>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseRespQueQianMaBossModel<CompanyInfoModelQueQianMaBoss>>() {
                        @Override
                        protected void onFail(NetError error) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onNext(BaseRespQueQianMaBossModel<CompanyInfoModelQueQianMaBoss> loginStatusModel) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (loginStatusModel != null) {
                                if (loginStatusModel.getData() != null) {
                                    mailStr = loginStatusModel.getData().getGsmail();
                                    mail_tv.setText(mailStr);
                                    SharedPreferencesUtilisQueQianMaBoss.saveStringIntoPref("APP_MAIL", mailStr);
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        if (normalQueQianMaBossDialog != null) {
            normalQueQianMaBossDialog.dismiss();
            normalQueQianMaBossDialog = null;
        }
        super.onDestroy();
    }
}
