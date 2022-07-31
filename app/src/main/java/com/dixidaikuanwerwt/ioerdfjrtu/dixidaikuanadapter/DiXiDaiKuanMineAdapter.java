package com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dixidaikuanwerwt.ioerdfjrtu.R;
import com.dixidaikuanwerwt.ioerdfjrtu.base.SimpleRecAdapter;
import com.dixidaikuanwerwt.ioerdfjrtu.kit.KnifeKit;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanmodel.MineItemModelDiXiDaiKuan;
import com.dixidaikuanwerwt.ioerdfjrtu.dixidaikuanutils.SharedDiXiDaiKuanPreferencesUtilis;

import butterknife.BindView;

public class DiXiDaiKuanMineAdapter extends SimpleRecAdapter<MineItemModelDiXiDaiKuan, DiXiDaiKuanMineAdapter.ViewHolder> {

    public DiXiDaiKuanMineAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_di_xi_dai_kuan_mine_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        if (i == 0){
            viewHolder.mail_tv.setVisibility(View.VISIBLE);
            viewHolder.rightImg.setVisibility(View.GONE);
            viewHolder.mail_tv.setText(SharedDiXiDaiKuanPreferencesUtilis.getStringFromPref("APP_MAIL"));
        } else {
            viewHolder.mail_tv.setVisibility(View.GONE);
            viewHolder.rightImg.setVisibility(View.VISIBLE);
        }
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
        @BindView(R.id.line)
        View line;
        @BindView(R.id.mail_tv)
        TextView mail_tv;
        @BindView(R.id.right_img)
        View rightImg;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
