package com.rihdkauecgh.plihgnytrvfws.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.base.SimpleRecAdapter;
import com.rihdkauecgh.plihgnytrvfws.imageloader.ILFactory;
import com.rihdkauecgh.plihgnytrvfws.imageloader.ILoader;
import com.rihdkauecgh.plihgnytrvfws.kit.KnifeKit;
import com.rihdkauecgh.plihgnytrvfws.model.GankResults;

import butterknife.BindView;

/**
 * Created by wanglei on 2016/12/10.
 */

public class GirlAdapter extends SimpleRecAdapter<GankResults.Item, GirlAdapter.ViewHolder> {


    public GirlAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_girl;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GankResults.Item item = data.get(position);

        //圆形
//        ILFactory.getLoader().loadCircle(item.getUrl(),holder.ivGirl, new ILoader.Options(R.mipmap.xdroid_logo_128, R.mipmap.xdroid_logo_128));
        //圆角
//        ILFactory.getLoader().loadCorner(item.getUrl(),holder.ivGirl, 50,new ILoader.Options(R.mipmap.xdroid_logo_128, R.mipmap.xdroid_logo_128));
        //正常
        ILFactory.getLoader().loadNet(holder.ivGirl, item.getUrl(), new ILoader.Options(R.mipmap.app_logo, R.mipmap.app_logo));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {

                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_girl)
        ImageView ivGirl;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
