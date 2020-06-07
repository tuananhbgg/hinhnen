package com.example.hinhnen.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hinhnen.R;
import com.example.hinhnen.Adapter.SliderImage_Adapter;
import com.example.hinhnen.model.Image_Model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ViewSlideImage extends AppCompatActivity {

    private ViewPager vpSliderImage;
    private ArrayList<Image_Model> models;
    private SliderImage_Adapter adapter;
    private boolean isOnLine;
    private ImageView datLamHinhNen, taiAnh, chiase;
    private int viTri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slide_image);
        vpSliderImage = findViewById(R.id.vpSliderImage);
        datLamHinhNen = findViewById(R.id.datLamHinhNen);
        taiAnh = findViewById(R.id.taiAnh);
        chiase = findViewById(R.id.chiase);
        models = new ArrayList<>();

        if (getIntent() != null) {
            models = getIntent().getParcelableArrayListExtra("listUrl");
            isOnLine = getIntent().getBooleanExtra("onLine", true);
        }
        viTri = getIntent().getIntExtra("viTri", 12);
        adapter = new SliderImage_Adapter(models, isOnLine);
        vpSliderImage.setAdapter(adapter);
        vpSliderImage.setCurrentItem(viTri);


        datLamHinhNen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int height = metrics.heightPixels;
                int width = metrics.widthPixels;

                BitmapDrawable drawable = (BitmapDrawable) getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                Bitmap bitmapScale = Bitmap.createScaledBitmap(bitmap, width, height, true);

                WallpaperManager wallpaperManager = WallpaperManager.getInstance(ViewSlideImage.this);
                wallpaperManager.setWallpaperOffsetSteps(1, 1);
                wallpaperManager.suggestDesiredDimensions(width, height);
                try {
                    wallpaperManager.setBitmap(bitmapScale);
                    Toast.makeText(ViewSlideImage.this, "Completed", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        taiAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ViewSlideImage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    BitmapDrawable drawable = (BitmapDrawable) getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    File filePath = Environment.getExternalStorageDirectory();
                    File dir = new File(filePath.getAbsolutePath() + "/Images");
                    dir.mkdir();
                    File myFile = new File(dir, System.currentTimeMillis() + ".jpg");
                    try {
                        FileOutputStream outputStream = new FileOutputStream(myFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();
                        Toast.makeText(ViewSlideImage.this, "Saved", Toast.LENGTH_SHORT).show();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    requestStoragePermisstion();
                }
            }
        });

        chiase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: API thấp bị lỗi không lấy được path bằng MediaStore
//                Drawable draw = getDrawable();
//                BitmapDrawable drawable = (BitmapDrawable) draw;
//                Bitmap bitmap = drawable.getBitmap();
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null);
//                if (path != null) {
//                    Uri mImageUri = Uri.parse(path);
//                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                    sharingIntent.setType("image/jpeg");
//                    sharingIntent.putExtra(Intent.EXTRA_STREAM, mImageUri);
//                    startActivity(Intent.createChooser(sharingIntent, "Share Using"));
//                }else {
//                    Toast.makeText(ViewSlideImage.this, "Error", Toast.LENGTH_SHORT).show();
//                }

                if (ContextCompat.checkSelfPermission(ViewSlideImage.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Drawable draw = getDrawable();
                    BitmapDrawable drawable = (BitmapDrawable) draw;
                    Bitmap bitmap = drawable.getBitmap();
                    File tempDir = Environment.getExternalStorageDirectory();
                    tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
                    tempDir.mkdir();
                    File tempFile = null;
                    try {
                        tempFile = File.createTempFile("title", ".jpg", tempDir);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    byte[] bitmapData = bytes.toByteArray();

                    //write the bytes in file
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(tempFile);
                        fos.write(bitmapData);
                        fos.flush();
                        fos.close();
                        Uri imageUri = Uri.fromFile(tempFile);
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("image/jpeg");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        startActivity(Intent.createChooser(sharingIntent, "Share Using"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    requestStoragePermisstion();
                }


            }
        });
    }

    private void requestStoragePermisstion() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ViewSlideImage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(ViewSlideImage.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setMessage("Cho phép truy cập vào bộ nhớ")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(ViewSlideImage.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        } else {
            ActivityCompat.requestPermissions(ViewSlideImage.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            Toast.makeText(this, "không có quyền truy cập bộ nhớ", Toast.LENGTH_SHORT).show();
        }
    }

    private Drawable getDrawable() {
        SliderImage_Adapter adapter = ((SliderImage_Adapter) vpSliderImage.getAdapter());
        Drawable target = (adapter.views.get(vpSliderImage.getCurrentItem())).getDrawable();
        return target;
    }

}
