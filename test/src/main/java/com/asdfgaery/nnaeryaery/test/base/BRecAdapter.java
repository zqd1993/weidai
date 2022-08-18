package com.asdfgaery.nnaeryaery.test.base;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.asdfgaery.nnaeryaery.base.SimpleRecAdapter;
import com.asdfgaery.nnaeryaery.kit.KnifeKit;

import cn.droidlover.get.test.R;

/**
 * Created by wanglei on 2017/1/30.
 */

public class BRecAdapter extends SimpleRecAdapter<String, BRecAdapter.ViewHolder> {

    public BRecAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_single;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, data.get(position), 0, holder);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
