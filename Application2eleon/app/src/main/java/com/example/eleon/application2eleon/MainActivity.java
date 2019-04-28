package com.example.eleon.application2eleon;

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
import android.widget.Toast;

/* Eric Leon eleon23 654889611
    CS 478 Assignment 3
    This is application 2 which is very similar to application 1
    This application will NOT start if application 1 does NOT have permission
    If Application 1 does have permission, then Application 2 will start with a message and button
        1. When the Button is press the application will check if the specific permission is granted,
            if it is then it will set up its own broadcast receiver and start application 3
        2. If the permission si NOT granted then it will ask for it in a similar manner to App 1.
            a. if the permission is NOT granted at this point the application will exit, toast a message, and return to App 1
            b. Else if the permission is granted it will continue the same steps in Situation 1.
 */
public class MainActivity extends AppCompatActivity {

    //In the manifest, this application also uses the specific permission like in App 1
    //Specific Unique permission that will be used
    private String myPermission = "edu.uic.cs478.s19.kaboom";

    //unique String that will be used to set up the broadcast receiver
    private static final String A3_INTENT="com.example.eleon.A3INTENT";

    //Delcare the Receiver and intent filter to be used later on
    private BroadcastReceiver mReceiver2;
    private IntentFilter mfilter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button that the user will click on
        Button checkButton = (Button)findViewById(R.id.checkA2permButton);
        //Add an event listener to the button
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
            }
        });

    }

    //Function to check the permissions
    private void checkPermissions(){
        //If the permission is granted then set up the broadcast receiver and start application 3
        if(ContextCompat.checkSelfPermission(getApplicationContext(), myPermission) == PackageManager.PERMISSION_GRANTED){
            receiverAndStart();
        }
        //Else the Permission were NOT granted then ask the user to allow them
        else{
            //Asks the user and waits for result
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{myPermission}, 0);
        }
    }

    //Function for when the user has granted the permission
    private void receiverAndStart(){
        //Set up the new intent filter with the specific string for app 3
        mfilter2 = new IntentFilter(A3_INTENT);
        //Set its priority to 100
        mfilter2.setPriority(100);
        //Set up the new custom broadcast receiver
        mReceiver2 = new myBroadcastReceiver2();
        //register it to wait for information from app 3
        registerReceiver(mReceiver2, mfilter2);

        //Create a new intent and launch Application 3
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.eleon.application3eleon");
        startActivity(intent);
    }

    //Function that will wait for user response regarding the unique permission
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results){
        //If the length is greater than 0
        if(results.length > 0){
            //check if the permission was granted
            if(results[0] == PackageManager.PERMISSION_GRANTED){
                //Call function to set up receiver and launch app 3
                receiverAndStart();
            }
            //Else it wasnt granted then toast and close app 2
            else{
                Toast.makeText(this, "That sucks! No permission", Toast.LENGTH_SHORT)
                        .show();
                finish();
            }
        }
    }

    //Unregister the broadcast receiver in the end
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver2);
    }

}
