package com.example.eleon.application1eleon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/* Eric Leon eleon23 654889611
    CS 478 Assignment 3
    This is a custom Broadcast Receiver that will receiver information from App 3 when it is sent
 */
public class myBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("BroadcastReceiver3 app", "Programmic Receiver 1 called into action.") ;

        //get the phone web page sent through the intent from App 3
        String phonePage = intent.getStringExtra("com.example.phoneNameWeb");
        //URI parse the info
        Uri infoPage = Uri.parse(phonePage);

        //Create intent and launch it with the specific phone site received
        Intent web = new Intent(Intent.ACTION_VIEW, infoPage );
        context.startActivity(web);



    }

}
