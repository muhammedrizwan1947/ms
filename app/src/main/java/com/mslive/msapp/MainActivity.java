package com.mslive.msapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    //MainAdapter mainAdapter;
    interfacex interfacex;

    MainAdapter adapterc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler_view);

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
        adapterc=new MainAdapter(this,arrayList,this.interfacex);
       // mainAdapter.setClickListener(this);
        recyclerView.setAdapter(adapterc);


    }
/*
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, mainAdapter.getItem(position)+"+"+position, Toast.LENGTH_SHORT).show();
    }


 */

/*

 class adapter extends RecyclerView.Adapter<adapter.viewholder>{


     private Context mContext;
     public List<String> data;
     public LayoutInflater layoutInflater;

     private interfacex Interfacex;

     public adapter(Context mContext, List<String> data, interfacex interfacex) {
         this.mContext = mContext;
         this.data = data;
         Interfacex = interfacex;
     }

     @NonNull
     @Override
     public adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view=layoutInflater.inflate(R.layout.main_recycler_item,parent,false);
         return new viewholder(view);     }

     @Override
     public void onBindViewHolder(@NonNull adapter.viewholder holder, int position) {
         String number= data.get(position);
         holder.textView.setText(number);
     }

     @Override
     public int getItemCount() {
         return 0;
     }

     public class viewholder extends RecyclerView.ViewHolder {
         TextView textView;

         public viewholder(@NonNull View itemView) {
             super(itemView);


             textView=itemView.findViewById(R.id.text_view);

             textView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     Interfacex.onclick2(getAdapterPosition());
                 }
             });

         }
     }


 }


*/




}
