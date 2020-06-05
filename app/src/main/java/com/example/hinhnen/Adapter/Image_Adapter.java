package com.example.hinhnen.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hinhnen.LoadImageFromUrl;
import com.example.hinhnen.R;
import com.example.hinhnen.model.Image_Model;

import java.io.File;
import java.util.ArrayList;

public class Image_Adapter extends BaseAdapter {
    private ArrayList<Image_Model> lists;
    private LayoutInflater inflater;
    private boolean isOffline;

    public void setListImage(ArrayList<Image_Model> listImage) {
        this.lists = listImage;
    }

    public Image_Adapter(ArrayList<Image_Model> listImage, LayoutInflater inflater, boolean isOffline) {
        this.lists = listImage;
        this.inflater = inflater;
        this.isOffline = isOffline;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Image_Model getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_image, null);
        }
        ImageView imgImage = view.findViewById(R.id.imgImage);
        if (isOffline) {
            //try {
            Uri imageUri = Uri.fromFile(new File("//android_asset/" + lists.get(position).getImageUrl()));
//                AssetManager assetManager = view.getContext().getAssets();
//                InputStream inputStream = assetManager.open(listImage.get(position).getImageUrl());
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                imgImage.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            Glide.with(view.getContext())
                    .load(imageUri)
                    .into(imgImage);
        } else {
            //new LoadImageFromUrl(imgImage).execute(lists.get(position).getImageUrl());
            Glide.with(view.getContext())
                    .load(lists.get(position).getImageUrl())
                    .into(imgImage);
        }

        return view;
    }

}
