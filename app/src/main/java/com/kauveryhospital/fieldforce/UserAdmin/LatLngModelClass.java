package com.kauveryhospital.fieldforce.UserAdmin;

import android.os.Parcel;
import android.os.Parcelable;

public class LatLngModelClass implements Parcelable {


    private int id;


    private double latitude;

    private double longitude;

    public LatLngModelClass() {
    }

    protected LatLngModelClass(Parcel in) {
        id = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<LatLngModelClass> CREATOR = new Creator<LatLngModelClass>() {
        @Override
        public LatLngModelClass createFromParcel(Parcel in) {
            return new LatLngModelClass(in);
        }

        @Override
        public LatLngModelClass[] newArray(int size) {
            return new LatLngModelClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
