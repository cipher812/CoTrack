package com.cipher.covid19diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class diary extends AppCompatActivity
{
    String uid;


    private ListView listView;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String[] dates ={"1","2","3","4","5","1","2","3","4","5","1","2","3","4","5"}, location={"1","2","3","4","5","1","2","3","4","5","1","2","3","4","5"}, xppls={"1","2","3","4","5","1","2","3","4","5","1","2","3","4","5"}, vehicles={"1","2","3","4","5","1","2","3","4","5","1","2","3","4","5"};

    /*private void SetLocation(String[] Data)
    {
        this.location=Data;
    }
    private void SetDate(String[] Data)
    {
        this.dates=Data;
    }
    private void SetPpl(String[] Data)
    {
        this.xppls=Data;
    }
    private void SetVeh(String[] Data)
    {
        this.vehicles=Data;
        try
        {
            Thread.sleep(2000);
            setDatax();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Error please try again",Toast.LENGTH_SHORT).show();
        }
    }

    private void initLocation()
    {
        db.collection(uid).document("details").collection("locationx").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                //int i=0;
                                if(document.getId().equals("1"))
                                {
                                    String values[]=document.getData().values().toArray(new String[0]);
                                    Log.d("ASDX",values[0]);
                                    SetLocation(values);
                                }
                            }
                        }


                    }
                });
    }

    private void initDate()
    {
        db.collection(uid).document("details").collection("time").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                //int i=0;
                                if(document.getId().equals("1"))
                                {
                                    String values[]=document.getData().values().toArray(new String[0]);
                                    Log.d("ASDX",values[0]);
                                    SetDate(values);
                                }
                            }
                        }
                    }
                });
    }

    private void initppl()
    {

        db.collection(uid).document("details").collection("people").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                //int i=0;
                                if(document.getId().equals("1"))
                                {
                                    String values[]=document.getData().values().toArray(new String[0]);
                                    Log.d("ASDX",values[0]);
                                    SetPpl(values);
                                }
                            }
                        }


                    }
                });
    }*/

    private void initData()
    {
        db.collection(uid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                if(document.getId().equals("details"))
                                {
                                    //Datax datax = document.toObject(Datax.class);
                                    List<Map<String, Object>> data= (List<Map<String, Object>>) document.get("vehicle");
                                    vehicles[i]=data.toString();

                                    List<Map<String, Object>> data2= (List<Map<String, Object>>) document.get("time");
                                    dates[i]=data2.toString();

                                    List<Map<String, Object>> data3= (List<Map<String, Object>>) document.get("people");
                                    xppls[i]=data3.toString();

                                    List<Map<String, Object>> data4= (List<Map<String, Object>>) document.get("location");
                                    location[i]=data4.toString();

                                    Log.d("TAG",location[0]);
                                }
                                i++;
                            }
                        }
                        setDatax();
                    }
                });
    }

    private void setDatax()
    {
        /*Log.d("ASDX",dates[0]);
        Log.d("ASDX",xppls[0]);
        Log.d("ASDX",vehicles[0]);
        Log.d("ASDX",location[0]);*/

        ExpandableListAdapter expandableListAdapter=new ExpandableListAdapter(this,dates,location,xppls,vehicles);
        listView.setAdapter(expandableListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);

        listView=findViewById(R.id.expv);
    }

    @Override
    protected void onStart()
    {

        super.onStart();
        try
        {
            initData();
            //Log.d("ASDF",Arrays.toString(dates));
        }
        catch (NullPointerException e)
        {
          Toast.makeText(this,"Error please try again",Toast.LENGTH_SHORT).show();
        }

    }
}
