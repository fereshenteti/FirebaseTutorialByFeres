package com.henteti.feres.firebasetutorialbyferes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.henteti.feres.firebasetutorialbyferes.Singleton.Singleton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //region Firebase variables
    private FirebaseAuth mAuth;

    //endregion

    private AppCompatEditText email, pwd;
    private AppCompatButton create, connectMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.this.setTitle("Login or Create account");
        setContentView(R.layout.activity_main);

        //initializing connexion with firebase
        initFirebase();

        //init view
        initView();

    }

    private void initView() {

        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        create = findViewById(R.id.create);
        connectMail = findViewById(R.id.connectMail);
        create.setOnClickListener(this);
        connectMail.setOnClickListener(this);

    }

    private void initFirebase() {

        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create:
                signUpWithMailandPwd();
                break;
            case R.id.connectMail:
                loginWithMailandPwd();
                break;
        }
    }

    private void loginWithMailandPwd() {

        if(email.getText()!= null && pwd.getText() != null
                && !email.getText().toString().isEmpty()
                && !pwd.getText().toString().isEmpty()){
        mAuth.signInWithEmailAndPassword(email.getText().toString(), pwd.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // User is signed in
                                Singleton.getInstance(MainActivity.this).setUser(user);
                                startActivity(new Intent(MainActivity.this, Welcome.class));
                            } else {
                                // No user is signed in
                                Toast.makeText(MainActivity.this, "Something went wrong! Please try again.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Something went wrong! Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
        }else {
            Toast.makeText(MainActivity.this, "Please type your email and your password correctly!",
                    Toast.LENGTH_SHORT).show();
        }


    }

    private void signUpWithMailandPwd() {

        if(email.getText()!= null && pwd.getText() != null
                && !email.getText().toString().isEmpty()
                && !pwd.getText().toString().isEmpty()){
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), pwd.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                // User is signed in
                                Singleton.getInstance(MainActivity.this).setUser(user);
                                startActivity(new Intent(MainActivity.this, Welcome.class));
                            } else {
                                // No user is signed in
                                Toast.makeText(MainActivity.this, "Something went wrong! Please try again.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            //1) register the user in the shared preferences to maintain his connexion
                            //for next openings of the app

                            //2) share the user's infos with the singleton so it can be recognized in
                            //the other activities of the app

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Something went wrong! Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
        }else {
            Toast.makeText(MainActivity.this, "Please type your email and your password correctly!",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
