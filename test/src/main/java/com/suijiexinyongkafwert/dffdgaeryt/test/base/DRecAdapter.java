package com.suijiexinyongkafwert.dffdgaeryt.test.base;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suijiexinyongkafwert.dffdgaeryt.kit.KnifeKit;

import cn.droidlover.get.test.R;
import cn.droidlover.xrecyclerview.RecyclerAdapter;

/**
 * Created by wanglei on 2017/1/30.
 */

public class DRecAdapter extends RecyclerAdapter<String, DRecAdapter.ViewHolder> {


    public DRecAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
