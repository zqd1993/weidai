package com.mbnmhj.poiohg.page;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbnmhj.poiohg.R;
import com.mbnmhj.poiohg.util.SBarUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UsActivity extends AppCompatActivity {

    /**
     * 建行POST
     * 回掉数据排序
     *
     * @param params
     * @return
     */
    public static String mapSort(Map<String, String> params) {

        params.remove("sign");

        List<String> keys = new ArrayList(params.keySet());

        Collections.sort(keys);
        StringBuffer content = new StringBuffer();

        for (int i = 0; i < keys.size(); ++i) {
            String key = keys.get(i);
            String value = params.get(key).toString();
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_us);
        ImageView backImg = findViewById(R.id.back_image);
        backImg.setOnClickListener(v -> finish());
        TextView textView = findViewById(R.id.biaoti_tv);
        textView.setText("关于我们");
    }

    /**
     * @param sourceDate 建行支付，拼成12位，单位是分
     * @return
     */
    public static String frontCompWithZore(int sourceDate) {
        return String.format("%012d", sourceDate);
    }

    public static void main(String[] args) {
        /*String JSON = "{\"amt\":\"000000000001\",\"batchNo\":\"000015\",\"cardIputMethod\":\"99\",\"cardNo\":\"58406012004700058kefx\",\"merchantID\":\"105584060120047\",\"merchantName\":\"中国建设银行股份有限公司深圳市分行\",\"operID\":\"01\",\"orgAmt\":\"000000000001\",\"payChannel\":\"CHANNEL_WEPAY\",\"refNo\":\"823214673732\",\"referInfo\":\"823214673732\",\"resCode\":\"00\",\"resMsg\":\"消费成功\",\"terminalID\":\"00066523\",\"traceNo\":\"000063\",\"transDate\":\"0820\",\"transName\":\"微信支付\",\"transTime\":\"141535\",\"wxAliPayOrderNo\":\"58406012004700058kefx\"}";
        // HashMap<String, String> orderMap = toLinkedHashMap(JSON);
        Map<String,String> orderMap = (Map<String,String>) com.alibaba.fastjson.JSON.parse(JSON);
        System.out.println(orderMap);*/


        String value = "12.34";
        String value1 = "12";
        String value2 = "1.00";

        Number num = Double.parseDouble(value) * 100;
        Number num1 = Double.parseDouble(value1) * 100;
        Number num2 = Double.parseDouble(value2) * 100;
        System.out.println(num.intValue());
        System.out.println(num1.intValue());
        System.out.println(num2.intValue());
    }

}
