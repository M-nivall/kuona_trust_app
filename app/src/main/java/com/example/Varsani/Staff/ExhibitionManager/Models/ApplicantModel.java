package com.example.Varsani.Staff.ExhibitionManager.Models;

public class ApplicantModel {

    private String ID;
    private String exhibitionID;
    private String artistID;
    private String artistName;
    private String title;
    private String artDesc;
    private String imageName;
    private String exStatus;


    public ApplicantModel(String ID, String exhibitionID, String artistID,String artistName, String title,
                           String artDesc,String imageName, String exStatus) {
        this.ID = ID;
        this.exhibitionID = exhibitionID;
        this.artistID = artistID;
        this.artistName = artistName;
        this.title = title;
        this.artDesc = artDesc;
        this.imageName = imageName;
        this.exStatus  = exStatus;
    }

    public String getID() {
        return ID;
    }

    public String getExhibitionID() {
        return exhibitionID;
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
        return exStatus;
    }
}