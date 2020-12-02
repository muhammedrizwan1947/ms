package com.mslive.msapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity3 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    ImageView imageViewIconFilter,imageViewIconCart,imageViewIconWishlist,imageViewIconProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_3);

        mAuth=FirebaseAuth.getInstance();

        mCurrentUser= mAuth.getCurrentUser();

        imageViewIconFilter=(ImageView)findViewById(R.id.icon_filter);
        imageViewIconCart=(ImageView)findViewById(R.id.icon_cart);
        imageViewIconWishlist=(ImageView)findViewById(R.id.icon_wishlist);
        imageViewIconProfile=(ImageView)findViewById(R.id.icon_profile);


        imageViewIconFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity3.this, "click", Toast.LENGTH_SHORT).show();
            }
        });



        imageViewIconCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity3.this,CartActivity.class);
                startActivity(intent);
            }
        });


        imageViewIconWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity3.this,MainActivity.class);
                startActivity(intent);
            }
        });



        imageViewIconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity3.this,ProfileActivity .class);
                startActivity(intent);
            }
        });








        ViewPagerAdapter2 adapter2= new ViewPagerAdapter2(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


        ViewPager viewPager2= findViewById(R.id.view_pager2);

        viewPager2.setAdapter(adapter2);







    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser()==null){


            Toast.makeText(this, "logged", Toast.LENGTH_SHORT).show();

            Intent intent= new Intent(this,LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);



            startActivity(intent);


        }


    }
}
