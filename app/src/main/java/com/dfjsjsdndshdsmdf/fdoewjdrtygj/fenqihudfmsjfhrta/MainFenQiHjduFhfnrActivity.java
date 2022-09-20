package com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrta;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dfjsjsdndshdsmdf.fdoewjdrtygj.R;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtf.MainFenQiHjduFhfnrFragment;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtf.ProductFenQiHjduFhfnrFragmen;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtf.SetFenQiHjduFhfnrFragment;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.mvp.XActivity;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.FenQiHjduFhfnrMyToast;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.PreferencFenQiHjduFhfnrOpenUtil;
import com.dfjsjsdndshdsmdf.fdoewjdrtygj.fenqihudfmsjfhrtu.FenQiHjduFhfnrStatusBarUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFenQiHjduFhfnrActivity extends XActivity {

    @BindView(R.id.main_view_pager)
    ViewPager2 mainViewPager;
    @BindView(R.id.bottom_rvy)
    RecyclerView bottomRvy;

    private List<TabModel> tabModels;

    private TabFenQiHjduFhfnrAdapter tabFenQiHjduFhfnrAdapter;

    private List<Fragment> fragments;

    private long exitTime = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        FenQiHjduFhfnrStatusBarUtil.setTransparent(this, false);
        FenQiHjduFhfnrStatusBarUtil.setLightMode(this);
        if (PreferencFenQiHjduFhfnrOpenUtil.getBool("NO_RECORD")) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        tabModels = new ArrayList<>();
        fragments = new ArrayList<>();
        TabModel tabModel = new TabModel();
        tabModel.setIcon(R.drawable.mxdfyhfgn);
        tabModel.setSelectedIcon(R.drawable.sertdfghfthj);
        tabModel.setName("首页");
        tabModel.setChecked(true);
        TabModel tabModel1 = new TabModel();
        tabModel1.setIcon(R.drawable.wtfdhxfjh);
        tabModel1.setSelectedIcon(R.drawable.kdtyhjxfghfgh);
        tabModel1.setName("精选");
        tabModel1.setChecked(false);
        TabModel tabModel2 = new TabModel();
        tabModel2.setIcon(R.drawable.ertgxfghfgj);
        tabModel2.setSelectedIcon(R.drawable.klthxfgh);
        tabModel2.setName("我的");
        tabModel2.setChecked(false);
        tabModels.add(tabModel);
        tabModels.add(tabModel1);
        tabModels.add(tabModel2);
        initAdapter();
        fragments.add(new MainFenQiHjduFhfnrFragment());
        fragments.add(new ProductFenQiHjduFhfnrFragmen());
        fragments.add(new SetFenQiHjduFhfnrFragment());
        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(new FenQiHjduFhfnrFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        mainViewPager.setCurrentItem(0);
    }

    public void jumpMine(){
        tabFenQiHjduFhfnrAdapter.getData().get(0).setChecked(false);
        tabFenQiHjduFhfnrAdapter.getData().get(1).setChecked(true);
        mainViewPager.setCurrentItem(1, false);
        tabFenQiHjduFhfnrAdapter.notifyDataSetChanged();
    }

    private void initAdapter(){
        if (tabFenQiHjduFhfnrAdapter == null){
            tabFenQiHjduFhfnrAdapter = new TabFenQiHjduFhfnrAdapter(R.layout.adapter_fen_qas_han_jnfe_tab, tabModels);
            tabFenQiHjduFhfnrAdapter.setHasStableIds(true);
            tabFenQiHjduFhfnrAdapter.setClickedListener(position -> {
                mainViewPager.setCurrentItem(position, false);
            });
            bottomRvy.setHasFixedSize(true);
            bottomRvy.setLayoutManager(new GridLayoutManager(this, 3));
            bottomRvy.setAdapter(tabFenQiHjduFhfnrAdapter);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_fen_qas_han_jnfe;
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

    public class TabModel{

        private int icon;

        private int selectedIcon;

        private String name;

        private boolean isChecked;

        public int getSelectedIcon() {
            return selectedIcon;
        }

        public void setSelectedIcon(int selectedIcon) {
            this.selectedIcon = selectedIcon;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                FenQiHjduFhfnrMyToast.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public float werfdrfgh(String imageFile) {
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

    public Bitmap bdftyhsfgh(Context context, Uri uri, int w, int h) {
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

}
