package com.journaldev.recyclerviewcardview.Utils.Notification;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Rushvi on 08/06/2016.
 */
public class ManageNotification {

    public SQLiteDatabase db;
    public void ResetNotification(Context context)
    {
        db = context.openOrCreateDatabase("Reminder.db", context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists notification(id INTEGER PRIMARY KEY AUTOINCREMENT,title text,msg text,link text,img text,date text,time text)");
        db.execSQL("delete from notification");

    }
    public void SetupNotification(Context context)
    {
        db = context.openOrCreateDatabase("Reminder.db", context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists notification(id INTEGER PRIMARY KEY AUTOINCREMENT,title text,msg text,link text,img text,date text,time text)");


    }
    public int InsertNotification(Context context,String Title, String message, String Link, String img, String date, String time)
    {
        db = context.openOrCreateDatabase("Reminder.db", context.MODE_PRIVATE, null);

        db.execSQL("insert into notification(title,msg,link,img,date,time) values('" + Title + "','" + message + "','" + Link + "','" + img + "','" + date + "','"+time+"')");
        Log.d("noti data", "database inserted");
        String query = "SELECT id from notification order by id DESC limit 1";
        int lastId=0;
        Cursor c = db.rawQuery(query,null);
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        db.close();
        return  lastId;
    }

    public notification_item GetSingleNotification(Context context,int id)
    {
        db = context.openOrCreateDatabase("Reminder.db", context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("select * from notification where id=" + id, null);
        notification_item notification_item1 = null;
        if(c.moveToNext())
        {
             notification_item1=new notification_item(id,c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6));
        }

        return notification_item1;
    }

    public ArrayList<notification_item> GetAllNotification(Context context)
    {
        db = context.openOrCreateDatabase("Reminder.db", context.MODE_PRIVATE, null);
        Cursor c=db.rawQuery("select * from notification order by id desc", null);

        ArrayList<notification_item> nls=new ArrayList<notification_item>();
        while(c.moveToNext())
        {
            notification_item notification_item1 =new notification_item(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6));
            nls.add(notification_item1);
        }

        return nls;
    }

}
