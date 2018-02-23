package com.rrsaikat.Myappgit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserDetails extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText name,emal,password,address,sex;
    TextView textView;
    Button bttn,logoutbt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        name= (EditText) findViewById(R.id.name1);
        emal= (EditText) findViewById(R.id.email1);
        password= (EditText) findViewById(R.id.password1);
        textView= (TextView) findViewById(R.id.textff);
        address= (EditText) findViewById(R.id.address1);
        sex= (EditText) findViewById(R.id.sex1);
        bttn= (Button) findViewById(R.id.save1);
        logoutbt= (Button) findViewById(R.id.logoutbttn);
        logoutbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });




        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                     mAuth=  FirebaseAuth.getInstance();
                if (mAuth.getCurrentUser()!=null){

                    String userid=mAuth.getCurrentUser().getUid();
                    String usermobile=mAuth.getCurrentUser().getPhoneNumber();


                    Toast.makeText(UserDetails.this,"userid "+userid+"mobile: " +usermobile,Toast.LENGTH_LONG).show();


                    FirebaseDatabase firebaseDataba=FirebaseDatabase.getInstance();
                    DatabaseReference current_user_db=FirebaseDatabase.getInstance().getReference(usermobile).child("User Information");
                    String username=name.getText().toString();
                    String useremail=emal.getText().toString();
                    String userpass=password.getText().toString();
                    String useraddress=address.getText().toString();
                    String usersex=sex.getText().toString();
                    Map<String, String> newPost= new HashMap<String, String>();

                    newPost.put("Name",username);
                    newPost.put("Phone",usermobile);
                    newPost.put("Password",userpass);
                    newPost.put("Email",useremail);
                    newPost.put("Address",useraddress);
                    newPost.put("Sex",usersex);

                    current_user_db.setValue(newPost);
                    expert();



                }

                else
                {
                    Toast.makeText(UserDetails.this,"userid is null",Toast.LENGTH_LONG).show();



                }

            }
        });


    }

    public void expert(){

        Intent intent=new Intent(UserDetails.this,Createpost.class);
        startActivity(intent);
        finish();
    }



    public void logout()
    {
        FirebaseAuth.getInstance().signOut();

        Intent intent=new Intent(UserDetails.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
