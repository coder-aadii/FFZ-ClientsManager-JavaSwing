package com.ffzclients;

import java.math.BigDecimal;
import java.sql.Date;

public class Member {
    private int id;
    private String name;
    private String phone;
    private String email;
    private Date registrationDate;
    private String fitnessType;
    private String feePackage;
    private BigDecimal feeAmount;
    private Date feePaidOn;
    private Date nextFeePaymentDate;

    // Getters and Setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getFitnessType() {
        return fitnessType;
    }

    public void setFitnessType(String fitnessType) {
        this.fitnessType = fitnessType;
    }

    public String getFeePackage() {
        return feePackage;
    }

    public void setFeePackage(String feePackage) {
        this.feePackage = feePackage;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Date getFeePaidOn() {
        return feePaidOn;
    }

    public void setFeePaidOn(Date feePaidOn) {
        this.feePaidOn = feePaidOn;
    }

    public Date getNextFeePaymentDate() {
        return nextFeePaymentDate;
    }

    public void setNextFeePaymentDate(Date nextFeePaymentDate) {
        this.nextFeePaymentDate = nextFeePaymentDate;
    }
}
