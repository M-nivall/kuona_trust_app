package com.example.Varsani.Artists.Models;

public class MyDonationsModel {
    private String donationID;
    private String donorID;
    private String artistName;
    private String donorName;
    private String artID;
    private String artistID;
    private String amount;
    private String paymentMethod;
    private String referenceCode;
    private String donationStatus;
    private String donationDate;
    private String title;

    public MyDonationsModel(String donationID, String donorID, String artistName,
                            String donorName, String artID, String artistID, String amount, String paymentMethod,
                            String referenceCode, String donationStatus, String donationDate, String title){
        this.donationID=donationID ;
        this.donorID=donorID ;
        this.artistName=artistName ;
        this.donorName =donorName ;
        this.artID=artID ;
        this.artistID=artistID ;
        this.amount=amount ;
        this.paymentMethod=paymentMethod ;
        this.referenceCode=referenceCode ;
        this.donationStatus=donationStatus ;
        this.donationDate=donationDate;
        this.title = title;
    }

    public String getDonationID() {
        return donationID;
    }

    public String getDonorID() {
        return donorID;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getArtID() {
        return artID;
    }

    public String getArtistID() {
        return artistID;
    }

    public String getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public String getDonationStatus() {
        return donationStatus;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public String getTitle() {
        return title;
    }
}
