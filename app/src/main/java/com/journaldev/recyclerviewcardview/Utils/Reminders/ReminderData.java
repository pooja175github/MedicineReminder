package com.journaldev.recyclerviewcardview.Utils.Reminders;

import java.util.ArrayList;

public class ReminderData
{

public String ID;
   public String Title;
public String reminder_type;
public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReminder_type() {
        return reminder_type;
    }

    public void setReminder_type(String reminder_type) {
        this.reminder_type = reminder_type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String Date;
   public ArrayList<Info> info;
   public int flag;

   /**
    *
    * @param title Title For Reminder
    * @param date When Reminder Should Trigger
    * @param info Other Information About Reminder
    * @param flag 1 for everyday 2 for month 3 for everyyear
    */
   public ReminderData(String title, String date, ArrayList<Info> info, int flag,String reminder_type,String status) {
       Title = title;
       Date = date;
       this.info = info;
       this.flag = flag;
       this.status=status;
       this.reminder_type=reminder_type;
   }

   public String getTitle() {
       return Title;
   }

   public void setTitle(String title) {
       Title = title;
   }

   public String getDate() {
       return Date;
   }

   public void setDate(String date) {
       Date = date;
   }

   public ArrayList<Info> getInfo() {
       return info;
   }

   public void setInfo(ArrayList<Info> info) {
       this.info = info;
   }

   public int getFlag() {
       return flag;
   }

   public void setFlag(int flag) {
       this.flag = flag;
   }
}
