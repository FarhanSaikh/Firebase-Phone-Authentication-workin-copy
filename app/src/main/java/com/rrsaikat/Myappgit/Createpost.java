package com.rrsaikat.Myappgit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Createpost extends AppCompatActivity {
    ImageView postimage;
    private static final int GALLREQ = 1;
    EditText titletext, descriptiontext;
    Button submitbutton,imagebuttton,logoutbt;
 StorageReference storageReference=null;
 FirebaseAuth mAuth;
 FirebaseDatabase firebaseDatabase;
 DatabaseReference mref;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpost);
        titletext = (EditText) findViewById(R.id.post_title);
        descriptiontext = (EditText) findViewById(R.id.post_desc);
        submitbutton = (Button) findViewById(R.id.updatebtton);
        imagebuttton= (Button) findViewById(R.id.imagbutton);
        postimage=(ImageView)findViewById(R.id.imageview);
        logoutbt= (Button) findViewById(R.id.logoutbttn);
        mAuth=FirebaseAuth.getInstance();
            String usermobile = mAuth.getCurrentUser().getPhoneNumber();
            storageReference = FirebaseStorage.getInstance().getReference("User Posts").child(usermobile);
            mref=FirebaseDatabase.getInstance().getReference(usermobile).child("Post: ");



    }

    public void upldimage(View view)

    {
        mAuth=FirebaseAuth.getInstance();

        Toast.makeText(Createpost.this,mAuth.getCurrentUser().getPhoneNumber(),Toast.LENGTH_SHORT).show();


        Intent gallery = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                           startActivityForResult(gallery, GALLREQ);


        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == GALLREQ) {
                imageUri = data.getData();
                postimage.setImageURI(imageUri);
            }
        }




    public void update(View view) {
        final String posttitleuser=titletext.getText().toString();
        final String postdesc=descriptiontext.getText().toString();

        if (!TextUtils.isEmpty(posttitleuser) && !TextUtils.isEmpty(postdesc)){

            StorageReference filepath=storageReference.child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    try {
                        mAuth=FirebaseAuth.getInstance();
                        titletext.setText("");
                        descriptiontext.setText("");
                        Toast.makeText(Createpost.this, "Image added", Toast.LENGTH_SHORT).show();
                        final Uri downloadurl = taskSnapshot.getDownloadUrl();
                        final DatabaseReference newpost = mref.push();
                        newpost.child("Post title: ").setValue(posttitleuser);
                        newpost.child("Post Description: ").setValue(postdesc);
                        newpost.child("Image Url: ").setValue(downloadurl.toString());
                        Toast.makeText(Createpost.this, "Title and description added", Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception e){

                        Toast.makeText(Createpost.this,"something not right, retry..", Toast.LENGTH_SHORT).show();

                    }
                }

            });
        }


        else {

            Toast.makeText(Createpost.this,"Pic and image and fill all the feilds. "+mAuth.getCurrentUser().getPhoneNumber(),Toast.LENGTH_SHORT).show();
        }
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();

        Intent intent=new Intent(Createpost.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}


