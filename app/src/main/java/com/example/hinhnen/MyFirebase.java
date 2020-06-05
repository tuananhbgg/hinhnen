package com.example.hinhnen;

import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hinhnen.Adapter.Image_Adapter;
import com.example.hinhnen.model.Image_Model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyFirebase {
    private DatabaseReference mData;
    public void initFirebase(){
        mData = FirebaseDatabase.getInstance().getReference();
    }
    public void LoadListImage(String folderName, final ArrayList<Image_Model> image_models, final Image_Adapter adapter, final ProgressBar progressBar){
        adapter.setListImage(image_models);
        mData.child(folderName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Image_Model model = dataSnapshot.getValue(Image_Model.class);
                image_models.add(new Image_Model(model.getImageUrl()));
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
