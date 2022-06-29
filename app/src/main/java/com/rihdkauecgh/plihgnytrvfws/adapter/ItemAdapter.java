package com.rihdkauecgh.plihgnytrvfws.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rihdkauecgh.plihgnytrvfws.R;
import com.rihdkauecgh.plihgnytrvfws.base.SimpleRecAdapter;
import com.rihdkauecgh.plihgnytrvfws.kit.KnifeKit;
import com.rihdkauecgh.plihgnytrvfws.model.ItemModel;

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
        if (model.isChecked()) {
            itemViewHolder.parent_fl.setBackgroundResource(R.drawable.item_bg);
            itemViewHolder.item_name.setTextColor(context.getResources().getColor(R.color.color_fd1e57));
        } else {
            itemViewHolder.parent_fl.setBackgroundResource(R.drawable.item_bg_1);
            itemViewHolder.item_name.setTextColor(context.getResources().getColor(R.color.text_normal_color));
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
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.parent_fl)
        View parent_fl;
        @BindView(R.id.item_name)
        TextView item_name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
