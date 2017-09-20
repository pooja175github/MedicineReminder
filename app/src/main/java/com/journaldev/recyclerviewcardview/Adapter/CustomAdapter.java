package com.journaldev.recyclerviewcardview.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.journaldev.recyclerviewcardview.MainActivity;
import com.journaldev.recyclerviewcardview.R;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName,textViewVersion;
        LinearLayout LinearLayoutCard_view;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.LinearLayoutCard_view = (LinearLayout) itemView.findViewById(R.id.LinearLayoutCard_view);
        }
    }

    public CustomAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        int colors[]={};
        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getName());
        textViewVersion.setText(dataSet.get(listPosition).getVersion());
        imageView.setImageResource(dataSet.get(listPosition).getImage());

        if(listPosition==0) {

            holder.LinearLayoutCard_view.setBackgroundColor(Color.GREEN);

        }
        else if(listPosition==1)
            holder.LinearLayoutCard_view.setBackgroundColor(Color.BLUE);
        else if(listPosition==2)
            holder.LinearLayoutCard_view.setBackgroundColor(Color.RED);
        else if(listPosition==3)
            holder.LinearLayoutCard_view.setBackgroundColor(Color.MAGENTA);
        else if(listPosition==4)
            holder.LinearLayoutCard_view.setBackgroundColor(Color.YELLOW);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
