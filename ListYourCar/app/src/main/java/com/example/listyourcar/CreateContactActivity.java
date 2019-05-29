package com.example.listyourcar;

import android.annotation.TargetApi;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.listyourcar.db.AppDatabase;
import com.example.listyourcar.db.ContactDAO;
import com.example.listyourcar.models.Contact;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateContactActivity extends AppCompatActivity {

    //variables
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mCarMakeEditText;
    private EditText mCarModelEditText;
    private EditText mPhoneNumberEditText;
    private EditText mPriceEditText;
    private Button mSaveButton;

    private Bitmap bp;
    private byte[] photo;
    private ImageView pic;
    private ContactDAO mContactDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        //build database
        mContactDAO = Room.databaseBuilder(this, AppDatabase.class, "db-contacts")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build()
                .getContactDAO();

        mFirstNameEditText = findViewById(R.id.firstNameEditText);
        mLastNameEditText = findViewById(R.id.lastNameEditText);
        mCarMakeEditText = findViewById(R.id.carMakeEditText);
        mCarModelEditText = findViewById(R.id.carModelEditText);
        mPhoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        mPriceEditText = findViewById(R.id.priceEditText);
        pic= (ImageView) findViewById(R.id.pic);
        mSaveButton = findViewById(R.id.saveButton);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mFirstNameEditText.getText().toString();
                String lastName = mLastNameEditText.getText().toString();
                String CarMake = mCarMakeEditText.getText().toString();
                String CarModel = mCarModelEditText.getText().toString();
                String phoneNumber = mPhoneNumberEditText.getText().toString();
                String price = mPriceEditText.getText().toString();
                photo = profileImage(bp);

                if (firstName.length() == 0 || lastName.length() == 0 || phoneNumber.length() == 0 || CarMake.length() == 0 || CarModel.length() == 0|| price.length() == 0) {
                    Toast.makeText(CreateContactActivity.this, "Please make sure all details are correct", Toast.LENGTH_SHORT).show();
                    return;
                }

                Contact contact = new Contact();
                contact.setFirstName(firstName);
                contact.setLastName(lastName);
                contact.setCarMake(CarMake);
                contact.setCarModel(CarModel);
                contact.setPhoneNumber(phoneNumber);
                contact.setPrice(price);
                contact.setImg(photo);
                contact.setCreatedDate(new Date());

                //Insert to database
                try {
                    mContactDAO.insert(contact);
                    setResult(RESULT_OK);
                    finish();
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(CreateContactActivity.this, "A contact with same phone number already exists.", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



    //select image
    public void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Uri choosenImage = data.getData();

                if (choosenImage != null) {

                    bp = decodeUri(choosenImage, 400);
                    pic.setImageBitmap(bp);
                }
            }
        }
    }


    //Convert and resize our image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }

//call select image method when pic is clicked
    public void onClick(View view) {

        selectImage();
    }
}
