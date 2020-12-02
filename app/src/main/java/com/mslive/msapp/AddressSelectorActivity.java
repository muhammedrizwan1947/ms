package com.mslive.msapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddressSelectorActivity extends AppCompatActivity implements AddressAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;

    private AddressAdapter mAdapter;
    private DatabaseReference mDatabaseRef;

    Button continueBtn;




    private List<Adressmodel> mUploads;


    FirebaseUser firebaseUser;

    int selectedItemPosition;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        continueBtn=(Button)findViewById(R.id.continue_btn);




        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        mUploads= new ArrayList<>();
        mAdapter = new AddressAdapter(AddressSelectorActivity.this,mUploads);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(AddressSelectorActivity.this);




        mDatabaseRef =  FirebaseDatabase.getInstance().getReference().child("userdetails")
                .child(firebaseUser.getUid()).child("address");


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    mUploads.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Adressmodel upload = postSnapshot.getValue(Adressmodel.class);

                        //to get key for onclick
                        //must be above mUploads.add(upload);

                        upload.setId(postSnapshot.getKey());
                        mUploads.add(upload);


                    }

                    mAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(AddressSelectorActivity.this, "no data", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(AddressSelectorActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }












/*
    @Override
    public void onWhateverClick(int position) {
        Toast.makeText(this, "do whatever", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteClick(int position) {

        Toast.makeText(this, "delete click at :" + position, Toast.LENGTH_SHORT).show();


    }



 */

    @Override
    public void onItemClick(int position) {




        continueBtn.setVisibility(View.VISIBLE);


      //  Toast.makeText(this, "click at position : " + position, Toast.LENGTH_SHORT).show();



















    }
}



