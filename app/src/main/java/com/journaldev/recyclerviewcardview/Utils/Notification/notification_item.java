package com.journaldev.recyclerviewcardview.Utils.Notification;

import java.io.Serializable;

/**
 * Created by rushvi on 08/06/2016.
 */
public class notification_item implements Serializable {

    private String title;
    private String msg;
    private String date;
    private String img;
    private String link;
    private String time;
    int id;



    public notification_item(int id,String title, String msg,String link, String img,String date,String time) {

        this.title = title;
        this.msg = msg;
        this.date=date;
        this.img=img;
        this.link=link;
        this.id=id;
        this.time=time;
    }

    public String getmsg() {
        return msg;
    }
    public String getdate() {
        return date;
    }
    public String getimg() {
        return img;
    }
    public String gettitle() {
        return title;
    }
    public  int getId(){return id;}
    public String getTime() {
        return time;
    }
    public String getLink() {
        return link;
    }

}
