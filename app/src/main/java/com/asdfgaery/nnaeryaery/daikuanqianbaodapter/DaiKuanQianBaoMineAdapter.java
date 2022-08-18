package com.asdfgaery.nnaeryaery.daikuanqianbaodapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.asdfgaery.nnaeryaery.R;
import com.asdfgaery.nnaeryaery.base.SimpleRecAdapter;
import com.asdfgaery.nnaeryaery.kit.KnifeKit;
import com.asdfgaery.nnaeryaery.daikuanqianbaomodel.MineItemModelDaiKuanQianBao;
import com.asdfgaery.nnaeryaery.daikuanqianbaoutils.SharedDaiKuanQianBaoPreferencesUtilis;

import butterknife.BindView;

public class DaiKuanQianBaoMineAdapter extends SimpleRecAdapter<MineItemModelDaiKuanQianBao, DaiKuanQianBaoMineAdapter.ViewHolder> {

    public DaiKuanQianBaoMineAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_dai_kuan_qian_bao_mine_item;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemImg.setImageResource(data.get(i).getImgRes());
        viewHolder.tvItem.setText(data.get(i).getItemTv());
        if (i == 0){
            viewHolder.mail_tv.setVisibility(View.VISIBLE);
            viewHolder.rightImg.setVisibility(View.GONE);
            viewHolder.mail_tv.setText(SharedDaiKuanQianBaoPreferencesUtilis.getStringFromPref("APP_MAIL"));
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
