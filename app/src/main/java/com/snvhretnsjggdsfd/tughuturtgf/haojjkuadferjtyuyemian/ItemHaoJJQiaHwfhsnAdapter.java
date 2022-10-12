package com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyuyemian;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.snvhretnsjggdsfd.tughuturtgf.R;
import com.snvhretnsjggdsfd.tughuturtgf.base.SimpleRecAdapter;
import com.snvhretnsjggdsfd.tughuturtgf.kit.KnifeKit;
import com.snvhretnsjggdsfd.tughuturtgf.haojjkuadferjtyushiti.HaoJJQiaHwfhsnItemModel;

import butterknife.BindView;

public class ItemHaoJJQiaHwfhsnAdapter extends SimpleRecAdapter<HaoJJQiaHwfhsnItemModel, ItemHaoJJQiaHwfhsnAdapter.ItemViewHolder> {

    public ItemHaoJJQiaHwfhsnAdapter(Context context) {
        super(context);
    }

    @Override
    public ItemViewHolder newViewHolder(View itemView) {
        return new ItemViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_item_layout_hao_jjqian_dfjr_uert_hw;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {
        HaoJJQiaHwfhsnItemModel model = data.get(position);
        itemViewHolder.item_name.setText(model.getName());
        itemViewHolder.amount_tv.setText(model.getAmount());
        if (model.isChecked()) {
            itemViewHolder.parent_fl.setBackgroundResource(R.drawable.item_bg_1);
            itemViewHolder.item_name.setTextColor(context.getResources().getColor(R.color.text_normal_color));
        } else {
            itemViewHolder.parent_fl.setBackgroundResource(R.drawable.item_bg);
            itemViewHolder.item_name.setTextColor(context.getResources().getColor(R.color.color_cd9a79));
        }
        if (model.isAomuntChecked()) {
            itemViewHolder.amount_tv.setTextColor(context.getResources().getColor(R.color.text_normal_color));
        } else {
            itemViewHolder.amount_tv.setTextColor(context.getResources().getColor(R.color.white));
        }
        itemViewHolder.parent_fl.setOnClickListener(v -> {
            if (!model.isChecked()) {
                model.setChecked(true);
                for (int i = 0; i < data.size(); i++) {
                    if (i != position) {
                        data.get(i).setChecked(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
        itemViewHolder.amount_tv.setOnClickListener(v -> {
            if (!model.isAomuntChecked()) {
                model.setAomuntChecked(true);
                for (int i = 0; i < data.size(); i++) {
                    if (i != position) {
                        data.get(i).setAomuntChecked(false);
                    }
                }
                getRecItemClick().onItemClick(position, model, 1, itemViewHolder);
                notifyDataSetChanged();
            }
        });
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.parent_fl)
        View parent_fl;
        @BindView(R.id.item_name)
        TextView item_name;
        @BindView(R.id.amount_tv)
        TextView amount_tv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}