package com.zividig.look.bean.zhihu;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by adolph
 * on 2016-10-13.
 */

public class ZhihuDaily {

    @SerializedName("date")
    private String date;  //日期

    @SerializedName("stories")
    private ArrayList<ZhihuDailyItem> stories;  //当日新闻

    @SerializedName("top_stories")
    private ArrayList<ZhihuDailyItem> mZhihuDailyItems;     //界面顶部 ViewPager 滚动显示的显示内容

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ZhihuDailyItem> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhihuDailyItem> stories) {
        this.stories = stories;
    }

    public ArrayList<ZhihuDailyItem> getZhihuDailyItems() {
        return mZhihuDailyItems;
    }

    public void setZhihuDailyItems(ArrayList<ZhihuDailyItem> zhihuDailyItems) {
        mZhihuDailyItems = zhihuDailyItems;
    }
}
