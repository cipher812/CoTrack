package com.cipher.covid19diary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity
{
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;

    private FirebaseAuth mAuth;
    private String uid;
    private EditText txtUname,txtPass;
    private Button btnLogin;
    private ProgressBar prg;
    private TextView signx;

    private void login_status(String email,String pass)
    {
        mAuth= FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid=user.getUid();
                            //dash.putExtra("uid",uid);
                            sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("uid",uid);
                            editor.commit();

                            Toast sucess = Toast.makeText(getApplicationContext(),"Sucess Id:"+uid,Toast.LENGTH_SHORT);
                            sucess.show();
                            prg.setVisibility(View.INVISIBLE);
                            Intent dash = new Intent(LoginActivity.this,dashboard.class);
                            startActivity(dash);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_LONG).show();
                            prg.setVisibility(View.INVISIBLE);
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
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUname=findViewById(R.id.username);
        txtPass=findViewById(R.id.password);
        btnLogin=findViewById(R.id.login);
        prg=findViewById(R.id.prgbar);
        signx=findViewById(R.id.signup);
    }

    protected void onStart()
    {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uid=sharedPreferences.getString("uid",null);

        super.onStart();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                prg.setVisibility(View.VISIBLE);
                login_status(txtUname.getText().toString(),txtPass.getText().toString());
            }
        });

        signx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent dash = new Intent(LoginActivity.this,sinup.class);
                startActivity(dash);
            }
        });
    }
}
