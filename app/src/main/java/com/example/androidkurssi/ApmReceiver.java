package com.example.androidkurssi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ApmReceiver extends BroadcastReceiver {
    public static final String TAG = "ApmReceiver";
    String msg;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        boolean state = intent.getBooleanExtra("state", false);

        if (state == true){
            Log.d(TAG,"nyt true airplane mode päälle");
            msg = context.getResources().getString(R.string.airPlaneModeOn);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d(TAG,"nyt false airplane pois päältä");
            msg = context.getResources().getString(R.string.airPlaneModeOff);
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }



}