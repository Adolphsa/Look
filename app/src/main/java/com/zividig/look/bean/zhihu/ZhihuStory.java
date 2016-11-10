package com.zividig.look.bean.zhihu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adolph
 * on 2016-10-13.
 */

public class ZhihuStory {

    @SerializedName("body")
    private String body;        // HTML 格式的新闻

    @SerializedName("title")
    private String title;       //新闻标题

    @SerializedName("image")
    private String image;       //新闻图片

    @SerializedName("share_url")
    private String mShareUrl;   //供在线查看内容与分享至 SNS 用的 URL

    @SerializedName("css")
    private String[] css;       //供手机端的 WebView(UIWebView) 使用

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShareUrl() {
        return mShareUrl;
    }

    public void setShareUrl(String shareUrl) {
        mShareUrl = shareUrl;
    }

    public String[] getCss() {
        return css;
    }

    public void setCss(String[] css) {
        this.css = css;
    }
}
