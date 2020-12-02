package com.mslive.msapp;

import android.content.Context;
import android.content.Intent;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Cartmodel> mUploads;
   // private OnItemClickListener mListener;
    private interfacex Interfacex;

    int total;
    int i=0;



    public CartAdapter(Context context, List<Cartmodel> uploads,interfacex interfacexx){
        mContext=context;
        mUploads=uploads;
        Interfacex=interfacexx;


    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(mContext).inflate(R.layout.cart_item_3,parent,false);
        return new ImageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        Cartmodel uploadCurrent= mUploads.get(position);







        final String quantity=uploadCurrent.getQuantity();
        holder.tt.setText(quantity);



        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("productdetails").child(uploadCurrent.getProductid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String imageurl = snapshot.child("imageurl").getValue().toString();
               final String productname=snapshot.child("productname").getValue().toString();
               final String productprice=snapshot.child("productprice").getValue().toString();

               int op=Integer.parseInt(productprice);

              // total=total+op;



               if (i<getItemCount()) {
                   total += Integer.parseInt(productprice);
               }
               else {
                   Intent intent=new Intent(mContext,CartActivity.class);
                   //intent.putExtra("total",Integer.toString(total));
                   intent.putExtra("total","abcd");
                   mContext.startActivity(intent);
               }




                Picasso.get().load(imageurl).placeholder(R.mipmap.ic_launcher)
                        .fit().into(holder.productImage);


                holder.productName.setText(productname);
                holder.productPrice.setText(productprice);





                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(mContext, "delete" + productname, Toast.LENGTH_SHORT).show();

                        Toast.makeText(mContext, getItemCount()+"delete" + total, Toast.LENGTH_SHORT).show();

                    }
                });








            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {


        public TextView productName, productPrice,tt;
        public ImageView productImage, delete,ti;

        LinearLayout  sizeDropdownLinear;

        CardView qtyDropdownLinear;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.tv_product_name);
            qtyDropdownLinear = itemView.findViewById(R.id.qty_drop_down_linear);
            sizeDropdownLinear = itemView.findViewById(R.id.size_drop_down_linear);
            productPrice = itemView.findViewById(R.id.tv_product_price);
            productImage = itemView.findViewById(R.id.image_view_product);
            delete = itemView.findViewById(R.id.delete);


            ti = itemView.findViewById(R.id.ti);
            tt = itemView.findViewById(R.id.tt);


            ti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Interfacex.onclick(getAdapterPosition());
                }
            });


            tt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Interfacex.onclick(getAdapterPosition());
                }
            });











        }





        }


    }









