package com.cipher.covid19diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class diary extends AppCompatActivity
{
    String uid;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;

    private void initData()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Details");
        final List<String> Closed_Loans = new ArrayList<>();

        db.collection(uid)
                .document("detail").collection("location").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d("X", document.getId() + " => " + document.getData());
                                Closed_Loans.add(document.getId() + " => " + document.getData());
                            }
                        }
                        else
                        {
                            Log.w("X", "Error getting documents.", task.getException());
                        }
                    }
                });

        listHash.put(listDataHeader.get(0), Closed_Loans);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        initData();
    }
}
