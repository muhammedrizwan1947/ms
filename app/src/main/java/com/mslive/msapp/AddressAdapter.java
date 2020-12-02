package com.mslive.msapp;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Adressmodel> mUploads;
    private OnItemClickListener mListener;
    int row_index=-1;


    public AddressAdapter(Context context, List<Adressmodel> uploads){
        mContext=context;
        mUploads=uploads;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(mContext).inflate(R.layout.address_item,parent,false);
        return new ImageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder,final int position) {
        final Adressmodel uploadCurrent= mUploads.get(position);



        String fullAddress = uploadCurrent.getAddress()
                +  uploadCurrent.getState() + ""  + "\t\t - " + uploadCurrent.getPincode();






       holder.name.setText(uploadCurrent.getName());
       holder.address.setText(fullAddress);

        holder.phone.setText(uploadCurrent.getPhone());


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();

               // Toast.makeText(mContext, "position" + uploadCurrent.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        if (row_index==position){
            holder.linearLayout.setBackgroundResource(R.drawable.address_select);

        }else {
            holder.linearLayout.setBackgroundResource(R.drawable.white_bg_3);

        }






    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        LinearLayout linearLayout;

        public TextView name,address,phone;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            name= itemView.findViewById(R.id.tv_name);
            address= itemView.findViewById(R.id.tv_address);
            phone= itemView.findViewById(R.id.tv_phone);

            linearLayout=itemView.findViewById(R.id.root_view);








            itemView.setOnClickListener(this);
            //itemView.setOnCreateContextMenuListener(this);


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

        /*

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


         */
    }




    public  interface OnItemClickListener{
        void onItemClick(int position);

       // void onWhateverClick(int position);


       // void onDeleteClick(int position);

    }


    public  void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;


    }

}


