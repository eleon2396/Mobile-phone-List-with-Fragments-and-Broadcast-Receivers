package com.example.eleon.application2eleon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/* Eric Leon eleon23 654889611
    CS 478 Assignment 3
    This is a custom broadcast receiver that will wait to receive information from App 3.
    This app will app get the phone that was selected and display a toast that signifies that it received a broadcast from app 3
 */
public class myBroadcastReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("BroadcastReceiver3 app", "Programmic Receiver 2 called into action.") ;

        //Get the phone string from the intent
        String foundPhone = intent.getStringExtra("com.example.phoneName");

        //Double check to make sure a string was found
        if(foundPhone != " ") {
            Log.i("BroadcastReceiver3 app", foundPhone);
            //Once it was found make a toast inside Application 2
            Toast.makeText(context, "APP 2 IS SOOOO TOASTY",
                    Toast.LENGTH_LONG).show();
        }





    }
}
