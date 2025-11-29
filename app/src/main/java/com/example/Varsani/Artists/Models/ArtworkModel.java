package com.example.Varsani.Artists.Models;

public class ArtworkModel {
    private String artID;
    private String artistID;
    private String title;
    private String desc;
    private String imgUrl;
    private String fullName;
    private String username;


    public ArtworkModel(String artID, String artistID, String title, String desc, String imgUrl, String fullName, String username) {
        this.artID = artID;
        this.artistID = artistID;
        this.title = title;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.fullName = fullName;
        this.username = username;
    }

    public String getArtID() {
        return artID;
    }

    public String getArtistID() {
        return artistID;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public String getFullName() {
        return fullName;
    }
    public String getUsername() {
        return username;
    }

}
