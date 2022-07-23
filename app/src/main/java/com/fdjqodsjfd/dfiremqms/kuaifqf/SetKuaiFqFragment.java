package com.fdjqodsjfd.dfiremqms.kuaifqf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fdjqodsjfd.dfiremqms.R;
import com.fdjqodsjfd.dfiremqms.kuaifqa.AboutInfoActivityKuaiFq;
import com.fdjqodsjfd.dfiremqms.kuaifqa.KuaiFqDlActivity;
import com.fdjqodsjfd.dfiremqms.kuaifqa.FeedbackKuaiFqiActivity;
import com.fdjqodsjfd.dfiremqms.kuaifqa.KuaiFqJumpH5Activity;
import com.fdjqodsjfd.dfiremqms.kuaifqa.SetItemKuaiFqAdapter;
import com.fdjqodsjfd.dfiremqms.kuaifqa.ZhuXiaKuaiFqActivity;
import com.fdjqodsjfd.dfiremqms.kuaifqapi.HttpApiKuaiFq;
import com.fdjqodsjfd.dfiremqms.kuaifqm.KuaiFqBaseModel;
import com.fdjqodsjfd.dfiremqms.kuaifqm.ConfigEntitKuaiFqy;
import com.fdjqodsjfd.dfiremqms.kuaifqm.ProductKuaiFqModel;
import com.fdjqodsjfd.dfiremqms.kuaifqm.KuaiFqSetModel;
import com.fdjqodsjfd.dfiremqms.mvp.XActivity;
import com.fdjqodsjfd.dfiremqms.mvp.XFragment;
import com.fdjqodsjfd.dfiremqms.net.ApiSubscriber;
import com.fdjqodsjfd.dfiremqms.net.NetError;
import com.fdjqodsjfd.dfiremqms.net.XApi;
import com.fdjqodsjfd.dfiremqms.kuaifqu.KuaiFqMyToast;
import com.fdjqodsjfd.dfiremqms.kuaifqu.OpeKuaiFqnUti;
import com.fdjqodsjfd.dfiremqms.kuaifqu.PreferencKuaiFqOpenUtil;
import com.fdjqodsjfd.dfiremqms.kuaifqw.RemindDialogKuaiFq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetKuaiFqFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.set_list_1)
    RecyclerView setList1;

    private ProductKuaiFqModel productKuaiFqModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemKuaiFqAdapter setItemKuaiFqAdapter, setItemKuaiFqAdapter1;

    private RemindDialogKuaiFq dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencKuaiFqOpenUtil.getString("app_mail");
        phone = PreferencKuaiFqOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kuai_fq_set;
    }

    @Override
    public Object newP() {
        return null;
    }

    public float getRatio(String imageFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, options);
        options.inJustDecodeBounds = false;
        float oW = options.outWidth;
        float oH = options.outHeight;
        if (oH == 0) {
            return 1;
        }
        return oW / oH;
    }

    public Bitmap loadFile2MemoryVersion10(Context context, Uri uri, int w, int h) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        Bitmap bitmap = null;
        try {
            if (w == 0) {
                w = 200;
            }
            if (h == 0) {
                h = 200;
            }
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), new Rect(0, 0, w, h), options);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

    private void initSetAdapter() {
        KuaiFqSetModel model = new KuaiFqSetModel(R.drawable.zxdgfbdxfyrty, "注册协议");
        KuaiFqSetModel model1 = new KuaiFqSetModel(R.drawable.wertgfghfgj, "隐私协议");
        KuaiFqSetModel model2 = new KuaiFqSetModel(R.drawable.mxdrghxfh, "意见反馈");
        KuaiFqSetModel model3 = new KuaiFqSetModel(R.drawable.wetgdxfhjh, "关于我们");
        KuaiFqSetModel model4 = new KuaiFqSetModel(R.drawable.kldtyhjxfghfg, "个性化推荐");
        KuaiFqSetModel model5 = new KuaiFqSetModel(R.drawable.werdfhfgj, "投诉邮箱");
        KuaiFqSetModel model6 = new KuaiFqSetModel(R.drawable.ldtyuhfghj, "注销账户");
        KuaiFqSetModel model7 = new KuaiFqSetModel(R.drawable.ldrthxfgh, "退出登录");
        List<KuaiFqSetModel> list = new ArrayList<>();
        List<KuaiFqSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list1.add(model4);
        list1.add(model5);
        list1.add(model6);
        list1.add(model7);
        setItemKuaiFqAdapter = new SetItemKuaiFqAdapter(R.layout.adpater_kuai_fqset_item, list);
        setItemKuaiFqAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    if (!TextUtils.isEmpty(PreferencKuaiFqOpenUtil.getString("AGREEMENT"))) {
                        webBundle = new Bundle();
                        webBundle.putString("url", PreferencKuaiFqOpenUtil.getString("AGREEMENT") + HttpApiKuaiFq.ZCXY);
                        webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                        OpeKuaiFqnUti.getValue((XActivity) getActivity(), KuaiFqJumpH5Activity.class, webBundle);
                    }
                    break;
                case 1:
                    if (!TextUtils.isEmpty(PreferencKuaiFqOpenUtil.getString("AGREEMENT"))) {
                        webBundle = new Bundle();
                        webBundle.putString("url", PreferencKuaiFqOpenUtil.getString("AGREEMENT") + HttpApiKuaiFq.YSXY);
                        webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                        OpeKuaiFqnUti.getValue((XActivity) getActivity(), KuaiFqJumpH5Activity.class, webBundle);
                    }
                    break;
                case 2:
                    OpeKuaiFqnUti.getValue((XActivity) getActivity(), FeedbackKuaiFqiActivity.class, null);
                    break;
                case 3:
                    OpeKuaiFqnUti.getValue((XActivity) getActivity(), AboutInfoActivityKuaiFq.class, null);
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        setList.setAdapter(setItemKuaiFqAdapter);
        setItemKuaiFqAdapter1 = new SetItemKuaiFqAdapter(R.layout.adpater_kuaifq_set_item, list1);
        setItemKuaiFqAdapter1.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    dialog = new RemindDialogKuaiFq(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialogKuaiFq.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            KuaiFqMyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            KuaiFqMyToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 1:
                    getConfig();
                    break;
                case 2:
                    OpeKuaiFqnUti.getValue((XActivity) getActivity(), ZhuXiaKuaiFqActivity.class, null);
                    break;
                case 3:
                    dialog = new RemindDialogKuaiFq(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindDialogKuaiFq.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencKuaiFqOpenUtil.saveString("phone", "");
                            OpeKuaiFqnUti.getValue((XActivity) getActivity(), KuaiFqDlActivity.class, null, true);
                        }

                        @Override
                        public void onCancelClicked() {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
            }
        });
        setList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        setList1.setAdapter(setItemKuaiFqAdapter1);
    }

    public void getConfig() {
        if (!TextUtils.isEmpty(PreferencKuaiFqOpenUtil.getString("HTTP_API_URL"))) {
            HttpApiKuaiFq.getInterfaceUtils().getConfig()
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(this.bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiFqBaseModel<ConfigEntitKuaiFqy>>() {
                        @Override
                        protected void onFail(NetError error) {

                        }

                        @Override
                        public void onNext(KuaiFqBaseModel<ConfigEntitKuaiFqy> configEntity) {
                            if (configEntity != null) {
                                if (configEntity.getData() != null) {
                                    mailStr = configEntity.getData().getAppMail();
                                    PreferencKuaiFqOpenUtil.saveString("app_mail", mailStr);
                                    dialog = new RemindDialogKuaiFq(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                    dialog.show();
                                }
                            }
                        }
                    });
        }
    }

    public void toWeb(ProductKuaiFqModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpeKuaiFqnUti.getValue((XActivity) getActivity(), KuaiFqJumpH5Activity.class, bundle);
        }
    }

    public void productList() {
        if (!TextUtils.isEmpty(PreferencKuaiFqOpenUtil.getString("HTTP_API_URL"))) {
            mobileType = PreferencKuaiFqOpenUtil.getInt("mobileType");
            phone = PreferencKuaiFqOpenUtil.getString("phone");
            HttpApiKuaiFq.getInterfaceUtils().productList(mobileType, phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiFqBaseModel<List<ProductKuaiFqModel>>>() {
                        @Override
                        protected void onFail(NetError error) {
                            OpeKuaiFqnUti.showErrorInfo(getActivity(), error);
                        }

                        @Override
                        public void onNext(KuaiFqBaseModel<List<ProductKuaiFqModel>> kuaiFqBaseModel) {
                            if (kuaiFqBaseModel != null) {
                                if (kuaiFqBaseModel.getCode() == 200 && kuaiFqBaseModel.getData() != null) {
                                    if (kuaiFqBaseModel.getData() != null && kuaiFqBaseModel.getData().size() > 0) {
                                        productKuaiFqModel = kuaiFqBaseModel.getData().get(0);
                                    }
                                }
                            }
                        }
                    });
        }
    }

    public void productClick(ProductKuaiFqModel model) {
        if (!TextUtils.isEmpty(PreferencKuaiFqOpenUtil.getString("HTTP_API_URL"))) {
            if (model == null) {
                return;
            }
            phone = PreferencKuaiFqOpenUtil.getString("phone");
            HttpApiKuaiFq.getInterfaceUtils().productClick(model.getId(), phone)
                    .compose(XApi.getApiTransformer())
                    .compose(XApi.getScheduler())
                    .compose(bindToLifecycle())
                    .subscribe(new ApiSubscriber<KuaiFqBaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            toWeb(model);
                        }

                        @Override
                        public void onNext(KuaiFqBaseModel kuaiFqBaseModel) {
                            toWeb(model);
                        }
                    });
        }
    }
}
