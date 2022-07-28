package com.wolai.dai.yemian;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wolai.dai.R;
import com.wolai.dai.base.SimpleRecAdapter;
import com.wolai.dai.kit.KnifeKit;
import com.wolai.dai.shiti.ItemModel;

import butterknife.BindView;

public class ItemAdapter extends SimpleRecAdapter<ItemModel, ItemAdapter.ItemViewHolder> {

    public ItemAdapter(Context context) {
        super(context);
    }

    @Override
    public ItemViewHolder newViewHolder(View itemView) {
        return new ItemViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_item_layout;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {
        ItemModel model = data.get(position);
        itemViewHolder.item_name.setText(model.getName());
        itemViewHolder.amount_tv.setText(model.getAmount());
        if (model.isChecked()) {
            itemViewHolder.parent_fl.setBackgroundResource(R.drawable.item_bg_1);
            itemViewHolder.item_name.setTextColor(context.getResources().getColor(R.color.text_normal_color));
        } else {
            itemViewHolder.parent_fl.setBackgroundResource(R.drawable.item_bg);
            itemViewHolder.item_name.setTextColor(context.getResources().getColor(R.color.white));
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
