package com.asdfgaery.nnaeryaery.test;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.asdfgaery.nnaeryaery.imageloader.ILFactory;
import com.asdfgaery.nnaeryaery.imageloader.ILoader;
import com.asdfgaery.nnaeryaery.imageloader.LoadCallback;
import com.asdfgaery.nnaeryaery.mvp.XActivity;

import java.io.File;

/**
 * Created by wanglei on 2017/1/30.
 */

public class ImageLoaderTest extends XActivity {

    @Override
    public void initData(Bundle savedInstanceState) {
        ImageView imageView = null;
        String assetPath = "";
        String filePath = "";
        String urlPath = "";
        int resIds = 0;

        ILFactory.getLoader().init(context);
        ILFactory.getLoader().loadAssets(imageView, assetPath, null);
        ILFactory.getLoader().loadFile(imageView, new File(filePath), null);
        ILFactory.getLoader().loadNet(imageView, urlPath, null);
        ILFactory.getLoader().loadNet(context, urlPath, null, new LoadCallback() {
            @Override
            public void onLoadReady(Drawable drawable) {

            }
        });
        ILFactory.getLoader().loadResource(imageView, resIds, null);
        ILFactory.getLoader().clearMemoryCache(context);
        ILFactory.getLoader().resume(context);
        ILFactory.getLoader().pause(context);

        new Thread() {
            @Override
            public void run() {
                super.run();
                ILFactory.getLoader().clearDiskCache(context);
            }
        }.start();

        int loadingResId = -1;
        int loadErrorResId = -1;
        ILFactory.getLoader().loadNet(imageView, urlPath,
                new ILoader.Options(loadingResId, loadErrorResId).scaleType(ImageView.ScaleType.FIT_CENTER));

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public Object newP() {
        return null;
    }
}
