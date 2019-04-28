package com.example.eleon.application3eleon;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.media.Image;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

/* Eric Leon eleon23 654889611
    CS 478 Assignment 3
    This is Application 3
    This application holds majority of the code which includes the fragments.
    This Application will have 2 fragments, one list fragment and another image fragment.
        1. The first fragment is a list fragment which will have the names of phones that the user can select
        2. The Second Fragment will hold an image fragment that will display the phone the user selected
    This app will also have an tool bar with options menu where:
        1. The user will be able to send a broadcast which will launch App 1 and App 2
        2. The user will be able to exit App 3
    Another feature of this app is that it will retain the fragment instances so that the image of the phone and
    the list selection will not be lost during vertical or horizontal configurations

    This application also defines the permission used throughout the program in App 1, App 2, App 3.

    ****NOTE: THIS CODE IS BASED OFF PROFESSOR UGO BUY'S CODE FROM "FRAGMENTS DYNAMIC LAYOUT" APPLICATION****
 */

public class MainActivity extends AppCompatActivity implements phoneNameFragment.ListSelectionListener {

    //Create arrays that will hold string that I set up in my string resources along with an image array
    // to hold all my drawables
    public static String[] phoneNamesArray;
    public static int[] phoneImages;
    public static String[] phoneSites;

    //create two fragments that will be used for this application
    private phoneImageFragment mPhoneImageFragment = new phoneImageFragment();
    private phoneNameFragment mPhoneNameFragment = new phoneNameFragment();

    //Reference to the Fragment manager
    private FragmentManager mFragmentManager;

    //Layouts that will be used in the creation of the fragments
    private FrameLayout phoneNameLayout, phoneImageLayout;

    //Variable to be used when fixing the layouts the fragments
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    //Tag for console printing
    private static final String TAG = "MainActivity";

    //Specific string that is used in App 1 and App 2, this will help facilitate the broadcasting
    private static final String A3_INTENT="com.example.eleon.A3INTENT";
    //Permission used with the broadcast receiver
    private String myPermission = "edu.uic.cs478.s19.kaboom";

    //Int to store the index of the currently selected phone
    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, getClass().getSimpleName() + ": entered onCreate()");

        //Get the string array resource and store the names into the array
        phoneNamesArray = getResources().getStringArray(R.array.phoneNames);
        Log.i("Array", phoneNamesArray[0]);

        //Initialize the images array with the drawbles
        phoneImages = new int[]{
                R.drawable.iphone8plus, R.drawable.iphonexsmax, R.drawable.iphonexr, R.drawable.galaxys10, R.drawable.galaxys9, R.drawable.samsungnote9
        };

        //Get the string array resource and store the websites into the array
        phoneSites = getResources().getStringArray(R.array.phoneWebsites);

        //Set up the tool bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Get the phone name container and the phone image container
        phoneNameLayout = (FrameLayout) findViewById(R.id.phone_names_container);
        phoneImageLayout = (FrameLayout) findViewById(R.id.phone_image_container);

        //set up the fragment mananger
        mFragmentManager = getSupportFragmentManager();

        //Begin the fragment transction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        //Set up the container with the fragment list that contains phone names
        fragmentTransaction.replace(R.id.phone_names_container, mPhoneNameFragment);

        //commit the fragment transaction
        fragmentTransaction.commit();

        //add an action listener for when the back stack changes and it will change the layout
        mFragmentManager.addOnBackStackChangedListener(
                // UB 2/24/2019 -- Use support version of Listener
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });


    }

    //In the onSaveInstanceState call back, store the index and boolean determining if the phone image
    // fragment is currently attached
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        //save the index and put it into the bundle
        savedInstanceState.putInt("SavedPhone", selectedIndex);
        //check if the image fragment is added if so set a "True" boolean
        if(mPhoneImageFragment.isAdded()) {
            savedInstanceState.putBoolean("SavedState", true);
        }
        //else set it to false
        else{
            savedInstanceState.putBoolean("SavedState", false);
        }


    }

    //In the onRestoreInstanceState, will get the selected index and save which phone was selected then display it
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        //check if the boolean is true
        if(savedInstanceState.getBoolean("SavedState")){
            //if it is then display that list item
            onListSelection(savedInstanceState.getInt("SavedPhone"));
        }

    }

    //Function to start Application 1 and Application 2, takes in a phone index
    private void startA1ANDA2(int phone){
        //Create intent with specific intent string
        Intent startA1A2 = new Intent(A3_INTENT);

        //check if a phone has been selected from the list fragment
        if(selectedIndex != -1) {
            //get the phone name
            String phoneName = phoneNamesArray[selectedIndex];
            //get the phone website
            String phoneNameWebsite = phoneSites[selectedIndex];
            //put in the phone name and website as extras in the intent
            startA1A2.putExtra("com.example.phoneName", phoneName);
            startA1A2.putExtra("com.example.phoneNameWeb", phoneNameWebsite);
            //send out the ordered broadcast, with Application 2 with a priority of 100, and Application 1 with a priority of 10,
            // Thus Application 2 will run first then Application 1 will run
            sendOrderedBroadcast(startA1A2, myPermission);
        }
        //Else display an error toast if a phone was not selected
        else{
            Toast.makeText(getApplicationContext(), "ERROR NO PHONE SELECTED! ",
                    Toast.LENGTH_LONG).show() ;
        }

    }

    //Function to inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.mymenu, menu);
        return true;
    }

    //Function to handle menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //Switch statement for the menu options
        switch(item.getItemId()){
            // if the user selects the first option, which starts application 1 and application 2
            case R.id.startA1A2:
                startA1ANDA2(0);
                return true;
            //If the user selects to exit
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //Function to set up the layouts for the fragment depending on the orientation of the phone
    private void setLayout(){

        //If the orientation is vertical
        if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            //If the image fragment is not added
            if (!mPhoneImageFragment.isAdded()) {
                Log.i("SetLayout", "In layout method");
                //set the list fragment to be the only one displayed
                phoneNameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                //set the image fragment to hidden
                phoneImageLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        0, 0));

            }
            //Else the image fragment is added
            else {
                //set the list fragment to hidden
                phoneNameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
                //set the image fragment to take over the screen
                phoneImageLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            }

        }

        //the phone is horizontal
        else {
            //if the image fragment is not added
            if (!mPhoneImageFragment.isAdded()){
                //set the list fragment to take over the screen
                phoneNameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                //set the image fragment to hidden
                phoneImageLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        0, 0));

            }
            //Else it is added to the screen
            else {
                //set the list fragment to 1/3 of the screen
                phoneNameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, 2));
                //set the image fragment to 2/3 of the screen
                phoneImageLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1));
            }

        }
    }

    //The on list selection that takes in an index
    @Override
    public void onListSelection(int index){

        //updated the global index and store what is currently selected
        selectedIndex = index;

        //check if the image fragment is added
        if(!mPhoneImageFragment.isAdded()){

            //start the transction process
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            //add the image fragment to the iamge container
            fragmentTransaction.add(R.id.phone_image_container, mPhoneImageFragment);

            //update the back stack
            fragmentTransaction.addToBackStack(null);

            //Commit the transction
            fragmentTransaction.commit();
            //Execute the pending transactions
            mFragmentManager.executePendingTransactions();
            Log.i("Transaction", "Executed");
        }

        //show the image at the index
        mPhoneImageFragment.showImageAtIndex(index);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onRestart()");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }


}
