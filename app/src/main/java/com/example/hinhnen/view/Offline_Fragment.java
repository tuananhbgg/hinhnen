package com.example.hinhnen.view;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.hinhnen.DBControler;
import com.example.hinhnen.Adapter.Image_Adapter;
import com.example.hinhnen.R;
import com.example.hinhnen.model.Image_Model;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Offline_Fragment extends Fragment {
    private GridView grdOffline;
    private Image_Adapter adapter;
    private DBControler dbControler;
    private ArrayList<Image_Model> listImages;


    public Offline_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        dbControler = new DBControler(getContext());
        dbControler.initDB();
        listImages = new ArrayList<>();


        grdOffline = view.findViewById(R.id.grdOffline);
        adapter = new Image_Adapter(listImages, getLayoutInflater(),true);
        grdOffline.setAdapter(adapter);
        loadImage();
        grdOffline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ViewSlideImage.class);
                intent.putExtra("viTri",i);
                intent.putExtra("imageName", listImages.get(i).getImageUrl());
                intent.putExtra("onLine",false);
                intent.putParcelableArrayListExtra("listUrl",listImages);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),view.findViewById(R.id.imgImage),"transition");
                startActivity(intent,optionsCompat.toBundle());

            }
        });
        return view;
    }

    private void loadImage(){
        new loadData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    class loadData extends AsyncTask<Void, Void, ArrayList<Image_Model>> {
        @Override
        protected ArrayList<Image_Model> doInBackground(Void... voids) {
            ArrayList<Image_Model> lists = dbControler.getListImage();
            return lists;
        }
        @Override
        protected void onPostExecute(ArrayList<Image_Model> image_models) {
            super.onPostExecute(image_models);
            listImages = image_models;
            adapter.setListImage(listImages);
            adapter.notifyDataSetChanged();
        }
    }


}
