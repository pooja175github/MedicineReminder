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
import com.journaldev.recyclerviewcardview.Model.Imagedata;
import com.journaldev.recyclerviewcardview.R;
import com.journaldev.recyclerviewcardview.Utils.Show_Reminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sohampandya on 11/05/16.
 * updated by rushvi on 4/06/16
 */
public class ManageDatabase {
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
        db.execSQL("create table if not exists setting(sound text,soundurl text,vibrate text,snooze text)");
        db.execSQL("create table if not exists instruction(id INTEGER PRIMARY KEY,title text)");
        db.execSQL("create table if not exists images(id INTEGER PRIMARY KEY,title text,type text,url INTEGER)");


        db.execSQL("delete from reminderdata");
        db.execSQL("delete from setting");
        Log.d("msg", "table created");
        Log.d("database","No data");

        db.execSQL("insert into setting values('yes','','yes','5')");

        db.execSQL("insert into instruction(title) values('Take before meal')");
        db.execSQL("insert into instruction(title) values('Take after meal')");
        db.execSQL("insert into instruction(title) values('Take during meal')");
        db.execSQL("insert into instruction(title) values('Take on an empty stomach')");
        db.execSQL("insert into instruction(title) values('Take with milk')");
        db.execSQL("insert into instruction(title) values('Take with water')");

        db.execSQL("insert into images(title,type,url) values('Tablet','default',"+R.mipmap.ic_tablate+")");
        db.execSQL("insert into images(title,type,url) values('Syrup','default',"+R.mipmap.ic_syrup+")");
        db.execSQL("insert into images(title,type,url) values('Powder','default',"+R.mipmap.ic_powder+")");
        db.execSQL("insert into images(title,type,url) values('Injection','default',"+R.mipmap.ic_injection+")");
        db.execSQL("insert into images(title,type,url) values('Capsule','default',"+R.mipmap.ic_pills+")");
        db.execSQL("insert into images(title,type,url) values('Drop','default',"+R.mipmap.ic_drop+")");
        db.execSQL("insert into images(title,type,url) values('Inhaler','default',"+R.mipmap.ic_inhalor+")");
        db.execSQL("insert into images(title,type,url) values('Cream','default',"+R.mipmap.ic_cream+")");



        db.close();

            //(i) Income Tax

            //(a) ITR Due Date


        Log.d("database", "Exists");

    }

    public Imagedata getImage(int id,Context context)
    {
        Imagedata imgdata=null;
        db = context.openOrCreateDatabase("Reminder.db", Context.MODE_PRIVATE,null);
        Cursor c1=db.rawQuery("select * from images where id="+id,null);
        while (c1.moveToNext())
        {
            imgdata=new Imagedata(c1.getInt(0),c1.getString(1),c1.getString(2),c1.getInt(3),c1.getString(4));
        }

        return imgdata;


    }
    public void setupdatabase(Context context) {
        db = context.openOrCreateDatabase("Reminder.db", Context.MODE_PRIVATE,null);
        db.execSQL("create table if not exists reminderdata(id INTEGER PRIMARY KEY,title TEXT,data TEXT,duedate TEXT,flag TEXT,reminder_type TEXT,status TEXT)");
        db.execSQL("create table if not exists setting(sound text,soundurl text,vibrate text,snooze text)");
        db.execSQL("create table if not exists instruction(id INTEGER PRIMARY KEY,title text)");
        db.execSQL("create table if not exists images(id INTEGER PRIMARY KEY,title text,type text,urldefault INTEGER,url text)");


        //   db.execSQL("create table if not exists setting(sound text,vibrate text,time text)");

        Log.d("msg", "table created");

       if(!checkdataexists(context)) {



           Log.d("msg", "table created");
           Log.d("database","No data");

           db.execSQL("insert into setting values('yes','','yes','5')");

           db.execSQL("insert into instruction(title) values('Take before meal')");
           db.execSQL("insert into instruction(title) values('Take after meal')");
           db.execSQL("insert into instruction(title) values('Take during meal')");
           db.execSQL("insert into instruction(title) values('Take on an empty stomach')");
           db.execSQL("insert into instruction(title) values('Take with milk')");
           db.execSQL("insert into instruction(title) values('Take with water')");

           db.execSQL("insert into images(title,type,urldefault) values('Tablet','default',"+R.mipmap.ic_tablate+")");
           db.execSQL("insert into images(title,type,urldefault) values('Syrup','default',"+R.mipmap.ic_syrup+")");
           db.execSQL("insert into images(title,type,urldefault) values('Powder','default',"+R.mipmap.ic_powder+")");
           db.execSQL("insert into images(title,type,urldefault) values('Injection','default',"+R.mipmap.ic_injection+")");
           db.execSQL("insert into images(title,type,urldefault) values('Capsule','default',"+R.mipmap.ic_pills+")");
           db.execSQL("insert into images(title,type,urldefault) values('Drop','default',"+R.mipmap.ic_drop+")");
           db.execSQL("insert into images(title,type,urldefault) values('Inhaler','default',"+R.mipmap.ic_inhalor+")");
           db.execSQL("insert into images(title,type,urldefault) values('Cream','default',"+R.mipmap.ic_cream+")");



           db.close();
           Log.d("database","No data");
        //   db.execSQL("insert into setting values('yes','yes','10:00')");
          // Log.d("setting", "insert into setting values('yes','yes','10:00')");



       }
        Log.d("database", "Exists");
        db.close();
    }

    public int InsertImage(Context context,String url)
    {  db = context.openOrCreateDatabase("Reminder.db", Context.MODE_PRIVATE,null);
        db.execSQL("insert into images(type,url) values('user','"+url+"')");

        String query = "SELECT id from images order by id DESC limit 1";
        int lastId=0;
        Cursor c = db.rawQuery(query,null);
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        db.close();
        return  lastId;
    }

    /**
     *
     * Used To Set Reminder
     * @param title Title For Reminder
     * @param date When Reminder Should Trigger
     * @param info Other Information About Reminder
     * @param flag 1 for everyday 2 for month 3 for everyyear 4 onetime 5 for weekly
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


    public ArrayList<String>getInstructions(Context context)
    {
        ArrayList<String>instruction=new ArrayList<>();

        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);

        Cursor c=db.rawQuery("select * from instruction",null);
        while (c.moveToNext())
        {
            instruction.add(c.getString(1));
        }
        db.close();

        return instruction;


    }

    public void Checkstatusforall(Context context) {

        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);
        Calendar calendar = Calendar.getInstance();
        Log.d("reminder-day",String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        Log.d("reminder-month",String.valueOf(calendar.get(Calendar.MONTH)));
        Log.d("reminder-year",String.valueOf(calendar.get(Calendar.YEAR)));
        String today_date=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf((calendar.get(Calendar.MONTH)+1))+"/"+String.valueOf(calendar.get(Calendar.YEAR));

        Cursor c1 = db.rawQuery("select * from reminderdata where status='active'",null);//where duedate='"+today_date+"' and
        //    Log.d("query here","select * from reminderdata where duedate='"+today_date+"' and status='active'");
        while (c1.moveToNext()) {
            if (!c1.getString(4).equals("4")) {
                //except one time reminder
                Date date = null;
                Calendar todate = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Gson gson = new Gson();
                ArrayList<Info> infodata =gson.fromJson(c1.getString(2),new TypeToken<ArrayList<Info>>(){}.getType());
                String alltime[]=infodata.get(7).getValue().split(",");
                String maxtime=alltime[0];
                for(int i=1;i<alltime.length;i++) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    try {
                        Date inTime = sdf.parse(maxtime);
                        Date outTime = sdf.parse(alltime[i]);


                        if(outTime.compareTo(inTime) > 0){

                            maxtime = alltime[i];
                        }
                    } catch (Exception e) {
                        Log.e("Error parse", e.toString());
                    }
                }
                try {
                    date = format.parse(infodata.get(5).getValue()+" "+maxtime);
                    System.out.println("Date ->" + date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                todate.setTime(date);
                if (todate.getTimeInMillis() < calendar.getTimeInMillis()) {
                    Log.d("here update status", c1.getString(0) + " " + c1.getString(5));
                    db.execSQL("update reminderdata set status='deactive' where id="+c1.getInt(0));

                        Log.d("update status in check","in if");
                }

            }else
            {
                //for one time reminders
                Date date = null;
                Calendar todate = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Gson gson = new Gson();
                ArrayList<Info> infodata =gson.fromJson(c1.getString(2),new TypeToken<ArrayList<Info>>(){}.getType());
                String alltime[]=infodata.get(7).getValue().split(",");
                String maxtime=alltime[0];
                for(int i=1;i<alltime.length;i++) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    try {
                        Date inTime = sdf.parse(maxtime);
                        Date outTime = sdf.parse(alltime[i]);


                        if(outTime.compareTo(inTime) > 0){

                            maxtime = alltime[i];
                        }
                    } catch (Exception e) {
                        Log.e("Error parse", e.toString());
                    }
                }
                try {
                    date = format.parse(c1.getString(3)+" "+maxtime);


                    System.out.println("Date ->" + date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                todate.setTime(date);
                if (todate.getTimeInMillis() <= calendar.getTimeInMillis()) {
                    Log.d("here update status", c1.getString(0) + " " + c1.getString(5));
                    db.execSQL("update reminderdata set status='deactive' where id="+c1.getInt(0));
                    Log.d("update status in check","in else");
                }

            }
        }
        }

    public boolean setReminder(String date,Context context,int id,String time)
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


            String timedata=time;
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

    public  ArrayList<ReminderData> getAllReminders(Context context)
    {
        db= context.openOrCreateDatabase("Reminder.db",Context.MODE_PRIVATE,null);
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
                    if(newtime.getTimeInMillis()<currenttime.getTimeInMillis()) {

                    /*  flag 1 for everyday 2 for month 3 for everyyear 4 onetime*/


                            if (c1.getString(4).equals("1")) {
                                currenttime.add(Calendar.DATE, 1);
                            } else if (c1.getString(4).equals("2")) {
                                currenttime.add(Calendar.MONTH, 1);
                            } else  if (c1.getString(5).equals("1")) {
                            currenttime.add(Calendar.DATE, 1);
                        }


                             thisYear = currenttime.get(Calendar.YEAR);
                             thisMonth = currenttime.get(Calendar.MONTH);
                             thisDay = currenttime.get(Calendar.DAY_OF_MONTH);


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
                String alltime[]=infodata.get(7).getValue().split(",");
                for(int i=0;i<alltime.length;i++)
                {
                    setReminder(c1.getString(3), context, Integer.parseInt(c1.getString(0)),alltime[i]);
                }

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
            if(old.getTimeInMillis()<calendar.getTimeInMillis())
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

        Log.d("Set all activate alarm","inside");

        while (c1.moveToNext())

        {
            if(c1.getString(6).equals("active"))
            {

                Date date_type=new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Gson gson = new Gson();
                    ArrayList<Info> infodata = gson.fromJson(c1.getString(2), new TypeToken<ArrayList<Info>>() {
                    }.getType());
                //    Log.d("reminder", " setReminder " + c1.getString(1) + " " + c1.getString(3) + " " + c1.getString(5) + " " + infodata + " " + Integer.parseInt(c1.getString(4)) + " " + context + " " + Integer.parseInt(c1.getString(0)));
                    String alltime[]=infodata.get(7).getValue().split(",");
                    String maxtime=alltime[0];

                    String weekday;
                    for(int i=1;i<alltime.length;i++)
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        Date inTime = sdf.parse(maxtime);
                        Date outTime = sdf.parse(alltime[i]);

                        if(outTime.compareTo(inTime) > 0){

                            maxtime=alltime[i];
                        }


                    }
                    Log.d("maxtime",maxtime);
                    String timedata=maxtime;
                    String timearray[]=timedata.split(":");
                    date_type = format.parse(c1.getString(3));
                    Calendar currenttime =Calendar.getInstance();
                    Calendar newtime = Calendar.getInstance();
                    newtime.setTime(date_type);

                    newtime.set(Calendar.MINUTE,Integer.parseInt(timearray[1]));
                    newtime.set(Calendar.SECOND,00);
                    newtime.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timearray[0]));
                    Log.d("here",c1.getString(4));
                   // if(newtime.getTimeInMillis()<=currenttime.getTimeInMillis())
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
                        else if(c1.getString(4).equals("5"))
                        {

                            Log.d("here in week","in week");
                            int weekdayint = 0;
                            int dayOfWeek=newtime.get(Calendar.DAY_OF_WEEK);
                            String weekDay = null;
                            if (Calendar.MONDAY == dayOfWeek) {
                                weekdayint=Calendar.MONDAY;
                                weekDay = context.getString(R.string.Monday);

                            } else if (Calendar.TUESDAY == dayOfWeek) {
                                weekdayint=Calendar.TUESDAY;
                                weekDay = context.getString(R.string.Tuesday);

                            } else if (Calendar.WEDNESDAY == dayOfWeek) {
                                weekdayint=Calendar.WEDNESDAY;
                                weekDay = context.getString(R.string.Wednesday);

                            } else if (Calendar.THURSDAY == dayOfWeek) {
                                weekdayint=Calendar.THURSDAY;
                                weekDay = context.getString(R.string.Thursday);

                            } else if (Calendar.FRIDAY == dayOfWeek) {
                                weekdayint=Calendar.FRIDAY;
                                weekDay = context.getString(R.string.Friday);

                            } else if (Calendar.SATURDAY == dayOfWeek) {
                                weekdayint=Calendar.SATURDAY;
                                weekDay = context.getString(R.string.Saturday);

                            } else if (Calendar.SUNDAY == dayOfWeek) {
                                weekdayint=Calendar.SUNDAY;
                                weekDay = context.getString(R.string.Sunday);

                            }

                            weekday=infodata.get(9).getValue();
                            Log.d("selected days",infodata.get(9).getValue());
                            String day[]=weekday.split(",");
                            String next="";
                            int nxtday = 0;
                            int addday;
                            Log.d("weekday today",weekDay+" "+weekdayint);
                            int count[]={1,2,3,4,5,6,7,1,2,3,4,5,6,7};
                            String getweek[]={"",context.getString(R.string.Sunday),context.getString(R.string.Monday),context.getString(R.string.Tuesday)
                            ,context.getString(R.string.Wednesday), context.getString(R.string.Thursday),
                            context.getString(R.string.Friday),context.getString(R.string.Saturday)};
                            for(int i=weekdayint;i<weekdayint+7;i++)
                            {
                                int c=count[i];
                                if (Arrays.asList(day).contains(getweek[c])) {

                                    next=getweek[c];
                                    nxtday=c;

                                    break;
                                }

                            }

                            if(nxtday>weekdayint)
                            {
                                addday=nxtday-weekdayint;
                            }else
                            {
                                addday=nxtday-weekdayint+7;
                            }

                            Log.d("next day of week",next+" "+nxtday);
                            Log.d("add day",addday+"");
                            newtime.add(Calendar.DATE, addday);
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
               // Log.d("reminder", " setReminder "+c1.getString(1)+" "+c1.getString(3)+" "+ c1.getString(5)+" "+ infodata+" "+Integer.parseInt(c1.getString(4))+" "+ context+" "+Integer.parseInt(c1.getString(0)));
                String alltime[]=infodata.get(7).getValue().split(",");

                Log.d("all time",infodata.get(7).getValue());
                Log.d("all time length",alltime.length+"");
                for(int i=0;i<alltime.length;i++)
                {
                    setReminder( c1.getString(3), context, Integer.parseInt(c1.getString(0)),alltime[i]);

                }


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

       setallactivatedalarm(context);



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


        }
    }
}
