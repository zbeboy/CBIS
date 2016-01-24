package com.school.cbis.vo;

/**
 * Created by lenovo on 2016-01-19.
 */
public class SearchData {
    private String title;
    private String url;
    private String text;

    public SearchData(String title, String url, String text) {
        this.title = title;
        this.url = url;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
