package com.mbnmhj.poiohg.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mbnmhj.poiohg.R;
import com.mbnmhj.poiohg.page.BannerGoodsAdapter;
import com.mbnmhj.poiohg.page.NetPageActivity;
import com.mbnmhj.poiohg.net.NetApi;
import com.mbnmhj.poiohg.entity.MainModel;
import com.mbnmhj.poiohg.entity.MoreModel;
import com.mbnmhj.poiohg.mvp.XFragment;
import com.mbnmhj.poiohg.net.ApiSubscriber;
import com.mbnmhj.poiohg.net.NetError;
import com.mbnmhj.poiohg.net.XApi;
import com.mbnmhj.poiohg.util.AllUtil;
import com.mbnmhj.poiohg.util.SpUtil;
import com.youth.banner.Banner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * <p>在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.</p>
 * 格式的意义如下： 日期和时间模式 <br>
 * <p>日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
 * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
 * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
 * </p>
 * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）： <br>
 * <table border="1" cellspacing="1" cellpadding="1" summary="Chart shows format letters, date/time component,
 * presentation, and examples.">
 * <tr>
 * <th align="left">字母</th>
 * <th align="left">日期或时间元素</th>
 * <th align="left">表示</th>
 * <th align="left">示例</th>
 * </tr>
 * <tr>
 * <td><code>G</code></td>
 * <td>Era 标志符</td>
 * <td>Text</td>
 * <td><code>AD</code></td>
 * </tr>
 * <tr>
 * <td><code>y</code> </td>
 * <td>年 </td>
 * <td>Year </td>
 * <td><code>1996</code>; <code>96</code> </td>
 * </tr>
 * <tr>
 * <td><code>M</code> </td>
 * <td>年中的月份 </td>
 * <td>Month </td>
 * <td><code>July</code>; <code>Jul</code>; <code>07</code> </td>
 * </tr>
 * <tr>
 * <td><code>w</code> </td>
 * <td>年中的周数 </td>
 * <td>Number </td>
 * <td><code>27</code> </td>
 * </tr>
 * <tr>
 * <td><code>W</code> </td>
 * <td>月份中的周数 </td>
 * <td>Number </td>
 * <td><code>2</code> </td>
 * </tr>
 * <tr>
 * <td><code>D</code> </td>
 * <td>年中的天数 </td>
 * <td>Number </td>
 * <td><code>189</code> </td>
 * </tr>
 * <tr>
 * <td><code>d</code> </td>
 * <td>月份中的天数 </td>
 * <td>Number </td>
 * <td><code>10</code> </td>
 * </tr>
 * <tr>
 * <td><code>F</code> </td>
 * <td>月份中的星期 </td>
 * <td>Number </td>
 * <td><code>2</code> </td>
 * </tr>
 * <tr>
 * <td><code>E</code> </td>
 * <td>星期中的天数 </td>
 * <td>Text </td>
 * <td><code>Tuesday</code>; <code>Tue</code> </td>
 * </tr>
 * <tr>
 * <td><code>a</code> </td>
 * <td>Am/pm 标记 </td>
 * <td>Text </td>
 * <td><code>PM</code> </td>
 * </tr>
 * <tr>
 * <td><code>H</code> </td>
 * <td>一天中的小时数（0-23） </td>
 * <td>Number </td>
 * <td><code>0</code> </td>
 * </tr>
 * <tr>
 * <td><code>k</code> </td>
 * <td>一天中的小时数（1-24） </td>
 * <td>Number </td>
 * <td><code>24</code> </td>
 * </tr>
 * <tr>
 * <td><code>K</code> </td>
 * <td>am/pm 中的小时数（0-11） </td>
 * <td>Number </td>
 * <td><code>0</code> </td>
 * </tr>
 * <tr>
 * <td><code>h</code> </td>
 * <td>am/pm 中的小时数（1-12） </td>
 * <td>Number </td>
 * <td><code>12</code> </td>
 * </tr>
 * <tr>
 * <td><code>m</code> </td>
 * <td>小时中的分钟数 </td>
 * <td>Number </td>
 * <td><code>30</code> </td>
 * </tr>
 * <tr>
 * <td><code>s</code> </td>
 * <td>分钟中的秒数 </td>
 * <td>Number </td>
 * <td><code>55</code> </td>
 * </tr>
 * <tr>
 * <td><code>S</code> </td>
 * <td>毫秒数 </td>
 * <td>Number </td>
 * <td><code>978</code> </td>
 * </tr>
 * <tr>
 * <td><code>z</code> </td>
 * <td>时区 </td>
 * <td>General time zone </td>
 * <td><code>Pacific Standard Time</code>; <code>PST</code>; <code>GMT-08:00</code> </td>
 * </tr>
 * <tr>
 * <td><code>Z</code> </td>
 * <td>时区 </td>
 * <td>RFC 822 time zone </td>
 * <td><code>-0800</code> </td>
 * </tr>
 * </table>
 * <pre>
 *                                             HH:mm    15:44
 *                                            h:mm a    3:44 下午
 *                                           HH:mm z    15:44 CST
 *                                           HH:mm Z    15:44 +0800
 *                                        HH:mm zzzz    15:44 中国标准时间
 *                                          HH:mm:ss    15:44:40
 *                                        yyyy-MM-dd    2016-08-12
 *                                  yyyy-MM-dd HH:mm    2016-08-12 15:44
 *                               yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
 *                          yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
 *                     EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
 *                          yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
 *                        yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
 *                      yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
 *                                            K:mm a    3:44 下午
 *                                  EEE, MMM d, ''yy    星期五, 八月 12, '16
 *                             hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
 *                      yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
 *                        EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
 *                                     yyMMddHHmmssZ    160812154440+0800
 *                        yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
 * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
 * </pre>
 * 注意：SimpleDateFormat不是线程安全的，线程安全需用{@code ThreadLocal<SimpleDateFormat>}
 */

public class OneFragment extends XFragment {

    private int mobileType;

    private String phone;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout setRefreshing;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.main_top_img)
    View main_top_img;
    @BindView(R.id.jx_bg)
    View jx_bg;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.goods_banner)
    Banner banner;
    @BindView(R.id.msg_layout)
    View msgLayout;
    @BindView(R.id.banner_fl)
    FrameLayout bannerFl;

    private MoreModel moreModel;

    private Bundle bundle;

    private BannerGoodsAdapter imageAdapter;

    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static final String[] CHINESE_ZODIAC = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    private static final String[] ZODIAC = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
    private static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};


    private String[] msg = {"恭喜130****8866用户领取86000元额度","恭喜187****5758用户领取87000元额度",
            "恭喜137****6699用户领取69000元额度", "恭喜166****5335用户领取29000元额度", "恭喜163****2299用户领取92000元额度",
            "恭喜131****8889用户领取18000元额度", "恭喜177****8899用户领取26000元额度", "恭喜155****6789用户领取58000元额度",
            "恭喜138****5666用户领取36000元额度", "恭喜199****5009用户领取49000元额度"};

    @Override
    public void initData(Bundle savedInstanceState) {
        msgLayout.setVisibility(View.VISIBLE);
        productList();
        initViewData();
        setViewConfig();
        setRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList();
            }
        });
        main_top_img.setOnClickListener(v -> {
            productClick(moreModel);
        });
        jx_bg.setOnClickListener(v -> {
            productClick(moreModel);
        });
        bannerFl.setOnClickListener(v -> {
            productClick(moreModel);
        });
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(final long millis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        return "";
    }

    private void initBannerAdapter(List<MoreModel> data) {
        imageAdapter = null;
        imageAdapter = new BannerGoodsAdapter(data);
        imageAdapter.setBannerClickedListener(entity -> {
            if (entity != null) {
                productClick(entity);
            }
        });
        banner.setAdapter(imageAdapter);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(final long millis, String dataFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        return "";
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2Str(final long millis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return "";
    }

    public void productClick(MoreModel model) {
        if (model == null){
            return;
        }
        phone = SpUtil.getString("phone");
        NetApi.getInterfaceUtils().productClick(model.getId(), phone)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<MainModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        toWeb(model);
                    }

                    @Override
                    public void onNext(MainModel mainModel) {
                        toWeb(model);
                    }
                });
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为format</p>
     *
     * @param millis 毫秒时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String millis2String(final long millis, final DateFormat format) {
        return "";
    }

    public void productList() {
        mobileType = SpUtil.getInt("mobileType");
        NetApi.getInterfaceUtils().productList(mobileType)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(bindToLifecycle())
                .subscribe(new ApiSubscriber<MainModel<List<MoreModel>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        setRefreshing.setRefreshing(false);
                        AllUtil.showErrorInfo(getActivity(), error);
                        if (imageAdapter == null) {
                            noDataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNext(MainModel<List<MoreModel>> mainModel) {
                        setRefreshing.setRefreshing(false);
                        if (mainModel != null) {
                            if (mainModel.getCode() == 200 && mainModel.getData() != null) {
                                if (mainModel.getData() != null && mainModel.getData().size() > 0) {
                                    moreModel = mainModel.getData().get(0);
                                    initBannerAdapter(mainModel.getData());
                                } else {
                                    if (imageAdapter == null) {
                                        noDataTv.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (imageAdapter == null) {
                                    noDataTv.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            if (imageAdapter == null) {
                                noDataTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }

    private void setViewConfig() {
        viewFlipper.setInAnimation(getActivity(), R.anim.text_anim_in);
        viewFlipper.setOutAnimation(getActivity(), R.anim.text_anim_out);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time, final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void initViewData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < msg.length; i++) {
            datas.add(msg[i]);
        }
        for (String data : datas) {
            View view = getLayoutInflater().inflate(R.layout.view_gundong_msg, null);
            TextView textView = view.findViewById(R.id.msg_view);
            textView.setText(data);
            viewFlipper.addView(view);
        }
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(final String time) {
        return null;
    }


    public void toWeb(MoreModel model) {
        if (model != null) {
            bundle = new Bundle();
            bundle.putString("url", model.getUrl());
            bundle.putString("biaoti", model.getProductName());
            AllUtil.jumpPage(getActivity(), NetPageActivity.class, bundle);
        }
    }
}
