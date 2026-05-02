package com.project.scheduler.controller;

import com.project.scheduler.middleware.RemoteLogger;
import com.project.scheduler.model.Vehicle;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final RemoteLogger logger;
    private final List<Vehicle> database = new ArrayList<>();

    public VehicleController(RemoteLogger logger) {
        this.logger = logger;
    }

    @PostMapping
    public Vehicle registerVehicle(@RequestBody Vehicle data) {
        if (data.getVehicleId() == null) {
            data.setVehicleId(java.util.UUID.randomUUID().toString());
        }
        database.add(data);
        
        logger.dispatchLog("backend", "info", "controller", "New vehicle registered: " + data.getPlateNumber());
        return data;
    }

    @GetMapping
    public List<Vehicle> fetchAll() {
        logger.dispatchLog("backend", "debug", "controller", "Retrieved all scheduled vehicles");
        return database;
    }
}
