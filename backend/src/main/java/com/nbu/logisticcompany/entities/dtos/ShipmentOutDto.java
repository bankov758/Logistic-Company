package com.nbu.logisticcompany.entities.dtos;

import java.time.LocalDateTime;

public class ShipmentOutDto {

    private int id;
    private String departureAddress;
    private String arrivalAddress;
    private int senderId;
    private int receiverId;
    private int employeeId;
    private boolean isSentFromOffice;
    private boolean isReceivedFromOffice;
    private double price;
    private LocalDateTime sentDate;
    private LocalDateTime receivedDate;
    private int courierId;

    public ShipmentOutDto() {
    }

    public ShipmentOutDto(int id, String departureAddress, String arrivalAddress, int senderId, int receiverId,
                          int employeeId, boolean isSentFromOffice, boolean isReceivedFromOffice, double price,
                          LocalDateTime sentDate, LocalDateTime receivedDate, int courierId) {
        this.id = id;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.employeeId = employeeId;
        this.isSentFromOffice = isSentFromOffice;
        this.isReceivedFromOffice = isReceivedFromOffice;
        this.price = price;
        this.sentDate = sentDate;
        this.receivedDate = receivedDate;
        this.courierId = courierId;
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

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public LocalDateTime getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }
}
