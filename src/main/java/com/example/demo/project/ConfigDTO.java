package com.example.demo.project;
public class ConfigDTO {
    private int measurementsPerRotation;
    private int rotationSpeed;
    private int targetSpeed;


    public ConfigDTO() {}


    public int getMeasurementsPerRotation() {
        return measurementsPerRotation;
    }

    public void setMeasurementsPerRotation(int measurementsPerRotation) {
        this.measurementsPerRotation = measurementsPerRotation;
    }

    public int getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(int rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public int getTargetSpeed() {
        return targetSpeed;
    }

    public void setTargetSpeed(int targetSpeed) {
        this.targetSpeed = targetSpeed;
    }


    @Override
    public String toString() {
        return "ConfigDTO{" +
                "measurementsPerRotation=" + measurementsPerRotation +
                ", rotationSpeed=" + rotationSpeed +
                ", targetSpeed=" + targetSpeed +
                '}';
    }
}
