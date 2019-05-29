package com.example.listyourcar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;

/*
 - Emily Murphy - c16409592
   - List your car for sale app
    - four screens
    - implements Camera and Notification features
    - Uses Room SQLite database with CRUD
  */

//home screen
public class MainActivity extends AppCompatActivity {

    //icon buttons main menu
    private FloatingActionButton mListCar;
    private FloatingActionButton mCreateNot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListCar = findViewById(R.id.ListCar);
        mCreateNot = findViewById(R.id.CreateNot);

        //start activity mainactivity2
        mListCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(mintent);
            }
        });



        //start activity createnotif
        mCreateNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Cintent = new Intent(MainActivity.this, CreateNotif.class);
                startActivity(Cintent);
            }
        });

    }

    //For Capture image
    String currentImagePath = null;

    private File getImageFile() throws IOException {
        // Create an image file name

        String imageName = "JPEG_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile( imageName,".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }

    //called by Camera Image view
    public void captureImage(View view) {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getPackageManager())!=null)
        {
            File imageFile = null;
            try {
                imageFile = getImageFile();
            }catch(IOException e){
                e.printStackTrace();
            }

            if(imageFile!=null)
            {

                Uri imageUri = FileProvider.getUriForFile(this,"com.example.android.fileprovider",imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivity(cameraIntent);


            }


        }


    }



}
