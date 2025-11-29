package com.example.Varsani.Staff.Mentor.Models;

public class WorkshopModel {
    private String workshopID;
    private String title;
    private String workshopDesc;
    private String workshopDate;
    private String venue;
    private String workshopType;
    private String bannerImg;
    private  String workshopStatus;


    public WorkshopModel(String workshopID, String title, String workshopDesc, String workshopDate,
                           String venue, String workshopType, String bannerImg, String workshopStatus) {
        this.workshopID = workshopID;
        this.title = title;
        this.workshopDesc = workshopDesc;
        this.workshopDate = workshopDate;
        this.venue = venue;
        this.workshopType  = workshopType;
        this.bannerImg = bannerImg;
        this.workshopStatus = workshopStatus;
    }

    public String getWorkshopID() {
        return workshopID;
    }

    public String getTitle() {
        return title;
    }

    public String getWorkshopDesc() {
        return workshopDesc;
    }

    public String getWorkshopDate() {
        return workshopDate;
    }

    public String getVenue() {
        return venue;
    }
    public String getWorkshopType() {
        return workshopType;
    }
    public String getBannerImg() {
        return bannerImg;
    }
    public String getWorkshopStatus() {
        return workshopStatus;
    }
}
