package com.journaldev.recyclerviewcardview.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.devspark.appmsg.AppMsg;
import com.journaldev.recyclerviewcardview.R;
import com.journaldev.recyclerviewcardview.Utils.Reminders.ManageReminders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Soham on 5/10/2016.
 */
public class allcontrols {

    public static void setdatepicker (final EditText e1,final Context context)
    {
                e1.setFocusable(false);
                e1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

                                        e1.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                                    }
                                })
                                .setFirstDayOfWeek(Calendar.SUNDAY)

                                .setDateRange(null, null)
                                .setDoneText("Yes")
                                .setCancelText("No")
                                .setThemeDark();

                        if(e1.getText().toString().equals(""))
                        {
                            cdp.setPreselectedDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) , Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                        }
                        else
                        {
                            String datestring = e1.getText().toString();
                            SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
                            try {
                                Date date = format.parse(datestring);

                                Calendar c1 = Calendar.getInstance();
                                c1.setTime(date);
                                cdp.setPreselectedDate(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH) , c1.get(Calendar.DAY_OF_MONTH));
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                        FragmentActivity activity = (FragmentActivity) context;
                        cdp.show(activity.getSupportFragmentManager(), "date");

                    }
                });
            }

    public static void settimeepicker (final EditText e1,final Context context)
    {
        e1.setFocusable(false);
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadialTimePickerDialogFragment cdp = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {


                                                  @Override
                                                  public void onTimeSet(RadialTimePickerDialogFragment radialTimePickerDialogFragment, int i, int i1) {
                                                      e1.setText(i + ":" + i1);
                                                  }
                                              }
                        );

                if(e1.getText().toString().equals(""))
                {
                    cdp.setStartTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE));


                }
                else
                {
                    String datestring = e1.getText().toString();
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm");
                    try {
                        Date date = format.parse(datestring);

                        Calendar c1 = Calendar.getInstance();
                        c1.setTime(date);
                        cdp.setStartTime(c1.get(Calendar.HOUR_OF_DAY), c1.get(Calendar.MINUTE));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }


                FragmentActivity activity = (FragmentActivity) context;
                cdp.show(activity.getSupportFragmentManager(), "date");

            }
        });
    }

    public static void settimepicker (final LinearLayout e1,final Context context, final int hour, final int min)
    {
        e1.setFocusable(false);
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadialTimePickerDialogFragment cdp = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {


                                                  @Override
                                                  public void onTimeSet(RadialTimePickerDialogFragment radialTimePickerDialogFragment, int i, int i1) {


                                                      FragmentActivity activity = (FragmentActivity) context;
                                                      AppMsg.makeText(activity,"Updated new reminder time "+i+":"+i1, AppMsg.STYLE_INFO).show();
                                                      ManageReminders manageReminders=new ManageReminders();
                                                      manageReminders.modifysetting("time",i+":"+i1,context);
                                                      manageReminders.cancelallreminders_without_update(context);
                                                      manageReminders.setallactivatedalarm(context);

                                                  }
                                              }
                        );


                    cdp.setStartTime(hour, min);





                FragmentActivity activity = (FragmentActivity) context;
                cdp.show(activity.getSupportFragmentManager(), "date");

            }
        });
    }

    public static void setflagspinner(final EditText e1,final Context c1)
    {
        e1.setFocusable(false);
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog alert;
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(c1);


                final ArrayList<String> list = new ArrayList<String>();

                list.add(c1.getString(R.string.onetime));
                list.add(c1.getString(R.string.everyday));
                list.add(c1.getString(R.string.everymonth));
                list.add(c1.getString(R.string.everyyear));

                String[] listarr = new String[list.size()];
                listarr = list.toArray(listarr);
                alertbuilder.setSingleChoiceItems(listarr, list.indexOf(e1.getText().toString()), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        e1.setText(list.get(arg1).toString());

                        arg0.dismiss();
                    }
                });


                alertbuilder.setTitle("Select Type");


                alert = alertbuilder.create();

                alert.getWindow().setLayout(600, 400);


                alert.show();

            }
        });


    }





    public static void setyearspinner(final EditText e1,final Context c1)
            {
                e1.setFocusable(false);
                e1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog alert;
                        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(c1);


                        final ArrayList<String> list = new ArrayList<String>();

                        list.add("2000-01");
                        list.add("2001-02");
                        list.add("2002-03");
                        list.add("2003-04");
                        list.add("2004-05");
                        list.add("2005-06");
                        list.add("2006-07");
                        list.add("2007-08");
                        list.add("2008-09");
                        list.add("2009-10");
                        list.add("2010-11");
                        list.add("2011-12");
                        list.add("2012-13");
                        list.add("2013-14");
                        list.add("2014-15");
                        String[] listarr = new String[list.size()];
                        listarr = list.toArray(listarr);
                        alertbuilder.setSingleChoiceItems(listarr, list.indexOf(e1.getText().toString()), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                e1.setText(list.get(arg1).toString());

                                arg0.dismiss();
                            }
                        });


                        alertbuilder.setTitle("Select Year");


                        alert = alertbuilder.create();

                        alert.getWindow().setLayout(600, 400);


                        alert.show();

                    }
                });


            }



    public static void setquarterspinner(final EditText e1,final Context c1)
    {
        e1.setFocusable(false);
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog alert;
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(c1);


                final ArrayList<String> list = new ArrayList<String>();

                list.add("March");
                list.add("June");
                list.add("September");
                list.add("December");

                String[] listarr = new String[list.size()];
                listarr = list.toArray(listarr);
                alertbuilder.setSingleChoiceItems(listarr, list.indexOf(e1.getText().toString()), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        e1.setText(list.get(arg1).toString());

                        arg0.dismiss();
                    }
                });


                alertbuilder.setTitle("Select Quarter");


                alert = alertbuilder.create();

                alert.getWindow().setLayout(600, 400);


                alert.show();

            }
        });


    }






}
