package com.nbu.logisticcompany.entities.dto;

public class ShipmentRegisterDTO {

    private int id;
    private String arrivalAddress;
    private String departureAddress;

    private double weight;

    private int senderID;

    private int receiverID;

    private int employeeID;

    private  boolean isSentFromOffice;
    private  boolean isReceivedFromOffice;

    public ShipmentRegisterDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArrivalAddress() {
        return arrivalAddress;
    }

    public void setArrivalAddress(String arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    public String getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(String departureAddress) {
        this.departureAddress = departureAddress;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public boolean isSentFromOffice() {
        return isSentFromOffice;
    }

    public void setSentFromOffice(boolean sentFromOffice) {
        isSentFromOffice = sentFromOffice;
    }

    public boolean isReceivedFromOffice() {
        return isReceivedFromOffice;
    }

    public void setReceivedFromOffice(boolean receivedFromOffice) {
        isReceivedFromOffice = receivedFromOffice;
    }
}
