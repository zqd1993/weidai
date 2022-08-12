package com.jiejijiekuansdafjer.zdfgbaeryazer.test.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jiejijiekuansdafjer.zdfgbaeryazer.base.XListAdapter;
import com.jiejijiekuansdafjer.zdfgbaeryazer.kit.KnifeKit;

import cn.droidlover.get.test.R;

/**
 * Created by wanglei on 2017/1/30.
 */

public class CListAdapter extends XListAdapter<String> {

    public CListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = data.get(position);
        CListAdapter.ViewHolder holder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_single, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public static class ViewHolder {

        public ViewHolder(View convertView) {
            KnifeKit.bind(this, convertView);
        }
    }
}
