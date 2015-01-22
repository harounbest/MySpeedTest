package com.num.myspeedtest.view.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.num.myspeedtest.Constants;
import com.num.myspeedtest.R;
import com.num.myspeedtest.controller.utils.DeviceUtil;


public class MainActivity extends ActionBarActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedpreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        if (!sharedpreferences.contains("terms_and_conditions") || !sharedpreferences.getBoolean("terms_and_conditions", false))
        {
            finish();
            Intent myIntent = new Intent(getApplicationContext(), TermsAndConditionsActivity.class);
            startActivity(myIntent);
        }

        setContentView(R.layout.activity_main);

        LinearLayout throughputButton, latencyButton, tracerouteButton, dataUsageButton,
                configureButton, aboutUsButton;

        activity = this;

        /* Check Internet Connection */
        if(!DeviceUtil.getInstance().isInternetAvailable(this)) {
            new AlertDialog.Builder(this)
                    .setTitle("Internet Warning")
                    .setMessage("You are not currently connected to the Internet. Some features may not work.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        throughputButton = (LinearLayout) findViewById(R.id.main_button_throughput);
        throughputButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(activity, ThroughputActivity.class);
                startActivity(i);
            }
        });

        latencyButton = (LinearLayout) findViewById(R.id.main_button_latency);
        latencyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(activity, LatencyActivity.class);
                startActivity(i);
            }
        });

        dataUsageButton = (LinearLayout) findViewById(R.id.main_button_data_usage);
        dataUsageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, DataUsageActivity.class);
                startActivity(i);
            }
        });

        tracerouteButton = (LinearLayout) findViewById(R.id.main_button_traceroute);
        tracerouteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(activity, TracerouteActivity.class);
                startActivity(i);
            }
        });

        configureButton = (LinearLayout) findViewById(R.id.main_button_configure);
        configureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, SettingsActivity.class);
                startActivity(i);
            }
        });

        aboutUsButton = (LinearLayout) findViewById(R.id.main_button_about_us);
        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, AboutUsActivity.class);
                startActivity(i);
            }
        });
    }
}