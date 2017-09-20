package com.journaldev.recyclerviewcardview.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.journaldev.recyclerviewcardview.R;
import com.journaldev.recyclerviewcardview.Utils.Reminders.ManageReminders;
import com.journaldev.recyclerviewcardview.Utils.Reminders.ReminderData;

import java.util.ArrayList;

/**
 * Created by Soham on 5/16/2016.
 * edited by rushvi
 */
public class ReminderAdapter extends BaseAdapter {
    Context context;
    ArrayList<ReminderData> data;
    private static LayoutInflater inflater=null;
    ReminderData temp;
    /*************  CustomAdapter Constructor *****************/
    public ReminderAdapter(Context a, ArrayList d) {

        /********** Take passed values **********/
        context = a;
        data=d;


        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{


        public TextView title;
        public TextView date;
        public TextView type;
        public TextView flag;
        public TextView status;




    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            vi = inflater.inflate(R.layout.reminder_singleitem, null);



            holder = new ViewHolder();
            holder.title = (TextView) vi.findViewById(R.id.reminderlist_title);
            holder.date = (TextView) vi.findViewById(R.id.reminderlist_date);
            holder.type=(TextView)vi.findViewById(R.id.reminderlist_type);
            holder.flag = (TextView) vi.findViewById(R.id.reminderlist_flag);
            holder.status = (TextView) vi.findViewById(R.id.reminderlist_status);


            holder.status.setTag(position);

            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            vi = inflater.inflate(R.layout.empty_list_view, null);


        }
        else
        {
            temp=null;
            temp = (ReminderData) data.get( position );

            holder.title.setText(temp.getTitle());
            holder.date.setText( temp.getDate() );
            String flagtittle="";
            if(temp.getFlag()==1)
            {
                flagtittle=context.getString(R.string.everyday);
            }else
             if(temp.getFlag()==2)
             {
                 flagtittle=context.getString(R.string.everymonth);
             }else if(temp.getFlag()==3)
             {
                 flagtittle=context.getString(R.string.everyyear);
             }else if(temp.getFlag()==4)
             {
                 flagtittle=context.getString(R.string.onetime);
             }

            holder.flag.setText(flagtittle);
            holder.status.setText( temp.getStatus() );
            String typedata="";
            if(temp.getReminder_type().equals("custom"))
            {
                typedata=context.getString(R.string.type_custom);
            }else if(temp.getReminder_type().equals("income"))
            {
                typedata=context.getString(R.string.type_income);
            }else  if(temp.getReminder_type().equals("service"))
            {
                typedata=context.getString(R.string.type_service);
            }

            holder.type.setText(typedata);
            if(temp.getStatus().equals("active"))
            {


                    holder.status.setBackgroundColor(context.getResources().getColor(R.color.material_green_900));


            }
            else
            {
                holder.status.setBackgroundColor(context.getResources().getColor(R.color.material_red_900));
            }

            Log.d(temp.getTitle(),temp.getStatus());
            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View v1 = v;


                    TextView status=(TextView) v;
                    ManageReminders manageReminders= new ManageReminders();

                    ReminderData obj = data.get(position);
                    Log.d("clicked position before",position+" "+obj.getStatus());
                    if(obj.getStatus().equals("active"))
                    {


                        status.setText("deactive");
                        status.setBackgroundColor(context.getResources().getColor(R.color.material_red_900));
                        obj.setStatus("deactive");

                        manageReminders.cancelwithoutdelete(Integer.parseInt(obj.getID()),context);


                    }
                    else
                    {
                        status.setText("active");
                        status.setBackgroundColor(context.getResources().getColor(R.color.material_green_900));
                        obj.setStatus("active");
                        manageReminders.modifyReminder(obj,context);

                    }

                    Log.d("after click " + obj.getTitle(), obj.getStatus());

                }
            });

        }


        return vi;
    }


}
