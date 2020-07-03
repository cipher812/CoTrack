package com.cipher.covid19diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity
{
    private String uid = null;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;

    private boolean checkPlayServices()
    {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (apiAvailability.isUserResolvableError(resultCode))
            {
                apiAvailability.getErrorDialog(this, resultCode, 9000);
            }
            else
            {
                finish();
            }

            return false;
        }
        return true;
    }

    private void get_permssion()
    {
        int perm=0;

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Please enable the necessary permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, perm);
        }
        else if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},perm);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "All permissions granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);

        final int time_out = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                if (uid==null)
                {
                    Intent home = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(home);
                    finish();
                }
                else
                {
                    Intent home = new Intent(MainActivity.this, dashboard.class);
                    startActivity(home);
                    finish();
                }
            }
        }, time_out);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        checkPlayServices();
        get_permssion();
    }

}
