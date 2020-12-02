package com.mslive.msapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

  public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

      private Context mContext;
    public List<String> data;
    public LayoutInflater layoutInflater;

      private interfacex Interfacex;
   // public ItemClickListener itemClickListener;


    public MainAdapter(Context context,List<String> data,interfacex interfacexx){
        this.layoutInflater=LayoutInflater.from(context);
        this.data=data;
        this.mContext=context;
        this.Interfacex=interfacexx;



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.main_recycler_item,parent,false);
        return new ViewHolder(view);
    }

      @Override
      public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
          String number= data.get(position);
          holder.textView.setText(number);







      }





    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text_view);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Interfacex.onclick2(getAdapterPosition());
                }
            });


          //  itemView.setOnClickListener(this);
        }
/*

        @Override
        public void onClick(View view) {
            if (itemClickListener!=null) {
                itemClickListener.onItemClick(view,getAdapterPosition());
            }


        }

 */
    }


    String getItem(int id){
        return data.get(id);
    }

/*
    void setClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
*/

    public interface ItemClickListener {
        void onItemClick(View view,int position);
    }
}
