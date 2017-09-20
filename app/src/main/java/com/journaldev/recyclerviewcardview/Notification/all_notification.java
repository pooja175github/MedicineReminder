package com.journaldev.recyclerviewcardview.Notification;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.journaldev.recyclerviewcardview.R;
import com.journaldev.recyclerviewcardview.Utils.Notification.ManageNotification;
import com.journaldev.recyclerviewcardview.Utils.Notification.notification_item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rushvi on 8/6/2016.
 */

public class all_notification extends AppCompatActivity {


    ArrayList<notification_item> noti_list;
    ListView noti_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Notification");
        noti_lv=(ListView)findViewById(R.id.listView5);
        SQLiteDatabase kalashdatabase ;


     //  new get_all_notification().execute();
        noti_list=new ArrayList<>();

        ManageNotification manageNotification=new ManageNotification();
        noti_list=manageNotification.GetAllNotification(all_notification.this);
        notification_adapter noti_ad=new notification_adapter(noti_list);
        noti_lv.setAdapter(noti_ad);


    }

    public class notification_adapter extends BaseAdapter {


        int i1 =0;

        ArrayList<notification_item> rowItems;

        public notification_adapter(ArrayList<notification_item> rowItems) {

            this.rowItems = rowItems;

        }

        @Override
        public int getCount() {
            if(rowItems.size()<=0)
                return 1;
            return rowItems.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        /* private view holder class */
        private class ViewHolder {
            ImageView imageView;
            TextView title;

            TextView datetime;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            View vi = convertView;
            ViewHolder holder;

            LayoutInflater mInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                vi = mInflater.inflate(R.layout.single_all_notification ,null);
                holder = new ViewHolder();

                holder.title = (TextView) vi
                        .findViewById(R.id.title);

                holder.datetime = (TextView) vi
                        .findViewById(R.id.datetime);
                holder.imageView = (ImageView) vi
                        .findViewById(R.id.imageView4);




                vi.setTag(holder);
            } else
                holder = (ViewHolder) vi.getTag();

            if(rowItems.size()<=0)
            {
                vi = mInflater.inflate(R.layout.empty_list_view, null);
                Log.d("here","size 0 "+rowItems.size() );

            }
            else {
                Log.d("here","size else "+rowItems.size());
                Picasso.with(all_notification.this).load(rowItems.get(position).getimg()).placeholder(R.mipmap.ic_launcher).resize(200, 200).into(holder.imageView);


                holder.title.setText(rowItems.get(position).gettitle());

                holder.datetime.setText(rowItems.get(position).getdate() + " " + rowItems.get(position).getTime());

                vi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent notificationIntent = new Intent(all_notification.this, single_notification.class);

                        Log.d("position",position+"");
                        notificationIntent.putExtra("img", noti_list.get(position).getimg());
                        notificationIntent.putExtra("msg", noti_list.get(position).getmsg());
                        notificationIntent.putExtra("Title", noti_list.get(position).gettitle());
                        notificationIntent.putExtra("Link", noti_list.get(position).getLink());
                        notificationIntent.putExtra("date", noti_list.get(position).getdate());
                        notificationIntent.putExtra("time", noti_list.get(position).getTime());
                        startActivity(notificationIntent);
                    }
                });

            }
            return vi;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
