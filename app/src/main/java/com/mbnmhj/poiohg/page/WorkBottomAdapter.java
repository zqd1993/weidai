package com.mbnmhj.poiohg.page;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mbnmhj.poiohg.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class WorkBottomAdapter extends BaseQuickAdapter<WorkActivity.TabModel, BaseViewHolder> {

    private ClickedListener clickedListener;

    public WorkBottomAdapter(int layoutResId, @Nullable List<WorkActivity.TabModel> data) {
        super(layoutResId, data);
    }

    /**
     * 获取html标签
     *
     * @param bodyHTML
     * @return
     */
    public static String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>*,body,html,div,p,img{border:0;margin:0;padding:0;} </style>" +
                "</head>";
        return "<html>" + head + "<body style=\"width:100%%;height: auto;word-wrap:break-word;\">" + bodyHTML + "</body></html>";
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, WorkActivity.TabModel item) {
        ImageView tabImg = helper.getView(R.id.tab_img);
        TextView tabName = helper.getView(R.id.tab_name);
        tabName.setText(item.getName());
        if (item.isChecked()){
            Glide.with(mContext).load(item.getSelectedIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            Glide.with(mContext).load(item.getIcon()).into(tabImg);
            tabName.setTextColor(mContext.getResources().getColor(R.color.color_bdbdc5));
        }
        helper.getView(R.id.click_view).setOnClickListener(v -> {
            if (!item.isChecked()){
                item.setChecked(true);
                for (int i = 0; i < getData().size(); i++){
                    if (i != helper.getLayoutPosition()){
                        getData().get(i).setChecked(false);
                    }
                }
                if (clickedListener != null){
                    clickedListener.onClick(helper.getLayoutPosition());
                }
                notifyDataSetChanged();
            }
        });
    }

    //
//    /**
//     * 设置img标签下的width为手机屏幕宽度，height自适应
//     *
//     * @param data html字符串
//     * @return 更新宽高属性后的html字符串
//     */
//    public static String getNewData(String data) {
//        Document document = Jsoup.parse(data);
//
//        Elements pElements = document.select("p:has(img)");
//        for (Element pElement : pElements) {
//            pElement.attr("style", "text-align:center");
//            pElement.attr("max-width", String.valueOf(ScreenUtils.getScreenWidth() + "px"))
//                    .attr("height", "auto");
//        }
//        Elements imgElements = document.select("img");
//        for (Element imgElement : imgElements) {
//            //重新设置宽高
//            imgElement.attr("max-width", "100%")
//                    .attr("height", "auto");
//            imgElement.attr("style", "max-width:100%;height:auto");
//        }
//        return document.toString();
//    }


    public void setClickedListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }

    public static boolean isValidStr(String str) {
//        boolean matches = str.matches("^[a-zA-Z0-9]{6,18}$");
        int length = str.length();
        if (5 < length && length < 19) {
            return true;
        } else {
            return false;
        }


    }


    public interface ClickedListener{
        void onClick(int position);
    }

    public static String stringAppend(List<String> list) {
        String appendStr = "";
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    appendStr = appendStr + list.get(i);
                } else {
                    appendStr = appendStr + list.get(i) + "，";
                }
            }
        }
        return appendStr;
    }

}
