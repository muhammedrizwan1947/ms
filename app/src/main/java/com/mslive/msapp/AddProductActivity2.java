package com.mslive.msapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddProductActivity2 extends AppCompatActivity {

    //this is a successful admin activity to store image in storage

    Button chooseImageButton,uploadImageButton,nextPageButton,saveButton,smallSizeButton,mediumSizeButton,
            largeSizeButton,nightwearToggleButton,topwearToggleButton;
    ImageView productUploadImage;
    EditText edtProductName,edtProductDescription,edtProductNumber,edtProductPrice;

    private DatabaseReference productRef,childref;
    private StorageReference productImageRef;


    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    int smallInt=1;
    int mediumInt=1;
    int largeInt=1;
    int wearInt=1;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_2);

        chooseImageButton=(Button)findViewById(R.id.button_choose_image);
        uploadImageButton=(Button)findViewById(R.id.button_upload_image);
        nextPageButton=(Button)findViewById(R.id.button_next_page);
        smallSizeButton=(Button)findViewById(R.id.button_small_size);
        mediumSizeButton=(Button)findViewById(R.id.button_medium_size);
        largeSizeButton=(Button)findViewById(R.id.button_large_size);

        nightwearToggleButton=(Button)findViewById(R.id.button_nightwear_toggle);
        topwearToggleButton=(Button)findViewById(R.id.button_topwear_toggle);

        saveButton=(Button)findViewById(R.id.button_save);
        productUploadImage=(ImageView)findViewById(R.id.upload_image);
        edtProductName=(EditText) findViewById(R.id.edittext_name);
        edtProductDescription=(EditText) findViewById(R.id.edittext_desc);
        edtProductNumber=(EditText) findViewById(R.id.edittext_number);
        edtProductPrice=(EditText) findViewById(R.id.edittext_price);

        productRef= FirebaseDatabase.getInstance().getReference().child("productdetails");
        productImageRef= FirebaseStorage.getInstance().getReference().child("productimagedetails");


        smallSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (smallInt==1){
                    smallInt=0;
                    smallSizeButton.setBackgroundColor(Color.GRAY);
                }

                else {
                    smallInt=1;
                    smallSizeButton.setBackgroundColor(Color.BLUE);
                }

            }
        });


        mediumSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mediumInt==1){
                    mediumInt=0;
                    mediumSizeButton.setBackgroundColor(Color.GRAY);
                }

                else {
                    mediumInt=1;
                    mediumSizeButton.setBackgroundColor(Color.BLUE);
                }

            }
        });



        largeSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (largeInt==1){
                    largeInt=0;
                    largeSizeButton.setBackgroundColor(Color.GRAY);
                }

                else {
                    largeInt=1;
                    largeSizeButton.setBackgroundColor(Color.BLUE);
                }

            }
        });





        nightwearToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wearInt=1;
                topwearToggleButton.setBackgroundColor(Color.GRAY);
                nightwearToggleButton.setBackgroundColor(Color.BLUE);

            }
        });



        topwearToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wearInt=0;
                topwearToggleButton.setBackgroundColor(Color.BLUE);
                nightwearToggleButton.setBackgroundColor(Color.GRAY);

            }
        });








        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AddProductActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });






        // on pressing btnSelect SelectImage() is called
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });

        // on pressing btnUpload uploadImage() is called
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uploadImage();
            }
        });
    }

    // Select Image method
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);


        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                productUploadImage.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage()


    {
        final String productName = edtProductName.getText().toString();
        final String productDesc = edtProductDescription.getText().toString();
        final String productNumber = edtProductNumber.getText().toString();
        final String productPrice = edtProductPrice.getText().toString();





        if (filePath != null) {


            final StorageReference ref
                    = productImageRef
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                   // progressDialog.dismiss();
                                    Toast
                                            .makeText(AddProductActivity2.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();





                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            Toast.makeText(AddProductActivity2.this, "download url:"+uri.toString(), Toast.LENGTH_SHORT).show();



                                            DatabaseReference databaseReference = productRef.push();
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("id", databaseReference.getKey());
                                            map.put("productname",productName);
                                            map.put("productdescription",productDesc);
                                            map.put("productnumber", productNumber);
                                            map.put("productprice", productPrice);
                                            map.put("imageurl", uri.toString());
                                            map.put("smallsize", smallInt);
                                            map.put("mediumsize", mediumInt);
                                            map.put("largesize", largeInt);
                                            map.put("weartype", wearInt);




                                            databaseReference.setValue(map);





                                           // productRef.child("productname").setValue(productName);
                                          //  productRef.child("productdescription").setValue(productDesc);
                                          //  productRef.child("productnumber").setValue(productNumber);

                                          //  childref=productRef.child("imageurl");


                                          //  String value=uri.toString();
                                            // childref.setValue(value);



                                        }
                                    });











                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                           // progressDialog.dismiss();
                            Toast
                                    .makeText(AddProductActivity2.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

        }
    }
}











