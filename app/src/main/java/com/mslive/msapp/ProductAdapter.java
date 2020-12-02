package com.mslive.msapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.CollegeViewHolder> {

    private Context mContext;
    private List<PModel> mUploads;
    private OnItemClickListener mListener;
    private int gridWidth,imageWidth,imageHeight;

    HashMap<String,String> map;





    public ProductAdapter(Context context, List<PModel> uploads){
        mContext=context;
        mUploads=uploads;



    }




    @NonNull
    @Override
    public CollegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.product_item2,parent,false);
        return new CollegeViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final CollegeViewHolder holder, int position) {

        final FirebaseUser currentUser;
        currentUser= FirebaseAuth.getInstance().getCurrentUser();


        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("wishlist").child(currentUser.getUid());

        gridWidth=mContext.getResources().getDisplayMetrics().widthPixels;
        imageWidth=gridWidth/2;
        imageHeight=imageWidth*3/2;


        final PModel uploadCurrent= mUploads.get(position);


        holder.mImageurl.requestLayout();
        holder.mImageurl.getLayoutParams().width=imageWidth;
        holder.mImageurl.getLayoutParams().height=imageHeight;
        Picasso.get().load(uploadCurrent.getImageurl()).placeholder(R.drawable.bgwh)
                .fit().into(holder.mImageurl);


        holder.mProductname.setText(uploadCurrent.getProductname());

//        holder.mProductnumber.setText(uploadCurrent.getProductnumber());
        String x= "Rs " + uploadCurrent.getProductprice();
        holder.mProductprice.setText(x);

        holder.mProductLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {








                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){

                        }
                        else {
                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("wishlist").child(currentUser.getUid()).push();

                            Map<String,Object> map =new HashMap<>();
                            map.put("productid",uploadCurrent.getKey());
                            map.put("size","notselected");
                            map.put("quantity","notselected");

                            databaseReference2.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {



                                }
                            });




                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });




    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }











    public class CollegeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
            View.OnCreateContextMenuListener , MenuItem.OnMenuItemClickListener{

     public    TextView mId,mProductname,mProductdescription,mProductnumber,mProductprice,mSmallsize,mMediumsize,mLargesize,mWeartype;
     public    ImageView mImageurl,mProductLike;


        public CollegeViewHolder(@NonNull View itemView) {
            super(itemView);


            mProductname=itemView.findViewById(R.id.product_name);

           // mProductnumber=itemView.findViewById(R.id.product_number);
            mProductprice = itemView.findViewById(R.id.product_price);
            mImageurl=itemView.findViewById(R.id.product_image_url);

            mProductLike=itemView.findViewById(R.id.product_like);



            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mListener!= null){

                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");

            MenuItem dowhatever =menu.add(Menu.NONE,1,1,"do whatever");

            MenuItem delete =menu.add(Menu.NONE,2,2,"Delete");


            dowhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);



        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            if (mListener!= null){

                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onWhateverClick(position);
                            return true;


                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }

            return false;
        }
    }

    public  interface OnItemClickListener{
        void onItemClick(int position);

        void onWhateverClick(int position);


        void onDeleteClick(int position);

    }


    public  void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;

    }



}
