package com.nbu.logisticcompany.entities.dtos.shipment;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class ShipmentCreateDto {

    @NotNull(message = "Address field is mandatory")
    @Size(min = 3, max = 64, message = "The address should be between 3 and 64 symbols")
    private String departureAddress;

    @NotNull(message = "Address field is mandatory")
    @Size(min = 3, max = 64, message = "The address should be between 3 and 64 symbols")
    private String arrivalAddress;

    @NotNull(message = "Please enter weight")
    @DecimalMin(value = "0.0", inclusive = false, message = "Weight has to be greater than 0")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Weight has to be less than or equal to 1000")
    @Positive(message = "Weight has to be a positive number")
    private double weight;

    @NotNull(message = "Please enter the sender's id")
    @Positive(message = "Sender ID has to be a positive number")
    private int senderId;

    @NotNull(message = "Please enter the receiver's id")
    @Positive(message = "Receiver ID has to be a positive number")
    private int receiverId;

    @NotNull(message = "Please enter the employee's id")
    @Positive(message = " Employee ID has to be a positive number")
    private int employeeId;

    @FutureOrPresent(message = "Please enter a current or future date")
    private LocalDate sentDate;

    @NotNull(message = "Please enter the courier's id")
    @Positive(message = "Courier ID has to be a positive number")
    private int courierId;

    @Positive(message = "Company ID has to be a positive number")
    private int companyId;

    public ShipmentCreateDto() {
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
