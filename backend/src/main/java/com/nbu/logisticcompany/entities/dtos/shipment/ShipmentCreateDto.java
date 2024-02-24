package com.nbu.logisticcompany.entities.dtos.shipment;

import java.time.LocalDateTime;

public class ShipmentCreateDto {

    private String arrivalAddress;
    private String departureAddress;
    private double weight;
    private int senderId;
    private int receiverId;
    private int employeeId;
    private boolean isSentFromOffice;
    private boolean isReceivedFromOffice;
    private double price;
    private LocalDateTime sentDate;
    private int courierId;

    public ShipmentCreateDto() {
    }

    public String getArrivalAddress() {
        return arrivalAddress;
    }

    public String getDepartureAddress() {
        return departureAddress;
    }

    public double getWeight() {
        return weight;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public boolean isSentFromOffice() {
        return isSentFromOffice;
    }

    public boolean isReceivedFromOffice() {
        return isReceivedFromOffice;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public int getCourierId() {
        return courierId;
    }

}
