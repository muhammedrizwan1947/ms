package com.mslive.msapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText etName,etAddress,etState,etPhone,etPincode;

    String Name,Address,State,Phone,Pincode;


    Button buttonAddAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        initialize();



        buttonAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFn();
            }
        });

    }

    private void onClickFn() {
        Name= etName.getText().toString();
        Address= etAddress.getText().toString();


        State= etState.getText().toString();
        Phone= etPhone.getText().toString();
        Pincode= etPincode.getText().toString();


       if (TextUtils.isEmpty(Name) ) {
            etName.requestFocus();
            Toast.makeText(this, "Add Name", Toast.LENGTH_SHORT).show();

            closeKeyboard();
        }


       else if (TextUtils.isEmpty(Phone)) {
           etPhone.requestFocus();
           Toast.makeText(this, "Add Phone", Toast.LENGTH_SHORT).show();
           closeKeyboard();
       }







      else   if (TextUtils.isEmpty(Address)) {
            etAddress.requestFocus();
            Toast.makeText(this, "Add Address", Toast.LENGTH_SHORT).show();
            closeKeyboard();
        }





       else if (TextUtils.isEmpty(State)) {
            etState.requestFocus();
            Toast.makeText(this, "Add State", Toast.LENGTH_SHORT).show();
            closeKeyboard();
        }









       else if (TextUtils.isEmpty(Pincode)) {
            etPincode.requestFocus();
            Toast.makeText(this, "Add Pincode", Toast.LENGTH_SHORT).show();
            closeKeyboard();
        }




        else {
           FirebaseUser firebaseUser;
           firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
           DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("userdetails")
                   .child(firebaseUser.getUid()).child("address").push();

           Map<String,Object> map =new HashMap<>();
           map.put("id",databaseReference.getKey());
           map.put("name",Name);

           map.put("address",Address);
           map.put("state",State);
           map.put("phone",Phone);
           map.put("pincode",Pincode);




           databaseReference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   Toast.makeText(AddAddressActivity.this, "Address added successfully", Toast.LENGTH_SHORT).show();
               }
           });



       }















    }


    private void closeKeyboard(){
        View view=this.getCurrentFocus();
        if (view!=null){
            InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


    private void initialize() {


        etName= (EditText) findViewById(R.id.et_name);

        etAddress= (EditText) findViewById(R.id.et_address);
        etState= (EditText) findViewById(R.id.et_state);
        etPhone= (EditText) findViewById(R.id.et_phone);
        etPincode= (EditText) findViewById(R.id.et_pincode);


        buttonAddAddress= (Button) findViewById(R.id.button_add_address);
    }
}
