package com.journaldev.recyclerviewcardview.Utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.journaldev.recyclerviewcardview.R;
import com.journaldev.recyclerviewcardview.Utils.Reminders.ManageReminders;
import com.journaldev.recyclerviewcardview.Utils.Reminders.ReminderData;


/**
 * Created by Soham on 5/14/2016.
 * updated by rushvi
 */
public class Show_Reminder extends AppCompatActivity {
    MediaPlayer mp=null ;
    String sound,vibrate;
    Vibrator v;
    Button stopAlarm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.showreminder);
        bindcontrols();
        String ids =getIntent().getStringExtra("id").toString();
        ManageReminders manageReminders=new ManageReminders();
        sound=manageReminders.getsetting("sound",Show_Reminder.this);
        vibrate=manageReminders.getsetting("vibrate",Show_Reminder.this);
       // Toast.makeText(Show_Reminder.this, ids, Toast.LENGTH_LONG).show();

        LinearLayout l1 =(LinearLayout) findViewById(R.id.container);

        String[] id=ids.split(",");

        for(int i =0;i<id.length;i++)
        {
            LinearLayout l2 =new LinearLayout(Show_Reminder.this);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            CardView card = (CardView)inflater.inflate(R.layout.show_dialog_item, null);
            ReminderData r1 = new ManageReminders().getReminder(Show_Reminder.this,id[i]);

            TextView reminder_title=(TextView)card.findViewById(R.id.reminderlist_title);
            TextView reminder_flag=(TextView)card.findViewById(R.id.reminderlist_flag);
            TextView reminder_type=(TextView)card.findViewById(R.id.reminderlist_type);
            reminder_title.setText(r1.getTitle());
            String typedata="";
            if(r1.getReminder_type().equals("custom"))
            {
                typedata=getString(R.string.type_custom);
            }else if(r1.getReminder_type().equals("income"))
            {
                typedata=getString(R.string.type_income);
            }else  if(r1.getReminder_type().equals("service"))
            {
                typedata=getString(R.string.type_service);
            }

            reminder_type.setText(typedata);
            int flag=r1.getFlag();
            String flagval="";
            //1 for everyday 2 for month 3 for everyyear 4 onetime
            if(flag==1)
            {
                flagval= "everyday";
            }else if(flag==2)
            {
                flagval="month";
            }else if(flag==3)
            {
                flagval="everyyear";
            }else if(flag==4)
            {
                flagval="onetime";
            }
            reminder_flag.setText(flagval);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(20, 20, 20, 20);
            l2.setLayoutParams(layoutParams);
            l2.addView(card);
            l1.addView(l2);
        }

     //   this.setTitle(r1.getTitle());


        if(vibrate.equals("yes"))
        {
            v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            // This example will cause the phone to vibrate "SOS" in Morse Code
// In Morse Code, "s" = "dot-dot-dot", "o" = "dash-dash-dash"
// There are pauses to separate dots/dashes, letters, and words
// The following numbers represent millisecond lengths
            int dot = 200;      // Length of a Morse Code "dot" in milliseconds
            int dash = 500;     // Length of a Morse Code "dash" in milliseconds
            int short_gap = 200;    // Length of Gap Between dots/dashes
            int medium_gap = 500;   // Length of Gap Between Letters
            int long_gap = 1000;    // Length of Gap Between Words
            long[] pattern = {
                    0,  // Start immediately
                    dot, short_gap, dot, short_gap, dot,    // s
                    medium_gap,
                    dash, short_gap, dash, short_gap, dash, // o
                    medium_gap,
                    dot, short_gap, dot, short_gap, dot,    // s
                    long_gap
            };

// Only perform this pattern one time (-1 means "do not repeat")
            v.vibrate(pattern, -1);


        }

        Uri alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mp = MediaPlayer.create(getBaseContext(), alarmTone);

        stopAlarm.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                if (sound.equals("yes")) {
                    mp.stop();
                }
                if(vibrate.equals("yes"))
                {
                    v.cancel();
                }
                finish();
                return false;
            }
        });
        if(sound.equals("yes")) {
            playSound(this, getAlarmUri());
        }

    }

    private void playSound(final Context context, Uri alert) {


        Thread background = new Thread(new Runnable() {
            public void run() {
                try {

                    mp.start();

                } catch (Throwable t) {
                    Log.i("Animation", "Thread  exception "+t);
                }
            }
        });
        background.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }               //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private Uri getAlarmUri() {

        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }
    public void bindcontrols()
    {
        stopAlarm = (Button) findViewById(R.id.stopAlarm);

    }
}
