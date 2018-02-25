package com.rrsaikat.Myappgit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Singlepost extends AppCompatActivity {
    String poost_key=null;
    ImageView postimage;
    TextView title,desc;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepost);

        postimage=(ImageView) findViewById(R.id.singlepostimage);
        title=findViewById(R.id.singleposttitle);
        desc=findViewById(R.id.singlepostdesc);
        poost_key= getIntent().getExtras().getString("post_id");
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Post");
        mDatabase.child(poost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               String posttitle=(String)dataSnapshot.child("posttitle").getValue();
                String postdescription=(String)dataSnapshot.child("postdesc").getValue();
                String postimage1=(String)dataSnapshot.child("postimage").getValue();


              //now set the value to the signle page elements
                title.setText(posttitle);
                desc.setText(postdescription);
                Picasso.with(Singlepost.this).load(postimage1).into(postimage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(Singlepost.this,"Something went wrong..",Toast.LENGTH_SHORT).show();


            }
        });


    }
}
