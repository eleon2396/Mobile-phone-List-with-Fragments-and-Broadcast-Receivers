package com.example.eleon.application3eleon;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/* Eric Leon eleon23 654889611
    CS 478 Assignment 3
    This class is designed to hold the phone image fragment.
 */

public class phoneImageFragment extends Fragment {

    private static final String TAG = "PhoneImageFragment";
    //Important variabels needed
    //  1. an imageview 2. the current index 3. imageArray length
    private ImageView phoneImage;
    private int currentIndex = -1;
    private int imageArrayLength;

    //get the current index
    int getShowIndex(){
        return currentIndex;
    }

    //show the image at the certain index
    void showImageAtIndex(int newIndex){
        //check to make sure the index is valid
        if(newIndex < 0 || newIndex >= imageArrayLength){
            return;
        }
        //set the new current index and set the image resource
        currentIndex = newIndex;
        phoneImage.setImageResource(MainActivity.phoneImages[currentIndex]);
    }

    @Override
    public void onAttach(Context activity){
        super.onAttach(activity);
    }

    //set the retain instance to true
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    //inflate the layout of the image view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        super.onCreateView(inflater, container, savedInstanceBundle);

        return inflater.inflate(R.layout.phoneimageslayout, container, false);

    }

    //get the image view and set the length of the image array
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        phoneImage = (ImageView) getActivity().findViewById(R.id.phoneImageView);
        imageArrayLength = MainActivity.phoneImages.length;

    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
