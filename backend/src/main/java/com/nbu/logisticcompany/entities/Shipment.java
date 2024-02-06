package com.nbu.logisticcompany.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id ;

    @Column(name = "departure_address")
    private String departure_address;
    //TODO map sender,receiver and employeeIDs
    @Column(name = "arrival_address")
    private String arrival_address;

    @Column(name = "weight")
    private double  weight;

    @Column(name = "senderID")
    private int senderID;

    @Column(name = "receiverID")
    private int receiverID;

    @Column(name = "employeeID")
    private int employeeID;

    @Column(name = "is_sent_from_office")
    private boolean isSentFromOffice;

    @Column(name = "is_received_from_office")
    private boolean isReceivedFromOffice;

    public Shipment() {
    }

    public Shipment(int id, String departure_address, String arrival_address, double weight, int senderID, int receiverID, int employeeID, boolean isSentFromOffice, boolean isReceivedFromOffice) {
        this.id = id;
        this.departure_address = departure_address;
        this.arrival_address = arrival_address;
        this.weight = weight;
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

    public String getDeparture_address() {
        return departure_address;
    }

    public void setDeparture_address(String departure_address) {
        this.departure_address = departure_address;
    }

    public String getArrival_address() {
        return arrival_address;
    }

    public void setArrival_address(String arrival_address) {
        this.arrival_address = arrival_address;
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

