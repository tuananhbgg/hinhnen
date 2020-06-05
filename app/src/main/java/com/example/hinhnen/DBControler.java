package com.example.hinhnen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hinhnen.model.Image_Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DBControler extends SQLiteOpenHelper {
    private static final String dbName = "dbImage.db";
    private static final int version = 1;
    private SQLiteDatabase database;
    private Context context;

    public DBControler(Context context) {
        super(context, dbName, null, version);
        this.context = context;
    }
    public void initDB(){
        if(checkExistsDB()) {
            database = getWritableDatabase();
        }else {
            database = getWritableDatabase();
            database.close();
            copyDB();
            database = getWritableDatabase();
        }
    }

    private void copyDB() {
        try {
            InputStream inputStream = context.getAssets().open(dbName);
            FileOutputStream outputStream = new FileOutputStream(context.getDatabasePath(dbName));
            byte data[] = new byte[2048];
            int status = inputStream.read(data);
            while (status!= -1){
                outputStream.write(data);
                status = inputStream.read(data);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean checkExistsDB(){
        File myDB = context.getDatabasePath(dbName);
        return myDB.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public ArrayList<Image_Model> getListImage(){
        ArrayList<Image_Model> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from image",null);
        while (cursor.moveToNext()){
            String imageName = cursor.getString(0);
            list.add(new Image_Model(imageName));
        }
        return list;
    }
}
