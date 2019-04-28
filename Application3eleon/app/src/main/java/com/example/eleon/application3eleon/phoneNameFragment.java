package com.example.eleon.application3eleon;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/* Eric Leon eleon23 654889611
    CS 478 Assignment 3
    This class is for the List fragment which will hold all the list items
 */

public class phoneNameFragment extends ListFragment {

    private static final String TAG = "PhoneNameFragment";

    //create the List listener
    private ListSelectionListener mListener = null;

    //create the interface for the list listener
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    //create the onlist item click function to listen to user selection
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id){
        getListView().setItemChecked(pos, true);
        mListener.onListSelection(pos);
    }

    //set the listener to the current activity
    @Override
    public void onAttach(Context activity){
        super.onAttach(activity);

        try{

            mListener = (ListSelectionListener)activity;
            Log.i("Attached", "Attached");
        }
        catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setRetainInstance(true);
    }

    //inflate the list fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //super.onCreateView(inflater, container, savedInstanceState);
        Log.i("Inflate", "Inflated");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //set the list adapter with strings from the phone names array
    @Override
    public void onActivityCreated(Bundle savedState){
        super.onActivityCreated(savedState);

        //create list adapter to store all the strings and display to the user
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.phonenamelayout, MainActivity.phoneNamesArray));
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        Log.i("Created", "Created List");
    }


    @Override
    public void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDetach()");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
        super.onDestroyView();
    }



}
