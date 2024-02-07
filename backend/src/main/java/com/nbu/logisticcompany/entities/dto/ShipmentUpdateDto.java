package com.nbu.logisticcompany.entities.dto;

public class ShipmentUpdateDto {

    private  int id;
    private String departureAddress;
    private String arrivalAddress;

    private int senderID;
    private int receiverID;
    private int employeeID;

    private boolean isSentFromOffice;
    private boolean isReceivedFromOffice;

    public ShipmentUpdateDto() {
    }

    public ShipmentUpdateDto(int id, String departureAddress, String arrivalAddress, int senderID, int receiverID, int employeeID, boolean isSentFromOffice, boolean isReceivedFromOffice) {
        this.id = id;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.employeeID = employeeID;
        this.isSentFromOffice = isSentFromOffice;
        this.isReceivedFromOffice = isReceivedFromOffice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(String departureAddress) {
        this.departureAddress = departureAddress;
    }

    public String getArrivalAddress() {
        return arrivalAddress;
    }

    public void setArrivalAddress(String arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
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
