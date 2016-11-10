package com.zividig.look.bean.zhihu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adolph
 * on 2016-10-13.
 */

public class ZhihuDailyItem {

    @SerializedName("images")
    private String[] images;    //图像地址

    @SerializedName("type")
    private int type;           //类型

    @SerializedName("id")
    private String id;             //内容的id

    @SerializedName("title")
    private String title;       //标题

    private String date;
    public boolean hasFadedIn = false;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
