package com.example.Varsani.Staff.ExhibitionManager.Models;

public class InventoryModel {
    private String ID;
    private String title;
    private String imageName;
    private String exhibitionID;
    private String artistID;
    private String artistName;
    private String startingDate;

    public InventoryModel(String ID, String title, String imageName,String exhibitionID,
                          String artistID, String artistName, String startingDate){
        this.title=title;
        this.imageName=imageName;
        this.ID=ID;
        this.exhibitionID=exhibitionID;
        this.artistName=artistName;
        this.artistID=artistID;
        this.startingDate=startingDate;
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getImageName() {
        return imageName;
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

    public String getStartingDate() {
        return startingDate;
    }
}
