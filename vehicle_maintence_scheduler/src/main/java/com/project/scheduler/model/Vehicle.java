package com.project.scheduler.model;

import java.util.UUID;

public class Vehicle {
    private String vehicleId;
    private String plateNumber;
    private String modelName;
    
    public Vehicle() {}

    public Vehicle(String plateNumber, String modelName) {
        this.vehicleId = UUID.randomUUID().toString();
        this.plateNumber = plateNumber;
        this.modelName = modelName;
    }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }
    
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    
    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
}
