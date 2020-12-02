package com.mslive.msapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText editTextPhone;
    Button getOtpButton;
    Query query;

    DatabaseReference databaseReference;

    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPhone=(EditText)findViewById(R.id.edittext_phone);
        getOtpButton=(Button)findViewById(R.id.button_get_otp);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("userdetail");





        getOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code="+91";
                String number =editTextPhone.getText().toString().trim();

                if (number.isEmpty()||number.length()<10)
                {

                    editTextPhone.setError("valid number is required");
                    editTextPhone.requestFocus();
                    return;//stop further execution
                }

                phoneNumber =  code + number;


                Intent intent= new Intent(LoginActivity.this,OtpActivity.class);
                intent.putExtra("phonenumber",phoneNumber); //passing data to intent


                startActivity(intent);



             //   query=databaseReference.orderByChild("phone").equalTo(phoneNumber);


/*
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){

                            Toast.makeText(LoginActivity.this, "f1", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(LoginActivity.this,OtpActivity.class);
                            intent.putExtra("phonenumber",phoneNumber); //passing data to intent


                            startActivity(intent);



                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "f2", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "you have not signed up with this number." , Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(LoginActivity.this,OtpNewUserActivity.class);
                            intent.putExtra("phonenumber",phoneNumber); //passing data to intent

                            startActivity(intent);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


*/





            }
        });




    }



    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser()!=null){

            Toast.makeText(LoginActivity.this, "f3", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);/*clear everything from
                     stack and start a new activity by closing all other preceding activities.
                     so if user press back button application close instead of going back to prevous activity*/

            startActivity(intent);


        }



    }



}
