package com.rrsaikat.Myappgit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Hallfinder extends AppCompatActivity implements View.OnClickListener{

    private Button mSignOutButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hallfinder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSignOutButton = (Button) findViewById(R.id.sign_out_button);
        mSignOutButton.setOnClickListener(this);


    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_out_button:
                signOut();
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                break;
        }
    }
}
