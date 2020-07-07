package com.cipher.covid19diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class dashboard extends AppCompatActivity
{
    private CardView profile,add,diary,med;
    private TextView name;
    private ImageView log_out;
    private String uid;

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;

    private void on_logout()
    {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid",null);
        editor.commit();

        Intent intent = new Intent(dashboard.this,LoginActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
    }

    private void readData()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                name.setText(document.getString("name"));
                                //Log.d("log_data", document.getId() + " => " + document.getData());
                            }
                        }
                        else
                        {
                            Log.w("log_data", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);

        profile=findViewById(R.id.dash_profile);
        add = findViewById(R.id.dash_emer_mode);
        diary = findViewById(R.id.dash_maps);
        med = findViewById(R.id.bcam);
        name=findViewById(R.id.name_dash);
        log_out= findViewById(R.id.logout);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        readData();

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new AlertDialog.Builder(dashboard.this)
                        .setTitle("Really Logout?")
                        .setMessage("Are you sure you want to logout?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1)
                            {
                                on_logout();
                            }
                        }).create().show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(dashboard.this,profile.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(dashboard.this,add.class);
                startActivity(intent);
            }
        });

        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(dashboard.this,diary.class);
                startActivity(intent);
            }
        });

        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:04712309250"));
                startActivity(intent);
            }
        });
    }
}
