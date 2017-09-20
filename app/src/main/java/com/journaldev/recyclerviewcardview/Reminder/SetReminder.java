package com.journaldev.recyclerviewcardview.Reminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.journaldev.recyclerviewcardview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.AdapterView.*;

public class SetReminder extends AppCompatActivity implements OnClickListener{
    LinearLayout LinearLayout_reminder_popup, LinearLayout_dosage, LinearLayout_date, LinearLayout_instruction_time, layout,layout1, LinearLayout_instruction_twice, LinearLayout_date_once,LinearLayout_weekly,LinearLayout_instruction;
    TextView TextView_as_needed, TextView_medication_instruction_once, TextView_medication_instruction_twice, TextView_medication_instruction_thrice;
    Button Button_dosage,Button_remove;
    ArrayAdapter<String> adapter;
    int click_counter=0;
    EditText EditText_date_once, EditText_from, EditText_to,EditText_instruction_once,EditText_instruction_thrice,EditText_instruction_twice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_reminder);
//CALLING BINDER() METHOD
        binder();
//SETTING VISIBILITY GONE
        LinearLayout_date.setVisibility(View.GONE);
        LinearLayout_dosage.setVisibility(View.GONE);
        LinearLayout_date_once.setVisibility(GONE);
        LinearLayout_weekly.setVisibility(GONE);

// ARRAY OF DOSAGE
        final String[] repeat_type = getResources().getStringArray(R.array.repeat_type);
        String[] dosage = new String[]{
                "Select one",
                "Once",
                "Twice",
                "Thrice"

        };
        final List<String> unit = new ArrayList<>(Arrays.asList(dosage));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SetReminder.this, R.layout.simple_list_item_2, dosage) {


            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {

                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
// Set the hint text color gray
                    tv.setTextColor(getResources().getColor(R.color.grey));

                } else {
                    tv.setTextColor(getResources().getColor(R.color.textcolour));
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_list_item_1);


        String[] instruction = new String[]{
                "Select instruction ",
                "Take before meal",
                "Take after meal",
                "Take during meal",
                "Take on an Empty stomach",
                "Take with milk",
                "[other]",

        };
// Create an ArrayAdapter using the string array and a default spinner layout
        adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_2, instruction) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
// Disable the first item from Spinner
// First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
// Set the hint text color gray
                    tv.setTextColor(getResources().getColor(R.color.grey));

                } else {
                    tv.setTextColor(getResources().getColor(R.color.textcolour));
                }
                return view;
            }
        };

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.simple_list_item_1);


        LinearLayout_reminder_popup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//OPENING ALERTDIALOGBOX
                final AlertDialog.Builder builder = new AlertDialog.Builder(SetReminder.this);
                builder.setTitle("Select Option");
//BIND ARRAY-REPEAT_TYPE IN ALERTDIALOGBOX
                builder.setItems(R.array.repeat_type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (repeat_type[which].equals("One-time")) {


                            TextView_as_needed.setText("One-time");
                            LinearLayout_dosage.setVisibility(VISIBLE);
                            LinearLayout_date.setVisibility(GONE);
                            LinearLayout_date_once.setVisibility(VISIBLE);
                            LinearLayout_weekly.setVisibility(GONE);

                            Button_dosage.setOnClickListener(SetReminder.this);


                        }else if(repeat_type[which].equals("Daily")) {
                            TextView_as_needed.setText("Daily");
                            LinearLayout_dosage.setVisibility(VISIBLE);
                            LinearLayout_date.setVisibility(VISIBLE);
                            LinearLayout_weekly.setVisibility(GONE);
                            LinearLayout_date_once.setVisibility(GONE);

                            Button_dosage.setOnClickListener(SetReminder.this);

                        }else if (repeat_type[which].equals("Weekly")) {
                            TextView_as_needed.setText("Weekly");
                            LinearLayout_dosage.setVisibility(VISIBLE);
                            LinearLayout_date.setVisibility(VISIBLE);
                            LinearLayout_weekly.setVisibility(VISIBLE);
                            LinearLayout_date_once.setVisibility(GONE);

                            Button_dosage.setOnClickListener(SetReminder.this);

                        } else if (repeat_type[which].equals("Monthly")) {

                        } else if (repeat_type[which].equals("Yearly")) {

                        }

                    }
                });
                AlertDialog genderAlert = builder.create();
                genderAlert.show();
                return false;
            }
        });
    }

    void binder() {
        LinearLayout_reminder_popup = (LinearLayout) findViewById(R.id.LinearLayout_reminder_popup);
        TextView_as_needed = (TextView) findViewById(R.id.TextView_as_needed);
        Button_dosage = (Button) findViewById(R.id.Button_dosage);
        LinearLayout_date = (LinearLayout) findViewById(R.id.LinearLayout_date);
        LinearLayout_dosage = (LinearLayout) findViewById(R.id.LinearLayout_dosage);
        EditText_date_once = (EditText) findViewById(R.id.EditText_date_once);
        EditText_from = (EditText) findViewById(R.id.EditText_from);
        EditText_to = (EditText) findViewById(R.id.EditText_to);
        LinearLayout_date_once = (LinearLayout) findViewById(R.id.LinearLayout_date_once);
        LinearLayout_weekly = (LinearLayout) findViewById(R.id.LinearLayout_weekly);
        LinearLayout_instruction_time= (LinearLayout) findViewById(R.id.LinearLayout_instruction_time);



    }

void LinearLayout(){

//TEXTVIEW
    TextView titleView = new TextView(this);
    LayoutParams lparams1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    titleView.setLayoutParams(lparams1);
    titleView.setText("Select dosage");
    LinearLayout_instruction_twice.addView(titleView);
//EDITTEXT
    EditText  EditText_instruction =new EditText(this);
    LayoutParams lparams2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    EditText_instruction.setLayoutParams(lparams2);
    EditText_instruction.setHint("Select dosage");
    LinearLayout_instruction_twice.addView(EditText_instruction);


}

    @Override
    public void onClick(View v) {

            click_counter = click_counter + 1;
            Log.e("click_counter", "" + click_counter);

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflater.inflate(R.layout.linear_layout_instruction_time, null);
            TextView TextView_instruction = (TextView) layout.findViewById(R.id.TextView_instruction);
            EditText EditText_dosage = (EditText) layout.findViewById(R.id.EditText_dosage);
            EditText EditText_other = (EditText) layout.findViewById(R.id.EditText_other);
            EditText EditText_time = (EditText) layout.findViewById(R.id.EditText_time);
            final Button Button_remove = (Button) layout.findViewById(R.id.Button_remove);
         if (click_counter == 1) {
           // Toast.makeText(getApplicationContext(), "" + click_counter, Toast.LENGTH_SHORT).show();
            Button_remove.setVisibility(GONE);
        }
        LinearLayout_dosage.addView(layout);

        Button_remove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layout = (LinearLayout) inflater.inflate(R.layout.linear_layout_instruction_time, null);
                Log.e("remove button click", "remove_item");
                Log.e("counter number", "" + click_counter);

                if (click_counter > 1) {

                    LinearLayout_dosage.removeViewAt(click_counter);
                    click_counter--;


               }
                if (click_counter == 1) {
                    //Toast.makeText(getApplicationContext(), "" + click_counter, Toast.LENGTH_SHORT).show();
                    Button_remove.setVisibility(GONE);
                }
            }
        });

        }
    }

