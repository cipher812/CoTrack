package com.cipher.covid19diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class sinup extends AppCompatActivity
{
    EditText name,age,address,mail,bg,pass;
    Button signup;
    String uid;

    private FirebaseAuth mAuth;

    private void signup(String uid)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("name", name.getText().toString());
        user.put("age", age.getText().toString());
        user.put("adress", address.getText().toString());
        user.put("email", mail.getText().toString());
        user.put("blood_group",bg.getText().toString());
        user.put("id", uid);

        db.collection(uid).document("details").set(new HashMap<Array, Object>())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e)
            {
                Toast.makeText(sinup.this,"Entry added failed",Toast.LENGTH_LONG).show();
            }
        });

        db.collection(uid).document("personal").set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        //Toast.makeText(add.this,"Entry added",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(sinup.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e)
            {
                Toast.makeText(sinup.this,"Entry added failed",Toast.LENGTH_LONG).show();
            }
        });

        /*db.collection(uid).document("details").set(new HashMap<Array, Object>())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        //Toast.makeText(sinup.this,"Entry added",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e)
            {
                Toast.makeText(sinup.this,"Entry added failed",Toast.LENGTH_LONG).show();
            }
        });

        db.collection(uid).document("details").set(new HashMap<Array, Object>())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        //Toast.makeText(sinup.this,"Entry added",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e)
            {
                Toast.makeText(sinup.this,"Entry added failed",Toast.LENGTH_LONG).show();
            }
        });

        db.collection(uid)
                .document("personal")
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Toast.makeText(sinup.this, "Success! Kindly sign in.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(sinup.this,LoginActivity.class);
                        startActivity(intent);
                    }
                });*/
    }

    private void auth(String email,String password)
    {
        mAuth= FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid=user.getUid();
                            signup(user.getUid());
                            Toast.makeText(sinup.this, "Success! Kindly sign in. Id:"+uid, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {// If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(sinup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinup);

        name=findViewById(R.id.sname);
        age=findViewById(R.id.sage);
        address=findViewById(R.id.sadr);
        mail=findViewById(R.id.semail);
        bg=findViewById(R.id.sbl);
        pass=findViewById(R.id.spass);
        signup=findViewById(R.id.slog);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                auth(mail.getText().toString(),pass.getText().toString());
            }
        });

    }
}
