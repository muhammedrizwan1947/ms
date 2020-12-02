package com.mslive.msapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mslive.msapp.Model.WishlistModel;

import java.util.ArrayList;

public class WishlistAdaper extends RecyclerView.Adapter<WishlistAdaper.ViewHolder>{

    private ArrayList<WishlistModel> wishlistModels;


    public WishlistAdaper(ArrayList<WishlistModel> wishlistModels) {
        this.wishlistModels = wishlistModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return wishlistModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
        }
    }

}


