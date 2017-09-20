package com.journaldev.recyclerviewcardview.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Soham on 5/5/2016.
 */
public class sharedpreference {

    public static final String MyPREFERENCES = "MyPrefs" ;


    public static void setvalue(String name,String value,Context c1)
    {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(name, value);
        editor.commit();


    }

    public static void clearall(Context c1)
    {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

    }

    public static void clear(String name,Context c1)
    {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(name);
        editor.commit();

    }
    public static String getvalue(String name, Context c1)
    {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans =sharedpreferences.getString(name,"no");
        return  ans;
    }
}
