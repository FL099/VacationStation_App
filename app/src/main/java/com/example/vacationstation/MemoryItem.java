package com.example.vacationstation;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MemoryItem implements Parcelable {


    private String name;
    private String tags;
    private double coordLat;
    private double coordLon;
    private String imgPath;
    private String comment;

    public MemoryItem(String name, String tags, String comment, double coordLat, double coordLon, String imgPath) {
        this.name = name;
        this.tags = tags;
        this.coordLat = coordLat;
        this.coordLon = coordLon;
        this.imgPath = imgPath;
        this.comment = comment;
    }

    public MemoryItem(JSONObject result) {
        try{
            this.name = result.getString("filename");

            if (result.getJSONArray("tags") != null){
                int len = result.getJSONArray("tags").length();
                for (int i  = 0; i< len; i++){
                    if (i == 0){
                        this.tags = "";
                    }
                    else {
                        this.tags += ", ";
                    }
                    this.tags += result.getJSONArray("tags").getString(i);
                }

            }
            this.comment = result.getString("comment");
            String[] temp = result.getString("coordinates").split(" ");
            coordLat = Double.parseDouble(temp[0]);
            if (!temp[1].equals("N,")){
                coordLat = coordLat*-1;
            }
            coordLon = Double.parseDouble(temp[2]);
            if (!temp[3].equals("E")){
                coordLon = coordLon*-1;
            }
            this.imgPath = result.getString("filename");

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getMemoryItemData(){
        return "name: " + this.name + "\nTags: " + this.tags + "\nComment: " + this.comment + "\n coordinates: " + this.coordLat +", " + this.coordLon +" \n";
    }

    protected MemoryItem(Parcel in) {
        name = in.readString();
        tags = in.readString();
        comment = in.readString();
        coordLat = in.readDouble();
        coordLon = in.readDouble();
        imgPath = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(tags);
        dest.writeString(comment);
        dest.writeDouble(coordLat);
        dest.writeDouble(coordLon);
        dest.writeString(imgPath);

    }

    public static final Creator<MemoryItem> CREATOR = new Creator<MemoryItem>() {
        @Override
        public MemoryItem createFromParcel(Parcel in) {
            return new MemoryItem(in);
        }

        @Override
        public MemoryItem[] newArray(int size) {
            return new MemoryItem[size];
        }
    };





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getCoordLat() {
        return coordLat;
    }

    public void setCoordLat(double coordLat) {
        this.coordLat = coordLat;
    }

    public double getCoordLon() {
        return coordLon;
    }

    public void setCoordLon(double coordLat) {
        this.coordLon = coordLon;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}