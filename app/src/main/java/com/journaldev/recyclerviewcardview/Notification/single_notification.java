package com.journaldev.recyclerviewcardview.Notification;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.journaldev.recyclerviewcardview.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

public class single_notification extends AppCompatActivity {
    JSONArray venues;


    String title,msg,img="",link="";
    ImageView iv;
    Button b1;
    TextView t1;
    TextView t2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_notification);
        iv=(ImageView)findViewById(R.id.imageView1);

        b1=(Button)findViewById(R.id.button);
        t1=(TextView)findViewById(R.id.textView1);
        t2=(TextView)findViewById(R.id.textView2);


        Bundle extras = getIntent().getExtras();
        if(extras == null) {

            Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_LONG).show();
        } else {
            title= extras.getString("Title");
            msg=extras.getString("msg");
            link=extras.getString("Link");
            img=extras.getString("img");
            t1.setText(title);
           t2.setText(msg);
            t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);



            if(!link.equals(""))
            {

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     try {
                         Intent i = new Intent(Intent.ACTION_VIEW);
                         i.setData(Uri.parse(link));
                         startActivity(i);
                     }
                     catch (Exception e){
                         Toast.makeText(getApplicationContext(), "Error opening this URL!!", Toast.LENGTH_LONG).show();
                     }
                    }

                });


            }
            else
                b1.setVisibility(View.GONE);
            Picasso.with(single_notification.this).load(img).placeholder(R.mipmap.ic_launcher).resize(200,200).into(iv);

        }

    }



}
