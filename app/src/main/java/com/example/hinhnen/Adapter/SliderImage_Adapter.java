package com.example.hinhnen.Adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.hinhnen.model.Image_Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SliderImage_Adapter extends PagerAdapter {

    private ArrayList<Image_Model> urls;
    private boolean isOnLine;
    public HashMap<Integer, ImageView> views = new HashMap();

    public SliderImage_Adapter(ArrayList<Image_Model> urls, boolean isOnLine) {
        this.urls = urls;
        this.isOnLine = isOnLine;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Tạo ImageView, container chính là ViewPager của chúng ta
        Context context = container.getContext();
        AppCompatImageView imageView = new AppCompatImageView(context);
        container.addView(imageView);
        if (isOnLine) {
            Glide.with(context)
                    .load(urls.get(position).getImageUrl())
                    .into(imageView);

        } else {
            Uri imageUri = Uri.fromFile(new File("//android_asset/" + urls.get(position).getImageUrl()));
            Glide.with(context)
                    .load(imageUri)
                    .into(imageView);
        }
        views.put(position, imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // container chính là ViewPager, còn Object chính là return của instantiateItem ứng với position
        views.remove(position, object);
        container.removeView((View) object);
    }
}
