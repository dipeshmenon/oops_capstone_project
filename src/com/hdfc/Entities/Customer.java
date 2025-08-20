package com.hdfc.Entities;

import java.time.LocalDate;
import java.util.Objects;

public class Customer {

    private String name;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private int phone;
    private String customerId;

    public Customer() {
    }

    public Customer(String name, String email, String password, LocalDate dateOfBirth, int phone, String customerId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phone=" + phone +
                ", customerId='" + customerId + '\'' +
                '}';
    }
    @Override
    public boolean equals (Object obj){
        if(obj == null){
            return  false;
        }
        if(this == obj){
            return true;
        }

        Customer customer = (Customer) obj;
        return Objects.equals(customerId, customer.getCustomerId()) && Objects.equals(email,customer.getEmail());
    }


    @Override
    public int hashCode() {
        return Objects.hash(email, customerId);
    }
}
