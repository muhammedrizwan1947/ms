package com.mslive.msapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity implements interfacex{

    RecyclerView recyclerView;
    MainAdapter mainAdapter;



    private RecyclerView mRecyclerView;

    private CartAdapter mAdapter;
    private DatabaseReference mDatabaseRef;

    String date,subject;

    int k;

    private List<Cartmodel> mUploads;
    EditText editText1,editText2;
    Button button;

    String totalprice="none";
    int totalp;

    TextView total;

    int selectedQuantity=0;


    FirebaseUser firebaseUser;


    ConstraintLayout constraintLayout2;
     String selectedKey="none";


     ImageView closeImage;

     View darkBg;

     Button continueBtn,checkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_2);

        //start

        closeImage=findViewById(R.id.close_btn_2);
        darkBg=findViewById(R.id.dark_bg_2);
        continueBtn=findViewById(R.id.continue_btn_2);
        checkout=findViewById(R.id.button_checkout);

        total=findViewById(R.id.tv_total);








        recyclerView=findViewById(R.id.recyclerview);


        ArrayList<String> arrayList= new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.add("6");
        arrayList.add("7");
        arrayList.add("8");
        arrayList.add("9");
        arrayList.add("10");


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mainAdapter=new MainAdapter(this,arrayList,this);
        // mainAdapter.setClickListener(this);
        recyclerView.setAdapter(mainAdapter);


        //end




        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        constraintLayout2=findViewById(R.id.main_view_2);






        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        mUploads= new ArrayList<>();
        mAdapter = new CartAdapter(CartActivity.this,mUploads,this);
        mRecyclerView.setAdapter(mAdapter);

      //  mAdapter.setOnItemClickListener(CartActivity.this);

        Toast.makeText(this, totalprice, Toast.LENGTH_SHORT).show();


        mDatabaseRef =  FirebaseDatabase.getInstance().getReference().child("cart").child(firebaseUser.getUid());


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    mUploads.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Cartmodel upload = postSnapshot.getValue(Cartmodel.class);

                        //to get key for onclick
                        //must be above mUploads.add(upload);

                        upload.setId(postSnapshot.getKey());
                        mUploads.add(upload);


                        totalprice=getIntent().getStringExtra("total");
                        Toast.makeText(CartActivity.this, totalprice, Toast.LENGTH_SHORT).show();


                    }

                    mAdapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(CartActivity.this, "no data", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(CartActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });




        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintLayout2.setVisibility(View.GONE);
            }
        });


        darkBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintLayout2.setVisibility(View.GONE);
            }
        });



        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseRef.child(selectedKey).child("quantity").setValue(Integer.toString(selectedQuantity)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CartActivity.this, "scss", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }


    @Override
    public void onclick(int v) {
        constraintLayout2.setVisibility(View.VISIBLE);

        k=v;

        Cartmodel selectedItem= mUploads.get(v);
         selectedKey= selectedItem.getId();
        Toast.makeText(this, selectedKey+"+"+v, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onclick2(int position) {
        Toast.makeText(this, "p"+position + "q\t" + selectedKey, Toast.LENGTH_SHORT).show();



        selectedQuantity=position+1;






    }






    @Override
    protected void onStart() {
        super.onStart();

    }


}



