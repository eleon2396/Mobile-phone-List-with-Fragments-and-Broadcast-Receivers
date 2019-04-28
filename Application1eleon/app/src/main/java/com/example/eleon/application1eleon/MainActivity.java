package com.example.eleon.application1eleon;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/* Eric Leon eleon23 654889611
    CS 478 Assignment 3
    This is Application 1. This app has two activities and one broadcast receiver.
    In this app, a user will press the button which will check for a specific user permission.
        1. If the permission is granted it will create a broadcast receiver and start up Application 2
        2. If the permission is NOT granted it will ask the user for a permission confirmation, when granted it
            will have the same behavior as in situation 1.
 */

public class MainActivity extends AppCompatActivity {

    //Special unique permission, also set up in the Manifest file since this App uses this permission
    private String myPermission = "edu.uic.cs478.s19.kaboom";
    //String to specify for the broadcast receiver
    private static final String A3_INTENT="com.example.eleon.A3INTENT";

    //Broadcast receiver and filter that will be used
    private BroadcastReceiver mReceiver1;
    private IntentFilter mfilter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the button the user will click on
        Button checkPerm = (Button)findViewById(R.id.checkA1permButton);
        //add an action listener tot he button
        checkPerm.setOnClickListener(new View.OnClickListener() {
            //calls function that will handle all the permission checking
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });

    }

    //Function that will check for the permissions of the application
    private void checkPermissions(){
        //if the permission is granted it will set up the broadcast receiver and start Application 2
        //If the permission is NOT granted it will ask the user to give it permission
        if (ContextCompat.checkSelfPermission(getApplicationContext(), myPermission) == PackageManager.PERMISSION_GRANTED) {

            receiverANDStart();
        }
        else {
            //ask the user for permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{myPermission}, 0) ;
        }
    }

    //Function for when the permission is granted
    private void receiverANDStart(){
        //set up the intent filter with specific string
        mfilter1 = new IntentFilter(A3_INTENT) ;
        //set its Priority level to 10
        mfilter1.setPriority(10) ;
        //set up the Broadcast receiver to my own broadcast receiver class
        mReceiver1 = new myBroadcastReceiver() ;
        //Register the filter and receiver to receive info from App 3 later on
        registerReceiver(mReceiver1, mfilter1) ;

        //Create Intent and Launch up Application 2
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.eleon.application2eleon");
        startActivity(intent);

    }

    //Function that will check for the permission check from when the App doesnt have the permission and granted and
    // must ask the user for permission
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results){

        //Check the length of the results
        if(results.length > 0){
            //if the permission was granted then continue as usual
            if(results[0] == PackageManager.PERMISSION_GRANTED){
                receiverANDStart();
            }
            //Else permission was NOT granted then toast
            else{
                Toast.makeText(this, "That sucks! No permission", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    //At the end unregister the broadcast receiver.
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver1);
    }

}
