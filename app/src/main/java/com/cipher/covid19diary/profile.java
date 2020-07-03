package com.cipher.covid19diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class profile extends AppCompatActivity
{
    private String uid;
    TextView name,mail,xuid,age,dob,med,xb,adr;

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;

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
                                if (document.getId().equals("personal"))
                                {
                                    name.setText(document.getString("name"));
                                    mail.setText(document.getString("email"));
                                    xuid.setText(uid);
                                    age.setText(document.getDouble("age").toString());
                                    dob.setText(document.getDate("dob").toString());
                                    med.setText(document.getString("mecial_condition"));
                                    xb.setText(document.getString("blood_group"));
                                    adr.setText(document.getString("adress"));
                                }
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);

        name=findViewById(R.id.xname);
        mail=findViewById(R.id.xemail);
        xuid=findViewById(R.id.xuid);
        age=findViewById(R.id.xage);
        dob=findViewById(R.id.xdob);
        med=findViewById(R.id.xmed);
        xb=findViewById(R.id.xb);
        adr=findViewById(R.id.xadr);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        readData();
    }
}
