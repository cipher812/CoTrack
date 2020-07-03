package com.cipher.covid19diary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class add extends AppCompatActivity
{
    private String uid,dat,tim,xloc="Waiting for auto-lock";
    private EditText date,time,loc,ppl,vnol;
    private Button addx;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private FusedLocationProviderClient fusedLocationClient;


    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;

    private void SendData()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String utime=DateFormat.getTimeInstance().format(new Date());

        Map<String, Object> time = new HashMap<>();
        time.put("time "+ utime, dat+" "+tim);

        Map<String, Object> location = new HashMap<>();
        location.put("location "+ utime, loc.getText().toString());

        Map<String, Object> people = new HashMap<>();
        people.put("presons "+ utime, ppl.getText().toString());

        Map<String, Object> vehicle = new HashMap<>();
        vehicle.put("vehicle "+ utime, vnol.getText().toString());

        db.collection(uid).document("details").collection("time").document("1").set(time)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                //Toast.makeText(add.this,"Entry added",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e)
                    {
                        Toast.makeText(add.this,"Entry added failed",Toast.LENGTH_LONG).show();
                    }
                });

        db.collection(uid).document("details").collection("location").document("1").set(location)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        //Toast.makeText(add.this,"Entry added",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e)
            {
                Toast.makeText(add.this,"Entry added failed",Toast.LENGTH_LONG).show();
            }
        });

        db.collection(uid).document("details").collection("people").document("1").set(people)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        //Toast.makeText(add.this,"Entry added",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e)
            {
                Toast.makeText(add.this,"Entry added failed",Toast.LENGTH_LONG).show();
            }
        });

        db.collection(uid).document("details").collection("vehicle").document("1").set(vehicle)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Toast.makeText(add.this,"Entry added",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e)
            {
                Toast.makeText(add.this,"Entry added failed",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getDate()
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        dat=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        date.setText(dat);
                        Log.d("datx",dat);
                        //date.setText(dat);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void getTime()
    {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {

                        tim=hourOfDay + ":" + minute;
                        time.setText(tim);
                        Log.d("timex",tim);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void getLocation()
    {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location)
                    {
                        // Got last known location. In some rare situations this can be null.
                        if(location != null)
                        {
                            xloc = "N" + " " + location.getLatitude() + "," + "E" + " " + location.getLongitude();
                            Log.d("ccc", location.getAltitude() + "");
                        }
                        else
                        {
                            loc.setText("Insert manually");
                        }
                    }
                });

        loc.setText(xloc);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //getLocation();

        date=findViewById(R.id.axdate);
        time=findViewById(R.id.atime);
        loc=findViewById(R.id.aadr);
        ppl=findViewById(R.id.appl);
        vnol=findViewById(R.id.aveh);
        addx=findViewById(R.id.badd);

        getLocation();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getLocation();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getDate();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getTime();
            }
        });

        addx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendData();
            }
        });
    }

}
