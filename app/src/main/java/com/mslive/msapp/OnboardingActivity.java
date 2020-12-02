package com.mslive.msapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class OnboardingActivity  extends AppCompatActivity {
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;

    TextView tvHeader,tvDesc;

    private ViewPager onboard_pager;

    private OnBoard_Adapter mAdapter;

    private Button btn_get_started,loginBtn;

    int previous_pos=0;

    Animation btt,bttdua,bttiga,imgalpha,x1,x2,x3;

    DatabaseReference userDetailsRef;




    ArrayList<OnBoardItem> onBoardItems=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);




        //import animation
        btt= AnimationUtils.loadAnimation(this,R.anim.btt);
        bttdua= AnimationUtils.loadAnimation(this,R.anim.bttdua);
        bttiga= AnimationUtils.loadAnimation(this,R.anim.bttiga);
        imgalpha= AnimationUtils.loadAnimation(this,R.anim.imgalpha);
        x1= AnimationUtils.loadAnimation(this,R.anim.x1);
        x2= AnimationUtils.loadAnimation(this,R.anim.x2);
        x3= AnimationUtils.loadAnimation(this,R.anim.x3);

        //btn_get_started = (Button) findViewById(R.id.signup_btn);
        loginBtn = (Button) findViewById(R.id.signin_btn);

        onboard_pager = (ViewPager) findViewById(R.id.pager_introduction);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        loadData();

        mAdapter = new OnBoard_Adapter(this,onBoardItems);
        onboard_pager.setAdapter(mAdapter);
        onboard_pager.setCurrentItem(0);
        onboard_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // Change the current position intimation

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.non_selected_item_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.selected_item_dot));


                int pos=position+1;

                if(pos==dotsCount&&previous_pos==(dotsCount-1)) {
                    show_animation();
                   // up();
                   // up2();
                }
                else if(pos==(dotsCount-1)&&previous_pos==dotsCount)
                   hide_animation();
                   // down();
                //down2();

                previous_pos=pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /*
        btn_get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OnboardingActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
*/


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OnboardingActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });





        setUiPageViewController();

    }

    private void up() {

        Animation show = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim);

        btn_get_started.startAnimation(show);

        show.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                btn_get_started.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                btn_get_started.clearAnimation();

            }

        });




    }









    private void up2() {

        Animation show2 = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim);

        loginBtn.startAnimation(show2);

        show2.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                loginBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                loginBtn.clearAnimation();

            }

        });




    }














    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser()!=null){


            //Toast.makeText(this, "logged", Toast.LENGTH_SHORT).show();












            Intent intent= new Intent(this,MainActivity3.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);



            startActivity(intent);


        }



    }

    // Load data into the viewpager

    public void loadData()
    {

        //Typeface custom_font= Typeface.createFromAsset(getAssets(), "fonts/calibril.ttf");


        int[] header = {R.string.ob_header1, R.string.ob_header2, R.string.ob_header3};
        int[] desc = {R.string.ob_desc1,
                R.string.ob_desc2,
                R.string.ob_desc3};
        int[] imageId = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3};


        for(int i=0;i<imageId.length;i++)
        {
            OnBoardItem item=new OnBoardItem();
            item.setImageID(imageId[i]);
            item.setTitle(getResources().getString(header[i]));

            item.setDescription(getResources().getString(desc[i]));

            onBoardItems.add(item);



        }
    }

    // Button bottomUp animation



    public void show_animation()

    {
        Animation show = AnimationUtils.loadAnimation(this, R.anim.slide_up_anim);

        //btn_get_started.startAnimation(show);
        loginBtn.startAnimation(show);

        show.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                //btn_get_started.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

               // btn_get_started.clearAnimation();
                loginBtn.clearAnimation();

            }

        });


    }

    // Button Topdown animation

    public void hide_animation()
    {
        Animation hide = AnimationUtils.loadAnimation(this, R.anim.slide_down_anim);

        //btn_get_started.startAnimation(hide);
        loginBtn.startAnimation(hide);

        hide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

               // btn_get_started.clearAnimation();
                loginBtn.clearAnimation();
                //btn_get_started.setVisibility(View.GONE);
                loginBtn.setVisibility(View.GONE);

            }

        });


    }

    // setup the
    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.non_selected_item_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(OnboardingActivity.this, R.drawable.selected_item_dot));
    }


}
