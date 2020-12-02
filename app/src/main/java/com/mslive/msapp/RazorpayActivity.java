package com.mslive.msapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class RazorpayActivity extends AppCompatActivity implements PaymentResultListener {

    Button buttonPay;

    String TAG = "Razorpay Error";

    String amount,key;

    int price;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);


        amount=getIntent().getStringExtra("price");
        key=getIntent().getStringExtra("key");

        price= Integer.parseInt(amount);
        price=price*100;
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();



        //to preload the page for low range areas
        Checkout.preload(getApplicationContext());


        startPayment();


       /*buttonPay=(Button)findViewById(R.id.button_pay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

*/




    }



    public void startPayment() {

        /**
         * Instantiate Checkout
         */
       final Checkout checkout = new Checkout();

        //checkout.setKeyID("rzp_test_oS38ESmAiU1zQm");

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.toolbar_logo);


        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /*
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();


            options.put("name", "Liya");
            options.put("description", "Test order");
            options.put("image", "");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#5e35b1");
            options.put("currency", "INR");
            options.put("amount", price);//pass amount in currency subunits
            options.put("prefill.email", "");
            options.put("prefill.contact","");
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e( TAG , "Error in starting Razorpay Checkout", e);
            Toast.makeText(activity, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "payment successful", Toast.LENGTH_SHORT).show();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("orders").child(firebaseUser.getUid()).child(key).child("paymentdone");

        databaseReference.setValue("yes").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(RazorpayActivity.this, "added to successful orders", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(this, "error" + s, Toast.LENGTH_SHORT).show();
        finish();


    }
}
