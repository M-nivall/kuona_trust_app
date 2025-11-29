package com.example.Varsani.Staff.Mentor.Models;

public class WorkshopApplicantModel {

    private String ID;
    private String workshopID;
    private String studentID;
    private String studentNames;
    private String attendanceStatus;

    public WorkshopApplicantModel(String ID, String workshopID, String studentID,String studentNames, String attendanceStatus) {
        this.ID = ID;
        this.workshopID = workshopID;
        this.studentID = studentID;
        this.studentNames = studentNames;
        this.attendanceStatus = attendanceStatus;
    }

    public String getID() {
        return ID;
    }

    public String getWorkshopID() {
        return workshopID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStudentNames() {
        return studentNames;
    }
    public String getAttendanceStatus() {
        return attendanceStatus;
    }

}
