package com.example.Varsani.Staff.ExhibitionManager.Models;

public class PendingArtworkModel {
    private String ID;
    private String artistID;
    private String artistName;
    private String title;
    private String artDesc;
    private String imageName;
    private String artStatus;


    public PendingArtworkModel(String ID, String artistID,String artistName, String title,
                          String artDesc,String imageName, String artStatus) {
        this.ID = ID;
        this.artistID = artistID;
        this.artistName = artistName;
        this.title = title;
        this.artDesc = artDesc;
        this.imageName = imageName;
        this.artStatus  = artStatus;
    }

    public String getID() {
        return ID;
    }


    public String getArtistID() {
        return artistID;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTitle() {
        return title;
    }

    public String getArtDesc() {
        return artDesc;
    }

    public String getImageName() {
        return imageName;
    }

    public String getExStatus() {
        return artStatus;
    }
}
