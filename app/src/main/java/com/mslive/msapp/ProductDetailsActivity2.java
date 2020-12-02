package com.mslive.msapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailsActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // View 1


    String[] quantity = {"1","2","3","4","5","6","7","8","9"};

    ImageView pImage;

    TextView pName,pPrice,pDescription,pMaterial;

    Button smallSizeBtn1,mediumSizeBtn1,largeSizeBtn1;



    Button checkoutBtn;

    ImageView cartBtn;


    RelativeLayout relativeLayoutImageView;
    Context context;




    //View 2
    ConstraintLayout mainView2,sizeChartView2;

    View darkBgView2;

    Button smallSizeBtn2,mediumSizeBtn2,largeSizeBtn2;

    Button continuebtn2;
           ImageView closeBtn2;










    //Database
    DatabaseReference productDetailsReference,cartReference;



    //Firebaseuser
    FirebaseUser firebaseUser;



    //strings
     String productId;
     String sizeSelectedString="none";
     String productPriceString;
     String cartOrCheckout="none";



    private int gridWidth,imageWidth,imageHeight;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_3);


        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        imageHeight=displayMetrics.heightPixels;

        //INITIALISATION


        {
        //view 1



            pImage = (ImageView) findViewById(R.id.p_image_1);
            pName = (TextView) findViewById(R.id.p_name_1);
            pDescription = (TextView) findViewById(R.id.p_desc_1);
            pPrice = (TextView) findViewById(R.id.p_price_1);
            pMaterial = (TextView) findViewById(R.id.p_material_1);


            smallSizeBtn1 = (Button) findViewById(R.id.size_s_btn_1);
            mediumSizeBtn1 = (Button) findViewById(R.id.size_m_btn_1);
            largeSizeBtn1 = (Button) findViewById(R.id.size_l_btn_1);




            checkoutBtn = (Button) findViewById(R.id.checkout_btn_1);
            cartBtn = (ImageView) findViewById(R.id.cart_btn_1);

            relativeLayoutImageView=findViewById(R.id.image_view_constrain);


            //view 2
            mainView2 = (ConstraintLayout) findViewById(R.id.main_view_2);

            darkBgView2 = (View) findViewById(R.id.dark_bg_2);

            sizeChartView2 = (ConstraintLayout) findViewById(R.id.size_chart_view_2);

            smallSizeBtn2 = (Button) findViewById(R.id.size_s_btn_2);
            mediumSizeBtn2 = (Button) findViewById(R.id.size_m_btn_2);
            largeSizeBtn2 = (Button) findViewById(R.id.size_l_btn_2);

            closeBtn2 = (ImageView) findViewById(R.id.close_btn_2);
            continuebtn2 = (Button) findViewById(R.id.continue_btn_2);


            //Strings
            productId = getIntent().getStringExtra("productid");


            //firebaseuser
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


            //databaseReferece
            productDetailsReference = FirebaseDatabase.getInstance().getReference().child("productdetails").child(productId);


            //FETCHING DATA
            productDetailsReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {


                        //imageview
                        Picasso.get().load(snapshot.child("imageurl").getValue().toString()).placeholder(R.drawable.bgwh).fit().into(pImage);


                        //TextView
                        pName.setText(snapshot.child("productname").getValue().toString());
                        productPriceString = snapshot.child("productprice").getValue().toString();
                        pPrice.setText("Rs \t" +productPriceString);
                        pDescription.setText(snapshot.child("productdescription").getValue().toString());


                        //Checks

                        if (snapshot.child("smallsize").getValue().toString().equals("1")) {
                            smallSizeBtn1.setVisibility(View.VISIBLE);
                            smallSizeBtn2.setVisibility(View.VISIBLE);
                        }


                        if (snapshot.child("mediumsize").getValue().toString().equals("1")) {
                            mediumSizeBtn1.setVisibility(View.VISIBLE);
                            mediumSizeBtn2.setVisibility(View.VISIBLE);

                        }


                        if (snapshot.child("largesize").getValue().toString().equals("1")) {
                            largeSizeBtn1.setVisibility(View.VISIBLE);
                            largeSizeBtn2.setVisibility(View.VISIBLE);
                        }


                    } else {
                        Toast.makeText(ProductDetailsActivity2.this, "product is no longer available", Toast.LENGTH_SHORT).show();
                        finish();

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }





       // gridWidth=context.getResources().getDisplayMetrics().heightPixels;

        //imageHeight=gridWidth*3/5;





        relativeLayoutImageView.requestLayout();

        relativeLayoutImageView.getLayoutParams().height=imageHeight*3/5;


        final Spinner quantitySpinner= findViewById(R.id.quantity_spinner);
        quantitySpinner.setOnItemSelectedListener(this);


        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,quantity);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        quantitySpinner.setAdapter(arrayAdapter);






        //ONCLICKS

        //VIEW 1


        {


            cartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sizeSelectedString.equals("none")) {
                        Toast.makeText(ProductDetailsActivity2.this, "Select size", Toast.LENGTH_SHORT).show();
                        cartOrCheckout="cart";

                        mainView2.setVisibility(View.VISIBLE);

                    } else {


                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("cart").child(firebaseUser.getUid()).push();

                        Map<String, Object> map = new HashMap<>();
                        map.put("productid", productId);
                        map.put("size", sizeSelectedString);
                        map.put("quantity",  quantitySpinner.getSelectedItem().toString());

                        databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ProductDetailsActivity2.this, "add success", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }
            });


            checkoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (sizeSelectedString.equals("none")) {
                        Toast.makeText(ProductDetailsActivity2.this, "Select size", Toast.LENGTH_SHORT).show();
                        cartOrCheckout="checkout";
                        mainView2.setVisibility(View.VISIBLE);

                    } else {
                        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("orders").child(firebaseUser.getUid()).push();

                        Map<String, Object> map = new HashMap<>();
                        map.put("productid", productId);
                        map.put("size", sizeSelectedString);
                        map.put("quantity", quantitySpinner.getSelectedItem().toString());
                        map.put("paymentdone","no");
                        map.put("price",productPriceString);


                        databaseReference1.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ProductDetailsActivity2.this, "added to orders", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ProductDetailsActivity2.this, RazorpayActivity.class);
                                intent.putExtra("key", databaseReference1.getKey());
                                intent.putExtra("price", productPriceString);


                                startActivity(intent);
                            }
                        });


                    }
                }
            });


            // size btn clicks
            smallSizeBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!sizeSelectedString.equals("S")) {

                        sizeSelectedString = "S";

                        smallSizeBtn1.setBackgroundResource(R.drawable.d);
                        mediumSizeBtn1.setBackgroundResource(R.drawable.c);
                        largeSizeBtn1.setBackgroundResource(R.drawable.c);


                        smallSizeBtn1.setTextColor(Color.parseColor("#ffffff"));
                        mediumSizeBtn1.setTextColor(Color.parseColor("#000000"));
                        largeSizeBtn1.setTextColor(Color.parseColor("#000000"));
                    }
                }
            });


            mediumSizeBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!sizeSelectedString.equals("M")) {

                        sizeSelectedString = "M";


                        smallSizeBtn1.setBackgroundResource(R.drawable.c);
                        mediumSizeBtn1.setBackgroundResource(R.drawable.d);
                        largeSizeBtn1.setBackgroundResource(R.drawable.c);


                        smallSizeBtn1.setTextColor(Color.parseColor("#000000"));
                        mediumSizeBtn1.setTextColor(Color.parseColor("#ffffff"));
                        largeSizeBtn1.setTextColor(Color.parseColor("#000000"));
                    }
                }
            });


            largeSizeBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!sizeSelectedString.equals("L")) {

                        sizeSelectedString = "L";


                        smallSizeBtn1.setBackgroundResource(R.drawable.c);
                        mediumSizeBtn1.setBackgroundResource(R.drawable.c);
                        largeSizeBtn1.setBackgroundResource(R.drawable.d);


                        smallSizeBtn1.setTextColor(Color.parseColor("#000000"));
                        mediumSizeBtn1.setTextColor(Color.parseColor("#000000"));
                        largeSizeBtn1.setTextColor(Color.parseColor("#ffffff"));


                    }
                }
            });


            // quantity button clicks



        }






        //View 2

        {


            closeBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainView2.setVisibility(View.GONE);
                    sizeSelectedString="none";

                }
            });


            darkBgView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainView2.setVisibility(View.GONE);
                    sizeSelectedString="none";
                }
            });


            // size btn clicks
            smallSizeBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!sizeSelectedString.equals("S")) {

                        sizeSelectedString = "S";

                        smallSizeBtn2.setBackgroundResource(R.drawable.d);
                        mediumSizeBtn2.setBackgroundResource(R.drawable.c);
                        largeSizeBtn2.setBackgroundResource(R.drawable.c);


                        smallSizeBtn2.setTextColor(Color.parseColor("#ffffff"));
                        mediumSizeBtn2.setTextColor(Color.parseColor("#000000"));
                        largeSizeBtn2.setTextColor(Color.parseColor("#000000"));
                    }
                }
            });


            mediumSizeBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!sizeSelectedString.equals("M")) {

                        sizeSelectedString = "M";


                        smallSizeBtn2.setBackgroundResource(R.drawable.c);
                        mediumSizeBtn2.setBackgroundResource(R.drawable.d);
                        largeSizeBtn2.setBackgroundResource(R.drawable.c);


                        smallSizeBtn2.setTextColor(Color.parseColor("#000000"));
                        mediumSizeBtn2.setTextColor(Color.parseColor("#ffffff"));
                        largeSizeBtn2.setTextColor(Color.parseColor("#000000"));
                    }
                }
            });


            largeSizeBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!sizeSelectedString.equals("L")) {

                        sizeSelectedString = "L";


                        smallSizeBtn2.setBackgroundResource(R.drawable.c);
                        mediumSizeBtn2.setBackgroundResource(R.drawable.c);
                        largeSizeBtn2.setBackgroundResource(R.drawable.d);


                        smallSizeBtn2.setTextColor(Color.parseColor("#000000"));
                        mediumSizeBtn2.setTextColor(Color.parseColor("#000000"));
                        largeSizeBtn2.setTextColor(Color.parseColor("#ffffff"));


                    }
                }
            });


            continuebtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (sizeSelectedString.equals("none")) {
                        Toast.makeText(ProductDetailsActivity2.this, "Select size", Toast.LENGTH_SHORT).show();


                    } else {

                        if (cartOrCheckout.equals("checkout")) {

                            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child(firebaseUser.getUid()).push();

                            Map<String, Object> map = new HashMap<>();
                            map.put("productid", productId);
                            map.put("size", sizeSelectedString);
                            map.put("quantity",  quantitySpinner.getSelectedItem().toString());
                            map.put("paymentdone","no");
                            map.put("price",productPriceString);


                            databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ProductDetailsActivity2.this, "added to orders", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(ProductDetailsActivity2.this, RazorpayActivity.class);
                                    intent.putExtra("price", productPriceString);
                                    intent.putExtra("key", databaseReference.getKey());
                                    startActivity(intent);
                                }
                            });










                        }
                        else if (cartOrCheckout.equals("cart")){

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("cart").child(firebaseUser.getUid()).push();

                            Map<String, Object> map = new HashMap<>();
                            map.put("productid", productId);
                            map.put("size", sizeSelectedString);
                            map.put("quantity",  quantitySpinner.getSelectedItem().toString());

                            databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ProductDetailsActivity2.this, "add success", Toast.LENGTH_SHORT).show();
                                }
                            });



                        }

                        else {

                        }



                    }

                }
            });


        }

























    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(context,quantity[i], Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), quantity[i], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
