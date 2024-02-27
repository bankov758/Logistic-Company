package com.nbu.logisticcompany.entities.dtos.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ShipmentCreateDto {

    private String departureAddress;
    private String arrivalAddress;
    private double weight;
    private int senderId;
    private int receiverId;
    private int employeeId;
    @JsonProperty("sentFromOffice")
    private boolean sentFromOffice;
    @JsonProperty("receivedFromOffice")
    private boolean receivedFromOffice;
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

    public boolean sentFromOffice() {
        return sentFromOffice;
    }

    public boolean receivedFromOffice() {
        return receivedFromOffice;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public int getCourierId() {
        return courierId;
    }

}
