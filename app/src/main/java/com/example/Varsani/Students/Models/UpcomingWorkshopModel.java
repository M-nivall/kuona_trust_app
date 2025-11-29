package com.example.Varsani.Students.Models;

public class UpcomingWorkshopModel {
    private String workshopID;
    private String title;
    private String mentor;
    private String workshopDesc;
    private String workshopDate;
    private String venue;
    private String workshopType;
    private String bannerImg;
    private String workshopStatus;


    public UpcomingWorkshopModel(String workshopID, String mentor, String title, String workshopDesc, String workshopDate,
                         String venue, String workshopType, String bannerImg, String workshopStatus) {
        this.workshopID = workshopID;
        this.title = title;
        this.mentor = mentor;
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

    public String getMentor() {
        return mentor;
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
