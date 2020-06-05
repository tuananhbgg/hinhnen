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

public class Album1_Fragment extends Fragment {
    private ArrayList<Image_Model> listImages;
    private Image_Adapter adapter;
    private GridView grdAlbum1;
    public static ProgressBar pbLoadAlbum1;
    private MyFirebase myFirebase;
    private CheckNetWork checkNetWork;
    private TextView tvInternet;

    public Album1_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_album1, container, false);
        myFirebase = new MyFirebase();
        myFirebase.initFirebase();
        checkNetWork = new CheckNetWork();
        listImages = new ArrayList<>();
        grdAlbum1 = view.findViewById(R.id.grdAlbum1);
        pbLoadAlbum1 = view.findViewById(R.id.pbLoadAlbum1);
        tvInternet = view.findViewById(R.id.tvInternet);
        adapter = new Image_Adapter(listImages, getLayoutInflater(), false);
        grdAlbum1.setAdapter(adapter);
        if(checkNetWork.checkNetWorkAvailable(getContext())){
            myFirebase.LoadListImage("Album1",listImages,adapter,pbLoadAlbum1);
        }else {
            pbLoadAlbum1.setVisibility(View.GONE);
            tvInternet.setVisibility(View.VISIBLE);
        }
        grdAlbum1.setOnItemClickListener(itemAlbum1Click);

        return view;
    }
    private AdapterView.OnItemClickListener itemAlbum1Click = new AdapterView.OnItemClickListener() {
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
