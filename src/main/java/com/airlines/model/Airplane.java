package com.airlines.model;

import java.time.LocalDate;

public class Airplane {
    private int id;
    private String codeName;
    private String model;
    private LocalDate manufactureDate;
    private int capacity;
    private double flightRange;
    private int crewId;

    public Airplane() {
    }

    public Airplane(int id, String codeName, String model, LocalDate manufactureDate, double flightRange, int crewId) {
        this.id = id;
        this.codeName = codeName;
        this.model = model;
        this.manufactureDate = manufactureDate;
        this.flightRange = flightRange;
        this.crewId = crewId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getFlightRange() {
        return flightRange;
    }

    public void setFlightRange(double flightRange) {
        this.flightRange = flightRange;
    }

    public int getCrewId() {
        return crewId;
    }

    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }
}
