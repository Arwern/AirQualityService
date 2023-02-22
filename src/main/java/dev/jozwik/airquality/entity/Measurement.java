package dev.jozwik.airquality.entity;

public class Measurement {
    private String type;
    private String name;
    private String value;
    private String measurementTime;

    public Measurement() {
    }

    public Measurement(final String type, final String name, final String value, final String measurementTime) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.measurementTime = measurementTime;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(final String measurementTime) {
        this.measurementTime = measurementTime;
    }
}
