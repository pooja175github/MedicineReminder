package com.journaldev.recyclerviewcardview.Utils.Reminders;

/**
 * Created by Soham on 5/20/2016.
 * updated by rushvi
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.journaldev.recyclerviewcardview.Utils.Show_Reminder;


public class AlarmReciver extends BroadcastReceiver {


    public AlarmReciver(){
        super();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("called","called");

        ManageReminders manageReminders = new ManageReminders();
        // manageReminders.setupdatabase(context);
        // For our recurring task, we'll just display a message
        //  Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();


        String ids=manageReminders.getreminderid_today(context);
        Log.d("ids", ids);
        if(!ids.equals("")) {
            manageReminders.setreminders_today(context);
            manageReminders.cancelallreminders_without_update(context);
            manageReminders.setallactivatedalarm(context);

            Intent service1 = new Intent(context, Show_Reminder.class);
            service1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            service1.putExtra("id", ids);
            context.startActivity(service1);
        }
    }
}