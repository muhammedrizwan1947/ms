package com.mslive.msapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class OtpNewUserActivity extends AppCompatActivity {
    private String verificationId;
    private FirebaseAuth mAuth;
    EditText otpEditText;
    Button verifyOtpButton;
    FirebaseUser currentUser;
    DatabaseReference userDetailsRef;
    String phonenumber,phone;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEditText=(EditText)findViewById(R.id.edittext_otp);
        verifyOtpButton=(Button)findViewById(R.id.verify_otp_button);
        mAuth=FirebaseAuth.getInstance();
        phone=getIntent().getStringExtra("phone");
         phonenumber=getIntent().getStringExtra("phonenumber");// get passed data from other activity
        sendVerificationCode(phonenumber);




        //when auto verification fails and user manually enters otp
        verifyOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code= otpEditText.getText().toString().trim();

                if (code.isEmpty()|| code.length()<6){

                    otpEditText.setError("Enter complete code...... ");
                }


                verifyCode(code);


            }
        });


    }



    //if user manually inputs otp

    private void verifyCode(String code){

        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);

    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    currentUser=FirebaseAuth.getInstance().getCurrentUser();

                    userDetailsRef = FirebaseDatabase.getInstance().getReference().child("userdetail")
                            .child(currentUser.getUid());





                    HashMap<String, String> profile = new HashMap<String, String>();


                    profile.put("phone",phonenumber);


                    userDetailsRef.setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent= new Intent(OtpNewUserActivity.this,MainActivity.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);/*clear everything from
                     stack and start a new activity by closing all other preceding activities.
                     so if user press back button application close instead of going back to prevous activity*/

                            startActivity(intent);

                        }
                    });






                }
                else{
                    Toast.makeText(OtpNewUserActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private void sendVerificationCode(String number){



        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );



    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code=phoneAuthCredential.getSmsCode();
            if (code != null){
                otpEditText.setText(code);


                verifyCode(code);


            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(OtpNewUserActivity.this, e.getMessage() + "Automatic Verification Failed \n Enter code manually", Toast.LENGTH_LONG).show();



        }
    };
}
