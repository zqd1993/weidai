package com.jiekuanzhijiasdoert.fghirtidfks.adapterjiekuanzhijia;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jiekuanzhijiasdoert.fghirtidfks.R;
import com.jiekuanzhijiasdoert.fghirtidfks.base.SimpleRecAdapter;
import com.jiekuanzhijiasdoert.fghirtidfks.kit.KnifeKit;
import com.jiekuanzhijiasdoert.fghirtidfks.modeljiekuanzhijia.MineItemModelJieKuanZhiJia;

import butterknife.BindView;

public class JieKuanZhiJiaMineAdapter extends SimpleRecAdapter<MineItemModelJieKuanZhiJia, JieKuanZhiJiaMineAdapter.ViewHolder> {

    public JieKuanZhiJiaMineAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_item_jie_kuan_zhi_jia;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        viewHolder.parentLl.setOnClickListener(v -> {
            getRecItemClick().onItemClick(i, data.get(i), 1, viewHolder);
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_title_tv)
        TextView tvItem;
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.parent_ll)
        View parentLl;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
