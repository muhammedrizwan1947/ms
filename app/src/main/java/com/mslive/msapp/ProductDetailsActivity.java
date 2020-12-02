package com.mslive.msapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    // PRIMARY

    // primary images
    ImageView productImage,sizeClose,qtyClose;
    TextView productName,productPrice,productDescription,productMaterial;
    String sizeSelected="none";

    String id,productname,productdescription,productnumber,productprice,
            imageurl,smallsize,mediumsize,largesize,weartype,material;
    Button smallSizeBtnPrimary,mediumSizeBtnPrimary,largeSizeBtnPrimary;
     Button   productSmallAvailableLinearButton,productMediumAvailableLinearButton,productLargeAvailableLinearButton;
     Button toCheckoutButton,toContiueButton;
     Button toCartButton;
     String productId;

     Button qtyPlusBtnPrimary,qtyMinusBtnPrimary,qtyPlusBtnSecondary,qtyMinusBtnSecondary;
     TextView qtyPrimaryTextview,qtySecondaryTextview;

     DatabaseReference databaseReference;
     int smallselected,mediumselected,largeselected,smallselectedlinear,mediumselectedlinear,largeselectedlinear;




     FirebaseUser firebaseUser;


    //SECONDARY
    ConstraintLayout sizeChartLayout;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);




        initialise();




        getdatafromDatabase();




 sizeButtonFunctionNew();


 toCheckoutButton.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         checkoutFn();

     }
 });













        toCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendToCart();




            }
        });








        sizechartlinearinitialiseFn();












    }

    private void checkoutFn() {

        Intent intent=new Intent(ProductDetailsActivity.this,RazorpayActivity.class);
        intent.putExtra("price",productprice);
        startActivity(intent);

    }

    private void sizechartlinearinitialiseFn() {



        sizeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sizeChartLayout.setVisibility(View.GONE);
            }
        });












        productSmallAvailableLinearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sizeSelected.equals("S")){
                    productSmallAvailableLinearButton.setBackgroundResource(R.drawable.d);
                    productMediumAvailableLinearButton.setBackgroundResource(R.drawable.c);
                    productLargeAvailableLinearButton.setBackgroundResource(R.drawable.c);


                    productSmallAvailableLinearButton.setTextColor(Color.parseColor("#ffffff"));
                    productMediumAvailableLinearButton.setTextColor(Color.parseColor("#000000"));
                    productLargeAvailableLinearButton.setTextColor(Color.parseColor("#000000"));

                    sizeSelected="S";


                }

            }
        });





        productMediumAvailableLinearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sizeSelected.equals("M")){
                    productSmallAvailableLinearButton.setBackgroundResource(R.drawable.c);
                    productMediumAvailableLinearButton.setBackgroundResource(R.drawable.d);
                    productLargeAvailableLinearButton.setBackgroundResource(R.drawable.c);

                    productSmallAvailableLinearButton.setTextColor(Color.parseColor("#000000"));
                    productMediumAvailableLinearButton.setTextColor(Color.parseColor("#ffffff"));
                    productLargeAvailableLinearButton.setTextColor(Color.parseColor("#000000"));
                    sizeSelected="M";


                }

            }
        });


        productLargeAvailableLinearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sizeSelected.equals("L")){
                    productSmallAvailableLinearButton.setBackgroundResource(R.drawable.c);
                    productMediumAvailableLinearButton.setBackgroundResource(R.drawable.c);
                    productLargeAvailableLinearButton.setBackgroundResource(R.drawable.d);


                    productSmallAvailableLinearButton.setTextColor(Color.parseColor("#000000"));
                    productMediumAvailableLinearButton.setTextColor(Color.parseColor("#000000"));
                    productLargeAvailableLinearButton.setTextColor(Color.parseColor("#ffffff"));

                    sizeSelected="L";
                }

            }
        });



        toContiueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sizeSelected.equals("none")){
                    Toast.makeText(ProductDetailsActivity.this, "Select a size", Toast.LENGTH_SHORT).show();
                }


                else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("cart").child(firebaseUser.getUid()).push();

                    Map<String,Object> map =new HashMap<>();
                    map.put("productid",productId);
                    map.put("size",sizeSelected);
                    map.put("quantity","1");

                    databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ProductDetailsActivity.this, "add success", Toast.LENGTH_SHORT).show();


                        }
                    });


                }







            }
        });







    }

    private void sendToCart() {

        if (sizeSelected.equals("none")){
            sizeChartLayout.setVisibility(View.VISIBLE);




        }
        else {


            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("cart").child(firebaseUser.getUid()).push();

            Map<String,Object> map =new HashMap<>();
            map.put("productid",productId);
            map.put("size",sizeSelected);
            map.put("quantity","1");

            databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(ProductDetailsActivity.this, "add success", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(ProductDetailsActivity.this,CartActivity.class);


                    startActivity(intent);
                }
            });





        }



    }

    private void sizeButtonFunctionNew() {


        smallSizeBtnPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sizeSelected.equals("S")){

                    smallSizeBtnPrimary.setBackgroundResource(R.drawable.d);
                    mediumSizeBtnPrimary.setBackgroundResource(R.drawable.c);
                    largeSizeBtnPrimary.setBackgroundResource(R.drawable.c);


                    smallSizeBtnPrimary.setTextColor(Color.parseColor("#ffffff"));
                    mediumSizeBtnPrimary.setTextColor(Color.parseColor("#000000"));
                    largeSizeBtnPrimary.setTextColor(Color.parseColor("#000000"));
                    sizeSelected="S";


                }

            }
        });





        mediumSizeBtnPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sizeSelected.equals("M")){

                    smallSizeBtnPrimary.setBackgroundResource(R.drawable.c);
                    mediumSizeBtnPrimary.setBackgroundResource(R.drawable.d);
                    largeSizeBtnPrimary.setBackgroundResource(R.drawable.c);


                    smallSizeBtnPrimary.setTextColor(Color.parseColor("#000000"));
                    mediumSizeBtnPrimary.setTextColor(Color.parseColor("#ffffff"));
                    largeSizeBtnPrimary.setTextColor(Color.parseColor("#000000"));
                    sizeSelected="M";


                }

            }
        });




        largeSizeBtnPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sizeSelected.equals("L")){

                    smallSizeBtnPrimary.setBackgroundResource(R.drawable.c);
                    mediumSizeBtnPrimary.setBackgroundResource(R.drawable.c);
                    largeSizeBtnPrimary.setBackgroundResource(R.drawable.d);


                    smallSizeBtnPrimary.setTextColor(Color.parseColor("#000000"));
                    mediumSizeBtnPrimary.setTextColor(Color.parseColor("#000000"));
                    largeSizeBtnPrimary.setTextColor(Color.parseColor("#ffffff"));
                    sizeSelected="M";


                }

            }
        });


    }



    private void initialise() {

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


        smallselected=0;
        mediumselected=0;
        largeselected=0;
        smallselectedlinear=0;
        mediumselectedlinear=0;
        largeselectedlinear=0;


        sizeChartLayout=(ConstraintLayout)findViewById(R.id.size_chart_constrain);
        productImage=(ImageView)findViewById(R.id.product_image);
        productName=(TextView) findViewById(R.id.product_name);
        productPrice=(TextView) findViewById(R.id.product_price);
        productDescription=(TextView) findViewById(R.id.product_desc);
        productMaterial=(TextView) findViewById(R.id.product_material);
        smallSizeBtnPrimary=(Button) findViewById(R.id.button_small_size_product_available);
        mediumSizeBtnPrimary=(Button) findViewById(R.id.button_large_size_product_available);
        largeSizeBtnPrimary=(Button) findViewById(R.id.button_medium_size_product_available);

        productSmallAvailableLinearButton=(Button) findViewById(R.id.button_small_size_product_selector);
        productMediumAvailableLinearButton=(Button) findViewById(R.id.button_medium_size_product_selector);
        productLargeAvailableLinearButton=(Button) findViewById(R.id.button_large_size_product_selector);

        toCartButton=(Button) findViewById(R.id.button_to_cart);
        toCheckoutButton=(Button) findViewById(R.id.button_to_checkout);
        toContiueButton=(Button) findViewById(R.id.button_to_continue);
        sizeClose=(ImageView) findViewById(R.id.button_close_size_selector);






        //size selector
        qtyPlusBtnPrimary=(Button) findViewById(R.id.button_plus_quantity);
        qtyMinusBtnPrimary=(Button) findViewById(R.id.button_minus_quantity);
        qtyPrimaryTextview=(TextView) findViewById(R.id.tv_qty);


        qtyPlusBtnSecondary=(Button) findViewById(R.id.button_plus_quantity_2);
        qtyMinusBtnSecondary=(Button) findViewById(R.id.button_minus_quantity_2);
        qtySecondaryTextview=(TextView) findViewById(R.id.tv_qty_2);







        productId=getIntent().getStringExtra("productid");// get passed data from other activity
        //productSmallAvailableButton.setVisibility(View.VISIBLE);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("productdetails").child(productId);

    }







    private void getdatafromDatabase() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                productname=snapshot.child("productname").getValue().toString();
                productprice= snapshot.child("productprice").getValue().toString();
                productdescription=snapshot.child("productdescription").getValue().toString();
                imageurl=snapshot.child("imageurl").getValue().toString();
                largesize=snapshot.child("largesize").getValue().toString();
                smallsize=snapshot.child("smallsize").getValue().toString();
                material=snapshot.child("material").getValue().toString();
                mediumsize=snapshot.child("mediumsize").getValue().toString();
                productnumber=snapshot.child("productnumber").getValue().toString();
                weartype=snapshot.child("weartype").getValue().toString();


                Picasso.get().load(imageurl).placeholder(R.drawable.bgwh).fit().into(productImage);

                productName.setText(productname);
                productDescription.setText(productdescription);
                productPrice.setText("Rs " +productprice);
                productMaterial.setText(material);





                if (mediumsize.equals("1")){
                    mediumSizeBtnPrimary.setVisibility(View.VISIBLE);
                    productMediumAvailableLinearButton.setVisibility(View.VISIBLE);
                }



                if (smallsize.equals("1")){
                    smallSizeBtnPrimary.setVisibility(View.VISIBLE);
                    productSmallAvailableLinearButton.setVisibility(View.VISIBLE);

                }






                if (largesize.equals("1")){
                    largeSizeBtnPrimary.setVisibility(View.VISIBLE);
                    productLargeAvailableLinearButton.setVisibility(View.VISIBLE);
                }







            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
