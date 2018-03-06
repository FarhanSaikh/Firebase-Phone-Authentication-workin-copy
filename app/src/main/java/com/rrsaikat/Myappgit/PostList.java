package com.rrsaikat.Myappgit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PostList extends AppCompatActivity {


RecyclerView mpostlist;
DatabaseReference mDatabse;
FirebaseAuth mAuth;
private LinearLayoutManager mLayoutManager;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Learn and Earn");
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);



        mpostlist=(RecyclerView) findViewById(R.id.recyclerview);
        mpostlist.setHasFixedSize(true);
       // mpostlist.setLayoutManager(new LinearLayoutManager(this));
        mLayoutManager = new LinearLayoutManager(PostList.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        // Now set the layout manager and the adapter to the RecyclerView
        mpostlist.setLayoutManager(mLayoutManager);
        mAuth=FirebaseAuth.getInstance();
        String usermobile=mAuth.getCurrentUser().getPhoneNumber();
        mDatabse= FirebaseDatabase.getInstance().getReference().child("Post");
        //floating button code is here..
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Redirecting to New Activity", Snackbar.LENGTH_LONG);

                Intent intent=new Intent(PostList.this,Createpost.class);
                startActivity(intent);

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Post,PostViewFolder> PSVF=new FirebaseRecyclerAdapter<Post, PostViewFolder>(
                Post.class,
                R.layout.singlepostitem,
                PostViewFolder.class,
                mDatabse) {
            @Override
            protected void populateViewHolder(PostViewFolder viewHolder, Post model, int position) {

                viewHolder.setTitle(model.getPosttitle());
                viewHolder.setDesc(model.getPostdesc());
                viewHolder.setImage(getApplicationContext(),model.getPostimage());

                final String post_key=getRef(position).getKey().toString();
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(PostList.this,Singlepost.class);
                        intent.putExtra("post_id",post_key);
                        startActivity(intent);


                    }
                });

            }
        };

        mpostlist.setAdapter(PSVF);


    }


    public static class PostViewFolder extends RecyclerView.ViewHolder{

        View mview;
        public PostViewFolder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setTitle(String posttitle){
            TextView post_title=(TextView) mview.findViewById(R.id.posttitle);
                post_title.setText(posttitle);

        }

        public void setDesc(String postdesc){
            TextView post_desc=(TextView) mview.findViewById(R.id.postdescription);
            post_desc.setText(postdesc);


        }
        public void setImage(Context ctx, String postimage){

            ImageView post_image=(ImageView) mview.findViewById(R.id.postimage);

            Picasso.with(ctx).load(postimage).into(post_image);

        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            AlertDialog.Builder builder = new AlertDialog.Builder(PostList.this);

            View view = LayoutInflater.from(PostList.this).inflate(R.layout.alertbox, null);

            TextView title = (TextView) view.findViewById(R.id.title);
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.image);

            title.setText("Hello There!");

            imageButton.setImageResource(R.drawable.ic_lock_black_24dp);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                //logout function is here
                    logout();

                    Toast.makeText(PostList.this, "Logged Out", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(PostList.this, "Thank you", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setView(view);
            builder.show();


            return true;
        }
        if (id == R.id.action_about) {

            Intent intent=new Intent(PostList.this,AboutUs.class);
            startActivity(intent);


        }


        return super.onOptionsItemSelected(item);
    }


    public void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent intent=new Intent(PostList.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

}






