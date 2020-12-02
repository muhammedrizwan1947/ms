package com.mslive.msapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FourFragment extends Fragment implements ProductAdapter.OnItemClickListener {


    private RecyclerView mRecyclerView;




    //#
    private ProductAdapter mAdapter;
    private DatabaseReference mDatabaseRef,userdetailsRef;


    FirebaseUser currentUser;
    ProgressBar progressBar;

    //#
    private List<PModel> mUploads;
    Toolbar toolBar;

    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;


    public static FourFragment newInstance() {
        return new FourFragment();
    }











    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, container, false);

        mRecyclerView = v.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        progressBar=v.findViewById(R.id.progress_bar);

       // linearLayoutManager= new LinearLayoutManager(getActivity());
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);

        gridLayoutManager= new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
       // gridLayoutManager.setReverseLayout(true);

        currentUser= FirebaseAuth.getInstance().getCurrentUser();


        //mProgressCircle = v.findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();



        //#
        mAdapter = new ProductAdapter(getContext(),mUploads);


        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);






        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("productdetails");
        userdetailsRef= FirebaseDatabase.getInstance().getReference().child("userdetail").child(currentUser.getUid());












                            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                                    mUploads.clear();
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){



                                        //#
                                        PModel upload= postSnapshot.getValue(PModel.class);

                                        //to get key for onclick
                                        //must be above mUploads.add(upload);

                                        upload.setKey(postSnapshot.getKey());

                                        mUploads.add(upload);



                                    }

                                    mAdapter.notifyDataSetChanged();

                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                    Toast.makeText(getContext() , databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);


                                }
                            });





















































        return v;










    }






    @Override
    public void onStart() {
        super.onStart();





    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "onclick"+ position, Toast.LENGTH_SHORT).show();

        PModel selectedItem= mUploads.get(position);
        String selectedKey= selectedItem.getKey();


        Intent intent =new Intent(getActivity(),ProductDetailsActivity2.class);
        intent.putExtra("productid",selectedKey);

        startActivity(intent);



    }

    @Override
    public void onWhateverClick(int position) {
        Toast.makeText(getActivity(), "whatever"+ position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteClick(int position) {
        Toast.makeText(getActivity(), "delete"+ position, Toast.LENGTH_SHORT).show();



    }









}
