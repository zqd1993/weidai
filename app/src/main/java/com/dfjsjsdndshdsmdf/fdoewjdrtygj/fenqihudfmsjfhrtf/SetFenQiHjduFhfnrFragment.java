package com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dfjsjsdndshdsmdf.fdoewjdrtygj.R;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta.AboutInfoFenQiHjduFhfnrActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta.FenQiHjduFhfnrDlActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta.FeedbackFenQiHjduFhfnrActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta.JumpH5FenQiHjduFhfnrActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta.SetItemFenQiHjduFhfnrAdapter;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta.ZhuXiaFenQiHjduFhfnrActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtapi.HttpFenQiHjduFhfnrApi;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.FenQiHjduFhfnrBaseModel;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.ConfigFenQiHjduFhfnrEntity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.ProductFenQiHjduFhfnrModel;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtm.FenQiHjduFhfnrSetModel;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.mvp.XActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.mvp.XFragment;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.net.ApiSubscriber;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.net.NetError;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.net.XApi;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.FenQiHjduFhfnrMyToast;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.OpeFenQiHjduFhfnrUti;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.PreferencFenQiHjduFhfnrOpenUtil;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtw.RemindDialogFenQiHjduFhfnr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SetFenQiHjduFhfnrFragment extends XFragment {

    @BindView(R.id.parent_view)
    View parentView;
    @BindView(R.id.user_phone_tv)
    TextView userPhoneTv;
    @BindView(R.id.set_list)
    RecyclerView setList;
    @BindView(R.id.set_list_1)
    RecyclerView setList1;

    private ProductFenQiHjduFhfnrModel productFenQiHjduFhfnrModel;

    private Bundle bundle, webBundle;

    private int mobileType;

    private String phone;

    private SetItemFenQiHjduFhfnrAdapter setItemFenQiHjduFhfnrAdapter, setItemFenQiHjduFhfnrAdapter1;

    private RemindDialogFenQiHjduFhfnr dialog;

    private String mailStr = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        mailStr = PreferencFenQiHjduFhfnrOpenUtil.getString("app_mail");
        phone = PreferencFenQiHjduFhfnrOpenUtil.getString("phone");
        userPhoneTv.setText(phone);
        productList();
        initSetAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fen_qas_han_jnfe_set;
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
        FenQiHjduFhfnrSetModel model = new FenQiHjduFhfnrSetModel(R.drawable.zxdgfbdxfyrty, "注册协议");
        FenQiHjduFhfnrSetModel model1 = new FenQiHjduFhfnrSetModel(R.drawable.wertgfghfgj, "隐私协议");
        FenQiHjduFhfnrSetModel model2 = new FenQiHjduFhfnrSetModel(R.drawable.mxdrghxfh, "意见反馈");
        FenQiHjduFhfnrSetModel model3 = new FenQiHjduFhfnrSetModel(R.drawable.wetgdxfhjh, "关于我们");
        FenQiHjduFhfnrSetModel model4 = new FenQiHjduFhfnrSetModel(R.drawable.kldtyhjxfghfg, "个性化推荐");
        FenQiHjduFhfnrSetModel model5 = new FenQiHjduFhfnrSetModel(R.drawable.werdfhfgj, "投诉邮箱");
        FenQiHjduFhfnrSetModel model6 = new FenQiHjduFhfnrSetModel(R.drawable.ldtyuhfghj, "注销账户");
        FenQiHjduFhfnrSetModel model7 = new FenQiHjduFhfnrSetModel(R.drawable.ldrthxfgh, "退出登录");
        List<FenQiHjduFhfnrSetModel> list = new ArrayList<>();
        List<FenQiHjduFhfnrSetModel> list1 = new ArrayList<>();
        list.add(model);
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list1.add(model4);
        list1.add(model5);
        list1.add(model6);
        list1.add(model7);
        setItemFenQiHjduFhfnrAdapter = new SetItemFenQiHjduFhfnrAdapter(R.layout.adpater_fen_qas_han_jnfe_set_item, list);
        setItemFenQiHjduFhfnrAdapter.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpFenQiHjduFhfnrApi.ZCXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.privacy_policy));
                    OpeFenQiHjduFhfnrUti.getValue((XActivity) getActivity(), JumpH5FenQiHjduFhfnrActivity.class, webBundle);
                    break;
                case 1:
                    webBundle = new Bundle();
                    webBundle.putString("url", HttpFenQiHjduFhfnrApi.YSXY);
                    webBundle.putString("biaoti", getResources().getString(R.string.user_service_agreement));
                    OpeFenQiHjduFhfnrUti.getValue((XActivity) getActivity(), JumpH5FenQiHjduFhfnrActivity.class, webBundle);
                    break;
                case 2:
                    OpeFenQiHjduFhfnrUti.getValue((XActivity) getActivity(), FeedbackFenQiHjduFhfnrActivity.class, null);
                    break;
                case 3:
                    OpeFenQiHjduFhfnrUti.getValue((XActivity) getActivity(), AboutInfoFenQiHjduFhfnrActivity.class, null);
                    break;
            }
        });
        setList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        setList.setAdapter(setItemFenQiHjduFhfnrAdapter);
        setItemFenQiHjduFhfnrAdapter1 = new SetItemFenQiHjduFhfnrAdapter(R.layout.adpater_fen_qas_han_jnf_set_item, list1);
        setItemFenQiHjduFhfnrAdapter1.setOnClickListener(position -> {
            switch (position) {
                case 0:
                    dialog = new RemindDialogFenQiHjduFhfnr(getActivity()).setCancelText("开启")
                            .setConfirmText("关闭").setTitle("温馨提示").setContent("关闭或开启推送");
                    dialog.setOnButtonClickListener(new RemindDialogFenQiHjduFhfnr.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            FenQiHjduFhfnrMyToast.showShort("关闭成功");
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancelClicked() {
                            FenQiHjduFhfnrMyToast.showShort("开启成功");
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case 1:
                    getConfig();
                    break;
                case 2:
                    OpeFenQiHjduFhfnrUti.getValue((XActivity) getActivity(), ZhuXiaFenQiHjduFhfnrActivity.class, null);
                    break;
                case 3:
                    dialog = new RemindDialogFenQiHjduFhfnr(getActivity()).setCancelText("取消")
                            .setConfirmText("退出").setTitle("温馨提示").setContent("确定退出当前登录");
                    dialog.setOnButtonClickListener(new RemindDialogFenQiHjduFhfnr.OnButtonClickListener() {
                        @Override
                        public void onSureClicked() {
                            dialog.dismiss();
                            PreferencFenQiHjduFhfnrOpenUtil.saveString("phone", "");
                            OpeFenQiHjduFhfnrUti.getValue((XActivity) getActivity(), FenQiHjduFhfnrDlActivity.class, null, true);
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
        setList1.setAdapter(setItemFenQiHjduFhfnrAdapter1);
    }

    public void getConfig() {
        HttpFenQiHjduFhfnrApi.getInterfaceUtils().getConfig()
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<FenQiHjduFhfnrBaseModel<ConfigFenQiHjduFhfnrEntity>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(FenQiHjduFhfnrBaseModel<ConfigFenQiHjduFhfnrEntity> configEntity) {
                        if (configEntity != null) {
                            if (configEntity.getData() != null) {
                                mailStr = configEntity.getData().getAppMail();
                                PreferencFenQiHjduFhfnrOpenUtil.saveString("app_mail", mailStr);
                                dialog = new RemindDialogFenQiHjduFhfnr(getActivity()).setTitle("温馨提示").setContent(mailStr).showOnlyBtn();
                                dialog.show();
                            }
                        }
                    }
                });
    }

    public void toWeb(ProductFenQiHjduFhfnrModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            OpeFenQiHjduFhfnrUti.getValue((XActivity) getActivity(), JumpH5FenQiHjduFhfnrActivity.class, bundle);
        }
    }

    public void productList() {
        mobileType = PreferencFenQiHjduFhfnrOpenUtil.getInt("mobileType");
        phone = PreferencFenQiHjduFhfnrOpenUtil.getString("phone");
        HttpFenQiHjduFhfnrApi.getInterfaceUtils().productList(mobileType, phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<FenQiHjduFhfnrBaseModel<List<ProductFenQiHjduFhfnrModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        OpeFenQiHjduFhfnrUti.showErrorInfo(getActivity(), error);
                    }

                    @Override
                    public void onNext(FenQiHjduFhfnrBaseModel<List<ProductFenQiHjduFhfnrModel>> fenQiHjduFhfnrBaseModel) {
                        if (fenQiHjduFhfnrBaseModel != null) {
                            if (fenQiHjduFhfnrBaseModel.getCode() == 200 && fenQiHjduFhfnrBaseModel.getData() != null) {
                                if (fenQiHjduFhfnrBaseModel.getData() != null && fenQiHjduFhfnrBaseModel.getData().size() > 0) {
                                    productFenQiHjduFhfnrModel = fenQiHjduFhfnrBaseModel.getData().get(0);
                                }
                            }
                        }
                    }
                });
    }

    public void productClick(ProductFenQiHjduFhfnrModel model) {
        if (model == null) {
            return;
        }
        phone = PreferencFenQiHjduFhfnrOpenUtil.getString("phone");
        HttpFenQiHjduFhfnrApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<FenQiHjduFhfnrBaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(FenQiHjduFhfnrBaseModel fenQiHjduFhfnrBaseModel) {
                        toWeb(model);
                    }
                });
    }
}
