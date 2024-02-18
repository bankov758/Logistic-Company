package com.nbu.logisticcompany.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name = "price")
    private double price;

    @Column(name = "sent_date")
    private LocalDateTime sentDate;

    @Column(name = "received_date")
    private LocalDateTime receivedDate;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Shipment() {
    }

    public Shipment(int id, String departureAddress, String arrivalAddress, double weight, User sender, User receiver,
                    OfficeEmployee employee, boolean isSentFromOffice, boolean isReceivedFromOffice, double price,
                    LocalDateTime sentDate, LocalDateTime receivedDate, Courier courier, Company company) {
        this.id = id;
        this.departureAddress = departureAddress;
        this.arrivalAddress = arrivalAddress;
        this.weight = weight;
        this.sender = sender;
        this.receiver = receiver;
        this.employee = employee;
        this.isSentFromOffice = isSentFromOffice;
        this.isReceivedFromOffice = isReceivedFromOffice;
        this.price = price;
        this.sentDate = sentDate;
        this.receivedDate = receivedDate;
        this.courier = courier;
        this.company = company;
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

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

