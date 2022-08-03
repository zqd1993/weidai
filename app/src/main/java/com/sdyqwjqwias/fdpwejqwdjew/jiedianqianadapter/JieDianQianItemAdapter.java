package com.sdyqwjqwias.fdpwejqwdjew.jiedianqianadapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sdyqwjqwias.fdpwejqwdjew.R;
import com.sdyqwjqwias.fdpwejqwdjew.base.SimpleRecAdapter;
import com.sdyqwjqwias.fdpwejqwdjew.kit.KnifeKit;
import com.sdyqwjqwias.fdpwejqwdjew.jiedianqianmodel.ItemJieDianQianModel;

import butterknife.BindView;

public class JieDianQianItemAdapter extends SimpleRecAdapter<ItemJieDianQianModel, JieDianQianItemAdapter.ItemViewHolder> {

    public JieDianQianItemAdapter(Context context) {
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
        ItemJieDianQianModel model = data.get(position);
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
