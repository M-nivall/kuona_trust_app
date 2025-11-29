package com.example.Varsani.Staff.Finance.Models;

public class DonationsModel {
    String donationID;
    String donorID;
    String artistName;
    String donorName;
    String artID;
    String artistID;
    String amount;
    String paymentMethod;
    String referenceCode;
    String donationStatus;

    public DonationsModel(String donationID, String donorID, String artistName,
                            String donorName, String artID, String artistID,String amount,String paymentMethod
            ,String referenceCode,String donationStatus){
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

}
