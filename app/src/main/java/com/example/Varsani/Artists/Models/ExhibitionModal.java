package com.example.Varsani.Artists.Models;

public class ExhibitionModal {

    private String exhibitionID;
    private String title;
    private String exhibitionDesc;
    private String startingDate;
    private String endDate;
    private String venue;
    private String exhibitionType;
    private String bannerImg;
    private String visibility;


    public ExhibitionModal(String exhibitionID, String title, String exhibitionDesc, String startingDate,
                           String endDate,String venue, String exhibitionType, String bannerImg, String visibility) {
        this.exhibitionID = exhibitionID;
        this.title = title;
        this.exhibitionDesc = exhibitionDesc;
        this.startingDate = startingDate;
        this.endDate = endDate;
        this.venue = venue;
        this.exhibitionType  = exhibitionType;
        this.bannerImg = bannerImg;
        this.visibility = visibility;
    }

    public String getExhibitionID() {
        return exhibitionID;
    }

    public String getTitle() {
        return title;
    }

    public String getExhibitionDesc() {
        return exhibitionDesc;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getVenue() {
        return venue;
    }
    public String getExhibitionType() {
        return exhibitionType;
    }
    public String getBannerImg() {
        return bannerImg;
    }
    public String getVisibility() {
        return visibility;
    }
}
