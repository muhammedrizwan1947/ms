package com.mslive.msapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DiversionActivity extends AppCompatActivity {

    Button addDataButton,toMainButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diversion);

        addDataButton=(Button)findViewById(R.id.button_add_data);
        toMainButton=(Button)findViewById(R.id.button_to_main);

        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DiversionActivity.this,MainActivity3.class);
                //Intent intent= new Intent(DiversionActivity.this,AddProductActivity2.class);
                startActivity(intent);
            }
        });


        toMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DiversionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
