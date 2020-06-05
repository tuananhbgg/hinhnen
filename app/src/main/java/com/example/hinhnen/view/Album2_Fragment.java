package com.example.hinhnen.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hinhnen.CheckNetWork;
import com.example.hinhnen.Adapter.Image_Adapter;
import com.example.hinhnen.MyFirebase;
import com.example.hinhnen.R;
import com.example.hinhnen.model.Image_Model;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Album2_Fragment extends Fragment {

    private ArrayList<Image_Model> listImages;
    private Image_Adapter adapter;
    private GridView grdDogPhoto;
    public static ProgressBar pbLoadDogPhoto;
    private MyFirebase myFirebase;
    private CheckNetWork checkNetWork;
    private TextView tvInternet;

    public Album2_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album2, container, false);
        myFirebase = new MyFirebase();
        myFirebase.initFirebase();
        listImages = new ArrayList<>();
        grdDogPhoto = view.findViewById(R.id.grdDogPhoto);
        tvInternet = view.findViewById(R.id.tvInternet);
        pbLoadDogPhoto = view.findViewById(R.id.pbLoadDogPhoto);
        checkNetWork = new CheckNetWork();

        adapter = new Image_Adapter(listImages, getLayoutInflater(), false);
        grdDogPhoto.setAdapter(adapter);
        if(checkNetWork.checkNetWorkAvailable(getContext())){
            myFirebase.LoadListImage("DogPhoto",listImages,adapter,pbLoadDogPhoto);
        }else {
            pbLoadDogPhoto.setVisibility(View.GONE);
            tvInternet.setVisibility(View.VISIBLE);
        }
        grdDogPhoto.setOnItemClickListener(itemDogPhoto);

        return view;
    }
    private AdapterView.OnItemClickListener itemDogPhoto = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getContext(), ViewSlideImage.class);
            intent.putExtra("viTri",i);
            intent.putExtra("imageName", listImages.get(i).getImageUrl());
            intent.putExtra("onLine",true);
            intent.putParcelableArrayListExtra("listUrl",listImages);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),view.findViewById(R.id.imgImage),"transition");
            startActivity(intent,optionsCompat.toBundle());
        }
    };

}
