package com.journaldev.recyclerviewcardview.Utils.Reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.journaldev.recyclerviewcardview.Utils.Show_Reminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sohampandya on 11/05/16.
 * updated by rushvi on 4/06/16
 */
public class ManageReminders {
 public   SQLiteDatabase db;



    public boolean checkdataexists(Context context)
    {   db = context.openOrCreateDatabase("Reminder.db", Context.MODE_PRIVATE,null);
        String Query = "Select * from reminderdata";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public  void resetdatabase(Context context)
    {
        db = context.openOrCreateDatabase("Reminder.db", Context.MODE_PRIVATE,null);
        db.execSQL("create table if not exists reminderdata(id INTEGER PRIMARY KEY,title TEXT,data TEXT,duedate TEXT,flag TEXT,reminder_type TEXT,status TEXT)");
        db.execSQL("create table if not exists setting(sound text,vibrate text,time text)");

        db.execSQL("delete from reminderdata");
        db.execSQL("delete from setting");
        Log.d("msg", "table created");



            Log.d("database","No data");
            db.execSQL("insert into setting values('yes','yes','10:00')");
            Log.d("setting", "insert into setting values('yes','yes','10:00')");
            db.close();

            //(i) Income Tax

            //(a) ITR Due Date


        Log.d("database", "Exists");

    }
    public void setupdatabase(Context context) {
        db = context.openOrCreateDatabase("Reminder.db", Context.MODE_PRIVATE,null);
        db.execSQL("create table if not exists reminderdata(id INTEGER PRIMARY KEY,title TEXT,data TEXT,duedate TEXT,flag TEXT,reminder_type TEXT,status TEXT)");
        db.execSQL("create table if not exists setting(sound text,vibrate text,time text)");

        Log.d("msg", "table created");

       if(!checkdataexists(context)) {

           Log.d("database","No data");
           db.execSQL("insert into setting values('yes','yes','10:00')");
           Log.d("setting", "insert into setting values('yes','yes','10:00')");
           db.close();

           //(i) Income Tax

           //(a) ITR Due Date


       }
        Log.d("database", "Exists");

    }
    /**
     *
     * Used To Set Reminder
     * @param title Title For Reminder
     * @param date When Reminder Should Trigger
     * @param info Other Information About Reminder
     * @param flag 1 for everyday 2 for month 3 for everyyear 4 onetime
     * @param context if you dont know what it is stop coding
     *  id Unique ID to identify the reminder
     */
    public int insertReminder(String title,String date,String reminder_type, ArrayList<Info> info, int flag,Context context)
    {
        Gson gson = new Gson();

        String infodata =gson.toJson(info);

        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        db.execSQL("insert into reminderdata (title,data,duedate,flag,reminder_type,status) values('" + title + "','" + infodata + "','" + date + "','" + flag + "','" + reminder_type + "','active')");
        String query = "SELECT id from reminderdata order by id DESC limit 1";
        int lastId=0;
        Cursor c = db.rawQuery(query,null);
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        db.close();
        return  lastId;

    }

    public boolean setReminder(String date,Context context,int id)
    {


            Intent myIntent = new Intent(context, AlarmReciver.class);
            myIntent.putExtra("id",String.valueOf(id));
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);


            Date date_type=new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try
            {
                date_type = format.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
            }


            String timedata=getsetting("time", context);
            String timearray[]=timedata.split(":");
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(date_type);
            calendar.set(Calendar.MINUTE,Integer.parseInt(timearray[1]));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timearray[0]));
            Calendar current = Calendar.getInstance();



            if(calendar.compareTo(current) <= 0){

             // Toast.makeText(context,"Invalid Date/Time",Toast.LENGTH_LONG).show();
           }

     else{
                Log.d("reminder-day",String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                Log.d("reminder-month",String.valueOf(calendar.get(Calendar.MONTH)));
                Log.d("reminder-year",String.valueOf(calendar.get(Calendar.YEAR)));
                Log.d("reminder-date",String.valueOf(date));
                Log.d("reminder-OVER",String.valueOf(id));

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+2000, pendingIntent);
          }


            try {
                //Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  true;
        }
    public long getrepeatingtime(int flag)
    {
        if(flag==1)
        {
            return (1000*60*60*24);

        }
        else if(flag==2)
        {
            return (1000*60*60*24*30);
        }
        else if(flag==3)
        {
            return (1000*60*60*24*365);
        }
        else if(flag==4)
        {
            return (0);
        }

        return 0;
    }
    public  ArrayList<ReminderData> getAllReminders(Context context)
    {
        ArrayList<ReminderData> reminders = new ArrayList<ReminderData>();
        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        Cursor c1 = db.rawQuery("select * from reminderdata",null);
        Gson gson = new Gson();



        while (c1.moveToNext())
        {
            ArrayList<Info> infodata =gson.fromJson(c1.getString(2),new TypeToken<ArrayList<Info>>(){}.getType());

            ReminderData r1 = new ReminderData(c1.getString(1),c1.getString(3),infodata, Integer.parseInt(c1.getString(4)),c1.getString(5),c1.getString(6));
            r1.setID(c1.getString(0));
            reminders.add(r1);
        }
        c1.close();
        db.close();
        return  reminders;
    }
    public void setreminders_today(Context context)
    {
        String ids="";
        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);
        Calendar calendar = Calendar.getInstance();
        Log.d("reminder-day",String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        Log.d("reminder-month",String.valueOf(calendar.get(Calendar.MONTH)));
        Log.d("reminder-year", String.valueOf(calendar.get(Calendar.YEAR)));
        String today_date=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(calendar.get(Calendar.YEAR));

        Cursor c1 = db.rawQuery("select * from reminderdata",null);


        while (c1.moveToNext())

        {
            if (c1.getString(6).equals("active")) {

                Date date_type = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {


                    date_type = format.parse(c1.getString(3));
                    Calendar currenttime = Calendar.getInstance();
                    Calendar newtime = Calendar.getInstance();
                    newtime.setTime(date_type);
                    int thisYear ;
                    int thisMonth;
                    int thisDay ;
                    if(newtime.getTimeInMillis()<=currenttime.getTimeInMillis()) {

                    /*  flag 1 for everyday 2 for month 3 for everyyear 4 onetime*/

                        if(c1.getString(5).equals("custom")) {
                            if (c1.getString(4).equals("1")) {
                                currenttime.add(Calendar.DATE, 1);
                            } else if (c1.getString(4).equals("2")) {
                                currenttime.add(Calendar.MONTH, 1);
                            } else if (c1.getString(4).equals("3")) {
                                currenttime.add(Calendar.YEAR, 1);
                            }


                             thisYear = currenttime.get(Calendar.YEAR);
                             thisMonth = currenttime.get(Calendar.MONTH);
                             thisDay = currenttime.get(Calendar.DAY_OF_MONTH);
                        }
                        else
                        {
                            if (c1.getString(4).equals("1")) {
                                newtime.add(Calendar.DATE, 1);
                            } else if (c1.getString(4).equals("2")) {
                                newtime.add(Calendar.MONTH, 1);
                            } else if (c1.getString(4).equals("3")) {
                                newtime.add(Calendar.YEAR, 1);
                            }


                            thisYear = newtime.get(Calendar.YEAR);
                            thisMonth = newtime.get(Calendar.MONTH);
                            thisDay = newtime.get(Calendar.DAY_OF_MONTH);
                        }

                        String newdate = thisDay + "/" + (thisMonth + 1) + "/" + thisYear;
                        db.execSQL("update reminderdata set duedate='" + newdate + "'where id='" + c1.getString(0) + "'");
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.d("error", e.toString());
                }

                Gson gson = new Gson();
                ArrayList<Info> infodata = gson.fromJson(c1.getString(2), new TypeToken<ArrayList<Info>>() {
                }.getType());
                Log.d("reminder", " setReminder " + c1.getString(1) + " " + c1.getString(3) + " " + c1.getString(5) + " " + infodata + " " + Integer.parseInt(c1.getString(4)) + " " + context + " " + Integer.parseInt(c1.getString(0)));
                setReminder(c1.getString(3), context, Integer.parseInt(c1.getString(0)));
                db.close();
            }
        }
    }


    public String getreminderid_today(Context context)
    {
        String ids="";
        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);
        Calendar calendar = Calendar.getInstance();
        Log.d("reminder-day",String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        Log.d("reminder-month",String.valueOf(calendar.get(Calendar.MONTH)));
        Log.d("reminder-year",String.valueOf(calendar.get(Calendar.YEAR)));
        String today_date=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf((calendar.get(Calendar.MONTH)+1))+"/"+String.valueOf(calendar.get(Calendar.YEAR));

        Cursor c1 = db.rawQuery("select * from reminderdata where status='active'",null);//where duedate='"+today_date+"' and
    //    Log.d("query here","select * from reminderdata where duedate='"+today_date+"' and status='active'");
        while (c1.moveToNext())
        {
            Date date=null;
            Calendar old = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = format.parse(c1.getString(3));


                System.out.println("Date ->" + date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            old.setTime(date);
            if(old.getTimeInMillis()<=calendar.getTimeInMillis())
            {
                Log.d("here in while",c1.getString(0)+" "+c1.getString(3));
                ids+=String.valueOf(c1.getString(0))+",";
            }




        }

        Log.d("ids here",ids);
        try {
            ids = ids.substring(0, ids.length() - 1);

        }catch (Exception e) {
            Log.d("error",e.toString());
        }
        db.close();
        return ids;

    }
    public  ReminderData getReminder(Context context,String id)
    {

        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        Cursor c1 = db.rawQuery("select * from reminderdata where id='"+id+"' ",null);
        Gson gson = new Gson();

        ReminderData r1=null;
        if(c1.getCount()>0)
        {
            while (c1.moveToNext()) {
                ArrayList<Info> infodata = gson.fromJson(c1.getString(2), new TypeToken<ArrayList<Info>>() {
                }.getType());

                r1 = new ReminderData(c1.getString(1), c1.getString(3), infodata, Integer.parseInt(c1.getString(4)), c1.getString(5), c1.getString(6));
                r1.setID(c1.getString(0));

            }

            c1.close();
            db.close();

            return  r1;
        }
        else
        {
            return  null;
        }
    }

    public  void cancelallreminders_without_delete(Context context)
    {

        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        Cursor c1 = db.rawQuery("select * from reminderdata",null);




        while (c1.moveToNext())
        {
            db.execSQL("update reminderdata set status='deactive' where id ='"+c1.getString(0)+"'");
            Intent myIntent = new Intent(context, Show_Reminder.class);

            PendingIntent mAlarmPendingIntent = PendingIntent.getActivity(context,Integer.parseInt( c1.getString(0)), myIntent, 0);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);

            alarmManager.cancel(mAlarmPendingIntent);


        }
        db.close();




    }


    public  void cancelallreminders_without_update(Context context)
    {

        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        Cursor c1 = db.rawQuery("select * from reminderdata",null);




        while (c1.moveToNext())
        {

            Intent myIntent = new Intent(context, Show_Reminder.class);

            PendingIntent mAlarmPendingIntent = PendingIntent.getActivity(context,Integer.parseInt( c1.getString(0)), myIntent, 0);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);

            alarmManager.cancel(mAlarmPendingIntent);


        }
        db.close();




    }

    public  void setallactivatedalarm(Context context)
    {
        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        Cursor c1 = db.rawQuery("select * from reminderdata",null);


        while (c1.moveToNext())

        {
            if(c1.getString(6).equals("active"))
            {

                Date date_type=new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {

                    String timedata=getsetting("time", context);
                    String timearray[]=timedata.split(":");
                    date_type = format.parse(c1.getString(3));
                    Calendar currenttime =Calendar.getInstance();
                    Calendar newtime = Calendar.getInstance();
                    newtime.setTime(date_type);

                    newtime.set(Calendar.MINUTE,Integer.parseInt(timearray[1]));
                    newtime.set(Calendar.SECOND,00);
                    newtime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timearray[0]));
                    if(newtime.getTimeInMillis()<=currenttime.getTimeInMillis())
                    {
                        /*  flag 1 for everyday 2 for month 3 for everyyear 4 onetime*/
                        if(c1.getString(4).equals("1"))
                        {
                            newtime.add(Calendar.DATE, 1);
                        }
                        else if(c1.getString(4).equals("2"))
                        {
                            newtime.add(Calendar.MONTH, 1);
                        }

                        else if(c1.getString(4).equals("3"))
                        {
                            newtime.add(Calendar.YEAR, 1);
                        }


                        int thisYear = newtime.get(Calendar.YEAR);
                        int thisMonth = newtime.get(Calendar.MONTH);
                        int thisDay = newtime.get(Calendar.DAY_OF_MONTH);

                        String newdate=thisDay+"/"+(thisMonth+1)+"/"+thisYear;
                        db.execSQL("update reminderdata set duedate='"+newdate+"'where id='"+c1.getString(0)+"'");

                    }


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                 Log.d("error",e.toString());
                }

                Gson gson = new Gson();
                ArrayList<Info> infodata = gson.fromJson(c1.getString(2), new TypeToken<ArrayList<Info>>() {
                }.getType());
                Log.d("reminder", " setReminder "+c1.getString(1)+" "+c1.getString(3)+" "+ c1.getString(5)+" "+ infodata+" "+Integer.parseInt(c1.getString(4))+" "+ context+" "+Integer.parseInt(c1.getString(0)));
                setReminder( c1.getString(3), context, Integer.parseInt(c1.getString(0)));

            }
        }


        db.close();




    }
    public void deletereminder(int id,Context context)
    {
        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        db.execSQL("delete from reminderdata where id ='"+id+"'");
        Intent myIntent = new Intent(context, Show_Reminder.class);

        PendingIntent mAlarmPendingIntent = PendingIntent.getActivity(context, id, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);

        alarmManager.cancel(mAlarmPendingIntent);

        db.close();


    }
    public  void cancelwithoutupdate(int id,Context context)
    {

        Intent myIntent = new Intent(context, Show_Reminder.class);

        PendingIntent mAlarmPendingIntent = PendingIntent.getActivity(context, id, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);

        alarmManager.cancel(mAlarmPendingIntent);



    }
    public  void cancelwithoutdelete(int id,Context context)
    {
        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        db.execSQL("update reminderdata set status='deactive' where id ='"+id+"'");
        Intent myIntent = new Intent(context, Show_Reminder.class);

        PendingIntent mAlarmPendingIntent = PendingIntent.getActivity(context, id, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);

        alarmManager.cancel(mAlarmPendingIntent);

        db.close();

    }
    public  void modifyReminder(ReminderData obj,Context context)
    {
        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);
        Gson gson = new Gson();
        String infodata =gson.toJson(obj.getInfo());

        db.execSQL("update reminderdata set title='" + obj.getTitle() + "',data='" + infodata + "',duedate='" + obj.getDate() + "',flag='" + obj.getFlag() + "',reminder_type='" + obj.getReminder_type() + "',status='" + obj.getStatus() + "' where id='" + obj.getID() + "'");
        db.close();
        cancelwithoutupdate(Integer.parseInt(obj.getID()), context);

        setReminder(obj.getDate(), context, Integer.parseInt(obj.getID()));



    }
    public  ArrayList<ReminderData> getCustomReminder(Context context,String type)
    {
        ArrayList<ReminderData> reminders = new ArrayList<ReminderData>();
        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        Cursor c1 = db.rawQuery("select * from reminderdata where reminder_type='"+type+"' ",null);
        Gson gson = new Gson();

        while (c1.moveToNext())
        {
            ArrayList<Info> infodata =gson.fromJson(c1.getString(2),new TypeToken<ArrayList<Info>>(){}.getType());

            ReminderData r1 = new ReminderData(c1.getString(1),c1.getString(3),infodata, Integer.parseInt(c1.getString(4)),c1.getString(5),c1.getString(6));
            r1.setID(c1.getString(0));
            reminders.add(r1);
        }
        c1.close();
        db.close();
        return  reminders;
    }
    public String getsetting(String key,Context context)
    {
        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        String value="";
        Cursor c;
        if(key.equals("sound"))
        {
            c=db.rawQuery("select sound from setting",null);
            c.moveToNext();
            value=c.getString(0);

            Log.d("here","in sound "+c.getString(0));
        }else if(key.equals("vibrate"))
        {
            c=db.rawQuery("select vibrate from setting",null);
            c.moveToNext();
            value=c.getString(0);

        }else if(key.equals("time"))
        {
            c=db.rawQuery("select time from setting",null);
            c.moveToNext();
            value=c.getString(0);

        }
        Log.d("value",value);
        return value;
    }
    public  void modifysetting(String key,String value,Context context)
    {
        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        if(key.equals("sound"))
        {
            db.execSQL("update setting set sound='"+value+"'");

        }else if(key.equals("vibrate"))
        {
            db.execSQL("update setting set vibrate='"+value+"'");


        }else if(key.equals("time"))
        {
            db.execSQL("update setting set time='"+value+"'");


        }
    }
}
