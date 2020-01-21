package com.example.hamadaelsha3r.intouch;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity(tableName = "newss")
public class Model implements Parcelable, Serializable {


    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("srcname")
    @Expose
    private String srcName;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("Date")
    @Expose
    private String Date;

    @SerializedName("Img")
    @Expose
    private String Img;


    @SerializedName("content")
    @Expose
    private String content;

    public Model(String id, String srcName, String title, String Description, String url, String Date, String Img, String content) {

        this.id = id;
        this.srcName = srcName;
        this.title = title;
        this.Description = Description;
        this.url = url;
        this.Date = Date;
        this.Img = Img;
        this.content = content;
    }

    protected Model(Parcel in) {
        id = in.readString();
        srcName = in.readString();
        title = in.readString();
        Description = in.readString();
        url = in.readString();
        Date = in.readString();
        Img = in.readString();
        content = in.readString();
    }

    @Ignore
    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(srcName);
        parcel.writeString(title);
        parcel.writeString(Description);
        parcel.writeString(url);
        parcel.writeString(Date);
        parcel.writeString(Img);
        parcel.writeString(content);
    }
}
