package com.rrsaikat.Myappgit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Createpost extends AppCompatActivity {
    ImageView postimage;
    private static final int GALLREQ = 1;
    EditText titletext, descriptiontext;
    Button submitbutton,imagebuttton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpost);
        titletext = (EditText) findViewById(R.id.post_title);
        descriptiontext = (EditText) findViewById(R.id.post_desc);
        submitbutton = (Button) findViewById(R.id.updatebtton);
        imagebuttton= (Button) findViewById(R.id.imagbutton);
        postimage=(ImageView)findViewById(R.id.imageview);

    }

    public void upldimage(View view)

    {
            Intent gallery = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                           startActivityForResult(gallery, GALLREQ);


        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == GALLREQ) {
                Uri imageUri = data.getData();
                postimage.setImageURI(imageUri);
            }
        }




    public void update(View view) {
    }



    }


