package com.journaldev.recyclerviewcardview.Model;

/**
 * Created by pragma1 on 17/06/2016.
 */
public class Imagedata {
    int id,urldefault;
    String title,type,url;

    public Imagedata(int id,String title, String type, int urldefault , String url) {
        this.id = id;
        this.urldefault = urldefault;
        this.title = title;
        this.type = type;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public int getUrldefault() {
        return urldefault;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
