package com.nbu.logisticcompany.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "departure_address")
    private String departureAddress;

    @Column(name = "arrival_address")
    private String arrivalAddress;

    @Column(name = "weight")
    private double weight;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "office_employee_id")
    private OfficeEmployee employee;

    @Column(name = "is_sent_from_office")
    private boolean isSentFromOffice;

    @Column(name = "is_received_in_office")
    private boolean isReceivedFromOffice;

    public Shipment() {
    }

    public Shipment(int id, String departureAddress, String arrivalAddress, double weight, User sender, User receiver,
                    OfficeEmployee employee, boolean isSentFromOffice, boolean isReceivedFromOffice) {
        this.id = id;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.weight = weight;
        this.sender = sender;
        this.receiver = receiver;
        this.employee = employee;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public OfficeEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(OfficeEmployee employee) {
        this.employee = employee;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return id == shipment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

