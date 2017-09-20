    package com.journaldev.recyclerviewcardview.Notification;

    import android.app.Notification;
    import android.app.NotificationManager;
    import android.app.PendingIntent;
    import android.content.Context;
    import android.content.Intent;
    import android.support.v7.app.NotificationCompat;
    import android.util.Log;

    import com.google.android.gcm.GCMBaseIntentService;
    import com.journaldev.recyclerviewcardview.R;
    import com.journaldev.recyclerviewcardview.Utils.Notification.ManageNotification;

    import java.util.Calendar;


    public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService() {
    super("461997205435");
    }

    private static final String TAG = "===GCMIntentService===";


    @Override
    protected void onRegistered(Context arg0, String registrationId) {
    Log.i(TAG, "Device registered: regId = " + registrationId);
    }

    @Override
    protected void onUnregistered(Context arg0, String arg1) {
    Log.i(TAG, "unregistered = " + arg1);
    }

    @Override
    protected void onMessage(Context arg0, Intent arg1) {
    Log.d(TAG, "new message= ");
    if(arg1.getExtras()!=null)
    {
        generateNotification(arg0,arg1.getExtras().getString("message"),arg1.getExtras().getString("title"),arg1.getExtras().getString("icon"),arg1.getExtras().getString("link"));
    }

    }

    @Override
    protected void onError(Context arg0, String errorId) {
    Log.i(TAG, "Received error: " + errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
    return super.onRecoverableError(context, errorId);
    }

    private static void generateNotification(Context context, String message,String Title,String img,String Link) {
        int icon = R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        String date=day+"/"+month+"/"+year;
        String time=hour+":"+minute;

        try {

            ManageNotification manageNotification=new ManageNotification();

            manageNotification.InsertNotification(context,Title,message,Link,img,date,time);
            Log.d("gcm data","database inserted");

        } catch (Exception e) {
            Log.d("Error", e.toString());
        }

        NotificationCompat.Builder b=new NotificationCompat.Builder(context);

        b.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL);

        b.setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(Title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(Title);



        Intent intent1=null;

            intent1=new Intent(context,single_notification.class);
        intent1.putExtra("img", img);
        intent1.putExtra("msg", message);
        intent1.putExtra("Title", Title);
        intent1.putExtra("Link", Link);
        intent1.putExtra("date", date);
        intent1.putExtra("time", time);

        Log.d("img", img);
        Log.d("msg", message);
        Log.d("Title", Title);
        Log.d("Link", Link);
        Log.d("date", date);
        Log.d("time", time);

       int defaults = 0;


            defaults = defaults | Notification.DEFAULT_SOUND;

            defaults = defaults | Notification.DEFAULT_VIBRATE;

        b.setDefaults(defaults);


        long timeinmili=System.currentTimeMillis();


        b.setWhen(timeinmili);
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        b.setContentIntent(PendingIntent.getActivity(context, 0, intent1, 0));

        NotificationManager mgr=
                (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        mgr.notify(0, b.build());




    }
    }