package com.rides.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VehicleModel implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("uid")
    private String uid;

    @SerializedName("vin")
    private String vin;

    @SerializedName("make_and_model")
    private String makeAndModel;

    @SerializedName("color")
    private String color;

    @SerializedName("transmission")
    private String transmission;

    @SerializedName("drive_type")
    private String driveType;

    @SerializedName("fuel_type")
    private String fuelType;

    @SerializedName("car_type")
    private String carType;

    @SerializedName("car_options")
    private List<String> carOptions = null;

    @SerializedName("specs")
    private List<String> specs = null;

    @SerializedName("doors")
    private Integer doors;

    @SerializedName("mileage")
    private Integer mileage;

    @SerializedName("kilometrage")
    private Integer kilometrage;

    @SerializedName("license_plate")
    private String licensePlate;

    public VehicleModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMakeAndModel() {
        return makeAndModel;
    }

    public void setMakeAndModel(String makeAndModel) {
        this.makeAndModel = makeAndModel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public List<String> getCarOptions() {
        return carOptions;
    }

    public void setCarOptions(List<String> carOptions) {
        this.carOptions = carOptions;
    }

    public List<String> getSpecs() {
        return specs;
    }

    public void setSpecs(List<String> specs) {
        this.specs = specs;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(Integer kilometrage) {
        this.kilometrage = kilometrage;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public static List<VehicleModel> getSortedListByVin(List<VehicleModel> vehicleList){
        Collections.sort(vehicleList, new Comparator<VehicleModel>() {
            @Override
            public int compare(VehicleModel v1, VehicleModel v2) {
                return v1.getVin().compareTo(v2.getVin());
            }
        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            vehicleList.sort(Comparator.comparing(VehicleModel::getVin));
//        }else{
//            Collections.sort(vehicleList, new Comparator<VehicleModel>() {
//                @Override
//                public int compare(VehicleModel v1, VehicleModel v2) {
//                    return v1.getVin().compareTo(v2.getVin());
//                }
//            });
//        }
        return vehicleList;
    }
}
