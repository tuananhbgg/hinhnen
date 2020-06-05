package com.example.hinhnen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadImageFromUrl extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public LoadImageFromUrl(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL ulrn = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
            con.setAllowUserInteraction(false);
            con.setInstanceFollowRedirects(true);
            con.setRequestMethod("GET");
            InputStream is = con.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            con.disconnect();
            if (bitmap != null)
            {
                return bitmap;
            }
        }catch(Exception e){}

       return null;



    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
       imageView.setImageBitmap(bitmap);
    }
}