package com.example.hinhnen.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image_Model implements Parcelable {
    private String imageUrl;

    public Image_Model(){

    }

    protected Image_Model(Parcel in) {
        imageUrl = in.readString();
    }

    public static final Creator<Image_Model> CREATOR = new Creator<Image_Model>() {
        @Override
        public Image_Model createFromParcel(Parcel in) {
            return new Image_Model(in);
        }

        @Override
        public Image_Model[] newArray(int size) {
            return new Image_Model[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Image_Model(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageUrl);
    }
    public static final Creator<Image_Model> creator = new Creator<Image_Model>() {
        @Override
        public Image_Model createFromParcel(Parcel source) {
            return new Image_Model(source);
        }

        @Override
        public Image_Model[] newArray(int size) {
            return new Image_Model[size];
        }
    };
}
