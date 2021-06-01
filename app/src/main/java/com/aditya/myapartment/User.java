package com.aditya.myapartment;

public class User {
    public String name;
    public String phone;
    public String secondaryPhone;
    public String flatNo;
    public String password;
    public String vehicleNos;
    public String ownerOrTenant;

    public User() {
    }

    public User(String name, String phone, String secondaryPhone, String flatNo, String password, String vehicleNos, String ownerOrTenant) {
        this.name = name;
        this.phone = phone;
        this.secondaryPhone = secondaryPhone;
        this.flatNo = flatNo;
        this.password = password;
        this.vehicleNos = vehicleNos;
        this.ownerOrTenant = ownerOrTenant;
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

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVehicleNos() {
        return vehicleNos;
    }

    public void setVehicleNos(String vehicleNos) {
        this.vehicleNos = vehicleNos;
    }

    public String getOwnerOrTenant() {
        return ownerOrTenant;
    }

    public void setOwnerOrTenant(String ownerOrTenant) {
        this.ownerOrTenant = ownerOrTenant;
    }
}
